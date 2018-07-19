package com.list.alvis.o2s.util;

import org.apache.jena.rdf.model.Resource;

/**
 * <p>
 * The OntologyUtils class contains functions for O2S mapping ontology.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-23
 */
public class OntologyUtils {
	
	private static String ENDPOINT_RESOURCE_URI = "http://openapi.alvis.kr/ontology/Endpoint";
	private static String WEB_ENDPOINT_RESOURCE_URI = "http://openapi.alvis.kr/ontology/WebEndpoint";
	private static String VIRTUOSO_ENDPOINT_RESOURCE_URI = "http://openapi.alvis.kr/ontology/VirtuosoEndpoint";
	private static String ONTOBASE_ENDPOINT_RESOURCE_URI = "http://openapi.alvis.kr/ontology/OntobaseEndpoint";

	/**
	 * This static method is used to get the type of endpoint.
	 * 
	 * @param r
	 *            This is an instance of Endpoint.
	 * @return type of Endpoint instance
	 */
	public static int getEndpointType(Resource r) {
		String uri = r.getURI();
		if(uri.equals(OntologyUtils.ENDPOINT_RESOURCE_URI)) {
			return 0;
		} else if(uri.equals(OntologyUtils.WEB_ENDPOINT_RESOURCE_URI)) {
			return 1;
		} else if(uri.equals(OntologyUtils.VIRTUOSO_ENDPOINT_RESOURCE_URI)) {
			return 2;
		} else if(uri.equals(OntologyUtils.ONTOBASE_ENDPOINT_RESOURCE_URI)) {
			return 3;
		} else {
			return 0;
		}
	}
}
