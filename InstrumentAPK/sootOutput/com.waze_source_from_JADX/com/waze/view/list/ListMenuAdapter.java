package com.waze.view.list;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ResManager;
import java.util.List;

public class ListMenuAdapter extends BaseAdapter {
    public static final int LIST_MENU_ADAPTER_LABEL_FONT_SIZE_DEFAULT = 0;
    public static final int LIST_MENU_ADAPTER_ROW_HEIGHT_DEFAULT = 0;
    private final Activity mContext;
    private int mCount;
    private List<RowData> mRowData = null;

    public static class RowData {
        private Drawable mArrowImage;
        private String mLabel;
        private int mLabelFontSize;
        private Drawable mLeftImage;
        private Drawable mRightImage;
        private int mRowHeight;
        private Object mValue;

        public RowData(String label, Object value, String leftImage, String rightImage, String arrowImage) {
            setLabel(label);
            setValue(value);
            setLeftImage(leftImage);
            setRightImage(rightImage);
            setArrowImage(arrowImage);
        }

        public Object getValue() {
            return this.mValue;
        }

        public void setValue(Object value) {
            this.mValue = value;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public void setLabel(String label) {
            this.mLabel = label;
        }

        public int getRowHeight() {
            return this.mRowHeight;
        }

        public void setRowHeight(int rowHeight) {
            this.mRowHeight = rowHeight;
        }

        public int getLabelFontSize() {
            return this.mLabelFontSize;
        }

        public void setLabelFontSize(int labelFontSize) {
            this.mLabelFontSize = labelFontSize;
        }

        public void setLeftImage(String leftImage) {
            this.mLeftImage = ResManager.GetSkinDrawable(leftImage);
        }

        public void setRightImage(String rightImage) {
            this.mRightImage = ResManager.GetSkinDrawable(rightImage);
        }

        public void setArrowImage(String arrowImage) {
            this.mArrowImage = ResManager.GetSkinDrawable(arrowImage);
        }
    }

    protected static class RowHolder {
        ImageView arrow;
        TextView label;
        ImageView leftImage;
        ImageView rightImage;

        protected RowHolder() {
        }
    }

    public ListMenuAdapter(Activity context, List<RowData> list) throws IllegalArgumentException {
        if (context == null) {
            throw new IllegalArgumentException("ListMenuAdapter cannot be created without context");
        }
        this.mCount = list.size();
        this.mRowData = list;
        this.mContext = context;
    }

    public void updateRowData(List<RowData> list) {
        this.mCount = list.size();
        this.mRowData = list;
    }

    public int getCount() {
        return this.mCount;
    }

    public Object getItem(int position) {
        return this.mRowData.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RowHolder holder;
        RowData rowData = (RowData) this.mRowData.get(position);
        if (convertView == null) {
            convertView = this.mContext.getLayoutInflater().inflate(C1283R.layout.listmenu_row, null, true);
            holder = new RowHolder();
            holder.leftImage = (ImageView) convertView.findViewById(C1283R.id.listmenu_row_left_image);
            holder.label = (TextView) convertView.findViewById(C1283R.id.listmenu_row_label);
            holder.rightImage = (ImageView) convertView.findViewById(C1283R.id.listmenu_row_right_image);
            holder.arrow = (ImageView) convertView.findViewById(C1283R.id.listmenu_row_arrow_image);
            convertView.setTag(holder);
        } else {
            holder = (RowHolder) convertView.getTag();
        }
        holder.label.setText(rowData.getLabel());
        if (rowData.mLeftImage != null) {
            holder.leftImage.setImageDrawable(rowData.mLeftImage);
            holder.leftImage.setVisibility(0);
        } else {
            holder.leftImage.setVisibility(8);
        }
        if (rowData.mRightImage != null) {
            holder.rightImage.setImageDrawable(rowData.mRightImage);
            holder.rightImage.setVisibility(0);
        } else {
            holder.rightImage.setVisibility(8);
        }
        if (rowData.mArrowImage != null) {
            holder.arrow.setImageDrawable(rowData.mArrowImage);
            holder.arrow.setVisibility(0);
        } else {
            holder.arrow.setVisibility(8);
        }
        if (rowData.getRowHeight() != 0) {
        }
        if (rowData.getLabelFontSize() != 0) {
            holder.label.setTextSize(1, (float) rowData.getLabelFontSize());
        }
        return convertView;
    }
}
