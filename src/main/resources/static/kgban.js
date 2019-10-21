/**
 * 掲示板JS
 */

//削除ボタン押下時のダイアログ
function disp() {
	if (window.confirm('本当に削除しますか？')) {
		return true;
	} else {
		window.alert('キャンセルされました');
		return false;
	}
}

//削除失敗時のダイアログ
window.onload = function() {
	var del = document.getElementById('deleteMessage').value;
	var size = del.length;
	if (size > 1) {
		alert(del);
	}
}
