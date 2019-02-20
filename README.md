# Media Picker

This is a work in progress Android library made with kotlin for anyone that wants an easy-to-implement and ready-to-use media picker.

### Add the library to your app

On your project's gradle file add

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

On your module's gradle file add

```
dependencies {
  implementation 'com.github.leonardodlana:media-picker:0.0.1'
}
``` 

### Usage

#### Java
```
MediaPickerAlbumActivity.Companion.launch({Activity}, {onlyPictures?}, {YOUR_REQUEST_CODE});
```

#### Kotlin

```
MediaPickerAlbumActivity.launch({Activity}, {onlyPictures?}, {YOUR_REQUEST_CODE})
```

README will be updated as soon as the project is updated
