package com.my;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;


public class HelloImpl implements Hello {

	@WebMethod
	@WebResult
	public String sayHello(@WebParam(name = "name") String name) {
		String msg = "Hello " + name + "!";
		return msg;
	}

}
