<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
    <c:import url="../config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <h2 class="course-section-name"><spring:message code="subject.name" htmlEscape="true" arguments="${course.subject.name}"/></h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="../components/course-sections-col.jsp">
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
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="teacher.file.section-heading"/> </h3>
                <form:form modelAttribute="fileForm" method="post" enctype="multipart/form-data"
                           class="form-wrapper reduced" acceptCharset="utf-8">
                    <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="teacher.file.new.file.title"/></h1>
                    <form:label path="file" for="file" class="form-label"><spring:message code="teacher.file.new.file"/></form:label>
                    <form:input path="file" type="file" class="form-input" style="font-size: 26px"/>
                    <form:errors path="file" element="p" cssClass="error-message"/>
                    <form:label path="categoryId" for="categoryId" class="form-label"><spring:message code="teacher.file.new.file.category"/></form:label>
                    <form:select path="categoryId" class="form-input" style="font-size: 26px">
                        <c:forEach var="category" items="${categories}">
                            <form:option value="${category.categoryId}"><spring:message code="category.${category.categoryName}" htmlEscape="true"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="categoryId" element="p" cssClass="error-message"/>
                    <button class="form-button"><spring:message code="teacher.file.button.upload.file" htmlEscape="true"/></button>
                </form:form>
                <div class="separator reduced">.</div>
                <div class="big-wrapper">
                    <c:set var="categories" value="${categories}" scope="request"/>
                    <c:set var="extensions" value="${extensions}" scope="request"/>
                    <c:set var="extensionType" value="${extensionType}" scope="request"/>
                    <c:set var="categoryType" value="${categoryType}" scope="request"/>
                    <jsp:include page="../components/file-searcher.jsp">
                        <jsp:param name="query" value="${query}"/>
                        <jsp:param name="orderProperty" value="${orderProperty}"/>
                        <jsp:param name="orderDirection" value="${orderDirection}"/>
                    </jsp:include>
                    <div class="file-grid">
                        <c:if test="${files.size() == 0}">
                            <p class="announcement-title" style="width: 100%; text-align: center">
                                <spring:message code="no.results"/>
                            </p>
                        </c:if>
                        <c:forEach var="file" items="${files}">
                            <c:set var="file" value="${file}" scope="request"/>
                            <jsp:include page="../components/file-unit.jsp">
                                <jsp:param name="isTeacher" value="${true}"/>
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
                                 alt="Next page" class="pagination-arrow x-rotated">
                        </a>
                    </c:if>
                    <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
                    <c:if test="${currentPage < maxPage}">
                        <a href="<c:out value="${nextUrl}"/>">
                            <img src="<c:url value="/resources/images/page-arrow.png"/>"
                                 alt="Next page" class="pagination-arrow">
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
