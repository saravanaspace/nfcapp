# NFC Toggle App

A simple Android application that provides quick access to NFC settings on your device.

Started coding with android studio+gemini (code suggestion). Then, vibe coded using cursor.

## Features

- Direct access to NFC settings
- Automatic detection of NFC support
- Clean and minimal interface
- No unnecessary permissions required

## Requirements

- Android device with NFC hardware
- Android 4.0 (API level 14) or higher

## Installation

1. Download the latest APK from the releases section
2. Enable installation from unknown sources in your device settings
3. Install the APK on your device

## Usage

1. Launch the app
2. The app will automatically:
   - Check if your device supports NFC
   - Open the NFC settings if supported
   - Close itself after opening settings

## Development

### Building from Source

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the project

### Project Structure

```
app/
├── src/
│   └── main/
│       ├── java/com/nfcapp/nfctoggleapp/
│       │   └── MainActivity.kt
│       └── res/
│           ├── drawable/
│           │   └── ic_launcher_foreground.xml
│           └── mipmap/
│               └── ic_launcher.xml
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## Acknowledgments

- Android Jetpack Compose team
- Material Design team
- Android NFC documentation
- Cursor
- 