package com.abaltatech.weblink.service.interfaces;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.InputEvent;
import android.view.KeyEvent;

public class WLInputEventParcelable implements Parcelable {
    public static final Creator<WLInputEventParcelable> CREATOR = new C03961();
    final InputEvent mInputEvent;

    static class C03961 implements Creator<WLInputEventParcelable> {
        C03961() throws  {
        }

        public WLInputEventParcelable createFromParcel(Parcel $r1) throws  {
            return WLInputEventParcelable.createFromParcelBody($r1);
        }

        public WLInputEventParcelable[] newArray(int $i0) throws  {
            return new WLInputEventParcelable[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public WLInputEventParcelable(InputEvent $r1) throws  {
        this.mInputEvent = $r1;
    }

    public InputEvent getEvent() throws  {
        return this.mInputEvent;
    }

    public static WLInputEventParcelable createFromParcelBody(Parcel $r0) throws  {
        InputEvent $r4 = (InputEvent) InputEvent.CREATOR.createFromParcel($r0);
        if (!($r4 instanceof KeyEvent)) {
            return new WLInputEventParcelable($r4);
        }
        String $r5 = $r0.readString();
        if ($r5 == null) {
            return new WLInputEventParcelable($r4);
        }
        return new WLInputEventParcelable(new KeyEvent($r4.getEventTime(), $r5, $r4.getDeviceId(), ((KeyEvent) $r4).getFlags()));
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        this.mInputEvent.writeToParcel($r1, $i0);
        if (this.mInputEvent instanceof KeyEvent) {
            $r1.writeString(((KeyEvent) this.mInputEvent).getCharacters());
        }
    }
}
