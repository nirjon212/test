package com.nascenia.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 6/5/2017.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class QueryResponse {

  private Result result;

    public QueryResponse() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
