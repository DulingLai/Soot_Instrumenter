package com.waze.reports;

import com.waze.NativeManager;
import com.waze.NativeManager.VenueFieldPoints;

public class EditPlacePointsHolder {
    private static VenueFieldPoints mAllPoints = null;

    static class C24681 implements Runnable {
        C24681() {
        }

        public void run() {
            EditPlacePointsHolder.mAllPoints = NativeManager.getInstance().venueProviderGetFieldPoints();
        }
    }

    public enum PointsType {
        Images,
        Location,
        Name,
        Categories,
        Street,
        HouseNumber,
        City,
        Services,
        OpeningHours,
        Description,
        PhoneNumber,
        URL,
        CreatePlace
    }

    public static VenueFieldPoints getAllPoints() {
        if (mAllPoints == null) {
            NativeManager.Post(new C24681());
        }
        return mAllPoints;
    }

    public static int getPoints(PointsType pt) {
        if (mAllPoints == null) {
            return 0;
        }
        switch (pt) {
            case Images:
                return mAllPoints.images;
            case Name:
                return mAllPoints.name;
            case Categories:
                return mAllPoints.categories;
            case Street:
                return mAllPoints.street;
            case HouseNumber:
                return mAllPoints.house_number;
            case City:
                return mAllPoints.city;
            case Services:
                return mAllPoints.services;
            case OpeningHours:
                return mAllPoints.hours;
            case Description:
                return mAllPoints.description;
            case PhoneNumber:
                return mAllPoints.phone;
            case URL:
                return mAllPoints.url;
            case Location:
                return mAllPoints.location;
            case CreatePlace:
                return 3;
            default:
                return 0;
        }
    }
}
