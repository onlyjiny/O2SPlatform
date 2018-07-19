package com.list.alvis.o2s.core;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * <p>
 * Vocabularies for Open API to SPARQL Mapping Platform
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
public class Vocabulary {

	/**
	 * the URI variable for OpenAPI ontology class
	 */
	public final static String OPENAPI_CLASS = "http://openapi.alvis.kr/ontology/OpenAPI";
	
	/**
	 * the variable of Resource type for OpenAPI ontology class
	 */
	public final static Resource OPENAPI_CLASS_RESOURCE = ResourceFactory.createResource(Vocabulary.OPENAPI_CLASS);
	
	/**
	 * the URI variable for Open API name property
	 */
	public final static String OPENAPI_NAME = "http://openapi.alvis.kr/ontology/openApiName";
	
	/**
	 * the variable of Property type for openAPIName property
	 */
	public final static Property OPENAPI_NAME_PROPERTY = ResourceFactory.createProperty(Vocabulary.OPENAPI_NAME);
	
	/**
	 * the URI variable for mapping SPARQL property
	 */
	public final static String MAPPING_SPARQL = "http://openapi.alvis.kr/ontology/mappingSparql";
	
	/**
	 * the variable of Property type for mappingSparql property
	 */
	public final static Property MAPPING_SPARQL_PROPERTY = ResourceFactory.createProperty(Vocabulary.MAPPING_SPARQL);
	
	/**
	 * the URI variable for mapping SPARQL property
	 */
	public final static String ENDPOINT = "http://openapi.alvis.kr/ontology/hasEndpoint";
	
	/**
	 * the variable of Property type for mappingSparql property
	 */
	public final static Property ENDPOINT_PROPERTY = ResourceFactory.createProperty(Vocabulary.ENDPOINT);
	
	/**
	 * the URI variable for url of SPARQL endpoint
	 */
	public final static String URL = "http://openapi.alvis.kr/ontology/url";
	
	/**
	 * the variable of Property type for url property
	 */
	public final static Property URL_PROPERTY = ResourceFactory.createProperty(Vocabulary.URL);
	
	/**
	 * the URI variable for id of SPARQL endpoint
	 */
	public final static String ID = "http://openapi.alvis.kr/ontology/endpointId";
	
	/**
	 * the variable of Property type for id property
	 */
	public final static Property ID_PROPERTY = ResourceFactory.createProperty(Vocabulary.ID);
	
	/**
	 * the URI variable for password of SPARQL endpoint
	 */
	public final static String PWD = "http://openapi.alvis.kr/ontology/endpointPwd";
	
	/**
	 * the variable of Property type for password property
	 */
	public final static Property PWD_PROPERTY = ResourceFactory.createProperty(Vocabulary.PWD);
	
	/**
	 * the URI variable for port of SPARQL endpoint
	 */
	public final static String PORT = "http://openapi.alvis.kr/ontology/endpointPort";
	
	/**
	 * the variable of Property type for password property
	 */
	public final static Property PORT_PROPERTY = ResourceFactory.createProperty(Vocabulary.PORT);
	
	/**
	 * the URI variable for password of SPARQL endpoint
	 */
	public final static String SERVICE_NAME = "http://openapi.alvis.kr/ontology/endpointServiceName";
	
	/**
	 * the variable of Property type for password property
	 */
	public final static Property SERVICE_NAME_PROPERTY = ResourceFactory.createProperty(Vocabulary.SERVICE_NAME);
	
	/**
	 * the URI variable for parameters of Open API
	 */
	public final static String PARAMETER = "http://openapi.alvis.kr/ontology/hasParameter";
	
	/**
	 * the variable of Property type for hasParameter property
	 */
	public final static Property PARAMETER_PROPERTY = ResourceFactory.createProperty(Vocabulary.PARAMETER);
	
	/**
	 * the URI variable for Open API name property
	 */
	public final static String PARAMETER_NAME = "http://openapi.alvis.kr/ontology/paramName";
	
	/**
	 * the variable of Property type for openAPIName property
	 */
	public final static Property PARAMETER_NAME_PROPERTY = ResourceFactory.createProperty(Vocabulary.PARAMETER_NAME);

}
