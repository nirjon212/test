package com.nascenia.domain.model;

import com.nascenia.domain.model.Items.StockItems;

/** Created by mozammal on 6/12/17. */
public class ExtensionAttributes {

  private StockItems stock_item;

  public ExtensionAttributes() {}

  public StockItems getStock_item() {
    return stock_item;
  }

  public void setStock_item(StockItems stock_item) {
    this.stock_item = stock_item;
  }
}
