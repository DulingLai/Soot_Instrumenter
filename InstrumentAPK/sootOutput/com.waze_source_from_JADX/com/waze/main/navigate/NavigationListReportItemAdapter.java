package com.waze.main.navigate;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.rtalerts.RTAlertsAlertData;

public class NavigationListReportItemAdapter extends BaseAdapter {
    boolean alertEnabled;
    private boolean driveOnLeft;
    private LayoutInflater inflater;
    private RTAlertsAlertData[] items;
    private NativeManager nativeManager = AppService.getNativeManager();
    boolean policeEnabled;

    public Object getItem(int arg0) throws  {
        return null;
    }

    public long getItemId(int position) throws  {
        return 0;
    }

    public android.view.View getView(int r24, android.view.View r25, android.view.ViewGroup r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:16:0x00ab
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
        r23 = this;
        r0 = r23;
        r2 = r0.items;
        if (r2 != 0) goto L_0x0008;
    L_0x0006:
        r3 = 0;
        return r3;
    L_0x0008:
        r0 = r26;
        r4 = r0.getResources();
        r0 = r23;
        r2 = r0.items;
        r5 = r2[r24];
        r6 = r25;
        if (r25 != 0) goto L_0x0026;
    L_0x0018:
        r0 = r23;
        r7 = r0.inflater;
        r8 = 2130903280; // 0x7f0300f0 float:1.7413374E38 double:1.0528061053E-314;
        r9 = 0;
        r0 = r26;
        r6 = r7.inflate(r8, r0, r9);
    L_0x0026:
        r8 = 2131691252; // 0x7f0f06f4 float:1.901157E38 double:1.053195415E-314;
        r25 = r6.findViewById(r8);
        r11 = r25;
        r11 = (com.waze.view.button.ReportMenuButton) r11;
        r10 = r11;
        r10.skipAnimation();
        r12 = r5.mType;
        switch(r12) {
            case 0: goto L_0x010c;
            case 1: goto L_0x0119;
            case 2: goto L_0x0132;
            case 3: goto L_0x013f;
            case 4: goto L_0x014c;
            case 5: goto L_0x0159;
            case 6: goto L_0x0169;
            case 7: goto L_0x0179;
            case 8: goto L_0x0189;
            case 9: goto L_0x0199;
            case 10: goto L_0x01a9;
            case 11: goto L_0x01c6;
            case 12: goto L_0x01d6;
            default: goto L_0x003a;
        };
    L_0x003a:
        goto L_0x003b;
    L_0x003b:
        r8 = 2131691253; // 0x7f0f06f5 float:1.9011573E38 double:1.0531954156E-314;
        r25 = r6.findViewById(r8);
        r14 = r25;
        r14 = (android.widget.TextView) r14;
        r13 = r14;
        r0 = r23;
        r15 = r0.nativeManager;
        r8 = 234; // 0xea float:3.28E-43 double:1.156E-321;
        r16 = r15.getLanguageString(r8);
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r0 = r5.mDistanceStr;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r19 = " ";
        r0 = r17;
        r1 = r19;
        r17 = r0.append(r1);
        r0 = r5.mUnit;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);
        r19 = " ";
        r0 = r17;
        r1 = r19;
        r17 = r0.append(r1);
        r0 = r17;
        r1 = r16;
        r17 = r0.append(r1);
        goto L_0x0091;
    L_0x008e:
        goto L_0x003b;
    L_0x0091:
        r0 = r17;
        r16 = r0.toString();
        r0 = r16;
        r13.setText(r0);
        goto L_0x00a0;
    L_0x009d:
        goto L_0x003b;
    L_0x00a0:
        r8 = 2131691254; // 0x7f0f06f6 float:1.9011575E38 double:1.053195416E-314;
        r25 = r6.findViewById(r8);
        goto L_0x00af;
    L_0x00a8:
        goto L_0x003b;
        goto L_0x00af;
    L_0x00ac:
        goto L_0x003b;
    L_0x00af:
        r20 = r25;
        r20 = (android.widget.TextView) r20;
        r13 = r20;
        r0 = r23;
        r15 = r0.nativeManager;
        goto L_0x00bd;
    L_0x00ba:
        goto L_0x003b;
    L_0x00bd:
        r0 = r5.mTitle;
        r16 = r0;
        r16 = r15.getLanguageString(r0);
        r0 = r16;
        r13.setText(r0);
        goto L_0x00ce;
    L_0x00cb:
        goto L_0x008e;
    L_0x00ce:
        r8 = 2131691255; // 0x7f0f06f7 float:1.9011577E38 double:1.0531954166E-314;
        r25 = r6.findViewById(r8);
        r21 = r25;
        r21 = (android.widget.TextView) r21;
        r13 = r21;
        r0 = r5.mLocationStr;
        r16 = r0;
        r13.setText(r0);
        goto L_0x00e6;
    L_0x00e3:
        goto L_0x009d;
    L_0x00e6:
        r8 = 2131691244; // 0x7f0f06ec float:1.9011554E38 double:1.053195411E-314;
        r25 = r6.findViewById(r8);
        r24 = r24 % 2;
        if (r24 != 0) goto L_0x01e6;
    L_0x00f1:
        goto L_0x00f5;
    L_0x00f2:
        goto L_0x00a8;
    L_0x00f5:
        r8 = 2131623950; // 0x7f0e000e float:1.8875066E38 double:1.0531621635E-314;
        r24 = r4.getColor(r8);
        goto L_0x0104;
    L_0x00fd:
        goto L_0x00ac;
        goto L_0x0104;
    L_0x0101:
        goto L_0x00ba;
    L_0x0104:
        r0 = r25;
        r1 = r24;
        r0.setBackgroundColor(r1);
        return r6;
    L_0x010c:
        r8 = 2130838286; // 0x7f02030e float:1.728155E38 double:1.052773994E-314;
        r10.setImageResource(r8);
        r8 = -4598636; // 0xffffffffffb9d494 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x00cb;
    L_0x0119:
        r0 = r23;
        r0 = r0.policeEnabled;
        r22 = r0;
        if (r22 == 0) goto L_0x012e;
    L_0x0121:
        r12 = 2130838308; // 0x7f020324 float:1.7281595E38 double:1.052774005E-314;
    L_0x0124:
        r10.setImageResource(r12);
        r8 = -7943985; // 0xffffffffff86c8cf float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x00e3;
    L_0x012e:
        r12 = 2130838913; // 0x7f020581 float:1.7282822E38 double:1.0527743037E-314;
        goto L_0x0124;
    L_0x0132:
        r8 = 2130838281; // 0x7f020309 float:1.728154E38 double:1.0527739915E-314;
        r10.setImageResource(r8);
        r8 = -4078653; // 0xffffffffffc1c3c3 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x00f2;
    L_0x013f:
        r8 = 2130838316; // 0x7f02032c float:1.728161E38 double:1.052774009E-314;
        r10.setImageResource(r8);
        r8 = -1411464; // 0xffffffffffea7678 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x00fd;
    L_0x014c:
        r8 = 2130838316; // 0x7f02032c float:1.728161E38 double:1.052774009E-314;
        r10.setImageResource(r8);
        r8 = -1411464; // 0xffffffffffea7678 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x0101;
    L_0x0159:
        r8 = 2130838293; // 0x7f020315 float:1.7281564E38 double:1.0527739974E-314;
        r10.setImageResource(r8);
        goto L_0x0163;
    L_0x0160:
        goto L_0x003b;
    L_0x0163:
        r8 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x0160;
    L_0x0169:
        r8 = 2130838293; // 0x7f020315 float:1.7281564E38 double:1.0527739974E-314;
        r10.setImageResource(r8);
        goto L_0x0173;
    L_0x0170:
        goto L_0x003b;
    L_0x0173:
        r8 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x0170;
    L_0x0179:
        r8 = 2130838287; // 0x7f02030f float:1.7281552E38 double:1.0527739944E-314;
        r10.setImageResource(r8);
        goto L_0x0183;
    L_0x0180:
        goto L_0x003b;
    L_0x0183:
        r8 = -28305; // 0xffffffffffff916f float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x0180;
    L_0x0189:
        r8 = 2130838293; // 0x7f020315 float:1.7281564E38 double:1.0527739974E-314;
        r10.setImageResource(r8);
        goto L_0x0193;
    L_0x0190:
        goto L_0x003b;
    L_0x0193:
        r8 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x0190;
    L_0x0199:
        r8 = 2130838293; // 0x7f020315 float:1.7281564E38 double:1.0527739974E-314;
        r10.setImageResource(r8);
        goto L_0x01a3;
    L_0x01a0:
        goto L_0x003b;
    L_0x01a3:
        r8 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x01a0;
    L_0x01a9:
        r0 = r23;
        r0 = r0.alertEnabled;
        r22 = r0;
        if (r22 == 0) goto L_0x01c2;
    L_0x01b1:
        r12 = 2130838313; // 0x7f020329 float:1.7281605E38 double:1.0527740073E-314;
    L_0x01b4:
        r10.setImageResource(r12);
        goto L_0x01bb;
    L_0x01b8:
        goto L_0x003b;
    L_0x01bb:
        r8 = -7614027; // 0xffffffffff8bd1b5 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x01b8;
    L_0x01c2:
        r12 = 2130837802; // 0x7f02012a float:1.7280568E38 double:1.052773755E-314;
        goto L_0x01b4;
    L_0x01c6:
        r8 = 2130838293; // 0x7f020315 float:1.7281564E38 double:1.0527739974E-314;
        r10.setImageResource(r8);
        goto L_0x01d0;
    L_0x01cd:
        goto L_0x003b;
    L_0x01d0:
        r8 = -15775; // 0xffffffffffffc261 float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x01cd;
    L_0x01d6:
        r8 = 2130838287; // 0x7f02030f float:1.7281552E38 double:1.0527739944E-314;
        r10.setImageResource(r8);
        goto L_0x01e0;
    L_0x01dd:
        goto L_0x003b;
    L_0x01e0:
        r8 = -28305; // 0xffffffffffff916f float:NaN double:NaN;
        r10.setBackgroundColor(r8);
        goto L_0x01dd;
    L_0x01e6:
        r8 = 2131623949; // 0x7f0e000d float:1.8875064E38 double:1.053162163E-314;
        r24 = r4.getColor(r8);
        r0 = r25;
        r1 = r24;
        r0.setBackgroundColor(r1);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.main.navigate.NavigationListReportItemAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public NavigationListReportItemAdapter(Context $r1, boolean $z0) throws  {
        this.inflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
        this.driveOnLeft = $z0;
        if (this.nativeManager.isEnforcementPoliceEnabledNTV() == 0) {
        }
        this.nativeManager.isEnforcementAlertsEnabledNTV();
    }

    public int getCount() throws  {
        return this.items == null ? 0 : this.items.length;
    }

    public void setItems(RTAlertsAlertData[] $r1) throws  {
        this.items = $r1;
    }
}
