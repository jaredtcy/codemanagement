# Inventory Management System

This web application is an inventory management system built with Java, JSP, and MySQL.

## Features
- Login page with authentication
- Inventory listing with options to add, delete, and update quantities
- Chatbot feature for customer support
- Basic error handling and validation

## Setup

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/inventory-management.git

   
2.  Set up a MySQL database with the following credentials:

- Database name: inventory
- Table: inventory
- Columns: brand, size, quantity

You can use this SQL query to create the table:

	```bash
	CREATE DATABASE inventory;
	
	USE inventory;
	
	CREATE TABLE inventory (
	    brand VARCHAR(255),
	    size INT,
	    quantity INT
	);
