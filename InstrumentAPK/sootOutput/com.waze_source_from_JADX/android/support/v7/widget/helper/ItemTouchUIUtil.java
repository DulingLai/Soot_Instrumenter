package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface ItemTouchUIUtil {
    void clearView(View view) throws ;

    void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) throws ;

    void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) throws ;

    void onSelected(View view) throws ;
}
