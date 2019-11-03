$(function() {
	//
	$(".middle_shang_1").click(function() {
		var res = $(".middle_shang_1");
		for(var i = 0; i < res.length; i++) {
			if($(res[i]).html() == $(this).html()) {
				$(this).css("border-bottom", "2px solid indianred");

				$(this).children().css("color", "darkred");
			} else {
				$(res[i]).css("border-bottom", "none");

				$(this).children().css("color", "black");
			}
		}

	});
	//删除按钮绑定事件
	$(document).on("click", "a[name = 'del']", function() {
		if(confirm("确认要删除吗？")) {
			$(this).parent().parent().remove();
		}
	});

	//按钮隐藏
	$("button").click(function() {
		$(this).toggle();
	});

});