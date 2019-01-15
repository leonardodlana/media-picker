package leonardolana.mediapickerlib.data

class MediaAlbum(val name: String) {

    val mediaItems = ArrayList<MediaItem>()

    fun addMedia(mediaItem: MediaItem) {
        mediaItems.add(mediaItem)
    }

}