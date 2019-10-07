
package kgban.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kgban.dto.KgbanGetDto;
import kgban.form.KgbanForm;
import kgban.service.KgbanService;

/**
 * 掲示板コントローラ.
 */
@Controller
public class KgbanController {
	@Autowired
	private KgbanService service;

	/**
	 * 過去の投稿を表示.
	 * 
	 * @param mav
	 * @return 過去の投稿を格納したリスト
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView form(@ModelAttribute("form")KgbanForm kgbanform,ModelAndView mav) throws SQLException {
		mav.setViewName("kgban");

		ArrayList<KgbanGetDto> list = service.getMessages();
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 投稿をBDに登録.
	 * 
	 * @param kf 投稿されたnameとmessage
	 * @return 掲示板画面再描画
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView message(@ModelAttribute("form") @Validated KgbanForm kgbanForm, BindingResult result)
			throws SQLException {


		String name = kgbanForm.getName();
		String message = kgbanForm.getMessage();

		name = name.trim();
		message = message.trim();


		if (result.hasErrors()) {
			return new ModelAndView("kgban");
		}
		service.setMessage(kgbanForm);
		return new ModelAndView("redirect:/");

	}

}

