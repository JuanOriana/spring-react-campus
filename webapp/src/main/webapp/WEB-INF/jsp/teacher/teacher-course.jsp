<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
  <title>Campus - <c:out value="${course.subject.name}"/></title>
  <c:import url="../config/generalHead.jsp"/>
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
        <h3 class="section-heading" style="margin: 0 0 20px 20px"> Anuncios </h3>
        <form:form modelAttribute="announcementForm" class="form-wrapper reduced" method="post">
          <h1 class="announcement-title" style="color:#176961; align-self:center">Crear nuevo anuncio</h1>
          <form:label path="title" for="title" class="form-label">Titulo</form:label>
          <form:input type="text" path="title" class="form-input" style="font-size: 26px"/>
          <form:errors path="title" element="p" cssStyle="color:red;margin-left: 10px"/>
          <form:label path="content" for="content" class="form-label">Contenido</form:label>
          <form:textarea path="content" class="form-input" style="width: 100%;resize: none" cols="50" rows="10"></form:textarea>
            <form:errors path="content" element="p" cssStyle="color:red; margin-left: 10px"/>
          <button class="form-button">Publicar</button>
        </form:form>
        <div class="separator reduced">.</div>
        <c:forEach var="announcementItem" items="${announcementList}">
          <div class="announcement-wrapper">
            <div class="announcement-header">
              <h4 class="announcement-title"><c:out value="${announcementItem.title}"/></h4>
              <p style="font-size: 14px">Publicado por: <c:out value="${announcementItem.author.name}
                                ${announcementItem.author.surname}"/></p>
            </div>
            <p class="announcement-date"><c:out value="${announcementItem.date}"/></p>
            <c:out value="${announcementItem.content}"/>
          </div>
        </c:forEach>
      </div>

    </div>
  </div>
  <jsp:include page="../components/footer.jsp"/>
</div>
</body>
</html>
