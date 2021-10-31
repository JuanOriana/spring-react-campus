<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>

<body>
<div class="file-unit" id="file-${requestScope.answer.answerId}">
    <div style="display: flex; align-items: center">
        <a href="<c:url value="/file/${requestScope.answer.answerFile.fileId}"/>" class="styleless-anchor"
           style="display: flex;margin-left: 10px; align-items: center" target="_blank">
            <img src="<c:url value="/resources/images/test.png"/>"
                 class="file-img" alt="${requestScope.answer.student.name}"/>
            <p class="file-name" style="padding-right: 15px; margin-right: 5px; border-right: 3px solid white">
                <c:out value="${requestScope.answer.student.name} ${requestScope.answer.student.surname}"/>
            </p>
        </a>
        <p class="file-name">
            <spring:message code="student.exam.unit.published.at.title" arguments="${requestScope.answer.deliveredDate}"/>
        </p>
    </div>
    <c:if test="${!param.isCorrected}">
    <form style="display: flex; align-items: center" method="post"
          action="<c:url value="${param.examId}/answer/${requestScope.answer.answerId}/correct"/>">
        <button style="background: none">
            <img src="<c:url value="/resources/images/check.png"/>"
                 alt="check" class="medium-icon">
        </button>
    </form>
    </c:if>
    <c:if test="${param.isCorrected}">
        <form style="display: flex; align-items: center" method="post"
              action="<c:url value="${param.examId}/answer/${requestScope.answer.answerId}/undo-correct"/>">
            <button style="background: none">
                <img src="<c:url value="/resources/images/x.png"/>"
                     alt="check" class="medium-icon">
            </button>
        </form>
    </c:if>
</div>
</body>
</html>

