package leonardolana.mediapickerlib

import android.content.Context
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.permission.Permission

interface MediaPickerView {

    fun getContext(): Context?
    fun askPermission(permission: Permission)
    fun onAlbumsLoaded(albums: Map<String, MediaAlbum>)

}