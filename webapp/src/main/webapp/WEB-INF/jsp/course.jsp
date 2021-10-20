<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
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
                <jsp:param name="itemId" value="${1}"/>
            </jsp:include>

            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="course.section-heading.title" htmlEscape="true"/> </h3>
                    <div style="display: flex; flex-direction: column;align-items: center">
                    <c:if test="${announcementList.size() == 0}">
                        <p class="announcement-title" style="width: 100%; text-align: center">
                            <spring:message code="announcement.no.announcement"/>
                        </p>
                    </c:if>

                    <c:forEach var="announcementItem" items="${announcementList}">
                        <c:set var="announcementItem" value="${announcementItem}" scope="request"/>
                        <jsp:include page="components/announcement-unit.jsp">
                            <jsp:param name="isGlobal" value="${false}"/>
                        </jsp:include>
                    </c:forEach>
                        <c:if test="${currentPage != 1 || announcementList.size() > 0}">
                        <div class="pagination-wrapper">
                            <c:if test="${currentPage > 1}">
                                <a href="<c:url value="/course/${courseId}/announcements?page=${currentPage-1}&pageSize=${pageSize}"/>">
                                    <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                         alt="Next page" class="pagination-arrow x-rotated">
                                </a>
                            </c:if>
                            <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
                            <c:if test="${currentPage < maxPage}">
                                <a href="<c:url value="/course/${courseId}/announcements?page=${currentPage+1}&pageSize=${pageSize}"/>">
                                    <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                         alt="Next page" class="pagination-arrow">
                                </a>
                            </c:if>
                        </div>
                        </c:if>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
