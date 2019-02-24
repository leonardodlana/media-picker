package leonardolana.mediapickerlib

import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.data.MediaItem

class MediaPickerPresenter(private val mediaPickerView: MediaPickerView,
                           private val selectionLimit: Int) : BasePresenter() {

    private var selectedItems: MutableMap<Int,MediaItem> = HashMap<Int,MediaItem>()

    fun onItemLongClick(position: Int, mediaItem: MediaItem) {
        changeItemStatus(position, mediaItem)
    }

    fun onItemClick(position: Int, mediaItem: MediaItem) {
        if(selectedItems.isNotEmpty()) {
            changeItemStatus(position, mediaItem)
        }
    }

    fun onSendClick() {
        mediaPickerView.closeWithResult(selectedItems.values)
    }

    override fun onDestroy() {

    }

    private fun isLimitReached(): Boolean {
        if(selectionLimit != MediaPickerAlbumActivity.SELECTION_LIMIT_LIMITLESS) {
            if(selectedItems.size + 1 > selectionLimit) {
                mediaPickerView.notifySelectionLimitReached()
                return true
            }
        }

        return false
    }


    private fun changeItemStatus(position: Int, mediaItem: MediaItem) {
        if (selectedItems.containsKey(position)) {
            selectedItems.remove(position)
        } else {

            if(isLimitReached())
                return

            selectedItems[position] = mediaItem
        }

        mediaPickerView.setSelectedItems(selectedItems.keys)
        mediaPickerView.changeMenuVisibility(selectedItems.isNotEmpty())
        mediaPickerView.notifyItemChanged(position)
    }

}