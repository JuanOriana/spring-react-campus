<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus</title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <%@ include file="components/navbar.jsp" %>
    <div class="page-container">
        <div class="big-wrapper" style="width: auto">
            <div class="user-section-wrapper">
                <h1 style="margin-bottom: 15px">
                    <c:out value="${currentUser.name} ${currentUser.surname}"/>
                </h1>
                <img src="https://pbs.twimg.com/profile_images/758084549821730820/_HYHtD8F.jpg"
                     class="user-section-img"/>
                <div style="display:flex; flex-direction: column; margin-top: 40px; font-size: 24px">
                    <p><span style="font-weight: 700">Nombre de usuario:</span>
                        <c:out value="${currentUser.username}"/>
                    </p>
                    <p><span style="font-weight: 700">Email:</span>
                        <c:out value="${currentUser.email}"/>
                    </p>
                    <p><span style="font-weight: 700">Legajo:</span>
                        <c:out value="${currentUser.fileNumber}"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>