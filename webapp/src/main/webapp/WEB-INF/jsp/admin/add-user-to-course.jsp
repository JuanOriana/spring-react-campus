<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="add.user.page.title"/></title>
    <c:import url="../config/general-head.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <div class="page-container" style="flex-direction: row;align-items: start">
        <jsp:include page="../components/admin-sections-col.jsp">
            <jsp:param name="itemId" value="${3}"/>
        </jsp:include>
        <form:form modelAttribute="userToCourseForm" class="form-wrapper reduced" method="post"
                   acceptCharset="utf-8" cssStyle="margin: 0px 40px 40px 40px">
            <a href="<c:url value="/admin/course/select" />" class="styleless-anchor"
               style="display: flex; align-items: center">
                <img src="<c:url value="/resources/images/page-arrow.png"/>" alt="<spring:message code="add.user.img.alt.back"/>" class="back-img">
                <p style="font-size: 22px; font-weight: 700"><spring:message code="back.button"/></p>
            </a>
            <h1 class="announcement-title" style="color:#176961; align-self:center">
                <spring:message code="add.user.to.course" htmlEscape="true"
                                arguments="${course.subject.name},${course.board}"/>
            </h1>
            <form:label path="userId" for="userId" class="form-label">
                <spring:message code="add.user.label.user"/>
            </form:label>
            <form:select path="userId" class="form-input" style="font-size: 26px">
                <c:forEach var="user" items="${users}">
                    <form:option value="${user.userId}">
                        <spring:message code="add.user.name.and.surname" htmlEscape="true"
                                        arguments="${user.fileNumber},${user.name},${user.surname}"/>
                    </form:option>
                </c:forEach>
            </form:select>
            <form:errors path="userId" element="p" cssClass="error-message"/>
            <form:label path="roleId" for="roleId" class="form-label">
                <spring:message code="add.user.label.role"/>
            </form:label>
            <form:select path="roleId" class="form-input" style="font-size: 26px">
                <c:forEach var="role" items="${roles}">
                    <form:option value="${role.roleId}">
                        <spring:message code="role.${role.roleName}" htmlEscape="true"/>
                    </form:option>
                </c:forEach>
            </form:select>
            <form:errors path="roleId" element="p" cssClass="error-message"/>
            <button class="form-button"><spring:message code="add.user.button.add"/></button>
            <c:if test="${courseTeachers.size() > 0 || courseStudents.size() > 0 || courseHelpers.size() > 0}">
                <div class="user-container">
                    <div class="user-column">
                        <h3 class="form-label" style="margin:0">
                            <spring:message code="add.user.students"/>
                        </h3>
                        <ul>
                            <c:forEach var="student" items="${courseStudents}">
                                <li><c:out value="${student.name} ${student.surname}"/></li>
                            </c:forEach>
                        </ul>
                        <c:if test="${maxPage > 1}">
                            <div class="pagination-wrapper" style="align-self: center; font-size: 14px; margin-right: 25%">
                                <c:if test="${currentPage > 1}">
                                    <a href="<c:url
                                    value="/admin/course/enroll?courseid=${course.courseId}&page=${currentPage-1}&pageSize=${pageSize}"/>">
                                        <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                             alt="<spring:message code="img.alt.previous.page"/>" class="pagination-arrow x-rotated mini-icon">
                                    </a>
                                </c:if>
                                <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
                                <c:if test="${currentPage < maxPage}">
                                    <a href="<c:url
                                    value="/admin/course/enroll?courseid=${course.courseId}&page=${currentPage+1}&pageSize=${pageSize}"/>">
                                        <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                             alt="<spring:message code="img.alt.next.page"/>" class="pagination-arrow mini-icon">
                                    </a>
                                </c:if>
                            </div>
                        </c:if>
                    </div>
                    <div class="user-column">
                        <c:if test="${courseTeachers.size() > 0}">
                            <h3 class="form-label" style="margin:0">
                                <spring:message code="add.user.teachers"/>
                            </h3>
                            <ul>
                                <c:forEach var="teacher" items="${courseTeachers}">
                                    <li><c:out value="${teacher.name} ${teacher.surname}"/></li>
                                </c:forEach>

                            </ul>
                        </c:if>
                        <c:if test="${courseHelpers.size() > 0}">
                            <h3 class="form-label" style="margin:0">
                                <spring:message code="add.user.helpers"/>
                            </h3>
                            <ul>
                                <c:forEach var="helper" items="${courseHelpers}">
                                    <li><c:out value="${helper.name} ${helper.surname}"/></li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </form:form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
