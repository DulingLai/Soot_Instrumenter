package com.waze.install;

import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableCallback;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.phone.LoginOptionsActivity;
import java.util.Locale;

public class InstallNativeManager {
    private static boolean m_CleanInstall = false;
    private static boolean m_MinimalInstallation = false;
    private static boolean ready = false;

    public interface VideoUrlListener {
        void onComplete(String str) throws ;
    }

    class C18098 extends RunnableUICallback {
        C18098() throws  {
        }

        public void event() throws  {
            Log.d(Logger.TAG, "wayToGoCont event running in thread " + Thread.currentThread().getId());
            InstallNativeManager.this.wayToGoContNTV();
        }

        public void callback() throws  {
            Log.d(Logger.TAG, "wayToGoCont callback running in thread " + Thread.currentThread().getId());
        }
    }

    private native void countrySelectedNTV(int i) throws ;

    private native String getVideoUrlNTV(boolean z, int i, int i2) throws ;

    private native void initNativeLayerNTV() throws ;

    private native void langSelectedNTV(int i) throws ;

    private static native void setCountryNTV(String str) throws ;

    private native void termsOfUseResponseNTV(int i) throws ;

    private native void wayToGoContNTV() throws ;

    public static void staticInit() throws  {
        InstallNativeManager $r0 = new InstallNativeManager();
    }

    private void init() throws  {
        Log.d(Logger.TAG, "init install nm ready=" + ready);
        if (!ready) {
            Log.d(Logger.TAG, "initNativeLayerNTV " + Thread.currentThread().getId());
            initNativeLayerNTV();
            ready = true;
        }
    }

    public InstallNativeManager() throws  {
        init();
        Log.d(Logger.TAG, "ctr running in thread " + Thread.currentThread().getId());
    }

    public void countrySelected(final int $i0) throws  {
        Log.d(Logger.TAG, "countrySelected running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                Log.d(Logger.TAG, "countrySelected event running in thread " + Thread.currentThread().getId());
                Log.d(Logger.TAG, "country= " + $i0);
                InstallNativeManager.this.countrySelectedNTV($i0);
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "countrySelected callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public static void openSelectCountryMenu() throws  {
        Log.d(Logger.TAG, "openSelectCountryMenu running in thread " + Thread.currentThread().getId());
        AppService.Post(new RunnableCallback(AppService.getNativeManager()) {
            public void event() throws  {
                Log.d(Logger.TAG, "openSelectCountryMenu event running in thread " + Thread.currentThread().getId());
                ActivityBase $r5 = AppService.getActiveActivity();
                String $r4 = ((TelephonyManager) $r5.getSystemService("phone")).getNetworkCountryIso();
                if ($r4 == null || $r4.length() <= 0) {
                    $r5.startActivityForResult(new Intent($r5, LocationFailedActivity.class), 1);
                } else {
                    InstallNativeManager.setCountry($r4.toUpperCase());
                }
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "openSelectCountryMenu callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public void langSelected(final int $i0) throws  {
        Log.d(Logger.TAG, "langSelected running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                Log.d(Logger.TAG, "langSelected event running in thread " + Thread.currentThread().getId());
                Log.d(Logger.TAG, "lang= " + $i0);
                InstallNativeManager.this.langSelectedNTV($i0);
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "langSelected callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public static String GetLocale() throws  {
        String $r1 = Locale.getDefault().getLanguage();
        String $r2 = $r1;
        if ($r1.equals("en") && Locale.getDefault().getCountry().equals("GB")) {
            $r2 = "en-GB";
        }
        if ($r2.equals("pt") && Locale.getDefault().getCountry().equals("PT")) {
            $r2 = "pt-PT";
        }
        return ($r2.equals("ch") && Locale.getDefault().getCountry().equals("TW")) ? "zh_TW" : $r2;
    }

    public void termsOfUseResponse(final int $i0) throws  {
        Log.d(Logger.TAG, "termsOfUseResponse running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {

            class C18041 implements Runnable {
                C18041() throws  {
                }

                public void run() throws  {
                    InstallNativeManager.this.termsOfUseResponseNTV($i0);
                }
            }

            public void event() throws  {
                Log.d(Logger.TAG, "termsOfUseResponse event running in thread " + Thread.currentThread().getId());
                Log.d(Logger.TAG, "selection= " + $i0);
                NativeManager.Post(new C18041());
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "termsOfUseResponse callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public static boolean IsCleanInstallation() throws  {
        return m_CleanInstall;
    }

    public static boolean IsMinimalInstallation() throws  {
        return m_MinimalInstallation;
    }

    public void displayWelcome(final boolean $z0, final boolean $z1, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                InstallNativeManager.m_CleanInstall = $z0;
                InstallNativeManager.m_MinimalInstallation = $z1;
                Logger.m41i("GilTestSignup: Display welcome called. clean install = " + $z0 + ", is minimal = " + $z1 + ", type = " + $i0);
                MainActivity.bReportMapShownAnalytics = true;
                ActivityBase $r4 = AppService.getActiveActivity();
                if (NativeManager.getInstance().isTermsAccepted()) {
                    Intent $r1 = new Intent($r4, LoginOptionsActivity.class);
                    $r1.putExtra(LoginOptionsActivity.EXTRA_IS_INSTALL_FLOW, $z0);
                    $r4.startActivity($r1);
                    return;
                }
                NativeManager.getInstance().SignUplogAnalytics("START", null, null, true);
                $r4.startActivity(new Intent($r4, SignUpWelcomeActivity.class));
            }
        });
    }

    public static void setCountry(final String $r0) throws  {
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                InstallNativeManager.setCountryNTV($r0);
            }

            public void callback() throws  {
            }
        });
    }

    public static void openTermsOfUse() throws  {
        Log.d(Logger.TAG, "openTermsOfUse running in thread " + Thread.currentThread().getId());
        AppService.Post(new RunnableCallback(AppService.getNativeManager()) {
            public void event() throws  {
                Log.d(Logger.TAG, "openTermsOfUse event running in thread " + Thread.currentThread().getId());
                ActivityBase $r5 = AppService.getActiveActivity();
                $r5.startActivityForResult(new Intent($r5, TermsOfUseActivity.class), 1);
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "openTermsOfUse callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public void wayToGoCont() throws  {
        Log.d(Logger.TAG, "wayToGoCont running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new C18098());
    }

    public static void openWayToGo() throws  {
        Log.d(Logger.TAG, "openWayToGo running in thread " + Thread.currentThread().getId());
        AppService.Post(new RunnableCallback(AppService.getNativeManager()) {
            public void event() throws  {
                Log.d(Logger.TAG, "openWayToGo event running in thread " + Thread.currentThread().getId());
                ActivityBase $r5 = AppService.getActiveActivity();
                $r5.startActivityForResult(new Intent($r5, WayToGoActivity.class), 1);
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "openWayToGo callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public static void welcomeGuidedTour(String url) throws  {
        Log.d(Logger.TAG, "welcomeGuidedTour running in thread " + Thread.currentThread().getId());
        AppService.Post(new RunnableCallback(AppService.getNativeManager()) {
            public void event() throws  {
                Log.d(Logger.TAG, "welcomeGuidedTour event running in thread " + Thread.currentThread().getId());
                AppService.getMainActivity().cancelSplash();
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "welcomeGuidedTour callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public void getVideoUrl(final boolean $z0, final int $i0, final int $i1, final VideoUrlListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            String url = null;

            public void event() throws  {
                this.url = InstallNativeManager.this.getVideoUrlNTV($z0, $i0, $i1);
            }

            public void callback() throws  {
                $r1.onComplete(this.url);
            }
        });
    }
}
