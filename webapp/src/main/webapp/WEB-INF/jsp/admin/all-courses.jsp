<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title><spring:message code="all.courses.page.title"/></title>
    <c:import url="../config/general-head.jsp"/>
    <link href="<c:url value = "/resources/css/course-table.css" />" rel="stylesheet" >
</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp"/>
    <div class="page-container">
        <h2 class="section-heading"><spring:message code="all.courses.from" htmlEscape="true"
                                                    arguments="${year},${quarter}"/></h2>
        <form method="get" class="course-table-form">
            <div style="display: flex">
                <div style="display: flex;flex-direction: column">
                    <label for="year" class="form-label"><spring:message code="all.courses.label.year"/></label>
                    <select name="year" id="year" class="form-input">
                        <c:forEach var="optionYear" items="${allYears}">
                            <option value="${optionYear}" <c:if test="${year == optionYear}">selected</c:if>>
                                <spring:message code="all.courses.year" htmlEscape="true"
                                                arguments="${optionYear.toString()}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div style="display: flex;flex-direction: column; align-items: center">
                    <p class="form-label"><spring:message code="all.courses.quarter"/></p>
                    <div style="display: flex; width: 70%; justify-content: space-between">
                        <label class="form-label" style="margin:0">
                            <input type="radio" value="1" name="quarter" <c:if test="${quarter == 1}">checked</c:if>>
                            <span><spring:message code="all.courses.first.quarter"/></span>
                        </label>
                        <label class="form-label" style="margin:0">
                            <input type="radio" value="2" name="quarter" <c:if test="${quarter == 2}">checked</c:if>>
                            <span><spring:message code="all.courses.second.quarter"/></span>
                        </label>
                    </div>
                </div>
            </div>
            <button class="form-button"><spring:message code="all.courses.button.search"/></button>
        </form>
        <c:if test="${empty courses}">
            <h2 style="margin-top: 20px"><spring:message code="all.courses.no.courses"/></h2>
        </c:if>
        <c:if test="${not empty courses}">
            <table class="course-table">
                <tr class="course-table-header">
                    <th><spring:message code="all.courses.course.code.title"/></th>
                    <th style="width: 300px"><spring:message code="all.courses.course.name.title"/></th>
                    <th><spring:message code="all.courses.course.board.title"/></th>
                </tr>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td><spring:message code="all.courses.course.code" htmlEscape="true"
                                            arguments="${course.subject.code}"/> </td>

                        <td>
                            <a class="styleless-anchor"
                               href="<c:url value="/admin/course/enroll?courseId=${course.courseId}"/>">
                                <spring:message code="all.courses.course.name" htmlEscape="true"
                                            arguments="${course.subject.name}"/>
                            </a>
                        </td>

                        <td><spring:message code="all.courses.course.board" htmlEscape="true"
                                            arguments="${course.board}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>

</body>
</html>
