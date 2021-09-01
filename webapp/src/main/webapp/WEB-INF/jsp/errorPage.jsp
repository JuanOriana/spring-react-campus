<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Error</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <link href="<c:url value = "../../resources/css/style.css" />" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap"
          rel="stylesheet">

</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="page-container">
    <h1>${errorMsg}</h1>
</div>
</body>
</html>