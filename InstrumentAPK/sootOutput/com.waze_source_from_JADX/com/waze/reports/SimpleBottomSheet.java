package com.waze.reports;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;

public class SimpleBottomSheet extends BottomSheet implements Adapter {
    SimpleBottomSheetListener mListen;
    private final SimpleBottomSheetValue[] mOptions;

    public interface SimpleBottomSheetListener {
        void onComplete(SimpleBottomSheetValue simpleBottomSheetValue);
    }

    public static class SimpleBottomSheetValue {
        public boolean disabled = false;
        public String display;
        public Drawable icon;
        public int id;

        public SimpleBottomSheetValue(int optionId, String optionDisplay, Drawable drawable) {
            this.id = optionId;
            this.display = optionDisplay;
            this.icon = drawable;
        }

        public SimpleBottomSheetValue(int optionId, String optionDisplay, Drawable drawable, boolean isDisabled) {
            this.id = optionId;
            this.display = optionDisplay;
            this.icon = drawable;
            this.disabled = isDisabled;
        }
    }

    public SimpleBottomSheet(Context context, int dialogTitleDS, SimpleBottomSheetValue[] options, SimpleBottomSheetListener l) {
        this(context, dialogTitleDS, options, l, false);
    }

    public SimpleBottomSheet(Context context, int dialogTitleDS, SimpleBottomSheetValue[] options, SimpleBottomSheetListener l, boolean showCancelButton) {
        super(context, dialogTitleDS, Mode.GRID_SMALL, showCancelButton);
        super.setAdapter(this);
        this.mListen = l;
        this.mOptions = options;
    }

    public SimpleBottomSheet(Context context, Mode mode, int dialogTitleDS, SimpleBottomSheetValue[] options, SimpleBottomSheetListener l) {
        super(context, dialogTitleDS, mode);
        super.setAdapter(this);
        this.mListen = l;
        this.mOptions = options;
    }

    public int getCount() {
        return this.mOptions.length;
    }

    public void onConfigItem(int position, ItemDetails item) {
        item.setItem(this.mOptions[position].display, this.mOptions[position].icon);
        item.setDisabled(this.mOptions[position].disabled);
    }

    public void setListener(SimpleBottomSheetListener l) {
        this.mListen = l;
    }

    public void onClick(int itemId) {
        if (!this.mOptions[itemId].disabled && itemId >= 0 && itemId < this.mOptions.length) {
            this.mListen.onComplete(this.mOptions[itemId]);
        }
    }
}
