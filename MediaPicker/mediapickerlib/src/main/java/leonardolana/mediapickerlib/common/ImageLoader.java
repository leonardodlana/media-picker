package leonardolana.mediapickerlib.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class ImageLoader {

    private static final ImageLoader INSTANCE = new ImageLoader();

    public interface OnThumbnailLoaded {

        void onLoaded(Bitmap bitmap);

    }

    public static void loadThumbnail(final String path, final OnThumbnailLoaded listener) {
        // Work in Background
        RunnableExecutorImpl.getInstance().executeInBackground(new Runnable() {
            @Override
            public void run() {
                Bitmap tmpBitmap;

                if(Utils.isVideo(path)) {
                    tmpBitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                } else {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 4;
                    tmpBitmap = BitmapFactory.decodeFile(path, opts);
                }

                final Bitmap bitmap = tmpBitmap;

                // Respond in UI Thread to be able to modify views
                RunnableExecutorImpl.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onLoaded(bitmap);
                    }
                });
            }
        });
    }

}
