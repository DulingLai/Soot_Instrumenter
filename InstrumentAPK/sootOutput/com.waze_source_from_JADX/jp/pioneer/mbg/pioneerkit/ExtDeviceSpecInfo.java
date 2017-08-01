package jp.pioneer.mbg.pioneerkit;

public class ExtDeviceSpecInfo {
    private int f336a = 0;
    private int f337b;
    private int f338c;
    private int f339d;
    private int f340e = 0;
    private int f341f = 0;

    public int getCanBusController() {
        return this.f341f;
    }

    public int getConnectedMode() {
        return this.f340e;
    }

    public int getExtDeviceID() {
        return this.f336a;
    }

    public int getLocationDevice() {
        return this.f338c;
    }

    public int getPointerNum() {
        return this.f337b;
    }

    public int getRemoteController() {
        return this.f339d;
    }

    public void setCanBusController(int i) {
        this.f341f = i;
    }

    public void setConnectedMode(int i) {
        this.f340e = i;
    }

    public void setExtDeviceID(int i) {
        this.f336a = i;
    }

    public void setLocationDevice(int i) {
        this.f338c = i;
    }

    public void setPointerNum(int i) {
        this.f337b = i;
    }

    public void setRemoteController(int i) {
        this.f339d = i;
    }
}
