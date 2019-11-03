<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="java.util.*"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单页</title>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/Order.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/Order.js"></script>
</head>
<script type="text/javascript">
    // var intDiff = parseInt(60); //倒计时总秒数量

    //intDiff 是当前时间距离开抢时间剩余的秒数,id 哪个商品的id 
    function timer(intDiff) {
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
            $('#day_show' ).html(day + "天");
            $('#hour_show' ).html('<s id="h"></s>' + hour + '时');
            $('#minute_show' ).html('<s></s>' + minute + '分');
            $('#second_show' ).html('<s></s>' + second + '秒');
            intDiff--;
            if (intDiff <= 0) {
                clearInterval(ss);
                
            }
        }, 1000);
    }

  
</script>
<body>
<!-- 纵向导航栏 -->
<nav class="bs-docs-sidebar  affix " >
		<ul class="nav bs-docs-sidenav">
		<li>
			<a >欢迎${user.name}</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>">购物车 </a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>">订单列表</a>
		</li>
		<li>
			<a href="">首页</a>
		</li>
	
	</nav>
	
<div id="middle" style="margin-top:0px;">
	<div class="middle_shang">
		<div class="middle_shang_1" style="border-bottom: 2px solid indianred;">
			<a href="javascaript:" class=""><span>当前订单</span></a>
		</div>

		<div class="middle_shang_1"></div>
		<div style="width: 904px;">
		</div>
	</div>
<div class="time-item">
								<strong
									id="hour_show">0时</strong> <strong
									id="minute_show">00分</strong> <strong
									id="second_show">00秒</strong>
							</div>
	<div class="middle_zhong">
		<table cellspacing="" cellpadding="">
			<tr>
				<td width="535px">宝贝</td>
				<td width="120px">单价</td>
				<td width="80px">数量</td>
				<td width="157px">实付款</td>
				<td width="132px">交易操作</td>
			</tr>
		</table>
	</div>
	<!-- 循环订单            循环体内容         需要orders对象    goods对象    goods集合
	goods集合里面不包含这个goods对象     所以合并那是集合.size-1
	-->
		<div class="middle_xia">
			<table border="" cellspacing="" cellpadding="">
				<tr style="background-color: #F1F1F1;height: 45px;">
					<td colspan="2">
						<b>下单时间:${orders.orderTime}</b>
						<span>订单号:${orders.orderId}</span>
					</td>
					<td colspan="2" style="text-align: center;">
						
					</td>
					<td style="text-align: center;">
						
					</td>
					<td style="text-align: center;">
						<a class="" name="del" href="">
							<span>订单状态</span>
						</a>
					</td>
				</tr>
				<tr class="tab_1">
					<td><img width="80px" height="80px" src="图片路径" /></td>
					<td style="width: 450px;">
						<div class="tab_pic">
							<a href="">物品名称</a>
							<div class="tab_pic_1">
								
							</div>
						</div>
					</td>
					<td>
						<div class="tab_price">￥商品价格</div>
					</td>
					<td style="text-align: center;width: 80px;">商品数量</td>
					<td rowspan="2"><!-- 合并订单商品集合加一 -->
						<div class="tab_price"><span style="font-weight: bold;color: black;">￥订单价格</span></div>
						<div class="tab_price2">(含运费：￥0.00)</div>
					</td>
					<td rowspan="2" style="text-align: center;"><!-- 合并订单商品集合加一 -->
						<span>
						<c:if test="${orders.staus==1}">
						<button onclick="">付款</button>
						</c:if>
						<c:if test="${orders.staus==2}">
						<span style="color:green">已付款</span>
						</c:if>
						<c:if test="${orders.staus==-1}">
						<span style="color:grey">已失效</span>
						</c:if>
						
						
						
						</span><br />
						<!--<c:if test="${'待收货' eq orderDemo.order_info.status }">
						<button class="btn btn-info btn-sm">确认收获</button>
						</c:if>-->
					</td>
				</tr>
				<!-- 循环订单商品集合 -->
				<c:forEach items="${orders.goodsList}" var="goods">
					<tr>
						<td style="width: 50px;height: 100px;"><img width="80px" height="80px" src="${goods.imgUrl}" /></td>
						<td>
							<div class="tab_pic">
								<a href="">商品名称:${goods.goodsName}</a>
								<div class="tab_pic_1">
									<img src="img/creditcard.png" />
									<img src="img/7day.png" />
									<img src="img/promise.png" />
								</div>
							</div>
						</td>
						<td>
							<div class="tab_price">￥商品价格:${price}</div>
						</td>
						<td style="text-align: center;">商品数量:1</td>
					</tr>
					</c:forEach>
				<!-- 结束订单商品集合 -->
			</table>
		</div>
		<!-- 结束订单详情 -->
	</div>
	
</body>
<c:set value="<%=new Date()%>" var="now"></c:set>
<script type="text/javascript">

$(function () {
	 timer((${expiredate.time-now.time}) / 1000);
	
})


</script>
</html>