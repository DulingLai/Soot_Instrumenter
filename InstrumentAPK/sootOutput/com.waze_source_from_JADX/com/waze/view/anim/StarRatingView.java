package com.waze.view.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.strings.DisplayStrings;

public class StarRatingView extends LinearLayout {
    private StarRatingListener listener;
    private int rating = -1;

    public interface StarRatingListener {
        void onRatingChanged(int i);
    }

    public StarRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(C1283R.layout.star_rating, this, true);
        setOrientation(1);
        final ViewGroup starLayout = (ViewGroup) findViewById(C1283R.id.stars);
        final TextView ratingText = (TextView) findViewById(C1283R.id.ratingText);
        ratingText.setText(" ");
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == 0 || action == 2) {
                    int width = v.getWidth();
                    float loc = event.getX();
                    int index = (int) ((5.0f * loc) / ((float) width));
                    if (index > 4) {
                        index = 4;
                    }
                    if (index <= 0) {
                        if (loc < 0.0f) {
                            index = -1;
                        } else {
                            index = 0;
                        }
                    }
                    if (StarRatingView.this.rating != index) {
                        int i;
                        if (StarRatingView.this.rating < index) {
                            for (i = StarRatingView.this.rating + 1; i <= index; i++) {
                                ((OneStarRatingView) starLayout.getChildAt(i)).animateIn((long) ((i - (StarRatingView.this.rating + 1)) * 50));
                            }
                        } else if (StarRatingView.this.rating > index) {
                            for (i = StarRatingView.this.rating; i > index; i--) {
                                ((OneStarRatingView) starLayout.getChildAt(i)).animateOut();
                            }
                            if (index >= 0) {
                                ((OneStarRatingView) starLayout.getChildAt(index)).animateSame();
                            }
                        }
                        StarRatingView.this.rating = index;
                        ratingText.setText(StarRatingView.this.getRatingString());
                        if (StarRatingView.this.listener != null) {
                            StarRatingView.this.listener.onRatingChanged(StarRatingView.this.rating + 1);
                        }
                    } else if (action == 0 && index >= 0) {
                        ((OneStarRatingView) starLayout.getChildAt(index)).animateSame();
                    }
                }
                return true;
            }
        });
    }

    public void setListener(StarRatingListener listener) {
        this.listener = listener;
    }

    private String getRatingString() {
        if (this.rating == -1) {
            return " ";
        }
        if (this.rating == 0) {
            return DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_POOR);
        }
        if (this.rating == 1) {
            return DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_BELOW_AVERAGE);
        }
        if (this.rating == 2) {
            return DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_AVERAGE);
        }
        if (this.rating == 3) {
            return DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_GOOD);
        }
        if (this.rating == 4) {
            return DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_RATING_EXCELLENT);
        }
        return " ";
    }

    public int getRating() {
        return this.rating + 1;
    }
}
