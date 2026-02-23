# Electricity Billing System
A console-based Java application for managing electricity billing operations. No GUI/Swing — fully terminal driven using Scanner input.

---

## 📁 Project Structure

```
electricityBilling/
├── conn.java               # PostgreSQL database connection
├── ConsoleHelper.java      # Console formatting utility
├── login.java              # Login with hardcoded + DB authentication
├── MainMenu.java           # Main menu (entry point)
├── new_customer.java       # Add a new customer
├── customer_details.java   # View / search customers
├── calculate_bill.java     # Calculate and store electricity bill
├── generate_bill.java      # Generate formatted bill receipt
├── LastBill.java           # View bill history for a meter
├── pay_bill.java           # Pay bill / mark as PAID
├── update_customer.java    # Update existing customer details
└── delete_customer.java    # Delete customer and their bills
```

---

## ⚙️ Prerequisites

- Java JDK 8 or above
- PostgreSQL database
- PostgreSQL JDBC Driver (`postgresql-xx.jar`) on classpath

---

## 🗄️ Database Setup

**1. Create the database:**
```sql
CREATE DATABASE electricity;
```

**2. Connect to it and run the following:**
```sql
-- Customer table
CREATE TABLE emp (
    name         VARCHAR(100),
    meter_number VARCHAR(50) PRIMARY KEY,
    address      VARCHAR(200),
    state        VARCHAR(100),
    city         VARCHAR(100),
    email        VARCHAR(100),
    phone        VARCHAR(15)
);

-- Bill table
CREATE TABLE bill (
    id             SERIAL PRIMARY KEY,
    meter_number   VARCHAR(50),
    month          VARCHAR(20),
    units          INT,
    amount         INT,
    bill_date      DATE,
    payment_status VARCHAR(20) DEFAULT 'PENDING'
);

-- Tax table
CREATE TABLE tax (
    meter_location VARCHAR(100),
    meter_type     VARCHAR(50),
    phase_code     VARCHAR(50),
    bill_type      VARCHAR(50),
    days           INT
);

-- Login table
CREATE TABLE login (
    username VARCHAR(50),
    password VARCHAR(50)
);

-- Optional: insert a DB user
INSERT INTO login VALUES ('suraj', 'suraj@123');
```

---

## 🔐 Login Credentials

The app supports two ways to log in:

| Method | Username | Password |
|--------|----------|----------|
| Hardcoded (always works) | `suraj` | `suraj@123` |
| Database login table | any row in `login` table | matching password |

> You get **3 attempts** before the app exits automatically.

---

## ▶️ How to Run

**1. Compile all classes:**
```bash
javac -cp .;postgresql.jar electricityBilling/*.java
```

**2. Run the application:**
```bash
java -cp .;postgresql.jar electricityBilling.MainMenu
```

> On Linux/Mac, replace `;` with `:` in the classpath.

---

## 📋 Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | Login | Secure login with 3-attempt limit |
| 2 | New Customer | Add customer with name, meter, address, email, phone |
| 3 | Customer Details | View all customers or search by meter number |
| 4 | Calculate Bill | Enter units → auto-calculates and stores bill |
| 5 | Pay Bill | View pending bills, mark as PAID or get Paytm link |
| 6 | Last Bill | Full bill history for any meter number |
| 7 | Generate Bill | Formatted bill receipt with customer + tax details |
| 8 | Update Customer | Edit existing customer info (keeps old value if left blank) |
| 9 | Delete Customer | Remove customer and all associated bills |

---

## 💡 Bill Calculation Formula

```
Energy Charge  = Units × 7
Fixed Charge   = Rs. 50
Meter Rent     = Rs. 12
Service Tax    = Rs. 102
Other Charges  = Rs. 20
Surcharge      = Rs. 50
─────────────────────────
TOTAL          = Energy Charge + all above charges
```

---

## 🗃️ Database Configuration

Edit `conn.java` to match your PostgreSQL setup:

```java
c = DriverManager.getConnection(
    "jdbc:postgresql://localhost:5432/electricity",
    "postgres",   // your username
    "root"        // your password
);
```

---

## 👤 Author

**Suraj**  
Electricity Billing System — Console Edition
