package com.nascenia.controller;

import com.nascenia.domain.model.ChatBotResponseMessage;
import com.nascenia.domain.model.Product;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class EchatBotController {

  private static final Logger log = LoggerFactory.getLogger(EchatBotController.class);

  String MAGENTO_URI = "http://localhost/pub/media/catalog/product/";

  @RequestMapping(value = "/eChatBot/conversations", method = RequestMethod.GET)
  public ChatBotResponseMessage sendChatMessageToDestination(@RequestParam("query") String query)
      throws URISyntaxException, IOException {

    final HttpClient httpClient = HttpClientBuilder.create().build();
    URI uri = buildUriBuilderApiAi(query);
    HttpGet httpGet = new HttpGet(uri);
    httpGet.setHeader("Authorization", "Bearer d5813c606f1543069cfe0b522c533d54");
    HttpResponse rawResponse = httpClient.execute(httpGet);
    StringBuffer result = retrieveAylienApiResponse(rawResponse);
    String imageURI = "";
    /* uri = buildUriBuilderMagento("/index.php/rest/V1/integration/admin/token");
    HttpPost httpPost = new HttpPost(uri);
    httpPost.setEntity(new StringEntity("{\"username\":\"" + "mozammal" + "\",\"password\":\"" + "1234567891Tomal" + "\"}",
            ContentType.create("application/json")));*/

    log.info(result.toString());
    log.info(
        "_________________________________________________________________________________________________-");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    log.info("result corrupted: " + result.toString());
    QueryResponse queryResponse = objectMapper.readValue(result.toString(), QueryResponse.class);
    String reply = queryResponse.getResult().getFulfillment().getSpeech();
    String action = queryResponse.getResult().getAction();

    List<String> braSize = queryResponse.getResult().getParameters().getBraSize();
    String underWearSize = queryResponse.getResult().getParameters().getUnderWearSize();
    byte[] bytes = null;

    /*if (action != null && action.equalsIgnoreCase("user.bra.size.action")) {
      if (braSize != null && braSize.size() > 0) reply = "we dont have " + braSize.get(0);
      reply = "we dont have any bras at this moment";
      bytes = sendImage();
    }*/

    if (action != null && action.equalsIgnoreCase(("brabrand.action"))) {

      String braBrand = queryResponse.getResult().getParameters().getBraBrand();
      log.info("bra Brand: {}", braBrand);
      StringBuffer magentoTokenResponseBuilder = retrieveProductDetails(httpClient, braBrand);
      log.info("response from magento api: {}", magentoTokenResponseBuilder);
      Product product =
          objectMapper.readValue(magentoTokenResponseBuilder.toString(), Product.class);
      reply = generateProductExistsMessage(reply, braBrand, product);
      String imageName = product.getMedia_gallery_entries().get(0).getFile().replaceAll("\\\\", "");
      imageURI = MAGENTO_URI + imageName;

    } else if (action != null && action.equalsIgnoreCase("user.bra.size.action")) {
      if (braSize != null && braSize.size() > 0) {
        String braBrand = queryResponse.getResult().getParameters().getBraBrand();
        StringBuffer magentoTokenResponseBuilder = retrieveProductDetails(httpClient, braBrand);
        log.info("response from magento api: {}", magentoTokenResponseBuilder);
        Product product =
            objectMapper.readValue(magentoTokenResponseBuilder.toString(), Product.class);
        boolean isProductInStock =
            product.getExtension_attributes().getStock_item().isIs_in_stock();
        if (!isProductInStock)
          reply = reply + "<br /><b>its costs around " + product.getPrice() + "</b>";
      }
    } else if (action.equalsIgnoreCase("pantybrand.action")) {
      String pantyBrand = queryResponse.getResult().getParameters().getPantyBrand();
      log.info("bra Brand: {}", pantyBrand);
      StringBuffer magentoTokenResponseBuilder = retrieveProductDetails(httpClient, pantyBrand);
      log.info("response from magento api: {}", magentoTokenResponseBuilder);
      Product product =
          objectMapper.readValue(magentoTokenResponseBuilder.toString(), Product.class);
      if (product.getName() == null || product.getName().trim().equals("")) {
        reply = "Sorry, we dont have " + pantyBrand + " at this moment";
      }
      String imageName = product.getMedia_gallery_entries().get(0).getFile().replaceAll("\\\\", "");
      imageURI = MAGENTO_URI + imageName;

    } else if (action.equalsIgnoreCase("user.panty.size.action")) {

    } else if (action.equalsIgnoreCase("man.underwear.brand.action")) {
      String underWearBrand = queryResponse.getResult().getParameters().getUnderWearBrand();
      log.info("underwear Brand: {}", underWearBrand);
      StringBuffer magentoTokenResponseBuilder = retrieveProductDetails(httpClient, underWearBrand);
      log.info("response from magento api: {}", magentoTokenResponseBuilder);
      Product product =
          objectMapper.readValue(magentoTokenResponseBuilder.toString(), Product.class);
      reply = generateProductExistsMessage(reply, underWearBrand, product);
      String imageName = product.getMedia_gallery_entries().get(0).getFile().replaceAll("\\\\", "");
      imageURI = MAGENTO_URI + imageName;
    } else if (action.equalsIgnoreCase("buy.underwear.size.action")) {

      if (underWearSize != null && underWearSize.trim().equals("")) {
        String underWearBrand = queryResponse.getResult().getParameters().getUnderWearBrand();
        log.info("underwear brand {}", underWearBrand);
        StringBuffer magentoTokenResponseBuilder =
            retrieveProductDetails(httpClient, underWearBrand);
        log.info("response from magento api: {}", magentoTokenResponseBuilder);
        Product product =
            objectMapper.readValue(magentoTokenResponseBuilder.toString(), Product.class);
        boolean isProductInStock =
            product.getExtension_attributes().getStock_item().isIs_in_stock();
        if (!isProductInStock)
          reply = reply + "<br /><b>its costs around " + product.getPrice() + "</b>";
      }
    }
    log.info("image uri: {}", imageURI);
    return new ChatBotResponseMessage(reply, bytes, imageURI);
  }

  private String generateProductExistsMessage(
      String reply, String underWearBrand, Product product) {
    if (product.getName() == null || product.getName().trim().equals("")) {
      reply = "Sorry, we dont have " + underWearBrand + " at this moment";
    } else if (product != null) {
      boolean isProductInStock = product.getExtension_attributes().getStock_item().isIs_in_stock();
      if (!isProductInStock) reply = "<br /><b>sorry we dont have it in stock now.</b>";
    }
    return reply;
  }

  private StringBuffer retrieveProductDetails(HttpClient httpClient, String braBrand)
      throws URISyntaxException, IOException {
    HttpGet httpGet;
    URI buildUriBuilderMagento = buildUriBuilderMagento("/index.php/rest/V1/products/" + braBrand);
    httpGet = new HttpGet(buildUriBuilderMagento);
    httpGet.addHeader("Authorization", "Bearer rmgex9ydihok1r3xjahlin8liyxbb83m");
    httpGet.addHeader("accept", "application/json");
    HttpResponse magentoTokenResponse = httpClient.execute(httpGet);
    return retrieveAylienApiResponse(magentoTokenResponse);
  }

  @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
  public @ResponseBody byte[] sendImage(String image) {
    try {
      InputStream is = this.getClass().getResourceAsStream(image);
      BufferedImage img = ImageIO.read(is);
      ByteArrayOutputStream bao = new ByteArrayOutputStream();
      // Write to output stream
      ImageIO.write(img, "jpg", bao);
      return bao.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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

  private URI buildUriBuilderMagento(String uriPath) throws URISyntaxException {

    // https://api.api.ai/v1/query?v=20150910&query=Hi&lang=en&sessionId=d5813c606f1543069cfe0b522c533d54
    return new URIBuilder().setScheme("http").setHost("localhost").setPath(uriPath).build();
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
