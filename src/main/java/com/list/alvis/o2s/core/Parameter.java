package com.list.alvis.o2s.core;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;

/**
 * <p>
 * The Parameter class includes a parameter information of Open API.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
public class Parameter {

	private String title;
	private String comment;
	private String parameterName;

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of Parameter class.
	 * @throws ValueNotExistException
	 *             If there is no value of Open API's title, name, and mapping
	 *             SPARQL
	 */
	public Parameter(Resource resource) throws ValueNotExistException {
		this.setTitle(resource);
		this.setComment(resource);
		this.setParameterName(resource);
	}

	private void setTitle(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(DC.title)) {
			throw new ValueNotExistException(
					"An " + resource.getURI() + " instance of Parameter class must have the dc:title property.");
		}
		Statement statement = resource.getProperty(DC.title);
		this.title = statement.getObject().asLiteral().getValue().toString();
	}

	private void setComment(Resource resource) {
		Statement statement = resource.getProperty(RDFS.comment);
		if (statement == null) {
			this.comment = new String();
		} else {
			this.comment = statement.getObject().asLiteral().getValue().toString();
		}
	}

	private void setParameterName(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(Vocabulary.PARAMETER_NAME_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of Parameter class must have the parameter name using oas:paramName property.");
		}
		Statement statement = resource.getProperty(Vocabulary.PARAMETER_NAME_PROPERTY);
		this.parameterName = statement.getObject().asLiteral().getValue().toString();
	}

	/**
	 * Return a title
	 * 
	 * @return title string of Open API parameter
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Return a comment
	 * 
	 * @return comment string of Open API parameter
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Return a Open API name
	 * 
	 * @return parameter name string of Open API parameter
	 */
	public String getParameterName() {
		return parameterName;
	}
	
	/**
	 * Returns a string representation of this Parameter instance
	 * 
	 * @return a string representation of this Parameter instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.title);
		sb.append(", ");
		sb.append(this.comment);
		return sb.toString();
	}
}
