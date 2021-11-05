<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="files.page.title"/></title>
    <c:import url="config/general-head.jsp"/>
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
                <jsp:param name="orderProperty" value="${orderProperty}"/>
                <jsp:param name="orderDirection" value="${orderDirection}"/>
                <jsp:param name="appliedFilters" value="${listOfAppliedFilters}"/>
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
                        <jsp:param name="isGlobal" value="${true}"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </div>
        <c:set var="nextUrl">
            <my:replaceParam name="page" value="${currentPage + 1}" baseUrl="/files?"/>
        </c:set>
        <c:set var="prevUrl">
            <my:replaceParam name="page" value="${currentPage - 1}" baseUrl="/files?"/>
        </c:set>
        <div class="pagination-wrapper" style="align-self: center">
            <c:if test="${currentPage > 1}">
                <a href="<c:out value="${prevUrl}"/>">
                    <img src="<c:url value="/resources/images/page-arrow.png"/>"
                         alt="<spring:message code="img.alt.next.page" />" class="pagination-arrow x-rotated">
                </a>
            </c:if>
            <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
            <c:if test="${currentPage < maxPage}">
                <a href="<c:out value="${nextUrl}"/>">
                    <img src="<c:url value="/resources/images/page-arrow.png"/>"
                         alt="<spring:message code="img.alt.next.page" />" class="pagination-arrow">
                </a>
            </c:if>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>

