package com.nascenia.domain.model;

import java.util.List;

/** Created by mozammal on 6/6/17. */
public class Parameters {

  private List<String> braSize;

  private List<String> braColor;

  public Parameters() {}

  public List<String> getBraSize() {
    return braSize;
  }

  public void setBraSize(List<String> braSize) {
    this.braSize = braSize;
  }

  public List<String> getBraColor() {
    return braColor;
  }

  public void setBraColor(List<String> braColor) {
    this.braColor = braColor;
  }
}
