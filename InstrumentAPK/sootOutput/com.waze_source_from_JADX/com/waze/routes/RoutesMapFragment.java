package com.waze.routes;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.main.navigate.MajorEventOnRoute;
import com.waze.map.MapView;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AlternativeRoutesListener;
import com.waze.navigate.DriveToNativeManager.MajorEventsOnRouteListener;
import com.waze.navigate.NavigateNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.tabs.RoutesTabBar;
import com.waze.view.tabs.RoutesTabBar.TabTransitionListener;

public class RoutesMapFragment extends Fragment {
    private DriveToNativeManager driveToNativeManager;
    private MapView mapView;
    private NativeManager nativeManager;
    private NavigateNativeManager navNativeManager;
    protected AlternativeRoute[] routes;
    private int selected = -1;

    class C25801 implements OnClickListener {

        class C25791 implements DialogInterface.OnClickListener {
            C25791() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    RoutesMapFragment.this.routeConfirmed();
                }
            }
        }

        C25801() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ALT_ROUTE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_VALUE_ROUTE_NUMBER, "" + RoutesMapFragment.this.selected);
            if (RoutesMapFragment.this.routes == null || RoutesMapFragment.this.routes.length <= RoutesMapFragment.this.selected || !RoutesMapFragment.this.routes[RoutesMapFragment.this.selected].closure) {
                RoutesMapFragment.this.routeConfirmed();
            } else {
                MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(RoutesMapFragment.this.nativeManager.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), RoutesMapFragment.this.nativeManager.getLanguageString(DisplayStrings.DS_ALTERNATIVE_CLOSURE_SELECTED), false, new C25791(), RoutesMapFragment.this.nativeManager.getLanguageString(DisplayStrings.DS_YES), RoutesMapFragment.this.nativeManager.getLanguageString(DisplayStrings.DS_NO), -1);
            }
        }
    }

    class C25812 implements AlternativeRoutesListener {
        C25812() {
        }

        public void onComplete(AlternativeRoute[] routes) {
            RoutesMapFragment.this.routes = routes;
            RoutesMapFragment.this.populateData(0, true);
        }
    }

    class C25823 implements MajorEventsOnRouteListener {
        C25823() {
        }

        public void onComplete(MajorEventOnRoute[] events) {
            if (events != null) {
                for (MajorEventOnRoute event : events) {
                    if (event.alertType == 12) {
                        for (int i = 0; i < RoutesMapFragment.this.routes.length; i++) {
                            if (event.alertRouteId == RoutesMapFragment.this.routes[i].id) {
                                RoutesMapFragment.this.routes[i].closure = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1283R.layout.routes_map, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nativeManager = AppService.getNativeManager();
        this.driveToNativeManager = DriveToNativeManager.getInstance();
        this.navNativeManager = NavigateNativeManager.instance();
        if (savedInstanceState != null) {
            Parcelable[] rts = savedInstanceState.getParcelableArray(getClass().getName() + ".routes");
            if (rts != null) {
                this.routes = new AlternativeRoute[rts.length];
                for (int i = 0; i < rts.length; i++) {
                    this.routes[i] = (AlternativeRoute) rts[i];
                }
                this.selected = savedInstanceState.getInt(getClass().getName() + ".selected");
            }
        }
        this.mapView = (MapView) getView().findViewById(C1283R.id.routesMapView);
        ((TextView) getView().findViewById(C1283R.id.routesMapGoText)).setText(this.nativeManager.getLanguageString(409).toUpperCase());
        getView().findViewById(C1283R.id.routesMapGoText).setOnClickListener(new C25801());
        if (this.routes == null) {
            this.driveToNativeManager.getAlternativeRoutes(new C25812());
            this.driveToNativeManager.getMajorEventsOnRoute(new C25823());
        } else {
            populateData(this.selected, false);
        }
        Resources res = getResources();
        final int titleIdleColor = res.getColor(C1283R.color.Dark);
        final int titleSelectedColor = res.getColor(C1283R.color.White);
        final int subIdleColor = res.getColor(C1283R.color.Light);
        final int subSelectedColor = res.getColor(C1283R.color.BlueDeep);
        ((RoutesTabBar) getView().findViewById(C1283R.id.RoutesTabBar)).setTabTransitionListener(new TabTransitionListener() {
            public void onUpdate(View v, float i) {
                ((TextView) v.findViewById(C1283R.id.routesMapTabTitle)).setTextColor(AnimationUtils.mixColors(titleSelectedColor, titleIdleColor, i));
                ((TextView) v.findViewById(C1283R.id.routesMapTabSub)).setTextColor(AnimationUtils.mixColors(subSelectedColor, subIdleColor, i));
            }
        });
    }

    private void routeConfirmed() {
        this.driveToNativeManager.selectAlternativeRoute(this.selected);
        getActivity().setResult(-1);
        getActivity().finish();
    }

    private String getRouteTimeString(int seconds) {
        int minutes = seconds / 60;
        if (minutes <= 60) {
            return minutes + DisplayStrings.displayString(DisplayStrings.DS_MIN);
        }
        return String.format("%d:%02d %s", new Object[]{Integer.valueOf(minutes / 60), Integer.valueOf(minutes % 60), DisplayStrings.displayString(423)});
    }

    private void populateData(int position, boolean highlightAll) {
        if (getView() != null) {
            final RoutesTabBar tabBar = (RoutesTabBar) getView().findViewById(C1283R.id.RoutesTabBar);
            tabBar.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View toSelect = null;
            for (int i = 0; i < this.routes.length; i++) {
                String distanceText;
                View child = (LinearLayout) inflater.inflate(C1283R.layout.routes_map_tab, tabBar, false);
                if (tabBar.getOrientation() == 0) {
                    child.setOrientation(1);
                } else {
                    child.setOrientation(0);
                }
                TextView timeLabel = (TextView) child.findViewById(C1283R.id.routesMapTabTitle);
                TextView distanceLabel = (TextView) child.findViewById(C1283R.id.routesMapTabSub);
                timeLabel.setTextColor(this.routes[i].routeColor);
                distanceLabel.setTextColor(this.routes[i].routeColor);
                timeLabel.setText(getRouteTimeString(this.routes[i].time));
                if (this.routes[i].distanceRound < 100) {
                    distanceText = this.routes[i].distanceRound + FileUploadSession.SEPARATOR + this.routes[i].distanceTenths;
                } else {
                    distanceText = "" + this.routes[i].distanceRound;
                }
                distanceLabel.setText(distanceText + " " + this.routes[i].distanceUnits);
                tabBar.addView(child);
                if (i == position) {
                    toSelect = child;
                }
                final int finalI = i;
                child.setTag(this.routes[i]);
                child.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        tabBar.setSelected(v);
                        RoutesMapFragment.this.onSelect(finalI);
                    }
                });
            }
            if (toSelect != null) {
                tabBar.setSelected(toSelect);
                onSelect(position);
            }
            if (highlightAll) {
                this.navNativeManager.SelectRoute(0);
            } else {
                this.navNativeManager.SelectRoute(position);
            }
        }
    }

    private void onSelect(int position) {
        int i = 0;
        if (this.routes.length > position) {
            int i2;
            if (getResources().getConfiguration().orientation == 2) {
            }
            this.selected = position;
            this.navNativeManager.SelectRoute(this.selected);
            getView().findViewById(C1283R.id.routesMapGoText).setVisibility(0);
            getView().findViewById(C1283R.id.routeVia).setVisibility(0);
            String viaStr = this.nativeManager.getLanguageString(DisplayStrings.DS_VIA);
            if (this.nativeManager.getLanguageRtl()) {
                ((TextView) getView().findViewById(C1283R.id.routeVia)).setGravity(5);
            } else {
                ((TextView) getView().findViewById(C1283R.id.routeVia)).setGravity(3);
            }
            ((TextView) getView().findViewById(C1283R.id.routeVia)).setText(viaStr + ": " + this.routes[this.selected].description);
            View findViewById = getView().findViewById(C1283R.id.routesMapFerry);
            if (this.routes[this.selected].ferry) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            findViewById.setVisibility(i2);
            View findViewById2 = getView().findViewById(C1283R.id.routesMapToll);
            if (!this.routes[this.selected].toll) {
                i = 8;
            }
            findViewById2.setVisibility(i);
        }
    }

    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    public void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(getClass().getName() + ".routes", this.routes);
        outState.putInt(getClass().getName() + ".selected", this.selected);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
