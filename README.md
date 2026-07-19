# Quirky - AI-Powered Note App

A modern Android note app with voice transcription and automatic tagging built with Jetpack Compose, Kotlin, and clean architecture.

## Features

### MVP
- ✅ Create, edit, delete text notes
- ✅ Notes listed in reverse chronological order
- ✅ Full-text search by title/content
- ✅ Local persistence with Room
- ✅ Clean Material 3 UI with Compose

### Phase 1: Voice Transcription
- 🎤 Record voice and transcribe to text
- 🔌 Android SpeechRecognizer integration
- 📍 Inline voice input in note editor

### Phase 2: Automatic Tagging
- 🏷️ Auto-tag notes based on content
- 🔍 Filter notes by tag
- 💾 Tag persistence in Room

### Future Enhancements
- ☁️ Cloud sync (Firebase)
- ⏰ Reminders
- 📸 Photo attachments
- 🌙 Dark mode

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Repository Pattern
- **Local Storage:** Room + Kotlin Coroutines/Flow
- **DI:** Hilt
- **Navigation:** Compose Navigation
- **Voice:** Android SpeechRecognizer

## Project Structure

```
app/src/main/java/com/example/quirky/
├── ui/
│   ├── screens/
│   │   ├── NoteListScreen.kt
│   │   ├── NoteDetailScreen.kt
│   │   └── NoteEditScreen.kt
│   ├── components/
│   ├── theme/
│   └── navigation/
├── viewmodel/
│   ├── NoteListViewModel.kt
│   └── NoteDetailViewModel.kt
├── domain/
│   ├── model/
│   │   └── Note.kt
│   ├── repository/
│   │   └── NoteRepository.kt
│   └── usecase/
├── data/
│   ├── local/
│   │   ├── NoteDatabase.kt
│   │   └── NoteDao.kt
│   └── repository/
│       └── NoteRepositoryImpl.kt
└── di/
    └── AppModule.kt
```

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Build and run on an emulator or device (API 28+)

## Development Progress

- [ ] MVP: Basic CRUD operations
- [ ] Phase 1: Voice transcription
- [ ] Phase 2: Automatic tagging
- [ ] UI Polish & Material 3 theming
- [ ] Cloud sync
