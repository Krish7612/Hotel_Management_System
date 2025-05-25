# Hotel Management System

## Overview

This is a Java-based Hotel Management System (HMS) that allows you to manage guests, rooms, and bookings. The system provides a graphical user interface (GUI) for easy interaction and performs database operations using JDBC with a MySQL backend.

---

## Features

- Add, view, and manage guests
- Add, view, and manage rooms
- Book rooms with check-in and check-out dates
- Update room availability status automatically upon booking

---

## Technologies Used

- Java (JDK 8+)
- Swing (for GUI)
- MySQL (Database)
- JDBC (Database connectivity)
- Maven or manual setup for dependencies

---

## Setup Instructions

### Prerequisites

- Java Development Kit (JDK)
- MySQL Server installed and running
- MySQL JDBC Driver (`mysql-connector-java`)
- An IDE such as IntelliJ IDEA or Eclipse (optional but recommended)

### Database Setup

1. Start your MySQL server.
2. Create the database and tables by executing the following SQL commands:

```sql
CREATE DATABASE hms;

USE hms;

CREATE TABLE guests (
    guest_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info VARCHAR(255) NOT NULL
);

CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_type VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status ENUM('available', 'booked') DEFAULT 'available'
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_id INT,
    room_id INT,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    FOREIGN KEY (guest_id) REFERENCES guests(guest_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);
