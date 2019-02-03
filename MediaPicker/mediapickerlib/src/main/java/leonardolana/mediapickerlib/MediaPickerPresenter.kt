package leonardolana.mediapickerlib

import leonardolana.mediapickerlib.common.BasePresenter
import leonardolana.mediapickerlib.data.MediaItem
import java.util.ArrayList

class MediaPickerPresenter(private val mediaPickerView: MediaPickerView) : BasePresenter() {

    private var selectedItems: MutableMap<Int,MediaItem> = HashMap<Int,MediaItem>()

    fun onItemLongClick(position: Int, mediaItem: MediaItem) {
        if(selectedItems.containsKey(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.put(position, mediaItem)
        }

        mediaPickerView.setSelectedItems(selectedItems.keys)
        mediaPickerView.changeMenuVisibility(selectedItems.isNotEmpty())
        mediaPickerView.notifyItemChanged(position)
    }

    fun onItemClick(position: Int, mediaItem: MediaItem) {
        if(selectedItems.isNotEmpty()) {
            if(selectedItems.containsKey(position)) {
                selectedItems.remove(position)
            } else {
                selectedItems.put(position, mediaItem)
            }

            mediaPickerView.setSelectedItems(selectedItems.keys)
            mediaPickerView.changeMenuVisibility(selectedItems.isNotEmpty())
            mediaPickerView.notifyItemChanged(position)
        }
    }

    fun onSendClick() {
        val pathList = ArrayList<String>()
        selectedItems.values.forEach {
            pathList.add(it.path)
        }

        mediaPickerView.closeWithResult(pathList)
    }

    override fun onDestroy() {

    }

}