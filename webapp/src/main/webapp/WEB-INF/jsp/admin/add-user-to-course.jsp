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
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <div class="page-container">
        <form:form modelAttribute="userToCourseForm" class="form-wrapper reduced" method="post"
                   acceptCharset="utf-8" cssStyle="margin: 30px 0">
            <h1 class="announcement-title" style="color:#176961; align-self:center">Agregar usuario a <c:out value="${course.subject.name}"/></h1>
            <form:label path="userId" for="userId" class="form-label">Usuario</form:label>
            <form:select path="userId" class="form-input" style="font-size: 26px">
                <c:forEach var="user" items="${users}">
                    <form:option value="${user.userId}"><c:out value="${user.name} ${user.surname}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="userId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="roleId" for="roleId" class="form-label">Rol</form:label>
            <form:select path="roleId" class="form-input" style="font-size: 26px">
                <c:forEach var="role" items="${roles}">
                    <form:option value="${role.roleId}"><c:out value="${role.roleName}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="roleId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <button class="form-button">Crear</button>
            <div class="user-container">
                <div class="user-column">
                    <h3 class="form-label" style="margin:0">Alumnos</h3>
                    <ul>
                        <c:forEach var="student" items="${courseStudents}">
                            <li>${student.name} ${student.surname}</li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="user-column">
                    <h3 class="form-label" style="margin:0">Profesores</h3>
                    <ul>
                        <c:forEach var="teacher" items="${courseTeachers}">
                            <li>${teacher.name} ${teacher.surname}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </form:form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
