<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js"></script>

<script type="text/javascript">
<!--当填写lastName的时候返回的值通过url映射到后端，通过后端进行解析，然后把值返回 -->
	$(function() {
		$("#lastName")
				.change(
						function() {
							var val = $(this).val();
							val = $.trim(val);
							$(this).val(val);
							//若修改的lastname和之前的lastName一致，则不发送ajax请求，直接发送可用
							var _oldLastName = $("#_oldLastName").val();
							_oldLastName = $.trim(_oldLastName);
							if (_oldLastName != null && _oldLastName != ""
									&& _oldLastName == val) {
								alert("lastName 可用!");
								return;
							}
							var url = "${pageContext.request.contextPath }/ajaxValidateLastName";
							var args = {
								"lastName" : val,
								"date" : new Date()
							};

							$.post(url, args, function(data) {
								if (data == "0") {
									alert("lastName 可用!");
								} else if (data == "1") {
									alert("lastName 不可用!");
									$("#lastName").val("");
								} else {
									alert("网络或程序出错. ");
								}
							});
						});
	})
</script>
</head>
<body>
	<!-- 
		設置：url=${pageContext.request.contextPath}/emp
		當employee.id!=null說明是在進行修改操作則，url需要帶參數，由前端@PathVariable獲取id值
		否則就是在進行保存操作，前端沒有值返回到後端
	 -->
	<c:set value="${pageContext.request.contextPath}/emp" var="url"></c:set>
	<c:if test="${employee.id!=null}">
		<c:set value="${pageContext.request.contextPath}/emp/${employee.id}" var="url"></c:set>
	</c:if>
	
	<!--
		提交表單，保存和修改都可以用此表單  
		
		modelAttribute：是獲取後端對象屬性的操作，前端屬性的名字必須和後端相對應，
		所以前端可以先不用傳值過來new Employee放進map中就能使前端獲取這個對象
	-->
	<form:form action="${url}"
		method="POST" modelAttribute="employee">
		
		
		<c:if test="${employee.id!=null }">
			<input type="hidden" id="_oldLastName" value="${employee.lastName }">
			<form:hidden path="id" />
			<input type="hidden" name="_method" value="PUT">
			<!-- 隐藏域和employee相关，还需要回显，就需要用form进行设置隐藏域 -->
		</c:if>
		
		LastName:<form:input path="lastName" id="lastName" />
		<br>
		Email:<form:input path="email" />
		<br>
		Birth:<form:input path="birth" />
		<br>
		Department:
		<form:select path="department.id" items="${departments}"
			itemLabel="departmentName" itemValue="id"></form:select>
		<br>
		<input type="submit" value="Submit">
		<br>
	</form:form>
</body>
</html>