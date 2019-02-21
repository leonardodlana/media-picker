# Media Picker

This is a work in progress Android library made with kotlin for anyone that wants an easy-to-implement and ready-to-use media picker.

![](https://cdn1.imggmi.com/uploads/2019/2/22/443c76e4a6a0e5fc8b157534156e2774-full.png)

### Walkthrough video

[![Watch the video](https://img.youtube.com/vi/xcf5sk9QoOg/maxresdefault.jpg)](https://youtu.be/xcf5sk9QoOg)

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

#### Getting the result

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedItems = data?.getParcelableArrayListExtra<MediaItem>(MediaPickerAlbumActivity.KEY_DATA)
        }
    }
```

README will be updated as soon as the project is updated
