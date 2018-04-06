package com.list.alvis.o2s.core;

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
public class ValueNotExistException extends Exception {
	
	public ValueNotExistException(String s) {
		super(s);
	}

}
