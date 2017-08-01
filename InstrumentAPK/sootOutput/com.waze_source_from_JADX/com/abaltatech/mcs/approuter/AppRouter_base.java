package com.abaltatech.mcs.approuter;

import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.logger.MCSLogger;

public abstract class AppRouter_base implements IAppRouter {
    protected static final int MaxApplications = 16;
    protected AppDescriptor[] m_registeredApps = new AppDescriptor[16];

    protected abstract void loadAppDescriptors() throws MCSException;

    protected abstract void onServerRegistered(int i, AppDescriptor appDescriptor) throws ;

    protected abstract void onServerStarted(int i, IAppServer iAppServer) throws ;

    protected abstract void onServerStopped(int i, IAppServer iAppServer) throws ;

    protected abstract void onServerUnregistered(int i, AppDescriptor appDescriptor) throws ;

    protected abstract void storeAppDescriptors() throws ;

    public void appServerStarted(AppID $r1, IAppServer $r2) throws MCSException {
        if ($r1 == null) {
            throw new MCSException("Invalid appID passed to appServerStarted() - NULL app ID");
        } else if ($r2 == null) {
            throw new MCSException("Invalid appID passed to appServerStarted() - NULL server");
        } else {
            int $i0 = (int) $r1.m_appID;
            if ($i0 < 0 || $i0 >= 16) {
                throw new MCSException("Invalid appID passed to appServerStarted(): " + $i0);
            } else if ($r1.m_appName == null) {
                throw new MCSException("Invalid appID passed to appServerStarted(): " + $i0 + " - name is null");
            } else {
                synchronized (this.m_registeredApps) {
                    if (this.m_registeredApps[$i0] == null) {
                        throw new MCSException("Invalid appID passed to appServerStarted() - application is not registered: " + $i0);
                    } else if (this.m_registeredApps[$i0].AppID.m_appName.compareTo($r1.m_appName) != 0) {
                        throw new MCSException("Invalid name passed to appServerStarted(): " + $r1.m_appName);
                    } else {
                        if (this.m_registeredApps[$i0].Server != null) {
                            MCSLogger.log("AppRouter_HTTP", "WARNING: app server already started - " + $r1.m_appName);
                        }
                        this.m_registeredApps[$i0].Server = $r2;
                        onServerStarted($i0, $r2);
                    }
                }
            }
        }
    }

    public void appServerStopped(AppID $r1) throws MCSException {
        if ($r1 == null) {
            throw new MCSException("Invalid appID passed to appServerStarted() - NULL app ID");
        }
        int $i0 = (int) $r1.m_appID;
        if ($i0 < 0 || $i0 >= 16) {
            throw new MCSException("Invalid appID passed to appServerStopped(): " + $i0);
        } else if ($r1.m_appName == null) {
            throw new MCSException("Invalid appID passed to appServerStopped(): " + $i0 + " - name is null");
        } else {
            synchronized (this.m_registeredApps) {
                if (this.m_registeredApps[$i0] == null) {
                    throw new MCSException("Invalid appID passed to appServerStarted() - application is not registered: " + $i0);
                } else if (this.m_registeredApps[$i0].AppID.m_appName.compareTo($r1.m_appName) != 0) {
                    throw new MCSException("Invalid name passed to appServerStarted(): " + $r1.m_appName);
                } else if (this.m_registeredApps[$i0].Server == null) {
                    throw new MCSException("AppRouter_HTTP: app server already stopped - " + $r1.m_appName);
                } else {
                    IAppServer $r2 = this.m_registeredApps[$i0].Server;
                    this.m_registeredApps[$i0].Server = null;
                    onServerStopped($i0, $r2);
                }
            }
        }
    }

    public AppID findAppByName(String $r1) throws MCSException {
        if ($r1 == null || $r1.trim().length() < 1) {
            throw new MCSException("Invalid appName passed to registerApp()");
        }
        synchronized (this.m_registeredApps) {
            int $i0 = 0;
            while ($i0 < 16) {
                if (this.m_registeredApps[$i0] == null || this.m_registeredApps[$i0].AppID.m_appName.compareTo($r1) != 0) {
                    $i0++;
                } else {
                    AppID $r7 = this.m_registeredApps[$i0].AppID;
                    return $r7;
                }
            }
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.abaltatech.mcs.approuter.AppID registerApp(java.lang.String r21, com.abaltatech.mcs.approuter.IStartupConfig r22) throws com.abaltatech.mcs.common.MCSException {
        /*
        r20 = this;
        if (r21 == 0) goto L_0x000f;
    L_0x0002:
        r0 = r21;
        r3 = r0.trim();
        r4 = r3.length();
        r5 = 1;
        if (r4 >= r5) goto L_0x0017;
    L_0x000f:
        r6 = new com.abaltatech.mcs.common.MCSException;
        r7 = "Invalid appName passed to registerApp()";
        r6.<init>(r7);
        throw r6;
    L_0x0017:
        r4 = 16;
        r0 = r21;
        r3 = r0.toLowerCase();
        r0 = r20;
        r8 = r0.m_registeredApps;
        monitor-enter(r8);
        r9 = 0;
    L_0x0025:
        r5 = 16;
        if (r9 >= r5) goto L_0x0120;
    L_0x0029:
        r0 = r20;
        r10 = r0.m_registeredApps;	 Catch:{ Throwable -> 0x00ae }
        r11 = r10[r9];	 Catch:{ Throwable -> 0x00ae }
        if (r11 != 0) goto L_0x0034;
    L_0x0031:
        if (r9 >= r4) goto L_0x0034;
    L_0x0033:
        r4 = r9;
    L_0x0034:
        r0 = r20;
        r10 = r0.m_registeredApps;	 Catch:{ Throwable -> 0x00ae }
        r11 = r10[r9];	 Catch:{ Throwable -> 0x00ae }
        if (r11 == 0) goto L_0x00f5;
    L_0x003c:
        r0 = r20;
        r10 = r0.m_registeredApps;	 Catch:{ Throwable -> 0x00ae }
        r11 = r10[r9];	 Catch:{ Throwable -> 0x00ae }
        r12 = r11.AppID;	 Catch:{ Throwable -> 0x00ae }
        r0 = r12.m_appName;	 Catch:{ Throwable -> 0x00ae }
        r21 = r0;
        r13 = r0.compareTo(r3);	 Catch:{ Throwable -> 0x00ae }
        if (r13 != 0) goto L_0x006d;
    L_0x004e:
        r0 = r20;
        r10 = r0.m_registeredApps;	 Catch:{ Throwable -> 0x00ae }
        r11 = r10[r9];	 Catch:{ Throwable -> 0x00ae }
        r12 = r11.AppID;	 Catch:{ Throwable -> 0x00ae }
        r0 = r20;
        r10 = r0.m_registeredApps;	 Catch:{ Throwable -> 0x00ae }
        r11 = r10[r9];	 Catch:{ Throwable -> 0x00ae }
    L_0x005c:
        if (r12 != 0) goto L_0x0119;
    L_0x005e:
        r5 = 16;
        if (r4 != r5) goto L_0x00f8;
    L_0x0062:
        r6 = new com.abaltatech.mcs.common.MCSException;	 Catch:{ Throwable -> 0x006a }
        r7 = "Too many applications registered with AppRouter_HTTP";
        r6.<init>(r7);	 Catch:{ Throwable -> 0x006a }
        throw r6;	 Catch:{ Throwable -> 0x006a }
    L_0x006a:
        r14 = move-exception;
    L_0x006b:
        monitor-exit(r8);	 Catch:{ Throwable -> 0x00ae }
        throw r14;
    L_0x006d:
        r0 = r21;
        r15 = r0.startsWith(r3);	 Catch:{ Throwable -> 0x00ae }
        if (r15 == 0) goto L_0x00b0;
    L_0x0075:
        r16 = new com.abaltatech.mcs.approuter.AppAlreadyRegisteredException;	 Catch:{ Throwable -> 0x00ae }
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00ae }
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x00ae }
        r7 = "AppName <";
        r0 = r17;
        r17 = r0.append(r7);	 Catch:{ Throwable -> 0x00ae }
        goto L_0x008a;
    L_0x0087:
        goto L_0x0025;
    L_0x008a:
        r0 = r17;
        r17 = r0.append(r3);	 Catch:{ Throwable -> 0x00ae }
        r7 = "> coincides with already reistered application name - ";
        r0 = r17;
        r17 = r0.append(r7);	 Catch:{ Throwable -> 0x00ae }
        r0 = r17;
        r1 = r21;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x00ae }
        r0 = r17;
        r21 = r0.toString();	 Catch:{ Throwable -> 0x00ae }
        r0 = r16;
        r1 = r21;
        r0.<init>(r1);	 Catch:{ Throwable -> 0x00ae }
        throw r16;	 Catch:{ Throwable -> 0x00ae }
    L_0x00ae:
        r14 = move-exception;
        goto L_0x006b;
    L_0x00b0:
        r0 = r21;
        r15 = r3.startsWith(r0);	 Catch:{ Throwable -> 0x00ae }
        if (r15 == 0) goto L_0x00f5;
    L_0x00b8:
        r16 = new com.abaltatech.mcs.approuter.AppAlreadyRegisteredException;	 Catch:{ Throwable -> 0x00ae }
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00ae }
        goto L_0x00c0;
    L_0x00bd:
        goto L_0x005c;
    L_0x00c0:
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x00ae }
        goto L_0x00c9;
    L_0x00c6:
        goto L_0x006b;
    L_0x00c9:
        r7 = "AppName <";
        r0 = r17;
        r17 = r0.append(r7);	 Catch:{ Throwable -> 0x00ae }
        r0 = r17;
        r17 = r0.append(r3);	 Catch:{ Throwable -> 0x00ae }
        r7 = "> coincides with already reistered application name - ";
        r0 = r17;
        r17 = r0.append(r7);	 Catch:{ Throwable -> 0x00ae }
        r0 = r17;
        r1 = r21;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x00ae }
        r0 = r17;
        r21 = r0.toString();	 Catch:{ Throwable -> 0x00ae }
        r0 = r16;
        r1 = r21;
        r0.<init>(r1);	 Catch:{ Throwable -> 0x00ae }
        throw r16;	 Catch:{ Throwable -> 0x00ae }
    L_0x00f5:
        r9 = r9 + 1;
        goto L_0x0087;
    L_0x00f8:
        r12 = new com.abaltatech.mcs.approuter.AppID;	 Catch:{ Throwable -> 0x006a }
        r0 = (long) r4;	 Catch:{ Throwable -> 0x006a }
        r18 = r0;
        r12.<init>(r0, r3);	 Catch:{ Throwable -> 0x006a }
        r11 = new com.abaltatech.mcs.approuter.AppDescriptor;	 Catch:{ Throwable -> 0x011e }
        r0 = r22;
        r11.<init>(r12, r0);	 Catch:{ Throwable -> 0x011e }
        r0 = r20;
        r10 = r0.m_registeredApps;	 Catch:{ Throwable -> 0x00ae }
        r10[r4] = r11;	 Catch:{ Throwable -> 0x00ae }
        r0 = r20;
        r0.onServerRegistered(r4, r11);	 Catch:{ Throwable -> 0x00ae }
    L_0x0112:
        monitor-exit(r8);	 Catch:{ Throwable -> 0x00ae }
        r0 = r20;
        r0.storeAppDescriptors();
        return r12;
    L_0x0119:
        r0 = r22;
        r11.Config = r0;	 Catch:{ Throwable -> 0x006a }
        goto L_0x0112;
    L_0x011e:
        r14 = move-exception;
        goto L_0x00c6;
    L_0x0120:
        r11 = 0;
        r12 = 0;
        goto L_0x00bd;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.approuter.AppRouter_base.registerApp(java.lang.String, com.abaltatech.mcs.approuter.IStartupConfig):com.abaltatech.mcs.approuter.AppID");
    }

    public void unregisterApp(AppID $r1) throws MCSException {
        if ($r1 == null) {
            throw new MCSException("Invalid appID passed to unregisterApp() - NULL");
        }
        int $i0 = (int) $r1.m_appID;
        if ($i0 < 0 || $i0 >= 16) {
            throw new MCSException("Invalid appID passed to unregisterApp(): " + $i0);
        }
        synchronized (this.m_registeredApps) {
            if (this.m_registeredApps[$i0] == null) {
                throw new MCSException("Invalid appID passed to unregisterApp(): " + $i0 + " is already unregistered");
            } else if ($r1.m_appName == null || this.m_registeredApps[$i0].AppID.m_appName.compareTo($r1.m_appName) != 0) {
                throw new MCSException("Invalid appID passed to unregisterApp(): " + $i0 + " - invalid app name");
            } else {
                if (this.m_registeredApps[$i0].Server != null) {
                    MCSLogger.log("AppRouter_HTTP", "WARNING - unregistreing application which is not yet stopped");
                    this.m_registeredApps[$i0].Server = null;
                }
                onServerUnregistered($i0, this.m_registeredApps[$i0]);
                this.m_registeredApps[$i0] = null;
            }
        }
        storeAppDescriptors();
    }
}
