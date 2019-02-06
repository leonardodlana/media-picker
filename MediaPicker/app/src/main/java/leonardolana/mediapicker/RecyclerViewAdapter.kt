package leonardolana.mediapicker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import leonardolana.mediapickerlib.data.MediaItem


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<MediaItem> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false)

        return RecyclerViewAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val mediaItem = data[position]
        val viewHolder = vh as RecyclerViewAdapterViewHolder

        viewHolder.textViewTitle.text = mediaItem.name

        val options = RequestOptions()
        options.centerCrop()

        Glide.with(viewHolder.itemView.context)
            .load(mediaItem.path)
            .thumbnail()
            .apply(options)
            .into(viewHolder.imageViewThumbnail)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(mediaItems: ArrayList<MediaItem>) {
        data.clear()
        data.addAll(mediaItems)
        notifyDataSetChanged()
    }

    private class RecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)

    }

}