<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Change Password - Personal Finance Manager</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    padding: 24px;
                    color: #263238;
                    background: #f4f7f6;
                }

                main {
                    max-width: 480px;
                    margin: 36px auto;
                    padding: 24px;
                    background: white;
                    border: 1px solid #dce4e0;
                    border-radius: 6px;
                }

                h1 {
                    margin-top: 0;
                }

                form {
                    display: grid;
                    gap: 14px;
                }

                label {
                    display: grid;
                    gap: 6px;
                }

                input {
                    padding: 10px;
                    border: 1px solid #b9c7c0;
                    border-radius: 4px;
                }

                button {
                    padding: 11px 14px;
                    color: white;
                    background: #176b57;
                    border: 0;
                    border-radius: 4px;
                    cursor: pointer;
                }

                .notice {
                    color: #176b57;
                }

                .error {
                    color: #a32626;
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body>
            <main>
                <h1>Change password</h1>
                <c:if test="${not empty notice}">
                    <p class="notice">
                        <c:out value="${notice}" />
                    </p>
                </c:if>
                <c:if test="${not empty error}">
                    <p class="error">
                        <c:out value="${error}" />
                    </p>
                </c:if>
                <form action="<%= request.getContextPath() %>/password/change" method="post">
                    <label>Current password<input type="password" name="currentPassword" autocomplete="current-password"
                            required></label>
                    <label>New password<input type="password" name="newPassword" minlength="8"
                            autocomplete="new-password" required></label>
                    <label>Confirm new password<input type="password" name="confirmPassword" minlength="8"
                            autocomplete="new-password" required></label>
                    <button type="submit">Update password</button>
                </form>
                <c:choose>
                    <c:when test="${sessionScope.user.role == 'ADMIN'}">
                        <p><a href="<%= request.getContextPath() %>/admin">Back to admin</a></p>
                    </c:when>
                    <c:otherwise>
                        <p><a href="<%= request.getContextPath() %>/dashboard">Back to dashboard</a></p>
                    </c:otherwise>
                </c:choose>
            </main>
        </body>

        </html>