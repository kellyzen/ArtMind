>Kelly Tan Kai Ling (20310184)
# ArtMind Mobile Application
## COMPILATION
1. Download [Android Studio](https://developer.android.com/studio).
2. Install the Android Studio IDE using the downloaded installer along with JDK and direct the installation to your preferred path.
3. Clone the repository to your local machine
   ```git clone https://github.com/kellyzen/artmind-mobile.git```.
4. Open the project in Android Studio.
5. Build the project using the "Build" menu in Android Studio.
6. Run the application on an emulator or physical device.

## FEATURES
### Login/ Sign-in
- [x] One account sign-in per phone number.
- [x] Secure OTP login using Firebase authentication.

### Art Analysis
- [x] Artwork analysis using dominant color to categorize images.
- [x] Results stored in Firebase and displayed on the Result page.
- [x] Display history on the History page.
- [ ] API call to Roboflow.

### Find Help
- [x] Find Help page to locate mental health centers based on the selected country.

### Focus Timer
- [x] Focus Timer page for users to set a timer for focused drawing.
- [x] Countdown timer with options to set, start, pause, stop, reset, and display the timer.

### App Settings
- [x] Navigate to About Us page to understand the goal of the mobile app.
- [x] Navigate to Ethical Guidelines page to read the important rules of using the mobile app.
- [x] Navigate to Profile page to update user's information.

### Others
- [x] Multi-language Support: Available in three languages: English, Chinese, Malay.
- [x] Responsive Design: Landscape mode support with responsive design.

## PROGRAM'S STRUCTURE
The project follows the MVC (Model-View-Controller) architectural pattern.

### Models
Located in the ```app/com/example/artmind/model``` folder for the user interface.
- UserModel: Stores user information.
- TimerModel: Stores timer information.
- MusicPlayerModel: Manages music loading, playing, pausing, and muting.
- HistoryModel: Stores user's drawing history.
- ColorAnalysisModel: Analyzes colors in uploaded images.
- SharedViewModel: Shares cropped or uploaded images for shared views.

### Views
XML files in the ```res/layout``` folder for the user interface.

### Controllers
Located in the ```app/com/example/artmind/component``` folder for the user interface.

## JAVADOC
```/javadoc/index.html```

## APK File
Use Android Studio to build the final version of the ArtMind application. Then, select the "Build" menu and choose the "Build Bundle(s) / APK(s)" option. Lastly, opt for the "Build APK(s)" selection to generate the APK file for deployment.
