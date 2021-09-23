<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<html>
<head>
    <title><spring:message code="add.user.page.title" htmlEscape="true"/></title>
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
            <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="add.user.to.course" htmlEscape="true" arguments="${course.subject.name}"/></h1>
            <form:label path="userId" for="userId" class="form-label"><spring:message code="add.user.label.user" htmlEscape="true"/></form:label>
            <form:select path="userId" class="form-input" style="font-size: 26px">
                <c:forEach var="user" items="${users}">
                    <form:option value="${user.userId}"><spring:message code="add.user.name.and.surname" htmlEscape="true" arguments="${user.name},${user.surname}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="userId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="roleId" for="roleId" class="form-label"><spring:message code="add.user.label.role" htmlEscape="true"/></form:label>
            <form:select path="roleId" class="form-input" style="font-size: 26px">
                <c:forEach var="role" items="${roles}">
                    <form:option value="${role.roleId}"><spring:message code="add.user.role.name" htmlEscape="true" arguments="${role.roleName}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="roleId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <button class="form-button"><spring:message code="add.user.button.create" htmlEscape="true"/></button>
            <div class="user-container">
                <div class="user-column">
                    <h3 class="form-label" style="margin:0"><spring:message code="add.user.students" htmlEscape="true"/></h3>
                    <ul>
                        <c:forEach var="student" items="${courseStudents}">
                            <li>${student.name} ${student.surname}</li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="user-column">
                    <h3 class="form-label" style="margin:0"><spring:message code="add.user.teachers" htmlEscape="true"/></h3>
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
