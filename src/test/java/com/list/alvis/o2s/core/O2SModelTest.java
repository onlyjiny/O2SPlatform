package com.list.alvis.o2s.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.TestCase;
import org.junit.Test;

public class O2SModelTest extends TestCase {
	
	O2SModel model;
	
	public O2SModelTest( String testName ) throws IOException, ValueNotExistException, OpenAPINotExistException {
        super( testName );
		this.model = new O2SModel();
		this.model.load("resource/OpenAPI.ttl");
    }
	
	public void testLoad() {
        try {
			this.model.load("resource/OpenAPI.ttl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValueNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenAPINotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertNotNull(this.model);
    }
	
	@Test
	public void testExecute() throws OpenAPINotExistException, MissingParameterException {
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("era", "조선");
		params.put("limit", "100");
		params.put("offset", "0");
		String json = this.model.execute("getPersonOfEra", params);
		System.out.println(json);
		assertNotNull(json);
	}
	
	@Test
	public void testGetResultVariables() throws OpenAPINotExistException, MissingParameterException {
//		ArrayList<String> vars = this.model.getOpenApi("getBookList").getResultVariables();
		ArrayList<String> vars = this.model.getOpenApi("getPersonOfEra").getResultVariables();
		for(int i = 0; i < vars.size(); i++) {
			System.out.println(vars.get(i));
		}
		assertNotNull(vars);
	}

	@Test(expected = OpenAPINotExistException.class)
	public void testExecuteOpenAPINotExistException() throws OpenAPINotExistException, MissingParameterException {
		this.model.execute("getAuthotList", null);
	}
	
	@Test(expected = MissingParameterException.class)
	public void testExecuteMissingParameterException() throws OpenAPINotExistException, MissingParameterException {
		this.model.execute("getBookList", null);
	}
	
}
