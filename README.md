# NFC App

A simple and elegant Android application that allows users to quickly toggle NFC (Near Field Communication) on and off. Built with modern Android development practices using Jetpack Compose.

Started coding with android studio+gemini (code suggestion). Then, vibe coded using cursor.

## Features

- 🎯 Simple one-tap NFC toggle
- 📱 Modern Material Design 3 UI
- 🔄 Real-time NFC status monitoring
- 🎨 Beautiful adaptive icon
- 🔒 Secure implementation with proper error handling
- 📊 Automatic state synchronization

## Screenshots

[Add screenshots here]

## Requirements

- Android 7.0 (API level 25) or higher
- Device with NFC hardware support
- Android Studio Arctic Fox or newer

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/nfcapp.git
```

2. Open the project in Android Studio

3. Build and run the app on your device:
   - Connect your Android device via USB
   - Enable USB debugging in Developer options
   - Click "Run" in Android Studio

## Building the APK

### Debug APK
```bash
./gradlew assembleDebug
```
The debug APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK
1. Open Android Studio
2. Click "Build" > "Generate Signed Bundle / APK"
3. Choose "APK"
4. Create or select a keystore
5. Fill in the key details
6. Choose release build type
7. Click "Finish"

The release APK will be generated at: `app/build/outputs/apk/release/app-release.apk`

## Usage

1. Launch the app
2. The current NFC status will be displayed
3. To change the NFC state:
   - Tap the switch
   - You'll be redirected to the system NFC settings
   - Toggle NFC in the system settings
   - Return to the app to see the updated status

## Technical Details

### Architecture
- Built with Jetpack Compose
- Uses Material Design 3 components
- Implements modern Android architecture components

### Security Features
- Proper NFC permission handling
- Secure intent handling
- Error handling and state management
- Configuration change handling

### Dependencies
- AndroidX Core KTX
- Jetpack Compose
- Material Design 3
- Kotlin Coroutines

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE] file for details.

## Acknowledgments

- Android Jetpack Compose team
- Material Design team
- Android NFC documentation
- Cursor