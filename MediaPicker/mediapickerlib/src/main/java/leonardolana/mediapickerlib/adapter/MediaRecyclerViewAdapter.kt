package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.data.MediaItem

class MediaRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onLongClick(position: Int, mediaItem: MediaItem)
        fun onClick(position: Int, mediaItem: MediaItem)
    }

    private var album : MediaAlbum? = null
    private var data : MutableList<MediaItem> = ArrayList<MediaItem>()
    private var selectedItems: MutableSet<Int>? = null
    private var onItemClickListener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.media_item, parent, false)
        return MediaRecyclerViewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val mediaHolder = vh as MediaRecyclerViewAdapterViewHolder
        val mediaItem = data[position]
        val isSelected = if (selectedItems != null) selectedItems!!.contains(position) else false
        mediaHolder.onBindViewHolder(position, mediaItem, isSelected, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(album: MediaAlbum) {
        data.clear()
        this.album = album
        data.addAll(album.mediaItems)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setSelectedItems(selectedItems: MutableSet<Int>) {
        this.selectedItems = selectedItems
    }

}