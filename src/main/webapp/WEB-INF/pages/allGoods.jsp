<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<!-- 导入样式表-->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>

<title>商品信息</title>

<style type="text/css">
.tablediv{

width:800px;
margin:40px auto;
border-collapse:collapse;

}

.tablediv tbody td{
border:1px solid #7FFFD4;

}

.tablemenu{
width:800px;
margin:40px auto;
border-collapse:collapse;
}

.fixeddiv{
position: fixed;
bottom: 120px;
left:600px;
}

</style>

<script type="text/javascript">
$(function(){
	//点击搜索按钮
	$("button[class='seach1']").click(function(){
		var likename=$("#likeName").val() ;

		window.location.href="seacherGoodsByLikeName.do?likename="+likename;
		 
	});
	
	//点击删除按钮
	$("button[class='delete']").click(function(){
		var id=$(this).parents("tr").children().eq(1).html();
		var name = $(this).parents("tr").children().eq(2).html();
		if(confirm("是否要删除<"+name+">的信息")){
			$(this).parents("tr").remove();
			
			window.location.href="deleteGoodsById.do?id="+id;
		}
	});
	
	//点击批量删除按钮
	$("button[class='delCheckGoods']").click(function(){
		var r=confirm("是否确认删除？");    
		if(r==true){
		var ckall = document.getElementsByName("ckall");
		var ids="";
		var n=0;
		for(var i = 0; i < ckall.length; i++) {
			if(ckall[i].checked == true){
				var id=ckall[i].value;
				if(n==0){
					ids+=id;
				}else{
					ids+="-"+id;
				}
				n++;
			}
		} 
	
		window.location.href="deleteGoodsByIds.do?ids="+ids;
		}else{
			return false;
		}
	});
	

	
});

//全选
function setall() {
	var thisec = event.srcElement.checked;
	var ckall = document.getElementsByName("ckall");
	for(var i = 0; i < ckall.length; i++) {
		ckall[i].checked = thisec;
	}
}

function addShop(id){
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/addShop.do?goodsId="+id,
		success:function(data){
			   if(data=='1'){
				   alert("添加进购物车成功!");
			   }else if(data=='2'){
				   alert("你已经添加过该商品")
			   }
			}
	});
	
}

</script>

</head>
<body>
<h1>你好,${user.username}</h1><br>
<a href="getAllGoods.do">首页</a>
<a href="logGet.do">日志信息</a>
<a href="shopList.do">购物车</a>
<a href="toKillList.do">单个限时抢购</a>
<a href="toManyKillList.do">多个商品限时抢购</a>

<div>
<table class="tablemenu">
	<thead>

	
	<form action="getAllGoods.do" method="post">
	<tr style="border:0px;">
	<td>商品名称：<input name="goodsName" value="${seachGoodsVo.goodsName }">价格：<input type="text" name="minPrice" value="<c:if test="${seachGoodsVo.minPrice!=0 }">${seachGoodsVo.minPrice }</c:if> "/>
	-<input type="text" name="maxPrice" value="<c:if test="${seachGoodsVo.maxPrice!=0 }">${seachGoodsVo.maxPrice }</c:if> "/>
	<button type="submit">搜索</button></td>
	</tr>
		</form>
	<tr>
	<td style="border:0px;"><a href="toAddGoods.do"><button>添加商品</button></a>
	<button class="delCheckGoods">批量删除</button>
	</td></tr>
	</thead>
</table>
</div>


<table class="tablediv" id="table1">
	<tr>
	<td><label><input type="checkbox" name="" id="" value="" onclick="setall()" />全选</label></td>
	<td>id</td>
	<td>商品名</td>
	<td>商品价格</td>
	<td>商品库存</td>
	<td>商品图片</td>
	<td>操作</td>
	<td>购物</td>
	</tr>

	<c:forEach items="${pageInfo.list}" var="goodsVo">
	<tr>
	<td><input type="checkbox" name="ckall" value="${goodsVo.goods.id }" ></td>
	<td>${goodsVo.goods.id }</td>
	<td>${goodsVo.goods.goodsName }</td>
	<td>${goodsVo.salePrice }</td>
	<td>${goodsVo.avaliableCount }</td>
	<td><img style="width:80px;height:70px" src="${goodsVo.goods.imgUrl }"/></td>
	
	
	<td><button class="delete">删除</button>
	<a href="findGoodsById.do?id=${goodsVo.goods.id }"><button>修改</button></a>
	</td>
	<td><a href="javascript:addShop('${goodsVo.goods.id }')">添加购物车</a></td>
	</tr>
	</c:forEach>

</table>

					<div class="row text-center fixeddiv" style="padding-left: 50px;">
						<ul class="pagination">
							<li><a href="">&lt;&lt;</a></li>
							<c:forEach var="i" begin="1" end="${pageInfo.pages }">
								<li
									<c:if test="${pageInfo.pageNum==i }"> class="active"</c:if>>
									<a href="<%=request.getContextPath() %>/getAllGoods.do?pageNo=${i}&goodsName=${serchPrice.goodsName}&minPrice=${serchPrice.minPrice}&maxPrice=${serchPrice.maxPrice}">${i }<span class="sr-only">(current)</span></a>
								</li>
							</c:forEach>
							<li class="disabled"><a href="">&gt;&gt;</a></li>

						</ul>
					</div>
</body>
</html>