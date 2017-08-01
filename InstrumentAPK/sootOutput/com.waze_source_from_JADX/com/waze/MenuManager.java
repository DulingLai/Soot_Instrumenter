package com.waze;

import android.R.drawable;
import android.util.Log;
import java.lang.reflect.Field;

public final class MenuManager {
    public static final int NATIVE_MSG_CATEGORY_MENU = 524288;
    private static final int WAZE_OPT_MENU_ITEMS_MAXNUM = 20;
    private static final int WAZE_OPT_MENU_MAX_FRONT_ITEMS = 6;
    private static final int WAZE_OPT_MENU_MORE_TYPE = 1;
    private boolean mIsInitialized = false;
    private int mMenuItemCount;
    private WazeMenuItem[] mMenuItems;
    private int mMoreItemId = -1;
    private NativeManager mNativeManager;

    private static class WazeMenuItem {
        int icon_id;
        public String icon_name;
        public boolean is_more_button;
        public int is_native;
        public int item_id;
        public String item_label;
        public int landscape_order;
        public int portrait_order;

        private WazeMenuItem() throws  {
        }
    }

    private native void InitMenuManagerNTV() throws ;

    public synchronized boolean BuildOptionsMenu(android.view.Menu r18, boolean r19) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00cf in list [B:39:0x00d1]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r17 = this;
        r1 = 0;
        monitor-enter(r17);
        r2 = 0;
        r0 = r18;	 Catch:{ Exception -> 0x00af }
        r0.clear();	 Catch:{ Exception -> 0x00af }
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r3 = r0.mIsInitialized;	 Catch:{ Exception -> 0x00af }
        if (r3 == 0) goto L_0x0014;	 Catch:{ Exception -> 0x00af }
    L_0x000e:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r4 = r0.mMenuItemCount;	 Catch:{ Exception -> 0x00af }
        if (r4 != 0) goto L_0x0016;
    L_0x0014:
        monitor-exit(r17);
        return r1;
    L_0x0016:
        r4 = 0;
    L_0x0017:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r5 = r0.mMenuItemCount;	 Catch:{ Exception -> 0x00af }
        if (r4 >= r5) goto L_0x00cf;	 Catch:{ Exception -> 0x00af }
    L_0x001d:
        if (r19 == 0) goto L_0x0075;	 Catch:{ Exception -> 0x00af }
    L_0x001f:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r5 = r7.portrait_order;	 Catch:{ Exception -> 0x00af }
    L_0x0027:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r1 = r7.is_more_button;	 Catch:{ Exception -> 0x00af }
        if (r1 == 0) goto L_0x007e;	 Catch:{ Exception -> 0x00af }
    L_0x0031:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r8 = r7.item_id;	 Catch:{ Exception -> 0x00af }
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r0.mMoreItemId = r8;	 Catch:{ Exception -> 0x00af }
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r8 = r7.item_id;	 Catch:{ Exception -> 0x00af }
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r9 = r7.item_label;	 Catch:{ Exception -> 0x00af }
        r11 = 0;	 Catch:{ Exception -> 0x00af }
        r0 = r18;	 Catch:{ Exception -> 0x00af }
        r10 = r0.addSubMenu(r11, r8, r5, r9);	 Catch:{ Exception -> 0x00af }
        r2 = r10;	 Catch:{ Exception -> 0x00af }
        r12 = r10.getItem();	 Catch:{ Exception -> 0x00af }
    L_0x0059:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r9 = r7.icon_name;	 Catch:{ Exception -> 0x00af }
        if (r9 == 0) goto L_0x006e;	 Catch:{ Exception -> 0x00af }
    L_0x0063:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r5 = r7.icon_id;	 Catch:{ Exception -> 0x00af }
        r12.setIcon(r5);	 Catch:{ Exception -> 0x00af }
    L_0x006e:
        r4 = r4 + 1;	 Catch:{ Exception -> 0x00af }
        goto L_0x0074;	 Catch:{ Exception -> 0x00af }
    L_0x0071:
        goto L_0x0014;	 Catch:{ Exception -> 0x00af }
    L_0x0074:
        goto L_0x0017;	 Catch:{ Exception -> 0x00af }
    L_0x0075:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r5 = r7.landscape_order;	 Catch:{ Exception -> 0x00af }
        goto L_0x0027;	 Catch:{ Exception -> 0x00af }
    L_0x007e:
        r11 = 6;	 Catch:{ Exception -> 0x00af }
        if (r4 < r11) goto L_0x0097;	 Catch:{ Exception -> 0x00af }
    L_0x0081:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r8 = r7.item_id;	 Catch:{ Exception -> 0x00af }
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r9 = r7.item_label;	 Catch:{ Exception -> 0x00af }
        r11 = 0;	 Catch:{ Exception -> 0x00af }
        r12 = r2.add(r11, r8, r5, r9);	 Catch:{ Exception -> 0x00af }
        goto L_0x0059;	 Catch:{ Exception -> 0x00af }
    L_0x0097:
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r8 = r7.item_id;	 Catch:{ Exception -> 0x00af }
        r0 = r17;	 Catch:{ Exception -> 0x00af }
        r6 = r0.mMenuItems;	 Catch:{ Exception -> 0x00af }
        r7 = r6[r4];	 Catch:{ Exception -> 0x00af }
        r9 = r7.item_label;	 Catch:{ Exception -> 0x00af }
        r11 = 0;	 Catch:{ Exception -> 0x00af }
        r0 = r18;	 Catch:{ Exception -> 0x00af }
        r12 = r0.add(r11, r8, r5, r9);	 Catch:{ Exception -> 0x00af }
        goto L_0x0059;
    L_0x00af:
        r13 = move-exception;
        r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00af }
        r14.<init>();	 Catch:{ Exception -> 0x00af }
        r15 = "Error while building the menu";	 Catch:{ Exception -> 0x00af }
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00af }
        r9 = r13.getMessage();	 Catch:{ Exception -> 0x00af }
        r14 = r14.append(r9);	 Catch:{ Exception -> 0x00af }
        r9 = r14.toString();	 Catch:{ Exception -> 0x00af }
        r15 = "WAZE";	 Catch:{ Exception -> 0x00af }
        android.util.Log.w(r15, r9);	 Catch:{ Exception -> 0x00af }
        r13.printStackTrace();	 Catch:{ Exception -> 0x00af }
    L_0x00cf:
        r1 = 1;
        goto L_0x0071;
    L_0x00d1:
        r16 = move-exception;
        monitor-exit(r17);
        throw r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.MenuManager.BuildOptionsMenu(android.view.Menu, boolean):boolean");
    }

    public MenuManager(NativeManager $r1) throws  {
        this.mNativeManager = $r1;
        this.mMenuItems = new WazeMenuItem[20];
        this.mMenuItemCount = 0;
        InitMenuManagerNTV();
    }

    public synchronized boolean getInitialized() throws  {
        return this.mIsInitialized;
    }

    public synchronized boolean OnMenuButtonPressed(int $i0) throws  {
        boolean $z0 = false;
        synchronized (this) {
            if ($i0 != this.mMoreItemId) {
                AppService.getNativeManager().PostNativeMessage(524288 | $i0, 0);
                $z0 = true;
            }
        }
        return $z0;
    }

    public synchronized void ResetOptionsMenu() throws  {
        this.mIsInitialized = false;
        this.mMenuItemCount = 0;
    }

    public synchronized void SubmitOptionsMenu() throws  {
        this.mIsInitialized = true;
    }

    public synchronized void AddOptionsMenuItem(int $i0, byte[] $r1, byte[] $r2, int $i1, int $i2, int $i3, int $i4) throws  {
        WazeMenuItem $r4 = new WazeMenuItem();
        $r4.item_id = $i0;
        $r4.item_label = new String($r1);
        $r4.portrait_order = $i2;
        $r4.landscape_order = $i3;
        $r4.is_native = $i1;
        $r4.is_more_button = ($i4 & 1) > 0;
        if ($r2 != null) {
            try {
                Field $r7;
                $r4.icon_name = new String($r2);
                if ($r4.is_native == 1) {
                    $r7 = drawable.class.getField($r4.icon_name);
                } else {
                    $r7 = C1283R.drawable.class.getField($r4.icon_name);
                }
                $r4.icon_id = $r7.getInt(null);
            } catch (Exception $r3) {
                Log.w(Logger.TAG, "Error while building the menu" + $r3.getMessage());
                $r3.printStackTrace();
            }
        } else {
            $r4.icon_name = null;
        }
        this.mMenuItems[this.mMenuItemCount] = $r4;
        this.mMenuItemCount++;
    }
}
