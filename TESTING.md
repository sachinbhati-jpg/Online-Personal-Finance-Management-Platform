# Evaluator Walkthrough

## Before starting

1. Install Java 11 or newer, Maven, MySQL 8, and Tomcat 9.
2. Run `src/main/resources/db/schema.sql` in MySQL. The script creates the schema and seeds `admin` / `admin123` and `user` / `user123`. Rerunning the script refreshes those demo accounts' password hashes. Do not use these demo credentials in production.
3. Set `FINANCE_DB_URL`, `FINANCE_DB_USER`, and `FINANCE_DB_PASSWORD` in the environment used by Tomcat. The default URL targets local MySQL database `finance_db`; the default account is `finance_user` with an empty password.
4. From the project directory, run `mvn test`. The seven JUnit tests cover password hashing and authentication service behavior without requiring a database.
5. Run `mvn clean package`. Confirm `BUILD SUCCESS` and that `target/personal-finance-web.war` exists.
6. Deploy the WAR to Tomcat 9. With the generated WAR filename, the context path is `/personal-finance-web`.

## Browser checks

1. Open `/personal-finance-web/`. Confirm it redirects to the login screen rather than returning HTTP 405.
2. Open the registration page, create an account, confirm mismatched passwords are rejected, then log in with the new account.
3. Add an expense. Confirm it appears in the expense list and the dashboard total changes.
4. Add a budget and a goal. Confirm each appears in its list and the dashboard counts update.
5. Log out. Use the browser back button, then request the dashboard or expense list again; protected pages should redirect to login.
6. Log in with the seeded `admin` / `admin123` and `user` / `user123` accounts if the database seed was run.

## Data-isolation check

Create records under two accounts. Attempt to update or delete a record using the other account's session and the record ID. The scoped repository operations should not return, update, or delete another user's data.