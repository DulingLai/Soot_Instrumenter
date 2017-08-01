package com.waze.menus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.DownloadResCallback;
import com.waze.NativeManager;
import com.waze.NativeManager.DistanceAndUnits;
import com.waze.ResManager;
import com.waze.ResourceDownloadType;
import com.waze.menus.SideMenuAutoCompleteRecycler.AutoCompleteItemModel;
import com.waze.navigate.AddressItem;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageUtils;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;

public class AutoCompleteItemView extends FrameLayout {
    private TextView mAddressLabel;
    private View mDistanceLayout;
    private TextView mDistanceNumber;
    private TextView mDistanceUnits;
    private ImageView mIconImage;
    private AutoCompleteItemModel mItemModel;
    private boolean mShowAcDistances;
    private TextView mTitleLabel;

    class C18871 implements OnClickListener {
        C18871() throws  {
        }

        public void onClick(View v) throws  {
            if (AutoCompleteItemView.this.mItemModel.getAction() != null) {
                ((InputMethodManager) AutoCompleteItemView.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(AutoCompleteItemView.this.getWindowToken(), 0);
                AutoCompleteItemView.this.mItemModel.getAction().run();
            }
        }
    }

    public AutoCompleteItemView(Context $r1) throws  {
        this($r1, null);
    }

    public AutoCompleteItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public AutoCompleteItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mShowAcDistances = false;
        init();
    }

    private void init() throws  {
        View $r3 = LayoutInflater.from(getContext()).inflate(C1283R.layout.auto_complete_item_view, null);
        this.mIconImage = (ImageView) $r3.findViewById(C1283R.id.imgAutoCompleteIcon);
        this.mTitleLabel = (TextView) $r3.findViewById(C1283R.id.lblTitle);
        this.mAddressLabel = (TextView) $r3.findViewById(C1283R.id.lblAddress);
        this.mDistanceNumber = (TextView) $r3.findViewById(C1283R.id.acDistanceAmount);
        this.mDistanceUnits = (TextView) $r3.findViewById(C1283R.id.acDistanceUnits);
        this.mDistanceLayout = $r3.findViewById(C1283R.id.acDistance);
        setOnClickListener(new C18871());
        addView($r3);
        boolean $z0 = ConfigManager.getInstance().getConfigValueBool(289) || ConfigManager.getInstance().getConfigValueBool(290);
        this.mShowAcDistances = $z0;
    }

    public AutoCompleteItemModel getModel() throws  {
        return this.mItemModel;
    }

    public void setModel(AutoCompleteItemModel $r1) throws  {
        this.mItemModel = $r1;
        if (this.mItemModel != null) {
            ImageView $r11;
            ImageView $r112;
            int $i0 = this.mItemModel.getIconResourceId();
            String $r3 = this.mItemModel.getTitle();
            String $r4 = this.mItemModel.getSubTitle();
            int $i1 = this.mItemModel.getBoldStart();
            int $i2 = this.mItemModel.getBoldLength();
            String $r5 = this.mItemModel.getIconName();
            String $r6 = $r3.substring(0, $i1);
            String $r7 = $r3.substring($i1, $i1 + $i2);
            $r6 = $r6 + "<b>" + $r7 + "</b>" + $r3.substring($i1 + $i2, $r3.length());
            this.mTitleLabel.setText(Html.fromHtml($r6));
            if (TextUtils.isEmpty($r4)) {
                this.mAddressLabel.setText("");
                this.mAddressLabel.setVisibility(8);
            } else {
                TextView $r9 = this.mAddressLabel;
                $r9.setText($r4);
                this.mAddressLabel.setVisibility(0);
            }
            this.mIconImage.setScaleX(1.0f);
            this.mIconImage.setScaleY(1.0f);
            boolean $z0 = false;
            if (this.mItemModel.getAddressItem() != null) {
                AddressItem $r12 = this.mItemModel.getAddressItem();
                if (!TextUtils.isEmpty($r12.mImageURL)) {
                    final AddressItem addressItem = $r12;
                    VolleyManager.getInstance().loadImageFromUrl($r12.mImageURL, new ImageRequestListener() {
                        public void onImageLoadComplete(Bitmap $r1, Object $r2, long duration) throws  {
                            if ($r2 == addressItem) {
                                AutoCompleteItemView.this.mIconImage.setImageBitmap($r1);
                                AutoCompleteItemView.this.mIconImage.setScaleX(0.75f);
                                AutoCompleteItemView.this.mIconImage.setScaleY(0.75f);
                            }
                        }

                        public void onImageLoadFailed(Object token, long duration) throws  {
                        }
                    }, $r12);
                    $z0 = true;
                } else if ($r12.hasIcon()) {
                    Drawable $r20 = ResManager.GetSkinDrawable($r12.getIcon() + ResManager.mImageExtension);
                    if ($r20 == null && $r12.getImage() != null) {
                        $r11 = this.mIconImage;
                        $r11.setImageResource($i0);
                        $z0 = true;
                    } else if ($r20 != null) {
                        this.mIconImage.setScaleX(0.75f);
                        this.mIconImage.setScaleY(0.75f);
                        $r11 = this.mIconImage;
                        $r112 = $r11;
                        $r11.setImageDrawable($r20);
                        $z0 = true;
                    }
                }
            }
            if (!$z0) {
                if ($r5 != null) {
                    this.mIconImage.setImageDrawable(null);
                    loadIconByName($r5);
                } else if (NativeManager.getInstance().isDebug()) {
                    $r5 = $r1.getVenueId();
                    if ($r5 == null || $r5.contains("oogle")) {
                        $r11 = this.mIconImage;
                        $r112 = $r11;
                        $r11.setImageResource($i0);
                    } else {
                        this.mIconImage.setImageResource(C1283R.drawable.autocomplete_location_debug);
                    }
                } else {
                    $r11 = this.mIconImage;
                    $r112 = $r11;
                    $r11.setImageResource($i0);
                }
            }
            AutoCompleteItemModel $r13 = this.mItemModel;
            if ($r13.getMyStoreModel() != null) {
                $r13 = this.mItemModel;
                if ($r13.getMyStoreModel().isAdvertiser()) {
                    this.mIconImage.setColorFilter(null);
                } else {
                    ImageUtils.applyColorFilterOnImage(this.mIconImage, -6836304);
                }
            } else {
                this.mIconImage.setColorFilter(null);
            }
            $r13 = this.mItemModel;
            DistanceAndUnits $r16 = $r13.getDistance();
            if (!this.mShowAcDistances || $r16 == null) {
                this.mDistanceLayout.setVisibility(8);
                return;
            }
            TextView $r92;
            Object[] $r17;
            float $f0;
            if ($r16.distance < 100.0f) {
                $r92 = this.mDistanceNumber;
                $r17 = new Object[1];
                $f0 = $r16.distance;
                $r17[0] = Float.valueOf($f0);
                $r92.setText(String.format("%.1f", $r17));
            } else {
                $r92 = this.mDistanceNumber;
                $r17 = new Object[1];
                $f0 = $r16.distance;
                $r17[0] = Float.valueOf($f0);
                $r92.setText(String.format("%.0f", $r17));
            }
            this.mDistanceUnits.setText(String.format(DisplayStrings.displayString(DisplayStrings.DS_SEARCH_RESULTS_UNITS_AWAY), new Object[]{$r16.units}));
            this.mDistanceLayout.setVisibility(0);
        }
    }

    private void loadIconByName(final String $r1) throws  {
        Drawable $r4 = ResManager.GetSkinDrawable($r1 + ResManager.mImageExtension);
        if ($r4 == null) {
            ResManager.downloadRes(ResourceDownloadType.RES_DOWNLOAD_AD_IMAGE.getValue(), $r1, new DownloadResCallback() {
                public void downloadResCallback(int $i0) throws  {
                    if ($i0 > 0) {
                        Drawable $r3 = ResManager.GetSkinDrawable($r1 + ResManager.mImageExtension);
                        if (AutoCompleteItemView.this.mItemModel.getMyStoreModel() != null) {
                            AutoCompleteItemView.this.mIconImage.setImageDrawable(AutoCompleteItemView.this.mItemModel.getMyStoreModel().postProcessDrawable($r3));
                        } else {
                            AutoCompleteItemView.this.mIconImage.setImageDrawable($r3);
                        }
                    }
                }
            });
        } else if (this.mItemModel.getMyStoreModel() != null) {
            this.mIconImage.setImageDrawable(this.mItemModel.getMyStoreModel().postProcessDrawable($r4));
        } else {
            this.mIconImage.setImageDrawable($r4);
        }
    }
}
