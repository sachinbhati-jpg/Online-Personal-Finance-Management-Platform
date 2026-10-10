<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>My Expenses</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background: #f4f4f4;
                    padding: 20px;
                }

                .container {
                    max-width: 900px;
                    margin: 0 auto;
                    background: #fff;
                    padding: 20px;
                    border-radius: 8px;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 15px;
                }

                th,
                td {
                    padding: 10px;
                    border-bottom: 1px solid #ddd;
                    text-align: left;
                }

                th {
                    background: #2c3e50;
                    color: #fff;
                }

                .btn {
                    display: inline-block;
                    background: #3498db;
                    color: #fff;
                    padding: 8px 16px;
                    text-decoration: none;
                    border-radius: 4px;
                }

                .del {
                    background: #e74c3c;
                    border: none;
                    color: #fff;
                    padding: 6px 12px;
                    border-radius: 4px;
                    cursor: pointer;
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body>
            <div class="container">
                <h2>My Expenses</h2>
                <c:if test="${not empty error}">
                    <p class="error">
                        <c:out value="${error}" />
                    </p>
                </c:if>
                <p><a class="btn" href="<%= request.getContextPath() %>/expense/form">+ Add Expense</a>
                    <a class="btn" href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
                </p>
                <c:choose>
                    <c:when test="${empty expenses}">
                        <p>No expenses recorded yet.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th>Date</th>
                                <th>Category</th>
                                <th>Amount</th>
                                <th>Description</th>
                                <th></th>
                            </tr>
                            <c:forEach var="e" items="${expenses}">
                                <tr>
                                    <td>
                                        <c:out value="${e.date}" />
                                    </td>
                                    <td>
                                        <c:out value="${e.category}" />
                                    </td>
                                    <td>
                                        <c:out value="${e.amount}" />
                                    </td>
                                    <td>
                                        <c:out value="${e.description}" />
                                    </td>
                                    <td>
                                        <form action="<%= request.getContextPath() %>/expense" method="post"
                                            style="margin:0">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id" value="${e.id}">
                                            <input type="submit" class="del" value="Delete"
                                                onclick="return confirm('Delete this expense?');">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </body>

        </html>