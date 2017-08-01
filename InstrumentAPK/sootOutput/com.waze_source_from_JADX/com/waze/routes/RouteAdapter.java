package com.waze.routes;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.main.navigate.EventOnRoute;
import com.waze.main.navigate.MajorEventOnRoute;
import com.waze.strings.DisplayStrings;

public class RouteAdapter extends BaseAdapter implements ListAdapter {
    private Context context;
    private EventOnRoute[] events;
    private Handler handler = new Handler();
    private LayoutInflater inflater;
    private MajorEventOnRoute[] majorEvents;
    private NativeManager nativeManager;
    private AlternativeRoute[] routes;

    static class ViewHolder {
        public TextView description;
        public TextView distance;
        public ETATrafficBar etaTrafficBar;
        public TextView ferryLabel;
        public View ferryLayout;
        public View footer;
        public TextView header;
        public ImageView preferred;
        public TextView time;
        public TextView tollLabel;
        public View tollLayout;

        ViewHolder() {
        }
    }

    public RouteAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.nativeManager = AppService.getNativeManager();
    }

    public void setEvents(EventOnRoute[] events) {
        this.events = events;
    }

    public void setMajorEvents(MajorEventOnRoute[] events) {
        this.majorEvents = events;
    }

    public void setRoutes(AlternativeRoute[] routes) {
        this.routes = routes;
    }

    public int getCount() {
        if (this.routes == null) {
            return 0;
        }
        return this.routes.length;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return getRouteView(position, convertView);
    }

    private View getRouteView(int position, View convertView) {
        ViewHolder holder;
        String distanceText;
        String timeText;
        if (convertView == null || convertView.findViewById(C1283R.id.routeViaText) == null) {
            convertView = this.inflater.inflate(C1283R.layout.route_item, null);
            holder = new ViewHolder();
            holder.description = (TextView) convertView.findViewById(C1283R.id.routeViaText);
            holder.tollLayout = convertView.findViewById(C1283R.id.routeTollLayout);
            holder.tollLabel = (TextView) convertView.findViewById(C1283R.id.routeTollLabel);
            holder.ferryLayout = convertView.findViewById(C1283R.id.routeFerryLayout);
            holder.ferryLabel = (TextView) convertView.findViewById(C1283R.id.routeFerryLabel);
            holder.distance = (TextView) convertView.findViewById(C1283R.id.routeDistance);
            holder.time = (TextView) convertView.findViewById(C1283R.id.routeTime);
            holder.preferred = (ImageView) convertView.findViewById(C1283R.id.routePreferredIcon);
            holder.etaTrafficBar = (ETATrafficBar) convertView.findViewById(C1283R.id.etaTrafficBar);
            holder.header = (TextView) convertView.findViewById(C1283R.id.routeCurrentTitle);
            holder.footer = convertView.findViewById(C1283R.id.routeFooter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        View myView = convertView;
        String viaText = this.nativeManager.getLanguageString(DisplayStrings.DS_VIA);
        final AlternativeRoute route = this.routes[position];
        holder.description.setText(viaText + ": " + this.nativeManager.getLanguageString(route.description));
        if (route.distanceRound < 100) {
            distanceText = route.distanceRound + FileUploadSession.SEPARATOR + route.distanceTenths;
        } else {
            distanceText = "" + route.distanceRound;
        }
        holder.distance.setText(distanceText + " " + route.distanceUnits);
        int minutes = route.time / 60;
        if (minutes > 60) {
            timeText = String.format("%d:%02d %s", new Object[]{Integer.valueOf(minutes / 60), Integer.valueOf(minutes % 60), DisplayStrings.displayString(423)});
        } else {
            timeText = minutes + DisplayStrings.displayString(DisplayStrings.DS_MIN);
        }
        holder.time.setText(timeText);
        if (route.preferred) {
            holder.preferred.setVisibility(0);
        } else {
            holder.preferred.setVisibility(8);
        }
        View view = holder.footer;
        int i = (route.toll || route.ferry) ? 0 : 8;
        view.setVisibility(i);
        if (route.toll) {
            holder.tollLayout.setVisibility(0);
            holder.tollLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ALT_ROUTE_LABEL_TOLL));
        } else {
            holder.tollLayout.setVisibility(4);
        }
        if (route.ferry) {
            holder.ferryLayout.setVisibility(0);
            holder.ferryLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ETA_SCREEN_FERRY));
        } else {
            holder.ferryLayout.setVisibility(8);
        }
        if (route.routeColor == NativeManager.getInstance().getAltRoutesCurrentRouteColorNTV()) {
            holder.header.setVisibility(0);
            holder.header.setText(DisplayStrings.displayString(DisplayStrings.DS_ALT_ROUTE_LABEL_CURRENT));
        } else {
            holder.header.setVisibility(8);
        }
        populateMajorEventsOnRoute(myView, route);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                holder.etaTrafficBar.populateEventsOnRoute(route, RouteAdapter.this.events, route.time, RouteAdapter.this.handler, true);
            }
        }, 500);
        return convertView;
    }

    private void populateMajorEventsOnRoute(View convertView, AlternativeRoute route) {
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
