package com.waze.navigate;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class NameFavoriteView extends FrameLayout {
    private AddressItem mAddressItem;
    private RelativeLayout mCancelButton;
    private TextView mCancelLabel;
    private ImageView mClearButton;
    private RelativeLayout mDoneButton;
    private TextView mDoneLabel;
    private boolean mIsRenameMode;
    private NameFavoriteListener mListener;
    private RelativeLayout mMainContainer;
    private EditText mNameEditText;
    private TextView mNameFavoriteLabel;

    class C21291 implements OnClickListener {
        C21291() throws  {
        }

        public void onClick(View v) throws  {
            NameFavoriteView.this.onCancelClick();
        }
    }

    class C21302 implements OnClickListener {
        C21302() throws  {
        }

        public void onClick(View v) throws  {
            NameFavoriteView.this.onDoneClick();
        }
    }

    class C21313 implements OnClickListener {
        C21313() throws  {
        }

        public void onClick(View v) throws  {
            NameFavoriteView.this.mNameEditText.setText("");
        }
    }

    class C21324 implements OnEditorActionListener {
        C21324() throws  {
        }

        public boolean onEditorAction(TextView v, int $i0, KeyEvent $r2) throws  {
            if ($i0 != 6) {
                if ($r2 == null) {
                    return true;
                }
                if ($r2.getAction() != 1) {
                    return true;
                }
            }
            NameFavoriteView.this.onDoneClick();
            return true;
        }
    }

    class C21335 implements TextWatcher {
        C21335() throws  {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) throws  {
            NameFavoriteView.this.mDoneButton.setAlpha(TextUtils.isEmpty(NameFavoriteView.this.mNameEditText.getText().toString()) ? CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR : 1.0f);
        }

        public void afterTextChanged(Editable s) throws  {
        }
    }

    public interface NameFavoriteListener {
        void onNameFavoriteCancelled() throws ;

        void onNameFavoriteComplete() throws ;
    }

    public NameFavoriteView(Context $r1) throws  {
        this($r1, null);
    }

    public NameFavoriteView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public NameFavoriteView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        View $r4 = LayoutInflater.from(getContext()).inflate(C1283R.layout.name_favorite_layout, null);
        this.mNameFavoriteLabel = (TextView) $r4.findViewById(C1283R.id.lblNameFavorite);
        this.mCancelLabel = (TextView) $r4.findViewById(C1283R.id.lblCancel);
        this.mDoneLabel = (TextView) $r4.findViewById(C1283R.id.lblDone);
        this.mMainContainer = (RelativeLayout) $r4.findViewById(C1283R.id.mainContainer);
        this.mCancelButton = (RelativeLayout) $r4.findViewById(C1283R.id.btnCancel);
        this.mDoneButton = (RelativeLayout) $r4.findViewById(C1283R.id.btnDone);
        this.mNameEditText = (EditText) $r4.findViewById(C1283R.id.nameEditText);
        this.mClearButton = (ImageView) $r4.findViewById(C1283R.id.btnClearText);
        this.mNameFavoriteLabel.setText(DisplayStrings.displayString(35));
        this.mCancelLabel.setText(DisplayStrings.displayString(344));
        this.mDoneLabel.setText(DisplayStrings.displayString(375));
        this.mNameEditText.setHint(DisplayStrings.displayString(DisplayStrings.DS_NAME_THIS_FAVORITE_LOCATION));
        C21291 c21291 = new C21291();
        this.mMainContainer.setOnClickListener(c21291);
        this.mCancelButton.setOnClickListener(c21291);
        this.mDoneButton.setOnClickListener(new C21302());
        this.mClearButton.setOnClickListener(new C21313());
        this.mNameEditText.setOnEditorActionListener(new C21324());
        this.mNameEditText.addTextChangedListener(new C21335());
        addView($r4);
    }

    public void setListener(NameFavoriteListener $r1) throws  {
        this.mListener = $r1;
    }

    public void setAddressItem(AddressItem $r1) throws  {
        this.mAddressItem = $r1;
        setFields();
    }

    private void setFields() throws  {
        if (!TextUtils.isEmpty(this.mAddressItem.getTitle())) {
            this.mNameEditText.setText(this.mAddressItem.getTitle());
        } else if (TextUtils.isEmpty(this.mAddressItem.getAddress())) {
            this.mNameEditText.setText("");
        } else {
            this.mNameEditText.setText(this.mAddressItem.getAddress());
        }
        this.mNameEditText.requestFocus();
    }

    private void onDoneClick() throws  {
        View $r0 = this;
        String $r3 = this.mNameEditText.getText().toString();
        if (!TextUtils.isEmpty($r3)) {
            if ($r0.mIsRenameMode) {
                DriveToNativeManager.getInstance().renameFavorite($r0.mAddressItem.getId(), $r3);
            } else {
                $r0.mAddressItem.setTitle($r3);
                $r0.mAddressItem.setCategory(Integer.valueOf(1));
                Log.d(Logger.TAG, "fav = " + $r0.mAddressItem.toString());
                DriveToNativeManager.getInstance().StoreAddressItem($r0.mAddressItem, false);
            }
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow($r0.mNameEditText.getWindowToken(), 0);
            if ($r0.mListener != null) {
                NameFavoriteListener $r12 = $r0.mListener;
                $r12.onNameFavoriteComplete();
            }
        }
    }

    private void onCancelClick() throws  {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mNameEditText.getWindowToken(), 0);
        if (this.mListener != null) {
            this.mListener.onNameFavoriteCancelled();
        }
    }

    public void setRenameMode(boolean $z0) throws  {
        this.mIsRenameMode = $z0;
    }

    public static NameFavoriteView showNameFavorite(Activity $r0, AddressItem $r1, Runnable $r2) throws  {
        return showNameFavorite($r0, $r1, $r2, null);
    }

    public static NameFavoriteView showNameFavorite(Activity $r0, AddressItem $r1, final Runnable $r2, final Runnable $r3) throws  {
        final NameFavoriteView $r5 = new NameFavoriteView($r0);
        LayoutParams $r4 = new LayoutParams(-1, -1);
        $r5.setLayoutParams($r4);
        $r5.setListener(new NameFavoriteListener() {

            class C21341 implements Runnable {
                C21341() throws  {
                }

                public void run() throws  {
                    if ($r5.getParent() != null) {
                        ((ViewGroup) $r5.getParent()).removeView($r5);
                    }
                }
            }

            class C21352 implements Runnable {
                C21352() throws  {
                }

                public void run() throws  {
                    if ($r5.getParent() != null) {
                        ((ViewGroup) $r5.getParent()).removeView($r5);
                    }
                }
            }

            public void onNameFavoriteComplete() throws  {
                ViewPropertyAnimatorHelper.initAnimation($r5).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C21341()));
                if ($r2 != null) {
                    $r2.run();
                }
            }

            public void onNameFavoriteCancelled() throws  {
                ViewPropertyAnimatorHelper.initAnimation($r5).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C21352()));
                if ($r3 != null) {
                    $r3.run();
                }
            }
        });
        $r0.addContentView($r5, $r4);
        $r0.getWindow().setSoftInputMode(16);
        $r5.setAddressItem($r1);
        $r5.setAlpha(0.0f);
        ViewPropertyAnimatorHelper.initAnimation($r5).alpha(1.0f).setListener(null);
        return $r5;
    }
}
