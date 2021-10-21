package com.rewards.fetch.service.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;

import com.rewards.fetch.service.objects.ErrorObject;

public class Controller {
    /**
     * Simply builds an error response containing an ErrorObject object.
     * @param code error code
     * @param msg error message
     * @return ErrorObject containing error code and error message
     */
  protected Response error(int code, String msg) {
    return Response.status(code).entity(new ErrorObject(code, msg)).build();
  }

  protected Response error(int code, Exception e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    String exceptionAsString = sw.toString();
    return Response.status(code).entity(new ErrorObject(code, exceptionAsString)).build();
  }
}
