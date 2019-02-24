package leonardolana.mediapickerlib.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.data.MediaItem

class MediaRecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var imageViewThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    private var imageViewShadow: View = itemView.findViewById(R.id.thumbnailShadow)
    private var imageViewThumnailCheck: ImageView = itemView.findViewById(R.id.thumbnailCheck)
    private var imageViewThumbnailVideoIcon: ImageView = itemView.findViewById(R.id.thumbnailVideoIcon)

    fun onBindViewHolder(
        position: Int,
        mediaItem: MediaItem,
        isSelected: Boolean,
        onItemClickListener: MediaRecyclerViewAdapter.OnItemClickListener?
    ) {

        Glide.with(itemView.context)
            .load(mediaItem.path)
            .thumbnail()
            .into(imageViewThumbnail)

        imageViewThumbnail.setOnLongClickListener {
            onItemClickListener?.onLongClick(position, mediaItem)
            true
        }

        imageViewThumbnail.setOnClickListener {
            onItemClickListener?.onClick(position, mediaItem)
        }

        imageViewThumbnailVideoIcon.visibility = if (mediaItem.isVideo) View.VISIBLE else View.GONE

        if (isSelected) {
            imageViewShadow.visibility = View.VISIBLE
            imageViewThumnailCheck.visibility = View.VISIBLE
        } else {
            if(!mediaItem.isVideo)
                imageViewShadow.visibility = View.GONE
            
            imageViewThumnailCheck.visibility = View.GONE
        }
    }

}