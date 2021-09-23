<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">

            <jsp:include page="components/courseSectionsCol.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
            </jsp:include>

            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="course.section-heading.title" htmlEscape="true"/> </h3>
                <c:forEach var="announcementItem" items="${announcementList}">
                    <div class="announcement-wrapper">
                        <div class="announcement-header">
                            <h4 class="announcement-title"><spring:message code="course.announcement.title" htmlEscape="true" arguments="${announcementItem.title}"/></h4>
                            <p style="font-size: 14px"><spring:message code="course.announcement.owner" htmlEscape="true" arguments="${announcementItem.author.name},${announcementItem.author.surname}"/></p>
                        </div>
                        <p class="announcement-date"><spring:message code="course.announcement.date" htmlEscape="true" arguments="${announcementItem.date.format(dateTimeFormatter)}"/></p>
                        <spring:message code="course.announcement.content" htmlEscape="true" arguments="${announcementItem.content}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
