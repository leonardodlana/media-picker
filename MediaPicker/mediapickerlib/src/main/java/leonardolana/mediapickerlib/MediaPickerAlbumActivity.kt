package leonardolana.mediapickerlib

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_media_album_picker.*
import leonardolana.mediapickerlib.adapter.MediaAlbumRecyclerViewAdapter
import leonardolana.mediapickerlib.common.BaseActivity
import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.common.RecyclerViewItemSeparator
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.permission.Permission
import leonardolana.mediapickerlib.permission.PermissionHelper


class MediaPickerAlbumActivity : BaseActivity(), MediaPickerAlbumView {

    private lateinit var mediaPresenter: MediaPickerAlbumPresenter
    private lateinit var mediaAdapter: MediaAlbumRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mediaPresenter = MediaPickerAlbumPresenter(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_media_album_picker)

        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(RecyclerViewItemSeparator())
        mediaAdapter = MediaAlbumRecyclerViewAdapter()

        mediaAdapter.setOnItemClickListener(object : MediaAlbumRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(album: MediaAlbum) {
                mediaPresenter.onAlbumClick(album)
            }
        })

        recyclerView.adapter = mediaAdapter
    }

    override fun createPresenter(): BasePresenter {
        return mediaPresenter
    }

    override fun onAlbumsLoaded(albums: Map<String, MediaAlbum>) {
        mediaAdapter.setData(albums)
    }

    override fun getContext(): Context {
        return applicationContext
    }

    override fun askPermission(permission: Permission) {
        PermissionHelper.requestPermission(this, null, permission)
    }

    override fun openAlbum(album: MediaAlbum) {
        MediaPickerActivity.launch(applicationContext, album)
    }


}