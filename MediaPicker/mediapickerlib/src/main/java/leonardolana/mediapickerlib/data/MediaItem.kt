package leonardolana.mediapickerlib.data

import java.io.File

class MediaItem(val path: String) {

    val name : String = path.split(File.pathSeparator).last()

}