<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Admin - Personal Finance Manager</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    color: #263238;
                    background: #f4f7f6;
                }

                header {
                    background: #263c36;
                    color: white;
                    padding: 16px 24px;
                    display: flex;
                    justify-content: space-between;
                }

                main {
                    max-width: 1200px;
                    margin: 24px auto;
                    padding: 0 20px;
                }

                section {
                    background: white;
                    padding: 18px;
                    margin: 16px 0;
                    border: 1px solid #dce4e0;
                    border-radius: 6px;
                    overflow-x: auto;
                }

                h1,
                h2 {
                    margin-top: 0;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th,
                td {
                    padding: 9px;
                    text-align: left;
                    border-bottom: 1px solid #e4e9e6;
                }

                th {
                    background: #edf3f0;
                }

                form {
                    display: grid;
                    grid-template-columns: repeat(2, minmax(0, 1fr));
                    gap: 12px;
                    align-items: end;
                }

                form button {
                    grid-column: 1 / -1;
                }

                @media (max-width: 640px) {
                    form {
                        grid-template-columns: minmax(0, 1fr);
                    }
                }

                label {
                    display: grid;
                    gap: 5px;
                }

                input {
                    padding: 9px;
                    border: 1px solid #b9c7c0;
                    border-radius: 4px;
                }

                button {
                    padding: 10px 14px;
                    background: #176b57;
                    color: white;
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

                a {
                    color: inherit;
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body>
            <header>
                <h1>Admin</h1>
                <a href="<%= request.getContextPath() %>/password/change">Change password</a>
                <a href="<%= request.getContextPath() %>/logout">Log out</a>
            </header>
            <main>
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

                <section>
                    <h2>Create admin account</h2>
                    <form action="<%= request.getContextPath() %>/admin" method="post">
                        <label>Username<input name="username" maxlength="50" required></label>
                        <label>Email<input name="email" type="email" maxlength="100" required></label>
                        <label>Password<input name="password" type="password" required></label>
                        <label>Confirm password<input name="confirmPassword" type="password" required></label>
                        <button type="submit">Create admin</button>
                    </form>
                </section>

                <section>
                    <h2>Users</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Created</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="account" items="${users}">
                                <tr>
                                    <td>
                                        <c:out value="${account.username}" />
                                    </td>
                                    <td>
                                        <c:out value="${account.email}" />
                                    </td>
                                    <td>
                                        <c:out value="${account.role}" />
                                    </td>
                                    <td>
                                        <c:out value="${account.createdAt}" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </section>

                <section>
                    <h2>Expenses</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Date</th>
                                <th>Category</th>
                                <th>Amount</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="expense" items="${expenses}">
                                <tr>
                                    <td>
                                        <c:out value="${usernamesById[expense.userId]}" />
                                    </td>
                                    <td>
                                        <c:out value="${expense.date}" />
                                    </td>
                                    <td>
                                        <c:out value="${expense.category}" />
                                    </td>
                                    <td>
                                        <c:out value="${expense.amount}" />
                                    </td>
                                    <td>
                                        <c:out value="${expense.description}" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </section>

                <section>
                    <h2>Budgets</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Category</th>
                                <th>Period</th>
                                <th>Limit</th>
                                <th>Spent</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="budget" items="${budgets}">
                                <tr>
                                    <td>
                                        <c:out value="${usernamesById[budget.userId]}" />
                                    </td>
                                    <td>
                                        <c:out value="${budget.category}" />
                                    </td>
                                    <td>
                                        <c:out value="${budget.period}" />
                                    </td>
                                    <td>
                                        <c:out value="${budget.limitAmount}" />
                                    </td>
                                    <td>
                                        <c:out value="${budget.spentAmount}" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </section>

                <section>
                    <h2>Goals</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>User</th>
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
                                        <c:out value="${usernamesById[goal.userId]}" />
                                    </td>
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
                </section>
            </main>
        </body>

        </html>