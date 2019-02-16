package leonardolana.mediapickerlib.common;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import leonardolana.mediapickerlib.data.MediaAlbum;
import leonardolana.mediapickerlib.data.MediaItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

    public interface OnSaveBitmapListener {
        void onSuccess(String path, long lastModified);
        void onError();
    }

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
                itemLastModifiedDate = cursorVideos.getLong(dateModifiedColumnIndex);

                mediaAlbum = result.get(albumName);
                if (mediaAlbum == null)
                    mediaAlbum = new MediaAlbum(albumName);

                mediaAlbum.addMedia(new MediaItem(itemPath, itemLastModifiedDate));
                result.put(albumName, mediaAlbum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (MediaAlbum album : result.values()) {
            album.sortByLastDateModified();
        }

        return result;
    }

    public static File createBitmapFile(Context context) {
        String timeStampString = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        try {
            File image = File.createTempFile(
                    timeStampString,
                    ".jpg",
                    context.getFilesDir()
            );

            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveBitmap(final Bitmap bitmap, final @NonNull OnSaveBitmapListener listener) {
        RunnableExecutorImpl.getInstance().executeInBackground(new Runnable() {
            @Override
            public void run() {
                long timestamp = System.currentTimeMillis();
                String timeStampString = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                FileOutputStream fileOutputStream = null;

                try {
                    File image = File.createTempFile(
                            timeStampString,
                            ".jpg",
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    );

                    fileOutputStream = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    listener.onSuccess(image.getAbsolutePath(), timestamp);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onError();
                } finally {
                    if(fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception ignored) {}
                    }
                }
            }
        });
    }

    public static boolean isVideo(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        if (TextUtils.isEmpty(extension))
            return false;

        return extension.equals("mp4");
    }

    /** The picker is not providing a content library, I just want
     *  to be able to save a freaking file on the external storage.
     */
    public static void disableChecks() {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
