# LicensePlateManagementSystem
# License Plate Management System

This project simulates a license plate management system using Java, socket programming, and multi-threading. Inspired by Mohammad's comprehensive design, the system allows users to register, log in, request license plates, and transfer them, all while connected to a central server.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Client Capabilities](#client-capabilities)
- [Server Capabilities](#server-capabilities)
- [License](#license)

## Introduction
Mohammad, who recently received his driver's license and bought a national car with his savings, has designed a comprehensive system for license plate allocation while waiting in line at the license plate exchange center. This project implements Mohammad's design using Java's socket programming and threading capabilities.

## Features
- User registration and login
- Unique license plate allocation
- License plate transfer between users
- Admin commands for managing users and plates
- Persistent data storage

## Requirements
- Java Development Kit (JDK) 8 or higher
- An IDE such as IntelliJ IDEA or Eclipse
- Network setup to allow socket communication between server and clients

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/LicensePlateManagementSystem.git
   ```
2. Navigate to the project directory:
   ```bash
   cd LicensePlateManagementSystem
   ```
3. Open the project in your IDE.

## Usage
1. Start the server:
   ```bash
   java -cp out/production/LicensePlateManagementSystem server.Main
   ```
2. Start a client instance:
   ```bash
   java -cp out/production/LicensePlateManagementSystem client.Main
   ```

## Client Capabilities

### Register
Users can register with the system using the following command:
```
NewUser [first name] [last name] [id]
```
- `[id]` must be unique.

### Login
Users can log in using their ID:
```
Login [id]
```
- Only one active session per user is allowed.

### Logout
Users can log out:
```
Logout
```

### Request Plate
Logged-in users can request a new license plate:
```
GetPlate
```
- Each user can only have one plate at a time.

### Transfer Plate
Users can transfer their plate to another user:
```
TransferPlate [id]
```
- The recipient must not already have a plate.

## Server Capabilities

### View Online Users
Admin can view all online users:
```
OnlineUsers
```

### View Registered Users
Admin can view all registered users:
```
RegisteredUsers
```

### View Next Plate
Admin can view the next plate to be issued:
```
next
```

## Additional Features
- Different scopes for plates (e.g., regular, taxi, government)
- Reservation of special plates
- Persistent storage of user and plate data
