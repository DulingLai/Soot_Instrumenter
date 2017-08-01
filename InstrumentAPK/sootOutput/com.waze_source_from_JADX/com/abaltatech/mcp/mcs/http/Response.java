package com.abaltatech.mcp.mcs.http;

public class Response {
    public String[] AdditionalHeaders;
    public int Code;
    public String ContentType;
    public byte[] Data;
    public boolean IsLast;
    public String Phrase;
    public boolean SendOnlyData;

    public Response(String $r1, int $i0, String $r2, byte[] $r3, boolean $z0) throws  {
        this.Phrase = "";
        this.Code = 0;
        this.ContentType = "";
        this.Data = new byte[0];
        this.IsLast = true;
        this.SendOnlyData = false;
        this.AdditionalHeaders = null;
        if ($r1 == null) {
            $r1 = "";
        }
        this.Phrase = $r1;
        this.Code = $i0;
        if ($r2 == null) {
            $r2 = "";
        }
        this.ContentType = $r2;
        if ($r3 == null) {
            $r3 = new byte[0];
        }
        this.Data = $r3;
        this.IsLast = $z0;
    }

    public Response() throws  {
        this.Phrase = "";
        this.Code = 0;
        this.ContentType = "";
        this.Data = new byte[0];
        this.IsLast = true;
        this.SendOnlyData = false;
        this.AdditionalHeaders = null;
    }

    public String[] getAdditionalHeaders() throws  {
        return this.AdditionalHeaders;
    }

    public void addAdditionalHeaders(String $r1) throws  {
        if ($r1 == null) {
            return;
        }
        if (this.AdditionalHeaders == null) {
            this.AdditionalHeaders = new String[]{$r1};
            return;
        }
        String[] $r2 = new String[(this.AdditionalHeaders.length + 1)];
        System.arraycopy(this.AdditionalHeaders, 0, $r2, 0, this.AdditionalHeaders.length);
        $r2[$r2.length - 1] = $r1;
        this.AdditionalHeaders = $r2;
    }

    public void addHeaderField(String $r1, String $r2) throws  {
        if ($r1 != null && $r2 != null) {
            $r2 = String.format("%s: %s", new Object[]{$r1, $r2});
            if (this.AdditionalHeaders == null) {
                addAdditionalHeaders($r2);
                return;
            }
            int $i0 = -1;
            for (int $i1 = 0; $i1 < this.AdditionalHeaders.length; $i1++) {
                if (this.AdditionalHeaders[$i1].contains($r1)) {
                    $i0 = $i1;
                    break;
                }
            }
            if ($i0 != -1) {
                this.AdditionalHeaders[$i0] = $r2;
            } else {
                addAdditionalHeaders($r2);
            }
        }
    }

    public void addAdditionalHeaders(String[] $r1) throws  {
        if ($r1 != null && $r1.length > 0) {
            if (this.AdditionalHeaders == null) {
                this.AdditionalHeaders = $r1;
                return;
            }
            String[] $r2 = new String[(this.AdditionalHeaders.length + $r1.length)];
            System.arraycopy(this.AdditionalHeaders, 0, $r2, 0, this.AdditionalHeaders.length);
            System.arraycopy($r1, 0, $r2, this.AdditionalHeaders.length, $r1.length);
            this.AdditionalHeaders = $r2;
        }
    }

    public String getPhrase() throws  {
        return this.Phrase;
    }

    public int getCode() throws  {
        return this.Code;
    }

    public String getContentType() throws  {
        return this.ContentType;
    }

    public byte[] getData() throws  {
        return this.Data;
    }

    public boolean getIsLast() throws  {
        return this.IsLast;
    }

    public boolean getSendOnlyData() throws  {
        return this.SendOnlyData;
    }

    public void setSendOnlyData(boolean $z0) throws  {
        this.SendOnlyData = $z0;
    }

    public void setIsLast(boolean $z0) throws  {
        this.IsLast = $z0;
    }

    public void setContentType(String $r1) throws  {
        this.ContentType = $r1;
    }

    public void setData(byte[] $r1) throws  {
        if ($r1 == null) {
            $r1 = new byte[0];
        }
        this.Data = $r1;
    }
}
