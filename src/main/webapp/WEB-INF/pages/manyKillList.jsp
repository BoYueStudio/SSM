<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	pageContext.setAttribute("path", request.getContextPath());
%>
<html>

<script type="text/javascript" src="${path}/js/jquery-3.3.1.min.js"></script>

<head>

</head>
<c:set value="<%=new Date()%>" var="now"></c:set>
<script type="text/javascript">
    // var intDiff = parseInt(60); //倒计时总秒数量

    //intDiff 是当前时间距离开抢时间剩余的秒数,id 哪个商品的id 
    function timer(intDiff, id) {
        var ss = window.setInterval(function () {
            var day = 0,
                hour = 0,
                minute = 0,
                second = 0; //时间默认值
            if (intDiff > 0) {
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#day_show' + id).html(day + "天");
            $('#hour_show' + id).html('<s id="h"></s>' + hour + '时');
            $('#minute_show' + id).html('<s></s>' + minute + '分');
            $('#second_show' + id).html('<s></s>' + second + '秒');
            intDiff--;
            if (intDiff <= 0) {
                clearInterval(ss);
                
            }
        }, 1000);
    }

  
</script>
<body>
	<center>

		<h3>抢购列表</h3>
		<div id="container">
			<div>
				<c:forEach items="${goodsList}" var="vo">
					<ul>
						<li><img src="${vo.goods.imgUrl}"></li>
						<li>${vo.goods.goodsName}</li>
						<li>￥${vo.price}</li>
						<li>剩余:${vo.initCount}</li>
						<li>
							<div class="time-item">
								<span class="day_show" id="day_show${vo.goods.id}">0天</span> <strong
									id="hour_show${vo.goods.id}">0时</strong> <strong
									id="minute_show${vo.goods.id}">00分</strong> <strong
									id="second_show${vo.goods.id}">00秒</strong>
							</div>
						</li>
						<li><script>
                            $(function () {
                            	
                            	  <c:if test="${now.time > vo.endTime.time}">
                            	   $("#a${vo.goods.id}").html("对不起，该商品已过期")
                            	  </c:if>
                            	   
                            	   <c:if test="${now.time<= vo.endTime.time}">
                            	   timer((${vo.startTime.time-now.time}) / 1000, ${vo.goods.id})
                            	   $("#a${vo.goods.id}").html("立即抢购");
                            	   </c:if>
                            	
                            	
                              
                                
                               
                              
                               
                               
                            })

                        </script> <a href="javascript:killGoods('${vo.goods.id}');"
							id="a${vo.goods.id}"></a></li>
					</ul>
				</c:forEach>

			</div>

		</div>

	</center>
</body>
<script>
function killGoods(id){
	$.ajax({
		url:"manyKillGoods.do?id="+id,
		type:"post",
		success:function(data){
			if(data=='1'){
				alert("您还没有登录!")
				window.location.href="${path}/login/toLogin";
			}else if(data=='3'){
				alert("对不起，该商品已卖光!")
			}else if(data=='4'){
				alert("你已经抢购过该商品,请去订单页查看")
			}else if(data=='5'){
				alert("恭喜你，抢购成功!")
				//转发到订单确认页 
				window.location.href="killOrdersDetail.do?id="+id
			}else if(data=='2'){
				alert("对不起，还没到时间")
			}
			
			
		}
	});
	
}

</script>
<style>
#container {
	width: 1000px;
}

img {
	border-radius: 10px;
	width: 200px;
	height: 200px;
}

ul {
	list-style: none;
	float: left;
}

ul>li:nth-child(1) {
	
}

ul>li:nth-child(3) {
	color: red;
}

a {
	text-decoration: none;
}

input[type='submit'] {
	line-height: 35px;
	cursor: pointer;
}

input[type='button'] {
	cursor: pointer;
}

.num+ , .num- {
	width: 20px;
}

.num {
	width: 40px
}

#tbody tr:nth-child(odd) {
	background: rgb(204, 232, 207);
}
</style>
<style type="text/css">
.time-item strong {
	background: #C71C60;
	color: #fff;
	line-height: 15px;
	font-size: 15px;
	font-family: Arial;
	padding: 0 10px;
	margin-right: 2px;
	border-radius: 5px;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
}

.day_show {
	float: left;
	line-height: 25px;
	color: #c71c60;
	font-size: 15px;
	margin: 0 10px;
	font-family: Arial, Helvetica, sans-serif;
}

.item-title .unit {
	background: none;
	line-height: 15px;
	font-size: 15px;
	padding: 0 10px;
	float: left;
}
</style>
</html>
