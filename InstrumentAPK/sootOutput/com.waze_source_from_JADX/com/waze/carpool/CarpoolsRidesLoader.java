package com.waze.carpool;

import android.os.Handler;
import android.os.Message;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import java.lang.ref.WeakReference;

public class CarpoolsRidesLoader {
    private CarpoolRidesLoaderListener mListener;
    private MyHandler myHandler;

    public interface CarpoolRidesLoaderListener {
        void onCarpoolRidesLoaded(int i) throws ;
    }

    private class MyHandler extends Handler {
        private final WeakReference<CarpoolsRidesLoader> loader;

        public MyHandler(CarpoolsRidesLoader $r2) throws  {
            this.loader = new WeakReference($r2);
        }

        public void handleMessage(Message $r1) throws  {
            if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED) {
                CarpoolDrive[] $r4 = (CarpoolDrive[]) $r1.getData().getParcelableArray("drives");
                CarpoolsRidesLoader $r7 = (CarpoolsRidesLoader) this.loader.get();
                if ($r7 != null) {
                    $r7.onDrivesLoaded($r4);
                    return;
                }
                return;
            }
            super.handleMessage($r1);
        }
    }

    public CarpoolsRidesLoader(CarpoolRidesLoaderListener $r1) throws  {
        this.mListener = $r1;
    }

    public void start() throws  {
        final CarpoolNativeManager $r2 = CarpoolNativeManager.getInstance();
        $r2.getDrives(false, new IResultObj<CarpoolDrive[]>() {
            public void onResult(CarpoolDrive[] $r1) throws  {
                if ($r1 != null) {
                    CarpoolsRidesLoader.this.onDrivesLoaded($r1);
                    return;
                }
                CarpoolsRidesLoader.this.myHandler = new MyHandler(CarpoolsRidesLoader.this);
                $r2.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, CarpoolsRidesLoader.this.myHandler);
            }
        });
    }

    private void onDrivesLoaded(CarpoolDrive[] $r1) throws  {
        int $i0 = 0;
        if ($r1 != null) {
            int $i1 = 0;
            while ($i1 < $r1.length) {
                if (!($r1[$i1] == null || $r1[$i1].isEmpty())) {
                    CarpoolRide $r4 = $r1[$i1].getRide();
                    if ($r4 != null && $r4.isPending()) {
                        $i0++;
                    }
                }
                $i1++;
            }
        }
        if (this.myHandler != null) {
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.myHandler);
        }
        this.mListener.onCarpoolRidesLoaded($i0);
    }
}
