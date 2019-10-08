
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

import kgban.dto.KgbanGetDto;
import kgban.form.KgbanForm;
import kgban.service.KgbanService;

/**
 * 掲示板コントローラ.
 */
@Controller
public class KgbanController {
	// ----------------------------------------------------------------------
	// インスタンス変数
	// ----------------------------------------------------------------------
	@Autowired
	private KgbanService service;

	/**
	 * 過去の投稿を表示.
	 * 
	 * @param mav
	 * @return 過去の投稿を格納したリスト
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView requestForm(@ModelAttribute("form") KgbanForm kgbanform, ModelAndView mav) throws SQLException {
		
		//表示画面と過去の投稿をセット
		mav.setViewName("kgban");
		ArrayList<KgbanGetDto> list = service.registerContents();
		mav.addObject("list", list);

		return mav;
	}

	/**
	 * 投稿をBDに登録.
	 * 
	 * @param kf 投稿されたnameとmessage
	 * @return 正常:掲示板画面再描画 異常:掲示板画面にエラーメッセージを表示
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView requestRegister(@ModelAttribute("form") @Validated KgbanForm kgbanForm, BindingResult result,
			ModelAndView mav) throws SQLException {
		
		//エラーがなければ掲示板画面再描画を、エラーがあれば掲示板画面とエラーメッセージをreturn
		if (result.hasErrors()) {
			mav.setViewName("kgban");
			ArrayList<KgbanGetDto> list = service.registerContents();
			mav.addObject("list", list);

			return mav;
		}
		service.loadContent(kgbanForm);

		return new ModelAndView("redirect:/");

	}

}
