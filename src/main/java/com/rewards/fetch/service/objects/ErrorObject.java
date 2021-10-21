package com.rewards.fetch.service.objects;

import org.codehaus.jackson.annotate.JsonProperty;

public class ErrorObject {
    @JsonProperty public int code;
    @JsonProperty public String msg;
  
    public ErrorObject(int code, String msg) {
      this.code = code;
      this.msg = msg;
    }
}
