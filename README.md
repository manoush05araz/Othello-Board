# ♟️ Othello Game (Java)
A full-featured implementation of the classic Othello (Reversi) board game built in Java with a graphical user interface and a strong emphasis on software architecture and design patterns.

---

## ✨ Features
- Human vs Human and Human vs Computer gameplay
- Interactive GUI-based board
- Configurable player strategies
- Undo and redo moves with full move history
- Save and load game state from file
- Restart game functionality

---

## 🏗️ Architecture
The project follows the Model–View–Controller (MVC) architecture to ensure clean separation of concerns:

- Model 
Core game logic, board state, move design, and validation rules.
- View
JavaFX-based graphical user interface for rendering the board and handling user interaction.
- Controller
Coordinates game flow, user actions, and communication between the model and view.
This structure improves maintainability, testability, and extensibility.

--- 

## 🧩 Design Patterns Used
Strategy Pattern
Encapsulates different player behaviors (Human, Random, Greedy), allowing strategies to be swapped dynamically.
Command Pattern
Implements undo/redo functionality by storing move commands and maintaining a history stack.
Visitor Pattern
Separates board traversal and operations from the board structure itself, enabling cleaner extensions.

---

## 🛠️ Technologies
Java
JavaFX
Object-Oriented Programming (OOP)
MVC Architecture
Design Patterns (Strategy, Command, Visitor)

---

## 📌 Notes
This project was developed as part of a software design course, with a focus on clean architecture, design pattern application, and readable, extensible code rather than minimal implementation.

## 🚀 Getting Started
Open the project in Eclipse, ensure Java 21 is configured, and run the main controller class to launch the GUI.
