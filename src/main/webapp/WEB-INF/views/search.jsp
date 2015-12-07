<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Search</title>
</head>
<form action="${website}search" method="post">
  <input type="text" name="queryWord" value="${queryWord}">
  <button type="submit"><i class="fa fa-search"></i>搜索</button>
</form>
<p>
Houseyoung为您找到相关结果${num}个，耗时${time}毫秒
<p>
<c:forEach var="doc" items="${docList}">
  <a href="${website}resources/Doc/${doc.docName}">${doc.docName}</a>
  <br>
  ${doc.searchAbstract} - <a href="${website}cache?docName=${doc.docName}">文档快照</a>
  <p>
</c:forEach>
</body>
</html>