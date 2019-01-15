package leonardolana.mediapickerlib.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.data.MediaAlbum
import java.util.*

class MediaAlbumRecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var imageViewThumbnail : ImageView = itemView.findViewById(R.id.albumThumbnail)
    private var textViewAlbumName : TextView = itemView.findViewById(R.id.albumName)
    private var textViewAlbumMediaCount : TextView = itemView.findViewById(R.id.albumMediaCount)

    fun onBindViewHolder(mediaAlbum: MediaAlbum) {
        textViewAlbumName.text = mediaAlbum.name
        textViewAlbumMediaCount.text = mediaAlbum.mediaItems.size.toString()
        val random = Random()
        imageViewThumbnail.setBackgroundColor(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)))
    }

}