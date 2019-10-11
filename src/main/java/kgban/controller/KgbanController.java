
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
	 * @param kgbanform 何も格納されていないForm
	 * @param mav       モデルおよび遷移先画面を設定するクラス
	 * @return 過去の投稿を格納したリスト
	 * @throws SQLException データベースアクセスエラー
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView requestUserMessage(@ModelAttribute("form") KgbanForm kgbanForm, ModelAndView mav) {

		try {
			// 表示画面と過去の投稿をセット
			mav.setViewName("kgban");
			ArrayList<KgbanDto> list = service.getUserMessages();
			mav.addObject("list", list);

		} catch (SQLException e) {
			e.printStackTrace();
			mav.setViewName("500");

			return mav;
		}

		return mav;
	}

	/**
	 * 投稿をBDに登録.
	 * 
	 * @param kgbanForm 投稿されたnameとmessage
	 * @param result    エラーメッセージ
	 * @param mav       モデルおよび遷移先画面を設定するクラス
	 * @return 正常:掲示板画面再描画 異常:掲示板画面にエラーメッセージを表示
	 * @throws SQLException データベースアクセスエラー
	 */

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView requestForm(@ModelAttribute("form") @Validated KgbanForm kgbanForm, BindingResult bindingResult,
			ModelAndView mav) {

		// 例外処理
		try {

			// エラーがあれば掲示板画面とエラーメッセージを返す
			if (bindingResult.hasErrors()) {
				mav.setViewName("kgban");
				ArrayList<KgbanDto> list = service.getUserMessages();
				mav.addObject("list", list);

				return mav;
			}

			// KgbanDtoに登録する内容を格納するメソッド呼び出し
			service.setUserMessage(kgbanForm);

			// 例外があった場合は500エラー画面へ遷移
		} catch (SQLException e) {
			e.printStackTrace();
			mav.setViewName("500");

			return mav;
		}

		// エラーがなければ掲示板画面再描画を返す
		return new ModelAndView("redirect:/");
	}

}
