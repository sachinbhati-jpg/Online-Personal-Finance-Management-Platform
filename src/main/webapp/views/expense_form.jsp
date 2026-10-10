<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Add Expense</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background: #f4f4f4;
                    padding: 20px;
                }

                .container {
                    max-width: 450px;
                    margin: 40px auto;
                    background: #fff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, .1);
                }

                .form-group {
                    margin-bottom: 15px;
                }

                label {
                    display: block;
                    margin-bottom: 5px;
                    font-weight: bold;
                }

                input,
                select {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 4px;
                    box-sizing: border-box;
                }

                input[type=submit] {
                    background: #3498db;
                    color: #fff;
                    border: none;
                    cursor: pointer;
                }

                .error {
                    color: #e74c3c;
                    margin-bottom: 15px;
                }

                .link a {
                    color: #3498db;
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body class="entry-page">
            <div class="container">
                <h2>Add Expense</h2>
                <c:if test="${not empty error}">
                    <div class="error">
                        <c:out value="${error}" />
                    </div>
                </c:if>
                <form action="<%= request.getContextPath() %>/expense" method="post">
                    <div class="form-group">
                        <label>Date:</label>
                        <input type="date" name="date" required>
                    </div>
                    <div class="form-group">
                        <label>Category:</label>
                        <select name="category" required>
                            <option value="">--Select--</option>
                            <option>Food</option>
                            <option>Transport</option>
                            <option>Bills</option>
                            <option>Entertainment</option>
                            <option>Shopping</option>
                            <option>Other</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Amount:</label>
                        <input type="number" name="amount" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label>Description:</label>
                        <input type="text" name="description" maxlength="500">
                    </div>
                    <input type="submit" value="Save Expense">
                </form>
                <p class="link"><a href="<%= request.getContextPath() %>/expense/list">Back to list</a></p>
            </div>
        </body>

        </html>