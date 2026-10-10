<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Goals - Personal Finance Manager</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background: #f4f4f4;
                    margin: 0;
                    padding: 20px;
                }

                main {
                    max-width: 960px;
                    margin: 0 auto;
                }

                section {
                    background: #fff;
                    margin-bottom: 20px;
                    padding: 20px;
                    border-radius: 6px;
                }

                form {
                    display: flex;
                    flex-wrap: wrap;
                    align-items: end;
                    gap: 12px;
                }

                label {
                    display: grid;
                    gap: 6px;
                    flex: 1 1 180px;
                }

                input,
                button {
                    box-sizing: border-box;
                    min-height: 40px;
                    padding: 8px;
                }

                button,
                .link {
                    background: #176b5b;
                    color: #fff;
                    border: 0;
                    padding: 10px 16px;
                    text-decoration: none;
                }

                .error {
                    color: #a21d27;
                }

                .table-wrap {
                    overflow-x: auto;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th,
                td {
                    padding: 10px;
                    border-bottom: 1px solid #ddd;
                    text-align: left;
                }

                th {
                    background: #213a35;
                    color: #fff;
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body>
            <main>
                <h1>Goals</h1>
                <p><a class="link" href="<%= request.getContextPath() %>/dashboard">Dashboard</a></p>
                <section>
                    <h2>New goal</h2>
                    <c:if test="${not empty error}">
                        <p class="error">
                            <c:out value="${error}" />
                        </p>
                    </c:if>
                    <form action="<%= request.getContextPath() %>/goal/list" method="post">
                        <label>Name<input name="name" maxlength="100" required></label>
                        <label>Category<input name="category" maxlength="50"></label>
                        <label>Target amount<input name="targetAmount" type="number" min="0.01" step="0.01"
                                required></label>
                        <label>Deadline<input name="deadline" type="date" required></label>
                        <button type="submit">Save goal</button>
                    </form>
                </section>
                <section>
                    <h2>Your goals</h2>
                    <c:choose>
                        <c:when test="${empty goals}">
                            <p>No goals recorded yet.</p>
                        </c:when>
                        <c:otherwise>
                            <div class="table-wrap">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Category</th>
                                            <th>Target</th>
                                            <th>Saved</th>
                                            <th>Deadline</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="goal" items="${goals}">
                                            <tr>
                                                <td>
                                                    <c:out value="${goal.name}" />
                                                </td>
                                                <td>
                                                    <c:out value="${goal.category}" />
                                                </td>
                                                <td>
                                                    <c:out value="${goal.targetAmount}" />
                                                </td>
                                                <td>
                                                    <c:out value="${goal.currentAmount}" />
                                                </td>
                                                <td>
                                                    <c:out value="${goal.deadline}" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </section>
            </main>
        </body>

        </html>