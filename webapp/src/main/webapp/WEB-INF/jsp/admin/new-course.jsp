<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Campus - Nuevo Usuario</title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="isAdmin" value="${true}"/>
    </jsp:include>
    <div class="page-container">
        <form:form modelAttribute="courseForm" class="form-wrapper reduced" method="post"
                   acceptCharset="utf-8" cssStyle="margin: 30px 0">
            <h1 class="announcement-title" style="color:#176961; align-self:center">Crear nuevo curso</h1>
            <form:label path="subjectId" for="subjectId" class="form-label">Materia</form:label>
            <form:select path="subjectId" class="form-input" style="font-size: 26px">
                <c:forEach var="subject" items="${subjects}">
                    <form:option value="${subject.subjectId}"><c:out value="${subject.name}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="subjectId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="quarter" for="quarter" class="form-label">Cuatrimestre</form:label>
            <form:input type="number" path="quarter" class="form-input" style="font-size: 26px"/>
            <form:errors path="quarter" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="year" for="year" class="form-label">Ano</form:label>
            <form:input type="number" path="year" class="form-input" style="font-size: 26px"/>
            <form:errors path="year" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="board" for="board" class="form-label">Comision</form:label>
            <form:input type="text" path="board" class="form-input" style="font-size: 26px"/>
            <form:errors path="board" element="p" cssStyle="color:red;margin-left: 10px"/>
            <button class="form-button">Crear</button>
        </form:form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
