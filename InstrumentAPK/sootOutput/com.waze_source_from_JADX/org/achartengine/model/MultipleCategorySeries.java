package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultipleCategorySeries implements Serializable {
    private List<String> mCategories = new ArrayList();
    private String mTitle;
    private List<String[]> mTitles = new ArrayList();
    private List<double[]> mValues = new ArrayList();

    public MultipleCategorySeries(String str) {
        this.mTitle = str;
    }

    public void add(String[] strArr, double[] dArr) {
        add(this.mCategories.size() + "", strArr, dArr);
    }

    public void add(String str, String[] strArr, double[] dArr) {
        this.mCategories.add(str);
        this.mTitles.add(strArr);
        this.mValues.add(dArr);
    }

    public void remove(int i) {
        this.mCategories.remove(i);
        this.mTitles.remove(i);
        this.mValues.remove(i);
    }

    public void clear() {
        this.mCategories.clear();
        this.mTitles.clear();
        this.mValues.clear();
    }

    public double[] getValues(int i) {
        return (double[]) this.mValues.get(i);
    }

    public String getCategory(int i) {
        return (String) this.mCategories.get(i);
    }

    public int getCategoriesCount() {
        return this.mCategories.size();
    }

    public int getItemCount(int i) {
        return ((double[]) this.mValues.get(i)).length;
    }

    public String[] getTitles(int i) {
        return (String[]) this.mTitles.get(i);
    }

    public XYSeries toXYSeries() {
        return new XYSeries(this.mTitle);
    }
}
