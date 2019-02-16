package leonardolana.mediapickerlib

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_media_album_picker.*
import leonardolana.mediapickerlib.adapter.MediaAlbumRecyclerViewAdapter
import leonardolana.mediapickerlib.common.BaseActivity
import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.common.RecyclerViewItemSeparator
import leonardolana.mediapickerlib.common.Utils
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.data.MediaItem
import leonardolana.mediapickerlib.permission.Permission
import leonardolana.mediapickerlib.permission.PermissionHelper


class MediaPickerAlbumActivity : BaseActivity(), MediaPickerAlbumView {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 342
    }

    private lateinit var mediaPresenter: MediaPickerAlbumPresenter
    private lateinit var mediaAdapter: MediaAlbumRecyclerViewAdapter
    private var cameraMenuButton: MenuItem? = null
    private var bitmapTempFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mediaPresenter = MediaPickerAlbumPresenter(this)
        super.onCreate(savedInstanceState)

        Utils.disableChecks()

        setContentView(R.layout.activity_media_album_picker)

        supportActionBar?.title = getString(R.string.choose_album)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.media_picker_album_menu, menu)
        cameraMenuButton = menu?.findItem(R.id.camera)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.camera) {
            mediaPresenter.onCameraClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_IMAGE_CAPTURE && bitmapTempFilePath != null) {
                mediaPresenter.onPictureTaken(bitmapTempFilePath!!)
            } else {
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
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
        MediaPickerActivity.launch(this, album)
    }

    override fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val file = Utils.createBitmapFile(applicationContext)
                bitmapTempFilePath = file.absolutePath
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun finishWithResult(mediaItem: MediaItem) {
        //todo make a common result builder
        val data = Intent()
        val list = ArrayList<MediaItem>()
        list.add(mediaItem)

        data.putParcelableArrayListExtra(MediaPickerActivity.KEY_DATA, list)
        setResult(Activity.RESULT_OK, data)
        finish()
    }


}