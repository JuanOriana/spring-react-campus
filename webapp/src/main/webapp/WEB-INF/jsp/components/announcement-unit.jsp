<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        function deleteById(announcementId){
            $.ajax({
                url: '${pageContext.request.contextPath}/announcements/' + announcementId,
                type: 'DELETE',
                success: function (result) {
                    $("#announcement-"+ announcementId).remove();
                }
            });
        }
    </script>
</head>
<body>
    <div class="announcement-wrapper <c:if test="${param.isGlobal}">reduced</c:if>"
         id="announcement-${requestScope.announcementItem.announcementId}">
        <div class="announcement-header">
            <h4 class="announcement-title">
                <spring:message code="announcement.unit.announcement.title" htmlEscape="true"
                                arguments="${requestScope.announcementItem.title}"/>
            </h4>
            <div style="display: flex">
                <div style="display: flex;flex-direction: column;font-size: 14px">
                    <p><spring:message code="announcement.unit.announcement.publisher" htmlEscape="true"
                                       arguments="${requestScope.announcementItem.author.name},${requestScope.announcementItem.author.surname}"/></p>
                    <c:if test="${param.isGlobal}">
                        <a href="<c:url value="/course/${requestScope.announcementItem.course.courseId}"/>"
                           class="styleless-anchor">
                            <p>
                                <spring:message code="announcement.unit.announcement.course" htmlEscape="true"
                                               arguments="${requestScope.announcementItem.course.subject.name}"/>
                            </p>
                        </a>
                    </c:if>
                </div>
                <c:if test="${param.isTeacher}">
                    <img src="<c:url value="/resources/images/trash-red.png"/>"
                         alt="delete" class="small-icon" style="margin-left: 10px"
                         onclick="deleteById(${requestScope.announcementItem.announcementId})">
                </c:if>
            </div>
        </div>
        <p class="announcement-date"><spring:message code="announcement.unit.announcement.date" htmlEscape="true"
                                                     arguments="${requestScope.announcementItem.date.format(dateTimeFormatter)}"/></p>
        <c:set var="newline" value="<%= \"\n\" %>" />
        ${fn:replace(fn:escapeXml(requestScope.announcementItem.content), newline, '<br />')}
    </div>
</body>
</html>
