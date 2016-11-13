package com.neto.deolino.trabalhoandroid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.EventType;
import com.neto.deolino.trabalhoandroid.util.DateHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by deolino on 10/11/16.
 */
public class EventAdapter2 extends ArrayAdapter<Event> {

    public EventAdapter2(Context context, ArrayList<Event> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_event, parent, false);
        }

        TextView tvDate = (TextView) convertView.findViewById(R.id.tvEventDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvEventTime);
        ImageView ivEventType = (ImageView) convertView.findViewById(R.id.ivEventType);
        TextView tvStart = (TextView) convertView.findViewById(R.id.tvEventStart);
        TextView tvEnd = (TextView) convertView.findViewById(R.id.tvEventEnd);

        DateHelper dateHelper = new DateHelper();

        Date date = event.getDate();

        tvDate.setText(dateHelper.dateToString(date));
        tvTime.setText(dateHelper.timeToString(date));

        EventType.Type type = event.getType().getType();
        int img = (type==EventType.Type.HIKE) ? R.drawable.hike : (type==EventType.Type.RUN) ? R.drawable.running : R.drawable.bike;
        ivEventType.setImageResource(img);


        Log.d("EventAdapter", "StartName: " + event.getDeparture().getAddress());
        tvStart.setText(event.getDeparture().getAddress());
        tvEnd.setText(event.getArrival().getAddress());

        // Return the completed view to render on screen
        return convertView;
    }
}
