<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="files.page.title" htmlEscape="true"/></title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="navItem" value="${3}"/>
    </jsp:include>
    <div class="page-container">
        <h2 class="section-heading"><spring:message code="files.section-heading.title" htmlEscape="true"/></h2>
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
                <c:forEach var="file" items="${files}">
                    <div class="file-unit">
                        <a href="<c:url value="/download/${file.fileId}"/>" class="styleless-anchor"
                           style="display: flex;margin-left: 10px; align-items: center">
                            <img src="<c:url value="${page.Context.request.contextPath}/resources/images/extensions/${file.extension.fileExtension}.png"/>"
                                 class="file-img" alt="${file.name}"/>
                            <p class="file-name"><spring:message code="files.file.name" htmlEscape="true" arguments="${file.name}"/></p>
                        </a>
                        <a href="<c:url value="/course/${file.course.courseId}"/>" class="styleless-anchor">
                            <p class="file-name"><spring:message code="files.file.subject.name" htmlEscape="true" arguments="${file.course.subject.name}"/></p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>

