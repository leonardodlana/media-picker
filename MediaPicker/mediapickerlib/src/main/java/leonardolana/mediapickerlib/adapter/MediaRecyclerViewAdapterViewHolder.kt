package leonardolana.mediapickerlib.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import leonardolana.mediapickerlib.R
import leonardolana.mediapickerlib.common.ImageLoader
import leonardolana.mediapickerlib.data.MediaItem
import java.lang.ref.WeakReference

class MediaRecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var imageViewThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)

    fun onBindViewHolder(mediaItem: MediaItem) {
        //Avoid wrong images when recycling the view
        imageViewThumbnail.setImageBitmap(null)

        val weakReference = WeakReference<ImageView>(imageViewThumbnail)

        ImageLoader.loadThumbnail(mediaItem.path) { bitmap ->
            imageViewThumbnail.setImageBitmap(bitmap)
        }
    }

}