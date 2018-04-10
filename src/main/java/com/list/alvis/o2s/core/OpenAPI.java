package com.list.alvis.o2s.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;

/**
 * <p>
 * The OpenAPI class includes an Open API information.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
public class OpenAPI {

	private String title;
	private String comment;
	private String openApiName;
	private String mappingSparql;
	private Endpoint endpoint;
	private ParameterSet params;

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of OpenAPI class.
	 * @throws ValueNotExistException
	 *             If there is no value of Open API's title, name, and mapping
	 *             SPARQL
	 */
	public OpenAPI(Resource resource) throws ValueNotExistException {
		this.setTitle(resource);
		this.setComment(resource);
		this.setOpenApiName(resource);
		this.setMappingSparql(resource);
		this.setEndpoint(resource);
		this.setParameters(resource);
	}

	private void setTitle(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(DC.title)) {
			throw new ValueNotExistException(
					"An " + resource.getURI() + " instance of OpenAPI class must have the dc:title property.");
		}
		Statement statement = resource.getProperty(DC.title);
		this.title = statement.getObject().asLiteral().getValue().toString();
	}

	private void setComment(Resource resource) {
		Statement statement = resource.getProperty(RDFS.comment);
		if (statement == null) {
			this.comment = new String();
		} else {
			this.comment = statement.getObject().asLiteral().getValue().toString();
		}
	}

	private void setOpenApiName(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(Vocabulary.OPENAPI_NAME_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of OpenAPI class must have the Open API method name using oas:openApiName property.");
		}
		Statement statement = resource.getProperty(Vocabulary.OPENAPI_NAME_PROPERTY);
		this.openApiName = statement.getObject().asLiteral().getValue().toString();
	}

	private void setMappingSparql(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(Vocabulary.MAPPING_SPARQL_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of OpenAPI class must have the mapping SPARQL using oas:mappingSparql property.");
		}
		Statement statement = resource.getProperty(Vocabulary.MAPPING_SPARQL_PROPERTY);
		this.mappingSparql = statement.getObject().asLiteral().getValue().toString();
	}

	private void setEndpoint(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(Vocabulary.ENDPOINT_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of OpenAPI class must have the SPARQL endpoint information using oas:hasEndpoint property.");
		}
		Statement statement = resource.getProperty(Vocabulary.ENDPOINT_PROPERTY);
		this.endpoint = new WebEndpoint(statement.getObject().asResource());
	}

	private void setParameters(Resource resource) throws ValueNotExistException {
		this.params = new ParameterSet(resource);
	}

	/**
	 * Constructor.
	 * 
	 * @param parameters
	 *            This is the parameters to execute the Open API.
	 * @return SPARQL string
	 * @throws MissingParameterException
	 *             If SPARQL has parameter(s) that not is(are) set value
	 */
	public String getSparql(Hashtable<String, String> parameters) throws MissingParameterException {
		String sparql = new String(this.mappingSparql);
		if ((parameters == null) || (parameters.isEmpty())) {
			// do nothing
		} else {
			Enumeration<String> keys = parameters.keys();
			while (keys.hasMoreElements()) {
				String paramName = keys.nextElement();
				String value = parameters.get(paramName);
				sparql = sparql.replace("[[" + paramName + "]]", value);
			}
		}
		String missing = this.isValid(sparql);
		if (missing == null) {
			return sparql;
		} else {
			throw new MissingParameterException(
					missing + " parameter(s) cannot be ommitted for the " + this.openApiName + " Open API.");
		}
	}

	private String isValid(String sparql) {
		Pattern pattern = Pattern.compile("(\\[\\[)(.*?)(\\]\\])");
		Matcher matcher = pattern.matcher(sparql);
		List<String> listMatches = new ArrayList<String>();
		while (matcher.find()) {
			listMatches.add(matcher.group(2));
		}
		if (listMatches.size() == 0) {
			return null;
		} else {
			String missing = new String();
			for (int i = 0; i < listMatches.size(); i++) {
				missing += listMatches.get(i);
				if (i != (listMatches.size() - 1)) {
					missing += ", ";
				}
			}
			return missing;
		}
	}

	/**
	 * Return a title
	 * 
	 * @return title string of Open API
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Return a comment
	 * 
	 * @return comment string of Open API
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Return a Open API name
	 * 
	 * @return Open API name string of Open API
	 */
	public String getOpenApiName() {
		return openApiName;
	}

	/**
	 * Return a mapping SPARQL
	 * 
	 * @return mapping SPARQL string of Open API
	 */
	public String getMappingSparql() {
		return mappingSparql;
	}

	/**
	 * Return a SPARQL endpoint
	 * 
	 * @return instance of Endpoint class
	 */
	public Endpoint getEndpoint() {
		return endpoint;
	}

	/**
	 * Return a SPARQL endpoint URL
	 * 
	 * @return URL string of SPARQL endpoint
	 */
	public String getEndpointUrl() {
		return endpoint.getUrl();
	}

	/**
	 * Return parameters of an Open API
	 * 
	 * @return parameter set of SPARQL endpoint as Hashtable
	 */
	public ParameterSet getParameterSet() {
		return params;
	}

	/**
	 * Return result variables of an Open API
	 * 
	 * @return result variable set of Open API
	 */
	public ArrayList<String> getResultVariables() {
		ArrayList<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(select\\s)(.*?)(where)");
		Matcher matcher = pattern.matcher(this.mappingSparql.toLowerCase());
		List<String> listMatches = new ArrayList<String>();
		while (matcher.find()) {
			listMatches.add(matcher.group(2));
		}
		if (listMatches.size() == 0) {
			return null;
		} else {
			String select = listMatches.get(0);
			String[] variables = select.trim().split(" ");
			for(int i = 0; i < variables.length; i++) {
				result.add(variables[i]);
			}
		}
		return result;
	}
	
	/**
	 * Returns a string representation of this OpenAPI instance
	 * 
	 * @return a string representation of this OpenAPI instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Open API Name: ");
		sb.append(this.openApiName);
		sb.append("\n");
		return sb.toString();
	}
}
