# Moathir Android

Android WebView wrapper for https://joinmoathir.com with a GitHub Actions workflow that builds an installable debug APK automatically.

## Build on GitHub

1. Create a new GitHub repository.
2. Upload all files from this project to the repository root.
3. Commit them to the `main` branch.
4. Open **Actions** > **Build Moathir APK**.
5. Tap **Run workflow**.
6. When the workflow finishes, open the run and download the **Moathir-APK** artifact.
7. Unzip it to get `app-debug.apk`.

## Main settings

- App ID: `com.moathir.app`
- App name: `مؤثر`
- Website: `https://joinmoathir.com`
- Minimum Android: Android 7.0 (API 24)
- Target SDK: 35

## Change the site URL

Edit `HOME_URL` in:

`app/src/main/java/com/moathir/app/MainActivity.java`

## Note

The workflow currently builds a **debug-signed APK**, which is installable for testing. A Play Store production release should use a private release signing key and an Android App Bundle (AAB).
