package com.waze.share;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CheckedRelativeLayout;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import java.util.ArrayList;

public class ShareFbWithListAdapter extends BaseAdapter {
    private final ActivityBase mContext;
    private ArrayList<ShareFbFriend> mItems;

    private static class ItemHolder {
        ImageView image;
        CheckedTextView name;
        CheckedRelativeLayout root;

        private ItemHolder() {
        }
    }

    public ShareFbWithListAdapter(ActivityBase context) {
        this.mContext = context;
    }

    public void updateList(ArrayList<ShareFbFriend> data) {
        this.mItems = data;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.mItems == null) {
            return 0;
        }
        return this.mItems.size();
    }

    public Object getItem(int position) {
        return this.mItems.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ShareFbFriend itemData = (ShareFbFriend) this.mItems.get(position);
        if (convertView == null) {
            convertView = this.mContext.getLayoutInflater().inflate(C1283R.layout.share_fb_with_item, parent, false);
        }
        return getView(this.mContext, convertView, itemData);
    }

    public void setChecked(int index, boolean checked) {
    }

    public void toggleChecked(int index) {
    }

    public static View getView(ActivityBase context, View convertView, ShareFbFriend itemData) {
        if (convertView == null) {
            return null;
        }
        ItemHolder holder = (ItemHolder) convertView.getTag();
        if (holder == null) {
            holder = new ItemHolder();
            holder.image = (ImageView) convertView.findViewById(C1283R.id.shareFbWithItemImage);
            holder.name = (CheckedTextView) convertView.findViewById(C1283R.id.checked_text);
            holder.root = (CheckedRelativeLayout) convertView.findViewById(C1283R.id.shareFbWithItemRoot);
            convertView.setTag(holder);
        }
        holder.name.setText(itemData.name);
        final ImageView imageView = holder.image;
        ImageRepository.instance.getImage(itemData.imageUrl, new ImageRepository$ImageRepositoryListener() {
            public void onImageRetrieved(final Bitmap bitmap) {
                AppService.Post(new Runnable() {
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        return convertView;
    }
}
