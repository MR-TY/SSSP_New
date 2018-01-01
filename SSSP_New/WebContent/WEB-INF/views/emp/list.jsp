<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".delete").click(function() {
			var label = $(this).next(":hidden").val();
			//彈出對話框是否要刪除信息，如果要刪除信息，則返回1，否則返回0
			var flag = confirm("确定要删除" + label + "的信息吗?");
			if (flag) {
				var url = $(this).attr("href");

				$("#_form").attr("action", url);
				$("#_method").val("DELETE");
				$("#_form").submit();
			}
			return false;
		});
	})
</script>
</head>
<body>
	<form action="" method="POST" id="_form">
		<input type="hidden" id="_method" name="_method" />
	</form>
	<c:if test="${page == null || page.numberOfElements == 0}">
		<h1>没有任何的记录</h1>
	</c:if>

	<c:if test="${page != null && page.numberOfElements > 0}">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>ID</th>
				<th>birth</th>
				<th>createTime</th>
				<th>department</th>
				<th>email</th>
				<th>lastName</th>


				<th>Delete</th>
				<th>Edit</th>
			</tr>
			<!-- 通过page.content： 获取集合中的对象，然后赋值给emp-->
			<c:forEach items="${page.content }" var="emp">

				<tr>
					<td>${emp.id}</td>
					<!-- pattern是用來寫時間顯示的格式 -->
					<td><fmt:formatDate value="${emp.birth }" pattern="yyyy-MM-dd" />
					</td>
					<td><fmt:formatDate value="${emp.createTime }"
							pattern="yyyy-MM-dd hh:mm:ss" /></td>
					<td>${emp.department.departmentName}</td>
					<td>${emp.email}</td>
					<td>${emp.lastName}</td>

					<td><a href="${pageContext.request.contextPath}/emp/${emp.id}">Edit</a>
					</td>
					<td><a href="${pageContext.request.contextPath}/emp/${emp.id}"
						class="delete">Delete</a> <input type="hidden"
						value="${emp.lastName }" /></td>
				</tr>
			</c:forEach>
			<!-- 通过此tr中的参数显示分页的信息 -->
			<tr>
				<td colspan="8">共 ${page.totalElements }条记录
					共${page.totalPages}页 当前 ${page.number+1}页 <a
					href="?pageNo=${page.number+1 -1}">上一页</a> <a
					href="?pageNo=${page.number+1 +1}">下一页</a>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>