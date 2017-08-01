package com.abaltatech.wlappservices;

import android.util.SparseArray;
import com.abaltatech.mcp.mcs.http.IHttpLayer;
import com.abaltatech.mcp.mcs.http.IRequestHandler;
import com.abaltatech.mcp.mcs.http.Request;
import com.abaltatech.mcp.mcs.http.Response;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.wlappservices.EServiceErrorCode.EServiceErrorCodeWrapper;
import com.abaltatech.wlappservices.ServiceUtils.InterruptFlagWrapper;
import com.waze.analytics.AnalyticsEvents;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WLServicesHTTPRequestHandler implements IRequestHandler, IServiceNotificationHandler, IServiceStatusNotification {
    private static final String PATH_WLSERVICE_EXECUTE = "/wlservice/execute?";
    private static final String PATH_WLSERVICE_NOTIFICATION = "/wlservice/notification?";
    private static final String PATH_WLSERVICE_STATUS = "/wlservices/status";
    private static final String TAG = WLServicesHTTPRequestHandler.class.getSimpleName();
    private static final String gc_badRequestResponse = "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"initial-scale=1, minimum-scale=1, width=device-width\"><title>Error 400 (Bad Request)</title><p><b>400.</b> <ins>That&apos;s an error.</ins></p><p>The request is not formatted well.</p></body></html>";
    private static final String gc_requestCannotBeExecutedResponse = "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"initial-scale=1, minimum-scale=1, width=device-width\"><title>Error 500 (Internal Server Error)</title><p><b>503.</b> <ins>That&apos;s an error.</ins></p><p>The request cannot be executed.</ins></p></body></html>";
    private static final String gc_unavailableResponse = "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"initial-scale=1, minimum-scale=1, width=device-width\"><title>Error 503 (Service Unavailable)</title><p><b>503.</b> <ins>That&apos;s an error.</ins></p><p>The service cannot be found.</ins></p></body></html>";
    private List<Integer> m_connectionForStatusNotification = new ArrayList();
    protected SparseArray<List<String>> m_connectionID2NotificationsMap = new SparseArray();
    private HashMap<Integer, List<String>> m_connectionToStatusNotificationsData = new HashMap();
    private InterruptFlagWrapper m_interruptFlag = null;
    private boolean m_isRegisteredForStatusNotification = false;
    private final Object m_lock = new Object();
    protected Map<String, List<Integer>> m_resourceToConnectionsMap = new HashMap();
    protected Map<String, ServiceProxy> m_serviceNameMap = new HashMap();
    protected Map<String, List<String>> m_serviceNameToResourceMap = new HashMap();

    private static class SingleResponse extends Response {
        SingleResponse(int $i0, String $r1, String response) throws  {
            byte[] $r3;
            if ($r1 != null) {
                $r3 = $r1.getBytes();
            } else {
                $r3 = null;
            }
            super($r1, $i0, null, $r3, true);
            addHeaderField("Access-Control-Allow-Origin", "*");
            addHeaderField("Keep-Alive", "timeout=5, max=100");
        }

        public void setContentType(String $r1) throws  {
            this.ContentType = $r1;
        }

        public void setIsLast(boolean $z0) throws  {
            this.IsLast = $z0;
        }
    }

    private static class BadRequestResponse extends SingleResponse {
        BadRequestResponse() throws  {
            super(400, "Bad Request", WLServicesHTTPRequestHandler.gc_badRequestResponse);
        }
    }

    private static class RequestCannotBeExecutedResponse extends SingleResponse {
        RequestCannotBeExecutedResponse() throws  {
            super(DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT, "Internal Server Error", WLServicesHTTPRequestHandler.gc_requestCannotBeExecutedResponse);
        }
    }

    private static class ServiceUnavailableResponse extends SingleResponse {
        ServiceUnavailableResponse() throws  {
            super(DisplayStrings.DS_NOTEC, "Service Unavailable", WLServicesHTTPRequestHandler.gc_unavailableResponse);
        }
    }

    private static class SuccessResponse extends SingleResponse {
        SuccessResponse(String $r1) throws  {
            super(200, AnalyticsEvents.ANALYTICS_EVENT_VALUE_OK, $r1);
        }
    }

    protected ServiceProxy findProxy(String $r1) throws  {
        ServiceProxy $r2 = null;
        synchronized (this.m_lock) {
            if (this.m_serviceNameMap.containsKey($r1)) {
                $r2 = (ServiceProxy) this.m_serviceNameMap.get($r1);
            }
        }
        this.m_interruptFlag = new InterruptFlagWrapper();
        if ($r2 != null) {
            return $r2;
        }
        $r2 = ServiceUtils.FindServiceByNameSynchronously($r1, this.m_interruptFlag, 5000);
        if ($r2 == null) {
            return $r2;
        }
        synchronized (this.m_lock) {
            this.m_serviceNameMap.put($r1, $r2);
        }
        return $r2;
    }

    public void terminate() throws  {
        synchronized (this.m_lock) {
            for (String clearRegistration : this.m_serviceNameMap.keySet()) {
                clearRegistration(clearRegistration);
            }
        }
    }

    public void onNotification(String $r1, byte[] $r2) throws  {
        String $r3 = new String($r2);
        synchronized (this.m_lock) {
            if (this.m_resourceToConnectionsMap.containsKey($r1)) {
                for (Integer $r9 : (List) this.m_resourceToConnectionsMap.get($r1)) {
                    List $r7 = (List) this.m_connectionID2NotificationsMap.get($r9.intValue());
                    if ($r7 != null && $r7.size() < 50) {
                        $r7.add($r3);
                    }
                }
            }
        }
    }

    public Response processRequest(Request $r1, int $i0) throws  {
        SingleResponse badRequestResponse;
        String $r3 = $r1.Method.toUpperCase(Locale.ENGLISH);
        SingleResponse $r7 = null;
        ERequestMethod $r8 = ERequestMethod.GET;
        this.m_interruptFlag = new InterruptFlagWrapper();
        String $r5 = $r1.getRequestParamByName("serviceName");
        String $r10 = $r1.getRequestParamByName("resourceAddress");
        String $r11 = $r10;
        if (($r5 == null || $r5.isEmpty() || $r10 == null || $r10.isEmpty()) && $r1.Url.indexOf(PATH_WLSERVICE_STATUS) != 0) {
            $r7 = badRequestResponse;
            badRequestResponse = new BadRequestResponse();
        } else if ($r3.compareTo("PUT") == 0) {
            $r8 = ERequestMethod.PUT;
        } else if ($r3.compareTo("POST") == 0) {
            $r8 = ERequestMethod.POST;
        } else if ($r3.compareTo("DELETE") == 0) {
            $r8 = ERequestMethod.DELETE;
        } else if ($r3.compareTo("GET") == 0) {
            $r8 = ERequestMethod.GET;
        } else if ($r3.compareTo(AnalyticsEvents.ANALYTICS_EVENT_VALUE_OPTIONS) == 0) {
            $r7 = badRequestResponse;
            badRequestResponse = new SuccessResponse("");
            $r7.addHeaderField("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        } else {
            $r7 = badRequestResponse;
            badRequestResponse = new BadRequestResponse();
        }
        if ($r7 != null) {
            return $r7;
        }
        if ($r1.Url.indexOf(PATH_WLSERVICE_STATUS) == 0 && ERequestMethod.GET == $r8) {
            return handleStatusRequest($i0);
        }
        ServiceProxy $r13 = findProxy($r5);
        if ($r13 == null) {
            return new ServiceUnavailableResponse();
        }
        HashMap $r14 = $r1.getRequestParams();
        for (String $r102 : $r14.keySet()) {
            $r3 = (String) $r14.get($r102);
            if (!($r102.compareTo("serviceName") == 0 || $r102.compareTo("resourceAddress") == 0)) {
                $r11 = ((($r11 + '&') + $r102) + '=') + $r3;
            }
        }
        $r3 = $r11;
        Map $r24;
        if ($r1.Url.indexOf(PATH_WLSERVICE_EXECUTE) == 0) {
            ServiceRequest $r2 = r0;
            ServiceRequest serviceRequest = new ServiceRequest();
            $r2.setAllowExecuteInForeground(true);
            $r2.setRequestMethod($r8);
            EServiceErrorCodeWrapper $r4 = r0;
            EServiceErrorCodeWrapper eServiceErrorCodeWrapper = new EServiceErrorCodeWrapper(-1);
            if ($r1.getBody() != null) {
                $r2.setRequestBody($r1.getBody());
            } else {
                $r2.setRequestBody(new byte[null]);
            }
            ServiceResponse $r20 = ServiceUtils.ExecuteRequestSynchronously($r13, $r2, $r3, $r4, this.m_interruptFlag, 60000);
            ServiceResponse $r21 = $r20;
            if ($r20 == null && $r4.getErrorCode() == EServiceErrorCode.NotAvailable) {
                synchronized (this.m_lock) {
                    $r24 = this.m_serviceNameMap;
                    $r24.remove($r5);
                    $r24 = this.m_serviceNameToResourceMap;
                    $r24.remove($r5);
                }
                $r13 = findProxy($r5);
                if ($r13 != null) {
                    $r21 = ServiceUtils.ExecuteRequestSynchronously($r13, $r2, $r3, $r4, this.m_interruptFlag, 60000);
                }
            }
            if ($r21 != null) {
                return new SuccessResponse(new String($r21.getResponseBody()));
            }
            return new SuccessResponse(String.format("{\"errorCode\": %d }", new Object[]{Integer.valueOf($r4.getValue())}));
        } else if ($r1.Url.indexOf(PATH_WLSERVICE_NOTIFICATION) != 0 || ERequestMethod.GET != $r8) {
            return new BadRequestResponse();
        } else {
            boolean $z0 = false;
            synchronized (this.m_lock) {
                this.m_connectionID2NotificationsMap.put($i0, new ArrayList());
                $r24 = this.m_resourceToConnectionsMap;
                if (!$r24.containsKey($r11)) {
                    this.m_resourceToConnectionsMap.put($r11, new ArrayList());
                }
                $r24 = this.m_resourceToConnectionsMap;
                ((List) $r24.get($r3)).add(Integer.valueOf($i0));
                $r24 = this.m_serviceNameToResourceMap;
                if (!$r24.containsKey($r5)) {
                    this.m_serviceNameToResourceMap.put($r5, new ArrayList());
                    $z0 = true;
                }
                $r24 = this.m_serviceNameToResourceMap;
                if (!((List) $r24.get($r5)).contains($r3)) {
                    $r24 = this.m_serviceNameToResourceMap;
                    ((List) $r24.get($r5)).add($r3);
                    $z0 = true;
                }
            }
            $r7 = badRequestResponse;
            badRequestResponse = new SuccessResponse("");
            $r7.setContentType("text/event-stream");
            $r7.setIsLast(false);
            $r7.addHeaderField("Cache-Control", "no-cache");
            $r7.addHeaderField("Access-Control-Allow-Origin", "*");
            if (!$z0) {
                return $r7;
            }
            $r13.registerForNotification($r3, this);
            return $r7;
        }
    }

    public boolean canProcess(Request $r1) throws  {
        boolean $z0 = false;
        if (!this.m_isRegisteredForStatusNotification) {
            ServiceManager.getInstance().registerForServiceStatusNotification(this);
            this.m_isRegisteredForStatusNotification = true;
        }
        if ($r1 == null) {
            return false;
        }
        String $r2 = $r1.Url;
        if ($r2 == null) {
            return false;
        }
        if ($r2.contains(PATH_WLSERVICE_EXECUTE) || $r2.contains(PATH_WLSERVICE_NOTIFICATION) || $r2.contains(PATH_WLSERVICE_STATUS)) {
            $z0 = true;
        }
        MCSLogger.log(TAG, "canProcess(" + $z0 + "):" + $r2);
        return $z0;
    }

    public void setHttpParams(IHttpLayer httpLayer) throws  {
    }

    public Response getAdditionalResponseData(int $i0) throws  {
        String $r2 = "";
        boolean $z0 = false;
        while (!$z0) {
            if (this.m_interruptFlag != null) {
                if (this.m_interruptFlag.isInterrupted()) {
                    break;
                }
            }
            try {
                if (this.m_connectionForStatusNotification.contains(Integer.valueOf($i0))) {
                    if (((List) this.m_connectionToStatusNotificationsData.get(Integer.valueOf($i0))).size() > 0) {
                        $z0 = true;
                        synchronized (this.m_lock) {
                            $r2 = (String) ((List) this.m_connectionToStatusNotificationsData.get(Integer.valueOf($i0))).get(0);
                            ((List) this.m_connectionToStatusNotificationsData.get(Integer.valueOf($i0))).remove(0);
                        }
                        if (!$z0) {
                            Thread.sleep(50);
                        }
                    }
                }
                SparseArray sparseArray = this.m_connectionID2NotificationsMap;
                SparseArray $r10 = sparseArray;
                if (sparseArray.get($i0) != null) {
                    synchronized (this.m_lock) {
                        sparseArray = this.m_connectionID2NotificationsMap;
                        $r10 = sparseArray;
                        List $r4 = (List) sparseArray.get($i0);
                        if ($r4.size() > 0) {
                            $z0 = true;
                            $r2 = (String) $r4.get(0);
                            $r4.remove(0);
                        }
                    }
                }
                if (!$z0) {
                    Thread.sleep(50);
                }
            } catch (Throwable $r1) {
                MCSLogger.log(TAG, "getAdditionalResponseData interrupted!", $r1);
                return null;
            }
        }
        if (this.m_interruptFlag == null || !this.m_interruptFlag.isInterrupted()) {
            $r2 = ("\n\ndata: " + $r2) + "\n\n";
            Response response = new Response();
            response.setIsLast(false);
            response.addHeaderField("Cache-Control", "no-cache");
            response.setContentType("text/event-stream");
            response.setSendOnlyData(true);
            response.setData($r2.getBytes());
            return response;
        }
        MCSLogger.log(TAG, "getAdditioanlResponseData interrupted!");
        return null;
    }

    public boolean interruptProcessing() throws  {
        if (this.m_interruptFlag != null) {
            this.m_interruptFlag.interrupt();
        }
        return true;
    }

    private SingleResponse handleStatusRequest(int $i0) throws  {
        if (!this.m_connectionForStatusNotification.contains(Integer.valueOf($i0))) {
            synchronized (this.m_lock) {
                this.m_connectionForStatusNotification.add(Integer.valueOf($i0));
                this.m_connectionToStatusNotificationsData.put(Integer.valueOf($i0), new ArrayList());
            }
        }
        SuccessResponse $r1 = new SuccessResponse("");
        $r1.setContentType("text/event-stream");
        $r1.setIsLast(false);
        $r1.addHeaderField("Cache-Control", "no-cache");
        $r1.addHeaderField("Access-Control-Allow-Origin", "*");
        return $r1;
    }

    private void clearRegistration(String $r1) throws  {
        synchronized (this.m_lock) {
            this.m_serviceNameMap.remove($r1);
            this.m_serviceNameToResourceMap.remove($r1);
            ServiceProxy $r5 = (ServiceProxy) this.m_serviceNameMap.get($r1);
            List<String> $r6 = (List) this.m_serviceNameToResourceMap.get($r1);
            if (!($r5 == null || $r6 == null)) {
                for (String unregisterFromNotification : $r6) {
                    $r5.unregisterFromNotification(unregisterFromNotification, this);
                }
            }
        }
    }

    public void onServiceRegistered(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2) throws  {
        clearRegistration($r1);
        String $r4 = ("{\"serviceName\":\"" + $r1 + "\"") + ",\"serviceProtocols\":[";
        for (String $r12 : $r2) {
            $r4 = $r4 + "\"" + $r12 + "\"";
        }
        $r12 = (($r4 + "],") + "\"serviceStatus\":\"REGISTERED\"") + "}";
        synchronized (this.m_lock) {
            List<Integer> $r22 = this.m_connectionForStatusNotification;
            for (Integer intValue : $r22) {
                ((List) this.m_connectionToStatusNotificationsData.get(Integer.valueOf(intValue.intValue()))).add($r12);
            }
        }
    }

    public void onServiceUnregistered(String $r1) throws  {
        clearRegistration($r1);
        $r1 = ((("{\"serviceName\":\"" + $r1 + "\"") + ",\"serviceProtocols\":[],") + "\"serviceStatus\":\"UNREGISTERED\"") + "}";
        synchronized (this.m_lock) {
            for (Integer intValue : this.m_connectionForStatusNotification) {
                ((List) this.m_connectionToStatusNotificationsData.get(Integer.valueOf(intValue.intValue()))).add($r1);
            }
        }
    }
}
