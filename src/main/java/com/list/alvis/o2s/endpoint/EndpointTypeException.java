package com.list.alvis.o2s.endpoint;

/**
 * <p>
 * Exception to throw when there is no value of Open API's title, name, and mapping SPARQL
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
@SuppressWarnings("serial")
public class EndpointTypeException extends Exception {

	public EndpointTypeException(String s) {
		super(s);
	}
}
