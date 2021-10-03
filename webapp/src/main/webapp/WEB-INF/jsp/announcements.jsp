<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="announcements.page.title"/></title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="navItem" value="${2}"/>
    </jsp:include>
  <div class="page-container">
      <h2 class="section-heading"><spring:message code="announcements.section-heading.title"/></h2>
      <c:if test="${announcementList.size() == 0}">
          <p class="announcement-title" style="width: 100%; text-align: center">
              <spring:message code="announcement.no.announcement"/>
          </p>
      </c:if>
      <c:forEach var="announcementItem" items="${announcementList}">
          <div class="announcement-wrapper reduced">
              <div class="announcement-header">
                  <h4 class="announcement-title"><spring:message code="announcement.title" htmlEscape="true" arguments="${announcementItem.title}"/></h4>
                  <div style="display: flex;flex-direction: column;font-size: 14px">
                      <p><spring:message code="announcement.publisher.owner" htmlEscape="true" arguments="${announcementItem.author.name},${announcementItem.author.surname}"/></p>
                      <a href="<c:url value="/course/${announcementItem.course.courseId}"/>" class="styleless-anchor">
                        <p><spring:message code="announcement.publisher.course" htmlEscape="true" arguments="${announcementItem.course.subject.name}"/></p>
                      </a>
                  </div>
              </div>
              <p class="announcement-date"><spring:message code="announcement.date" htmlEscape="true" arguments="${announcementItem.date.format(dateTimeFormatter)}"/></p>
              <c:set var="newline" value="<%= \"\n\" %>" />
              <c:out escapeXml="false" value="${fn:replace(announcementItem.content, newline, '<br />')}" />
          </div>
      </c:forEach>


      <div class="pagination-wrapper">
          <c:if test="${currentPage > 1}">
              <a href="<c:url value="/announcements?page=${currentPage-1}&pageSize=${pageSize}"/>">
                <img src="<c:url value="/resources/images/page-arrow.png"/>"
                     alt="Next page" class="pagination-arrow x-rotated">
              </a>
          </c:if>
          <spring:message code="page.actual" htmlEscape="true" arguments="${currentPage},${maxPage}" />
          <c:if test="${currentPage < maxPage}">
              <a href="<c:url value="/announcements?page=${currentPage+1}&pageSize=${pageSize}"/>">
                  <img src="<c:url value="/resources/images/page-arrow.png"/>"
                       alt="Next page" class="pagination-arrow">
              </a>
          </c:if>
      </div>

  </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
