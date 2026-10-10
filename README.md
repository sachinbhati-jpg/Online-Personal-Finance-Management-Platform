# Personal Finance Manager

## Requirements

- Java 11 or newer
- MySQL 8
- Apache Tomcat 9 (the application uses the `javax.servlet` API)

## Database

Run `src/main/resources/db/schema.sql` against MySQL. Configure these environment variables for the application process:

- `FINANCE_DB_URL`: JDBC URL; defaults to the local `finance_db` database.
- `FINANCE_DB_USER`: database account; defaults to `finance_user`.
- `FINANCE_DB_PASSWORD`: database password; there is no password default.

The database account needs privileges on `finance_db`. Do not commit production credentials to the repository.

## Build and deploy

From this directory, run `mvn test` to run the JUnit unit tests, then `mvn clean package` to build the WAR. The current seven tests cover password hashing and authentication behavior and do not require MySQL. Deploy `target/personal-finance-web.war` to a Tomcat 9 server and set the database environment variables in the Tomcat service environment before starting it. The application context path is `/personal-finance-web` when deployed under the generated WAR name.