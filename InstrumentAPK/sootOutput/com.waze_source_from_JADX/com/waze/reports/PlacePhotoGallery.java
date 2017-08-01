package com.waze.reports;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.reports.PhotoPagerSection.VenueImage;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.text.WazeTextView;
import java.util.ArrayList;

public class PlacePhotoGallery extends Dialog {
    private static final int ANIMATION_DURATION = 200;
    private ActivityBase mActivity;
    private PagerAdapter mAdapter;
    private Bundle mImageLocation = null;
    private ArrayList<VenueImage> mImages;
    private int mItemMargin = 0;
    private IPhotoGalleryListener mListener;
    private RelativeLayout mMainLayout;
    private NativeManager mNm;
    private ViewPager mPager;

    public interface IPhotoGalleryListener {
        void onDelete(int i);

        void onFlag(int i, int i2);

        void onLike(int i);

        void onScroll(int i);

        void onUnlike(int i);
    }

    class C25021 implements OnClickListener {
        C25021() {
        }

        public void onClick(View v) {
            PlacePhotoGallery.this.animatePagerOut();
        }
    }

    class C25082 extends PagerAdapter {

        class C25031 implements OnClickListener {
            C25031() {
            }

            public void onClick(View v) {
            }
        }

        class C25042 implements OnClickListener {
            C25042() {
            }

            public void onClick(View v) {
                PlacePhotoGallery.this.animatePagerOut();
            }
        }

        C25082() {
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int getCount() {
            return PlacePhotoGallery.this.mImages.size();
        }

        public int getItemPosition(Object object) {
            return getImagePosition((VenueImage) ((View) object).getTag());
        }

        int getImagePosition(VenueImage vi) {
            if (PlacePhotoGallery.this.mImages.contains(vi)) {
                return PlacePhotoGallery.this.mImages.indexOf(vi);
            }
            return -2;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout layout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(C1283R.layout.place_photo_pager_large_item, container, false);
            VenueImage vi = (VenueImage) PlacePhotoGallery.this.mImages.get(position);
            ProgressAnimation pa = (ProgressAnimation) layout.findViewById(C1283R.id.placePhotoProgress);
            if (ImageRepository.instance.isCached(vi.imageUri)) {
                pa.setVisibility(8);
            } else {
                pa.start();
            }
            ImageView image = (ImageView) layout.findViewById(C1283R.id.placePhoto);
            boolean isFile = vi.imageUri.startsWith("file");
            ImageRepository.instance.getImage(vi.imageUri, 0, image, pa, PlacePhotoGallery.this.mActivity);
            layout.setTag(vi);
            container.addView(layout);
            if (PlacePhotoGallery.this.mItemMargin > 0) {
                View frame = layout.findViewById(C1283R.id.placePhotoFrme);
                LayoutParams p = (RelativeLayout.LayoutParams) frame.getLayoutParams();
                p.setMargins(PlacePhotoGallery.this.mItemMargin, 0, PlacePhotoGallery.this.mItemMargin, 0);
                frame.setLayoutParams(p);
                frame.setOnClickListener(new C25031());
                layout.findViewById(C1283R.id.forMarginesOnly).setOnClickListener(new C25042());
            }
            WazeTextView byView = (WazeTextView) layout.findViewById(C1283R.id.placePhotoBy);
            ImageView moodView = (ImageView) layout.findViewById(C1283R.id.placePhotoByMood);
            View sepView = layout.findViewById(C1283R.id.placePhotoSep);
            final ImageButton likeView = (ImageButton) layout.findViewById(C1283R.id.placePhotoLike);
            ImageButton flagView = (ImageButton) layout.findViewById(C1283R.id.placePhotoFlag);
            if (vi.didLike) {
                likeView.setImageResource(C1283R.drawable.places_like_pic_active);
            } else {
                likeView.setImageResource(C1283R.drawable.places_like_pic);
            }
            String nick = vi.submitter;
            String mood = vi.mood;
            if (vi.isUploading) {
                nick = PlacePhotoGallery.this.mNm.getLanguageString(DisplayStrings.DS_UPLOADING_DATA___);
            } else if (!(nick == null || nick.isEmpty())) {
                nick = String.format(PlacePhotoGallery.this.mNm.getLanguageString(DisplayStrings.DS_PHOTO_BY_PS), new Object[]{nick});
            }
            final VenueImage venueImage;
            if (vi.canDelete || isFile) {
                if (!isFile) {
                    nick = "";
                }
                byView.setText(nick);
                moodView.setVisibility(8);
                sepView.setVisibility(8);
                likeView.setVisibility(8);
                if (isFile) {
                    flagView.setVisibility(8);
                } else {
                    flagView.setImageResource(C1283R.drawable.places_delete_pic);
                    venueImage = vi;
                    flagView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            int position = C25082.this.getImagePosition(venueImage);
                            if (position >= 0) {
                                PlacePhotoGallery.this.areYouSureDelete(position);
                            }
                        }
                    });
                }
            } else {
                venueImage = vi;
                likeView.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        int position = C25082.this.getImagePosition(venueImage);
                        if (position >= 0) {
                            if (venueImage.didLike) {
                                venueImage.didLike = false;
                                likeView.setImageResource(C1283R.drawable.places_like_pic);
                                PlacePhotoGallery.this.mListener.onUnlike(position);
                                return;
                            }
                            venueImage.didLike = true;
                            likeView.setImageResource(C1283R.drawable.places_like_pic_active);
                            PlacePhotoGallery.this.mListener.onLike(position);
                        }
                    }
                });
                venueImage = vi;
                flagView.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        int position = C25082.this.getImagePosition(venueImage);
                        if (position >= 0) {
                            PlacePhotoGallery.this.showFlagSubmenu(position);
                        }
                    }
                });
                if (nick == null || nick.isEmpty()) {
                    byView.setText("");
                } else {
                    byView.setText(nick);
                }
                if (mood == null || mood.isEmpty()) {
                    moodView.setVisibility(8);
                } else {
                    moodView.setImageDrawable(MoodManager.getMoodDrawable(PlacePhotoGallery.this.mActivity, mood));
                }
            }
            return layout;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class C25093 implements OnGlobalLayoutListener {
        C25093() {
        }

        public void onGlobalLayout() {
            PlacePhotoGallery.this.mPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            PlacePhotoGallery.this.animatePagerIn();
        }
    }

    class C25104 implements OnPageChangeListener {
        C25104() {
        }

        public void onPageSelected(int position) {
            if (PlacePhotoGallery.this.mListener != null) {
                PlacePhotoGallery.this.mListener.onScroll(position);
            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    class C25137 implements AnimationListener {
        C25137() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            PlacePhotoGallery.this.dismiss();
        }
    }

    public PlacePhotoGallery(Context context, ArrayList<VenueImage> images, int startAt, Bundle imageLocation, ActivityBase ab) {
        super(context, C1283R.style.ReportDialog);
        this.mActivity = ab;
        this.mImages = images;
        this.mNm = NativeManager.getInstance();
        setContentView(C1283R.layout.place_photo_gallery);
        getWindow().setLayout(-1, -1);
        this.mMainLayout = (RelativeLayout) findViewById(C1283R.id.galleryMainLayout);
        this.mMainLayout.setOnClickListener(new C25021());
        this.mPager = (ViewPager) this.mMainLayout.findViewById(C1283R.id.galleryPager);
        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        if (width > height) {
            this.mItemMargin = (width - ((height * 4) / 3)) / 2;
        } else {
            LayoutParams p = this.mPager.getLayoutParams();
            p.height = (width * 3) / 4;
            this.mPager.setLayoutParams(p);
        }
        int marginPixels = (int) (20.0f * context.getResources().getDisplayMetrics().density);
        if (marginPixels > this.mItemMargin) {
            this.mPager.setPageMargin(marginPixels - this.mItemMargin);
        }
        if (this.mAdapter != null) {
            this.mPager.setAdapter(null);
        }
        this.mAdapter = new C25082();
        this.mPager.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        this.mPager.setCurrentItem(startAt);
        if (imageLocation != null) {
            this.mImageLocation = imageLocation;
            this.mPager.getViewTreeObserver().addOnGlobalLayoutListener(new C25093());
        }
        this.mPager.setOnPageChangeListener(new C25104());
    }

    private void areYouSureDelete(final int position) {
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNm.getLanguageString(DisplayStrings.DS_DELETE_PICTURE), this.mNm.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_YOU_WANT_TO_DELETE_PICTURE), true, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    PlacePhotoGallery.this.mListener.onDelete(position);
                }
            }
        }, this.mNm.getLanguageString(368), this.mNm.getLanguageString(344), -1);
    }

    private void animatePagerIn() {
        int left = this.mImageLocation.getInt("left");
        int top = this.mImageLocation.getInt("top");
        int width = this.mImageLocation.getInt("width");
        int height = this.mImageLocation.getInt("height");
        int[] location = new int[2];
        this.mPager.getLocationInWindow(location);
        AnimationSet as = new AnimationSet(false);
        int pagerWidth = this.mPager.getWidth();
        int pagerHeight = this.mPager.getHeight();
        ScaleAnimation sa = new ScaleAnimation(((float) width) / ((float) pagerWidth), 1.0f, ((float) height) / ((float) pagerHeight), 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(200);
        as.addAnimation(sa);
        TranslateAnimation ta = new TranslateAnimation(0, (float) (((width / 2) + left) - (location[0] + (pagerWidth / 2))), 1, 0.0f, 0, (float) (((height / 2) + top) - (location[1] + (pagerHeight / 2))), 1, 0.0f);
        ta.setDuration(200);
        ta.setInterpolator(new LinearInterpolator());
        as.addAnimation(ta);
        this.mPager.startAnimation(as);
    }

    private void showFlagSubmenu(final int position) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_PHOTO_FLAGGING_POPUP_SHOWN, null, null);
        String dialogTitle = this.mNm.getLanguageString(DisplayStrings.DS_WHATS_WRONG_WITH_THIS_PHOTOQ);
        String[] options = new String[]{this.mNm.getLanguageString(DisplayStrings.DS_IT_HAS_NOTHING_TO_DO_WITH_THIS_PLACE), this.mNm.getLanguageString(DisplayStrings.DS_IT_IS_INAPPROPRIATE), this.mNm.getLanguageString(DisplayStrings.DS_IT_IS_IN_LOW_QUALITY)};
        final int[] optionValues = new int[]{8, 5, 7};
        Builder bob = new Builder(getContext(), C1283R.style.CustomPopup);
        bob.setTitle(dialogTitle);
        bob.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                PlacePhotoGallery.this.mListener.onFlag(position, optionValues[item]);
            }
        });
        bob.setIcon(C1283R.drawable.flag_it_popup);
        bob.setCancelable(true);
        AlertDialog alertDialog = bob.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void animatePagerOut() {
        if (this.mImageLocation == null) {
            dismiss();
            return;
        }
        int left = this.mImageLocation.getInt("left");
        int top = this.mImageLocation.getInt("top");
        int width = this.mImageLocation.getInt("width");
        int height = this.mImageLocation.getInt("height");
        int[] location = new int[2];
        this.mPager.getLocationInWindow(location);
        Animation animationSet = new AnimationSet(false);
        int pagerWidth = this.mPager.getWidth();
        int pagerHeight = this.mPager.getHeight();
        ScaleAnimation sa = new ScaleAnimation(1.0f, ((float) width) / ((float) pagerWidth), 1.0f, ((float) height) / ((float) pagerHeight), 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(300);
        animationSet.addAnimation(sa);
        TranslateAnimation ta = new TranslateAnimation(1, 0.0f, 0, (float) (((width / 2) + left) - (location[0] + (pagerWidth / 2))), 1, 0.0f, 0, (float) (((height / 2) + top) - (location[1] + (pagerHeight / 2))));
        ta.setDuration(200);
        ta.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(ta);
        animationSet.setAnimationListener(new C25137());
        this.mPager.startAnimation(animationSet);
    }

    public void onBackPressed() {
        animatePagerOut();
    }

    void setListener(IPhotoGalleryListener l) {
        this.mListener = l;
    }

    public void updateImages(ArrayList<VenueImage> images) {
        this.mImages = images;
        this.mAdapter.notifyDataSetChanged();
    }
}
