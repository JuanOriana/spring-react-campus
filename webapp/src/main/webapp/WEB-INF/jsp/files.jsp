<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="files.page.title"/></title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="navItem" value="${3}"/>
    </jsp:include>
    <div class="page-container">
        <h2 class="section-heading"><spring:message code="files.section-heading.title"/></h2>
        <div class="big-wrapper">
            <c:set var="categories" value="${categories}" scope="request"/>
            <c:set var="extensions" value="${extensions}" scope="request"/>
            <c:set var="extensionType" value="${extensionType}" scope="request"/>
            <c:set var="categoryType" value="${categoryType}" scope="request"/>
            <jsp:include page="components/file-searcher.jsp">
                <jsp:param name="query" value="${query}"/>
                <jsp:param name="order-property" value="${orderProperty}"/>
                <jsp:param name="order-direction" value="${orderDirection}"/>
            </jsp:include>
            <div class="file-grid">
                <c:if test="${files.size() == 0}">
                    <p class="announcement-title" style="width: 100%; text-align: center">
                        <spring:message code="no.results" htmlEscape="true"/>
                    </p>
                </c:if>
                <c:forEach var="file" items="${files}">
                    <div class="file-unit">
                        <a href="<c:url value="/files/${file.fileId}"/>" class="styleless-anchor" target="_blank"
                           style="display: flex;margin-left: 10px; align-items: center">
                            <img src="<c:url value="/resources/images/extensions/${file.extension.fileExtensionName}.png"/>"
                                 class="file-img" alt="${file.name}"/>
                            <p class="file-name"><spring:message code="files.file.name" htmlEscape="true" arguments="${file.name}"/></p>
                        </a>
                        <div style="display: flex; align-items: center">
                            <p class="file-name" style="padding-right: 20px; border-right: 3px solid white">
                                <spring:message code="files.file.downloads" htmlEscape="true" arguments="${file.downloads}"/>
                            </p>
                            <a href="<c:url value="/course/${file.course.courseId}"/>" class="styleless-anchor">
                                <p class="file-name"><spring:message code="subject.name" htmlEscape="true" arguments="${file.course.subject.name}"/></p>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="pagination-wrapper" style="align-self: center">
            <c:if test="${currentPage > 1}">
                <a href="<c:url value="/files?page=${currentPage-1}&pageSize=${pageSize}"/>">
                    <img src="<c:url value="/resources/images/page-arrow.png"/>"
                         alt="Next page" class="pagination-arrow x-rotated">
                </a>
            </c:if>
            <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
            <c:if test="${currentPage < maxPage}">
                <a href="<c:url value="/files?page=${currentPage+1}&pageSize=${pageSize}"/>">
                    <img src="<c:url value="/resources/images/page-arrow.png"/>"
                         alt="Next page" class="pagination-arrow">
                </a>
            </c:if>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>

