<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" %>

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
    <h2 class="course-section-name">
        <spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/>
    </h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="components/course-sections-col.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
                <jsp:param name="year" value="${course.year}"/>
                <jsp:param name="quarter" value="${course.quarter}"/>
                <jsp:param name="code" value="${course.subject.code}"/>
                <jsp:param name="board" value="${course.board}"/>
                <jsp:param name="itemId" value="${3}"/>
            </jsp:include>
            <c:url value="/course/${courseId}/files" var="postUrl"/>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px">
                    <spring:message code="course.file.section-heading.title" htmlEscape="true"/>
                </h3>
                <div class="big-wrapper" style="display: flex; flex-direction: column">
                    <c:set var="categories" value="${categories}" scope="request"/>
                    <c:set var="extensions" value="${extensions}" scope="request"/>
                    <c:set var="extensionType" value="${extensionType}" scope="request"/>
                    <c:set var="categoryType" value="${categoryType}" scope="request"/>
                    <c:set var="filteredCategories" value="${filteredCategories}" scope="request"/>
                    <c:set var="filteredExtensions" value="${filteredExtensions}" scope="request"/>
                    <jsp:include page="components/file-searcher.jsp">
                        <jsp:param name="query" value="${query}"/>
                        <jsp:param name="orderDirection" value="${orderDirection}"/>
                        <jsp:param name="orderDirection" value="${orderDirection}"/>
                    </jsp:include>
                    <div class="file-grid">
                        <c:if test="${files.size() == 0}">
                            <p class="announcement-title" style="width: 100%; text-align: center">
                                <spring:message code="no.results" htmlEscape="true"/>
                            </p>
                        </c:if>
                        <c:forEach var="file" items="${files}">
                            <c:set var="file" value="${file}" scope="request"/>
                            <jsp:include page="components/file-unit.jsp">
                                <jsp:param name="isTeacher" value="${false}"/>
                            </jsp:include>
                        </c:forEach>
                    </div>
                </div>
                <c:set var="nextUrl">
                    <my:replaceParam name="page" value="${currentPage + 1}" baseUrl="/course/${courseId}/files?"/>
                </c:set>
                <c:set var="prevUrl">
                    <my:replaceParam name="page" value="${currentPage - 1}" baseUrl="/course/${courseId}/files?"/>
                </c:set>
                <div class="pagination-wrapper" style="align-self: center">
                    <c:if test="${currentPage > 1}">
                        <a href="<c:out value="${prevUrl}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="<spring:message code="img.alt.previous.page"/>" class="pagination-arrow x-rotated">
                        </a>
                    </c:if>
                    <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
                    <c:if test="${currentPage < maxPage}">
                        <a href="<c:out value="${nextUrl}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="<spring:message code="img.alt.next.page"/>" class="pagination-arrow">
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>