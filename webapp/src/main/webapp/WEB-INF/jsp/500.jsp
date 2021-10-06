<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="500.page.title"/></title>
    <c:import url="config/general-head.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp"/>
    <div class="page-container">
        <h1 class="error-title"><spring:message code="500.page.message"/></h1>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>