package leonardolana.mediapickerlib.data

import java.io.File

class MediaItem(val path: String, val lastModifiedDate: Long) {

    val name : String = path.split(File.pathSeparator).last()

}