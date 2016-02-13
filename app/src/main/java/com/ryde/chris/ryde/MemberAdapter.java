package com.ryde.chris.ryde;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 2/12/2016.
 */

public class MemberAdapter extends ArrayAdapter<User>{
    private int resource;
    public MemberAdapter(Context context, int resource, List<User> items) {
        super(context, resource, items);
        this.resource = resource;
    }
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View memberView = convertView;
        if (memberView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            memberView = vi.inflate(resource, null);
        }
        User user = getItem(position);
        ((TextView)memberView.findViewById(R.id.memberName)).setText(user.getName());
        ((ImageView)memberView.findViewById(R.id.profileImage)).setImageResource(user.getProfileImage());
        return memberView;
    }
}