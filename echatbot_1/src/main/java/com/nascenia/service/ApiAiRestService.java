package com.nascenia.service;

import com.nascenia.domain.model.Entities;
import com.nascenia.domain.model.EntitiesRestPayload;
import com.nascenia.domain.model.QueryResponse;
import com.nascenia.utility.CommonUtility;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/** Created by mozammal on 6/13/17. */
@Service
public class ApiAiRestService {

  private static final Logger log = LoggerFactory.getLogger(ApiAiRestService.class);

  @Autowired private CommonUtility commonUtility;

  public QueryResponse retrieveConversation(String query) throws URISyntaxException, IOException {

    final HttpClient httpClient = HttpClientBuilder.create().build();
    URI uri = buildUriBuilderApiAi(query);
    HttpGet httpGet = new HttpGet(uri);
    httpGet.setHeader("Authorization", "Bearer d5813c606f1543069cfe0b522c533d54");
    HttpResponse rawResponse = httpClient.execute(httpGet);
    StringBuffer response = commonUtility.retrieveRestApiResponseText(rawResponse);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    log.info("response from API.ai: " + response.toString());
    QueryResponse queryResponse = objectMapper.readValue(response.toString(), QueryResponse.class);
    return queryResponse;
  }

  public void createEntities(EntitiesRestPayload entitiesList)
      throws URISyntaxException, IOException {

    final HttpClient httpClient = HttpClientBuilder.create().build();
    URI uri = buildUriBuilderApiAiPostEntities();
    HttpPost httpPost = new HttpPost(uri);
    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer d5813c606f1543069cfe0b522c533d54");
    ObjectMapper objectMapper = new ObjectMapper();
    String reader = objectMapper.writeValueAsString(entitiesList);
    log.info("created entity: {} ", reader);
    StringEntity postingString = new StringEntity(reader);
    httpPost.setEntity(postingString);
    HttpResponse rawResponse = httpClient.execute(httpPost);
    StringBuffer response = commonUtility.retrieveRestApiResponseText(rawResponse);
    log.info("response on entities post " + response.toString());
  }

  private URI buildUriBuilderApiAiPostEntities() throws URISyntaxException {

    //https://api.api.ai/v1/entities?v=20150910
    return new URIBuilder()
        .setScheme("https")
        .setHost("api.api.ai")
        .setPath("/v1/entities")
        .addParameter("v", "20150910")
        /* .addParameter("lang", "en")
        .addParameter("sessionId", "d5813c606f1543069cfe0b522c533d54")*/
        .build();
  }

  private URI buildUriBuilderApiAi(String query) throws URISyntaxException {

    // https://api.api.ai/v1/query?v=20150910&query=Hi&lang=en&sessionId=d5813c606f1543069cfe0b522c533d54
    return new URIBuilder()
        .setScheme("https")
        .setHost("api.api.ai")
        .setPath("/v1/query")
        .addParameter("v", "20150910")
        .addParameter("query", query)
        .addParameter("lang", "en")
        .addParameter("sessionId", "d5813c606f1543069cfe0b522c533d54")
        .build();
  }
}
