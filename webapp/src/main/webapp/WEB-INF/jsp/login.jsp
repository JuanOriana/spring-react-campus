<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: matia
  Date: 9/9/2021
  Time: 12:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <div>
        <label>
            <input name="username" placeholder="Username"/>
        </label>
    </div>
    <div>
        <label>
            <input name="password" placeholder="Password" type="password"/>
        </label>
    </div>
    <div>
        <label>
            <input name="rememberMe" type="checkbox"/>
        </label>
    </div>
    <div>
        <input name="submit" type="submit"/>
    </div>
</form>
</body>
</html>
