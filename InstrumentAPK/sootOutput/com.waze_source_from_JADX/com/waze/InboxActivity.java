package com.waze;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.ifs.ui.ActivityBase;
import com.waze.inbox.InboxNativeManager;
import com.waze.inbox.InboxRecycler;
import com.waze.inbox.InboxRecycler.InboxListener;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.title.TitleBar;

public class InboxActivity extends ActivityBase implements InboxListener {
    private static final long INBOX_ACTION_DELAY = 350;
    private RelativeLayout mInboxBottomBarContainer;
    private InboxRecycler mInboxRecycler;
    private NativeManager mNm;
    private RelativeLayout mRootContainer;
    private ImageView mSelectAllButton;
    private TextView mSelectAllLabel;
    private TitleBar mTitleBar;

    class C11491 implements OnClickListener {

        class C11481 implements Runnable {
            C11481() throws  {
            }

            public void run() throws  {
                InboxActivity.this.adjustSelectAllButton();
            }
        }

        C11491() throws  {
        }

        public void onClick(View v) throws  {
            if (InboxActivity.this.mInboxRecycler.isAllSelected()) {
                InboxActivity.this.mInboxRecycler.unselectAll();
                InboxActivity.this.onNoInboxItemsSelected();
                return;
            }
            InboxActivity.this.mInboxRecycler.selectAll();
            InboxActivity.this.postDelayed(new C11481(), InboxActivity.INBOX_ACTION_DELAY);
        }
    }

    class C11512 implements OnClickListener {

        class C11501 implements Runnable {
            C11501() throws  {
            }

            public void run() throws  {
                InboxActivity.this.onNoInboxItemsSelected();
            }
        }

        C11512() throws  {
        }

        public void onClick(View v) throws  {
            InboxActivity.this.mInboxRecycler.deleteSelected();
            InboxActivity.this.postDelayed(new C11501(), InboxActivity.INBOX_ACTION_DELAY);
        }
    }

    class C11523 implements OnClickListener {
        C11523() throws  {
        }

        public void onClick(View v) throws  {
            InboxActivity.this.mInboxRecycler.markSelectedAsRead();
        }
    }

    class C11534 implements Runnable {
        C11534() throws  {
        }

        public void run() throws  {
            InboxActivity.this.adjustSelectAllButton();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        setContentView(C1283R.layout.inbox_menu);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.inboxTitle);
        this.mTitleBar.init(this, NativeManager.getInstance().getLanguageString(12));
        this.mRootContainer = (RelativeLayout) findViewById(C1283R.id.inboxContainer);
        this.mInboxRecycler = (InboxRecycler) findViewById(C1283R.id.inboxRecycler);
        this.mInboxBottomBarContainer = (RelativeLayout) findViewById(C1283R.id.inboxBottomBarContainer);
        this.mSelectAllButton = (ImageView) findViewById(C1283R.id.btnSelectAll);
        this.mSelectAllLabel = (TextView) findViewById(C1283R.id.lblSelectAll);
        this.mInboxRecycler.setListener(this);
        View $r3 = findViewById(C1283R.id.btnDeleteSelected);
        View $r10 = findViewById(C1283R.id.btnMarkAsRead);
        this.mSelectAllButton.setOnClickListener(new C11491());
        $r3.setOnClickListener(new C11512());
        $r10.setOnClickListener(new C11523());
        initStrings();
        this.mInboxRecycler.reloadData();
    }

    private void initStrings() throws  {
        this.mSelectAllLabel.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_SELECT_ALL));
    }

    private void adjustSelectAllButton() throws  {
        if (this.mInboxRecycler.isAllSelected()) {
            this.mSelectAllButton.setImageResource(C1283R.drawable.inbox_unselectall_icon);
            this.mSelectAllLabel.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_SELECT_NONE));
            return;
        }
        this.mSelectAllButton.setImageResource(C1283R.drawable.inbox_selectall_icon);
        this.mSelectAllLabel.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_SELECT_ALL));
    }

    public void onInboxItemSelected() throws  {
        if (this.mInboxBottomBarContainer.getVisibility() != 0) {
            this.mInboxBottomBarContainer.setVisibility(0);
            this.mInboxBottomBarContainer.setTranslationY((float) PixelMeasure.dimension(C1283R.dimen.friendItemHeight));
            ViewPropertyAnimatorHelper.initAnimation(this.mInboxBottomBarContainer).translationY(0.0f).setListener(null);
            adjustSelectAllButton();
        }
    }

    public void onNoInboxItemsSelected() throws  {
        if (this.mInboxBottomBarContainer.getVisibility() != 8) {
            this.mInboxBottomBarContainer.setVisibility(8);
            ViewPropertyAnimatorHelper.initAnimation(this.mInboxBottomBarContainer).translationY((float) PixelMeasure.dimension(C1283R.dimen.friendItemHeight)).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mInboxBottomBarContainer));
            postDelayed(new C11534(), INBOX_ACTION_DELAY);
        }
    }

    public void onInboxLoaded() throws  {
        InboxNativeManager.getInstance().resetInboxBadge();
    }
}
