package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.data.MediaItem

class MediaRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var album : MediaAlbum? = null
    private var data : MutableList<MediaItem> = ArrayList<MediaItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.media_item, parent, false)
        return MediaRecyclerViewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val mediaHolder = vh as MediaRecyclerViewAdapterViewHolder
        val mediaItem = data[position]

        mediaHolder.onBindViewHolder(mediaItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(album: MediaAlbum) {
        data.clear()
        this.album = album
        data.addAll(album.mediaItems)
    }


}