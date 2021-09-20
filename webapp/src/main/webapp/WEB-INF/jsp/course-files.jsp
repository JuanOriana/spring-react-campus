<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - ${course.subject.name}</title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <h2 class="course-section-name">${course.subject.name}</h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="components/courseSectionsCol.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
            </jsp:include>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> Material </h3>
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
                                <a href="<c:url value="/savefile/${file.fileId}"/> " class="styleless-anchor" target="_blank"
                                   style="display: flex;margin-left: 10px; align-items: center">
                                    <img src="<c:url value="${page.Context.request.contextPath}/resources/images/extensions/${file.extension.fileExtension}.png"/>"
                                         class="file-img" alt="${file.name}"/>
                                    <p class="file-name"><c:out value=" ${file.name}"/></p>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
