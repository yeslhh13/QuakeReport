package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gowon on 2017-02-01.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCAL_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Earthquake currentEarthquake = getItem(position);
        Date date = new Date(currentEarthquake.getTimeInMilliseconds());

        magnitudeColor(viewHolder.mMagnitudeView, currentEarthquake.getMagnitude());

        viewHolder.mMagnitudeView.setText(decimalFormatter(currentEarthquake.getMagnitude()));
        viewHolder.mMetersView.setText(separateLocation(currentEarthquake.getLocation())[0]);
        viewHolder.mLocationView.setText(separateLocation(currentEarthquake.getLocation())[1]);
        viewHolder.mDateView.setText(dateFormatter(date));
        viewHolder.mTimeView.setText(timeFormatter(date));

        return convertView;
    }

    public String decimalFormatter(double input) {
        return new DecimalFormat("0.0").format(input);
    }

    public void magnitudeColor(View view, double input) {
        GradientDrawable magnitudeCircle = (GradientDrawable) view.getBackground();
        int magnitudeColor = getMagnitudeColor(input);
        magnitudeCircle.setColor(magnitudeColor);
    }

    public int getMagnitudeColor(double magnitude) {
        int colorResourceId;
        int magnitudeInt = (int) Math.floor(magnitude);
        switch (magnitudeInt) {
            case 0:
            case 1:
                colorResourceId = R.color.magnitude1;
                break;
            case 2:
                colorResourceId = R.color.magnitude2;
                break;
            case 3:
                colorResourceId = R.color.magnitude3;
                break;
            case 4:
                colorResourceId = R.color.magnitude4;
                break;
            case 5:
                colorResourceId = R.color.magnitude5;
                break;
            case 6:
                colorResourceId = R.color.magnitude6;
                break;
            case 7:
                colorResourceId = R.color.magnitude7;
                break;
            case 8:
                colorResourceId = R.color.magnitude8;
                break;
            case 9:
                colorResourceId = R.color.magnitude9;
                break;
            case 10:
                colorResourceId = R.color.magnitude10plus;
                break;
            default:
                colorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colorResourceId);
    }

    public String dateFormatter(Date date) {
        return new SimpleDateFormat("LLL dd, yyyy", Locale.US).format(date);
    }

    public String timeFormatter(Date date) {
        return new SimpleDateFormat("h:mm a", Locale.US).format(date);
    }

    public String[] separateLocation(String rawString) {
        if (rawString.contains(LOCAL_SEPARATOR)) {
            String[] parts = rawString.split(LOCAL_SEPARATOR);
            parts[0] += LOCAL_SEPARATOR;
            return parts;
        } else {
            String[] parts = {getContext().getString(R.string.near_the), rawString};
            return parts;
        }
    }

    static class ViewHolder {
        @BindView(R.id.magnitude_text_view)
        TextView mMagnitudeView;
        @BindView(R.id.meters_text_view)
        TextView mMetersView;
        @BindView(R.id.location_text_view)
        TextView mLocationView;
        @BindView(R.id.date_text_view)
        TextView mDateView;
        @BindView(R.id.time_text_view)
        TextView mTimeView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
