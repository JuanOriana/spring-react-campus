<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        function uncheckById(answerId, path){
            const deleteMessage = "<spring:message code="alert.student.exam.unit.undo.correction" htmlEscape="true"/>"
            const result = confirm(deleteMessage);
            if (result === true){
                const form = $('<form></form>');
                form.attr("method", "post");
                form.attr("action", path);
                $(document.body).append(form);
                form.submit();
            }
        }
    </script>
</head>
<body>
<div class="file-unit" id="file-${requestScope.answer.answerId}">
    <div style="display: flex; align-items: center">
            <img src="<c:url value="/resources/images/test.png"/>"
                 class="file-img" alt="${requestScope.answer.student.name}"/>
            <p class="file-name" style="padding-right: 15px; margin-right: 5px; border-right: 3px solid white">
                <c:out value="${requestScope.answer.student.name} ${requestScope.answer.student.surname}"/>
            </p>
        <c:if test="${requestScope.answer.deliveredDate == null}">
            <p class="file-name" style="color:red">
                <spring:message code="student.exam.unit.not.published" arguments="${requestScope.answer.deliveredDate}"/>
            </p>
        </c:if>
        <c:if test="${requestScope.answer.deliveredDate != null&& requestScope.dateTimeFormatter != null}">
            <p class="file-name">
                <spring:message code="student.exam.unit.published.at.title"
                                arguments="${requestScope.dateTimeFormatter.format(requestScope.answer.deliveredDate)}"/>
            </p>
        </c:if>
    </div>
    <c:if test="${!param.isCorrected}">
    <a class="styleless-anchor"  href="<c:url value="${param.examId}/answer/${requestScope.answer.answerId}/correct"/>">
        <img src="<c:url value="/resources/images/check.png"/>" alt="<spring:message code="img.alt.check" />" class="medium-icon">
    </a>
    </c:if>
    <c:if test="${param.isCorrected}">
<%--        TODO: rev si esta bien el uso de la funcion uncheckById --%>
<%--        <form style="display: flex; align-items: center" method="post"--%>
<%--              action="<c:url value="${param.examId}/answer/${requestScope.answer.answerId}/undo-correct"/>">--%>
            <button style="background: none" type="button">
                <img src="<c:url value="/resources/images/x.png"/>"
                     alt="<spring:message code="img.alt.check" />" class="medium-icon"
                     onclick="uncheckById(${requestScope.answer.answerId}, ${param.examId}+'/answer/' + ${requestScope.answer.answerId} + '/undo-correct' )">
            </button>
        </form>
    </c:if>
</div>
</body>
</html>

