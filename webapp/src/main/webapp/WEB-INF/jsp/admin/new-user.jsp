<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="new.user.page.title" /></title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
    </jsp:include>
    <div class="page-container">
    <form:form modelAttribute="userRegisterForm" class="form-wrapper reduced" method="post"
               acceptCharset="utf-8" cssStyle="margin: 30px 0">
        <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="new.user.header" htmlEscape="true"/></h1>
        <form:label path="fileNumber" for="fileNumber" class="form-label"><spring:message code="new.user.file.number" /></form:label>
        <form:input type="number" path="fileNumber" class="form-input" style="font-size: 26px"/>
        <form:errors path="fileNumber" element="p" cssStyle="color:red;margin-left: 10px"/>
        <form:label path="name" for="name" class="form-label"><spring:message code="new.user.name" /></form:label>
        <form:input type="text" path="name" class="form-input" style="font-size: 26px"/>
        <form:errors path="name" element="p" cssStyle="color:red;margin-left: 10px"/>
        <form:label path="surname" for="surname" class="form-label"><spring:message code="new.user.surname" /></form:label>
        <form:input type="text" path="surname" class="form-input" style="font-size: 26px"/>
        <form:errors path="surname" element="p" cssStyle="color:red;margin-left: 10px"/>
        <form:label path="username" for="username" class="form-label"><spring:message code="new.user.username" /></form:label>
        <form:input type="text" path="username" class="form-input" style="font-size: 26px"/>
        <form:errors path="username" element="p" cssStyle="color:red;margin-left: 10px"/>
        <form:label path="email" for="email" class="form-label"><spring:message code="new.user.email" /></form:label>
        <form:input type="text" path="email" class="form-input" style="font-size: 26px"/>
        <form:errors path="email" element="p" cssStyle="color:red;margin-left: 10px"/>
        <form:label path="password" for="password" class="form-label"><spring:message code="new.user.password" /></form:label>
        <form:input type="password" path="password" class="form-input" style="font-size: 26px"/>
        <form:errors path="password" element="p" cssStyle="color:red;margin-left: 10px"/>
        <button class="form-button"><spring:message code="new.user.button.create"/></button>
    </form:form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
