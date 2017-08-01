package com.waze.mywaze;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.DownloadResCallback;
import com.waze.ResManager;
import com.waze.ResourceDownloadType;
import com.waze.utils.ImageUtils;

public class MyStoreListItemView extends FrameLayout {
    private MyStoreListItemListener mListener;
    private ImageView mMoreButton;
    private View mSeparatorView;
    private ImageView mStoreIconImage;
    private MyStoreModel mStoreModel;
    private TextView mStoreNameLabel;

    class C19781 implements OnClickListener {
        C19781() throws  {
        }

        public void onClick(View v) throws  {
            if (MyStoreListItemView.this.mListener != null) {
                MyStoreListItemView.this.mListener.onOpenBottomSheet(MyStoreListItemView.this.mStoreModel);
            }
        }
    }

    class C19792 implements DownloadResCallback {
        C19792() throws  {
        }

        public void downloadResCallback(int $i0) throws  {
            if ($i0 > 0) {
                MyStoreListItemView.this.mStoreIconImage.setImageDrawable(MyStoreListItemView.this.mStoreModel.postProcessDrawable(ResManager.GetSkinDrawable(MyStoreListItemView.this.mStoreModel.getCorrectIcon() + ResManager.mImageExtension)));
            }
        }
    }

    public interface MyStoreListItemListener {
        void onOpenBottomSheet(MyStoreModel myStoreModel) throws ;
    }

    public MyStoreListItemView(Context $r1) throws  {
        this($r1, null);
    }

    public MyStoreListItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public MyStoreListItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        View $r3 = LayoutInflater.from(getContext()).inflate(C1283R.layout.my_store_list_item, null);
        this.mStoreIconImage = (ImageView) $r3.findViewById(C1283R.id.imgStoreIcon);
        this.mStoreNameLabel = (TextView) $r3.findViewById(C1283R.id.lblStoreName);
        this.mMoreButton = (ImageView) $r3.findViewById(C1283R.id.btnStoreMore);
        this.mSeparatorView = $r3.findViewById(C1283R.id.bottomSeparator);
        this.mMoreButton.setOnClickListener(new C19781());
        addView($r3);
    }

    public void setModel(MyStoreModel $r1) throws  {
        this.mStoreModel = $r1;
        setFields();
    }

    public void setListener(MyStoreListItemListener $r1) throws  {
        this.mListener = $r1;
    }

    public void setSeparatorVisibility(boolean $z0) throws  {
        this.mSeparatorView.setVisibility($z0 ? (byte) 0 : (byte) 8);
    }

    private void setFields() throws  {
        this.mStoreNameLabel.setText(this.mStoreModel.getName());
        this.mStoreIconImage.setImageDrawable(null);
        Drawable $r7 = ResManager.GetSkinDrawable(this.mStoreModel.getIcon() + ResManager.mImageExtension);
        if ($r7 != null) {
            this.mStoreIconImage.setImageDrawable(this.mStoreModel.postProcessDrawable($r7));
        } else {
            ResManager.downloadRes(ResourceDownloadType.RES_DOWNLOAD_AD_IMAGE.getValue(), this.mStoreModel.getCorrectIcon(), new C19792());
        }
        if (this.mStoreModel.isAdvertiser()) {
            this.mStoreIconImage.setColorFilter(null);
        } else {
            ImageUtils.applyColorFilterOnImage(this.mStoreIconImage, -4534834);
        }
    }
}
