package com.nascenia.domain.model;

/** Created by mozammal on 6/6/17. */
public class ChatBotResponseMessage {

  private String reply;

  private byte[] bytes;

  private String uri;

  public ChatBotResponseMessage() {}

  public ChatBotResponseMessage(String reply, byte[] bytes, String uri) {
    this.reply = reply;
    this.bytes = bytes;
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }
}
