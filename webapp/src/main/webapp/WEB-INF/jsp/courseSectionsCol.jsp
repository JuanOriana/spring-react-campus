<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<div class="course-sections-col">
  <h3 class="course-sections-col-title">${param.courseName}</h3>
  <p class="course-sections-item"><a class="styleless-anchor" href="/course/${param.courseId}">Anuncios</a> </p>
  <p class="course-sections-item"><a class="styleless-anchor" href="/course/${param.courseId}/professors">Profesores</a> </p>
</div>
</body>
</html>
