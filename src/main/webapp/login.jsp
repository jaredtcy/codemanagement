<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Assignment 2</title>
</head>
<body>
    <h2>Login</h2>
    <% String errorMessage = (String) request.getAttribute("error"); %>
    <% if (errorMessage != null) { %>
        <div style="color: red;"><%= errorMessage %></div>
    <% } %>
    <form action="login" method="post">
        <input type="text" id="username" name="username" placeholder="Username" required>
        <input type="password" id="password" name="password" placeholder="Password" required>
        <input type="submit" value="Login">
    </form>
</body>
</html>
