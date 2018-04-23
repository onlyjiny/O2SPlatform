package com.list.alvis.o2s.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The O2SModel class includes functions to mapping and use Open API as SPARQL.
 * You can load their ontologies that contains mapping information between Open
 * API and SPARQL. And you can process user's request through Open API protocol.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
public class O2SModel {
	
	private static final Logger logger = LoggerFactory.getLogger(O2SModel.class);

	/**
	 * the variable for Open API list
	 */
	private Hashtable<String, OpenAPI> mapper;

	/**
	 * Constructor.
	 */
	public O2SModel() {
		this.mapper = new Hashtable<String, OpenAPI>();
	}

	/**
	 * This method is used to load ontology that includes mapping information
	 * between Open API and SPARQL.
	 * 
	 * @param filename
	 *            This is the file name to load.
	 * @throws IOException 
	 *            If there is no file to read
	 * @throws ValueNotExistException
	 *            If there is no value of Open API's title, name, and mapping SPARQL 
	 * @throws OpenAPINotExistException 
	 *            If there is no Open API declaration in the ontology 
	 */
	public void load(String filename) throws IOException, ValueNotExistException, OpenAPINotExistException {
		Model model = this.readModel(filename);
		ResIterator oaList = model.listSubjectsWithProperty(RDF.type, Vocabulary.OPENAPI_CLASS_RESOURCE);
		if(oaList.hasNext()) {
			while(oaList.hasNext()) {
				Resource oa = oaList.nextResource();
				OpenAPI openApi = new OpenAPI(oa);
				this.mapper.put(openApi.getOpenApiName(), openApi);
			}
		} else {
			logger.error("At least one Open API must be defined in ontology.");
			throw new OpenAPINotExistException("At least one Open API must be defined in ontology.");
		}
		logger.debug(this.toString());
		logger.info("Complete to read Open API to SPARQL mapping ontology.");
	}
	
	private Model readModel(String filename) throws IOException {
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(filename);
		String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
		if(extension.equals("ttl")) {
			logger.info("Start to read Open API to SPARQL mapping ontology as Turtle format.");
			model.read(in, null, "TURTLE");
		} else if(extension.equals("owl")) {
			logger.info("Start to read Open API to SPARQL mapping ontology as RDF/XML format.");
			model.read(in, null, "RDF/XML");
		} else if(extension.equals("n3")) {
			logger.info("Start to read Open API to SPARQL mapping ontology as N3 format.");
			model.read(in, null, "N3");
		}
		in.close();
		return model;
	}
	
	/**
	 * This method is used to run Open API.
	 * 
	 * @param openApiName
	 *            This is the Open API name to execute.
	 * @param parameters
	 *            This is the parameters to execute the Open API.
	 * @return SPARQL results as JSON
	 * @throws OpenAPINotExistException 
	 *             There is no Open API list or Open API given name as request
	 * @throws MissingParameterException 
	 *             If SPARQL has parameter(s) that not is(are) set value
	 */
	public String execute(String openApiName, Hashtable<String, String> parameters) throws OpenAPINotExistException, MissingParameterException {
		if(!this.mapper.containsKey(openApiName)) {
			throw new OpenAPINotExistException("There is no proper Open API of " + openApiName + " name.");
		}
		OpenAPI openApi = this.mapper.get(openApiName);
		String sparql = openApi.getSparql(parameters);
		logger.debug("Open API name: " + openApiName);
		logger.debug("Endpoint: " + openApi.getEndpoint().getUrl());
		logger.debug("SPARQL: " + sparql);
		return openApi.getEndpoint().execute(sparql);
	}
	
	/**
	 * This method is used to run Open API.
	 * 
	 * @param parameters
	 *            This is the parameters to execute the Open API.
	 * @return SPARQL results as JSON
	 * @throws OpenAPINotExistException 
	 *             There is no Open API list or Open API given name as request
	 * @throws MissingParameterException 
	 *             If SPARQL has parameter(s) that not is(are) set value
	 */
	public String execute(Hashtable<String, String> parameters) throws OpenAPINotExistException, MissingParameterException {
		if(!parameters.containsKey("method")) {
			throw new OpenAPINotExistException("The url must contain the method parameter.");
		}
		String openApiName = parameters.get("method");
		parameters.remove("method");
		return this.execute(openApiName, parameters);
	}
	
	/**
	 * This method is used to run Open API.
	 * 
	 * @param url
	 *            This is the parameters to execute the Open API.
	 * @return SPARQL results as JSON
	 * @throws OpenAPINotExistException 
	 *             There is no Open API list or Open API given name as request
	 * @throws MissingParameterException 
	 *             If SPARQL has parameter(s) that not is(are) set value
	 */
	public String execute(URL url) throws OpenAPINotExistException, MissingParameterException {
		return this.execute(this.getQueryMap(url.getQuery()));
	}
	
	/**
	 * This method is used to run Open API.
	 * 
	 * @param urlString
	 *            This is the parameters to execute the Open API.
	 * @return SPARQL results as JSON
	 * @throws OpenAPINotExistException 
	 *             There is no Open API list or Open API given name as request
	 * @throws MissingParameterException 
	 *             If SPARQL has parameter(s) that not is(are) set value
	 * @throws MalformedURLException
	 *             If url string parameter is invalid
	 */
	public String execute(String urlString) throws OpenAPINotExistException, MissingParameterException, MalformedURLException {
		URL url = new URL(urlString);
		return this.execute(this.getQueryMap(url.getQuery()));
	}
	
	private Hashtable<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Hashtable<String, String> map = new Hashtable<String, String>();
	    for (String param : params)
	    {
	    	int pos = param.indexOf("=");
	        String name = param.substring(0, pos);
	        String value = param.substring(pos + 1, param.length());
	        if((!name.equals("")) && (!value.equals(""))) {
		        map.put(name, value);
	        }
	    }
	    return map;
	}
	
	/**
	 * Return a Open API information
	 * 
	 * @param openApiName
	 *            This is the Open API name to get a object.
	 * @return title string of Open API
	 */
	public OpenAPI getOpenApi(String openApiName) {
		return this.mapper.get(openApiName);
	}
	
	/**
	 * Returns a string representation of this O2SModel instance
	 * 
	 * @return a string representation of this O2SModel instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> keys = this.mapper.keys();
		while(keys.hasMoreElements()) {
			String methodName = keys.nextElement();
			String openApi = this.mapper.get(methodName).toString();
			sb.append(openApi.toString());
		}
		return sb.toString();
	}

}
