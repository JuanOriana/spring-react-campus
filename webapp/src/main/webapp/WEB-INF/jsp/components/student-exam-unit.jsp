<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>

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
        <c:if test="${requestScope.answer.deliveredDate != null}">
            <p class="file-name">
                <spring:message code="student.exam.unit.published.at.title" arguments="${requestScope.answer.deliveredDate}"/>
            </p>
        </c:if>
    </div>
    <c:if test="${!param.isCorrected}">
    <a class="styleless-anchor"  href="<c:url value="${param.examId}/answer/${requestScope.answer.answerId}/correct"/>">
        <img src="<c:url value="/resources/images/check.png"/>" alt="check" class="medium-icon">
    </a>
    </c:if>
    <c:if test="${param.isCorrected}">
        <form style="display: flex; align-items: center" method="post"
              action="<c:url value="${param.examId}/answer/${requestScope.answer.answerId}/undo-correct"/>">
            <button style="background: none">
                <img src="<c:url value="/resources/images/x.png"/>"
                     alt="<spring:message code="img.alt.check" />" class="medium-icon">
            </button>
        </form>
    </c:if>
</div>
</body>
</html>

