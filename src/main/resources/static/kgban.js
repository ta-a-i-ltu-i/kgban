/**
 * 掲示板JS
 */
function disp() {
	var deleteForm = document.getElementById("deleteForm");
	if (window.confirm('本当に削除しますか？')) {
		location.href = "./";
		 deleteForm.submit();
	} else {
		window.alert('キャンセルされました');
	}
}