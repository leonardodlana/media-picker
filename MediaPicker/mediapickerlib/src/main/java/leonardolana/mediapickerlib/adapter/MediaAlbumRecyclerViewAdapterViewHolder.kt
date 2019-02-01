package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.common.ImageLoader
import leonardolana.mediapickerlib.data.MediaAlbum

class MediaAlbumRecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var imageViewThumbnail: ImageView = itemView.findViewById(R.id.albumThumbnail)
    private var textViewAlbumName: TextView = itemView.findViewById(R.id.albumName)
    private var textViewAlbumMediaCount: TextView = itemView.findViewById(R.id.albumMediaCount)

    fun onBindViewHolder(
        mediaAlbum: MediaAlbum,
        onItemClickListener: MediaAlbumRecyclerViewAdapter.OnItemClickListener?
    ) {
        textViewAlbumName.text = mediaAlbum.name
        textViewAlbumMediaCount.text = mediaAlbum.mediaItems.size.toString()

        imageViewThumbnail.setImageBitmap(null)
        ImageLoader.loadThumbnail(mediaAlbum.getCover().path) { bitmap ->
            imageViewThumbnail.setImageBitmap(bitmap)
        }

        imageViewThumbnail.setOnClickListener {
            onItemClickListener?.onClick(mediaAlbum)
        }
    }

}