# 🚀 Jetpack Compose DI Module

A starter template for building Android applications using **Jetpack Compose** and **Dependency Injection**. This project includes all the basic structure and setup required to jumpstart your Compose-based Android app.

---

## 📦 Features

- ✅ Jetpack Compose UI Toolkit
- ✅ Dependency Injection setup (e.g., Hilt or Koin)
- ✅ Modular project structure
- ✅ MVVM Architecture
- ✅ Navigation Component for Compose
- ✅ Sample ViewModel and Composable screen
- ✅ Theming and UI base setup

---

## 🏗️ Project Structure
com.pixeldev.compose
│
├── data             // Handles data sources: API, Database, Cache
│   ├── remote       // Network-related classes (e.g., Retrofit)
│   ├── local        // Local data (Room DB, SharedPrefs)
│   ├── repository   // Repository implementations
│   └── model        // Data-layer models (DTOs)
│
├── domain           // Business logic layer
│   ├── model        // Domain-layer models (Entities)
│   ├── repository   // Repository interfaces (contracts)
│   └── usecase      // Interactors or Use Cases
│
├── presentation     // UI Layer (Activities, Fragments, ViewModels)
│   ├── feature1     // Each feature has its own module or folder
│   │   ├── ui       // UI components (Fragments, Activities)
│   │   ├── viewmodel// ViewModel for the feature
│   │   └── mapper   // UI <-> Domain model mapper
│   └── ...
│
└── di               // Dependency Injection (e.g., Hilt modules)


## 📦 Source Code

📁 Full source code available on GitHub:  
👉 [GitHub @Dinesh2510](https://github.com/Dinesh2510)  
⭐ Don't forget to star the repo if you find it helpful!

---


## 🙌 Contributions Welcome

Want to learn, improve, or contribute? Fork the repo, raise issues or open pull requests — let’s grow together!

---

## 🔗 Contact

📧 Email: `support@pixeldev.in`  
📱 Instagram: `https://www.instagram.com/pixel.designdeveloper/`  
🎬 YouTube Channel: https://www.youtube.com/@pixeldesigndeveloper

---

