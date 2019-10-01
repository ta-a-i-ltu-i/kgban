package kgban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KgbanController {
	 @Autowired
	  private KgbanService service;
	
	
	 //一覧表示するためのメソッド
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "kgban";  //表示するHTMLファイルの名前（拡張子不要）を指定
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


