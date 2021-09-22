<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Anuncios</title>
    <c:import url="config/generalHead.jsp"/>
</head>
<body>
<div class="page-organizer">
    <jsp:include page="components/navbar.jsp">
        <jsp:param name="navItem" value="${2}"/>
    </jsp:include>
  <div class="page-container">
      <h2 class="section-heading">Mis Anuncios</h2>
      <c:forEach var="announcementItem" items="${announcementList}">
          <div class="announcement-wrapper reduced">
              <div class="announcement-header">
                  <h4 class="announcement-title"><c:out value="${announcementItem.title}"/></h4>
                  <div style="display: flex;flex-direction: column;font-size: 14px">
                      <p>Publicado por: <c:out value="${announcementItem.author.name}
                      ${announcementItem.author.surname}"/></p>
                      <a href="<c:url value="/course/${announcementItem.course.courseId}"/>" class="styleless-anchor">
                        <p>Para: <c:out value="${announcementItem.course.subject.name}"/></p>
                      </a>
                  </div>
              </div>
              <p class="announcement-date"><c:out value="${announcementItem.date.format(dateTimeFormatter)}"/></p>
              <c:out value="${announcementItem.content}"/>
          </div>
      </c:forEach>


      <div class="pagination-wrapper">
          <c:if test="${currentPage > 1}">
              <a href="<c:url value="/announcements?page=${currentPage-1}&pageSize=${pageSize}"/>">
                <img src="<c:url value="/resources/images/page-arrow.png"/>"
                     alt="Next page" class="pagination-arrow x-rotated">
              </a>
          </c:if>
          Pagina ${currentPage} de ${maxPage}
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
