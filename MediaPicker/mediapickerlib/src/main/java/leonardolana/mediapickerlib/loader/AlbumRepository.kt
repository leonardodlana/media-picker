package leonardolana.mediapickerlib.loader

import android.content.Context
import leonardolana.mediapickerlib.common.Utils
import leonardolana.mediapickerlib.data.MediaAlbum

class AlbumRepository {

    interface OnAlbumListener {
        fun onLoad(albums: Map<String, MediaAlbum>)
    }

    companion object {
        val instance = AlbumRepository()
    }

    private var albums : Map<String, MediaAlbum>? = null

    fun load(context: Context, listener: OnAlbumListener) {
        val loadedAlbums = Utils.getAllMediaPathsOnGallery(context)
        albums = loadedAlbums
        listener.onLoad(albums!!)
    }

    fun getAlbumByName(name: String) : MediaAlbum? {
        return albums?.get(name)
    }

}