package leonardolana.mediapickerlib

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_media_picker.*
import leonardolana.mediapickerlib.MediaPickerAlbumActivity.Companion.SELECTION_LIMIT_LIMITLESS
import leonardolana.mediapickerlib.adapter.MediaRecyclerViewAdapter
import leonardolana.mediapickerlib.common.BaseActivity
import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.common.RecyclerViewItemSeparator
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.data.MediaItem
import leonardolana.mediapickerlib.loader.AlbumRepository

class MediaPickerActivity : BaseActivity(), MediaPickerView {

    companion object {

        private const val KEY_ALBUM_NAME = "album_name"
        private const val KEY_SELECTION_LIMIT = "selection_limit"

        const val KEY_DATA = "data"

        fun launch(activity: Activity, album: MediaAlbum, selectionLimit: Int) {
            val intentPicker = Intent(activity, MediaPickerActivity::class.java)
            intentPicker.putExtra(KEY_ALBUM_NAME, album.name)
            intentPicker.putExtra(KEY_SELECTION_LIMIT, selectionLimit)
            activity.startActivityForResult(intentPicker, 999)
        }
    }

    private lateinit var mediaPresenter: MediaPickerPresenter
    private lateinit var album: MediaAlbum
    private lateinit var mediaAdapter: MediaRecyclerViewAdapter
    private var sendMenuButton: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val selectionLimit = intent.getIntExtra(KEY_SELECTION_LIMIT, SELECTION_LIMIT_LIMITLESS)

        mediaPresenter = MediaPickerPresenter(this, selectionLimit)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_media_picker)

        supportActionBar?.title = getString(R.string.choose_media)

        val albumName = intent.getStringExtra(KEY_ALBUM_NAME)
        album = AlbumRepository.instance.getAlbumByName(albumName)!!

        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(RecyclerViewItemSeparator())
        mediaAdapter = MediaRecyclerViewAdapter()
        mediaAdapter.setData(album)
        mediaAdapter.setOnItemClickListener(object : MediaRecyclerViewAdapter.OnItemClickListener {
            override fun onLongClick(position: Int, mediaItem: MediaItem) {
                mediaPresenter.onItemLongClick(position, mediaItem)
            }

            override fun onClick(position: Int, mediaItem: MediaItem) {
                mediaPresenter.onItemClick(position, mediaItem)
            }
        })

        recyclerView.adapter = mediaAdapter

    }

    override fun createPresenter(): BasePresenter {
        return mediaPresenter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.media_picker_menu, menu)
        sendMenuButton = menu?.findItem(R.id.send)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.send) {
            mediaPresenter.onSendClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun changeMenuVisibility(visible: Boolean) {
        sendMenuButton?.isVisible = visible
    }

    override fun setSelectedItems(selectedItems: MutableSet<Int>) {
        mediaAdapter.setSelectedItems(selectedItems)
    }

    override fun notifyItemChanged(position: Int) {
        mediaAdapter.notifyItemChanged(position)
    }

    override fun notifySelectionLimitReached() {
        //todo feedback
    }

    override fun closeWithResult(mediaItems: Collection<MediaItem>) {
        //todo make a common result builder
        val data = Intent()
        data.putParcelableArrayListExtra(KEY_DATA, ArrayList(mediaItems))
        setResult(Activity.RESULT_OK, data)
        finish()
    }


}