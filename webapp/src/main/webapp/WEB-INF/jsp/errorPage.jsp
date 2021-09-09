<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Error</title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <div class="page-container">
        <h1>${errorMsg}</h1>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>