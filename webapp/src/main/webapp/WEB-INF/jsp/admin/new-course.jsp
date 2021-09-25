<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="new.course.page.title"/></title>
    <c:import url="../config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <jsp:include page="../components/navbar.jsp" >
        <jsp:param name="isAdmin" value="${true}"/>
    </jsp:include>
    <div class="page-container">
        <form:form modelAttribute="courseForm" class="form-wrapper reduced" method="post"
                   acceptCharset="utf-8" cssStyle="margin: 30px 0">
            <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="new.course.header" htmlEscape="true"/></h1>
            <form:label path="subjectId" for="subjectId" class="form-label"><spring:message code="new.course.subject"/></form:label>
            <form:select path="subjectId" class="form-input" style="font-size: 26px">
                <c:forEach var="subject" items="${subjects}">
                    <form:option value="${subject.subjectId}"><spring:message code="subject.name" htmlEscape="true" arguments="${subject.name}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="subjectId" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="quarter" for="quarter" class="form-label"><spring:message code="new.course.quarter"/></form:label>
            <div style="display:flex; width: 200px; align-items: center;
            justify-content: space-between; margin-left: 20px; font-size: 20px">
                <div>
                    <form:radiobutton path="quarter" value="1" cssStyle="margin-right: 5px"/>1
                </div>
                <div>
                    <form:radiobutton path="quarter" value="2" cssStyle="margin-right: 5px"/>2
                </div>
            </div>
            <form:errors path="quarter" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="year" for="year" class="form-label"><spring:message code="new.course.year"/></form:label>
            <form:input type="number" path="year" class="form-input" style="font-size: 26px"/>
            <form:errors path="year" element="p" cssStyle="color:red;margin-left: 10px"/>
            <form:label path="board" for="board" class="form-label"><spring:message code="new.course.board"/></form:label>
            <form:input type="text" path="board" class="form-input" style="font-size: 26px"/>
            <form:errors path="board" element="p" cssStyle="color:red;margin-left: 10px"/>
            <div style="display: grid; grid-template-columns: 400px 400px; margin: 20px 20px 0 20px">
                <c:forEach var="day" items="${days}" varStatus="dayStatus">
                    <div style="display: flex; flex-direction: column">
                        <p class="form-label"><spring:message code="day.${day}"/></p>
                        <div style="display: flex">
                            <form:input cssStyle="width: 3em" type="number" path="startTimes[${dayStatus.index}]"
                                        min="8" max="22"/>
                            <p>:00 ----  </p>
                            <form:input cssStyle="width: 3em" type="number" path="endTimes[${dayStatus.index}]"
                                        min="8" max="22"/>
                            <p>:00</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <button class="form-button"><spring:message code="new.course.button.create"/></button>
        </form:form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
