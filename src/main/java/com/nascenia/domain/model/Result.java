package com.nascenia.domain.model;

/** Created by user on 6/5/2017. */
public class Result {

  private SpeechMessages fulfillment;

  private String action;

  private Parameters parameters;

  public Result() {}

  public Parameters getParameters() {
    return parameters;
  }

  public void setParameters(Parameters parameters) {
    this.parameters = parameters;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public SpeechMessages getFulfillment() {
    return fulfillment;
  }

  public void setFulfillment(SpeechMessages fulfillment) {
    this.fulfillment = fulfillment;
  }
}
