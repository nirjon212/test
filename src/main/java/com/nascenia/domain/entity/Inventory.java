package com.nascenia.domain.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Date;

/** Created by mozammal on 6/6/17. */
@Entity
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String productId;

  private Integer quantity;

  private Date updateDate;

  public Inventory() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
