/**
 * 掲示板JS
 */

// 削除ボタン押下時のダイアログ
function deleteMessage() {
	if (window.confirm('本当に削除しますか？')) {
		return true;
	} else {
		window.alert('キャンセルされました');
		return false;
	}
}

// 削除処理終了時のメッセージ表示のダイアログ
window.onload = function() {
	var result = document.getElementById('deleteMessage').value;
	var size = result.length;
	if (size > 0) {
		alert(result);
	}
}
