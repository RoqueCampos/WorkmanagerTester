# WorkmanagerTester

Very simple Android app to test the reliability of the Workmanager api on an Android device. The first time that the app is launched, the app creates three workers: one each 15 minutes, on each 30 minutes,
and one an hour. Then the results of the execution of each worker are stored in the shared preferences, and displayed on textviews on the user interface.

The target of this app was to find out if the Workmanager api was reliable on some devices that have strong restrictions for background tasks (like Xiaomi or Huawei devices).

The app was not intended to be published, was developed in half an hour, and there is no a fancy architecture, nor best practices, and so on. Please don't judge the quality of the source code very hard. I'm publishing it here by popular demand on reddir.

Feel free to fork it and improve it!

## Getting Started

Simple open the Android Studio project, build and deploy the app to a device.


## Authors

* **Roque Campos** - *Initial work* - (https://github.com/RoqueCampos)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

