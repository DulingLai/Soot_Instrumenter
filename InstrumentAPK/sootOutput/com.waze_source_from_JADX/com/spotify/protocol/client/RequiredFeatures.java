package com.spotify.protocol.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RequiredFeatures {
    public static final List<String> FEATURES = Collections.unmodifiableList(Arrays.asList(new String[]{FEATURES_V1, FEATURES_V2, FEATURES_V3}));
    public static final String FEATURES_V1 = "com.spotify.features.v1";
    public static final String FEATURES_V2 = "com.spotify.features.v2";
    public static final String FEATURES_V3 = "com.spotify.features.v3";
    public static final List<String> NONE = Collections.emptyList();

    private RequiredFeatures() throws  {
    }
}
