package leonardolana.mediapicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import leonardolana.mediapickerlib.MediaPickerActivity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentPicker = Intent(applicationContext, MediaPickerActivity::class.java)
        startActivity(intentPicker)
    }

}