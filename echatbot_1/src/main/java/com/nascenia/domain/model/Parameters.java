package com.nascenia.domain.model;

import java.util.List;

/** Created by mozammal on 6/6/17. */
public class Parameters {

  private List<String> braSize;

  private List<String> braColor;

  private String braBrand;

  private String pantyBrand;

  private String underWearBrand;

  private String underWearSize;

  private String condomBrand;

  private String hijabBrand;

  public Parameters() {}

  public String getHijabBrand() {
    return hijabBrand;
  }

  public void setHijabBrand(String hijabBrand) {
    this.hijabBrand = hijabBrand;
  }

  public String getCondomBrand() {
    return condomBrand;
  }

  public void setCondomBrand(String condomBrand) {
    this.condomBrand = condomBrand;
  }

  public String getUnderWearSize() {
    return underWearSize;
  }

  public void setUnderWearSize(String underWearSize) {
    this.underWearSize = underWearSize;
  }

  public String getUnderWearBrand() {
    return underWearBrand;
  }

  public void setUnderWearBrand(String underWearBrand) {
    this.underWearBrand = underWearBrand;
  }

  public String getPantyBrand() {
    return pantyBrand;
  }

  public void setPantyBrand(String pantyBrand) {
    this.pantyBrand = pantyBrand;
  }

  public String getBraBrand() {
    return braBrand;
  }

  public void setBraBrand(String braBrand) {
    this.braBrand = braBrand;
  }

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
