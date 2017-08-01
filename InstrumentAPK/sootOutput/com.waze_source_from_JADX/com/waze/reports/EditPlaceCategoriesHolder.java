package com.waze.reports;

import com.waze.NativeManager;
import com.waze.NativeManager.VenueCategory;
import com.waze.settings.SettingsValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class EditPlaceCategoriesHolder {
    private static VenueCategory[] mAllCategories = null;
    private static HashMap<String, String> mCategoryIconById = null;
    private static HashMap<String, List<String>> mCategoryIdForParent = null;
    private static HashMap<String, String> mCategoryNameById = null;
    private static ArrayList<String> mCategoryParentIds = null;
    private static SettingsValue[] mSettingValues;

    static class C24501 implements Runnable {
        C24501() {
        }

        public void run() {
            EditPlaceCategoriesHolder.mAllCategories = NativeManager.getInstance().venueProviderGetCategories();
        }
    }

    public static VenueCategory[] getCategories() {
        if (mAllCategories == null) {
            NativeManager.Post(new C24501());
        }
        return mAllCategories;
    }

    public static String getCategoryById(String id) {
        if (mCategoryNameById == null) {
            if (mAllCategories == null) {
                return null;
            }
            mCategoryNameById = new HashMap();
            for (int i = 0; i < mAllCategories.length; i++) {
                mCategoryNameById.put(mAllCategories[i].id, mAllCategories[i].label);
            }
        }
        return (String) mCategoryNameById.get(id);
    }

    public static String getCategoryIconById(String id) {
        if (mCategoryIconById == null) {
            if (mAllCategories == null) {
                return null;
            }
            mCategoryIconById = new HashMap();
            for (int i = 0; i < mAllCategories.length; i++) {
                mCategoryIconById.put(mAllCategories[i].id, mAllCategories[i].icon);
            }
        }
        return (String) mCategoryIconById.get(id);
    }

    public static SettingsValue[] getCategoryValues() {
        if (mCategoryIdForParent == null) {
            if (mAllCategories == null) {
                return null;
            }
            mCategoryIdForParent = new HashMap();
            mCategoryParentIds = new ArrayList();
            for (int i = 0; i < mAllCategories.length; i++) {
                String id = mAllCategories[i].id;
                String parent = mAllCategories[i].parent;
                if (parent == null || parent.isEmpty()) {
                    mCategoryParentIds.add(id);
                    mCategoryIdForParent.put(id, new ArrayList());
                } else {
                    ((List) mCategoryIdForParent.get(parent)).add(id);
                }
            }
        }
        if (mSettingValues == null) {
            int idx = 0;
            mSettingValues = new SettingsValue[mAllCategories.length];
            Iterator it = mCategoryParentIds.iterator();
            while (it.hasNext()) {
                String parentId = (String) it.next();
                mSettingValues[idx] = new SettingsValue(parentId, getCategoryById(parentId), false);
                mSettingValues[idx].isHeader = true;
                mSettingValues[idx].iconName = getCategoryIconById(parentId);
                idx++;
                for (String childId : (List) mCategoryIdForParent.get(parentId)) {
                    mSettingValues[idx] = new SettingsValue(childId, getCategoryById(childId), false);
                    idx++;
                }
            }
        } else {
            for (SettingsValue sv : mSettingValues) {
                sv.isSelected = false;
                sv.rank = 0.0f;
            }
        }
        return mSettingValues;
    }

    public static int sortCategoryIdsArray(String[] categoryIds) {
        if (mAllCategories == null) {
            return categoryIds.length;
        }
        HashSet<String> categorySet = new HashSet(Arrays.asList(categoryIds));
        int j = 0;
        for (int i = 0; i < mAllCategories.length; i++) {
            if (categorySet.contains(mAllCategories[i].id)) {
                categoryIds[j] = mAllCategories[i].id;
                j++;
            }
        }
        return j;
    }
}
