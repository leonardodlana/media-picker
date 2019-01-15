package leonardolana.mediapickerlib

import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.common.Utils
import leonardolana.mediapickerlib.permission.Permission
import leonardolana.mediapickerlib.permission.PermissionHelper
import leonardolana.mediapickerlib.permission.PermissionWatcher

class MediaPickerPresenter(private val mediaPickerView: MediaPickerView) : BasePresenter(), PermissionWatcher {

    override fun onResume() {
        if(!PermissionHelper.isPermissionGranted(mediaPickerView.getContext(), Permission.READ_STORAGE)) {
            mediaPickerView.askPermission(Permission.READ_STORAGE)
            PermissionHelper.addPermissionWatcher(this)
        } else {
            load()
        }
    }

    private fun load() {
        val albums = Utils.getAllMediaPathsOnGallery(mediaPickerView.getContext())
        mediaPickerView.onAlbumsLoaded(albums)
    }

    override fun onPermissionGranted(permission: Permission?) {
        load()
    }

    override fun onPermissionDenied(permission: Permission?) {
    }

    override fun onDestroy() {
    }

}