<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<nav class="navbar-container">
    <h1 class="nav-title"><a class="styleless-anchor" href="<c:url value ="/portal"/>">CAMPUS</a></h1>
    <ul class="nav-sections-container">
        <li class="nav-sections-item"><a href="<c:url value ="/portal"/>" class="styleless-anchor">Mis Cursos</a> </li>
        <li class="nav-sections-item"><a href="<c:url value ="/announcements"/>" class="styleless-anchor">Mis Anuncios</a></li>
        <li class="nav-sections-item"><a href="<c:url value ="/timetable"/>" class="styleless-anchor">Mis Horarios</a></li>
    </ul>
    <p class="user-data">Hola Jefe!</p>
</nav>
</body>
</html>
