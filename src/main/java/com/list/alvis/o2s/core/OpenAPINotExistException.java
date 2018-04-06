package com.list.alvis.o2s.core;

/**
 * <p>
 * Exception to throw when there is no Open API list or Open API given name as request
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
@SuppressWarnings("serial")
public class OpenAPINotExistException extends Exception {

	public OpenAPINotExistException(String s) {
		super(s);
	}
}
