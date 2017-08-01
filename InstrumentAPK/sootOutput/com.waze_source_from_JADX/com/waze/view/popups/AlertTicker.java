package com.waze.view.popups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MoodManager;
import com.waze.NativeManager;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.user.AlertTickerData;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import java.util.ArrayList;
import java.util.Iterator;

public class AlertTicker extends PopUp {
    private Context mContext = null;
    private ArrayList<AlertTickerData> mData = new ArrayList();
    private int mIndex;
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;
    private String mMoodName = null;
    private int mType;
    private String mUserImageUrl = null;
    private boolean mWasAdded = false;
    private int nCount = 0;

    class C30483 implements Runnable {
        C30483() {
        }

        public void run() {
            if (!AlertTicker.this.mWasAdded) {
                AlertTicker.this.mLayoutManager.addView(AlertTicker.this);
                AlertTicker.this.mWasAdded = true;
            }
        }
    }

    class C30494 implements OnClickListener {
        C30494() {
        }

        public void onClick(View v) {
            int[] nTypes = new int[AlertTicker.this.nCount];
            int[] nIndexes = new int[AlertTicker.this.nCount];
            for (int i = 0; i < AlertTicker.this.nCount; i++) {
                AlertTickerData adata = (AlertTickerData) AlertTicker.this.mData.get(i);
                nTypes[i] = adata.mType;
                nIndexes[i] = adata.mIndex;
            }
            NativeManager.getInstance().OpenTickersPopups(nTypes, nIndexes);
        }
    }

    public boolean onBackPressed() {
        close();
        return true;
    }

    public void dismiss() {
        this.mIsShown = false;
        this.mLayoutManager.dismiss(this);
        this.mData.clear();
        this.nCount = 0;
        if (this.mType == 0) {
            NativeManager.getInstance().BeepClosed(this.mIndex);
        }
        this.mLayoutManager.showSpotifyButton();
    }

    private void close() {
        dismiss();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.alert_ticker, this);
    }

    public boolean IsShown() {
        return this.mIsShown;
    }

    public AlertTicker(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mContext = context;
        this.mLayoutManager = layoutManager;
        init();
    }

    private boolean isImageCached() {
        return this.mUserImageUrl == null || ImageRepository.instance.isThumbnailCached(this.mUserImageUrl);
    }

    private void setImage() {
        if (this.mUserImageUrl != null) {
            final ImageView UserSmallImg = (ImageView) findViewById(C1283R.id.SmallImage);
            UserSmallImg.setVisibility(0);
            ImageRepository.instance.getImage(this.mUserImageUrl, true, new ImageRepository$ImageRepositoryListener() {
                public void onImageRetrieved(final Bitmap bitmap) {
                    if (bitmap != null) {
                        AppService.Post(new Runnable() {
                            public void run() {
                                UserSmallImg.setImageDrawable(new CircleShaderDrawable(bitmap, 0));
                            }
                        });
                    }
                }
            });
        } else if (this.mMoodName == null || this.mMoodName.isEmpty()) {
            findViewById(C1283R.id.MoodImage).setVisibility(8);
            ((TextView) findViewById(C1283R.id.MoodLayout)).setVisibility(0);
        } else {
            showMoodIcon();
        }
    }

    private void showMoodIcon() {
        ((ImageView) findViewById(C1283R.id.SmallImage)).setVisibility(8);
        ImageView icon = (ImageView) findViewById(C1283R.id.MoodImage);
        icon.setImageDrawable(getResourceDrawable(this.mContext, this.mMoodName));
        icon.setVisibility(0);
    }

    private void setImageAndShow() {
        if (this.mUserImageUrl != null) {
            final ImageView UserSmallImg = (ImageView) findViewById(C1283R.id.SmallImage);
            UserSmallImg.setVisibility(0);
            ImageRepository.instance.getImage(this.mUserImageUrl, true, new ImageRepository$ImageRepositoryListener() {
                public void onImageRetrieved(final Bitmap bitmap) {
                    AppService.Post(new Runnable() {
                        public void run() {
                            if (bitmap != null) {
                                UserSmallImg.setVisibility(0);
                                UserSmallImg.setImageDrawable(new CircleShaderDrawable(bitmap, 0));
                                AlertTicker.this.findViewById(C1283R.id.MoodImage).setVisibility(8);
                            }
                            if (!AlertTicker.this.mWasAdded) {
                                AlertTicker.this.mLayoutManager.addView(AlertTicker.this);
                                AlertTicker.this.mWasAdded = true;
                            }
                        }
                    });
                }
            });
            AppService.Post(new C30483(), 3000);
        }
        if (this.mMoodName == null || this.mMoodName.isEmpty()) {
            findViewById(C1283R.id.MoodImage).setVisibility(8);
            ((TextView) findViewById(C1283R.id.MoodLayout)).setVisibility(0);
            return;
        }
        showMoodIcon();
    }

    public Drawable getResourceDrawable(Context context, String image) {
        return MoodManager.getMoodDrawable(context, image);
    }

    public void ChangeImage(boolean bIsFew) {
        if (bIsFew) {
            findViewById(C1283R.id.pingRightLayout).setBackgroundResource(C1283R.drawable.ticker_background_stack);
        } else {
            findViewById(C1283R.id.pingRightLayout).setBackgroundResource(C1283R.drawable.ticker_background);
        }
    }

    public void RemoveAlertTicker(int index) {
        if (index < this.nCount) {
            this.mData.remove(index);
            this.nCount--;
        }
    }

    public void RemoveAllAlertTickersOfType(int type) {
        Iterator<AlertTickerData> it = this.mData.iterator();
        while (it.hasNext()) {
            if (((AlertTickerData) it.next()).mType == type) {
                this.nCount--;
                it.remove();
            }
        }
        if (this.nCount < 0) {
            Logger.d("RemoveAllAlertTickersOfType: nCount < 0:" + this.nCount + "; zeroing it");
            this.nCount = 0;
        }
    }

    public void RemoveAllAlertTickers() {
        Iterator<AlertTickerData> it = this.mData.iterator();
        while (it.hasNext()) {
            this.nCount--;
            it.remove();
        }
        if (this.nCount < 0) {
            Logger.d("RemoveAllAlertTickers: nCount < 0:" + this.nCount + "; zeroing it");
            this.nCount = 0;
        }
    }

    public void Refresh() {
        if (this.nCount > 0) {
            AlertTickerData alertData = (AlertTickerData) this.mData.get(0);
            this.mUserImageUrl = alertData.mImage;
            this.mMoodName = alertData.mMood;
            this.mType = alertData.mType;
            setIcon();
            if (this.nCount == 1) {
                findViewById(C1283R.id.pingRightLayout).setBackgroundResource(C1283R.drawable.ticker_background);
            } else {
                findViewById(C1283R.id.pingRightLayout).setBackgroundResource(C1283R.drawable.ticker_background_stack);
            }
            setImage();
            return;
        }
        dismiss();
    }

    public void show(int nType, String UserImageUrl, String mMood, int Index) {
        this.nCount++;
        AlertTickerData AlertTicker = new AlertTickerData();
        AlertTicker.mType = nType;
        AlertTicker.mImage = UserImageUrl;
        AlertTicker.mMood = mMood;
        AlertTicker.mIndex = Index;
        this.mData.add(AlertTicker);
        if (!this.mIsShown) {
            this.mIsShown = true;
            this.mUserImageUrl = UserImageUrl;
            this.mMoodName = mMood;
            this.mIndex = Index;
            this.mType = nType;
            setIcon();
            setImage();
            findViewById(C1283R.id.pingRightLayout).setOnClickListener(new C30494());
            this.mWasAdded = false;
            if (isImageCached()) {
                setImage();
                this.mLayoutManager.addView(this);
                this.mWasAdded = true;
                return;
            }
            setImageAndShow();
        }
    }

    private void setIcon() {
        if (this.mType == 0) {
            ((ImageView) findViewById(C1283R.id.SmallalertIcon)).setImageResource(C1283R.drawable.beep_small_icon);
        } else if (this.mType == 1) {
            ((ImageView) findViewById(C1283R.id.SmallalertIcon)).setImageResource(C1283R.drawable.message_small_icon);
        } else if (this.mType == 2) {
            ((ImageView) findViewById(C1283R.id.SmallalertIcon)).setImageResource(C1283R.drawable.share_small_icon);
        } else if (this.mType == 3) {
            ((ImageView) findViewById(C1283R.id.SmallalertIcon)).setImageResource(C1283R.drawable.share_small_icon);
        }
    }

    public void hide() {
        if (this.mIsShown) {
            close();
        }
    }

    public int getType() {
        return this.mType;
    }
}
