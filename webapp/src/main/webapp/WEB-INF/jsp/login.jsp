<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Campus - Login</title>
    <c:import url="config/generalHead.jsp"/>

</head>
<body>
<div class="page-organizer">
    <div class="page-container" style="justify-content: center">
        <form class="login-wrapper" method="post">
            <h1 class="section-heading">Ingresar</h1>
            <label for="username" class="login-label">Username</label>
            <input type="text" id="username" name="username" class="login-input"/>
            <label for="password" class="login-label">Password</label>
            <input type="password" id="password" name="password" class="login-input"/>
            <button class="login-button">Hola xd</button>
        </form>
    </div>
    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>