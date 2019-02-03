package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.common.ImageLoader
import leonardolana.mediapickerlib.data.MediaItem

class MediaRecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var imageViewThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    private var imageViewShadow: View = itemView.findViewById(R.id.thumbnailShadow)
    private var imageViewThumnailCheck: ImageView = itemView.findViewById(R.id.thumbnailCheck)

    fun onBindViewHolder(
        position: Int,
        mediaItem: MediaItem,
        isSelected: Boolean,
        onItemClickListener: MediaRecyclerViewAdapter.OnItemClickListener?
    ) {
        //Avoid wrong images when recycling the view
        imageViewThumbnail.setImageBitmap(null)

        ImageLoader.loadThumbnail(mediaItem.path) { bitmap ->
            imageViewThumbnail.setImageBitmap(bitmap)
        }

        imageViewThumbnail.setOnLongClickListener {
            onItemClickListener?.onLongClick(position, mediaItem)
            true
        }

        imageViewThumbnail.setOnClickListener {
            onItemClickListener?.onClick(position, mediaItem)
        }

        if(isSelected) {
            imageViewShadow.visibility = View.VISIBLE
            imageViewThumnailCheck.visibility = View.VISIBLE
        } else {
            imageViewShadow.visibility = View.GONE
            imageViewThumnailCheck.visibility = View.GONE
        }
    }

}