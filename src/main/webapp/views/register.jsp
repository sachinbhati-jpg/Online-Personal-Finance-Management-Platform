<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Register - Personal Finance Manager</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 20px;
                }

                .container {
                    max-width: 400px;
                    margin: 50px auto;
                    background: white;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }

                h2 {
                    text-align: center;
                    color: #2c3e50;
                }

                .form-group {
                    margin-bottom: 15px;
                }

                label {
                    display: block;
                    margin-bottom: 5px;
                    font-weight: bold;
                }

                input[type="text"],
                input[type="email"],
                input[type="password"] {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 4px;
                    box-sizing: border-box;
                }

                select {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 4px;
                    box-sizing: border-box;
                }

                input[type="submit"] {
                    background-color: #2ecc71;
                    color: white;
                    padding: 10px 20px;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                }

                input[type="submit"]:hover {
                    background-color: #27ae60;
                }

                .error {
                    color: #e74c3c;
                    text-align: center;
                    margin-bottom: 15px;
                }

                .success {
                    color: #2ecc71;
                    text-align: center;
                    margin-bottom: 15px;
                }

                .link {
                    text-align: center;
                    margin-top: 15px;
                }

                .link a {
                    color: #3498db;
                    text-decoration: none;
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body class="auth-page">
            <div class="container">
                <h2>Register</h2>
                <c:if test="${not empty error}">
                    <div class="error">
                        <c:out value="${error}" />
                    </div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="success">
                        <c:out value="${message}" />
                    </div>
                </c:if>
                <form action="<%= request.getContextPath() %>/register" method="post">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    <input type="submit" value="Register">
                </form>
                <div class="link">
                    Already have an account? <a href="<%= request.getContextPath() %>/login">Login
                        here</a>
                </div>
            </div>
        </body>

        </html>