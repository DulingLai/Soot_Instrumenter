package com.waze.navigate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.DownloadResCallback;
import com.waze.ResManager;
import com.waze.ResourceDownloadType;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;

public class SearchResultItemViewBase extends FrameLayout {
    static final int EXTRA_PADDING = 8;
    private static final int ICON_COLOR = -4529427;
    static final int NORMAL_PADDING = 4;
    protected View content;
    protected AddressItem mAddressItem;
    private TextView mDetailsLabel;
    private String mIcon;
    protected ImageView mIconImage;
    private SearchResultItemListener mListener;
    protected ViewGroup mMainContainer;
    private ViewGroup mMainWrapper;
    protected TextView mTitleLabel;

    public interface SearchResultItemListener {
        void onSearchResultClick(AddressItem addressItem) throws ;
    }

    class C21521 implements OnClickListener {
        C21521() throws  {
        }

        public void onClick(View v) throws  {
            SearchResultItemViewBase.this.mListener.onSearchResultClick(SearchResultItemViewBase.this.mAddressItem);
        }
    }

    class C21532 implements ImageRequestListener {
        C21532() throws  {
        }

        public void onImageLoadComplete(Bitmap $r1, Object $r2, long duration) throws  {
            if ($r2 == SearchResultItemViewBase.this.mAddressItem) {
                SearchResultItemViewBase.this.mIconImage.setImageBitmap($r1);
            }
        }

        public void onImageLoadFailed(Object token, long duration) throws  {
        }
    }

    class C21543 implements DownloadResCallback {
        C21543() throws  {
        }

        public void downloadResCallback(int $i0) throws  {
            if ($i0 > 0) {
                SearchResultItemViewBase.this.setFields();
            }
        }
    }

    public SearchResultItemViewBase(Context $r1) throws  {
        this($r1, null);
    }

    public SearchResultItemViewBase(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SearchResultItemViewBase(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    protected void init() throws  {
        this.mMainWrapper = (ViewGroup) this.content.findViewById(C1283R.id.mainWrapper);
        this.mMainContainer = (ViewGroup) this.content.findViewById(C1283R.id.mainContainer);
        this.mIconImage = (ImageView) this.content.findViewById(C1283R.id.imgIcon);
        this.mTitleLabel = (TextView) this.content.findViewById(C1283R.id.lblTitle);
        this.mDetailsLabel = (TextView) this.content.findViewById(C1283R.id.lblDetails);
        if (VERSION.SDK_INT >= 21) {
            this.mMainContainer.setBackground(new RippleDrawable(ColorStateList.valueOf(getResources().getColor(C1283R.color.BlueWhaleLight)), getResources().getDrawable(C1283R.drawable.inbox_item_bg, null), null));
        }
        this.mMainContainer.setOnClickListener(new C21521());
        addView(this.content);
        setWillNotDraw(false);
    }

    public void setIsFirstAdItem(boolean $z0, int position) throws  {
        this.mMainWrapper.setBackgroundColor(getResources().getColor($z0 ? C1283R.color.BlueDeepLight : C1283R.color.BlueWhale));
    }

    public void setListener(SearchResultItemListener $r1) throws  {
        this.mListener = $r1;
    }

    public void setFirstItem() throws  {
        setVerticalPadding(true, false);
    }

    public void setLastItem() throws  {
        setVerticalPadding(false, true);
    }

    public void setOnlyItem() throws  {
        setVerticalPadding(true, true);
    }

    public void setMiddleItem() throws  {
        setVerticalPadding(false, false);
    }

    public void setVerticalPadding(boolean extraPaddingTop, boolean extraPaddingBottom) throws  {
    }

    public AddressItem getAddressItem() throws  {
        return this.mAddressItem;
    }

    public void setAddressItem(AddressItem $r1) throws  {
        this.mAddressItem = $r1;
        setFields();
    }

    protected void setFields() throws  {
        if (TextUtils.isEmpty(this.mAddressItem.getTitle())) {
            this.mTitleLabel.setVisibility(8);
        } else {
            this.mTitleLabel.setText(this.mAddressItem.getTitle());
        }
        if (this.mAddressItem.getAddress().equals("")) {
            this.mDetailsLabel.setVisibility(8);
        } else {
            this.mDetailsLabel.setVisibility(0);
            this.mDetailsLabel.setText(this.mAddressItem.getAddress());
        }
        setItemImage();
    }

    public void setIcon(String $r1) throws  {
        this.mIcon = $r1;
    }

    protected void setItemImage() throws  {
        Drawable $r4;
        this.mIconImage.setImageBitmap(null);
        if (!(this.mIcon == null || this.mAddressItem.mSpecificIcon)) {
            $r4 = ResManager.GetSkinDrawable(this.mIcon);
            if ($r4 != null) {
                $r4 = $r4.mutate();
                $r4.setColorFilter(ICON_COLOR, Mode.SRC_ATOP);
                this.mIconImage.setImageDrawable($r4);
                return;
            }
        }
        if (!TextUtils.isEmpty(this.mAddressItem.mImageURL)) {
            VolleyManager.getInstance().loadImageFromUrl(this.mAddressItem.mImageURL, new C21532(), this.mAddressItem);
        } else if (this.mAddressItem.hasIcon()) {
            if (this.mAddressItem.mSpecificIcon) {
                this.mIconImage.setImageDrawable(ResManager.GetSkinDrawable(this.mAddressItem.getIcon() + ResManager.mImageExtension));
                return;
            }
            $r4 = ResManager.GetSkinDrawable(this.mAddressItem.getIcon() + ResManager.mImageExtension);
            if ($r4 != null) {
                $r4 = $r4.mutate();
                $r4.setColorFilter(ICON_COLOR, Mode.SRC_ATOP);
                this.mIconImage.setImageDrawable($r4);
            }
        } else if (this.mAddressItem.getImage() != null) {
            this.mIconImage.setImageResource(this.mAddressItem.getImage().intValue());
        } else {
            ResManager.downloadRes(ResourceDownloadType.RES_DOWNLOAD_IMAGE_JAVA.getValue(), this.mAddressItem.getIcon() + ResManager.mImageExtension, new C21543());
        }
    }
}
