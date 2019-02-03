package leonardolana.mediapickerlib

import java.util.ArrayList

interface MediaPickerView {

    fun setSelectedItems(selectedItems: MutableSet<Int>)
    fun notifyItemChanged(position: Int)
    fun changeMenuVisibility(visible: Boolean)
    fun closeWithResult(pathList: ArrayList<String>)

}