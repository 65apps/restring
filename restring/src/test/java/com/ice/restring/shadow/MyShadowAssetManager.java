package com.ice.restring.shadow;


import android.content.res.AssetManager;

import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowLegacyAssetManager;

import java.util.LinkedHashMap;
import java.util.Map;

@Implements(AssetManager.class)
public class MyShadowAssetManager extends ShadowLegacyAssetManager {

    private Map<Integer, String> resourceEntryNames = new LinkedHashMap<>();

    @Override
    public CharSequence getResourceText(int id) {
        return "@" + id;
    }

    @Override
    public String getResourceEntryName(int resid) {
        if (resourceEntryNames.containsKey(resid)) {
            return resourceEntryNames.get(resid);
        }
        return super.getResourceEntryName(resid);
    }

    public void addResourceEntryNameForTesting(int resId, String stringName) {
        resourceEntryNames.put(resId, stringName);
    }
}
