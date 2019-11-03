<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/buyprodend.css"/>
</head>
<body>
<!-- 纵向导航栏 -->
<nav class="bs-docs-sidebar  affix " >
		<ul class="nav bs-docs-sidenav">
		<li>
			<a >欢迎${user.name}</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/shop/carShop.action">购物车 </a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/order/orderAll.action">订单列表</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/shop/index.action">首页</a>
		</li>
	
	</nav>
	<div class="buyprodbigdiv">
		<div class="topdiv">
			<span class="topspan">您已成功付款</span>
		</div>
		<div class="centerdiv">
			<ul>
				<li>收货地址：收货地址---- 收货人---- 收货人电话</li>
				<li>实付款：<span class="payprice">${price}</span></li>
				<li>预计08月19日送达</li>
				<li class="findli">您可以<a href="<%=request.getContextPath()%>/order/orderAll.action">查看已买到的宝贝交易详情</a></li>
				<li class="findli"><a href="<%=request.getContextPath()%>/shop/index.action">">返回首页</a></li>
			</ul>
		</div>
		<div class="bottomdiv">
			<span class="gantanspan">!</span><span class="aqtx">安全提醒</span>：下单后，<span class="redtx">用QQ给您发送链接办理退款的都是骗子！</span>天猫不存在系统升级，订单异常等问题，谨防假冒客服电话诈骗！
		</div>
	</div>

</body>
</html>