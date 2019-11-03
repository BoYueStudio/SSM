<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>购物车</title>

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
	//点击删除按钮
	$("button[class='delete']").click(function(){
		
		var id=$(this).parents("tr").children().eq(1).html();
		var name = $(this).parents("tr").children().eq(2).html();
		if(confirm("是否要删除<"+name+">的信息")){
			$(this).parents("tr").remove();
			$.ajax({
				url:"${pageContext.request.contextPath}/shopDel.do?goodsId="+id,
				type:"post",
				success:function(data){
					if(data==1){
						alert("删除成功！");
					}else{
						alert("删除失败！");
					}
				
				}
			});
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

function setnum(a) {
	var pedele = event.srcElement.parentElement; //父元素td
	var id=pedele.parentElement.cells[1].innerHTML; //父元素  tr
	var ped = pedele.children[1]; //数量的文本框对象
	if(a == '-') {
		if(ped.value == 1){
			alert("不能再减少了")
		}else{
		    $.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/shopNum.do?goodsId="+id+"&str=-",
				success:function(data){
					   if(data=='1'){
						   alert("修改商品成功!");
					   }else{
						   alert("修改商品失败！");
					   }
					}
			});
			
		}

		ped.value = (parseInt(ped.value) == 1) ? 1 : parseInt(ped.value) - 1;
	} else if(a == "+") {
		    $.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/shopNum.do?goodsId="+id+"&str=%2B",//转义+
				success:function(data){
					   if(data=='1'){
						   alert("修改商品成功!");
					   }else{
						   alert("修改商品失败！")
					   }
					}
			});
			
		ped.value = parseInt(ped.value) + 1;
	}
	//////////////////////
	var partr = pedele.parentElement; //父元素  tr
	var numv = partr.cells[4]; //获取单价td
	var numje = partr.cells[6]; //获取金额td
	numje.innerHTML = parseInt(ped.value) * parseInt(numv.innerHTML);

	sumrowje(); //计算总的金额
	
}
//计算总的金额
function sumrowje() {
	var tabrows = document.getElementById("table1").rows;
	var sumje = 0;
	for(var i = 1; i < tabrows.length; i++) {
		var je = tabrows[i].cells[6].innerHTML;
		sumje += parseInt(je);
	}
	document.getElementById("summoney").innerHTML = "￥:" + sumje;
}



</script>


</head>

<body onload='sumrowje()'>

<h1>你好,${user.username}</h1><br>
<a href="getAllGoods.do">首页</a>
<a href="logGet.do">日志信息</a>
<a href="shopList.do">购物车</a>

<table class="tablediv" id="table1">
	<tr>
	<td><label><input type="checkbox" name="" id="" value="" onclick="setall()" />全选</label></td>
	<td>id</td>
	<td>商品名</td>
	<td>商品图片</td>
	<td>商品单价</td>
	<td>商品数量</td>
	<td>商品金额</td>
	<td>操作</td>

	</tr>

	<c:forEach items="${goodsList}" var="goodsVo">
	<tr>
	<td><input type="checkbox" name="ckall" value="${goodsVo.goods.id }" ></td>
	<td>${goodsVo.goods.id }</td>
	<td>${goodsVo.goods.goodsName }</td>
	<td><img style="width:80px;height:70px" src="${goodsVo.goods.imgUrl }"/></td>
	<td>${goodsVo.salePrice }</td>
	<td>
	<input type="button" id="" value="-" onclick="setnum('-')" style="width: 16px;padding: 0px;" />
	<input type="text" id="" name="num" value="${goodsVo.avaliableCount }" style="width: 40px;" disabled="disabled" />
	<input type="button" id="" value="+" onclick="setnum('+')" style="width: 16px;padding: 0px;" />
	</td>
	
	<td>${goodsVo.salePrice * goodsVo.avaliableCount}</td>
	
	
	<td><button class="delete">删除</button>
	
	</td>
	
	</tr>
	</c:forEach>

</table>
<div id="summoney" style="text-align: right;width: 80%;">
			￥:
</div>
</body>
</html>