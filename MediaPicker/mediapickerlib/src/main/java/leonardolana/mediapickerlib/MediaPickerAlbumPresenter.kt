package leonardolana.mediapickerlib

import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.common.Utils
import leonardolana.mediapickerlib.data.MediaAlbum
import leonardolana.mediapickerlib.loader.AlbumRepository
import leonardolana.mediapickerlib.permission.Permission
import leonardolana.mediapickerlib.permission.PermissionHelper
import leonardolana.mediapickerlib.permission.PermissionWatcher

class MediaPickerAlbumPresenter(private val mediaPickerAlbumView: MediaPickerAlbumView) : BasePresenter(), PermissionWatcher {

    private fun load() {
        AlbumRepository.instance.load(mediaPickerAlbumView.getContext(), object : AlbumRepository.OnAlbumListener {
            override fun onLoad(albums: Map<String, MediaAlbum>) {
                mediaPickerAlbumView.onAlbumsLoaded(albums)
            }

        })
    }

    override fun onResume() {
        if(!PermissionHelper.isPermissionGranted(mediaPickerAlbumView.getContext(), Permission.READ_STORAGE)) {
            mediaPickerAlbumView.askPermission(Permission.READ_STORAGE)
            PermissionHelper.addPermissionWatcher(this)
        } else {
            load()
        }
    }

    override fun onPermissionGranted(permission: Permission?) {
        load()
    }

    override fun onPermissionDenied(permission: Permission?) {
    }

    fun onAlbumClick(album: MediaAlbum) {
        mediaPickerAlbumView.openAlbum(album)
    }

    override fun onDestroy() {
    }

}