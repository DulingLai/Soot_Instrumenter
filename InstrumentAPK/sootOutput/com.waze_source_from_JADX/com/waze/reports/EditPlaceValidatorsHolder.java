package com.waze.reports;

import com.waze.NativeManager;
import com.waze.NativeManager.VenueFieldValidators;

public class EditPlaceValidatorsHolder {
    private static VenueFieldValidators mAllValidators = null;

    static class C24731 implements Runnable {
        C24731() {
        }

        public void run() {
            EditPlaceValidatorsHolder.mAllValidators = NativeManager.getInstance().venueProviderGetFieldValidators();
        }
    }

    public enum ValidatorType {
        Name,
        Street,
        HouseNumber,
        City,
        Description,
        PhoneNumber,
        URL
    }

    public static VenueFieldValidators getAllValidators() {
        if (mAllValidators == null) {
            NativeManager.Post(new C24731());
        }
        return mAllValidators;
    }

    public static String getValidator(ValidatorType pt) {
        if (mAllValidators == null) {
            return null;
        }
        switch (pt) {
            case Name:
                return mAllValidators.name;
            case Street:
                return mAllValidators.street;
            case HouseNumber:
                return mAllValidators.house_number;
            case City:
                return mAllValidators.city;
            case Description:
                return mAllValidators.description;
            case PhoneNumber:
                return mAllValidators.phone;
            case URL:
                return mAllValidators.url;
            default:
                return null;
        }
    }
}
