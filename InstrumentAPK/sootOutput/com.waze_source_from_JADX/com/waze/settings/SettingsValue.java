package com.waze.settings;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View;
import java.util.Comparator;

public class SettingsValue implements Comparable<SettingsValue>, Parcelable {
    public static final Creator<SettingsValue> CREATOR = new C27852();
    public static Comparator<SettingsValue> comparator = new C27841();
    public String[] aliases;
    public View custom = null;
    public String display;
    public String display2 = "";
    public Drawable icon = null;
    public String iconName = null;
    public int index;
    public boolean isHeader = false;
    public boolean isSelected;
    public float rank = 0.0f;
    public String value;

    static class C27841 implements Comparator<SettingsValue> {
        C27841() {
        }

        public int compare(SettingsValue setting1, SettingsValue setting2) {
            return setting1.display.toUpperCase().compareTo(setting2.display.toUpperCase());
        }
    }

    static class C27852 implements Creator<SettingsValue> {
        C27852() {
        }

        public SettingsValue createFromParcel(Parcel in) {
            return new SettingsValue(in);
        }

        public SettingsValue[] newArray(int size) {
            return new SettingsValue[size];
        }
    }

    public SettingsValue(String value, String display, boolean isSelected) {
        this.display = display;
        this.value = value;
        this.isSelected = isSelected;
    }

    public SettingsValue(String value, String display, String displaySub, boolean isSelected) {
        this.display = display;
        this.value = value;
        this.isSelected = isSelected;
        this.display2 = displaySub;
    }

    public SettingsValue(float rank, String display, Drawable icon) {
        this.display = display;
        this.rank = rank;
        this.icon = icon;
    }

    public SettingsValue(View customView) {
        this.custom = customView;
    }

    public String toString() {
        return this.display;
    }

    public int compareTo(SettingsValue another) {
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 1;
        dest.writeString(this.display);
        dest.writeString(this.value);
        dest.writeInt(this.isSelected ? 1 : 0);
        dest.writeString(this.display2);
        dest.writeFloat(this.rank);
        if (!this.isHeader) {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeString(this.iconName);
    }

    public SettingsValue(Parcel in) {
        boolean z = true;
        this.display = in.readString();
        this.value = in.readString();
        this.isSelected = in.readInt() != 0;
        this.display2 = in.readString();
        this.rank = in.readFloat();
        if (in.readInt() == 0) {
            z = false;
        }
        this.isHeader = z;
        this.iconName = in.readString();
    }
}
