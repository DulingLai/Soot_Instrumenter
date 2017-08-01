package com.waze;

import dalvik.annotation.Signature;

public enum ResourceDownloadType {
    RES_DOWNLOAD_IMAGE(0),
    RES_DOWNLOAD_SOUND(1),
    RES_DOWNLOAD_CONFIFG(2),
    RES_DOWNLOAD_LANG(3),
    RES_DOWNLOAD_COUNTRY_SPECIFIC_IMAGES(4),
    RES_DOWNLOAD_SEARCH_CONF(5),
    RES_DOWNLOAD_LANG_TTS(7),
    RES_DOWNLOAD_AD_IMAGE(8),
    RES_DOWNLOAD_SHIELD(9),
    RES_DOWNLOAD_LANG_ASR(10),
    RES_DOWNLOAD_IMAGE_JAVA(11);
    
    private int value;

    private ResourceDownloadType(@Signature({"(I)V"}) int $i1) throws  {
        this.value = $i1;
    }

    public int getValue() throws  {
        return this.value;
    }
}
