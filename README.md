# Noter Android

Simple example of notes taking android app

Noter lets you create notes, edit them, and when note is not needed anymore you can delete it.

Main goal of this app is to show how to setup internal MYSQL database and perform CRUD 
(create, read, update, delete) operations. Also this app shows how to use recycler view 
and how to send note as email via action intent

### Whats inside

Java code is divided to 4 packages: **adapters**, **data**, **ui** and **utils**

* **adapters** package contains one recycler view adapter for notes
* **data** package have SQLHelper class and Note POJO (plain old java object) for data storing
* **ui** package controlls all the views
* **utils** package have a class which implements TextWatcher interface

##### Screenshots

![alt text](https://github.com/vytautassugintas/android-noter/blob/master/img001_noter_main.png?raw=true "Main Activity")
![alt text](https://github.com/vytautassugintas/android-noter/blob/master/img002_noter_create.png?raw=true "Create Activity")
