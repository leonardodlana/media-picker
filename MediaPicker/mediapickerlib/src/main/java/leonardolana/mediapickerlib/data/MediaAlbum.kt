package leonardolana.mediapickerlib.data

import java.util.*
import kotlin.Comparator

class MediaAlbum(val name: String) {

    val mediaItems = ArrayList<MediaItem>()

    fun addMedia(mediaItem: MediaItem) {
        mediaItems.add(mediaItem)
    }

    fun sortByLastDateModified() {
        mediaItems.sortWith(Comparator { o1, o2 ->
            o2.lastModifiedDate.compareTo(o1.lastModifiedDate)
        })
    }

    fun getCover() : MediaItem {
        return mediaItems.first()
    }

}