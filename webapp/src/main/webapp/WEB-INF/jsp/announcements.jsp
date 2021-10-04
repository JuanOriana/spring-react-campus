<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="announcements.page.title"/></title>
    <c:import url="config/general-head.jsp"/>
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
          <c:set var="announcementItem" value="${announcementItem}" scope="request"/>
          <jsp:include page="components/announcement-unit.jsp">
              <jsp:param name="isGlobal" value="${true}"/>
          </jsp:include>
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
