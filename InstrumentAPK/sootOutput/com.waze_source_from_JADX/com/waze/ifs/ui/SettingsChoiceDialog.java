package com.waze.ifs.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.settings.SettingsDialogListener;
import com.waze.view.anim.AnimationUtils;
import dalvik.annotation.Signature;

public class SettingsChoiceDialog extends Dialog {
    private static final int CORNERS_ANIM = 300;
    private ListView mContentList;
    final Context mCtx;
    final float mDensity;
    private View mFrame;
    private TextView mTitle;
    int selected;
    int toSelect = -1;
    int toUnselect = -1;

    class C17491 implements OnClickListener {
        C17491() throws  {
        }

        public void onClick(View v) throws  {
            SettingsChoiceDialog.this.dismiss();
        }
    }

    private final class ChoiceDialogAdapter extends BaseAdapter {
        LayoutInflater inflater;
        private final String[] options;

        private ChoiceDialogAdapter(String[] $r2) throws  {
            this.options = $r2;
            this.inflater = (LayoutInflater) SettingsChoiceDialog.this.mCtx.getSystemService("layout_inflater");
        }

        public int getCount() throws  {
            return this.options.length;
        }

        public Object getItem(int $i0) throws  {
            return this.options[$i0];
        }

        public long getItemId(int $i0) throws  {
            return (long) this.options[$i0].hashCode();
        }

        public View getView(int $i0, View $r2, ViewGroup $r1) throws  {
            byte $b2;
            if ($r2 == null) {
                $r2 = this.inflater.inflate(C1283R.layout.settings_dialog_list_item, $r1, false);
            }
            ((TextView) $r2.findViewById(C1283R.id.settingsDialogItemText)).setText((String) getItem($i0));
            boolean $z0 = $i0 == getCount() + -1;
            View $r6 = $r2.findViewById(C1283R.id.settingsDialogItemSeparator);
            if ($z0) {
                $b2 = (byte) 8;
            } else {
                $b2 = (byte) 0;
            }
            $r6.setVisibility($b2);
            WazeRadioView $r8 = (WazeRadioView) $r2.findViewById(C1283R.id.settingsDialogItemRadio);
            SettingsChoiceDialog $r9 = SettingsChoiceDialog.this;
            if ($i0 == $r9.toSelect) {
                $r8.setValue(false);
                $r8.setValueAnimated(true);
                return $r2;
            }
            $r9 = SettingsChoiceDialog.this;
            if ($i0 == $r9.toUnselect) {
                $r8.setValue(true);
                $r8.setValueAnimated(false);
                return $r2;
            }
            $r9 = SettingsChoiceDialog.this;
            if ($i0 == $r9.selected) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $r8.setValue($z0);
            return $r2;
        }
    }

    public SettingsChoiceDialog(Context $r1, String $r2, String[] $r3, int $i0, SettingsDialogListener $r4) throws  {
        super($r1, C1283R.style.GrowShrinkDialog);
        setContentView(C1283R.layout.settings_dialog);
        this.mDensity = $r1.getResources().getDisplayMetrics().density;
        this.mCtx = $r1;
        Window $r7 = getWindow();
        $r7.setLayout(-1, -1);
        $r7.setGravity(17);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.mFrame = findViewById(C1283R.id.settingsDialogFrame);
        this.mFrame.setOnClickListener(new C17491());
        NativeManager.getInstance();
        this.mTitle = (TextView) findViewById(C1283R.id.settingsDialogTitle);
        this.mTitle.setText($r2);
        this.selected = $i0;
        this.mContentList = (ListView) findViewById(C1283R.id.settingsDialogList);
        this.mContentList.setDivider(null);
        this.mContentList.setDividerHeight(0);
        this.mContentList.setAdapter(new ChoiceDialogAdapter($r3));
        final SettingsDialogListener settingsDialogListener = $r4;
        this.mContentList.setOnItemClickListener(new OnItemClickListener() {

            class C17501 implements Runnable {
                C17501() throws  {
                }

                public void run() throws  {
                    SettingsChoiceDialog.this.toSelect = -1;
                    SettingsChoiceDialog.this.toUnselect = -1;
                    settingsDialogListener.onComplete(SettingsChoiceDialog.this.selected);
                    SettingsChoiceDialog.this.dismiss();
                }
            }

            public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                if (SettingsChoiceDialog.this.selected != $i0) {
                    SettingsChoiceDialog.this.toSelect = $i0;
                    SettingsChoiceDialog.this.toUnselect = SettingsChoiceDialog.this.selected;
                    SettingsChoiceDialog.this.selected = $i0;
                    SettingsChoiceDialog.this.mContentList.invalidateViews();
                }
                SettingsChoiceDialog.this.mContentList.postDelayed(new C17501(), 200);
            }
        });
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(this.mContentList);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        if ($r1.getAction() == 0) {
            Rect $r2 = new Rect();
            this.mFrame.getHitRect($r2);
            if (!$r2.contains((int) $r1.getX(), (int) $r1.getY())) {
                dismiss();
            }
        }
        return super.dispatchTouchEvent($r1);
    }
}
