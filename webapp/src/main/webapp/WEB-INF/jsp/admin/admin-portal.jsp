<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="admin.page.title" htmlEscape="true"/></title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="isAdmin" value="${true}"/>
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <div class="page-container" >
        <h1 class="admin-title"><spring:message code="admin.page.header" htmlEscape="true"/></h1>
        <div style="display: flex">
            <a href="<c:url value="/admin/user/new"/>">
                <button class="redirection-button" style="margin-right: 25px;"><spring:message code="admin.button.create.user" htmlEscape="true"/></button>
            </a>
            <a href="<c:url value="/admin/course/new"/>">
                <button class="redirection-button" style="margin:0 25px;"><spring:message code="admin.button.create.course" htmlEscape="true"/></button>
            </a>
            <a href="<c:url value="/admin/course/select"/>">
                <button class="redirection-button" style="margin-left:25px;"><spring:message code="admin.button.add.user.to.course" htmlEscape="true"/></button>
            </a>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
