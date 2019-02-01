package leonardolana.mediapickerlib.common;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import leonardolana.mediapickerlib.data.MediaAlbum;
import leonardolana.mediapickerlib.data.MediaItem;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonardo Lana
 * Github: https://github.com/leonardodlana
 * <p>
 * Copyright 2018 Leonardo Lana
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Utils {

    public static Map<String, MediaAlbum> getAllMediaPathsOnGallery(Context context) {
        Map<String, MediaAlbum> result = new HashMap<>();

        try {
            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.MediaColumns.DATE_MODIFIED
            };

            Cursor cursor = context.getContentResolver().query(uri, projection, null,
                    null, MediaStore.MediaColumns.DATE_MODIFIED + " DESC");

            String[] projectionVideo = {MediaStore.MediaColumns.DATA,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.MediaColumns.DATE_MODIFIED
            };

            Cursor cursorVideos = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projectionVideo, null, null,
                    MediaStore.MediaColumns.DATE_MODIFIED + " DESC");

            int pathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            int albumNameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int dateModifiedColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);

            String itemPath;
            String albumName;
            long itemLastModifiedDate;
            MediaAlbum mediaAlbum;

            while (cursor.moveToNext()) {
                itemPath = cursor.getString(pathColumnIndex);
                albumName = cursor.getString(albumNameColumnIndex);
                itemLastModifiedDate = cursor.getLong(dateModifiedColumnIndex);

                mediaAlbum = result.get(albumName);
                if (mediaAlbum == null)
                    mediaAlbum = new MediaAlbum(albumName);

                mediaAlbum.addMedia(new MediaItem(itemPath, itemLastModifiedDate));
                result.put(albumName, mediaAlbum);
            }

            while (cursorVideos.moveToNext()) {
                itemPath = cursorVideos.getString(pathColumnIndex);
                albumName = cursorVideos.getString(albumNameColumnIndex);
                itemLastModifiedDate = cursor.getLong(dateModifiedColumnIndex);

                mediaAlbum = result.get(albumName);
                if (mediaAlbum == null)
                    mediaAlbum = new MediaAlbum(albumName);

                mediaAlbum.addMedia(new MediaItem(itemPath, itemLastModifiedDate));
                result.put(albumName, mediaAlbum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(MediaAlbum album : result.values()) {
            album.sortByLastDateModified();
        }

        return result;
    }

    public static boolean isVideo(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        if (TextUtils.isEmpty(extension))
            return false;

        return extension.equals("mp4");
    }

}
