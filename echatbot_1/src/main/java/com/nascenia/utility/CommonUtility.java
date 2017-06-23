package com.nascenia.utility;

import com.nascenia.domain.model.Product;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Created by mozammal on 6/13/17. */
@Component
public class CommonUtility {

  private static String MAGENTO_URI = "http://localhost/pub/media/catalog/product/";

  public String findImageUri(Product product) {
    if (product == null
        || product.getMedia_gallery_entries() == null
        || product.getMedia_gallery_entries().size() == 0) return "";
    return MAGENTO_URI + product.getMedia_gallery_entries().get(0).getFile().replaceAll("\\\\", "");
  }

  public String generateProductExistsMessage(String reply, String underWearBrand, Product product) {
    if (product.getName() == null || product.getName().trim().equals("")) {
      reply = "Sorry, we dont have " + underWearBrand + " at this moment";
    } else if (product != null) {
      boolean isProductInStock = product.getExtension_attributes().getStock_item().isIs_in_stock();
      if (!isProductInStock) reply = "<br /><b>sorry we dont have it in stock now.</b>";
    }
    return reply;
  }

  public StringBuffer retrieveRestApiResponseText(HttpResponse rawResponse) throws IOException {

    BufferedReader rd =
        new BufferedReader(new InputStreamReader(rawResponse.getEntity().getContent()));
    StringBuffer result = new StringBuffer();
    String line = "";
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
    return result;
  }
}