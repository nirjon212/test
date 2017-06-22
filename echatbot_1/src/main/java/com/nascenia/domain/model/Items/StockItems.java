package com.nascenia.domain.model.Items;

/** Created by mozammal on 6/12/17. */
public class StockItems {

  private String item_id;

  private String stock_id;

  private boolean is_in_stock;

  private boolean is_qty_decimal;

  private boolean show_default_notification_message;

  private int min_qty;

  private boolean use_config_enable_qty_inc;

  private int use_config_min_sale_qty;

  private int max_sale_qty;

  private boolean use_config_backorders;

  private boolean use_config_notify_stock_qty;

  public StockItems() {}

  public String getItem_id() {
    return item_id;
  }

  public void setItem_id(String item_id) {
    this.item_id = item_id;
  }

  public String getStock_id() {
    return stock_id;
  }

  public void setStock_id(String stock_id) {
    this.stock_id = stock_id;
  }

  public boolean isIs_in_stock() {
    return is_in_stock;
  }

  public void setIs_in_stock(boolean is_in_stock) {
    this.is_in_stock = is_in_stock;
  }

  public boolean isIs_qty_decimal() {
    return is_qty_decimal;
  }

  public void setIs_qty_decimal(boolean is_qty_decimal) {
    this.is_qty_decimal = is_qty_decimal;
  }

  public boolean isShow_default_notification_message() {
    return show_default_notification_message;
  }

  public void setShow_default_notification_message(boolean show_default_notification_message) {
    this.show_default_notification_message = show_default_notification_message;
  }

  public int getMin_qty() {
    return min_qty;
  }

  public void setMin_qty(int min_qty) {
    this.min_qty = min_qty;
  }

  public boolean isUse_config_enable_qty_inc() {
    return use_config_enable_qty_inc;
  }

  public void setUse_config_enable_qty_inc(boolean use_config_enable_qty_inc) {
    this.use_config_enable_qty_inc = use_config_enable_qty_inc;
  }

  public int getUse_config_min_sale_qty() {
    return use_config_min_sale_qty;
  }

  public void setUse_config_min_sale_qty(int use_config_min_sale_qty) {
    this.use_config_min_sale_qty = use_config_min_sale_qty;
  }

  public int getMax_sale_qty() {
    return max_sale_qty;
  }

  public void setMax_sale_qty(int max_sale_qty) {
    this.max_sale_qty = max_sale_qty;
  }

  public boolean isUse_config_backorders() {
    return use_config_backorders;
  }

  public void setUse_config_backorders(boolean use_config_backorders) {
    this.use_config_backorders = use_config_backorders;
  }

  public boolean isUse_config_notify_stock_qty() {
    return use_config_notify_stock_qty;
  }

  public void setUse_config_notify_stock_qty(boolean use_config_notify_stock_qty) {
    this.use_config_notify_stock_qty = use_config_notify_stock_qty;
  }
}
