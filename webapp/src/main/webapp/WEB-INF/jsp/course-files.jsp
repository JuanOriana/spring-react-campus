<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="components/courseSectionsCol.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
            </jsp:include>
            <c:url value="/course/${courseId}/files" var="postUrl"/>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="course.file.section-heading.title" htmlEscape="true"/> </h3>
                <div class="big-wrapper">
                    <c:set var="categories" value="${categories}" scope="request"/>
                    <c:set var="extensions" value="${extensions}" scope="request"/>
                    <c:set var="extensionType" value="${extensionType}" scope="request"/>
                    <c:set var="categoryType" value="${categoryType}" scope="request"/>
                    <jsp:include page="components/file-searcher.jsp">
                        <jsp:param name="query" value="${query}"/>
                        <jsp:param name="orderClass" value="${orderClass}"/>
                        <jsp:param name="orderBy" value="${orderBy}"/>
                    </jsp:include>
                    <div class="file-grid">
                        <c:if test="${files.size() == 0}">
                            <p class="announcement-title" style="width: 100%; text-align: center">
                                No hay resultados que coincidan con tu busqueda
                            </p>
                        </c:if>
                        <c:forEach var="file" items="${files}">
                            <div class="file-unit" id="file-${file.fileId}">
                                <a href="<c:url value="/files/${file.fileId}"/>" class="styleless-anchor" target="_blank"
                                   style="display: flex;margin-left: 10px; align-items: center">
                                    <img src="<c:url value="/resources/images/extensions/${file.extension.fileExtensionName}.png"/>"
                                         class="file-img" alt="${file.name}"/>
                                    <p class="file-name"><spring:message code="course.file.file.name" htmlEscape="true" arguments="${file.name}"/></p>
                                </a>
                                <div style="display: flex; align-items: center">
                                    <p class="file-name">
                                        Descargas: <c:out value="${file.downloads}"/>
                                    </p>
                                </div>
                            </div>
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
                        <a href="<c:url value="${prevUrl}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="Next page" class="pagination-arrow x-rotated">
                        </a>
                    </c:if>
                    <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
                    <c:if test="${currentPage < maxPage}">
                        <a href="<c:url value="${nextUrl}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="Next page" class="pagination-arrow">
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