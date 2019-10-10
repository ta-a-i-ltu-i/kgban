
package kgban.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kgban.dto.KgbanDto;
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
	 * @param kgbanform
	 * @param mav
	 * @return 過去の投稿を格納したリスト
	 * @throws SQLException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView requestUserMessage(@ModelAttribute("form") KgbanForm kgbanform, ModelAndView mav) {

		// 表示画面と過去の投稿をセット
		mav.setViewName("kgban");
		ArrayList<KgbanDto> list = service.getUserMessages();
		mav.addObject("list", list);

		return mav;
	}

	/**
	 * 投稿をBDに登録.
	 * 
	 * @param kgbanForm 投稿されたnameとmessage
	 * @param result
	 * @param mav
	 * @return 正常:掲示板画面再描画 異常:掲示板画面にエラーメッセージを表示
	 * @throws SQLException
	 */

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView requestForm(@ModelAttribute("form") @Validated KgbanForm kgbanForm, BindingResult result,
			ModelAndView mav) {

		// エラーがなければ掲示板画面再描画を、エラーがあれば掲示板画面とエラーメッセージをreturn
		if (result.hasErrors()) {
			mav.setViewName("kgban");
			ArrayList<KgbanDto> list = service.getUserMessages();
			mav.addObject("list", list);

			return mav;
		}
		service.setUserMessage(kgbanForm);

		return new ModelAndView("redirect:/");

	}

}
