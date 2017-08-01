package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class ServiceStatusCommand extends WLServiceCommand {
    private static final int CMD_FIXED_SIZE = 10;
    public static final short ID = (short) 114;
    private static final String TAG = "RegisterStatusNotificationCommand";
    private boolean m_isRegistered;
    private boolean m_isValid;
    private List<String> m_protocols;
    private int m_registerNotificationID;
    private String m_serviceName;

    public ServiceStatusCommand(@dalvik.annotation.Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;ZI)V"}) java.lang.String r22, @dalvik.annotation.Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;ZI)V"}) java.lang.String r23, @dalvik.annotation.Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;ZI)V"}) java.lang.String r24, @dalvik.annotation.Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;ZI)V"}) java.util.List<java.lang.String> r25, @dalvik.annotation.Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;ZI)V"}) boolean r26, @dalvik.annotation.Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;ZI)V"}) int r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:31:0x018a in {3, 5, 8, 14, 17, 18, 20, 24, 29, 32, 36, 37} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r21 = this;
        r0 = r24;
        r1 = r25;
        r3 = getNeededLength(r0, r1);
        r4 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        r0 = r21;
        r1 = r22;
        r2 = r23;
        r0.<init>(r4, r3, r1, r2);
        r4 = 0;
        r0 = r21;
        r0.m_isValid = r4;
        r4 = 0;
        r0 = r21;
        r0.m_registerNotificationID = r4;
        r4 = 0;
        r0 = r21;
        r0.m_isRegistered = r4;
        r0 = r21;
        r5 = super.isValid();
        if (r5 == 0) goto L_0x01b0;
    L_0x002a:
        if (r24 != 0) goto L_0x0115;
    L_0x002c:
        r22 = "";
    L_0x002e:
        if (r25 != 0) goto L_0x0165;
    L_0x0030:
        r6 = new java.util.ArrayList;
        r25 = r6;
        r6.<init>();
    L_0x0037:
        r0 = r22;
        r7 = r0.getBytes();
        r0 = r21;
        r3 = r0.getPayloadOffset();
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r3 + 0;
        r0 = r27;
        r8.putInt(r9, r0);
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r3 + 4;
        if (r26 == 0) goto L_0x018e;
    L_0x0056:
        r10 = 1;
    L_0x0057:
        r8.putInt(r9, r10);
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r3 + 8;
        r0 = r25;
        r11 = r0.size();
        r10 = (byte) r11;
        r8.putByte(r9, r10);
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r3 + 9;
        r11 = r7.length;
        r10 = (byte) r11;
        r8.putByte(r9, r10);
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r12 = r8.getData();
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r8.getPos();
        r9 = r9 + r3;
        r9 = r9 + 10;
        r11 = r7.length;
        r4 = 0;
        java.lang.System.arraycopy(r7, r4, r12, r9, r11);
        r3 = r3 + 10;
        r9 = r7.length;
        r3 = r3 + r9;
        r0 = r25;
        r13 = r0.iterator();
    L_0x0097:
        r5 = r13.hasNext();
        if (r5 == 0) goto L_0x0197;
    L_0x009d:
        r14 = r13.next();
        r15 = r14;
        r15 = (java.lang.String) r15;
        r23 = r15;
        if (r23 != 0) goto L_0x0190;
    L_0x00a9:
        r23 = "";
    L_0x00ab:
        r0 = r23;
        r9 = r0.length();
        r4 = 50;
        if (r9 <= r4) goto L_0x00ea;
    L_0x00b5:
        r4 = 0;
        r16 = 50;
        r0 = r23;
        r1 = r16;
        r24 = r0.substring(r4, r1);
        r23 = r24;
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r19 = "Protocol name too long, trimming to: ";
        r0 = r17;
        r1 = r19;
        r18 = r0.append(r1);
        r0 = r18;
        r1 = r24;
        r18 = r0.append(r1);
        r0 = r18;
        r24 = r0.toString();
        r19 = "RegisterStatusNotificationCommand";
        r0 = r19;
        r1 = r24;
        android.util.Log.e(r0, r1);
    L_0x00ea:
        r0 = r23;
        r7 = r0.getBytes();
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r7.length;
        r10 = (byte) r9;
        r8.putByte(r3, r10);
        r3 = r3 + 1;
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r12 = r8.getData();
        r0 = r21;
        r8 = r0.m_binaryCommandContainer;
        r9 = r8.getPos();
        r11 = r9 + r3;
        r9 = r7.length;
        r4 = 0;
        java.lang.System.arraycopy(r7, r4, r12, r11, r9);
        r9 = r7.length;
        r3 = r3 + r9;
        goto L_0x0097;
    L_0x0115:
        r0 = r24;
        r23 = r0.trim();
        r22 = r23;
        goto L_0x0121;
    L_0x011e:
        goto L_0x00ab;
    L_0x0121:
        r0 = r23;
        r3 = r0.length();
        r4 = 50;
        if (r3 <= r4) goto L_0x002e;
    L_0x012b:
        r4 = 0;
        r16 = 50;
        r0 = r23;
        r1 = r16;
        r23 = r0.substring(r4, r1);
        r22 = r23;
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        r19 = "serviceName is too long, trimming to: ";
        r0 = r17;
        r1 = r19;
        r18 = r0.append(r1);
        r0 = r18;
        r1 = r23;
        r18 = r0.append(r1);
        r0 = r18;
        r23 = r0.toString();
        goto L_0x015b;
    L_0x0158:
        goto L_0x002e;
    L_0x015b:
        r19 = "RegisterStatusNotificationCommand";
        r0 = r19;
        r1 = r23;
        android.util.Log.e(r0, r1);
        goto L_0x0158;
    L_0x0165:
        r0 = r25;
        r3 = r0.size();
        r4 = 10;
        if (r3 <= r4) goto L_0x0037;
    L_0x016f:
        r4 = 0;
        r16 = 10;
        r0 = r25;
        r1 = r16;
        r25 = r0.subList(r4, r1);
        goto L_0x017e;
    L_0x017b:
        goto L_0x0037;
    L_0x017e:
        r19 = "RegisterStatusNotificationCommand";
        r20 = "Too many protocols, trimming to 10";
        r0 = r19;
        r1 = r20;
        android.util.Log.e(r0, r1);
        goto L_0x017b;
        goto L_0x018e;
    L_0x018b:
        goto L_0x0057;
    L_0x018e:
        r10 = 0;
        goto L_0x018b;
    L_0x0190:
        r0 = r23;
        r23 = r0.trim();
        goto L_0x011e;
    L_0x0197:
        r0 = r26;
        r1 = r21;
        r1.m_isRegistered = r0;
        r0 = r27;
        r1 = r21;
        r1.m_registerNotificationID = r0;
        r0 = r22;
        r1 = r21;
        r1.m_serviceName = r0;
        r0 = r25;
        r1 = r21;
        r1.m_protocols = r0;
        return;
    L_0x01b0:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.ServiceStatusCommand.<init>(java.lang.String, java.lang.String, java.lang.String, java.util.List, boolean, int):void");
    }

    public /* bridge */ /* synthetic */ String getReceiverID() throws  {
        return super.getReceiverID();
    }

    public /* bridge */ /* synthetic */ String getSenderID() throws  {
        return super.getSenderID();
    }

    public ServiceStatusCommand(DataBuffer $r1) throws  {
        super($r1);
        this.m_isValid = false;
        this.m_registerNotificationID = 0;
        this.m_isRegistered = false;
        if (!isValid()) {
            Log.e(TAG, "Not a valid command!");
        }
        short $s0 = getCommandID();
        if ($s0 != ID) {
            Log.e(TAG, "Invalid command ID: " + $s0);
        }
    }

    private static int getNeededLength(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)I"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)I"}) List<String> $r1) throws  {
        if ($r0 == null) {
            $r0 = "";
        } else {
            String $r5 = $r0.trim();
            $r0 = $r5;
            if ($r5.length() > 50) {
                $r0 = $r5.substring(0, 50);
            }
        }
        List $r12;
        if ($r1 == null) {
            $r12 = r6;
            ArrayList r6 = new ArrayList();
        } else if ($r1.size() > 10) {
            $r12 = $r1.subList(0, 10);
        }
        int $i0 = 10 + $r0.getBytes().length;
        for (String $r02 : $r1) {
            if ($r02 == null) {
                $r02 = "";
            } else {
                $r02 = $r02.trim();
            }
            if ($r02.length() > 50) {
                $r02 = $r02.substring(0, 50);
            }
            $i0 += $r02.getBytes().length + 1;
        }
        return $i0;
    }

    public boolean isValid() throws  {
        boolean $z0 = this.m_isValid;
        if ($z0) {
            return $z0;
        }
        $z0 = super.isValid();
        int $i1 = getPayloadOffset();
        $z0 = $z0 && this.m_binaryCommandContainer.getSize() >= $i1 + 10;
        if ($z0) {
            this.m_registerNotificationID = this.m_binaryCommandContainer.getInt($i1 + 0);
            if (this.m_binaryCommandContainer.getInt($i1 + 4) == 1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            this.m_isRegistered = $z0;
            short $s0 = this.m_binaryCommandContainer.getByte($i1 + 8) & (short) 255;
            $i1 += 9;
            short $s5 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
            $i1++;
            int $i2 = this.m_binaryCommandContainer.getSize();
            if ($i1 + $s5 > $i2 || $s5 < (short) 0) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            if ($z0) {
                this.m_serviceName = new String(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i1, $s5);
                $i1 += $s5;
            }
            this.m_protocols = new ArrayList();
            short $i3 = (short) 0;
            while ($z0 && $i3 < $s0) {
                if ($i1 < $i2) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                if ($z0) {
                    $s5 = this.m_binaryCommandContainer.getByte($i1) & (short) 255;
                    $i1++;
                    if ($i1 + $s5 <= $i2) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($z0) {
                        this.m_protocols.add(new String(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + $i1, $s5));
                        $i1 += $s5;
                    }
                }
                $i3++;
            }
        }
        this.m_isValid = $z0;
        return $z0;
    }

    public int getRegisterNotificationID() throws  {
        return this.m_registerNotificationID;
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public List<String> getProtocols() throws  {
        return this.m_protocols;
    }

    public boolean isRegistered() throws  {
        return this.m_isRegistered;
    }
}
