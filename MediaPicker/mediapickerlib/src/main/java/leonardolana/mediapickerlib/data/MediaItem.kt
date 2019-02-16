package leonardolana.mediapickerlib.data

import android.os.Parcel
import android.os.Parcelable
import java.io.File

class MediaItem(val path: String, val lastModifiedDate: Long) : Parcelable {

    val name : String = path.split(File.separator).last()
    var isVideo : Boolean = false
        private set

    constructor(path: String, lastModifiedDate: Long, isVideo : Boolean) : this(path, lastModifiedDate) {
        this.isVideo = isVideo
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readInt() == 1
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeLong(lastModifiedDate)
        parcel.writeInt(if (isVideo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaItem> {

        override fun createFromParcel(parcel: Parcel): MediaItem {
            return MediaItem(parcel)
        }

        override fun newArray(size: Int): Array<MediaItem?> {
            return arrayOfNulls(size)
        }
    }

}