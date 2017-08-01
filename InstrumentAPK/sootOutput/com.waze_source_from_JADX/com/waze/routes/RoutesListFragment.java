package com.waze.routes;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Space;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.main.navigate.EventOnRoute;
import com.waze.main.navigate.MajorEventOnRoute;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AlternativeRoutesListener;
import com.waze.navigate.DriveToNativeManager.EventsOnRouteListener;
import com.waze.navigate.DriveToNativeManager.MajorEventsOnRouteListener;
import com.waze.strings.DisplayStrings;

public class RoutesListFragment extends Fragment {
    DriveToNativeManager mDtnm;
    protected EventOnRoute[] mEvents;
    private boolean mGotEvents = false;
    private boolean mGotMajorEvents = false;
    private boolean mGotRoutes = false;
    protected MajorEventOnRoute[] mMajorEvents;
    private NativeManager mNm;
    private RouteAdapter mRouteAdapter;
    private ListView mRouteList;
    private AlternativeRoute[] mRoutes;

    class C25741 implements AlternativeRoutesListener {
        C25741() {
        }

        public void onComplete(AlternativeRoute[] routes) {
            RoutesListFragment.this.mRoutes = routes;
            RoutesListFragment.this.mGotRoutes = true;
            RoutesListFragment.this.invalidateRoutes();
        }
    }

    class C25752 implements EventsOnRouteListener {
        C25752() {
        }

        public void onComplete(EventOnRoute[] events) {
            RoutesListFragment.this.mEvents = events;
            RoutesListFragment.this.mGotEvents = true;
            RoutesListFragment.this.invalidateRoutes();
        }
    }

    class C25763 implements MajorEventsOnRouteListener {
        C25763() {
        }

        public void onComplete(MajorEventOnRoute[] events) {
            RoutesListFragment.this.mMajorEvents = events;
            RoutesListFragment.this.mGotMajorEvents = true;
            RoutesListFragment.this.markClosure();
            RoutesListFragment.this.invalidateRoutes();
        }
    }

    class C25774 implements OnItemClickListener {
        C25774() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            RoutesListFragment.this.onRouteSelected(position - 1);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1283R.layout.fragment_routes_list, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mNm = NativeManager.getInstance();
        this.mDtnm = DriveToNativeManager.getInstance();
        this.mRouteAdapter = new RouteAdapter(getActivity());
        this.mDtnm.getAlternativeRoutes(new C25741());
        this.mDtnm.getEventsOnRoute(new C25752());
        this.mDtnm.getMajorEventsOnRoute(new C25763());
        this.mRouteList = (ListView) getView().findViewById(C1283R.id.routesList);
        this.mRouteList.addHeaderView(new Space(getActivity()));
        this.mRouteList.addFooterView(new Space(getActivity()));
        this.mRouteList.setAdapter(this.mRouteAdapter);
        this.mRouteList.setOnItemClickListener(new C25774());
    }

    void onRouteSelected(final int position) {
        if (this.mRoutes != null && position < this.mRoutes.length) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ALT_ROUTE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_VALUE_ROUTE_NUMBER, "" + position);
            if (this.mRoutes[position].closure) {
                MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNm.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), this.mNm.getLanguageString(DisplayStrings.DS_ALTERNATIVE_CLOSURE_SELECTED), false, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            RoutesListFragment.this.routeConfirmed(position);
                        }
                    }
                }, this.mNm.getLanguageString(DisplayStrings.DS_YES), this.mNm.getLanguageString(DisplayStrings.DS_NO), -1);
            } else {
                routeConfirmed(position);
            }
        }
    }

    private void routeConfirmed(int position) {
        this.mDtnm.selectAlternativeRoute(position);
        getActivity().finish();
    }

    private void invalidateRoutes() {
        if (this.mGotMajorEvents && this.mGotEvents && this.mGotRoutes) {
            this.mRouteAdapter.setEvents(this.mEvents);
            this.mRouteAdapter.setMajorEvents(this.mMajorEvents);
            this.mRouteAdapter.setRoutes(this.mRoutes);
            this.mRouteList.invalidateViews();
        }
    }

    private void markClosure() {
        if (this.mGotMajorEvents && this.mMajorEvents != null && this.mRoutes != null) {
            for (MajorEventOnRoute event : this.mMajorEvents) {
                if (event.alertType == 12) {
                    for (int i = 0; i < this.mRoutes.length; i++) {
                        if (event.alertRouteId == this.mRoutes[i].id) {
                            this.mRoutes[i].closure = true;
                            break;
                        }
                    }
                }
            }
        }
    }
}
