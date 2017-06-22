package com.nascenia.service;

import com.nascenia.domain.model.Product;
import com.nascenia.utility.CommonUtility;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/** Created by mozammal on 6/13/17. */
@Service
public class MagentoRestService {

  @Autowired private CommonUtility commonUtility;

  public Product retrieveProductDetails(String brand) throws URISyntaxException, IOException {
    final HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet;
    URI buildUriBuilderMagento = buildUriBuilderMagento("/index.php/rest/V1/products/" + brand);
    httpGet = new HttpGet(buildUriBuilderMagento);
    httpGet.addHeader("Authorization", "Bearer rmgex9ydihok1r3xjahlin8liyxbb83m");
    httpGet.addHeader("accept", "application/json");
    HttpResponse magentoTokenResponse = httpClient.execute(httpGet);
    StringBuffer response = commonUtility.retrieveRestApiResponseText(magentoTokenResponse);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper.readValue(response.toString(), Product.class);
  }

  private URI buildUriBuilderMagento(String uriPath) throws URISyntaxException {

    // https://api.api.ai/v1/query?v=20150910&query=Hi&lang=en&sessionId=d5813c606f1543069cfe0b522c533d54
    return new URIBuilder().setScheme("http").setHost("localhost").setPath(uriPath).build();
  }
}
