<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
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
        <form:form modelAttribute="userToCourseForm" class="form-wrapper reduced" method="post"
                   acceptCharset="utf-8" cssStyle="margin: 30px 0">
            <h1 class="announcement-title" style="color:#176961; align-self:center">Agregar usuario a curso</h1>
            <form:label path="userId" for="userId" class="form-label">Materia</form:label>
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
            <form:label path="courseId" for="courseId" class="form-label">Materia</form:label>
            <form:select path="courseId" class="form-input" style="font-size: 26px">
                <c:forEach var="course" items="${courses}">
                    <form:option value="${course.courseId}">
                        <c:out value="${course.subject.name}[${course.board}]-${course.year}/${course.quarter}Q}"/>
                    </form:option>
                </c:forEach>
            </form:select>
            <form:errors path="courseId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <button class="form-button">Crear</button>
        </form:form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
