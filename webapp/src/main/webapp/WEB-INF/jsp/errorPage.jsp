<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="errorPage.page.title" htmlEscape="true"/></title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <div class="page-container">
        <h1><spring:message code="errorPage.message" htmlEscape="true" arguments="${errorMsg}"/></h1>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>