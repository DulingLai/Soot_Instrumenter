package com.waze.navigate;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import com.waze.Logger;
import com.waze.autocomplete.PlaceData;
import com.waze.config.ConfigValues;
import com.waze.push.Alerter;
import com.waze.reports.VenueData;
import com.waze.strings.DisplayStrings;
import java.io.Serializable;

public class AddressItem implements Serializable {
    public static final int CALENDAR_ENC_ITEM = 12;
    public static final int CALENDAR_ITEM = 11;
    public static final int CATEGORY_CALENDAR = 7;
    public static final int CATEGORY_CONTACT = 5;
    public static final int CATEGORY_ERROR = 4;
    public static final int CATEGORY_EVENTS = 6;
    public static final int CATEGORY_FAVORITE = 1;
    public static final int CATEGORY_FAVORITE_HISTORY = 150;
    public static final int CATEGORY_HISTORY = 2;
    public static final int CATEGORY_OTHER = 99;
    public static final int CATEGORY_PLANNED_DRIVE = 9;
    public static final int CATEGORY_SEARCH = 3;
    public static final int CATEGORY_SHARED = 8;
    public static final int CUR_LOC_ITEM = 16;
    public static final int DROPOFF_ITEM = 14;
    public static final int EVENT_ITEM = 9;
    public static final int FAVORITE_ITEM = 5;
    public static final int FAVORITE_ITEM_EMPTY = 6;
    public static final int FB_ENC_ITEM = 10;
    public static final int HISTORY_ITEM = 8;
    public static final int HOME_ITEM = 1;
    public static final int HOME_ITEM_EMPTY = 2;
    public static final String[] NO_ADDITIONS = new String[0];
    public static final int PARKING_ITEM = 20;
    public static final int PICKUP_ITEM = 15;
    public static final int PLANNED_DRIVE_FOLDER_ITEM = 16;
    public static final int SEARCH_ITEM = 7;
    public static final int SHARED_ITEM = 13;
    public static final int UNKNOWN_ITEM = 0;
    public static final int WORK_ITEM = 3;
    public static final int WORK_ITEM_EMPTY = 4;
    private static final long serialVersionUID = 1;
    private String MeetingId;
    public String VanueID;
    private String accreditation;
    private String[] additions;
    private String address;
    public String adsContext;
    public int advPointId;
    public String brand;
    public String brandId;
    private Integer category;
    private String categoryDesc;
    private String city;
    private String country;
    public String currency;
    public String dealId;
    public String dealText;
    public String dealTitle;
    public int dealType;
    private String distance;
    public int distanceMeters;
    private String house;
    private String icon;
    private String id;
    private Integer image;
    public int index;
    public boolean isAddNewFavorite;
    private boolean isRecurring;
    private Boolean is_validate;
    public long lastUpdated;
    private Integer locationX;
    private Integer locationY;
    AdvertiserData mAdvertiserData;
    public String mImageURL;
    public boolean mIsNavigable;
    public String mPreviewIcon;
    public boolean mSpecificIcon;
    private Boolean more_action;
    private String phone;
    public float price;
    public String price_format;
    public int range;
    private String resultId;
    public String routingContext;
    private String secondaryTitle;
    private String special_url;
    public boolean sponsored;
    private String starttime;
    private String state;
    private String street;
    private String title;
    private Integer type;
    private String url;
    public VenueData venueData;

    public static class AdvertiserData implements Serializable {
        private static final long serialVersionUID = 1;
        String cookie;
        String query;
        int session;
        String url;
    }

    private void init(int r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, java.lang.String r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, java.lang.String r28, java.lang.String r29, java.lang.String r30, com.waze.reports.VenueData r31, java.lang.String r32) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:30:0x00cf
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        if (r10 == 0) goto L_0x00c7;
    L_0x0002:
        r1 = java.lang.Integer.parseInt(r10);
        r2 = java.lang.Integer.valueOf(r1);
        r8.locationX = r2;
    L_0x000c:
        if (r11 == 0) goto L_0x00d3;
    L_0x000e:
        r1 = java.lang.Integer.parseInt(r11);
        r2 = java.lang.Integer.valueOf(r1);
        r8.locationY = r2;
    L_0x0018:
        r8.address = r14;
        r8.title = r12;
        r8.secondaryTitle = r13;
        r8.distance = r15;
        if (r21 == 0) goto L_0x002e;
    L_0x0022:
        r0 = r21;
        r3 = java.lang.Boolean.parseBoolean(r0);
        r4 = java.lang.Boolean.valueOf(r3);
        r8.more_action = r4;
    L_0x002e:
        if (r25 == 0) goto L_0x003c;
    L_0x0030:
        r0 = r25;
        r1 = java.lang.Integer.parseInt(r0);
        r2 = java.lang.Integer.valueOf(r1);
        r8.type = r2;
    L_0x003c:
        if (r22 == 0) goto L_0x006d;
    L_0x003e:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "AddressItem - Got non-null image: ";
        r5 = r5.append(r6);
        r0 = r22;
        r5 = r5.append(r0);
        r10 = r5.toString();
        com.waze.Logger.m41i(r10);
        r6 = "list_icon_home";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x00df;
    L_0x0060:
        goto L_0x0064;
    L_0x0061:
        goto L_0x000c;
    L_0x0064:
        r7 = 2130838435; // 0x7f0203a3 float:1.7281852E38 double:1.0527740676E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
    L_0x006d:
        if (r23 == 0) goto L_0x0084;
    L_0x006f:
        goto L_0x0073;
    L_0x0070:
        goto L_0x0018;
    L_0x0073:
        r6 = "F";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x029f;
    L_0x007d:
        r7 = 1;
        r2 = java.lang.Integer.valueOf(r7);
        r8.category = r2;
    L_0x0084:
        r0 = r24;
        r8.id = r0;
        r0 = r26;
        r8.url = r0;
        r0 = r30;
        r8.VanueID = r0;
        r0 = r27;
        r8.special_url = r0;
        r0 = r28;
        r8.icon = r0;
        r8.index = r9;
        r0 = r29;
        r8.accreditation = r0;
        r0 = r16;
        r8.country = r0;
        r0 = r17;
        r8.state = r0;
        r0 = r18;
        r8.city = r0;
        r0 = r19;
        r8.street = r0;
        goto L_0x00b2;
    L_0x00af:
        goto L_0x006d;
    L_0x00b2:
        goto L_0x00b6;
    L_0x00b3:
        goto L_0x006d;
    L_0x00b6:
        r0 = r20;
        r8.house = r0;
        r0 = r31;
        r8.venueData = r0;
        goto L_0x00c2;
    L_0x00bf:
        goto L_0x006d;
    L_0x00c2:
        r0 = r32;
        r8.routingContext = r0;
        return;
    L_0x00c7:
        r7 = -1;
        r2 = java.lang.Integer.valueOf(r7);
        r8.locationX = r2;
        goto L_0x0061;
        goto L_0x00d3;
    L_0x00d0:
        goto L_0x006d;
    L_0x00d3:
        r7 = -1;
        r2 = java.lang.Integer.valueOf(r7);
        r8.locationY = r2;
        goto L_0x0070;
        goto L_0x00df;
    L_0x00dc:
        goto L_0x006d;
    L_0x00df:
        r6 = "list_icon_work";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x00f3;
    L_0x00e9:
        r7 = 2130838498; // 0x7f0203e2 float:1.728198E38 double:1.0527740987E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        goto L_0x00af;
    L_0x00f3:
        r6 = "list_icon_history";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0107;
    L_0x00fd:
        r7 = 2130838434; // 0x7f0203a2 float:1.728185E38 double:1.052774067E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        goto L_0x00b3;
    L_0x0107:
        r6 = "Ads";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x011b;
    L_0x0111:
        r7 = 2130837609; // 0x7f020069 float:1.7280177E38 double:1.0527736595E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        goto L_0x00bf;
    L_0x011b:
        r6 = "Event";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0141;
    L_0x0125:
        r7 = 2130838405; // 0x7f020385 float:1.7281791E38 double:1.0527740527E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        r6 = "Tap to verify";
        r8.secondaryTitle = r6;
        r7 = 0;
        r4 = java.lang.Boolean.valueOf(r7);
        r8.is_validate = r4;
        r7 = 0;
        r4 = java.lang.Boolean.valueOf(r7);
        r8.more_action = r4;
        goto L_0x00d0;
    L_0x0141:
        r6 = "PlanDrive";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0155;
    L_0x014b:
        r7 = 2130838440; // 0x7f0203a8 float:1.7281862E38 double:1.05277407E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        goto L_0x00dc;
    L_0x0155:
        r6 = "EventVerified";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0174;
    L_0x015f:
        r7 = 2130838405; // 0x7f020385 float:1.7281791E38 double:1.0527740527E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        r7 = 1;
        r4 = java.lang.Boolean.valueOf(r7);
        goto L_0x0171;
    L_0x016e:
        goto L_0x006d;
    L_0x0171:
        r8.is_validate = r4;
        goto L_0x016e;
    L_0x0174:
        r6 = "list_icon_deals";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x018c;
    L_0x017e:
        r7 = 2130838412; // 0x7f02038c float:1.7281806E38 double:1.052774056E-314;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x0189;
    L_0x0186:
        goto L_0x006d;
    L_0x0189:
        r8.image = r2;
        goto L_0x0186;
    L_0x018c:
        r6 = "list_icon_addfavorite";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x01a7;
    L_0x0196:
        r7 = 2130838401; // 0x7f020381 float:1.7281783E38 double:1.052774051E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        goto L_0x01a3;
    L_0x01a0:
        goto L_0x006d;
    L_0x01a3:
        r7 = 1;
        r8.isAddNewFavorite = r7;
        goto L_0x01a0;
    L_0x01a7:
        r6 = "list_icon_favorite";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x01bf;
    L_0x01b1:
        r7 = 2130838425; // 0x7f020399 float:1.7281832E38 double:1.0527740626E-314;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x01bc;
    L_0x01b9:
        goto L_0x006d;
    L_0x01bc:
        r8.image = r2;
        goto L_0x01b9;
    L_0x01bf:
        r6 = "list_icon_calendar_fb";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 != 0) goto L_0x01d3;
    L_0x01c9:
        r6 = "ic_settings_calendar_fb";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x01e8;
    L_0x01d3:
        r7 = 2130838405; // 0x7f020385 float:1.7281791E38 double:1.0527740527E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        r7 = 1;
        r4 = java.lang.Boolean.valueOf(r7);
        goto L_0x01e5;
    L_0x01e2:
        goto L_0x006d;
    L_0x01e5:
        r8.is_validate = r4;
        goto L_0x01e2;
    L_0x01e8:
        r6 = "calendar_blue";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0207;
    L_0x01f2:
        r7 = 2130838404; // 0x7f020384 float:1.728179E38 double:1.0527740523E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        r7 = 0;
        r4 = java.lang.Boolean.valueOf(r7);
        goto L_0x0204;
    L_0x0201:
        goto L_0x006d;
    L_0x0204:
        r8.is_validate = r4;
        goto L_0x0201;
    L_0x0207:
        r6 = "list_icon_calendar";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 != 0) goto L_0x021b;
    L_0x0211:
        r6 = "list_icon_calendar_settings";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0230;
    L_0x021b:
        r7 = 2130838404; // 0x7f020384 float:1.728179E38 double:1.0527740523E-314;
        r2 = java.lang.Integer.valueOf(r7);
        r8.image = r2;
        r7 = 1;
        r4 = java.lang.Boolean.valueOf(r7);
        goto L_0x022d;
    L_0x022a:
        goto L_0x006d;
    L_0x022d:
        r8.is_validate = r4;
        goto L_0x022a;
    L_0x0230:
        r6 = "Star";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0248;
    L_0x023a:
        r7 = 2130838425; // 0x7f020399 float:1.7281832E38 double:1.0527740626E-314;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x0245;
    L_0x0242:
        goto L_0x006d;
    L_0x0245:
        r8.image = r2;
        goto L_0x0242;
    L_0x0248:
        r6 = "category_menu_GAS_STATION";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0260;
    L_0x0252:
        r7 = 2130839122; // 0x7f020652 float:1.7283246E38 double:1.052774407E-314;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x025d;
    L_0x025a:
        goto L_0x006d;
    L_0x025d:
        r8.image = r2;
        goto L_0x025a;
    L_0x0260:
        r6 = "category_menu_default";
        r0 = r22;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0278;
    L_0x026a:
        r7 = 2130839124; // 0x7f020654 float:1.728325E38 double:1.052774408E-314;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x0275;
    L_0x0272:
        goto L_0x006d;
    L_0x0275:
        r8.image = r2;
        goto L_0x0272;
    L_0x0278:
        r2 = r8.type;
        r1 = r2.intValue();
        r7 = 13;
        if (r1 == r7) goto L_0x029a;
    L_0x0282:
        r2 = r8.type;
        r1 = r2.intValue();
        r7 = 14;
        if (r1 == r7) goto L_0x029a;
    L_0x028c:
        r2 = r8.type;
        r1 = r2.intValue();
        r7 = 15;
        if (r1 != r7) goto L_0x006d;
    L_0x0296:
        goto L_0x029a;
    L_0x0297:
        goto L_0x006d;
    L_0x029a:
        r0 = r22;
        r8.mImageURL = r0;
        goto L_0x0297;
    L_0x029f:
        r6 = "A";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x02b5;
    L_0x02a9:
        r7 = 2;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x02b2;
    L_0x02af:
        goto L_0x0084;
    L_0x02b2:
        r8.category = r2;
        goto L_0x02af;
    L_0x02b5:
        r6 = "S";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x02cb;
    L_0x02bf:
        r7 = 3;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x02c8;
    L_0x02c5:
        goto L_0x0084;
    L_0x02c8:
        r8.category = r2;
        goto L_0x02c5;
    L_0x02cb:
        r6 = "E";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x02e1;
    L_0x02d5:
        r7 = 6;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x02de;
    L_0x02db:
        goto L_0x0084;
    L_0x02de:
        r8.category = r2;
        goto L_0x02db;
    L_0x02e1:
        r6 = "C";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x02f7;
    L_0x02eb:
        r7 = 7;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x02f4;
    L_0x02f1:
        goto L_0x0084;
    L_0x02f4:
        r8.category = r2;
        goto L_0x02f1;
    L_0x02f7:
        r6 = "H";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x030e;
    L_0x0301:
        r7 = 8;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x030b;
    L_0x0308:
        goto L_0x0084;
    L_0x030b:
        r8.category = r2;
        goto L_0x0308;
    L_0x030e:
        r6 = "P";
        r0 = r23;
        r3 = r0.equals(r6);
        if (r3 == 0) goto L_0x0084;
    L_0x0318:
        r7 = 9;
        r2 = java.lang.Integer.valueOf(r7);
        goto L_0x0322;
    L_0x031f:
        goto L_0x0084;
    L_0x0322:
        r8.category = r2;
        goto L_0x031f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AddressItem.init(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.waze.reports.VenueData, java.lang.String):void");
    }

    public AddressItem(int $i0, int $i1, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7) throws  {
        this.categoryDesc = null;
        this.starttime = null;
        this.country = "";
        this.state = "";
        this.city = "";
        this.street = "";
        this.house = "";
        this.venueData = null;
        this.price_format = null;
        this.distanceMeters = 0;
        this.brand = "";
        this.index = -1;
        this.VanueID = null;
        this.mImageURL = null;
        this.mPreviewIcon = "";
        this.mIsNavigable = true;
        this.routingContext = "";
        this.isAddNewFavorite = false;
        this.mSpecificIcon = false;
        this.locationX = Integer.valueOf($i0);
        this.locationY = Integer.valueOf($i1);
        this.title = $r1;
        this.country = $r2;
        this.city = $r3;
        this.street = $r4;
        this.house = $r5;
        this.venueData = new VenueData();
        this.venueData.id = $r6;
        this.routingContext = $r7;
    }

    public AddressItem(Integer $r1, Integer $r2, String $r3, String $r4, String $r5, String $r6, Boolean $r7, Integer $r8, Integer $r9, String $r10, Integer $r11, String $r12, String $r13, String $r14, String $r15, String $r16, String $r17, String $r18, String $r19, String $r20, VenueData $r21, String $r22) throws  {
        this.categoryDesc = null;
        this.starttime = null;
        this.country = "";
        this.state = "";
        this.city = "";
        this.street = "";
        this.house = "";
        this.venueData = null;
        this.price_format = null;
        this.distanceMeters = 0;
        this.brand = "";
        this.index = -1;
        this.VanueID = null;
        this.mImageURL = null;
        this.mPreviewIcon = "";
        this.mIsNavigable = true;
        this.routingContext = "";
        this.isAddNewFavorite = false;
        this.mSpecificIcon = false;
        if ($r1 != null) {
            this.locationX = $r1;
        } else {
            this.locationX = Integer.valueOf(-1);
        }
        if ($r2 != null) {
            this.locationY = $r2;
        } else {
            this.locationY = Integer.valueOf(-1);
        }
        this.address = $r5;
        this.title = $r3;
        this.secondaryTitle = $r4;
        this.distance = $r6;
        this.more_action = $r7;
        this.image = $r8;
        this.category = $r9;
        this.id = $r10;
        this.type = $r11;
        this.url = $r12;
        this.special_url = $r13;
        this.icon = $r14;
        this.VanueID = $r15;
        this.country = $r16;
        this.state = $r17;
        this.city = $r18;
        this.street = $r19;
        this.house = $r20;
        this.venueData = $r21;
        this.routingContext = $r22;
    }

    public AddressItem(String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7, String $r8, String $r9, String $r10, String $r11, String $r12, String $r13, String $r14, String $r15, String $r16, String $r17, String $r18, String $r19, String $r20, String $r21, String $r22, String $r23, VenueData $r24, boolean $z0, boolean $z1, String $r25) throws  {
        this($r1, $r2, $r3, $r4, $r5, $r6, $r7, $r8, $r9, $r10, $r11, $r12, $r13, $r14, $r15, $r16, $r17, $r18, $r19, $r20, $r21, $r22, $r23, $r24, $z0, $r25);
        setIsValidate(Boolean.valueOf($z1));
    }

    public AddressItem(String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7, String $r8, String $r9, String $r10, String $r11, String $r12, String $r13, String $r14, String $r15, String $r16, String $r17, String $r18, String $r19, String $r20, String $r21, String $r22, String $r23, VenueData $r24, boolean $z0, String $r25) throws  {
        AddressItem addressItem = this;
        this.categoryDesc = null;
        this.starttime = null;
        this.country = "";
        this.state = "";
        this.city = "";
        this.street = "";
        this.house = "";
        this.venueData = null;
        this.price_format = null;
        this.distanceMeters = 0;
        this.brand = "";
        this.index = -1;
        this.VanueID = null;
        this.mImageURL = null;
        this.mPreviewIcon = "";
        this.mIsNavigable = true;
        this.routingContext = "";
        this.isAddNewFavorite = false;
        this.mSpecificIcon = false;
        init(-1, $r1, $r2, $r3, $r4, $r5, $r6, $r7, $r8, $r9, $r10, $r11, $r12, $r13, $r14, $r15, $r16, $r17, $r18, $r19, $r20, $r23, $r24, $r25);
        if ($r21 != null) {
            this.starttime = $r21;
            this.MeetingId = $r22;
            this.isRecurring = $z0;
        }
        if ($r16 != null && Integer.parseInt($r16) == 8 && $r22 != null) {
            this.MeetingId = $r22;
        }
    }

    public AddressItem(int $i0, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7, String $r8, String $r9, String $r10, String $r11, String $r12, String $r13, String $r14, String $r15, String $r16, String $r17, String $r18, String $r19, String $r20, String $r21, VenueData $r22, String $r23) throws  {
        this.categoryDesc = null;
        this.starttime = null;
        this.country = "";
        this.state = "";
        this.city = "";
        this.street = "";
        this.house = "";
        this.venueData = null;
        this.price_format = null;
        this.distanceMeters = 0;
        this.brand = "";
        this.index = -1;
        this.VanueID = null;
        this.mImageURL = null;
        this.mPreviewIcon = "";
        this.mIsNavigable = true;
        this.routingContext = "";
        this.isAddNewFavorite = false;
        this.mSpecificIcon = false;
        init($i0, $r1, $r2, $r3, $r4, $r5, $r6, $r7, $r8, $r9, $r10, $r11, $r12, $r13, $r14, $r15, $r16, $r17, $r18, $r19, $r20, $r21, $r22, $r23);
    }

    public AddressItem(PlaceData $r1) throws  {
        this.categoryDesc = null;
        this.starttime = null;
        this.country = "";
        this.state = "";
        this.city = "";
        this.street = "";
        this.house = "";
        this.venueData = null;
        this.price_format = null;
        this.distanceMeters = 0;
        this.brand = "";
        this.index = -1;
        this.VanueID = null;
        this.mImageURL = null;
        this.mPreviewIcon = "";
        this.mIsNavigable = true;
        this.routingContext = "";
        this.isAddNewFavorite = false;
        this.mSpecificIcon = false;
        this.locationX = Integer.valueOf($r1.mLocX);
        this.locationY = Integer.valueOf($r1.mLocY);
        this.address = null;
        this.title = $r1.mTitle;
        this.secondaryTitle = $r1.mSecondaryTitle;
        this.distance = null;
        this.more_action = Boolean.valueOf(false);
        this.image = null;
        this.category = Integer.valueOf(3);
        this.id = null;
        this.type = Integer.valueOf(7);
        this.url = null;
        this.special_url = null;
        this.icon = null;
        this.VanueID = $r1.mVenueId;
        this.country = null;
        this.state = null;
        this.city = null;
        this.street = null;
        this.house = null;
        this.venueData = null;
        this.routingContext = null;
    }

    public Integer getLocationX() throws  {
        return this.locationX;
    }

    public void setLocationX(Integer $r1) throws  {
        if ($r1 != null) {
            this.locationX = $r1;
        } else {
            this.locationX = Integer.valueOf(-1);
        }
    }

    public Integer getLocationY() throws  {
        return this.locationY;
    }

    public void setLocationY(Integer $r1) throws  {
        if ($r1 != null) {
            this.locationY = $r1;
        } else {
            this.locationY = Integer.valueOf(-1);
        }
    }

    public String getAddress() throws  {
        return this.address == null ? "" : this.address;
    }

    public void setAddress(String $r1) throws  {
        this.address = $r1;
    }

    public String getTitle() throws  {
        return this.title == null ? "" : this.title;
    }

    public String getDealId() throws  {
        return this.dealId == null ? "" : this.dealId;
    }

    public void setTitle(String $r1) throws  {
        this.title = $r1;
    }

    public String getSecondaryTitle() throws  {
        return this.secondaryTitle == null ? "" : this.secondaryTitle;
    }

    public void setSecondaryTitle(String $r1) throws  {
        this.secondaryTitle = $r1;
    }

    public String getDistance() throws  {
        return this.distance == null ? "" : this.distance;
    }

    public void setDistance(String $r1) throws  {
        this.distance = $r1;
    }

    public Boolean getMoreAction() throws  {
        return this.more_action;
    }

    public void setMoreAction(Boolean $r1) throws  {
        this.more_action = $r1;
    }

    public Integer getImage() throws  {
        return this.image;
    }

    public void setImage(Integer $r1) throws  {
        this.image = $r1;
    }

    public boolean hasLocation() throws  {
        return ((this.locationY.intValue() == 0 && this.locationX.intValue() == 0) || (this.locationY.intValue() == -1 && this.locationY.intValue() == -1)) ? false : true;
    }

    public Integer getCategory() throws  {
        if (this.category != null) {
            return this.category;
        }
        return Integer.valueOf(99);
    }

    public String getCategoryDesc() throws  {
        return this.categoryDesc;
    }

    public void setCategory(Integer $r1) throws  {
        this.category = $r1;
    }

    public String getId() throws  {
        return this.id;
    }

    public void setId(String $r1) throws  {
        this.id = $r1;
    }

    public int getType() throws  {
        return this.type == null ? 0 : this.type.intValue();
    }

    public void setType(Integer $r1) throws  {
        this.type = $r1;
    }

    public void setType(int $i0) throws  {
        this.type = Integer.valueOf($i0);
    }

    public String getUrl() throws  {
        return this.url;
    }

    public void setUrl(String $r1) throws  {
        this.url = $r1;
    }

    public String getSpecialUrl() throws  {
        return this.special_url;
    }

    public void setDistance(int $i0) throws  {
        this.distanceMeters = $i0;
    }

    public void setSpecialUrl(String $r1) throws  {
        this.special_url = $r1;
    }

    public String toString() throws  {
        return ((((((((((PublicMacros.ADDRESS_ITEM + " T:" + this.title) + " A:" + this.address) + " D:" + this.distance) + " X:" + this.locationX) + " Y:" + this.locationY) + " ma:" + this.more_action) + " C:" + this.category) + " T:" + this.type) + " id:" + this.id) + " I:" + this.image) + " R:" + this.routingContext;
    }

    public void setDeal(boolean $z0, int $i0, String $r1, String $r2, float $f0, int $i1, String $r3, long $l2, String $r4, int $i3, String $r5) throws  {
        this.sponsored = $z0;
        this.dealType = $i0;
        this.dealTitle = $r1;
        this.dealText = $r2;
        this.price = $f0;
        this.range = $i1;
        this.currency = $r3;
        this.lastUpdated = $l2;
        this.dealId = $r4;
        this.advPointId = $i3;
        this.price_format = $r5;
    }

    public String[] getAdditions() throws  {
        if (this.additions != null) {
            return this.additions;
        }
        return NO_ADDITIONS;
    }

    public void setAdditions(String[] $r1) throws  {
        this.additions = $r1;
        for (String $r2 : $r1) {
        }
    }

    public void setPhone(String $r1) throws  {
        this.phone = $r1;
    }

    public String getPhone() throws  {
        return this.phone == null ? "" : this.phone;
    }

    public void setResultId(String $r1) throws  {
        this.resultId = $r1;
    }

    public String getResultId() throws  {
        if (this.resultId != null) {
            return this.resultId;
        }
        return "";
    }

    public String getIcon() throws  {
        return (this.icon == null || this.icon.equals("")) ? "category_menu_default" : this.icon;
    }

    public boolean hasIcon() throws  {
        return (this.icon == null || this.icon.isEmpty() || this.icon.equals("category_menu_default")) ? false : true;
    }

    public int getIntId() throws  {
        if (this.id == null) {
            return 0;
        }
        int $i0 = Integer.parseInt(this.id.substring(0, this.id.indexOf("|")));
        Logger.m41i("AddressItem - Returning ID: " + $i0);
        return $i0;
    }

    public void setIcon(String $r1) throws  {
        this.icon = $r1;
    }

    public void setBrand(String $r1) throws  {
        this.brand = $r1;
    }

    public String getAccreditation() throws  {
        return this.accreditation;
    }

    public String getStartTime() throws  {
        return this.starttime;
    }

    public void setStartTime(String $r1) throws  {
        this.starttime = $r1;
    }

    public long getStartTimeMillis() throws  {
        try {
            return Long.parseLong(getStartTime()) * 1000;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Boolean getIsValidate() throws  {
        if (this.is_validate == null) {
            return Boolean.valueOf(false);
        }
        return this.is_validate;
    }

    public void setIsValidate(Boolean $r1) throws  {
        this.is_validate = $r1;
    }

    public String getMeetingId() throws  {
        return this.MeetingId;
    }

    public void setMeetingId(String $r1) throws  {
        this.MeetingId = $r1;
    }

    public boolean getisRecurring() throws  {
        return this.isRecurring;
    }

    public String getCountry() throws  {
        return this.country;
    }

    public void setCountry(String $r1) throws  {
        this.country = $r1;
    }

    public String getState() throws  {
        return this.state;
    }

    public void setState(String $r1) throws  {
        this.state = $r1;
    }

    public String getCity() throws  {
        return this.city;
    }

    public void setCity(String $r1) throws  {
        this.city = $r1;
    }

    public String getStreet() throws  {
        return this.street;
    }

    public void setStreet(String $r1) throws  {
        this.street = $r1;
    }

    public String getHouse() throws  {
        return this.house;
    }

    public void setHouse(String $r1) throws  {
        this.house = $r1;
    }

    public void setAdvertiserData(String $r1, String $r2, int $i0, String $r3) throws  {
        this.mAdvertiserData = new AdvertiserData();
        this.mAdvertiserData.url = $r1;
        this.mAdvertiserData.query = $r2;
        this.mAdvertiserData.session = $i0;
        this.mAdvertiserData.cookie = $r3;
    }

    public AdvertiserData getAdvertiserData() throws  {
        return this.mAdvertiserData;
    }

    public VenueData getVenueDataForParking() throws  {
        VenueData $r1 = this.venueData;
        if ($r1 != null) {
            return $r1;
        }
        $r1 = new VenueData();
        $r1.name = getTitle();
        $r1.longitude = getLocationX().intValue();
        $r1.latitude = getLocationY().intValue();
        $r1.id = this.VanueID;
        return $r1;
    }

    @NonNull
    public static AddressItem getCurLocAddressItem(Context $r0) throws  {
        int $i2;
        Location $r2 = Alerter.getBestLocation($r0);
        int $i0 = ConfigValues.getIntValue(63);
        if ($r2 == null || $r2.getAccuracy() > ((float) $i0)) {
            $i0 = 0;
            $i2 = 0;
        } else {
            double $d0 = $r2.getLongitude() * 1000000.0d;
            double d = $d0;
            $i0 = (int) $d0;
            int $i22 = $r2.getLatitude() * 1000000.0d;
            long j = $i22;
            $i2 = (int) $i22;
        }
        AddressItem addressItem = new AddressItem($i0, $i2, DisplayStrings.displayString(DisplayStrings.DS_AUTOCOMPLETE_CURRENT_LOCATION), "", "", "", "", null, null);
        addressItem.setType(16);
        return addressItem;
    }

    public boolean isHome() throws  {
        if (this.type.intValue() == 1) {
            return true;
        }
        if ("home".equalsIgnoreCase(this.title)) {
            return true;
        }
        return DisplayStrings.displayString(287).equalsIgnoreCase(this.title);
    }

    public boolean isWork() throws  {
        if (this.type.intValue() == 3) {
            return true;
        }
        if ("work".equalsIgnoreCase(this.title)) {
            return true;
        }
        return DisplayStrings.displayString(288).equalsIgnoreCase(this.title);
    }
}
