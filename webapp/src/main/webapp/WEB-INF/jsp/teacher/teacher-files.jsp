<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Campus - ${course.subject.name}</title>
    <c:import url="../config/generalHead.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        function deleteById(fileId){
            $.ajax({
                url: '${pageContext.request.contextPath}/files/' + fileId,
                type: 'DELETE',
                success: function (result) {
                    $("#file-"+ fileId).remove();
                }
            });
        }
    </script>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="successMessage" value="${successMessage}"/>
    </jsp:include>
    <h2 class="course-section-name">${course.subject.name}</h2>
    <div class="page-container" style="padding-top: 0">
        <div class="course-page-wrapper">
            <jsp:include page="../components/courseSectionsCol.jsp">
                <jsp:param name="courseName" value="${course.subject.name}"/>
                <jsp:param name="courseId" value="${course.courseId}"/>
            </jsp:include>
            <c:url value="/course/${courseId}/files" var="postUrl"/>
            <div class="course-data-container">
                <h3 class="section-heading" style="margin: 0 0 20px 20px"> Material </h3>
                <form:form modelAttribute="fileForm" method="post" enctype="multipart/form-data"
                           class="form-wrapper reduced" acceptCharset="utf-8">
                    <h1 class="announcement-title" style="color:#176961; align-self:center">Subir nuevo archivo</h1>
                    <form:label path="file" for="file" class="form-label">Archivo</form:label>
                    <form:input path="file" type="file" class="form-input" style="font-size: 26px"/>
                    <form:errors path="file" element="p" cssStyle="color:red;margin-left: 10px"/>
                    <form:label path="categoryId" for="categoryId" class="form-label">Categoria</form:label>
                    <form:select path="categoryId" class="form-input" style="font-size: 26px">
                        <c:forEach var="category" items="${categories}">
                            <form:option value="${category.categoryId}"><c:out value="${category.categoryName}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="categoryId" element="p" cssStyle="color:red;margin-left: 10px"/>
                    <button class="form-button">Publicar</button>
                </form:form>
                <div class="separator reduced">.</div>
                <div class="big-wrapper">
                    <c:set var="categories" value="${categories}" scope="request"/>
                    <c:set var="extensions" value="${extensions}" scope="request"/>
                    <c:set var="extensionType" value="${extensionType}" scope="request"/>
                    <c:set var="categoryType" value="${categoryType}" scope="request"/>
                    <jsp:include page="../components/file-searcher.jsp">
                        <jsp:param name="query" value="${query}"/>
                        <jsp:param name="orderClass" value="${orderClass}"/>
                        <jsp:param name="orderBy" value="${orderBy}"/>
                    </jsp:include>
                    <div class="file-grid">
                        <c:forEach var="file" items="${files}">
                            <div class="file-unit" id="file-${file.fileId}">
                                <a href="<c:url value="/files/${file.fileId}"/>" class="styleless-anchor" target="_blank"
                                   style="display: flex;margin-left: 10px; align-items: center">
                                    <img src="<c:url value="/resources/images/extensions/${file.extension.fileExtension}.png"/>"
                                         class="file-img" alt="${file.name}"/>
                                <p class="file-name"><c:out value=" ${file.name}"/></p>
                                </a>
                                <img src="<c:url value="/resources/images/trash.png"/>"
                                     alt="delete" class="medium-icon" onclick="deleteById(${file.fileId})">
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
