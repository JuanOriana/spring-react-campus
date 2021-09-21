<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title><spring:message code="login.page.title" htmlEscape="true"/></title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <div class="page-container" style="justify-content: center">
        <form class="login-wrapper" method="post">
            <h1 class="section-heading"><spring:message code="login.section-heading.title" htmlEscape="true"/></h1>
            <label for="username" class="login-label"><spring:message code="login.label.user" htmlEscape="true"/></label>
            <input type="text" id="username" name="username" class="login-input"/>
            <label for="password" class="login-label"><spring:message code="login.label.password" htmlEscape="true"/></label>
            <input type="password" id="password" name="password" class="login-input"/>
            <div style="display: flex;align-items: center; margin: 10px 0;">
                <input type="checkbox" name="rememberMe" id="remember-me"/>
                <label for="remember-me" style="color: #176961; margin-left: 5px"><spring:message code="login.label.remeber.me" htmlEscape="true"/></label>
            </div>
            <c:if test="${param.error != null}">
                <p style="color:red; text-align: center"><spring:message code="login.bad.credentials" htmlEscape="true"/></p>
            </c:if>
            <button class="login-button"><spring:message code="login.button.login" htmlEscape="true"/></button>
        </form>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>