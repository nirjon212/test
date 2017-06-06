package com.nascenia.domain.model;

/** Created by mozammal on 6/6/17. */
public class ChatBotResponseMessage {

  private String reply;

  private byte[] bytes;

  public ChatBotResponseMessage() {}

    public ChatBotResponseMessage(String reply, byte[] bytes) {
        this.reply = reply;
        this.bytes = bytes;
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
