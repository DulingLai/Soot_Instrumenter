package com.google.android.apps.analytics;

import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import java.util.Locale;

/* compiled from: dalvik_source_com.waze.apk */
class NetworkRequestUtil {
    private static final String FAKE_DOMAIN_HASH = "999";
    private static final String GOOGLE_ANALYTICS_GIF_PATH = "/__utm.gif";
    private static final int X10_PROJECT_NAMES = 8;
    private static final int X10_PROJECT_SCOPES = 11;
    private static final int X10_PROJECT_VALUES = 9;

    NetworkRequestUtil() throws  {
    }

    static void appendCurrencyValue(StringBuilder $r0, String $r1, double $d0) throws  {
        $r0.append($r1).append("=");
        $d0 = Math.floor(($d0 * 1000000.0d) + 0.5d) / 1000000.0d;
        if ($d0 != 0.0d) {
            $r0.append(Double.toString($d0));
        }
    }

    private static void appendStringValue(StringBuilder $r0, String $r1, String $r2) throws  {
        $r0.append($r1).append("=");
        if ($r2 != null && $r2.trim().length() > 0) {
            $r0.append(AnalyticsParameterEncoder.encode($r2));
        }
    }

    public static String constructEventRequestPath(Event $r0, String $r1) throws  {
        Locale $r3 = Locale.getDefault();
        StringBuilder $r2 = new StringBuilder();
        StringBuilder $r4 = new StringBuilder();
        $r4.append(String.format("5(%s*%s", new Object[]{encode($r0.category), encode($r0.action)}));
        if ($r0.label != null) {
            $r4.append("*").append(encode($r0.label));
        }
        $r4.append(")");
        if ($r0.value > -1) {
            $r4.append(String.format("(%d)", new Object[]{Integer.valueOf($r0.value)}));
        }
        $r4.append(getCustomVariableParams($r0));
        $r2.append(GOOGLE_ANALYTICS_GIF_PATH);
        $r2.append("?utmwv=4.6ma");
        $r2.append("&utmn=").append($r0.randomVal);
        $r2.append("&utmt=event");
        $r2.append("&utme=").append($r4.toString());
        $r2.append("&utmcs=UTF-8");
        $r2.append(String.format("&utmsr=%dx%d", new Object[]{Integer.valueOf($r0.screenWidth), Integer.valueOf($r0.screenHeight)}));
        $r2.append(String.format("&utmul=%s-%s", new Object[]{$r3.getLanguage(), $r3.getCountry()}));
        $r2.append("&utmac=").append($r0.accountId);
        $r2.append("&utmcc=").append(getEscapedCookieString($r0, $r1));
        return $r2.toString();
    }

    public static String constructItemRequestPath(Event $r0, String $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        $r2.append(GOOGLE_ANALYTICS_GIF_PATH);
        $r2.append("?utmwv=4.6ma");
        $r2.append("&utmn=").append($r0.randomVal);
        $r2.append("&utmt=item");
        Item $r4 = $r0.getItem();
        if ($r4 != null) {
            appendStringValue($r2, "&utmtid", $r4.getOrderId());
            appendStringValue($r2, "&utmipc", $r4.getItemSKU());
            appendStringValue($r2, "&utmipn", $r4.getItemName());
            appendStringValue($r2, "&utmiva", $r4.getItemCategory());
            appendCurrencyValue($r2, "&utmipr", $r4.getItemPrice());
            $r2.append("&utmiqt=");
            if ($r4.getItemCount() != 0) {
                $r2.append($r4.getItemCount());
            }
        }
        $r2.append("&utmac=").append($r0.accountId);
        $r2.append("&utmcc=").append(getEscapedCookieString($r0, $r1));
        return $r2.toString();
    }

    public static String constructPageviewRequestPath(Event $r0, String $r1) throws  {
        String $r3 = "";
        if ($r0.action != null) {
            $r3 = $r0.action;
        }
        if (!$r3.startsWith("/")) {
            $r3 = "/" + $r3;
        }
        $r3 = encode($r3);
        String $r4 = getCustomVariableParams($r0);
        Locale $r5 = Locale.getDefault();
        StringBuilder $r2 = new StringBuilder();
        $r2.append(GOOGLE_ANALYTICS_GIF_PATH);
        $r2.append("?utmwv=4.6ma");
        $r2.append("&utmn=").append($r0.randomVal);
        if ($r4.length() > 0) {
            $r2.append("&utme=").append($r4);
        }
        $r2.append("&utmcs=UTF-8");
        $r2.append(String.format("&utmsr=%dx%d", new Object[]{Integer.valueOf($r0.screenWidth), Integer.valueOf($r0.screenHeight)}));
        $r2.append(String.format("&utmul=%s-%s", new Object[]{$r5.getLanguage(), $r5.getCountry()}));
        $r2.append("&utmp=").append($r3);
        $r2.append("&utmac=").append($r0.accountId);
        $r2.append("&utmcc=").append(getEscapedCookieString($r0, $r1));
        return $r2.toString();
    }

    public static String constructTransactionRequestPath(Event $r0, String $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        $r2.append(GOOGLE_ANALYTICS_GIF_PATH);
        $r2.append("?utmwv=4.6ma");
        $r2.append("&utmn=").append($r0.randomVal);
        $r2.append("&utmt=tran");
        Transaction $r4 = $r0.getTransaction();
        if ($r4 != null) {
            appendStringValue($r2, "&utmtid", $r4.getOrderId());
            appendStringValue($r2, "&utmtst", $r4.getStoreName());
            appendCurrencyValue($r2, "&utmtto", $r4.getTotalCost());
            appendCurrencyValue($r2, "&utmttx", $r4.getTotalTax());
            appendCurrencyValue($r2, "&utmtsp", $r4.getShippingCost());
            appendStringValue($r2, "&utmtci", "");
            appendStringValue($r2, "&utmtrg", "");
            appendStringValue($r2, "&utmtco", "");
        }
        $r2.append("&utmac=").append($r0.accountId);
        $r2.append("&utmcc=").append(getEscapedCookieString($r0, $r1));
        return $r2.toString();
    }

    private static void createX10Project(CustomVariable[] $r0, StringBuilder $r1, int $i0) throws  {
        $r1.append($i0).append("(");
        boolean $z0 = true;
        for (int $i1 = 0; $i1 < $r0.length; $i1++) {
            if ($r0[$i1] != null) {
                CustomVariable $r3 = $r0[$i1];
                if ($z0) {
                    $z0 = false;
                } else {
                    $r1.append("*");
                }
                $r1.append($r3.getIndex()).append("!");
                switch ($i0) {
                    case 8:
                        $r1.append(x10Escape(encode($r3.getName())));
                        break;
                    case 9:
                        $r1.append(x10Escape(encode($r3.getValue())));
                        break;
                    case 10:
                        break;
                    case 11:
                        $r1.append($r3.getScope());
                        break;
                    default:
                        break;
                }
            }
        }
        $r1.append(")");
    }

    private static String encode(String $r0) throws  {
        return AnalyticsParameterEncoder.encode($r0);
    }

    public static String getCustomVariableParams(Event $r0) throws  {
        StringBuilder $r1 = new StringBuilder();
        CustomVariableBuffer $r2 = $r0.getCustomVariableBuffer();
        if ($r2 == null) {
            return "";
        }
        if (!$r2.hasCustomVariables()) {
            return "";
        }
        CustomVariable[] $r3 = $r2.getCustomVariableArray();
        createX10Project($r3, $r1, 8);
        createX10Project($r3, $r1, 9);
        createX10Project($r3, $r1, 11);
        return $r1.toString();
    }

    public static String getEscapedCookieString(Event $r0, String $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        $r2.append("__utma=");
        $r2.append(FAKE_DOMAIN_HASH).append(FileUploadSession.SEPARATOR);
        $r2.append($r0.userId).append(FileUploadSession.SEPARATOR);
        $r2.append($r0.timestampFirst).append(FileUploadSession.SEPARATOR);
        $r2.append($r0.timestampPrevious).append(FileUploadSession.SEPARATOR);
        $r2.append($r0.timestampCurrent).append(FileUploadSession.SEPARATOR);
        $r2.append($r0.visits);
        if ($r1 != null) {
            $r2.append("+__utmz=");
            $r2.append(FAKE_DOMAIN_HASH).append(FileUploadSession.SEPARATOR);
            $r2.append($r0.timestampFirst).append(FileUploadSession.SEPARATOR);
            $r2.append("1.1.");
            $r2.append($r1);
        }
        return encode($r2.toString());
    }

    private static String x10Escape(String $r0) throws  {
        return $r0.replace("'", "'0").replace(")", "'1").replace("*", "'2").replace("!", "'3");
    }
}
