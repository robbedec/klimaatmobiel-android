# Klimaatmobiel Android - projecten III

This android project is the primary interface that will be used by students. It provides a (fake)
webshop that challenges users to think about the ecological impact of some products when they use it
in their STEM projects. Teachers can control and manage the webshop with the webinterface that we created
[here](https://github.com/HoGent-Projecten3/projecten3-1920-angular-klim03)

The back-end that supports this system can be found [here](https://github.com/HoGent-Projecten3/projecten3-1920-backend-klim03)

## Getting started

These instructions will allow you to run a copy of the project on your local machine for development and testing

### Prerequisits

```
Android Studio
```

### Installing

Clone the repository

```
git clone git@github.com:HoGent-Projecten3/projecten3-1920-android-klim03.git
```

Open the project in Android Studio and build

## Using the application
### Log in

There is a project / group provided for teting purposes. To gain access to the application enter the following code in the textfield.

```
212345
```


## Running tests

Open the root directory of the project in a terminal and run

```
./gradlew connectedAndroidTest
```

Or run the gradle task in Android Studio.

## Built with

* [Gradle](https://gradle.org) - Dependency Management
* [Moshi](https://github.com/square/moshi) - JSON parser
* [Timber](https://github.com/JakeWharton/timber) - Logging tool
* [Retrofit](https://github.com/square/retrofit) - Http client
* [Glide](https://github.com/bumptech/glide) - Image loading tool

## Authors

* **Daan Dedecker**
* **Robbe Decorte**
