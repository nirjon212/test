package com.nascenia.domain.model;

import java.util.List;

/** Created by mozammal on 6/13/17. */
public class Entities {

  private String value;

  private List<String> synonyms;

  public Entities() {}

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<String> getSynonyms() {
    return synonyms;
  }

  public void setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
  }
}
