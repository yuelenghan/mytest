package com.my;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface Hello {

	@WebMethod
	@WebResult
	public String sayHello(@WebParam(name = "name") String name);
}
