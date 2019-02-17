package leonardolana.mediapickerlib

import android.graphics.Bitmap
import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.common.Utils
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.data.MediaItem
import leonardolana.mediapickerlib.loader.AlbumRepository
import leonardolana.mediapickerlib.permission.Permission
import leonardolana.mediapickerlib.permission.PermissionHelper
import leonardolana.mediapickerlib.permission.PermissionWatcher

class MediaPickerAlbumPresenter(private val mediaPickerAlbumView: MediaPickerAlbumView, private val showOnlyPictures: Boolean) : BasePresenter(), PermissionWatcher {

    private fun load() {
        AlbumRepository.instance.load(mediaPickerAlbumView.getContext(), showOnlyPictures, object : AlbumRepository.OnAlbumListener {
            override fun onLoad(albums: Map<String, MediaAlbum>) {
                mediaPickerAlbumView.onAlbumsLoaded(albums)
            }

        })
    }

    override fun onResume() {
        if(!PermissionHelper.isPermissionGranted(mediaPickerAlbumView.getContext(), Permission.WRITE_STORAGE)) {
            mediaPickerAlbumView.askPermission(Permission.WRITE_STORAGE)
            PermissionHelper.addPermissionWatcher(this)
        } else {
            load()
        }
    }

    override fun onPermissionGranted(permission: Permission?) {
        when(permission) {
            Permission.WRITE_STORAGE -> load()
            Permission.CAMERA -> mediaPickerAlbumView.openCamera()
        }
    }

    override fun onPermissionDenied(permission: Permission?) {
        //todo
    }

    fun onAlbumClick(album: MediaAlbum) {
        mediaPickerAlbumView.openAlbum(album)
    }

    fun onCameraClick() {
        if(PermissionHelper.isPermissionGranted(mediaPickerAlbumView.getContext(), Permission.CAMERA))
            mediaPickerAlbumView.openCamera()
        else {
            mediaPickerAlbumView.askPermission(Permission.CAMERA)
            PermissionHelper.addPermissionWatcher(this)
        }
    }

    fun onPictureTaken(bitmapPath: String) {
        val mediaItem = MediaItem(bitmapPath, System.currentTimeMillis())
        mediaPickerAlbumView.finishWithResult(mediaItem)
    }

    override fun onDestroy() {
    }

}