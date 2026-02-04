# Smartext - Android SMS Gateway

Smartext is a robust Android-based SMS Gateway that transforms your physical smartphone into a functional SMS API server. This project allows you to send and receive messages programmatically via REST APIs, providing an affordable alternative to traditional SMS aggregators.

## 🚀 Key Features
* **SMS API Gateway:** Send and receive SMS through HTTP requests.
* **Modern Android Stack:** Built with Kotlin following MVVM architecture patterns.
* **Database Reliability:** Integrated with Room Database (v17) featuring automated schema migrations.
* **Real-time Monitoring:** Detailed log system for message states, deliveries, and webhooks.
* **Firebase Integration:** Uses Google Services for real-time cloud-to-device communication.
* **Self-Hosting Ready:** Optimized for Docker and local server infrastructure environments.

## 🛠 Tech Stack
* **Language:** Kotlin / Java
* **Database:** Room Persistence Library
* **Push Notifications:** Firebase Cloud Messaging (FCM)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Backend:** RESTful API & Webhooks

## 📦 Installation & Setup

### Prerequisites
* Android Device with SIM card.
* Android Studio (Ladybug or newer).
* USB Debugging enabled in Developer Options.

### Getting Started
1. **Clone the Repo:**
   ```bash
   git clone [https://github.com/faisalk97/Smartext.git](https://github.com/faisalk97/Smartext.git)

2. Setup Firebase: Place your google-services.json in the /app folder.
3. Build & Run: Sync Gradle and install the APK on your device.
