package com.nascenia.web;

import com.nascenia.domain.entity.Inventory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** Created by mozammal on 6/6/17. */
@Controller
public class EChatWeb {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String eChatBotMainPage(Model model) {
    return "ai";
  }
}
