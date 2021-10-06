<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <title><spring:message code="400.page.title"/></title>
  <c:import url="config/general-head.jsp"/>

</head>
<body>
<div class="page-organizer">
  <%@ include file="components/navbar.jsp" %>
  <div class="page-container">
    <h1><spring:message code="400.page.message"/></h1>
  </div>
  <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>