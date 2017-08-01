package com.waze.reports;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.VenueServices;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EditPlaceServicesFragment extends Fragment {
    public static final String SERVICES = "services";
    private static VenueServices mAllServices = null;
    private static HashMap<String, String> mServiceIconById = null;
    private static HashMap<String, String> mServiceNameById = null;
    private ViewGroup mLinesContainer;
    private NativeManager mNm;
    private HashSet<String> mSetServiceIds;
    private View f94r;

    static class C24701 implements Runnable {
        C24701() {
        }

        public void run() {
            EditPlaceServicesFragment.mAllServices = NativeManager.getInstance().venueProviderGetServices();
        }
    }

    class C24712 implements OnClickListener {
        C24712() {
        }

        public void onClick(View v) {
            ArrayList<String> servicesList = new ArrayList(EditPlaceServicesFragment.this.mSetServiceIds.size());
            for (int i = 0; i < EditPlaceServicesFragment.mAllServices.count; i++) {
                if (EditPlaceServicesFragment.this.mSetServiceIds.contains(EditPlaceServicesFragment.mAllServices.ids[i])) {
                    servicesList.add(EditPlaceServicesFragment.mAllServices.ids[i]);
                }
            }
            ((EditPlaceFlowActivity) EditPlaceServicesFragment.this.getActivity()).doneServices(servicesList);
        }
    }

    public void setServiceIds(HashSet<String> ids) {
        this.mSetServiceIds = ids;
    }

    public static VenueServices getServices() {
        if (mAllServices == null) {
            NativeManager.Post(new C24701());
        }
        return mAllServices;
    }

    public static String getServiceById(String id) {
        if (mServiceNameById == null) {
            if (mAllServices == null) {
                return null;
            }
            mServiceNameById = new HashMap();
            getServices();
            for (int i = 0; i < mAllServices.count; i++) {
                mServiceNameById.put(mAllServices.ids[i], mAllServices.names[i]);
            }
        }
        return (String) mServiceNameById.get(id);
    }

    public static String getServiceIconById(String id) {
        if (mServiceIconById == null) {
            if (mAllServices == null) {
                return null;
            }
            mServiceIconById = new HashMap();
            getServices();
            for (int i = 0; i < mAllServices.count; i++) {
                mServiceIconById.put(mAllServices.ids[i], mAllServices.icons[i]);
            }
        }
        return (String) mServiceIconById.get(id);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(getClass().getName() + ".mSetServiceIds", new ArrayList(this.mSetServiceIds));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(getClass().getName() + ".mSetServiceIds")) {
            this.mSetServiceIds = new HashSet(savedInstanceState.getStringArrayList(getClass().getName() + ".mSetServiceIds"));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mNm = NativeManager.getInstance();
        getServices();
        this.f94r = inflater.inflate(C1283R.layout.edit_place_services, container, false);
        setUpFragment();
        if (this.mSetServiceIds == null) {
            this.mSetServiceIds = new HashSet();
        }
        for (int i = 0; i < mAllServices.count; i++) {
            addLine(mAllServices.names[i], mAllServices.ids[i], this.mSetServiceIds.contains(mAllServices.ids[i]));
        }
        return this.f94r;
    }

    private void setUpFragment() {
        TitleBar titleBar = (TitleBar) this.f94r.findViewById(C1283R.id.theTitleBar);
        titleBar.init(getActivity(), this.mNm.getLanguageString(DisplayStrings.DS_SERVICES), 0);
        titleBar.setCloseImageResource(C1283R.drawable.confirm_icon);
        titleBar.setOnClickCloseListener(new C24712());
        this.mLinesContainer = (ViewGroup) this.f94r.findViewById(C1283R.id.editServicesLinesContainer);
    }

    public static int sortServiceIdsArray(String[] serviceIds, int size) {
        if (mAllServices == null) {
            return serviceIds.length;
        }
        int i;
        HashSet<String> serviceSet = new HashSet(size);
        for (i = 0; i < size; i++) {
            serviceSet.add(serviceIds[i]);
        }
        int j = 0;
        for (i = 0; i < mAllServices.count; i++) {
            if (serviceSet.contains(mAllServices.ids[i])) {
                serviceIds[j] = mAllServices.ids[i];
                j++;
            }
        }
        return j;
    }

    protected void addLine(String service, final String id, boolean isOn) {
        WazeSettingsView sv = new WazeSettingsView(getActivity(), false, 0, null, 2);
        sv.setValue(isOn);
        sv.setText(service);
        sv.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean bIsChecked) {
                if (bIsChecked) {
                    EditPlaceServicesFragment.this.mSetServiceIds.add(id);
                } else {
                    EditPlaceServicesFragment.this.mSetServiceIds.remove(id);
                }
            }
        });
        this.mLinesContainer.addView(sv);
        sv.getLayoutParams().height = (int) (80.0f * getResources().getDisplayMetrics().density);
    }

    public static String getServicesString(VenueData vd, String separator) {
        StringBuilder bob = new StringBuilder();
        for (int i = 0; i < vd.numServices; i++) {
            bob.append(getServiceById(vd.services[i]));
            bob.append(separator);
        }
        if (bob.length() > 0) {
            bob.delete(bob.length() - separator.length(), bob.length());
        }
        return bob.toString();
    }

    public static String getServicesString(VenueData vd) {
        return getServicesString(vd, "\n");
    }
}
