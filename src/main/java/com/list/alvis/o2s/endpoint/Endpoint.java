package com.list.alvis.o2s.endpoint;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import com.list.alvis.o2s.core.ValueNotExistException;
import com.list.alvis.o2s.core.Vocabulary;

/**
 * <p>
 * The Endpoint class indicates the entry point to a service for SPARQL. It is
 * the abstract class, and it is extended various classes that represent a way
 * to connect.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
public abstract class Endpoint {

	protected String url;

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of Endpoint class.
	 * @throws ValueNotExistException
	 *             If there is no value of Open API's title, name, and mapping
	 *             SPARQL
	 */
	public Endpoint(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(Vocabulary.URL_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of Endpoint class must have the url property to connect SPARQL endpoint.");
		}
		Statement statement = resource.getProperty(Vocabulary.URL_PROPERTY);
		this.url = statement.getObject().asLiteral().getValue().toString();
	}

	/**
	 * Return a URL of the SPARQL endpoint
	 * 
	 * @return URL string of SPARQL endpoint
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Abstract method to execute SPARQL
	 * 
	 * @param sparql
	 *            This is the SPARQL to run.
	 * @return SPARQL results as JSON
	 */
	public abstract String execute(String sparql);

}
