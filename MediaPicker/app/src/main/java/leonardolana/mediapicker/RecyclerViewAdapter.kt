package leonardolana.mediapicker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions



class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false)

        return RecyclerViewAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val path = data[position]
        val viewHolder = vh as RecyclerViewAdapterViewHolder

        viewHolder.textViewTitle.text = path

        val options = RequestOptions()
        options.centerCrop()

        Glide.with(viewHolder.itemView.context)
            .load(path)
            .thumbnail()
            .apply(options)
            .into(viewHolder.imageViewThumbnail)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(paths: ArrayList<String>) {
        data.clear()
        data.addAll(paths)
        notifyDataSetChanged()
    }

    private class RecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)

    }

}