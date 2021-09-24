<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="campus.page.title"/></title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <div class="page-container">
        <form:form modelAttribute="mailForm" class="form-wrapper reduced" method="post" acceptCharset="utf-8">
            <h1 class="announcement-title r" style="color:#176961; align-self:center">
                <spring:message code="sendmail.title.to" htmlEscape="true" arguments="${user.name},${user.surname}"/>
            </h1>
            <form:label path="subject" for="subject" class="form-label"><spring:message code="sendmail.label.subject"/></form:label>
            <form:input type="text" path="subject" class="form-input" style="width:100%;font-size: 26px"/>
            <form:errors path="subject" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="content" for="content" class="form-label"><spring:message code="sendmail.label.content"/></form:label>
            <form:textarea path="content" class="form-input" style="width: 100%;resize: none" cols="50" rows="10"></form:textarea>
            <form:errors path="content" element="p" cssStyle="color:red; margin-left: 10px"/>
            <button class="form-button"><spring:message code="sendmail.button.send"/></button>
        </form:form>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>