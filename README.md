# BoilerFruit: A Java-Based Client-Server Market Simulation

## Overview

**BoilerFruit** is a client-server market simulation application developed as part of Purdue University's CS180 Project 5. The project demonstrates fundamental concepts in Java programming, including object-oriented design, GUI development, and client-server communication.

## Key Features

- **Client-Server Architecture**: Utilizes Java's networking capabilities to establish communication between client and server.
- **Graphical User Interface (GUI)**: Developed using JavaFX to provide an intuitive user experience.
- **CSV File Handling**: Employs the OpenCSV library for reading and writing CSV files, facilitating data persistence.
- **Multithreading**: Implements threads to handle multiple client connections concurrently.

## Technologies Used

- **Programming Language**: Java
- **IDE**: IntelliJ IDEA 2022.3.2
- **SDK**: Amazon Corretto 19
- **Libraries**: OpenCSV 5.5
- **Version Control**: Git

## Contributors

- **Ethan Lee**, **Bao Phan** both contributed equally.

## Getting Started

To run the application locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/lee3291/BoilerFruit.git
   ```
2. Navigate to the project directory:
   ```bash
   cd BoilerFruit
   ```
3. Compile and run the server:
   ```bash
   javac Server.java
   java Server
   ```
4. Compile and run the client:
   ```bash
   javac ClientGUI.java
   java ClientGUI
   ```
   - When prompted, enter the server's IP address. For local testing, press Enter without typing anything.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
