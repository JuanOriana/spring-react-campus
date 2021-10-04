<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="campus.page.title"/></title>
    <c:import url="config/general-head.jsp"/>

</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <div class="page-container">
        <div class="big-wrapper" style="max-width: 800px">
            <div class="user-section-wrapper">
                <div style="display:flex; flex-direction: column; align-items: center" >
                    <h1 style="margin-bottom: 15px"><spring:message code="user.name" htmlEscape="true" arguments="${currentUser.name},${currentUser.surname}"/></h1>
                    <c:if test="${currentUser.image == null}">
                        <img src="<c:url value="/resources/images/default-user-image.png"/>" class="user-section-img"/>
                    </c:if>
                    <c:if test="${currentUser.image != null}">
                        <img src="<c:url value="/user/profile-image/${currentUser.userId}"/>" class="user-section-img"/>
                    </c:if>
                    <form:form modelAttribute="userProfileForm" method="post" enctype="multipart/form-data"
                               acceptCharset="utf-8" cssStyle="margin: 30px 0; display: flex; padding:10px;
                               flex-direction: column; border: 2px solid #2EC4B6; border-radius:12px">
                        <form:label path="image" class="form-label" cssStyle="margin: 0">
                            <spring:message code="user.insert.image.title"/>
                        </form:label>
                        <form:input type="file" path="image" accept="image/png, image/jpeg" />
                        <form:errors path="image" element="p" cssStyle="color:red;margin-left: 10px"/>
                        <button style="border-radius:4px; padding:4px; font-size: 18px; margin-top: 5px"><spring:message code="user.insert.image.button"/></button>
                    </form:form>
                </div>
                <div style="display:flex; flex-direction: column">
                    <p><span style="font-weight: 700"><spring:message code="user.username.title"/></span>
                        <spring:message code="user.username" htmlEscape="true" arguments="${currentUser.username}"/>
                    </p>
                    <p><span style="font-weight: 700"><spring:message code="user.email.title"/></span>
                        <spring:message code="user.email" htmlEscape="true" arguments="${currentUser.email}"/>
                    </p>
                    <p><span style="font-weight: 700"><spring:message code="user.filenumber.title" /></span>
                        <spring:message code="user.filenumber" htmlEscape="true"
                                        arguments="${currentUser.fileNumber.toString()}"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>