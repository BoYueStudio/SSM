<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--导入JS文件-->

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>

<title>欢迎登陆</title>
<style type="text/css">
*{
margin: 0px;
}
body{
background: url("images/ali.jpg") no-repeat;
background-size: 100%;

}
.login{

border:1px solid white;
width:240px;
margin:230px  auto;
padding: 40px 40px;	
background-color: rgba(0,0,0,0.4);
font-family:微软雅黑;
font-weight: 500;
font-size: large;

}
</style>
<script type="text/javascript">


 function login(){
 			var username=$("#uname").val();
 			var password=$("#upwd").val();
 			
 			$.ajax({
 				type:"post",
 				url:"${pageContext.request.contextPath}/Login.do",
 				contentType:"application/json;charset=utf-8",
 				data:'{"loginName":"'+username+'","password":"'+password+'"}',
 				success:function(data){
 						if(data!=null &&data!=''){
 							window.location.href="${pageContext.request.contextPath}/getAllGoods.do";
 						}else{
 							$("#msg").html("用户名或密码错误 ");
 						}
 					}
 			});
 		}
 				
 			
 	
 		
 		

 	

</script>
<body>
<div class="login">

	用户：<input type="text" name="uname" id="uname"/><br><span style="color:red;font-size:small;" id="msg"></span><br>
	密码：<input type="password" name="upwd" id="upwd"><br><br>
	
	<button  type="button" onclick="login()" style="width:130px;margin-left: 58px;background-color: #63B8FF;border:0px;">登录</button>


</div>
</body>
</html>