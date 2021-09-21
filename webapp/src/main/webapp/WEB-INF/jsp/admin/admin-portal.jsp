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
    <div class="page-container" >
        <h1 class="admin-title">Centro de administracion de Campus</h1>
        <div style="display: flex">
            <a href="<c:url value="/admin/user/new"/>">
                <button class="redirection-button" style="margin-right: 25px;">Crear usuario</button>
            </a>
            <a href="<c:url value="/admin/course/new"/>">
                <button class="redirection-button" style="margin:0 25px;">Crear curso</button>
            </a>
            <a href="<c:url value="/admin/course/select"/>">
                <button class="redirection-button" style="margin-left:25px;">Agregar usuario a curso</button>
            </a>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
