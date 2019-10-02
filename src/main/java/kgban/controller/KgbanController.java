package kgban.controller;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kgban.dto.KgbanDto;
import kgban.form.KgbanForm;
import kgban.service.KgbanService;

import org.apache.commons.lang3.StringUtils;

@Controller
public class KgbanController {
	 @Autowired
	  private KgbanService service;
	
	
	 //一覧表示するためのメソッド
		/**
		 * 過去の投稿を表示
		 * @param mav
		 * @return
		 */
	  @RequestMapping(value = "/", method = RequestMethod.GET)
	  public ModelAndView getWebBoardPage(ModelAndView mav) {
	    mav.setViewName("kgban");
	    
	    ArrayList<KgbanDto> list = service.getMessage();
	    mav.addObject("list", list);
	    return mav;
	  }
    
  //登録するためのメソッド
  	@RequestMapping(value="/",  method = RequestMethod.POST)
  	public ModelAndView message(@ModelAttribute("form")  KgbanForm kf) {
  		
  		final int NAME_MAX = 12;
  		final int MESSAGE_MAX = 25;
  		
  		String name = kf.getName();
  		String message = kf.getMessage();
  		
  		name = name.trim();
  		message = message.trim();
  		
  		
  		if(!StringUtils.isEmpty(name) && name.length() <= NAME_MAX){
  			if(!StringUtils.isEmpty(message) && message.length() <= MESSAGE_MAX){
  				service.setmessage(kf); 				
  			}
  		}
  		
  		return new ModelAndView("redirect:/");
  		
  	}
  	
}


