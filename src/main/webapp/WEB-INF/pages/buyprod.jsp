<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>结算</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/shop.css">	
		
<script type="text/javascript" src="${path}/js/jquery-3.3.1.min.js"></script>

<script src="${path}/js/shop.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function(){
		$("input[name='isneAddress']").click(function() {
			var checked=$(this).prop("checked");
			if(checked){
				$("#oldAddress").css("display","none");
				$("#newAddress").css("display","block");
			}else {
				$("#oldAddress").css("display","block");
				$("#newAddress").css("display","none");
			}
		});
		
	});
</script>
</head>
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
			<a href="<%=request.getContextPath()%>">首页</a>
		</li>
	
	</nav>
		<form id="payForm" action="goAlipay.do" method="post">	
		<h2>当前用户</h2><br/>
		<!-- session用户名称 -->
		用户名
		<h2>选择地址</h2><br/>
		<div id="oldAddress">
			<!-- 循环地址开始 -->
			      <c:forEach items="${receiverList}" var="rec">
				<input type="radio" name="address" value="${rec.id}"/>
				<!-- 用户地址-->${rec.receiverAddress}----<!-- 收货人名称-->${rec.receiverUsername}-----<!-- 联系人电话 -->${rec.receiverPhone}<br/>
				</c:forEach>
			<!-- 循环地址结束 -->
		</div>
		
	<table border="0" class="gouwutable" cellspacing="0" cellpadding="0">
		<tr class="prodtitle">
			<td class="prodcol1">订单号</td>
			<td class="prodcol2">订单金额</td>
			<td class="prodcol3">商品详情</td>
			<td class="prodcol4">创建时间</td>
			<td class="prodcol5">订单状态</td>
			<td class="prodcol6">商品描述</td>
		</tr>
		
		<tr class="prodRow">			
			<td class="prodcol1"><input type="hidden" name="WIDout_trade_no" value="${orders.orderId}"/>${orders.orderId}</td>
			<td class="prodcol2"><a href="javascript:">${orders.ordersMoney}</a> </td>
			<td class="prodcol3">
			<c:forEach items="${orders.goodsList}" var="goods">
			<img src="${goods.imgUrl}">
			<span class="xianjia" >商品名称:${goods.goodsName}  单价:${orders.ordersMoney}</span>
			
			<input type="hidden" name="WIDsubject" value="${goods.goodsName}"><!-- 商品名称和数量 -->
			<input type="hidden" name="WIDtotal_amount" value="${orders.ordersMoney}">
			</c:forEach>
			</td>
			<td class="prodcol4">${orders.orderTime}</td>
			<td class="prodcol5" style="border-right:solid 1px #666666;">
			<c:if test="${orders.staus==1}">
			 <span style="color:red">未付款</span>
			</c:if>
			
			</td>
			<td>
        			
        			<input id="WIDbody" name="WIDbody" value="" />	
        		</td>
		</tr>	
		
	</table>
	<input type="submit" style="width: 150px; height: 50px" value="付款"/>
	</form>
	
</body>
</html>