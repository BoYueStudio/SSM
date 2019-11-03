<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
pageContext.setAttribute("cxt",request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改商品</title>
<style type="text/css">
.tablediv{
border:1px solid #FFBBFF;
margin: 80px auto;
width:400px;
padding-left: 80px;


}

</style>
</head>
<body>

<div class="tablediv">
<h1>修改商品</h1>
<form action="<%=pageContext.getAttribute("cxt") %>/updateGoodsById.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="${goods.id }"><br>
商品名称:<input type="text" name="goodsName" value="${goods.goodsName }"/><br><br>
商品价格:<input type="text" name="goodsPrice" value="${goods.goodsPrice }"/><br><Br>
商品图片:<img src="${goods.goodsPic }" style="width:120px;height:100px"><input type="hidden" name="goodsPic" value="${goods.goodsPic }">
<input type="file" name="file" value="${goods.id }"/><br><br>
商品状态:<select name="state">
<option value="1" >正常</option>
<option value="0">待确定</option>
</select><br><br>
<button type="submit">修改</button>

</form>
</div>

</body>
</html>