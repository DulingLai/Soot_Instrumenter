package com.waze.inbox;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.ifs.ui.ActivityBase;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;

public class InboxMsgListAdapter extends BaseAdapter {
    private final int MIN_LIST_COUNT = 7;
    private final OnClickListener mCheckBoxContainerClickListener = new C17732();
    private final ArrayList<String> mCheckedIds = new ArrayList();
    private final OnCheckedChangeListener mCheckedListener = new C17721();
    private final ActivityBase mContext;
    private final ArrayList<InboxMessage> mItems;
    private boolean mLoading = false;
    private final IOnCheckedListener mUserCheckedListener;

    class C17721 implements OnCheckedChangeListener {
        C17721() throws  {
        }

        public void onCheckedChanged(CompoundButton $r1, boolean $z0) throws  {
            Integer $r4 = (Integer) $r1.getTag();
            String $r2 = ((InboxMessage) InboxMsgListAdapter.this.mItems.get($r4.intValue())).id;
            if ($z0) {
                if (!InboxMsgListAdapter.this.mCheckedIds.contains($r2)) {
                    InboxMsgListAdapter.this.mCheckedIds.add($r2);
                }
            } else if (InboxMsgListAdapter.this.mCheckedIds.contains($r2)) {
                InboxMsgListAdapter.this.mCheckedIds.remove($r2);
            }
            if (InboxMsgListAdapter.this.mUserCheckedListener != null) {
                InboxMsgListAdapter.this.mUserCheckedListener.onCheckClicked($r4.intValue(), $z0);
            }
        }
    }

    class C17732 implements OnClickListener {
        C17732() throws  {
        }

        public void onClick(View $r1) throws  {
            CheckBox $r3 = (CheckBox) $r1.getTag();
            $r3.setChecked(!$r3.isChecked());
        }
    }

    public interface IOnCheckedListener {
        void onCheckClicked(int i, boolean z) throws ;
    }

    private static class ItemHolder {
        CheckBox chkBox;
        RelativeLayout chkBoxContainer;
        RelativeLayout contentContainer;
        TextView preview;
        ProgressBar progress;
        View root;
        TextView time;
        TextView title;

        private ItemHolder() throws  {
        }
    }

    public long getItemId(int position) throws  {
        return 0;
    }

    public InboxMsgListAdapter(@Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/ArrayList", "<", "Lcom/waze/inbox/InboxMessage;", ">;", "Lcom/waze/inbox/InboxMsgListAdapter$IOnCheckedListener;", ")V"}) ActivityBase $r1, @Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/ArrayList", "<", "Lcom/waze/inbox/InboxMessage;", ">;", "Lcom/waze/inbox/InboxMsgListAdapter$IOnCheckedListener;", ")V"}) ArrayList<InboxMessage> $r2, @Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/ArrayList", "<", "Lcom/waze/inbox/InboxMessage;", ">;", "Lcom/waze/inbox/InboxMsgListAdapter$IOnCheckedListener;", ")V"}) IOnCheckedListener $r3) throws  {
        this.mContext = $r1;
        this.mItems = $r2;
        this.mUserCheckedListener = $r3;
    }

    public void prepare() throws  {
        clearChecked();
    }

    public void clearChecked() throws  {
        this.mCheckedIds.clear();
        refresh();
    }

    public void checkAll() throws  {
        clearChecked();
        for (int $i0 = 0; $i0 < this.mItems.size(); $i0++) {
            this.mCheckedIds.add(((InboxMessage) this.mItems.get($i0)).id);
        }
        refresh();
    }

    private void filterChecked() throws  {
        if (this.mCheckedIds != null) {
            Iterator $r3 = this.mCheckedIds.iterator();
            InboxMessage $r1 = new InboxMessage();
            while ($r3.hasNext()) {
                $r1.id = (String) $r3.next();
                if (!this.mItems.contains($r1)) {
                    $r3.remove();
                }
            }
        }
    }

    public void setLoading(boolean $z0) throws  {
        this.mLoading = $z0;
    }

    public void refresh() throws  {
        notifyDataSetChanged();
    }

    public int getCount() throws  {
        int $i0 = 0;
        if (this.mItems != null) {
            $i0 = this.mItems.size();
        }
        return $i0 > 7 ? $i0 : 7;
    }

    public Object getItem(int $i0) throws  {
        if ($i0 < this.mItems.size()) {
            return this.mItems.get($i0);
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r33, android.view.View r34, android.view.ViewGroup r35) throws  {
        /*
        r32 = this;
        r2 = 1;
        r3 = 0;
        if (r34 != 0) goto L_0x0125;
    L_0x0004:
        r0 = r32;
        r4 = r0.mContext;
        r5 = r4.getLayoutInflater();
        r6 = 2130903219; // 0x7f0300b3 float:1.741325E38 double:1.052806075E-314;
        r7 = 0;
        r0 = r35;
        r34 = r5.inflate(r6, r0, r7);
    L_0x0016:
        if (r3 != 0) goto L_0x00c9;
    L_0x0018:
        r3 = new com.waze.inbox.InboxMsgListAdapter$ItemHolder;
        r8 = 0;
        r3.<init>();
        r6 = 2131690838; // 0x7f0f0556 float:1.901073E38 double:1.0531952106E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r11 = r9;
        r11 = (android.widget.TextView) r11;
        r10 = r11;
        r3.title = r10;
        r6 = 2131690839; // 0x7f0f0557 float:1.9010733E38 double:1.053195211E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r12 = r9;
        r12 = (android.widget.TextView) r12;
        r10 = r12;
        r3.preview = r10;
        r6 = 2131690835; // 0x7f0f0553 float:1.9010725E38 double:1.053195209E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r13 = r9;
        r13 = (android.widget.TextView) r13;
        r10 = r13;
        r3.time = r10;
        r6 = 2131690832; // 0x7f0f0550 float:1.9010719E38 double:1.0531952076E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r3.root = r9;
        r6 = 2131690833; // 0x7f0f0551 float:1.901072E38 double:1.053195208E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r15 = r9;
        r15 = (android.widget.RelativeLayout) r15;
        r14 = r15;
        r3.contentContainer = r14;
        r6 = 2131690837; // 0x7f0f0555 float:1.9010729E38 double:1.05319521E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r17 = r9;
        r17 = (android.widget.CheckBox) r17;
        r16 = r17;
        r0 = r16;
        r3.chkBox = r0;
        r6 = 2131690836; // 0x7f0f0554 float:1.9010727E38 double:1.0531952096E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r18 = r9;
        r18 = (android.widget.RelativeLayout) r18;
        r14 = r18;
        r3.chkBoxContainer = r14;
        r6 = 2131690840; // 0x7f0f0558 float:1.9010735E38 double:1.0531952116E-314;
        r0 = r34;
        r9 = r0.findViewById(r6);
        r20 = r9;
        r20 = (android.widget.ProgressBar) r20;
        r19 = r20;
        r0 = r19;
        r3.progress = r0;
        r0 = r3.progress;
        r19 = r0;
        r6 = 1;
        r0 = r19;
        r0.setIndeterminate(r6);
        r14 = r3.chkBoxContainer;
        r0 = r32;
        r0 = r0.mCheckBoxContainerClickListener;
        r21 = r0;
        r14.setOnClickListener(r0);
        r0 = r3.chkBox;
        r16 = r0;
        r0 = r32;
        r0 = r0.mCheckedListener;
        r22 = r0;
        r0 = r16;
        r1 = r22;
        r0.setOnCheckedChangeListener(r1);
        r0 = r34;
        r0.setTag(r3);
    L_0x00c9:
        r0 = r32;
        r0 = r0.mItems;
        r23 = r0;
        r24 = r0.size();
        r0 = r33;
        r1 = r24;
        if (r0 >= r1) goto L_0x01db;
    L_0x00d9:
        r0 = r32;
        r0 = r0.mItems;
        r23 = r0;
        r1 = r33;
        r25 = r0.get(r1);
        r27 = r25;
        r27 = (com.waze.inbox.InboxMessage) r27;
        r26 = r27;
        r9 = r3.root;
        r6 = 0;
        r9.setVisibility(r6);
        r0 = r32;
        r0 = r0.mLoading;
        r28 = r0;
        if (r28 == 0) goto L_0x0136;
    L_0x00f9:
        r0 = r32;
        r0 = r0.mItems;
        r23 = r0;
        r24 = r0.size();
        r24 = r24 + -1;
        r0 = r33;
        r1 = r24;
        if (r0 != r1) goto L_0x0136;
    L_0x010b:
        r14 = r3.contentContainer;
        r6 = 8;
        r14.setVisibility(r6);
        r0 = r3.progress;
        r19 = r0;
        r6 = 0;
        r0 = r19;
        r0.setVisibility(r6);
        r9 = r3.root;
        r6 = 2131624072; // 0x7f0e0088 float:1.8875313E38 double:1.053162224E-314;
        r9.setBackgroundResource(r6);
        return r34;
    L_0x0125:
        r0 = r34;
        r25 = r0.getTag();
        goto L_0x012f;
    L_0x012c:
        goto L_0x0016;
    L_0x012f:
        r29 = r25;
        r29 = (com.waze.inbox.InboxMsgListAdapter.ItemHolder) r29;
        r3 = r29;
        goto L_0x012c;
    L_0x0136:
        r14 = r3.contentContainer;
        r6 = 0;
        r14.setVisibility(r6);
        r0 = r3.progress;
        r19 = r0;
        r6 = 8;
        r0 = r19;
        r0.setVisibility(r6);
        r10 = r3.title;
        r0 = r26;
        r0 = r0.title;
        r30 = r0;
        r10.setText(r0);
        r10 = r3.preview;
        r0 = r26;
        r0 = r0.preview;
        r30 = r0;
        r10.setText(r0);
        r10 = r3.time;
        r0 = r26;
        r0 = r0.sentFString;
        r30 = r0;
        r10.setText(r0);
        r0 = r26;
        r0 = r0.unread;
        r28 = r0;
        if (r28 == 0) goto L_0x01c4;
    L_0x0170:
        r10 = r3.title;
        r0 = r32;
        r4 = r0.mContext;
        r6 = 2131427644; // 0x7f0b013c float:1.847691E38 double:1.0530651755E-314;
        r10.setTextAppearance(r4, r6);
        r9 = r3.root;
        r6 = 2131624127; // 0x7f0e00bf float:1.8875425E38 double:1.053162251E-314;
        r9.setBackgroundResource(r6);
    L_0x0184:
        r14 = r3.chkBoxContainer;
        r0 = r3.chkBox;
        r16 = r0;
        r14.setTag(r0);
        r0 = r3.chkBox;
        r16 = r0;
        r0 = r33;
        r31 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r31;
        r0.setTag(r1);
        r0 = r32;
        r0 = r0.mCheckedIds;
        r23 = r0;
        if (r23 == 0) goto L_0x01d9;
    L_0x01a6:
        r0 = r32;
        r0 = r0.mCheckedIds;
        r23 = r0;
        r0 = r26;
        r0 = r0.id;
        r30 = r0;
        r0 = r23;
        r1 = r30;
        r28 = r0.contains(r1);
        if (r28 == 0) goto L_0x01d9;
    L_0x01bc:
        r0 = r3.chkBox;
        r16 = r0;
        r0.setChecked(r2);
        return r34;
    L_0x01c4:
        r10 = r3.title;
        r0 = r32;
        r4 = r0.mContext;
        r6 = 2131427646; // 0x7f0b013e float:1.8476914E38 double:1.0530651765E-314;
        r10.setTextAppearance(r4, r6);
        r9 = r3.root;
        r6 = 2131624072; // 0x7f0e0088 float:1.8875313E38 double:1.053162224E-314;
        r9.setBackgroundResource(r6);
        goto L_0x0184;
    L_0x01d9:
        r2 = 0;
        goto L_0x01bc;
    L_0x01db:
        r9 = r3.root;
        r6 = 4;
        r9.setVisibility(r6);
        return r34;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.inbox.InboxMsgListAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public ArrayList<String> getCheckedIds() throws  {
        return this.mCheckedIds;
    }
}
