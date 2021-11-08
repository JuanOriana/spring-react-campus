<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        function deleteById(courseId,fileId){
            const deleteMessage = "<spring:message code="alert.exam.delete" htmlEscape="true"/>"
            const result = confirm(deleteMessage)
            if (result === true){
                $.ajax({
                    url: '${pageContext.request.contextPath}/course/'+ courseId+ '/exam/' + fileId,
                    type: 'DELETE',
                    success: function (result) {
                        $("#exam-"+ fileId).remove();
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="file-unit" id="exam-${requestScope.exam.examId}">
    <div style="display: flex; align-items: center">
        <a <c:if test="${param.isDelivered}">style="pointer-events: none; display: flex; align-items: center"</c:if>
           href="<c:url value="exam/${requestScope.exam.examId}"/>" class="styleless-anchor"
           style="display: flex;margin-left: 10px; align-items: center">
            <img src="<c:url value="/resources/images/test.png"/>"
                 class="file-img" alt="${requestScope.exam.title}"/>
            <p class="file-name">
                <spring:message code="exam.unit.file.name" htmlEscape="true" arguments="${requestScope.exam.title}"/>
            </p>
        </a>
    </div>
    <div style="display: flex; align-items: center">
        <c:if test="${param.isDelivered}">
            <p class="file-name">
                <spring:message code="exam.unit.grade" />
                <c:if test="${requestScope.answer.score != null}">
                    <c:out value="${requestScope.answer.score}"/>
                </c:if>
                <c:if test="${requestScope.answer.score == null}">
                    --
                </c:if>
            </p>
        </c:if>
        <c:if test="${param.isTeacher}">
            <p class="file-name"><spring:message code="exam.unit.number.of.corrected.exams" htmlEscape="true" arguments="${param.examsSolved},${param.userCount}"/></p>
            <img src="<c:url value="/resources/images/trash.png"/>"
                 alt="<spring:message code="img.alt.delete" />" class="medium-icon" onclick="deleteById(${param.courseId},${requestScope.exam.examId})">
        </c:if>
    </div>
</div>
</body>
</html>
