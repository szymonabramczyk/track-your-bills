# Track Your Bills

The goal of the application is to help users manage their home budget by registering transactions, analyzing finances, and managing budgets and savings goals.

<img width="417" alt="image" src="https://github.com/user-attachments/assets/84708d29-12b0-4bb3-8587-75ac43ebf926" />

## Features

1. **User Registration**  
   - Users can create accounts.
2. **Transaction Management**  
   - Add, edit, and delete transactions (income and expenses).  
   - Categorize transactions.
3. **Budgeting**  
   - Create monthly budgets.  
   - Notifications when budgets are exceeded.
4. **Savings Goals**  
   - Define savings goals (e.g., vacations, a new car).
5. **Reporting and Filtering Transactions**  
   - Filter transactions by date, amount, category, and type.  
   - Generate monthly reports showing income, expenses, and category summaries.

## Technologies

### Backend
- **Java 17** with **Spring Boot 3**: REST API development.
- **Spring Data JPA** (Hibernate): ORM and database management.
- **PostgreSQL**: Relational database for storing user, transaction, and budget data.
- **Spring Security**: Authentication and authorization with **JWT** tokens.
- **Lombok**: Simplifies repetitive code (e.g., getters, setters, constructors).

### Frontend
- **React.js**: Building the user interface.
- **Axios**: HTTP requests for backend communication.
- **Bootstrap**: Responsive styling framework.
- **Vite JS**: Development environment for React.

## Application Architecture

- **Backend**: REST API endpoints for managing users, transactions, budgets, and reports.  
- **Frontend**: Interactive and dynamic user interface.  
- **Database**: Schema includes tables for users, transactions, categories, budgets, and savings goals.

## How It Works

1. **User Registration and Login**  
   Users can create accounts and log in to access the application.

2. **Adding Transactions**  
   Users can add transactions, assign them to categories, and filter them based on specific criteria.

3. **Budget Management**  
   Create monthly budgets with feedback on whether limits are exceeded.

4. **Savings Goals**  
   Define and track savings goals.

5. **Reporting**  
   Generate financial reports with customizable views, such as by category or transaction type.

## Future Development

- Add email confirmation during registration.
- Enable descriptions/notes for transactions and categories.
- Budget reporting for specific categories.
- Implement charts using **Chart.js**.
- Support multiple languages.
- Advanced budget analysis using AI models (e.g., predicting future expenses).

