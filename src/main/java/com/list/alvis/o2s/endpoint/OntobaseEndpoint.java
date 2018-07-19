package com.list.alvis.o2s.endpoint;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.sparql.resultset.SPARQLResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.list.alvis.o2s.core.ValueNotExistException;
import com.list.alvis.o2s.core.Vocabulary;
import com.list.ontobase.client.query.SparqlQuery;
import com.list.ontobase.msg.common.OntoException;

/**
 * <p>
 * The OntobaseEndpoint class indicates the entry point to a service for SPARQL. It
 * implements a way to connect the endpoint for Ontobase.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-23
 */
public class OntobaseEndpoint extends Endpoint {

	private static final Logger logger = LoggerFactory.getLogger(OntobaseEndpoint.class);
	
	private int port;
	private String service;
	
	private SparqlQuery sq;

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of Endpoint class.
	 * @throws ValueNotExistException 
	 *            If there is no value of Open API's title, name, and mapping SPARQL 
	 */
	public OntobaseEndpoint(Resource resource) throws ValueNotExistException {
		super(resource);
		if (!resource.hasProperty(Vocabulary.PORT_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of Ontobase Endpoint class must have the port property to connect Ontobase endpoint.");
		}
		Statement idStatement = resource.getProperty(Vocabulary.PORT_PROPERTY);
		this.port = idStatement.getObject().asLiteral().getInt();
		if (!resource.hasProperty(Vocabulary.SERVICE_NAME_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of Ontobase Endpoint class must have the service name property to connect Ontobase endpoint.");
		}
		Statement pwdStatement = resource.getProperty(Vocabulary.SERVICE_NAME_PROPERTY);
		this.service = pwdStatement.getObject().asLiteral().getValue().toString();
		
		logger.info("Run SPARQL to Ontobase endpoint.");
		logger.debug("Ontobase endpoint: " + super.url);
		logger.debug("Ontobase endpoint ID: " + this.port);
		logger.debug("Ontobase endpoint Password: " + this.service);
		this.sq = new SparqlQuery(super.url, this.port, this.service);
	}

	/**
	 * This method is used to execute SPARQL through Ontobase SPARQL endpoint.
	 * 
	 * @param sparql
	 *            This is the SPARQL to run.
	 * @return SPARQL results as JSON
	 */
	@Override
	public String execute(String sparql) {
		SPARQLResult result = null;
		try {
			result = this.sq.execute(sparql);
		} catch (OntoException e) {
			logger.error("Ontobase query exception: " + e.getMessage());
			logger.error(e.getPrintStackTrace());
		}
		ResultSet results = result.getResultSet();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, results);
		logger.debug(new String(outputStream.toByteArray()));
		return new String(outputStream.toByteArray());
	}
	
	/**
	 * Returns a string representation of this OntobaseEndpoint instance
	 * 
	 * @return a string representation of this OntobaseEndpoint instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.url);
		sb.append(":");
		sb.append(this.port);
		sb.append("/");
		sb.append(this.service);
		return sb.toString();
	}
}
