<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Login - Personal Finance Manager</title>
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
                input[type="password"] {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 4px;
                    box-sizing: border-box;
                }

                input[type="submit"] {
                    background-color: #3498db;
                    color: white;
                    padding: 10px 20px;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                }

                input[type="submit"]:hover {
                    background-color: #2980b9;
                }

                .error {
                    color: #e74c3c;
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
                <h2>Login</h2>
                <c:if test="${not empty error}">
                    <div class="error">
                        <c:out value="${error}" />
                    </div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="error">
                        <c:out value="${message}" />
                    </div>
                </c:if>
                <form action="<%= request.getContextPath() %>/login" method="post">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <input type="submit" value="Login">
                </form>
                <div class="link">
                    Don't have an account? <a href="<%= request.getContextPath() %>/register">Register here</a>
                </div>
            </div>
        </body>

        </html>