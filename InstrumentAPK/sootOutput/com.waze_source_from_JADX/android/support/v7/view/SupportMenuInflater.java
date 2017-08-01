package android.support.v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParserException;

public class SupportMenuInflater extends MenuInflater {
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class};
    private static final String LOG_TAG = "SupportMenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;

    private static class InflatedOnMenuItemClickListener implements OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public boolean onMenuItemClick(android.view.MenuItem r12) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0021 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r11 = this;
            r0 = r11.mMethod;	 Catch:{ Exception -> 0x0030 }
            r1 = r0.getReturnType();	 Catch:{ Exception -> 0x0030 }
            r2 = java.lang.Boolean.TYPE;
            if (r1 != r2) goto L_0x0021;	 Catch:{ Exception -> 0x0030 }
        L_0x000a:
            r0 = r11.mMethod;	 Catch:{ Exception -> 0x0030 }
            r3 = r11.mRealOwner;	 Catch:{ Exception -> 0x0030 }
            r5 = 1;	 Catch:{ Exception -> 0x0030 }
            r4 = new java.lang.Object[r5];	 Catch:{ Exception -> 0x0030 }
            r5 = 0;	 Catch:{ Exception -> 0x0030 }
            r4[r5] = r12;	 Catch:{ Exception -> 0x0030 }
            r3 = r0.invoke(r3, r4);	 Catch:{ Exception -> 0x0030 }
            r7 = r3;	 Catch:{ Exception -> 0x0030 }
            r7 = (java.lang.Boolean) r7;	 Catch:{ Exception -> 0x0030 }
            r6 = r7;	 Catch:{ Exception -> 0x0030 }
            r8 = r6.booleanValue();	 Catch:{ Exception -> 0x0030 }
            return r8;
        L_0x0021:
            r0 = r11.mMethod;	 Catch:{ Exception -> 0x0030 }
            r3 = r11.mRealOwner;	 Catch:{ Exception -> 0x0030 }
            r5 = 1;	 Catch:{ Exception -> 0x0030 }
            r4 = new java.lang.Object[r5];	 Catch:{ Exception -> 0x0030 }
            r5 = 0;	 Catch:{ Exception -> 0x0030 }
            r4[r5] = r12;	 Catch:{ Exception -> 0x0030 }
            r0.invoke(r3, r4);	 Catch:{ Exception -> 0x0030 }
            r5 = 1;
            return r5;
        L_0x0030:
            r9 = move-exception;
            r10 = new java.lang.RuntimeException;
            r10.<init>(r9);
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.view.SupportMenuInflater.InflatedOnMenuItemClickListener.onMenuItemClick(android.view.MenuItem):boolean");
        }

        public InflatedOnMenuItemClickListener(Object $r1, String $r2) throws  {
            this.mRealOwner = $r1;
            Class $r5 = $r1.getClass();
            try {
                this.mMethod = $r5.getMethod($r2, PARAM_TYPES);
            } catch (Exception $r3) {
                InflateException $r4 = new InflateException("Couldn't resolve menu item onClick handler " + $r2 + " in class " + $r5.getName());
                $r4.initCause($r3);
                throw $r4;
            }
        }
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private String itemListenerMethodName;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private boolean itemVisible;
        private Menu menu;

        public MenuState(Menu $r2) throws  {
            this.menu = $r2;
            resetGroup();
        }

        public void resetGroup() throws  {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }

        public void readGroup(AttributeSet $r1) throws  {
            TypedArray $r5 = SupportMenuInflater.this.mContext.obtainStyledAttributes($r1, C0192R.styleable.MenuGroup);
            this.groupId = $r5.getResourceId(C0192R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = $r5.getInt(C0192R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = $r5.getInt(C0192R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = $r5.getInt(C0192R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = $r5.getBoolean(C0192R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = $r5.getBoolean(C0192R.styleable.MenuGroup_android_enabled, true);
            $r5.recycle();
        }

        public void readItem(AttributeSet $r1) throws  {
            TypedArray $r5 = SupportMenuInflater.this.mContext.obtainStyledAttributes($r1, C0192R.styleable.MenuItem);
            this.itemId = $r5.getResourceId(C0192R.styleable.MenuItem_android_id, 0);
            this.itemCategoryOrder = (SupportMenu.CATEGORY_MASK & $r5.getInt(C0192R.styleable.MenuItem_android_menuCategory, this.groupCategory)) | (SupportMenu.USER_MASK & $r5.getInt(C0192R.styleable.MenuItem_android_orderInCategory, this.groupOrder));
            this.itemTitle = $r5.getText(C0192R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = $r5.getText(C0192R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = $r5.getResourceId(C0192R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = getShortcut($r5.getString(C0192R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemNumericShortcut = getShortcut($r5.getString(C0192R.styleable.MenuItem_android_numericShortcut));
            if ($r5.hasValue(C0192R.styleable.MenuItem_android_checkable)) {
                this.itemCheckable = $r5.getBoolean(C0192R.styleable.MenuItem_android_checkable, false) ? (byte) 1 : (byte) 0;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = $r5.getBoolean(C0192R.styleable.MenuItem_android_checked, false);
            this.itemVisible = $r5.getBoolean(C0192R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = $r5.getBoolean(C0192R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = $r5.getInt(C0192R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = $r5.getString(C0192R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = $r5.getResourceId(C0192R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = $r5.getString(C0192R.styleable.MenuItem_actionViewClass);
            this.itemActionProviderClassName = $r5.getString(C0192R.styleable.MenuItem_actionProviderClass);
            boolean $z0 = this.itemActionProviderClassName != null;
            if ($z0 && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if ($z0) {
                    Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            $r5.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(String $r1) throws  {
            return $r1 == null ? '\u0000' : $r1.charAt(0);
        }

        private void setItem(MenuItem $r1) throws  {
            $r1.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
            if (this.itemShowAsAction >= 0) {
                MenuItemCompat.setShowAsAction($r1, this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                $r1.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if ($r1 instanceof MenuItemImpl) {
                MenuItemImpl $r10 = (MenuItemImpl) $r1;
            }
            if (this.itemCheckable >= 2) {
                if ($r1 instanceof MenuItemImpl) {
                    ((MenuItemImpl) $r1).setExclusiveCheckable(true);
                } else if ($r1 instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS) $r1).setExclusiveCheckable(true);
                }
            }
            boolean $z0 = false;
            if (this.itemActionViewClassName != null) {
                MenuItemCompat.setActionView($r1, (View) newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
                $z0 = true;
            }
            if (this.itemActionViewLayout > 0) {
                if ($z0) {
                    Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                } else {
                    MenuItemCompat.setActionView($r1, this.itemActionViewLayout);
                }
            }
            if (this.itemActionProvider != null) {
                MenuItemCompat.setActionProvider($r1, this.itemActionProvider);
            }
        }

        public void addItem() throws  {
            this.itemAdded = true;
            setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() throws  {
            this.itemAdded = true;
            SubMenu $r3 = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem($r3.getItem());
            return $r3;
        }

        public boolean hasAddedItem() throws  {
            return this.itemAdded;
        }

        private <T> T newInstance(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;[", "Ljava/lang/Object;", ")TT;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;[", "Ljava/lang/Object;", ")TT;"}) Class<?>[] $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;[", "Ljava/lang/Object;", ")TT;"}) Object[] $r3) throws  {
            try {
                Constructor $r9 = SupportMenuInflater.this.mContext.getClassLoader().loadClass($r1).getConstructor($r2);
                $r9.setAccessible(true);
                return $r9.newInstance($r3);
            } catch (Exception $r4) {
                Log.w(SupportMenuInflater.LOG_TAG, "Cannot instantiate class: " + $r1, $r4);
                return null;
            }
        }
    }

    private void parseMenu(org.xmlpull.v1.XmlPullParser r18, android.util.AttributeSet r19, android.view.Menu r20) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:20:0x0070
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
        r17 = this;
        r3 = new android.support.v7.view.SupportMenuInflater$MenuState;
        r0 = r17;
        r1 = r20;
        r3.<init>(r1);
        r0 = r18;
        r4 = r0.getEventType();
        r5 = 0;
        r6 = 0;
    L_0x0011:
        r7 = 2;
        if (r4 != r7) goto L_0x004f;
    L_0x0014:
        r0 = r18;
        r8 = r0.getName();
        r10 = "menu";
        r9 = r8.equals(r10);
        if (r9 == 0) goto L_0x0036;
    L_0x0022:
        r0 = r18;
        r4 = r0.next();
    L_0x0028:
        r9 = 0;
    L_0x0029:
        if (r9 != 0) goto L_0x0102;
    L_0x002b:
        switch(r4) {
            case 1: goto L_0x00fa;
            case 2: goto L_0x005a;
            case 3: goto L_0x00ab;
            default: goto L_0x002e;
        };
    L_0x002e:
        goto L_0x002f;
    L_0x002f:
        r0 = r18;
        r4 = r0.next();
        goto L_0x0029;
    L_0x0036:
        r11 = new java.lang.RuntimeException;
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r10 = "Expecting menu, got ";
        r12 = r12.append(r10);
        r12 = r12.append(r8);
        r6 = r12.toString();
        r11.<init>(r6);
        throw r11;
    L_0x004f:
        r0 = r18;
        r13 = r0.next();
        r4 = r13;
        r7 = 1;
        if (r13 != r7) goto L_0x0011;
    L_0x0059:
        goto L_0x0028;
    L_0x005a:
        if (r5 != 0) goto L_0x002f;
    L_0x005c:
        r0 = r18;
        r8 = r0.getName();
        r10 = "group";
        r14 = r8.equals(r10);
        if (r14 == 0) goto L_0x0078;
    L_0x006a:
        r0 = r19;
        r3.readGroup(r0);
        goto L_0x002f;
        goto L_0x0078;
        goto L_0x0075;
    L_0x0072:
        goto L_0x002f;
    L_0x0075:
        goto L_0x002f;
    L_0x0078:
        r10 = "item";
        r14 = r8.equals(r10);
        if (r14 == 0) goto L_0x008e;
    L_0x0080:
        r0 = r19;
        r3.readItem(r0);
        goto L_0x002f;
        goto L_0x008e;
    L_0x0087:
        goto L_0x002f;
        goto L_0x008e;
    L_0x008b:
        goto L_0x002f;
    L_0x008e:
        r10 = "menu";
        r14 = r8.equals(r10);
        goto L_0x0098;
    L_0x0095:
        goto L_0x002f;
    L_0x0098:
        if (r14 == 0) goto L_0x00a8;
    L_0x009a:
        r15 = r3.addSubMenuItem();
        r0 = r17;
        r1 = r18;
        r2 = r19;
        r0.parseMenu(r1, r2, r15);
        goto L_0x002f;
    L_0x00a8:
        r5 = 1;
        r6 = r8;
        goto L_0x002f;
    L_0x00ab:
        r0 = r18;
        r8 = r0.getName();
        if (r5 == 0) goto L_0x00bc;
    L_0x00b3:
        r14 = r8.equals(r6);
        if (r14 == 0) goto L_0x00bc;
    L_0x00b9:
        r5 = 0;
        r6 = 0;
        goto L_0x0072;
    L_0x00bc:
        r10 = "group";
        r14 = r8.equals(r10);
        if (r14 == 0) goto L_0x00c8;
    L_0x00c4:
        r3.resetGroup();
        goto L_0x0075;
    L_0x00c8:
        r10 = "item";
        r14 = r8.equals(r10);
        if (r14 == 0) goto L_0x00f0;
    L_0x00d0:
        r14 = r3.hasAddedItem();
        if (r14 != 0) goto L_0x002f;
    L_0x00d6:
        r16 = r3.itemActionProvider;
        if (r16 == 0) goto L_0x00ec;
    L_0x00dc:
        r16 = r3.itemActionProvider;
        r0 = r16;
        r14 = r0.hasSubMenu();
        if (r14 == 0) goto L_0x00ec;
    L_0x00e8:
        r3.addSubMenuItem();
        goto L_0x0087;
    L_0x00ec:
        r3.addItem();
        goto L_0x008b;
    L_0x00f0:
        r10 = "menu";
        r14 = r8.equals(r10);
        if (r14 == 0) goto L_0x002f;
    L_0x00f8:
        r9 = 1;
        goto L_0x0095;
    L_0x00fa:
        r11 = new java.lang.RuntimeException;
        r10 = "Unexpected end of document";
        r11.<init>(r10);
        throw r11;
    L_0x0102:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.view.SupportMenuInflater.parseMenu(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.Menu):void");
    }

    public SupportMenuInflater(Context $r1) throws  {
        super($r1);
        this.mContext = $r1;
        this.mActionViewConstructorArguments = new Object[]{$r1};
    }

    public void inflate(int $i0, Menu $r1) throws  {
        if ($r1 instanceof SupportMenu) {
            XmlResourceParser $r2 = null;
            try {
                XmlResourceParser $r5 = this.mContext.getResources().getLayout($i0);
                $r2 = $r5;
                parseMenu($r5, Xml.asAttributeSet($r5), $r1);
                if ($r5 != null) {
                    $r5.close();
                }
            } catch (XmlPullParserException $r7) {
                throw new InflateException("Error inflating menu XML", $r7);
            } catch (IOException $r10) {
                throw new InflateException("Error inflating menu XML", $r10);
            } catch (Throwable th) {
                if ($r2 != null) {
                    $r2.close();
                }
            }
        } else {
            super.inflate($i0, $r1);
        }
    }

    private Object getRealOwner() throws  {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private Object findRealOwner(Object $r1) throws  {
        return (!($r1 instanceof Activity) && ($r1 instanceof ContextWrapper)) ? findRealOwner(((ContextWrapper) $r1).getBaseContext()) : $r1;
    }
}
