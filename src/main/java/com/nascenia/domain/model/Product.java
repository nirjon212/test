package com.nascenia.domain.model;

import java.util.Date;
import java.util.List;

/** Created by mozammal on 6/9/17. */
public class Product {

  private int id;

  private String name;

  private double price;

  private String sku;

  private int status;

  /*
    private Date created_at;

    private Date updated_at;
  */

  private ExtensionAttributes extension_attributes;

  private List<MediaGalleryEntries> media_gallery_entries;

  public Product() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ExtensionAttributes getExtension_attributes() {
    return extension_attributes;
  }

  public void setExtension_attributes(ExtensionAttributes extension_attributes) {
    this.extension_attributes = extension_attributes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public List<MediaGalleryEntries> getMedia_gallery_entries() {
    return media_gallery_entries;
  }

  public void setMedia_gallery_entries(List<MediaGalleryEntries> media_gallery_entries) {
    this.media_gallery_entries = media_gallery_entries;
  }
}
