package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.data.MediaAlbum

class MediaAlbumRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(album: MediaAlbum)
    }

    private var data : MutableList<MediaAlbum> = ArrayList<MediaAlbum>()
    private var onItemClickListener : OnItemClickListener? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.media_album_item, parent, false)
        return MediaAlbumRecyclerViewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val mediaAlbumHolder = vh as MediaAlbumRecyclerViewAdapterViewHolder
        val mediaAlbum = data[position]

        mediaAlbumHolder.onBindViewHolder(mediaAlbum, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(albumsMap: Map<String, MediaAlbum>) {
        data.clear()
        val albums = albumsMap.values.sortedWith(Comparator { o1, o2 -> o2.getCover().lastModifiedDate.compareTo(o1.getCover().lastModifiedDate) })
        data.addAll(albums)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        onItemClickListener = listener
    }

}