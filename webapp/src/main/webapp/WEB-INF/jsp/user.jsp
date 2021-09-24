<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="campus.page.title" htmlEscape="true"/></title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <div class="page-container">
        <div class="big-wrapper" style="max-width: 1100px">
            <div class="user-section-wrapper">
                <div style="display:flex; flex-direction: column; align-items: center" >
                    <h1 style="margin-bottom: 15px"><spring:message code="user.name" htmlEscape="true" arguments="${currentUser.name},${currentUser.surname}"/></h1>
                    <c:if test="${image == null}">
                        <img src="https://pbs.twimg.com/profile_images/758084549821730820/_HYHtD8F.jpg"
                             class="user-section-img"/>
                    </c:if>
                    <c:if test="${image != null}">
                        <img src="<c:url value="/user/profile-image"/>" class="user-section-img"/>
                    </c:if>
                </div>
                <div style="display:flex; flex-direction: column">
                    <p><span style="font-weight: 700"><spring:message code="user.username.title" htmlEscape="true"/></span> <spring:message code="user.username" htmlEscape="true" arguments="${currentUser.username}"/></p>
                    <p><span style="font-weight: 700"><spring:message code="user.email.title" htmlEscape="true"/></span> <spring:message code="user.email" htmlEscape="true" arguments="${currentUser.email}"/></p>
                    <p><span style="font-weight: 700"><spring:message code="user.filenumber.title" htmlEscape="true"/></span> <spring:message code="user.filenumber" htmlEscape="true" arguments="${currentUser.fileNumber.toString()}"/></p>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>