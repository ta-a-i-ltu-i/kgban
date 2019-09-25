package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class KgbanController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "kgban";  //表示するHTMLファイルの名前（拡張子不要）を指定
    }       
}