package leonardolana.mediapicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import leonardolana.mediapickerlib.MediaPickerAlbumActivity
import kotlinx.android.synthetic.main.activity_main.*
import leonardolana.mediapickerlib.data.MediaItem

class MainActivity : Activity() {

    companion object {
        const val REQUEST_CODE = 999
    }

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        adapter = RecyclerViewAdapter()

        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        buttonOpenPicker.setOnClickListener {
            openPicker()
        }
    }

    private fun openPicker() {
        MediaPickerAlbumActivity.launch(this, false, MediaPickerAlbumActivity.SELECTION_LIMIT_LIMITLESS, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedItems = data?.getParcelableArrayListExtra<MediaItem>(MediaPickerAlbumActivity.KEY_DATA)
            if (selectedItems != null) {
                adapter.setData(selectedItems)
            }
        }
    }

}