package com.waze.reports;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.reports.PlacePhotoGallery.IPhotoGalleryListener;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.map.ProgressAnimation;
import java.util.ArrayList;

public class PhotoPagerSection {
    private static final float MIN_ALPHA = 0.8f;
    private static final float MIN_SCALE = 0.85f;
    final ActivityBase mActivity;
    private TextView mAtLeast;
    private ImageButton mCameraButton;
    private PlacePhotoGallery mGallery = null;
    private ArrayList<VenueImage> mImageArray;
    final boolean mIsEdit;
    private IPhotoGalleryListener mListener;
    private NativeManager mNm;
    private PagerAdapter mPagerAdapter;
    private ProgressAnimation mProgress;
    View mRoot;
    private int mScreenWidth;
    private ViewPager mViewPager;

    class C24961 implements IPhotoGalleryListener {
        C24961() {
        }

        public void onScroll(int position) {
            PhotoPagerSection.this.mViewPager.setCurrentItem(position, true);
            PhotoPagerSection.this.mListener.onScroll(position);
        }

        public void onFlag(int position, int reason) {
            PhotoPagerSection.this.mListener.onFlag(position, reason);
        }

        public void onDelete(int position) {
            PhotoPagerSection.this.mListener.onDelete(position);
        }

        public void onLike(int position) {
            PhotoPagerSection.this.mListener.onLike(position);
        }

        public void onUnlike(int position) {
            PhotoPagerSection.this.mListener.onUnlike(position);
        }
    }

    class C25003 implements Runnable {
        C25003() {
        }

        public void run() {
            PhotoPagerSection.this.mViewPager.setCurrentItem(PhotoPagerSection.this.mPagerAdapter.getCount() - 1, true);
        }
    }

    public class DepthPageTransformer implements PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1.0f) {
                view.setAlpha(0.0f);
            } else if (position <= 0.0f) {
                view.setAlpha(1.0f);
                view.setTranslationX(0.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
            } else if (position <= 1.0f) {
                view.setAlpha(1.0f - position);
                view.setTranslationX(((float) pageWidth) * (-position));
                float scaleFactor = MIN_SCALE + (0.25f * (1.0f - Math.abs(position)));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0.0f);
            }
        }
    }

    public static class VenueImage implements Parcelable {
        public static final Creator<VenueImage> CREATOR = new C25011();
        public boolean canDelete = false;
        public boolean didLike = false;
        public String imageThumbnailUri;
        public String imageUri;
        public boolean isUploading = false;
        public String mood;
        public String submitter;

        static class C25011 implements Creator<VenueImage> {
            C25011() {
            }

            public VenueImage createFromParcel(Parcel in) {
                return new VenueImage(in);
            }

            public VenueImage[] newArray(int size) {
                return new VenueImage[size];
            }
        }

        public VenueImage(String uri, String uriThumbnail, String submittedBy, String submitterMood, boolean liked, boolean byMe, boolean uploading) {
            this.imageUri = uri;
            this.imageThumbnailUri = uriThumbnail;
            this.submitter = submittedBy;
            this.mood = submitterMood;
            this.didLike = liked;
            this.canDelete = byMe;
            this.isUploading = uploading;
        }

        public VenueImage(Parcel in) {
            boolean z;
            boolean z2 = true;
            this.imageUri = in.readString();
            this.imageThumbnailUri = in.readString();
            this.submitter = in.readString();
            this.mood = in.readString();
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.didLike = z;
            if (in.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.canDelete = z;
            if (in.readInt() == 0) {
                z2 = false;
            }
            this.isUploading = z2;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            int i;
            int i2 = 1;
            dest.writeString(this.imageUri);
            dest.writeString(this.imageThumbnailUri);
            dest.writeString(this.submitter);
            dest.writeString(this.mood);
            if (this.didLike) {
                i = 1;
            } else {
                i = 0;
            }
            dest.writeInt(i);
            if (this.canDelete) {
                i = 1;
            } else {
                i = 0;
            }
            dest.writeInt(i);
            if (!this.isUploading) {
                i2 = 0;
            }
            dest.writeInt(i2);
        }
    }

    public class ZoomOutPageTransformer implements PageTransformer {
        public void transformPage(View view, float position) {
            if (position >= -1.0f && position <= 1.0f) {
                float scaleFactor = Math.max(PhotoPagerSection.MIN_SCALE, 1.0f - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }
        }
    }

    public PhotoPagerSection(ActivityBase a, View root, boolean isEdit, OnClickListener takeAnother) {
        this.mActivity = a;
        this.mIsEdit = isEdit;
        this.mRoot = root;
        this.mNm = NativeManager.getInstance();
        this.mScreenWidth = a.getResources().getDisplayMetrics().widthPixels;
        this.mAtLeast = (TextView) this.mRoot.findViewById(C1283R.id.placeTakePhotoAtLeastText);
        this.mAtLeast.setText(this.mNm.getLanguageString(DisplayStrings.DS_ADD_A_PHOTO));
        this.mCameraButton = (ImageButton) this.mRoot.findViewById(C1283R.id.placeTakePhoto);
        this.mCameraButton.setOnClickListener(takeAnother);
        this.mProgress = (ProgressAnimation) this.mRoot.findViewById(C1283R.id.placeTakePhotoProgress);
        this.mViewPager = (ViewPager) this.mRoot.findViewById(C1283R.id.placePhotoPager);
        LayoutParams p = this.mViewPager.getLayoutParams();
        p.height = (((this.mScreenWidth * 6) / 10) * 3) / 4;
        this.mViewPager.setLayoutParams(p);
        this.mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        this.mViewPager.setPageMargin(((-this.mScreenWidth) * 4) / 10);
        this.mViewPager.setOffscreenPageLimit(2);
    }

    public void setVenue(ArrayList<VenueImage> array, IPhotoGalleryListener listener) {
        this.mListener = listener;
        this.mImageArray = array;
        setupViewPager();
    }

    public void venueUpdated(ArrayList<VenueImage> array) {
        int prevSize = 0;
        if (this.mImageArray != null) {
            prevSize = this.mImageArray.size();
        }
        this.mImageArray = array;
        if (this.mPagerAdapter == null || prevSize == 0) {
            setupViewPager();
        } else if (this.mImageArray.size() > 0) {
            this.mPagerAdapter.notifyDataSetChanged();
        } else {
            setupViewPager();
        }
        if (this.mGallery == null) {
            return;
        }
        if (this.mImageArray.size() > 0) {
            this.mGallery.updateImages(this.mImageArray);
            return;
        }
        this.mGallery.dismiss();
        this.mGallery = null;
    }

    private void setupViewPager() {
        int i = 0;
        final IPhotoGalleryListener listener = new C24961();
        this.mPagerAdapter = new PagerAdapter() {
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            public int getCount() {
                return PhotoPagerSection.this.mImageArray.size();
            }

            public int getItemPosition(Object object) {
                return getImagePosition((VenueImage) ((View) object).getTag());
            }

            private int getImagePosition(VenueImage vi) {
                if (PhotoPagerSection.this.mImageArray.contains(vi)) {
                    return PhotoPagerSection.this.mImageArray.indexOf(vi);
                }
                return -2;
            }

            public Object instantiateItem(ViewGroup container, int position) {
                View layout = LayoutInflater.from(container.getContext()).inflate(C1283R.layout.place_photo_pager_small_item, container, false);
                final VenueImage vi = (VenueImage) PhotoPagerSection.this.mImageArray.get(position);
                ProgressAnimation pa = (ProgressAnimation) layout.findViewById(C1283R.id.placePhotoProgress);
                String imageThumbnailUri = vi.imageThumbnailUri;
                if (imageThumbnailUri == null || imageThumbnailUri.isEmpty()) {
                    imageThumbnailUri = vi.imageUri;
                }
                if (ImageRepository.instance.isThumbnailCached(imageThumbnailUri)) {
                    pa.setVisibility(8);
                } else {
                    pa.start();
                }
                ImageView image = (ImageView) layout.findViewById(C1283R.id.placePhoto);
                ImageRepository.instance.getImage(imageThumbnailUri, 1, image, pa, PhotoPagerSection.this.mActivity, AnalyticsEvents.ANALYTICS_EVENT_LATENCY_VENUE_IMAGE);
                layout.setTag(vi);
                container.addView(layout);
                layout.setScaleX(PhotoPagerSection.MIN_SCALE);
                layout.setScaleY(PhotoPagerSection.MIN_SCALE);
                image.setOnClickListener(new OnClickListener() {

                    class C24971 implements OnDismissListener {
                        C24971() {
                        }

                        public void onDismiss(DialogInterface dialog) {
                            PhotoPagerSection.this.mGallery = null;
                        }
                    }

                    public void onClick(View v) {
                        int position = C24992.this.getImagePosition(vi);
                        if (position >= 0) {
                            if (position != PhotoPagerSection.this.mViewPager.getCurrentItem()) {
                                PhotoPagerSection.this.mViewPager.setCurrentItem(position, true);
                                return;
                            }
                            PhotoPagerSection.this.mGallery = PhotoPagerSection.openPlacePhotoGalleryFromView(v, PhotoPagerSection.this.mImageArray, position, listener, PhotoPagerSection.this.mActivity);
                            PhotoPagerSection.this.mGallery.setOnDismissListener(new C24971());
                        }
                    }
                });
                return layout;
            }

            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setCurrentItem(this.mPagerAdapter.getCount() - 1);
        this.mViewPager.postDelayed(new C25003(), 10);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) this.mCameraButton.getLayoutParams();
        if (this.mImageArray.isEmpty()) {
            p.addRule(3, 0);
            p.addRule(15);
            p.topMargin = 0;
        } else {
            p.addRule(15, 0);
            p.addRule(3, C1283R.id.placePhotoPager);
            p.topMargin = (int) (this.mActivity.getResources().getDisplayMetrics().density * -3.0f);
        }
        this.mCameraButton.setLayoutParams(p);
        TextView textView = this.mAtLeast;
        if (!this.mImageArray.isEmpty()) {
            i = 8;
        }
        textView.setVisibility(i);
    }

    public static PlacePhotoGallery openPlacePhotoGalleryFromView(View v, ArrayList<VenueImage> images, int position, IPhotoGalleryListener listener, ActivityBase ab) {
        Bundle imageLocation = new Bundle();
        int[] location = new int[2];
        v.getLocationInWindow(location);
        imageLocation.putInt("left", location[0]);
        imageLocation.putInt("top", location[1]);
        imageLocation.putInt("width", v.getWidth());
        imageLocation.putInt("height", v.getHeight());
        PlacePhotoGallery ppg = new PlacePhotoGallery(v.getContext(), images, position, imageLocation, ab);
        ppg.setListener(listener);
        ppg.setCanceledOnTouchOutside(true);
        ppg.show();
        return ppg;
    }

    public void setInProgress(boolean isInProgress) {
        if (isInProgress) {
            this.mProgress.setVisibility(0);
            this.mProgress.start();
            this.mCameraButton.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            return;
        }
        this.mProgress.stop();
        this.mProgress.setVisibility(8);
        this.mCameraButton.setAlpha(1.0f);
    }
}
