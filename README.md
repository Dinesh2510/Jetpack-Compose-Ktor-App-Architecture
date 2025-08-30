# 🚀 Jetpack Compose Retrofit App Architecture

A starter template for building modern Android applications using Jetpack Compose, MVVM architecture, **Retrofit client**, and Dependency Injection (Hilt).  
This project provides a ready-to-use base structure for Android apps with all essential setup done, so you can focus on building features instead of boilerplate.

---

## ✨ Features
- 🧩 **Clean Architecture** (Separation of Concerns)
- 🏗 **MVVM (Model-View-ViewModel)**
- 🧭 **Navigation Component for Compose**
- 🔗 **Retrofit Client** for networking
- 💉 **Dependency Injection with Hilt** (Koin support coming soon)
- 🎨 **Jetpack Compose** for UI
- ⚡ **Coroutines + Flow** for async & reactive programming
- 📱 **Material 3** design components

---

## This template is perfect for developers who want a solid foundation to quickly start any Android project using modern best practices.

---

### ✅ Implemented Features with Retrofit
- **Quotes Listing** – Fetch and display quotes on the home page using a public API (DummyJSON or similar)

> Note: This version does **not include login, user details, or image upload**. It focuses on demonstrating **Retrofit integration** with Jetpack Compose and MVVM.

---

## 📲 App Screens
| Screenshot | Screenshot |
|------------|------------|
| ![s1](Screens/Screenshot_20250830_233521.png) | ![s2](Screens/Screenshot_20250830_231022.png) |

---

## 🏗️ Project Structure

<pre>
com.pixeldev.compose
│
├── data             // Handles data sources: API, Database, Cache
│   ├── remote       // Network-related classes (Retrofit API interfaces)
│   ├── local        // Local data (Room DB, SharedPrefs)
│   ├── repository   // Repository implementations
│   └── model        // Data-layer models (DTOs)
│
├── domain           // Business logic layer
│   ├── model        // Domain-layer models (Entities)
│   ├── repository   // Repository interfaces (contracts)
│   └── usecase      // Interactors or Use Cases
│
├── presentation     // UI Layer (Jetpack Compose Screens, ViewModels)
│   ├── feature1     // Each feature has its own module or folder
│   │   ├── ui       // UI components (Composables, Screens)
│   │   ├── viewmodel// ViewModel for the feature
│   │   └── mapper   // UI <-> Domain model mapper
│   └── ...
│
└── di               // Dependency Injection (e.g., Hilt modules)
</pre>

---

## 📦 Source Code

Full source code for this **Retrofit + Compose starter template** is available on GitHub:  
👉 [GitHub @Dinesh2510](https://github.com/Dinesh2510)

This branch uses **main** as the networking client:  
🌿 [`main`](https://github.com/Dinesh2510/Jetpack-Compose-Ktor-App-Architecture/tree/main)

🎥 Watch the tutorial on my YouTube channel:  
👉 [YouTube - PixelDeveloper](https://www.youtube.com/@PixelDeveloper)

⭐ Don't forget to **star the repo** if you find it helpful!

---

## 🙌 Contributions Welcome

Want to learn, improve, or contribute? Fork the repo, raise issues or open pull requests — let’s grow together!

---
