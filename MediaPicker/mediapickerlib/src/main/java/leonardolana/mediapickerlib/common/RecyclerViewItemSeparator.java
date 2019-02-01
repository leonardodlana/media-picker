package leonardolana.mediapickerlib.common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewItemSeparator extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = 2;
        outRect.top = 2;
        outRect.right = 2;
        outRect.bottom = 2;
    }
}
