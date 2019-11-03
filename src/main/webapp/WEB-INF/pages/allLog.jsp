<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<title>日志信息</title>

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


</style>



</head>
<body>



<table class="tablediv" id="table1">
	<tr>
	<td>日志id</td>
	<td>操作者id</td>
	<td>操作表格</td>
	<td>方法及参数</td>
	<td>操作类型</td>
	<td>操作时间</td>
	<td>结果</td>
	</tr>

	<c:forEach items="${sysLogList}" var="log">
	<tr>
	<td>${log.id }</td>
	<td>${log.userId }</td>
	<td>${log.tableName}</td>
	<td>${log.param }</td>
	<td>${log.operateType }</td>
	<td>${log.operateTime }</td>
	<td>${log.result}</td>

	</tr>
	</c:forEach>

</table>
</body>
</html>