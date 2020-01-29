# Klimaatmobiel Android - projecten III

This android project is the primary interface that will be used by students. It provides a (fake)
webshop that challenges users to think about the ecological impact of some products when they use it
in their STEM projects. Teachers can control and manage the webshop with the webinterface that we created
[here](https://github.com/HoGent-Projecten3/projecten3-1920-angular-klim03).

The back-end that supports this system can be found [here](https://github.com/HoGent-Projecten3/projecten3-1920-backend-klim03).

## Getting started

These instructions will allow you to run a copy of the project on your local machine for development and testing.

### Prerequisits

```
Android Studio
```

For the duration of this course project a setup has been built to host the back-end, database and a CI/CD pipeline. To start the project if these are no longer online a user should change the build variant to 'development' and change the connection environment to localhost. 

Simular instruction to run the back-end can be found in its repository.

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

Run the following commands in the terminal or run the designated gradle tasks in Android Studio.

To run unit tests:
```
./gradlew test
```

To run instrumented unit tests (i.e. ui tests):
```
./gradlew connectedAndroidTest
```

## Main features
<p align="center"><img src="https://github.com/robbedec/klimaatmobiel-android/blob/master/docs/images/home.png" width="90%" /></p> 
<p align="center"><img src="https://github.com/robbedec/klimaatmobiel-android/blob/master/docs/images/webshop.png" width="90%" /></p>
<p align="center"><img src="https://github.com/robbedec/klimaatmobiel-android/blob/master/docs/images/shopping-cart.png" width="90%" /></p>
<p align="center"><img src="https://github.com/robbedec/klimaatmobiel-android/blob/master/docs/images/details.png" width="90%" /></p>

## Built with

* [Gradle](https://gradle.org) - Dependency Management
* [Moshi](https://github.com/square/moshi) - JSON parser
* [Timber](https://github.com/JakeWharton/timber) - Logging tool
* [Retrofit](https://github.com/square/retrofit) - Http client
* [Glide](https://github.com/bumptech/glide) - Image loading tool

## Authors

* **Daan Dedecker**
* **Robbe Decorte**
