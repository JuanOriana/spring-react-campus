<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Campus - Error</title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <div class="page-container">
        <form:form modelAttribute="mailForm" class="form-wrapper reduced" method="post">
            <h1 class="announcement-title r" style="color:#176961; align-self:center">
                Enviar un mail a <c:out value="${user.name} ${user.surname}"/>
            </h1>
            <form:label path="subject" for="subject" class="form-label">Asunto</form:label>
            <form:input type="text" path="subject" class="form-input" style="width:100%;font-size: 26px"/>
            <form:errors path="subject" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="content" for="content" class="form-label">Contenido</form:label>
            <form:textarea path="content" class="form-input" style="width: 100%;resize: none" cols="50" rows="10"></form:textarea>
            <form:errors path="content" element="p" cssStyle="color:red; margin-left: 10px"/>
            <button class="form-button">Enviar</button>
        </form:form>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>