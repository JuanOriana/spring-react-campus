<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
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
    <div class="file-unit" id="file-${requestScope.file.fileId}">
        <div style="display: flex; align-items: center">
            <a href="<c:url value="/files/${requestScope.file.fileId}"/>" class="styleless-anchor" target="_blank"
               style="display: flex;margin-left: 10px; align-items: center">
                <img src="<c:url value="/resources/images/extensions/${requestScope.file.extension.fileExtensionName}.png"/>"
                     class="file-img" alt="${requestScope.file.name}"/>
                <p class="file-name">
                    <spring:message code="teacher.file.course.file.name" htmlEscape="true" arguments="${requestScope.file.name}"/>
                </p>
            </a>
            <c:forEach var="category" items="${requestScope.file.categories}">
                <p class="file-category-name">
                    <spring:message code="category.${category.categoryName}" htmlEscape="true"/>
                </p>
            </c:forEach>
        </div>
        <div style="display: flex; align-items: center">
            <p class="file-name">
                <spring:message code="files.file.downloads" htmlEscape="true" arguments="${requestScope.file.downloads}"/>
            </p>
            <c:if test="${param.isTeacher}">
                <img src="<c:url value="/resources/images/trash.png"/>"
                     alt="delete" class="medium-icon" onclick="deleteById(${requestScope.file.fileId})">
            </c:if>
            <c:if test="${param.isGlobal}">
                <div style="padding-left: 5px; margin-left: 15px; border-left: 3px solid white">
                    <a href="<c:url value="/course/${file.course.courseId}"/>" class="styleless-anchor">
                        <p class="file-name"><spring:message code="subject.name" htmlEscape="true" arguments="${file.course.subject.name}"/></p>
                    </a>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
