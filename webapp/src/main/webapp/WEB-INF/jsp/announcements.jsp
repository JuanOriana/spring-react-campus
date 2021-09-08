<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Anuncios</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <link href="<c:url value = "${page.Context.request.contextPath}/resources/css/style.css" />" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;700&family=Righteous&display=swap"
          rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp">
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
                      <p>Para: <c:out value="${announcementItem.course.subject.name}"/></p>
                  </div>
              </div>
              <p class="announcement-date"><c:out value="${announcementItem.date}"/></p>
              <c:out value="${announcementItem.content}"/>
          </div>
      </c:forEach>

  </div>

</body>
</html>
