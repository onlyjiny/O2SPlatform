package com.list.alvis.o2s.core;

import java.util.Hashtable;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

/**
 * <p>
 * The ParameterSet class includes parameters information of Open API.
 * </p>
 * 
 * @author Myungjin Lee
 * @version 0.1
 * @since 2018-04-05
 */
@SuppressWarnings("serial")
public class ParameterSet extends Hashtable<String, Parameter> {

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            This is the resource that indicates an instance of OpenAPI class.
	 * @throws ValueNotExistException
	 *             If there is no value of Open API's title, name, and mapping
	 *             SPARQL
	 */
	public ParameterSet(Resource resource) throws ValueNotExistException {
		super();
		Model model = resource.getModel();
		NodeIterator ri = model.listObjectsOfProperty(resource, Vocabulary.PARAMETER_PROPERTY);
		while(ri.hasNext()) {
			RDFNode node = ri.nextNode();
			Parameter param = new Parameter(node.asResource());
			this.put(param.getParameterName(), param);
		}
	}
}
