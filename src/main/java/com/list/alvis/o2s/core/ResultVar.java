package com.list.alvis.o2s.core;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;

/**
 * <p>
 * The ResultVar class includes a result variables of Open API.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-07-19
 */
public class ResultVar {

	private String title;
	private String comment;
	private String resultVar;
	
	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of Parameter class.
	 * @throws ValueNotExistException
	 *             If there is no value of Open API's title, name, and mapping
	 *             SPARQL
	 */
	public ResultVar(Resource resource) throws ValueNotExistException {
		this.setTitle(resource);
		this.setComment(resource);
		this.setResultVar(resource);
	}

	private void setTitle(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(DC.title)) {
			throw new ValueNotExistException(
					"An " + resource.getURI() + " instance of ResultVar class must have the dc:title property.");
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

	private void setResultVar(Resource resource) throws ValueNotExistException {
		if (!resource.hasProperty(Vocabulary.RESULT_VAR_PROPERTY)) {
			throw new ValueNotExistException("An " + resource.getURI()
					+ " instance of ResultVar class must have the result variable name using oas:resultVar property.");
		}
		Statement statement = resource.getProperty(Vocabulary.RESULT_VAR_PROPERTY);
		this.resultVar = statement.getObject().asLiteral().getValue().toString();
	}
	
	/**
	 * Return a title
	 * 
	 * @return title string of Open API result variable
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Return a comment
	 * 
	 * @return comment string of Open API result variable
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Return a result variable name
	 * 
	 * @return result variable name string of Open API result variable
	 */
	public String getResultVar() {
		return resultVar;
	}
	
	/**
	 * Returns a string representation of this ResultVar instance
	 * 
	 * @return a string representation of this ResultVar instance
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.title);
		sb.append(", ");
		sb.append(this.comment);
		return sb.toString();
	}
}
