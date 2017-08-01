package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleCursorAdapter extends ResourceCursorAdapter {
    private CursorToStringConverter mCursorToStringConverter;
    protected int[] mFrom;
    String[] mOriginalFrom;
    private int mStringConversionColumn = -1;
    protected int[] mTo;
    private ViewBinder mViewBinder;

    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor) throws ;
    }

    public interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int i) throws ;
    }

    @Deprecated
    public SimpleCursorAdapter(@Deprecated Context $r1, @Deprecated int $i0, @Deprecated Cursor $r2, @Deprecated String[] $r3, @Deprecated int[] $r4) throws  {
        super($r1, $i0, $r2);
        this.mTo = $r4;
        this.mOriginalFrom = $r3;
        findColumns($r3);
    }

    public SimpleCursorAdapter(Context $r1, int $i0, Cursor $r2, String[] $r3, int[] $r4, int $i1) throws  {
        super($r1, $i0, $r2, $i1);
        this.mTo = $r4;
        this.mOriginalFrom = $r3;
        findColumns($r3);
    }

    public void bindView(View $r1, Context context, Cursor $r3) throws  {
        ViewBinder $r4 = this.mViewBinder;
        int $i0 = this.mTo.length;
        int[] $r5 = this.mFrom;
        int[] $r6 = this.mTo;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r7 = $r1.findViewById($r6[$i1]);
            if ($r7 != null) {
                boolean $z0 = false;
                if ($r4 != null) {
                    $z0 = $r4.setViewValue($r7, $r3, $r5[$i1]);
                }
                if ($z0) {
                    continue;
                } else {
                    String $r8 = $r3.getString($r5[$i1]);
                    String $r9 = $r8;
                    if ($r8 == null) {
                        $r9 = "";
                    }
                    if ($r7 instanceof TextView) {
                        setViewText((TextView) $r7, $r9);
                    } else if ($r7 instanceof ImageView) {
                        setViewImage((ImageView) $r7, $r9);
                    } else {
                        throw new IllegalStateException($r7.getClass().getName() + " is not a " + " view that can be bounds by this SimpleCursorAdapter");
                    }
                }
            }
        }
    }

    public ViewBinder getViewBinder() throws  {
        return this.mViewBinder;
    }

    public void setViewBinder(ViewBinder $r1) throws  {
        this.mViewBinder = $r1;
    }

    public void setViewImage(ImageView $r1, String $r2) throws  {
        try {
            $r1.setImageResource(Integer.parseInt($r2));
        } catch (NumberFormatException e) {
            $r1.setImageURI(Uri.parse($r2));
        }
    }

    public void setViewText(TextView $r1, String $r2) throws  {
        $r1.setText($r2);
    }

    public int getStringConversionColumn() throws  {
        return this.mStringConversionColumn;
    }

    public void setStringConversionColumn(int $i0) throws  {
        this.mStringConversionColumn = $i0;
    }

    public CursorToStringConverter getCursorToStringConverter() throws  {
        return this.mCursorToStringConverter;
    }

    public void setCursorToStringConverter(CursorToStringConverter $r1) throws  {
        this.mCursorToStringConverter = $r1;
    }

    public CharSequence convertToString(Cursor $r1) throws  {
        if (this.mCursorToStringConverter != null) {
            return this.mCursorToStringConverter.convertToString($r1);
        }
        if (this.mStringConversionColumn > -1) {
            return $r1.getString(this.mStringConversionColumn);
        }
        return super.convertToString($r1);
    }

    private void findColumns(String[] $r1) throws  {
        if (this.mCursor != null) {
            int $i0 = $r1.length;
            if (this.mFrom == null || this.mFrom.length != $i0) {
                this.mFrom = new int[$i0];
            }
            for (int $i2 = 0; $i2 < $i0; $i2++) {
                this.mFrom[$i2] = this.mCursor.getColumnIndexOrThrow($r1[$i2]);
            }
            return;
        }
        this.mFrom = null;
    }

    public Cursor swapCursor(Cursor $r1) throws  {
        $r1 = super.swapCursor($r1);
        findColumns(this.mOriginalFrom);
        return $r1;
    }

    public void changeCursorAndColumns(Cursor $r1, String[] $r2, int[] $r3) throws  {
        this.mOriginalFrom = $r2;
        this.mTo = $r3;
        super.changeCursor($r1);
        findColumns(this.mOriginalFrom);
    }
}
