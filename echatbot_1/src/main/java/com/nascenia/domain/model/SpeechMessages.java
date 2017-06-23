package com.nascenia.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/** Created by user on 6/5/2017. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeechMessages {

  private String speech;

  private List<MessagesWithType> messages;

  public SpeechMessages() {}

  public List<MessagesWithType> getMessages() {
    return messages;
  }

  public void setMessages(List<MessagesWithType> messages) {
    this.messages = messages;
  }

  public String getSpeech() {
    return speech;
  }

  public void setSpeech(String speech) {
    this.speech = speech;
  }
}
