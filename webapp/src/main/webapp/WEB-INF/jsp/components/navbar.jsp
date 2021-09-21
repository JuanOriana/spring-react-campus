<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<body>
<nav class="navbar-container">
    <h1 class="nav-title"><a class="styleless-anchor" href="<c:url value ="/portal"/>"><spring:message code="navbar.title" htmlEscape="true"/></a></h1>
    <ul class="nav-sections-container">
        <li class="${param.navItem == 1? "nav-sections-item nav-sections-item-active" : "nav-sections-item" }">
            <a href="<c:url value ="/portal"/>" class="styleless-anchor"><spring:message code="navbar.my.courses" htmlEscape="true"/></a>
        </li>
        <li class="${param.navItem == 2? "nav-sections-item nav-sections-item-active" : "nav-sections-item" }">
            <a href="<c:url value ="/announcements"/>" class="styleless-anchor"><spring:message code="navbar.my.announcements" htmlEscape="true"/></a>
        </li>
        <li class="${param.navItem == 3? "nav-sections-item nav-sections-item-active" : "nav-sections-item" }">
            <a href="<c:url value ="/files"/>" class="styleless-anchor"><spring:message code="navbar.my.files" htmlEscape="true"/></a>
        </li>
        <li class="${param.navItem == 4? "nav-sections-item nav-sections-item-active" : "nav-sections-item" }">
            <a href="<c:url value ="/timetable"/>" class="styleless-anchor"><spring:message code="navbar.my.timetable" htmlEscape="true"/></a>
        </li>
    </ul>
    <c:if test="${currentUser != null}">
        <div class="user-nav-wrapper">
            <a href="<c:url value="/user"/>" class="styleless-anchor">
                <h4><spring:message code="navbar.user" htmlEscape="true" arguments="${currentUser.name}"/></h4>
            </a>
            <a class="styleless-anchor" href="<c:url value ="/logout"/>">
                <button class="logout-btn"><spring:message code="navbar.button.logout" htmlEscape="true"/></button>
            </a>
        </div>
    </c:if>
    <c:if test="${currentUser == null}">
        <div style="width: 120px"></div>
    </c:if>
</nav>
<c:if test="${param.successMessage != null && !param.successMessage.equals('')}">
    <div class="success-box">
        <spring:message code="navbar.success.message" htmlEscape="true" arguments="${param.successMessage}"/>
    </div>
</c:if>
</body>
</html>
