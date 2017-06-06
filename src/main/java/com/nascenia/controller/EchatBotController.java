package com.nascenia.controller;

import com.nascenia.domain.model.QueryResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class EchatBotController {

  private static final Logger log = LoggerFactory.getLogger(EchatBotController.class);

  @RequestMapping(value = "/eChatBot/conversations", method = RequestMethod.GET)
  public String sendChatMessageToDestination(@RequestParam("query") String query)
      throws URISyntaxException, IOException {

    final HttpClient httpClient = HttpClientBuilder.create().build();
    URI uri = buildUriBuilder(query);
    HttpGet httpGet = new HttpGet(uri);
    httpGet.setHeader("Authorization", "Bearer d5813c606f1543069cfe0b522c533d54");
    HttpResponse rawResponse = httpClient.execute(httpGet);
    StringBuffer result = retrieveAylienApiResponse(rawResponse);
    log.info(result.toString());
    log.info(
        "_________________________________________________________________________________________________-");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    QueryResponse queryResponse = objectMapper.readValue(result.toString(), QueryResponse.class);
    String speech = queryResponse.getResult().getFulfillment().getSpeech();
    String action = queryResponse.getResult().getAction();

    List<String> braSize = queryResponse.getResult().getParameters().getBraSize();
    if (action != null && action.equalsIgnoreCase("user.bra.size.action")) {
      if (braSize != null && braSize.size() > 0) return "we dont have " + braSize.get(0);
      return "we dont have bras at this moment";
    }
    return speech;
  }

  private URI buildUriBuilder(String query) throws URISyntaxException {

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

  private StringBuffer retrieveAylienApiResponse(HttpResponse rawResponse) throws IOException {
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
