package leonardolana.mediapicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import leonardolana.mediapickerlib.MediaPickerAlbumActivity

class MainActivity : Activity() {

    companion object {
        const val REQUEST_CODE = 999
        const val KEY_DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentPicker = Intent(applicationContext, MediaPickerAlbumActivity::class.java)
        startActivityForResult(intentPicker, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedPaths = data?.getStringArrayListExtra(KEY_DATA)

            Toast.makeText(applicationContext, "" + selectedPaths?.size, Toast.LENGTH_SHORT).show()
        }
    }

}