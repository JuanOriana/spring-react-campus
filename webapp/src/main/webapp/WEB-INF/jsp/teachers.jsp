<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
<jsp:include page="components/navbar.jsp">
    <jsp:param name="successMessage" value="${successMessage}"/>
</jsp:include>
<h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
<div class="page-container" style="padding-top: 0">
    <div class="course-page-wrapper">
        <jsp:include page="components/course-sections-col.jsp">
            <jsp:param name="courseName" value="${course.subject.name}"/>
            <jsp:param name="courseId" value="${course.courseId}"/>
            <jsp:param name="year" value="${course.year}"/>
            <jsp:param name="quarter" value="${course.quarter}"/>
            <jsp:param name="code" value="${course.subject.code}"/>
            <jsp:param name="board" value="${course.board}"/>
            <jsp:param name="itemId" value="${2}"/>
        </jsp:include>
        <div class="course-data-container">
            <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="teachers.section-heading.title"/> </h3>
            <div class="big-wrapper">
                <c:forEach var="teacher" items="${teacherSet}">
                        <div class="professor-unit">
                            <c:if test="${teacher.key.image == null}">
                                <img alt="professor icon" class="professor-icon" src="<c:url value="/resources/images/default-user-image.png"/>"/>
                            </c:if>
                            <c:if test="${teacher.key.image != null}">
                                <img alt="professor icon" class="professor-icon" src="<c:url value="/user/profile-image/${teacher.key.userId}"/>"/>
                            </c:if>
                            <div style="display: flex; width:200px; flex-direction: column">
                                <p><spring:message code="teachers.teacher.name" htmlEscape="true" arguments="${teacher.key.name},${teacher.key.surname}"/></p>
                                <p><spring:message code="teachers.teacher.email" htmlEscape="true" arguments="${teacher.key.email}"/></p>
                            </div>
                            <a class="styleless-anchor" href="<c:url value="/course/${courseId}/mail/${teacher.key.userId}"/>">
                                <img alt="mail icon" class="mail-icon"
                                     src="https://i.pinimg.com/originals/3a/4e/95/3a4e95aa862636d6f22c95fded897f94.jpg"/>
                            </a>
                        </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
