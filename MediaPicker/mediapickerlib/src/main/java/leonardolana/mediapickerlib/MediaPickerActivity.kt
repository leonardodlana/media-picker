package leonardolana.mediapickerlib

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import leonardolana.mediapickerlib.common.BaseActivity
import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.loader.AlbumRepository
import kotlinx.android.synthetic.main.activity_media_picker.*
import leonardolana.mediapickerlib.adapter.MediaRecyclerViewAdapter
import leonardolana.mediapickerlib.common.RecyclerViewItemSeparator

class MediaPickerActivity : BaseActivity(), MediaPickerView {

    companion object {

        private const val KEY_ALBUM_NAME = "album_name"

        fun launch(context: Context, album: MediaAlbum) {
            val intentPicker = Intent(context, MediaPickerActivity::class.java)
            intentPicker.putExtra(KEY_ALBUM_NAME, album.name)
            context.startActivity(intentPicker)
        }
    }

    private lateinit var mediaPresenter: MediaPickerPresenter
    private lateinit var album: MediaAlbum
    private lateinit var mediaAdapter : MediaRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mediaPresenter = MediaPickerPresenter(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_media_picker)

        val albumName = intent.getStringExtra(KEY_ALBUM_NAME)
        album = AlbumRepository.instance.getAlbumByName(albumName)!!

        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(RecyclerViewItemSeparator())
        mediaAdapter = MediaRecyclerViewAdapter()
        mediaAdapter.setData(album)

        recyclerView.adapter = mediaAdapter

    }

    override fun createPresenter(): BasePresenter {
        return mediaPresenter
    }

}