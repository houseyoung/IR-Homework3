<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Cache</title>
</head>
<body>
<form action="${website}search" method="post">
  <input type="text" name="queryWord" value="">
  <button type="submit"><i class="fa fa-search"></i>搜索</button>
</form>
<p>
以下是文档<a href="${website}resources/Doc/${docName}">${docName}</a>的快照：
<p>
<hr>
<div style="text-indent:2em">
${cacheContent}
</div>
</body>
</html>