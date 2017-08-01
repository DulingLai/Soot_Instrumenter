package jp.pioneer.mbg.pioneerkit;

public class ExtCertifiedInfo {
    private String f333a;
    private String f334b;
    private String f335c;

    public ExtCertifiedInfo(String str, String str2, String str3) {
        this.f333a = str;
        this.f334b = str2;
        this.f335c = str3;
    }

    public String getCompanyName() {
        return this.f333a;
    }

    public String getPackageName() {
        return this.f334b;
    }

    public String getSecretKey() {
        return this.f335c;
    }
}
