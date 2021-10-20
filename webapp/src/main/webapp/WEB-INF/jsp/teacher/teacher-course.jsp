<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <title><spring:message code="page.title.course.subject.name" htmlEscape="true" arguments="${course.subject.name}"/></title>
  <c:import url="../config/general-head.jsp"/>
</head>
<body>
<div class="page-organizer">
  <jsp:include page="../components/navbar.jsp">
    <jsp:param name="successMessage" value="${successMessage}"/>
  </jsp:include>
  <h2 class="course-section-name">${course.subject.name}</h2>
  <div class="page-container" style="padding-top: 0">
    <div class="course-page-wrapper">
      <jsp:include page="../components/course-sections-col.jsp">
        <jsp:param name="courseName" value="${course.subject.name}"/>
        <jsp:param name="courseId" value="${course.courseId}"/>
        <jsp:param name="year" value="${course.year}"/>
        <jsp:param name="quarter" value="${course.quarter}"/>
        <jsp:param name="code" value="${course.subject.code}"/>
        <jsp:param name="board" value="${course.board}"/>
        <jsp:param name="itemId" value="${1}"/>
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

        <div style="display: flex; flex-direction: column;align-items: center";
          <c:if test="${announcementList.size() == 0}">
            <p class="announcement-title" style="width: 100%; text-align: center">
              <spring:message code="teacher.course.no.announcement"/>
            </p>
          </c:if>

          <c:forEach var="announcementItem" items="${announcementList}">
            <c:set var="announcementItem" value="${announcementItem}" scope="request"/>
            <jsp:include page="../components/announcement-unit.jsp">
              <jsp:param name="isTeacher" value="${true}"/>
            </jsp:include>
          </c:forEach>
          <c:if test="${currentPage != 1 || announcementList.size() > 0}">
            <div class="pagination-wrapper">
              <c:if test="${currentPage > 1}">
                <a href="<c:url value="/course/${courseId}/announcements?page=${currentPage-1}&pageSize=${pageSize}"/>">
                  <img src="<c:url value="/resources/images/page-arrow.png"/>"
                       alt="Next page" class="pagination-arrow x-rotated">
                </a>
              </c:if>
              <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
              <c:if test="${currentPage < maxPage}">
                <a href="<c:url value="/course/${courseId}/announcements?page=${currentPage+1}&pageSize=${pageSize}"/>">
                  <img src="<c:url value="/resources/images/page-arrow.png"/>"
                       alt="Next page" class="pagination-arrow">
                </a>
              </c:if>
            </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
