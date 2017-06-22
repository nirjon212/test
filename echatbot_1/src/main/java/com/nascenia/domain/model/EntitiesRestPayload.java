package com.nascenia.domain.model;

import java.util.List;

/** Created by mozammal on 6/13/17. */
public class EntitiesRestPayload {
  private String name;

  private List<Entities> entries;

  public EntitiesRestPayload() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Entities> getEntries() {
    return entries;
  }

  public void setEntries(List<Entities> entries) {
    this.entries = entries;
  }
}
