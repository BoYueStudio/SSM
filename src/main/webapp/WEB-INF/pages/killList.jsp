<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="utf-8">
<title>jquery版的网页倒计时效果</title>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
<meta
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
	name="viewport" id="viewport">
<meta name="format-detection" content="telephone=no" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>

<style type="text/css">
.time-item strong {
	background: #C71C60;
	color: #fff;
	line-height: 49px;
	font-size: 36px;
	font-family: Arial;
	padding: 0 10px;
	margin-right: 10px;
	border-radius: 5px;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
}

.day_show {
	float: left;
	line-height: 49px;
	color: #c71c60;
	font-size: 32px;
	margin: 0 10px;
	font-family: Arial, Helvetica, sans-serif;
}

.item-title .unit {
	background: none;
	line-height: 49px;
	font-size: 24px;
	padding: 0 10px;
	float: left;
}
</style>


<script type="text/javascript">
function timer(idName, intDiff) {
		window.setInterval(function() {
			var day = 0, hour = 0, minute = 0, second = 0; //时间默认值     
			if (intDiff > 0) {
				day = Math.floor(intDiff / (60 * 60 * 24));
				hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				minute = Math.floor(intDiff / 60) - (day * 24 * 60)
						- (hour * 60);
				second = Math.floor(intDiff) - (day * 24 * 60 * 60)
						- (hour * 60 * 60) - (minute * 60);
			}
			if (minute <= 9)
				minute = '0' + minute;
			if (second <= 9)
				second = '0' + second;
			$(idName + ' .day_show').html(day + "天");
			$(idName + ' .hour_show').html('<s id="h"></s>' + hour + '时');
			$(idName + ' .minute_show').html('<s></s>' + minute + '分');
			$(idName + ' .second_show').html('<s></s>' + second + '秒');
			intDiff--;
		}, 1000);
	}

	$(function() {
		//开始抢购时间-系统当前时间的秒数 就是senconds这个变量的值
		timer('#interval', ${killGoods.beginTime});
		
		
	});
	
	
	function miaosha(id1,id2,money){
		//alert("开抢"+id1+id2);
		if(false){
			alert("时间还没到");
		}else{
			
	
		$.ajax({
			type:"post",
			url:"killGoods.do?id1="+id1+"&id2="+id2,
			success:function(data){
				   if(data=='1'){
					   
					   alert("秒杀成功!");
					   $("#num").html=$("#num").html-1;
					   
					   window.location.href="goPay.do?id1="+id1+"&id2="+id2+"&money="+money;
				   }else if(data=='2'){
					   alert("下次再来！")
				   }else if(data=='3'){
					   alert("你已抢过,请把机会留给别人！")
				   }else{
					   alert("false");
				   }
				}
		});	
		}
		
		
		
	}
</script>
</head>

<body>

	<div>
		<p>
			<img src="${killGoods.goods.imgUrl}" style="height:100px;width:100px">
		</p>
		<p>
			库存:<span id="num">${num}</span>
		</p>
		<p>
			价格:<span>${killGoods.goodsPrice.salePrice}</span>
		</p>

	</div>


	<div id="interval" class="time-item">
		<span class="day_show">0天</span> <strong class="hour_show">0时</strong>
		<strong class="minute_show">0分</strong> <strong class="second_show">0秒</strong>
	</div>
	<button onclick="miaosha('${killGoods.goods.id}','${user.id }','${killGoods.goodsPrice.salePrice}')" >开始抢购</button>



</body>

</html>