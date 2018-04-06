package com.list.alvis.o2s.core;

/**
 * <p>
 * Exception to throw when one or more parameter value are missing
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
@SuppressWarnings("serial")
public class MissingParameterException extends Exception {
	
	public MissingParameterException(String s) {
		super(s);
	}

}
