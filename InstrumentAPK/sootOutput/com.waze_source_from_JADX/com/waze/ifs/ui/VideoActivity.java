package com.waze.ifs.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import com.waze.C1283R;

public class VideoActivity extends ActivityBase {
    private VideoView video = null;

    class C17631 implements OnPreparedListener {
        C17631() throws  {
        }

        public void onPrepared(MediaPlayer mp) throws  {
            VideoActivity.this.findViewById(C1283R.id.videoProgress).setVisibility(8);
            ((VideoView) VideoActivity.this.findViewById(C1283R.id.videoVideo)).setVisibility(0);
        }
    }

    class C17642 implements OnCompletionListener {
        C17642() throws  {
        }

        public void onCompletion(MediaPlayer mp) throws  {
            ((VideoView) VideoActivity.this.findViewById(C1283R.id.videoVideo)).setVisibility(8);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.video);
        Intent $r3 = getIntent();
        String $r4 = $r3.getStringExtra("url");
        if ($r3.getBooleanExtra("landscape", false)) {
            setRequestedOrientation(0);
        } else {
            setRequestedOrientation(1);
        }
        this.video = (VideoView) findViewById(C1283R.id.videoVideo);
        MediaController $r2 = new MediaController(this);
        $r2.setMediaPlayer(this.video);
        this.video.setMediaController($r2);
        this.video.setVideoURI(Uri.parse($r4));
        this.video.setOnPreparedListener(new C17631());
        this.video.setOnCompletionListener(new C17642());
        this.video.start();
    }

    public void onBackPressed() throws  {
        if (this.video != null && this.video.isPlaying()) {
            this.video.stopPlayback();
        }
        super.onBackPressed();
    }
}
