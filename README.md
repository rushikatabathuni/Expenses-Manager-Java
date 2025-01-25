# Personal Finance Tracker

This is a **Personal Finance Tracker** application designed to help you manage your expenses, income, and track your bank balance. It allows users to:
- Add and view expenses and incomes
- Track total expenses, total income, and bank balance
- Save data to CSV files for persistence
- View detailed lists of expenses and incomes

## Features

- **Add Expenses**: Track your daily, weekly, or monthly expenses by specifying a name and amount.
- **Add Income**: Track your income from various sources with a description and amount.
- **View Financial Summary**: View your current bank balance, total expenses, and total income.
- **Persistent Data**: All expenses, incomes, and bank balance are saved in CSV files to retain data across application sessions.
- **Expense & Income Management**: View a list of all expenses, income sources, and their amounts.

## Technologies Used

- **Java**: The application is built using Java with `Swing` for the graphical user interface (GUI).
- **CSV Files**: Used for storing expenses, incomes, and bank balance data.
- **Swing GUI**: Provides an interactive interface for adding expenses, incomes, and viewing summaries.

## Getting Started

### Prerequisites

To run this project, you need to have the following installed:
- Java Development Kit (JDK) version 8 or higher.
- An IDE or text editor (e.g., IntelliJ IDEA, Eclipse, or Visual Studio Code).

### Installing

1. Clone the repository to your local machine using the following command:
   
   `git clone https://github.com/your-username/personal-finance-tracker.git`
2.Navigate to the project directory:
   `cd personal-finance-tracker`
3. Compile and run the `MainFrame.java` file:
   `javac MainFrame.java
    java MainFrame`




## Import to your IDE: 

### Part 3: Usage and File Structure


## Usage

1. **Launch the Application**: Run the application using the command above or from your IDE.
2. **Adding Expenses**: 
   - Enter the name of the expense in the "Name" field.
   - Enter the amount in the "Amount" field.
   - Click the "Add Expense" button to add the expense.
   - The expense will be subtracted from your bank balance.

3. **Adding Income**: 
   - Enter the income source in the "Source" field.
   - Enter the amount in the "Amount" field.
   - Click the "Add Income" button to add the income.
   - The income will be added to your bank balance.

4. **Viewing Financial Summary**: 
   - The application displays your current **bank balance**, **total expenses**, and **total income** in the summary section.
   
5. **Viewing Expenses and Incomes**: 
   - Click on the "View Expenses" button to see a list of all expenses with their respective amounts.
   - Click on the "View Incomes" button to see a list of all incomes with their respective sources and amounts.

6. **Persistent Data**: 
   - The expenses, incomes, and bank balance are stored in CSV files (`expenses.csv`, `incomes.csv`, `bank_balance.csv`).
   - This ensures that your data is saved even after closing the application.

### File Structure



### CSV File Formats

- **expenses.csv**: Each line contains an expense name and amount, separated by a comma.

- **incomes.csv**: Each line contains an income source and amount, separated by a comma.

- **bank_balance.csv**: The current bank balance, stored as a single number.

