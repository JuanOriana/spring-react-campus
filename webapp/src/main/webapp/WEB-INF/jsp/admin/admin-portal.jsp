<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Admin</title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="isAdmin" value="${true}"/>
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <div class="page-container">
        <h1>Admin</h1>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
