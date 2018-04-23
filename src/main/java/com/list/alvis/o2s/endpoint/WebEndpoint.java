package com.list.alvis.o2s.endpoint;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.list.alvis.o2s.core.ValueNotExistException;

/**
 * <p>
 * The WebEndpoint class indicates the entry point to a service for SPARQL. It
 * implements a way to connect the endpoint through the web.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
public class WebEndpoint extends Endpoint {

	private static final Logger logger = LoggerFactory.getLogger(WebEndpoint.class);
	
	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of Endpoint class.
	 * @throws ValueNotExistException 
	 *            If there is no value of Open API's title, name, and mapping SPARQL 
	 */
	public WebEndpoint(Resource resource) throws ValueNotExistException {
		super(resource);
	}
	
	/**
	 * This method is used to execute SPARQL through Web-base SPARQL endpoint.
	 * 
	 * @param sparql
	 *            This is the SPARQL to run.
	 * @return SPARQL results as JSON
	 */
	public String execute(String sparql) {
		logger.info("Run SPARQL to web endpoint.");
		logger.debug("Web endpoint: " + this.url);
		Query query = QueryFactory.create(sparql);
		QueryEngineHTTP qEngine = QueryExecutionFactory.createServiceRequest(this.url, query);
		ResultSet results = qEngine.execSelect();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, results);
		logger.debug(new String(outputStream.toByteArray()));
		return new String(outputStream.toByteArray());
	}
	
	/**
	 * Returns a string representation of this WebEndpoint instance
	 * 
	 * @return a string representation of this WebEndpoint instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.url);
		return sb.toString();
	}
}
