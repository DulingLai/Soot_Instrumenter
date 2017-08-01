package com.abaltatech.weblinkserver;

public class WLSettingNameValuePair {
    private String m_name;
    private String m_value;

    public WLSettingNameValuePair() throws  {
        setName("");
        this.m_value = "";
    }

    public WLSettingNameValuePair(String $r1, String $r2) throws  {
        setName($r1);
        this.m_value = $r2;
    }

    public String getValue() throws  {
        return this.m_value;
    }

    public void setValue(String $r1) throws  {
        this.m_value = $r1;
    }

    public String getName() throws  {
        return this.m_name;
    }

    public void setName(String $r1) throws  {
        this.m_name = $r1;
    }
}
