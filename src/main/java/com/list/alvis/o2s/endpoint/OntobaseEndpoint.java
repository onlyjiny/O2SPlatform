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

public class OntobaseEndpoint extends Endpoint {

	private static final Logger logger = LoggerFactory.getLogger(OntobaseEndpoint.class);
	
	private int port;
	private String service;
	
	private SparqlQuery sq;

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
}
