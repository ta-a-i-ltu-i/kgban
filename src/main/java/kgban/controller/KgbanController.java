
package kgban.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kgban.config.PropertyConfig;
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
	@Autowired
	private PropertyConfig propertyConfig;

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
			// 例外があった場合500エラー画面に遷移
			e.printStackTrace();
			mav.setViewName("500");

			return mav;
		}

		return mav;
	}

	/**
	 * 投稿をDBに登録.
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

		} catch (SQLException e) {
			// 例外があった場合は500エラー画面へ遷移
			e.printStackTrace();
			mav.setViewName("500");

			return mav;
		}

		// エラーがなければ掲示板画面再描画を返す
		return new ModelAndView("redirect:/");
	}

	/**
	 * 削除処理.
	 * 
	 * @param id                 削除したい投稿のID
	 * @param mav                モデルおよび遷移先画面を設定するクラス
	 * @param redirectAttributes リダイレクト先にメッセージを送る
	 * @return 500エラー画面もしくは掲示板画面
	 */
	@Transactional
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id") int id, ModelAndView mav, RedirectAttributes redirectAttributes) {

		boolean existsId = false;
		boolean canDelete = false;

		try {
			// 送られてきたIDの投稿があるかチェック
			if (service.getCountId(id)) {
				existsId = true;
			} else {
				// 送られてきたIDの投稿が１件ではなかった場合500エラー画面に遷移
				mav.setViewName("500");
				return mav;
			}

			// 送られてきたIDの投稿が既に削除されていないかチェック
			if (service.getIsInvald(id)) {
				canDelete = true;
			} else {
				// 投稿が既に削除されていた場合エラーメッセージとともに掲示板再描画
				redirectAttributes.addFlashAttribute("resultMessage", propertyConfig.get("error.app.delete"));
				return new ModelAndView("redirect:/");
			}

			// 削除処理を実行
			if (existsId && canDelete) {
				// 送られてきたIDの投稿の無効フラグを0から1に変えるメソッド
				service.postDelete(id);
				mav.setViewName("MessageBoard");
				redirectAttributes.addFlashAttribute("resultMessage", propertyConfig.get("ok.app.delete"));
				return new ModelAndView("redirect:/");
			}
			mav.setViewName("500");
			return mav;

		} catch (SQLException e) {
			// 想定外エラーの場合500エラー画面に遷移
			e.printStackTrace();
			mav.setViewName("500");

			return mav;
		}

	}

}
