package com.list.alvis.o2s.endpoint;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.list.alvis.o2s.core.ValueNotExistException;
import com.list.alvis.o2s.core.Vocabulary;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * <p>
 * The VirtuosoEndpoint class indicates the entry point to a service for SPARQL. It
 * implements a way to connect the endpoint for Virtuoso.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-23
 */
public class VirtuosoEndpoint extends Endpoint {

	private static final Logger logger = LoggerFactory.getLogger(VirtuosoEndpoint.class);

	private String id;
	private String pwd;
	
	private VirtGraph set;
	
	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of Endpoint class.
	 * @throws ValueNotExistException 
	 *            If there is no value of Open API's title, name, and mapping SPARQL 
	 */
	public VirtuosoEndpoint(Resource resource) throws ValueNotExistException {
		super(resource);
		if (!resource.hasProperty(Vocabulary.ID_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of Virtuoso Endpoint class must have the id property to connect Virtuoso endpoint.");
		}
		Statement idStatement = resource.getProperty(Vocabulary.ID_PROPERTY);
		this.id = idStatement.getObject().asLiteral().getValue().toString();
		if (!resource.hasProperty(Vocabulary.PWD_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of Virtuoso Endpoint class must have the password property to connect Virtuoso endpoint.");
		}
		Statement pwdStatement = resource.getProperty(Vocabulary.PWD_PROPERTY);
		this.pwd = pwdStatement.getObject().asLiteral().getValue().toString();
		
		logger.info("Run SPARQL to Virtuoso endpoint.");
		logger.debug("Virtuoso endpoint: " + this.url);
		logger.debug("Virtuoso endpoint ID: " + this.id);
		logger.debug("Virtuoso endpoint Password: " + this.pwd);
		this.set = new VirtGraph (super.url, this.id, this.pwd);
	}
	
	/**
	 * This method is used to execute SPARQL through Virtuoso SPARQL endpoint.
	 * 
	 * @param sparql
	 *            This is the SPARQL to run.
	 * @return SPARQL results as JSON
	 */
	public String execute(String sparql) {
		Query query = QueryFactory.create(sparql);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, set);
		ResultSet results = vqe.execSelect();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, results);
		logger.debug(new String(outputStream.toByteArray()));
		return new String(outputStream.toByteArray());
	}
	
	/**
	 * Returns a string representation of this VirtuosoEndpoint instance
	 * 
	 * @return a string representation of this VirtuosoEndpoint instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.url);
		return sb.toString();
	}
}
