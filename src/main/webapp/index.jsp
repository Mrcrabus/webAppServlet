<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<p/>
<form action="/webAppServlet_war_exploded/books" method="post">
    <input type="text" name="title">
    <input type="text" name="author">
    <input type="text" name="year">
    <button type="submit">click</button>
</form>

<a href="hello-servlet">Hello Servlet</a>
</body>
</html>