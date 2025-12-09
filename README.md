# 📱 SMART PICK – Intelligent Smartphone Recommendation App

Smart Pick is an Android application built using **Kotlin** and **Jetpack Compose** to intelligently recommend smartphones based on user preferences such as budget, usage type, performance needs, and brand interest.  

---

## 🚀 Features

### 🔍 **User Features**
- Browse smartphones with clean UI cards  
- Apply filters based on:
  - Budget  
  - Brand  
  - Storage  
  - Camera features  
  - Battery capacity  
  - Performance category  
- View complete phone specifications  
- Compare two phones side-by-side  
- Interactive image viewer for product images  
- Price history chart  
- Festival and offer banners  
- Smooth animations and responsive layouts  

### 🛠️ **Admin Features**
- Login-secured admin panel  
- Add new smartphone details  
- Update or delete existing records  
- Manage brand offers, images, specifications  
- Track user analytics  
- Local database storage using Room  

---

## 🏗️ Tech Stack

| Layer | Technology |
|------|-------------|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM |
| Database | Room Database |
| Build Tool | Gradle |
| Image Handling | Coil |
| Chart Display | Jetpack Compose Canvas / Custom |
| Navigation | Compose Navigation |

---

## 📂 Project Structure

```
/MySP
 ├── app/src/main/java/com/smartpick/mysp/
 │    ├── ui/components/
 │    ├── ui/screens/
 │    ├── database/
 │    ├── repository/
 │    ├── model/
 │    ├── viewmodel/
 │    └── MainActivity.kt
 ├── app/src/main/res/
 │    ├── drawable/
 │    ├── layout/
 │    ├── values/
 │    └── fonts/
 └── build.gradle
```

---

## 🧠 How It Works
1. User selects filters (budget, brand, features).
2. Smart Pick processes the requirements.
3. Room Database retrieves matching phones.
4. UI displays recommendations with cards and images.
5. User can:
   - view details  
   - compare phones  
   - check price history  
   - explore offers  

---

## ▶️ Installation

1. Download or clone the repository:
   ```bash
   git clone https://github.com/yourusername/SmartPick.git
   ```
2. Open the project in **Android Studio Dolphin or above**.
3. Sync Gradle.
4. Run the app on emulator or physical device.

---

## 🧩 Future Enhancements

- AI-based recommendation engine  
- Voice-based input  
- Cloud database integration  
- Real-time price updates via API  
- User login with profiles  
- Wishlist & recently viewed section  

---

## 👥 Contributors
- **NADIN SURYA S** – Lead Developer (Kotlin, Jetpack Compose)
- **DINESH S** – UI/UX and Frontend
- **NALIN T** – Database & Admin Module
- **RAMANA G** – QA, Testing & Documentation
---

## 📜 License
This project is for academic/learning purposes.
