package com.waze.rtalerts;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.ResManager;
import java.util.ArrayList;

public class RTAlertsListAdapter extends BaseAdapter {
    private final Activity mContext;
    private ArrayList<RTAlertsAlertData> mItems;

    private static class ItemHolder {
        TextView alertBy;
        TextView alertDesc;
        TextView alertLoc;
        TextView alertType;
        TextView comments;
        View container;
        TextView distance;
        TextView distanceNote;
        TextView distanceUnit;
        View groupContainer;
        TextView groupDesc;
        ImageView groupIcon;
        ImageView icon;
        TextView thanks;

        private ItemHolder() {
        }
    }

    public RTAlertsListAdapter(Activity context) {
        this.mContext = context;
    }

    public void updateList(ArrayList<RTAlertsAlertData> data) {
        this.mItems = data;
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
        RTAlertsAlertData itemData = (RTAlertsAlertData) this.mItems.get(position);
        if (convertView == null) {
            convertView = this.mContext.getLayoutInflater().inflate(C1283R.layout.rtalerts_list_item, null, true);
        }
        return getView(this.mContext, convertView, itemData);
    }

    public static View getView(final Activity context, View convertView, final RTAlertsAlertData alertData) {
        RTAlertsNativeManager ntvMgr = RTAlertsNativeManager.getInstance();
        ItemHolder holder = (ItemHolder) convertView.getTag();
        if (holder == null) {
            holder = new ItemHolder();
            holder.container = convertView.findViewById(C1283R.id.rtalerts_list_item_container);
            holder.icon = (ImageView) convertView.findViewById(C1283R.id.rtalerts_list_item_image);
            holder.alertType = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_alert_type);
            holder.alertLoc = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_alert_loc);
            holder.alertBy = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_alert_by);
            holder.groupIcon = (ImageView) convertView.findViewById(C1283R.id.rtalerts_list_item_group_image);
            holder.groupDesc = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_group_desc);
            holder.groupContainer = convertView.findViewById(C1283R.id.rtalerts_list_item_groups_container);
            holder.comments = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_comments);
            holder.thanks = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_thanks);
            holder.alertDesc = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_desc);
            holder.distance = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_distance);
            holder.distanceUnit = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_distance_unit);
            holder.distanceNote = (TextView) convertView.findViewById(C1283R.id.rtalerts_list_item_distance_note);
            convertView.setTag(holder);
        }
        int res_id = ResManager.GetDrawableId(alertData.mIcon.toLowerCase());
        if (res_id > 0) {
            holder.icon.setImageResource(res_id);
        }
        holder.container.setPadding(0, 0, 0, 0);
        holder.alertType.setText(alertData.mTitle);
        holder.alertLoc.setText(alertData.mLocationStr);
        if (alertData.mDescription == null || alertData.mDescription.length() == 0) {
            holder.alertDesc.setVisibility(8);
        } else {
            holder.alertDesc.setVisibility(0);
            holder.alertDesc.setText(alertData.mDescription);
        }
        holder.alertBy.setText(alertData.mReportedBy + " | " + alertData.mTimeRelative);
        if (alertData.mGroupName != null) {
            holder.groupContainer.setVisibility(0);
            holder.groupContainer.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, RTAlertsGroupActivity.class);
                    intent.putExtra("url", alertData.mGroupUrl);
                    context.startActivityForResult(intent, MainActivity.RTALERTS_REQUEST_CODE);
                }
            });
            holder.groupIcon.setImageDrawable(ResManager.GetSkinDrawable(alertData.mGroupIcon + ResManager.mImageExtension));
            holder.groupDesc.setText(alertData.mGroupName);
        } else {
            holder.groupContainer.setVisibility(8);
        }
        holder.comments.setText(ntvMgr.getLangStr(C1283R.string.rtalerts_list_comments) + ": " + alertData.mNumComments);
        holder.thanks.setText(" " + ntvMgr.getLangStr(C1283R.string.rtalerts_list_thanks) + ": " + alertData.mNumThumbsUp);
        holder.distance.setText(alertData.mDistanceStr);
        holder.distanceUnit.setText(alertData.mUnit);
        holder.distanceNote.setText(ntvMgr.getLangStr(C1283R.string.rtalerts_list_away));
        return convertView;
    }
}
