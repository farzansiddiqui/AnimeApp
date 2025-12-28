# 📱 Anime Explorer App (Seekho Assignment)

## 📌 Overview

Anime Explorer is an Android application built using **Jetpack Compose** that displays a list of popular anime and detailed information for each anime using the **Jikan API** (MyAnimeList unofficial API).

The app is designed with an **offline-first approach**, ensuring a smooth user experience even when the device has no internet connection.

---

## ✨ Features

### 🏠 Anime List Screen

* Fetches top anime from Jikan API
* Displays:

  * Anime title
  * Poster image
  * Rating
  * Number of episodes
* Cached locally for offline access

### 📄 Anime Detail Screen

* Displays:

  * Poster image
  * Trailer (if available)
  * Title
  * Rating
  * Episodes
  * Genres
  * Synopsis
* Trailer opens externally (YouTube) using embed URL
* Fully supports offline viewing for previously opened anime

### 📴 Offline Support

* Anime list and anime details are stored locally using Room
* App continues to work without internet for cached data
* Graceful UI when data is unavailable offline

---

## 🧱 Architecture

The app follows **MVVM (Model–View–ViewModel)** architecture.

```
UI (Jetpack Compose)
        ↓
ViewModel (StateFlow)
        ↓
Repository
        ↓
Room Database  ←→  Network (Retrofit)
```

### Why MVVM?

* Clean separation of concerns
* Easy state management with Compose
* Scalable and testable

---

## 🛠 Tech Stack

* **Kotlin**
* **Jetpack Compose** – UI
* **MVVM Architecture**
* **Retrofit** – Network calls
* **Room** – Local database
* **Coroutines + StateFlow** – Async & state handling
* **Coil** – Image loading

---

## 🌐 API Used

### Top Anime

```
GET https://api.jikan.moe/v4/top/anime
```

### Anime Details

```
GET https://api.jikan.moe/v4/anime/{anime_id}
```

---

## 🎥 Trailer Handling (Important Design Decision)

* The API provides trailer via `embed_url`
* YouTube playback inside WebView is unreliable due to platform restrictions
* **Solution used**:

  * Show anime poster with a Play button
  * Open trailer externally in YouTube / browser

✔ Stable
✔ Legal
✔ Production-safe

---

## 💾 Offline Strategy

### Anime List

* Stored in Room on first successful API call
* Loaded from Room when offline

### Anime Detail

* Cached **only after user opens detail screen online**
* Offline behavior:

  * If cached → detail screen opens normally
  * If not cached → friendly offline error UI is shown

### Why not cache everything?

* Keeps storage usage minimal
* Matches real-world app behavior

---

## ⚠️ Error Handling

Handled gracefully for:

* Network failures
* Offline mode
* Missing cached data
* Partial / null API fields

Instead of generic errors, the app shows:

* Friendly offline messages
* Retry option
* Non-blocking UI

---

## 🧪 Known Limitations

* Anime details must be opened once online to be available offline
* Trailer playback requires internet connection
* Characters data not implemented (requires additional API endpoint)

---

## 🚀 Possible Improvements

* Add character list using `/anime/{id}/characters`
* Prefetch anime details in background
* Add pagination for anime list
* Add connectivity observer for auto-sync
* Add UI tests

---

## 🧠 Key Learnings / Highlights

* Offline-first architecture
* Defensive UI design for unreliable data
* Proper separation of DTO, Entity, and UI layers
* Real-world handling of third-party video content

---

## 👨‍💻 Author

**Farzan Siddiqui**


