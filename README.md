# ExpenseTrackerV2 Server

This repository contains the server-side code for ExpenseTrackerV2, a web application that integrates an AI-driven chatbot to act as a virtual financial advisor, helping users manage and analyze their expenses more effectively.

## Features

1. **User Authentication**
   - Secure user authentication process that generates a JWT for session handling after successful login.

2. **Expenses Management**
   - Comprehensive CRUD operations for managing expenses, categories, and monthly records.

3. **AI-Driven Financial Advisor**
   - Utilizes OpenAI's GPT model to provide financial advice based on user's specific queries about expenses.

## Prerequisites

Before running the server, ensure you have the following environment variables configured:

| Variable            | Description                                  |
|---------------------|----------------------------------------------|
| `JWT_KEY`           | Secret key for signing JWT tokens            |
| `DB_URL`            | MySQL database connection URL               |
| `DB_Name`           | Database name                                |
| `DB_PWD`            | Database password                            |
| `USERNAME`          | Admin username                               |
| `USERPWD`           | Admin password                               |
| `ExpenseTrackerKey` | OpenAI API key for accessing chatbot features|

## Setup and Installation

### 1. Clone the Repository
git clone https://github.com/wzNaji/Expense-TrackerV2.git

### 2. Environment Configuration
Create a `.env` file in the root directory and populate it with necessary values.

### 3. Database Setup
Ensure you have MySQL installed and running. Use the provided DDL script to set up the database schema: See directory script -> DDL

### 4. Build and Run
Navigate to the project directory and execute the following commands:
mvn clean install java -jar target/expensetrackerv2-0.0.1-SNAPSHOT.jar

## API Endpoints

### Authentication
- **POST** `/login` - Authenticates user and returns a JWT.

### Category Management
- **POST** `/api/category/create` - Creates a new category.
- **DELETE** `/api/category/delete/{id}` - Deletes a category by ID.
- **GET** `/api/category/categoryList` - Retrieves a list of all categories.

### Expense Management
- **POST** `/api/expense/create` - Creates a new expense.
- **DELETE** `/api/expense/delete/{id}` - Deletes an expense by ID.
- **GET** `/api/expense/{expenseId}` - Retrieves details of a specific expense.

### Month Management
- **POST** `/api/month/create` - Creates a record for a new month.
- **DELETE** `/api/month/delete/{id}` - Deletes a month record by ID.
- **GET** `/api/month/{monthId}` - Retrieves details of a specific month.
- **GET** `/api/month/monthList` - Retrieves a list of all months.
- **GET** `/api/month/expenseList/{monthId}` - Retrieves all expenses for a specified month.

### AI Chatbot Interaction
- **GET** `/api/bot` - Interacts with the AI chatbot using a message parameter.

## Security

This application uses Spring Security to manage authentication and protect API endpoints. Ensure that you handle credentials and JWT tokens securely.

**Note:** It is essential to configure the OpenAI API key correctly to enable the AI chatbot functionality.


