package com.waze.reports;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.waze.NativeManager;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class OpeningHours implements Parcelable, Serializable {
    public static final Creator<OpeningHours> CREATOR = new C24951();
    public static final boolean FIRST_DAY_IS_SUNDAY;
    private static int MAX_DAYS = 7;
    private static String friday = null;
    private static String monday = null;
    private static String saturday = null;
    private static final long serialVersionUID = 1;
    private static String sunday = null;
    private static String thursday;
    private static String tuesday;
    private static String wednesday;
    public int[] days;
    public String from;
    public String to;

    static class C24951 implements Creator<OpeningHours> {
        C24951() {
        }

        public OpeningHours createFromParcel(Parcel in) {
            return new OpeningHours(in);
        }

        public OpeningHours[] newArray(int size) {
            return new OpeningHours[size];
        }
    }

    class DayStringStatus {
        boolean isFirst = true;
        String prevDayStr = null;
        boolean prevOpen = false;
        String toStr = null;
        boolean wroteTo = false;

        DayStringStatus() {
        }
    }

    static {
        boolean z = true;
        if (Calendar.getInstance().getFirstDayOfWeek() != 1) {
            z = false;
        }
        FIRST_DAY_IS_SUNDAY = z;
    }

    public OpeningHours() {
        this.days = new int[MAX_DAYS];
    }

    public OpeningHours(Parcel in) {
        this.days = new int[MAX_DAYS];
        in.readIntArray(this.days);
        this.from = in.readString();
        this.to = in.readString();
    }

    public OpeningHours(OpeningHours orig) {
        this.days = Arrays.copyOf(orig.days, MAX_DAYS);
        this.from = new String(orig.from);
        this.to = new String(orig.to);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.days);
        dest.writeString(this.from);
        dest.writeString(this.to);
    }

    public String getDaysString() {
        return getDaysString(", ");
    }

    public String getDaysString(String separator) {
        StringBuilder bob = new StringBuilder();
        NativeManager nm = NativeManager.getInstance();
        DayStringStatus s = new DayStringStatus();
        s.toStr = " " + nm.getLanguageString(DisplayStrings.DS_DAYS_TO) + " ";
        if (sunday == null) {
            SimpleDateFormat formatLetterDay = new SimpleDateFormat("EEE", new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV()));
            Calendar c = Calendar.getInstance();
            c.set(7, 1);
            sunday = formatLetterDay.format(c.getTime());
            c.set(7, 2);
            monday = formatLetterDay.format(c.getTime());
            c.set(7, 3);
            tuesday = formatLetterDay.format(c.getTime());
            c.set(7, 4);
            wednesday = formatLetterDay.format(c.getTime());
            c.set(7, 5);
            thursday = formatLetterDay.format(c.getTime());
            c.set(7, 6);
            friday = formatLetterDay.format(c.getTime());
            c.set(7, 7);
            saturday = formatLetterDay.format(c.getTime());
        }
        if (FIRST_DAY_IS_SUNDAY) {
            addDay(bob, this.days[0] != 0, sunday, s, separator);
        }
        addDay(bob, this.days[1] != 0, monday, s, separator);
        addDay(bob, this.days[2] != 0, tuesday, s, separator);
        addDay(bob, this.days[3] != 0, wednesday, s, separator);
        addDay(bob, this.days[4] != 0, thursday, s, separator);
        addDay(bob, this.days[5] != 0, friday, s, separator);
        addDay(bob, this.days[6] != 0, saturday, s, separator);
        if (!FIRST_DAY_IS_SUNDAY) {
            addDay(bob, this.days[0] != 0, sunday, s, separator);
        }
        addDay(bob, false, null, s, separator);
        return bob.toString();
    }

    private void addDay(StringBuilder bob, boolean bThisDayOpen, String thisDayStr, DayStringStatus s, String separator) {
        if (!bThisDayOpen) {
            if (s.wroteTo) {
                bob.append(s.prevDayStr);
            }
            s.wroteTo = false;
            s.prevOpen = false;
        } else if (!s.prevOpen) {
            if (!s.isFirst) {
                bob.append(separator);
            }
            bob.append(thisDayStr);
            s.prevOpen = true;
            s.isFirst = false;
        } else if (!s.wroteTo) {
            bob.append(s.toStr);
            s.wroteTo = true;
        }
        s.prevDayStr = thisDayStr;
    }

    public String getHoursString() {
        if ((this.from == null || this.from.isEmpty()) && (this.to == null || this.to.isEmpty())) {
            return NativeManager.getInstance().getLanguageString(DisplayStrings.DS_24_HOURS);
        }
        if (this.from == null || this.from.isEmpty()) {
            this.from = "0:00";
        }
        if (this.to == null || this.to.isEmpty()) {
            this.to = "24:00";
        }
        if (this.from.equals(this.to)) {
            return NativeManager.getInstance().getLanguageString(DisplayStrings.DS_24_HOURS);
        }
        return String.format("%s - %s", new Object[]{this.from, this.to});
    }
}
