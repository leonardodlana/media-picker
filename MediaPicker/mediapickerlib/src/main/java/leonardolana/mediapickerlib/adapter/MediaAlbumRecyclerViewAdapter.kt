package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.data.MediaAlbum

class MediaAlbumRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data : MutableList<MediaAlbum> = ArrayList<MediaAlbum>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.media_album_item, parent, false)
        return MediaAlbumRecyclerViewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val mediaAlbumHolder = vh as MediaAlbumRecyclerViewAdapterViewHolder
        val mediaAlbum = data[position]

        mediaAlbumHolder.onBindViewHolder(mediaAlbum)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(albums: Map<String, MediaAlbum>) {
        data.clear()
        data.addAll(albums.values)
    }

}