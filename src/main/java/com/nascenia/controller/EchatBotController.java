package com.nascenia.controller;

import com.nascenia.domain.model.ChatBotResponseMessage;
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
import org.springframework.web.util.HtmlUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class EchatBotController {

  private static final Logger log = LoggerFactory.getLogger(EchatBotController.class);

  @RequestMapping(value = "/eChatBot/conversations", method = RequestMethod.GET)
  public ChatBotResponseMessage sendChatMessageToDestination(@RequestParam("query") String query)
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
    String reply = queryResponse.getResult().getFulfillment().getSpeech();
    String action = queryResponse.getResult().getAction();

    List<String> braSize = queryResponse.getResult().getParameters().getBraSize();
    byte[] bytes = null;

    /*if (action != null && action.equalsIgnoreCase("user.bra.size.action")) {
      if (braSize != null && braSize.size() > 0) reply = "we dont have " + braSize.get(0);
      reply = "we dont have any bras at this moment";
      bytes = sendImage();
    }*/

    if (action != null && action.equalsIgnoreCase(("brabrand.action"))) {
      String braBrand = queryResponse.getResult().getParameters().getBraBrand();
      // Now do something with the brand
    } else if (action != null && action.equalsIgnoreCase("user.bra.size.action")) {
      if (braSize != null && braSize.size() > 0) bytes = sendImage();
    }
    return new ChatBotResponseMessage(reply, bytes);
  }

  @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
  public @ResponseBody byte[] sendImage() {
    try {
      InputStream is = this.getClass().getResourceAsStream("/testbra.jpeg");
      BufferedImage img = ImageIO.read(is);
      ByteArrayOutputStream bao = new ByteArrayOutputStream();
      // Write to output stream
      ImageIO.write(img, "jpg", bao);
      return bao.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
