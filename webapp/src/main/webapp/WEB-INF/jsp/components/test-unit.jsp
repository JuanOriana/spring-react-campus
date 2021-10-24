<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        function deleteById(fileId){
            const deleteMessage = "<spring:message code="alert.file.delete" htmlEscape="true"/>"
            const result = confirm(deleteMessage)
            if (result === true){
                $.ajax({
                    url: '${pageContext.request.contextPath}/exams/' + fileId,
                    type: 'DELETE',
                    success: function (result) {
                        $("#file-"+ fileId).remove();
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="file-unit" id="file-${requestScope.exam.examId}">
    <div style="display: flex; align-items: center">
        <a href="<c:url value="/exams/${requestScope.exam.examId}"/>" class="styleless-anchor"
           style="display: flex;margin-left: 10px; align-items: center">
            <img src="<c:url value="/resources/images/test.png"/>"
                 class="file-img" alt="${requestScope.exam.name}"/>
            <p class="file-name">
                <spring:message code="file.unit.file.name" htmlEscape="true" arguments="${requestScope.exam.name}"/>
            </p>
        </a>
    </div>
    <div style="display: flex; align-items: center">
        <c:if test="${param.isTeacher}">
            <img src="<c:url value="/resources/images/trash.png"/>"
                 alt="delete" class="medium-icon" onclick="deleteById(${requestScope.exam.examId})">
        </c:if>
    </div>
</div>
</body>
</html>
