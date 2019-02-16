package leonardolana.mediapickerlib

import leonardolana.mediapickerlib.data.MediaItem

interface MediaPickerView {

    fun setSelectedItems(selectedItems: MutableSet<Int>)
    fun notifyItemChanged(position: Int)
    fun changeMenuVisibility(visible: Boolean)
    fun closeWithResult(mediaItems: Collection<MediaItem>)

}