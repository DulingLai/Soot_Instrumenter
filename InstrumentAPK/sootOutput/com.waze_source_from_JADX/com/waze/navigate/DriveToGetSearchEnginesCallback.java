package com.waze.navigate;

import dalvik.annotation.Signature;
import java.util.List;

public interface DriveToGetSearchEnginesCallback {
    void getSearchEnginesCallback(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/navigate/SearchEngine;", ">;)V"}) List<SearchEngine> list) throws ;
}
