package com.nascenia.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 6/5/2017.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class MessagesWithType {

    public MessagesWithType() {
    }

    private int type;

    private String speech;

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
