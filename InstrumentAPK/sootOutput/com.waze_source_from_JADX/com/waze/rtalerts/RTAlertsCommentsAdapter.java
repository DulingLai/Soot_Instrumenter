package com.waze.rtalerts;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ResManager;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RTAlertsCommentsAdapter extends BaseAdapter {
    private final Activity mContext;
    private RTAlertsCommentData[] mItems = null;

    public RTAlertsCommentsAdapter(Activity context) {
        this.mContext = context;
    }

    public int getCount() {
        if (this.mItems == null) {
            return 0;
        }
        return this.mItems.length;
    }

    public void update(RTAlertsCommentData[] data) {
        this.mItems = data;
    }

    public void updateFbImage(View commentView, byte[] image, int width, int height) {
        ImageView icon = (ImageView) commentView.findViewById(C1283R.id.rtalerts_comments_item_wazer_image);
        Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        bmp.copyPixelsFromBuffer(ByteBuffer.wrap(image));
        icon.setImageBitmap(bmp);
    }

    public Object getItem(int position) {
        if (this.mItems == null) {
            return null;
        }
        return this.mItems[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.mItems.length == 0) {
            return convertView;
        }
        RTAlertsCommentData itemData = this.mItems[position];
        View view = this.mContext.getLayoutInflater().inflate(C1283R.layout.rtalerts_comments_list_item, null, true);
        ImageView icon = (ImageView) view.findViewById(C1283R.id.rtalerts_comments_item_wazer_image);
        TextView description = (TextView) view.findViewById(C1283R.id.rtalerts_comments_item_comment);
        TextView postedby = (TextView) view.findViewById(C1283R.id.rtalerts_comments_item_username);
        TextView time = (TextView) view.findViewById(C1283R.id.rtalerts_comments_item_time);
        view.setId(itemData.mCommentID);
        if (itemData.mFacebookImage != null) {
            updateFbImage(view, itemData.mFacebookImage, itemData.mFacebookImageWidth, itemData.mFacebookImageHeight);
        } else if (itemData.mIcon != null) {
            icon.setImageDrawable(ResManager.GetSkinDrawable(itemData.mIcon));
        }
        description.setText(itemData.mDescription);
        postedby.setText(itemData.mReportedBy);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(itemData.m64Time * 1000);
        time.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
        return view;
    }
}
