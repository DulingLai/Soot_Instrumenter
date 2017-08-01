package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.map.CanvasFont;
import com.waze.settings.SettingsSpotifyActivity;
import com.waze.strings.DisplayStrings;
import com.waze.view.button.PillButton;
import com.waze.view.text.WazeTextView;

public class SpotifyPopup extends PopUp {
    private static Context mContext = null;
    private static SpotifyPopup mInstance = null;
    private static LayoutManager mLayoutManager;
    public static int[] prgmImages = new int[]{C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.music_player_artwork_placeholder, C1283R.drawable.notification_spotify_button};
    private String mAlbum = "";
    private Bitmap mBitmap = null;
    boolean mCanSkipNext = true;
    boolean mCanSkipPrevious = true;
    private int mCurrentPlayingPos = -1;
    private GridView mGv;
    private CustomAdapter mGvAdaptor;
    private boolean mInitialized = false;
    private boolean mIsPlaying = false;
    private boolean mIsSaved = false;
    private boolean mIsShown = false;
    private boolean mPlayListShow = false;
    private String mPlaylistName;
    private boolean mShowSavedButton = true;
    private String mSong = "";
    private int mTrackDuration = -1;
    private Runnable mUpdateTimer;

    class C32131 implements OnTouchListener {
        C32131() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            AlphaAnimation animation;
            switch (event.getAction()) {
                case 0:
                    if (VERSION.SDK_INT >= 11) {
                        v.animate().setDuration(100).alpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                        break;
                    }
                    animation = new AlphaAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                    animation.setDuration(100);
                    animation.setFillAfter(true);
                    v.startAnimation(animation);
                    break;
                case 1:
                case 3:
                    if (VERSION.SDK_INT >= 11) {
                        v.animate().setDuration(100).alpha(255.0f);
                        break;
                    }
                    animation = new AlphaAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f);
                    animation.setDuration(100);
                    animation.setFillAfter(true);
                    v.startAnimation(animation);
                    break;
            }
            return false;
        }
    }

    class C32142 implements OnLayoutChangeListener {
        C32142() {
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int position = SpotifyManager.getInstance().getPlayListIndex();
            if (position != -1) {
                SpotifyPopup.this.mGv.getChildAt(position).setSelected(true);
            } else {
                SpotifyPopup.this.mGv.setSelection(-1);
            }
        }
    }

    class C32153 implements OnClickListener {
        C32153() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onNextButton();
        }
    }

    class C32164 implements OnClickListener {
        C32164() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onPrevButton();
        }
    }

    class C32175 implements OnClickListener {
        C32175() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onPlayButton();
        }
    }

    class C32186 implements OnClickListener {
        C32186() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onSaveButton();
        }
    }

    class C32197 implements OnClickListener {
        C32197() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onGotoSpotidyButton();
        }
    }

    class C32208 implements OnClickListener {
        C32208() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onBackPressed();
        }
    }

    class C32219 implements OnClickListener {
        C32219() {
        }

        public void onClick(View view) {
            SpotifyPopup.this.onShowPlayList();
        }
    }

    public class CustomAdapter extends BaseAdapter {
        Context context;
        int[] imageId;
        String[] result;

        public class Holder {
            ImageView badge;
            LinearLayout bg;
            ImageView img;
            TextView tv;
        }

        public CustomAdapter(Context context, String[] prgmNameList, int[] prgmImages) {
            this.result = prgmNameList;
            this.imageId = prgmImages;
        }

        public int getCount() {
            return this.result.length;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView;
            Holder holder;
            if (convertView == null) {
                rowView = ((LayoutInflater) SpotifyPopup.this.getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.playlist_item, null);
                holder = new Holder();
                rowView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                rowView = convertView;
            }
            holder.tv = (TextView) rowView.findViewById(C1283R.id.textView1);
            holder.img = (ImageView) rowView.findViewById(C1283R.id.imageView1);
            holder.badge = (ImageView) rowView.findViewById(C1283R.id.NowPlayingBadge);
            holder.bg = (LinearLayout) rowView.findViewById(C1283R.id.playerBgView);
            holder.tv.setText(this.result[position]);
            if (position == getCount() - 1) {
                holder.img.setImageResource(C1283R.drawable.notification_spotify_button);
                holder.bg.setBackgroundColor(0);
            } else {
                holder.img.setImageResource(this.imageId[position]);
                holder.img.setBackgroundResource(C1283R.drawable.playlist_bg_selector);
            }
            holder.badge.setVisibility(8);
            holder.tv.setTextColor(SpotifyPopup.this.getResources().getColor(C1283R.color.solid_white));
            if (position != getCount() - 1) {
                SpotifyManager.getInstance().getPlayListImage(position, holder.img);
            }
            if (position == SpotifyManager.getInstance().getPlayListIndex()) {
                holder.tv.setTextColor(SpotifyPopup.this.getResources().getColor(C1283R.color.SpotifyGotoButton));
                rowView.setSelected(true);
            }
            return rowView;
        }
    }

    public static SpotifyPopup getInstance(Context context, LayoutManager layoutManager) {
        if (mInstance == null) {
            mInstance = new SpotifyPopup(context, layoutManager);
        }
        return mInstance;
    }

    public static SpotifyPopup getInstance() {
        if (mInstance == null) {
            mInstance = new SpotifyPopup(null, null);
        }
        return mInstance;
    }

    private SpotifyPopup(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        mContext = context;
        mLayoutManager = layoutManager;
    }

    private void setHighlightEffect(int id) {
        ((ImageView) findViewById(id)).setOnTouchListener(new C32131());
    }

    public void setmCurrentPlayingPos(long playingPos) {
        this.mCurrentPlayingPos = (int) playingPos;
        PillButton pb = (PillButton) findViewById(C1283R.id.MusicPlayerPlayButton);
        if (this.mTrackDuration != -1 && this.mTrackDuration != 0) {
            int percentage = (this.mCurrentPlayingPos * 100) / this.mTrackDuration;
            if (percentage > 100) {
                percentage = 100;
            }
            pb.updatePath((float) percentage);
        }
    }

    public void setmTrackDuration(long trackDuration) {
        this.mTrackDuration = (int) trackDuration;
        PillButton pb = (PillButton) findViewById(C1283R.id.MusicPlayerPlayButton);
        if (this.mTrackDuration > 0) {
            pb.setTimeoutMilliSec(this.mTrackDuration);
        }
    }

    public void refreshPlayLists() {
        if (this.mGvAdaptor != null) {
            this.mGvAdaptor.notifyDataSetChanged();
        }
    }

    public void updateSelected() {
        this.mGv.addOnLayoutChangeListener(new C32142());
        refreshPlayLists();
    }

    public void init() {
        if (!this.mInitialized) {
            this.mInitialized = true;
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.spotify_player_popup, this);
            findViewById(C1283R.id.MusicPlayerNextButton).setOnClickListener(new C32153());
            findViewById(C1283R.id.MusicPlayerPrevButton).setOnClickListener(new C32164());
            findViewById(C1283R.id.MusicPlayerPlayButton).setOnClickListener(new C32175());
            findViewById(C1283R.id.MusicPlayerSaveButton).setOnClickListener(new C32186());
            findViewById(C1283R.id.MusicPlayerGotoSpotifyButton).setOnClickListener(new C32197());
            findViewById(C1283R.id.closeSpotifyTakeoverButton).setOnClickListener(new C32208());
            findViewById(C1283R.id.MusicPlayerShowPlayListButton).setOnClickListener(new C32219());
            findViewById(C1283R.id.MusicPlayerSettingsButton).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SpotifyPopup.this.onSettings();
                }
            });
            ((WazeTextView) findViewById(C1283R.id.MusicPlayerShowPlayListButtonText)).setText(DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_SHOW_PLAYLIST));
            ((WazeTextView) findViewById(C1283R.id.MusicPlayerGotoSpotifyButtonText)).setText(DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_GO_TO));
            findViewById(C1283R.id.MusicPlayerDetailsAreaSeperatorTop).setAlpha(0.0f);
            setHighlightEffect(C1283R.id.MusicPlayerPrevButton);
            setHighlightEffect(C1283R.id.MusicPlayerNextButton);
            setHighlightEffect(C1283R.id.MusicPlayerSaveButton);
            this.mGv = (GridView) findViewById(C1283R.id.playListGridView);
            this.mGv.setChoiceMode(1);
            this.mGv.setSelector(17170445);
            this.mGv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SpotifyPopup.this.onShowPlayList();
                    if (i == SpotifyPopup.this.mGvAdaptor.getCount() - 1) {
                        SpotifyPopup.this.onGotoSpotidyButton();
                        return;
                    }
                    SpotifyManager.getInstance().playPlayList(i);
                    view.setSelected(true);
                }
            });
            String[] playListNames = SpotifyManager.getInstance().getPlayListsTitle();
            if (playListNames != null) {
                this.mGvAdaptor = new CustomAdapter(mContext, playListNames, prgmImages);
                this.mGv.setAdapter(this.mGvAdaptor);
            } else {
                findViewById(C1283R.id.MusicPlayerShowPlayListButton).setVisibility(8);
            }
            final ScrollView scrollView = (ScrollView) findViewById(C1283R.id.SpotifyScrollView);
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                class C32121 implements Runnable {
                    C32121() {
                    }

                    public void run() {
                        scrollView.fullScroll(130);
                    }
                }

                public void onGlobalLayout() {
                    if (VERSION.SDK_INT >= 16) {
                        scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    if (SpotifyPopup.this.mAlbum == null || SpotifyPopup.this.mAlbum.length() <= 0) {
                        SpotifyPopup.this.findViewById(C1283R.id.MusicPlayerShowPlayListButtonImg).animate().rotation(180.0f).start();
                        SpotifyPopup.this.findViewById(C1283R.id.MusicPlayerGotoSpotifyButton).animate().alpha(0.0f);
                        SpotifyPopup.this.findViewById(C1283R.id.MusicPlayerDetailsAreaSeperatorTop).setAlpha(0.2f);
                        SpotifyPopup.this.findViewById(C1283R.id.MusicPlayerDetailsAreaSeperator).setAlpha(0.0f);
                        SpotifyPopup.this.mPlayListShow = true;
                        return;
                    }
                    scrollView.post(new C32121());
                }
            });
            scrollView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            setAlbumName(this.mAlbum);
            setSongName(this.mSong);
        }
    }

    public void showSaveButton(boolean show) {
        this.mShowSavedButton = show;
        if (show) {
            findViewById(C1283R.id.MusicPlayerSaveButton).setVisibility(0);
        } else {
            findViewById(C1283R.id.MusicPlayerSaveButton).setVisibility(8);
        }
    }

    public void setPlayListName(String playListName) {
        if (playListName == null || playListName.length() == 0) {
            playListName = DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_SHOW_PLAYLIST);
        }
        this.mPlaylistName = playListName;
        ((WazeTextView) findViewById(C1283R.id.MusicPlayerShowPlayListButtonText)).setText(playListName);
    }

    public void updatePlayLists(String[] playListNames) {
        if (playListNames != null) {
            findViewById(C1283R.id.MusicPlayerShowPlayListButton).setVisibility(0);
            this.mGvAdaptor = new CustomAdapter(mContext, playListNames, prgmImages);
            this.mGv.setAdapter(this.mGvAdaptor);
            float scale = mContext.getResources().getDisplayMetrics().density;
            LayoutParams p1 = this.mGv.getLayoutParams();
            p1.width = (int) (((((float) (AppService.getAppContext().getResources().getConfiguration().orientation == 2 ? 160 : 100)) * scale) * ((float) playListNames.length)) + (20.0f * scale));
            this.mGv.setNumColumns(playListNames.length);
            this.mGv.setLayoutParams(p1);
            return;
        }
        findViewById(C1283R.id.MusicPlayerShowPlayListButton).setVisibility(8);
    }

    public void onShowPlayList() {
        ImageView imageView = (ImageView) findViewById(C1283R.id.MusicPlayerShowPlayListButtonImg);
        final ScrollView scrollView = (ScrollView) findViewById(C1283R.id.SpotifyScrollView);
        if (this.mPlayListShow) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_CLOSE_PLAYLISTS);
            View view = findViewById(C1283R.id.playlistLayout);
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.smoothScrollTo(0, scrollView.getBottom());
                }
            });
            imageView.animate().rotation(0.0f).start();
            findViewById(C1283R.id.MusicPlayerDetailsAreaSeperatorTop).setAlpha(0.0f);
            findViewById(C1283R.id.MusicPlayerDetailsAreaSeperator).setAlpha(0.2f);
            findViewById(C1283R.id.MusicPlayerGotoSpotifyButton).animate().alpha(1.0f);
        } else {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_OPEN_PLAYLISTS);
            imageView.animate().rotation(180.0f).start();
            findViewById(C1283R.id.MusicPlayerGotoSpotifyButton).animate().alpha(0.0f);
            findViewById(C1283R.id.MusicPlayerDetailsAreaSeperatorTop).setAlpha(0.2f);
            findViewById(C1283R.id.MusicPlayerDetailsAreaSeperator).setAlpha(0.0f);
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.smoothScrollTo(0, 0);
                }
            });
        }
        this.mPlayListShow = !this.mPlayListShow;
    }

    private void updateSavedButton() {
        if (this.mIsSaved) {
            ((ImageView) findViewById(C1283R.id.MusicPlayerSaveButton)).setImageResource(C1283R.drawable.music_player_unsave_button);
        } else {
            ((ImageView) findViewById(C1283R.id.MusicPlayerSaveButton)).setImageResource(C1283R.drawable.music_player_save_button);
        }
    }

    private void toggleSavedButton() {
        if (this.mIsSaved) {
            SpotifyManager.getInstance().unsave();
        } else {
            SpotifyManager.getInstance().save();
        }
        this.mIsSaved = !this.mIsSaved;
        updateSavedButton();
    }

    public void setSaved(boolean saved) {
        this.mIsSaved = saved;
        updateSavedButton();
    }

    public void updatePlayButton(boolean isPlaying) {
        this.mIsPlaying = isPlaying;
        if (isPlaying) {
            ((PillButton) findViewById(C1283R.id.MusicPlayerPlayButton)).setColor(C1283R.color.transparent_full);
            ((WazeTextView) findViewById(C1283R.id.MusicPlayerPlayButtonText)).setBackgroundResource(C1283R.drawable.music_player_pause_button);
            if (this.mUpdateTimer == null) {
                this.mUpdateTimer = new Runnable() {
                    public void run() {
                        SpotifyPopup.this.mCurrentPlayingPos = SpotifyPopup.this.mCurrentPlayingPos + 1000;
                        if (SpotifyPopup.this.mCurrentPlayingPos < SpotifyPopup.this.mTrackDuration) {
                            SpotifyPopup.this.setmCurrentPlayingPos((long) SpotifyPopup.this.mCurrentPlayingPos);
                            SpotifyPopup.this.postDelayed(SpotifyPopup.this.mUpdateTimer, 1000);
                        }
                    }
                };
            }
            removeCallbacks(this.mUpdateTimer);
            postDelayed(this.mUpdateTimer, 1000);
            return;
        }
        ((PillButton) findViewById(C1283R.id.MusicPlayerPlayButton)).setColor(C1283R.color.solid_white);
        ((WazeTextView) findViewById(C1283R.id.MusicPlayerPlayButtonText)).setBackgroundResource(C1283R.drawable.music_player_play_button);
        if (this.mUpdateTimer != null) {
            removeCallbacks(this.mUpdateTimer);
        }
        ((PillButton) findViewById(C1283R.id.MusicPlayerPlayButton)).updatePath(-1.0f);
        ((PillButton) findViewById(C1283R.id.MusicPlayerPlayButton)).invalidate();
    }

    public void setCanSkipNext(boolean canSkipNext) {
        this.mCanSkipNext = canSkipNext;
        findViewById(C1283R.id.MusicPlayerNextButton).setEnabled(this.mCanSkipNext);
        if (this.mCanSkipNext) {
            findViewById(C1283R.id.MusicPlayerNextButton).setAlpha(1.0f);
        } else {
            findViewById(C1283R.id.MusicPlayerNextButton).setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        }
    }

    public void setCanSkipPrevious(boolean canSkipPrevious) {
        this.mCanSkipPrevious = canSkipPrevious;
        findViewById(C1283R.id.MusicPlayerPrevButton).setEnabled(this.mCanSkipPrevious);
        if (this.mCanSkipPrevious) {
            findViewById(C1283R.id.MusicPlayerPrevButton).setAlpha(1.0f);
        } else {
            findViewById(C1283R.id.MusicPlayerPrevButton).setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        }
    }

    private void onNextButton() {
        SpotifyManager.getInstance().playNext();
    }

    private void onPrevButton() {
        SpotifyManager.getInstance().playPrevious();
    }

    private void onSaveButton() {
        toggleSavedButton();
    }

    private void onPlayButton() {
        if (this.mIsPlaying) {
            SpotifyManager.getInstance().pause();
        } else {
            SpotifyManager.getInstance().play();
        }
    }

    private void onGotoSpotidyButton() {
        SpotifyManager.getInstance().openApp();
    }

    public void setSongName(String song) {
        this.mSong = song;
        setLine(C1283R.id.MusicPlayerSongNameTextView, song);
    }

    public void setAlbumName(String albumName) {
        this.mAlbum = albumName;
        setLine(C1283R.id.MusicPlayerAlbumNameTextView, albumName);
    }

    public void setImage(Bitmap bitmap) {
        this.mBitmap = bitmap;
        ((ImageView) findViewById(C1283R.id.MusicPlayerArtWork)).setImageBitmap(bitmap);
    }

    public void hide() {
        dismiss();
    }

    public void dismiss() {
        if (AppService.getMainActivity() != null && this.mIsShown) {
            mLayoutManager = AppService.getMainActivity().getLayoutMgr();
            if (mLayoutManager != null) {
                this.mIsShown = false;
                mLayoutManager.onSpotifyClose();
                mLayoutManager.preCloseAllPopups(1);
                AppService.Post(new Runnable() {
                    public void run() {
                        SpotifyPopup.mLayoutManager.doneCloseAllPopups();
                    }
                });
            }
        }
    }

    public boolean onBackPressed() {
        if (!isShown() || AppService.getMainActivity() == null) {
            return false;
        }
        mLayoutManager = AppService.getMainActivity().getLayoutMgr();
        if (mLayoutManager == null) {
            return false;
        }
        this.mIsShown = false;
        mLayoutManager.callCloseAllPopups(1);
        mLayoutManager.preCloseAllPopups(1);
        AppService.Post(new Runnable() {
            public void run() {
                SpotifyPopup.mLayoutManager.doneCloseAllPopups();
            }
        });
        mLayoutManager.showSpotifyButton();
        return true;
    }

    void setLine(int resId, String str) {
        TextView textView = (TextView) findViewById(resId);
        if (str == null || str.isEmpty()) {
            textView.setVisibility(8);
        }
        textView.setVisibility(0);
        textView.setText(str);
    }

    public void initView() {
        if (this.mIsShown) {
            dismiss();
        }
        this.mIsShown = true;
    }

    public void onOrientationChanged() {
        super.onOrientationChanged();
        removeAllViews();
        this.mInitialized = false;
        init();
        if (this.mSong != null) {
            setSongName(this.mSong);
        }
        if (this.mAlbum != null) {
            setAlbumName(this.mAlbum);
        }
        if (this.mBitmap != null) {
            setImage(this.mBitmap);
        }
        updatePlayButton(this.mIsPlaying);
        updateSavedButton();
        updateSelected();
        setCanSkipNext(this.mCanSkipNext);
        setCanSkipPrevious(this.mCanSkipPrevious);
        setPlayListName(this.mPlaylistName);
        showSaveButton(this.mShowSavedButton);
        if (this.mGv != null && this.mGvAdaptor != null) {
            this.mGv.setNumColumns(this.mGvAdaptor.getCount());
            float scale = mContext.getResources().getDisplayMetrics().density;
            LayoutParams p1 = this.mGv.getLayoutParams();
            p1.width = (int) (((((float) (AppService.getAppContext().getResources().getConfiguration().orientation == 2 ? 160 : 100)) * scale) * ((float) this.mGvAdaptor.getCount())) + (20.0f * scale));
            this.mGv.setLayoutParams(p1);
        }
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    private void onSettings() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", "SPOTIFY_SETTINGS");
        AppService.getMainActivity().startActivityForResult(new Intent(AppService.getMainActivity(), SettingsSpotifyActivity.class), 0);
    }
}
