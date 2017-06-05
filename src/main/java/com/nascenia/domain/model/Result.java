package com.nascenia.domain.model;

/**
 * Created by user on 6/5/2017.
 */
public class Result {

    private SpeechMessages fulfillment;

    public Result() {
    }

    public SpeechMessages getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(SpeechMessages fulfillment) {
        this.fulfillment = fulfillment;
    }
}
