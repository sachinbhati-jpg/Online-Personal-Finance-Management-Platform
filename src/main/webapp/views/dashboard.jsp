<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Dashboard - Personal Finance Manager</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #ecf0f1;
                }

                .header {
                    background-color: #2c3e50;
                    color: white;
                    padding: 20px;
                    text-align: center;
                }

                .header h1 {
                    margin: 0;
                }

                .user-info {
                    text-align: center;
                    margin: 20px 0;
                }

                .nav {
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: center;
                    gap: 20px;
                    margin: 20px 0;
                }

                .nav a {
                    background-color: #3498db;
                    color: white;
                    padding: 12px 24px;
                    text-decoration: none;
                    border-radius: 4px;
                }

                .nav a:hover {
                    background-color: #2980b9;
                }

                .content {
                    max-width: 1200px;
                    margin: 0 auto;
                    padding: 20px;
                }

                .card {
                    background: white;
                    margin-bottom: 20px;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }

                .card h3 {
                    margin-top: 0;
                    color: #2c3e50;
                }

                .stats {
                    display: grid;
                    grid-template-columns: repeat(3, minmax(0, 1fr));
                    gap: 12px;
                    margin-top: 20px;
                }

                .stat {
                    display: block;
                    padding: 16px 10px;
                    text-align: center;
                    color: inherit;
                    text-decoration: none;
                    border-radius: 6px;
                    transition: background-color 140ms ease, transform 140ms ease;
                }

                .stat:hover,
                .stat:focus-visible {
                    background: #f1f6f8;
                    transform: translateY(-2px);
                    outline: 2px solid #2980b9;
                    outline-offset: 2px;
                }

                .stat-value {
                    font-size: 24px;
                    font-weight: bold;
                    color: #2c3e50;
                }

                .stat-label {
                    font-size: 14px;
                    color: #7f8c8d;
                }

                .quick-actions {
                    display: grid;
                    grid-template-columns: repeat(3, minmax(0, 1fr));
                    gap: 12px;
                }

                .quick-action {
                    display: block;
                    padding: 16px;
                    color: #fff;
                    background: #176b5b;
                    text-align: center;
                    text-decoration: none;
                    border-radius: 4px;
                    transition: background-color 140ms ease, transform 140ms ease;
                }

                .quick-action:hover,
                .quick-action:focus-visible {
                    background: #105546;
                    transform: translateY(-2px);
                    outline: 2px solid #213a35;
                    outline-offset: 2px;
                }

                @media (max-width: 640px) {

                    .stats,
                    .quick-actions {
                        grid-template-columns: 1fr;
                    }

                    .content {
                        padding: 12px;
                    }
                }
            </style>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
        </head>

        <body class="dashboard-page">
            <header class="app-header">
                <a class="brand" href="<%= request.getContextPath() %>/dashboard"
                    aria-label="Personal Finance dashboard">
                    <span class="brand-mark">PF</span>
                    <span class="brand-name">Personal Finance</span>
                </a>
                <div class="account-chip">Signed in as <strong>
                        <c:out value="${user.username}" />
                    </strong> ·
                    <c:out value="${user.role}" />
                </div>
            </header>
            <main class="dashboard-main">
                <section class="welcome-band">
                    <div class="welcome-copy">
                        <p class="eyebrow">YOUR MONEY, IN FOCUS</p>
                        <h1>Welcome back,<br>
                            <c:out value="${user.username}" />
                        </h1>
                        <p>A clear view of spending, budgets, and the goals you are working toward.</p>
                    </div>
                    <a class="welcome-action" href="<%= request.getContextPath() %>/expense/form">
                        <small>QUICK ENTRY</small>
                        <strong>Add an expense</strong>
                        <span>Record a purchase</span>
                    </a>
                </section>
                <nav class="nav dashboard-nav" aria-label="Main navigation">
                    <c:if test="${user.role == 'ADMIN'}">
                        <a href="<%= request.getContextPath() %>/admin">Admin tools</a>
                    </c:if>
                    <a href="<%= request.getContextPath() %>/expense/list">Expenses</a>
                    <a href="<%= request.getContextPath() %>/budget/list">Budgets</a>
                    <a href="<%= request.getContextPath() %>/goal/list">Goals</a>
                    <a href="<%= request.getContextPath() %>/password/change">Change password</a>
                    <a href="<%= request.getContextPath() %>/logout">Logout</a>
                </nav>
                <section class="dashboard-section" aria-labelledby="overview-title">
                    <div class="section-heading">
                        <div>
                            <p class="eyebrow">AT A GLANCE</p>
                            <h2 id="overview-title">Your overview</h2>
                        </div>
                        <p>Choose a number to explore the details.</p>
                    </div>
                    <div class="stats">
                        <a class="stat" href="<%= request.getContextPath() %>/expense/list">
                            <div class="stat-value">
                                <c:out value="${totalExpenses}" />
                            </div>
                            <div class="stat-label">Total expenses</div>
                        </a>
                        <a class="stat" href="<%= request.getContextPath() %>/budget/list">
                            <div class="stat-value">
                                <c:out value="${budgetCount}" />
                            </div>
                            <div class="stat-label">Active budgets</div>
                        </a>
                        <a class="stat" href="<%= request.getContextPath() %>/goal/list">
                            <div class="stat-value">
                                <c:out value="${goalCount}" />
                            </div>
                            <div class="stat-label">Savings goals</div>
                        </a>
                    </div>
                </section>
                <section class="dashboard-section" aria-labelledby="actions-title">
                    <div class="section-heading">
                        <div>
                            <p class="eyebrow">MAKE A MOVE</p>
                            <h2 id="actions-title">Quick actions</h2>
                        </div>
                        <p>Small steps keep your plan up to date.</p>
                    </div>
                    <div class="quick-actions">
                        <a class="quick-action" href="<%= request.getContextPath() %>/expense/form">
                            <span class="action-index">01 / SPENDING</span>
                            <strong>Add an expense</strong>
                            <span>Log a payment or purchase</span>
                        </a>
                        <a class="quick-action" href="<%= request.getContextPath() %>/budget/list">
                            <span class="action-index">02 / PLANNING</span>
                            <strong>Create a budget</strong>
                            <span>Set a limit for a category</span>
                        </a>
                        <a class="quick-action" href="<%= request.getContextPath() %>/goal/list">
                            <span class="action-index">03 / SAVING</span>
                            <strong>Set a savings goal</strong>
                            <span>Give your next milestone a date</span>
                        </a>
                    </div>
                </section>
            </main>
        </body>

        </html>