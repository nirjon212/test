package com.nascenia.controller;

import com.nascenia.domain.model.*;
import com.nascenia.service.ApiAiRestService;
import com.nascenia.service.MagentoRestService;
import com.nascenia.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EchatBotController {

  private static final Logger log = Logge

Mapowipqowipoqpiwoipoqwipqiwp

asasasqpwoqw[ 



qw\]q[wq]rFactory.getLogger(EchatBotController.class);

  private static final String PRODUCT_COST_MESSAGE = "<br /><b>its costs around ";

  private static final String BRABRAND_ACTION = "brabrand.action";

  private static final String USER_BRA_SIZE_ACTION = "user.bra.size.action";

  private static final String PANTYBRAND_ACTION = "pantybrand.action";

  private static final String MAN_UNDERWEAR_BRAND_ACTION = "man.underwear.brand.action";

  private static final String BUY_UNDERWEAR_SIZE_ACTION = "buy.underwear.size.action";

  private static final String MAN_CONDOM_BRAND_ACTION = "man.condom.brand.action";

  private static final String HIJABBRAND_ACTION = "hijabbrand.action";

  @Autowired private ApiAiRestService apiAiRestService;

  @Autowired private MagentoRestService magentoRestService;

  @Autowired private CommonUtility commonUtility;

  @RequestMapping(value = "/eChatBot/conversations", method = RequestMethod.GET)
  public ChatBotResponseMessage sendChatMessageToDestination(@RequestParam("query") String query)
      throws URISyntaxException, IOException {

    try {

      QueryResponse queryResponse = apiAiRestService.retrieveConversation(query);
      String reply = queryResponse.getResult().getFulfillment().getSpeech();
      String action = queryResponse.getResult().getAction();

      List<String> braSize = queryResponse.getResult().getParameters().getBraSize();
      String underWearSize = queryResponse.getResult().getParameters().getUnderWearSize();
      byte[] bytes = null;
      String imageURI = "";

      switch (action) {
        case BRABRAND_ACTION:
          String braBrand = queryResponse.getResult().getParameters().getBraBrand();
          log.info("bra Brand: {}", braBrand);
          Product product = magentoRestService.retrieveProductDetails(braBrand);
          reply = commonUtility.generateProductExistsMessage(reply, braBrand, product);
          imageURI = commonUtility.findImageUri(product);
          break;
        case USER_BRA_SIZE_ACTION:
          if (braSize != null && braSize.size() > 0) {
            braBrand = queryResponse.getResult().getParameters().getBraBrand();
            product = magentoRestService.retrieveProductDetails(braBrand);
            boolean isProductInStock =
                product.getExtension_attributes().getStock_item().isIs_in_stock();
            if (isProductInStock)
              reply = reply + PRODUCT_COST_MESSAGE + product.getPrice() + "</b>";
            else reply = "Sorry, we dont have " + braBrand + " at this moment";
          }
          break;
        case PANTYBRAND_ACTION:
          String pantyBrand = queryResponse.getResult().getParameters().getPantyBrand();
          log.info("panty Brand: {}", pantyBrand);
          product = magentoRestService.retrieveProductDetails(pantyBrand);
          if (product.getName() == null || product.getName().trim().equals("")) {
            reply = "Sorry, we dont have " + pantyBrand + " at this moment";
          }
          imageURI = commonUtility.findImageUri(product);
          break;
        case MAN_UNDERWEAR_BRAND_ACTION:
          String underWearBrand = queryResponse.getResult().getParameters().getUnderWearBrand();
          log.info("underwear Brand: {}", underWearBrand);
          product = magentoRestService.retrieveProductDetails(underWearBrand);
          reply = commonUtility.generateProductExistsMessage(reply, underWearBrand, product);
          imageURI = commonUtility.findImageUri(product);
          break;
        case BUY_UNDERWEAR_SIZE_ACTION:
          if (underWearSize != null && underWearSize.trim().equals("")) {
            underWearBrand = queryResponse.getResult().getParameters().getUnderWearBrand();
            log.info("underwear brand {}", underWearBrand);
            product = magentoRestService.retrieveProductDetails(underWearBrand);
            boolean isProductInStock =
                product.getExtension_attributes().getStock_item().isIs_in_stock();
            if (!isProductInStock)
              reply = reply + PRODUCT_COST_MESSAGE + product.getPrice() + "</b>";
          }
          break;
        case MAN_CONDOM_BRAND_ACTION:
          String condomBrand = queryResponse.getResult().getParameters().getCondomBrand();
          log.info("condom Brand: {}", condomBrand);
          product = magentoRestService.retrieveProductDetails(condomBrand);
          reply = commonUtility.generateProductExistsMessage(reply, condomBrand, product);
          imageURI = commonUtility.findImageUri(product);
          break;
        case HIJABBRAND_ACTION:
          String hijabBrand = queryResponse.getResult().getParameters().getHijabBrand();
          log.info("hijab Brand: {}", hijabBrand);
          product = magentoRestService.retrieveProductDetails(hijabBrand);
          reply = commonUtility.generateProductExistsMessage(reply, hijabBrand, product);
          imageURI = commonUtility.findImageUri(product);
          break;
      }
      log.info("image uri: {}", imageURI);
      return new ChatBotResponseMessage(reply, bytes, imageURI);
    } catch (Exception exception) {
      log.info(exception.toString());
      return new ChatBotResponseMessage(
          "I have suffered from a mini stroke now. please say me hello or hi.", null, "");
    }
  }

  public void createEntity() throws URISyntaxException, IOException {
    Entities entities = new Entities();
    entities.setValue("coffee maker");
    String[] synonyms = {"coffee machine", "coffee", "cofe maker"};
    entities.setSynonyms(Arrays.asList(synonyms));
    EntitiesRestPayload entitiesRestPayload = new EntitiesRestPayload();
    entitiesRestPayload.setName("applicance");
    List<Entities> entitiesList = new ArrayList<>();
    entitiesList.add(entities);
    entitiesRestPayload.setEntries(entitiesList);
    apiAiRestService.createEntities(entitiesRestPayload);
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
}
