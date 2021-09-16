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
        <div class="big-wrapper" style="max-width: 1100px">
            <div class="user-section-wrapper">
                <div style="display:flex; flex-direction: column; align-items: center" >
                    <h1 style="margin-bottom: 15px">${currentUser.name} ${currentUser.surname}</h1>
                    <img src="https://pbs.twimg.com/profile_images/758084549821730820/_HYHtD8F.jpg"
                         class="user-section-img"/>
                </div>
                <div style="display:flex; flex-direction: column">
                    <p><span style="font-weight: 700">Nombre de usuario:</span> ${currentUser.username}</p>
                    <p><span style="font-weight: 700">Email:</span> ${currentUser.email}</p>
                    <p><span style="font-weight: 700">Legajo:</span> ${currentUser.fileNumber}</p>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>