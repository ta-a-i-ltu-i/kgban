package kgban;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
  		String name = kf.getName();
  		String message = kf.getMessage();

  		service.setmessage(kf);
  		
  		return new ModelAndView("redirect:/");
  		
  	}
  	
}


