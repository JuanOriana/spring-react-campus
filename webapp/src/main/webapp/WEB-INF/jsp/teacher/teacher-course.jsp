<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
  <c:import url="../config/generalHead.jsp"/>
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

      <div class="course-data-container">
        <h3 class="section-heading" style="margin: 0 0 20px 20px"> <spring:message code="teacher.course.section-heading"/> </h3>
        <form:form modelAttribute="announcementForm" class="form-wrapper reduced" method="post" acceptCharset="utf-8">
          <h1 class="announcement-title" style="color:#176961; align-self:center"><spring:message code="teacher.course.new.announcement"/></h1>
          <form:label path="title" for="title" class="form-label">
            <spring:message code="teacher.course.new.announcement.title"/>
          </form:label>
          <form:input type="text" path="title" class="form-input" style="font-size: 26px"/>
          <form:errors path="title" element="p" cssClass="error-message"/>
          <form:label path="content" for="content" class="form-label">
            <spring:message code="teacher.course.new.announcement.content"/>
          </form:label>
          <form:textarea path="content" class="form-input" style="width: 95%;resize: none" cols="50" rows="10"></form:textarea>
            <form:errors path="content" element="p" cssClass="error-message"/>
          <button class="form-button"><spring:message code="teacher.course.button.create.announcement" htmlEscape="true"/></button>
        </form:form>
        <div class="separator reduced">.</div>

        <c:if test="${announcementList.size() == 0}">
          <p class="announcement-title" style="width: 100%; text-align: center">
            <spring:message code="teacher.course.no.announcement"/>
          </p>
        </c:if>

        <c:forEach var="announcementItem" items="${announcementList}">
          <div class="announcement-wrapper" id="announcement-${announcementItem.announcementId}">
            <div class="announcement-header">
              <h4 class="announcement-title">
                <spring:message code="teacher.course.announcement.title"
                                htmlEscape="true" arguments="${announcementItem.title}"/>
              </h4>
              <div style="display: flex">
                <p style="font-size: 14px"><spring:message code="teacher.course.announcement.owner"
                                                           htmlEscape="true" arguments="${announcementItem.author.name},
                                                           ${announcementItem.author.surname}"/>
                </p>
                <img src="<c:url value="/resources/images/trash-red.png"/>"
                     alt="delete" class="small-icon" style="margin-left: 10px"
                     onclick="deleteById(${announcementItem.announcementId})">
              </div>
            </div>
            <p class="announcement-date"><spring:message code="teacher.course.announcement.date" htmlEscape="true" arguments="${announcementItem.date.format(dateTimeFormatter)}"/></p>
            <spring:message code="teacher.course.announcement.content" htmlEscape="true" arguments="${announcementItem.content}"/>
          </div>
        </c:forEach>
      </div>

    </div>
  </div>
  <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
