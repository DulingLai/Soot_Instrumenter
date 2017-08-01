package com.waze.view.web;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.ScreenShotManager;
import com.waze.WzWebView;
import com.waze.WzWebView.WebViewBackCallback;
import com.waze.WzWebView.WebViewPageProgressCallback;
import com.waze.WzWebView.WebViewSizeCallback;
import com.waze.WzWebView.WebViewUrlOverride;
import com.waze.ifs.ui.ActivityBase;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.title.TitleBar;
import java.io.File;
import java.net.URL;

public class SimpleWebActivity extends ActivityBase implements WebViewSizeCallback {
    public static final String EXTRA_TITLE = "webViewTitle";
    public static final String EXTRA_URL = "webViewURL";
    protected final String WAZE_URL_CLOSE = "waze://browser_close";
    protected final String WAZE_URL_ERROR = "waze://browser_error";
    protected final String WAZE_URL_HIDE = "waze://dialog_hide_current";
    private String mAcceptType;
    protected TitleBar mTitleBar;
    UploadHandler mUploadHandler;
    private ValueCallback<Uri> mUploadMsg;
    protected String mUrl;
    private MyWebChromeClient mWebChromeClient;
    private ProgressAnimation mWebViewLoadAnimation;
    protected WzWebView webView;

    class C32781 implements WebViewBackCallback {
        C32781() {
        }

        public boolean onBackEvent(KeyEvent aEvent) {
            return false;
        }
    }

    class C32792 implements WebViewUrlOverride {
        C32792() {
        }

        public boolean onUrlOverride(WebView view, String url) {
            return SimpleWebActivity.this.onUrlOverride(url);
        }
    }

    class C32803 implements WebViewPageProgressCallback {
        C32803() {
        }

        public void onWebViewPageStarted() {
            SimpleWebActivity.this.onLoadStarted();
        }

        public void onWebViewPageFinished() {
            SimpleWebActivity.this.onLoadFinished();
        }
    }

    class C32814 implements OnClickListener {
        C32814() {
        }

        public void onClick(View v) {
            SimpleWebActivity.this.setResult(0);
            SimpleWebActivity.this.finish();
        }
    }

    class Controller {
        static final int FILE_SELECTED = 4;

        Controller() {
        }

        Activity getActivity() {
            return SimpleWebActivity.this;
        }
    }

    final class MyWebChromeClient extends WebChromeClient {
        MyWebChromeClient() {
        }

        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new Builder(SimpleWebActivity.this, C1283R.style.CustomPopup).setTitle("").setMessage(message).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).create().show();
            return true;
        }

        private String getTitleFromUrl(String url) {
            String title = url;
            try {
                URL urlObj = new URL(url);
                String host = urlObj.getHost();
                if (host != null && !host.isEmpty()) {
                    return urlObj.getProtocol() + "://" + host;
                }
                if (url.startsWith("file:")) {
                    String fileName = urlObj.getFile();
                    if (!(fileName == null || fileName.isEmpty())) {
                        return fileName;
                    }
                }
                return title;
            } catch (Exception e) {
            }
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, "", "filesystem");
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            SimpleWebActivity.this.mUploadHandler = new UploadHandler(new Controller());
            SimpleWebActivity.this.mUploadHandler.openFileChooser(uploadMsg, acceptType, capture);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            String[] acceptTypes = fileChooserParams.getAcceptTypes();
            String acceptType = "";
            int i = 0;
            while (i < acceptTypes.length) {
                if (!(acceptTypes[i] == null || acceptTypes[i].length() == 0)) {
                    acceptType = acceptType + acceptTypes[i] + ";";
                }
                i++;
            }
            if (acceptType.length() == 0) {
                acceptType = "*/*";
            }
            final ValueCallback<Uri[]> finalFilePathCallback = filePathCallback;
            ValueCallback<Uri> vc = new ValueCallback<Uri>() {
                public void onReceiveValue(Uri value) {
                    finalFilePathCallback.onReceiveValue(value != null ? new Uri[]{value} : null);
                }
            };
            if (ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.CAMERA") == 0) {
                openFileChooser(vc, acceptType, "filesystem");
            } else {
                SimpleWebActivity.this.mUploadMsg = vc;
                SimpleWebActivity.this.mAcceptType = acceptType;
                ActivityCompat.requestPermissions(AppService.getActiveActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 4212);
            }
            return true;
        }
    }

    class UploadHandler {
        private String mCameraFilePath;
        private boolean mCaughtActivityNotFoundException;
        private Controller mController;
        private boolean mHandled;
        private ValueCallback<Uri> mUploadMessage;

        public UploadHandler(Controller controller) {
            this.mController = controller;
        }

        String getFilePath() {
            return this.mCameraFilePath;
        }

        boolean handled() {
            return this.mHandled;
        }

        void onResult(int resultCode, Intent intent) {
            if (resultCode == 0 && this.mCaughtActivityNotFoundException) {
                this.mCaughtActivityNotFoundException = false;
                return;
            }
            Uri result;
            if (intent == null || resultCode != -1) {
                result = null;
            } else {
                result = intent.getData();
            }
            if (result == null && resultCode == -1) {
                File cameraFile = new File(this.mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    this.mController.getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", result));
                }
            }
            this.mUploadMessage.onReceiveValue(result);
            this.mHandled = true;
            this.mCaughtActivityNotFoundException = false;
        }

        void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            String imageMimeType = "image/*";
            String videoMimeType = "video/*";
            String audioMimeType = "audio/*";
            String mediaSourceKey = "capture";
            String mediaSourceValueCamera = "camera";
            String mediaSourceValueFileSystem = "filesystem";
            String mediaSourceValueCamcorder = "camcorder";
            String mediaSourceValueMicrophone = "microphone";
            String mediaSource = "filesystem";
            if (this.mUploadMessage == null) {
                this.mUploadMessage = uploadMsg;
                String[] params = acceptType.split(";");
                String mimeType = params[0];
                if (capture.length() > 0) {
                    mediaSource = capture;
                }
                if (capture.equals("filesystem")) {
                    for (String p : params) {
                        String[] keyValue = p.split("=");
                        if (keyValue.length == 2 && "capture".equals(keyValue[0])) {
                            mediaSource = keyValue[1];
                        }
                    }
                }
                this.mCameraFilePath = null;
                Intent chooser;
                if (mimeType.equals("image/*")) {
                    if (mediaSource.equals("camera")) {
                        startActivity(createCameraIntent());
                        return;
                    }
                    chooser = createChooserIntent(createCameraIntent());
                    chooser.putExtra("android.intent.extra.INTENT", createOpenableIntent("image/*"));
                    startActivity(chooser);
                } else if (mimeType.equals("video/*")) {
                    if (mediaSource.equals("camcorder")) {
                        startActivity(createCamcorderIntent());
                        return;
                    }
                    chooser = createChooserIntent(createCamcorderIntent());
                    chooser.putExtra("android.intent.extra.INTENT", createOpenableIntent("video/*"));
                    startActivity(chooser);
                } else if (!mimeType.equals("audio/*")) {
                    startActivity(createDefaultOpenableIntent());
                } else if (mediaSource.equals("microphone")) {
                    startActivity(createSoundRecorderIntent());
                } else {
                    chooser = createChooserIntent(createSoundRecorderIntent());
                    chooser.putExtra("android.intent.extra.INTENT", createOpenableIntent("audio/*"));
                    startActivity(chooser);
                }
            }
        }

        private void startActivity(Intent intent) {
            try {
                this.mController.getActivity().startActivityForResult(intent, 4);
            } catch (ActivityNotFoundException e) {
                try {
                    this.mCaughtActivityNotFoundException = true;
                    this.mController.getActivity().startActivityForResult(createDefaultOpenableIntent(), 4);
                } catch (ActivityNotFoundException e2) {
                }
            }
        }

        private Intent createDefaultOpenableIntent() {
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            Intent chooser = createChooserIntent(createCameraIntent(), createCamcorderIntent(), createSoundRecorderIntent());
            chooser.putExtra("android.intent.extra.INTENT", i);
            return chooser;
        }

        private Intent createChooserIntent(Intent... intents) {
            Intent chooser = new Intent("android.intent.action.CHOOSER");
            chooser.putExtra("android.intent.extra.INITIAL_INTENTS", intents);
            return chooser;
        }

        private Intent createOpenableIntent(String type) {
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType(type);
            return i;
        }

        private Intent createCameraIntent() {
            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            File cameraDataDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + "browser-photos");
            cameraDataDir.mkdirs();
            this.mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ScreenShotManager.mScrnShotsNameSuffix;
            cameraIntent.putExtra("output", Uri.fromFile(new File(this.mCameraFilePath)));
            return cameraIntent;
        }

        private Intent createCamcorderIntent() {
            return new Intent("android.media.action.VIDEO_CAPTURE");
        }

        private Intent createSoundRecorderIntent() {
            return new Intent("android.provider.MediaStore.RECORD_SOUND");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.web_simple);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.webTitle);
        this.mWebViewLoadAnimation = (ProgressAnimation) findViewById(C1283R.id.webTitleProgress);
        this.webView = (WzWebView) findViewById(C1283R.id.webView);
        this.webView.setFlags(65536);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.mWebChromeClient = new MyWebChromeClient();
        this.webView.setWebChromeClient(this.mWebChromeClient);
        this.webView.setBackCallback(new C32781());
        this.webView.setUrlOverride(new C32792());
        this.webView.setPageProgressCallback(new C32803());
        this.webView.setSizeCallback(this);
        String url = getIntent().getStringExtra(EXTRA_URL);
        if (url != null) {
            loadUrl(url);
        }
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (title != null) {
            setTitleStr(title);
            this.mTitleBar.setOnClickCloseListener(new C32814());
        }
    }

    protected void onLoadStarted() {
        this.mTitleBar.findViewById(C1283R.id.titleBarTitleText).setVisibility(4);
        this.mWebViewLoadAnimation.setVisibility(0);
        this.mWebViewLoadAnimation.start();
    }

    protected void onLoadFinished() {
        this.mWebViewLoadAnimation.stop();
        this.mWebViewLoadAnimation.setVisibility(8);
        this.mTitleBar.findViewById(C1283R.id.titleBarTitleText).setVisibility(0);
    }

    public void onBackPressed() {
        if (passBackPresses() && this.webView != null && this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected void onDestroy() {
        if (this.webView != null) {
            this.webView = null;
        }
        super.onDestroy();
    }

    public void setWebViewFlags(int flags) {
        if (this.webView != null) {
            this.webView.setFlags(flags);
        }
    }

    public void onWebViewSize(int width, int height) {
    }

    protected void onSaveInstanceState(Bundle state) {
        if (this.webView != null) {
            Bundle webViewState = new Bundle();
            this.webView.saveState(webViewState);
            state.putBundle("webViewState", webViewState);
        }
        super.onSaveInstanceState(state);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (this.webView != null) {
            this.webView.restoreState(state.getBundle("webViewState"));
        }
    }

    protected boolean canGoBack() {
        return this.webView != null && this.webView.canGoBack();
    }

    protected void setTitleText(int resTitle, boolean showClose) {
        if (showClose) {
            this.mTitleBar.init((Activity) this, getString(resTitle));
        } else {
            this.mTitleBar.init((Activity) this, getString(resTitle), showClose);
        }
    }

    protected void setTitleText(int resTitle) {
        setTitleText(resTitle, true);
    }

    protected void setTitleStr(String title) {
        this.mTitleBar.init((Activity) this, title);
    }

    protected void loadUrl(String url) {
        if (this.webView != null) {
            Logger.d("SimpleWebActivity.loadUrl() url=" + url);
            this.webView.loadUrl(url);
            this.mUrl = url;
        }
    }

    protected boolean onUrlOverride(String url) {
        return false;
    }

    protected boolean passBackPresses() {
        return true;
    }

    protected WzWebView getWebView() {
        return this.webView;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4212 && grantResults.length > 1 && grantResults[0] == 0 && grantResults[1] == 0) {
            this.mWebChromeClient.openFileChooser(this.mUploadMsg, this.mAcceptType, "filesystem");
        } else if (requestCode == 4212 && this.mUploadMsg != null) {
            this.mUploadMsg.onReceiveValue(null);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mUploadHandler != null) {
            this.mUploadHandler.onResult(resultCode, data);
        }
    }
}
