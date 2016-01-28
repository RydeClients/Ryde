package com.ryde.chris.ryde;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 1/23/2016.
 */
public class MessageListAdapter extends BaseAdapter {
    //if message type is of received
    public static final int MESSAGE_RECEIVED = 1;
    public static final int MESSAGE_SENT = 2;
    private List<Chat> messageList;
    private Query messagesQuery;
    private ChildEventListener mListener;
    private Activity myActivity;
    public MessageListAdapter(Activity theActivity, Query theQuery) {
        myActivity = theActivity;
        messageList = new ArrayList<Chat>();
        messagesQuery = theQuery;
        mListener = messagesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Map<String, String> model = (Map<String, String>)dataSnapshot.getValue();
                // Insert into the correct location, based on previousChildName
                messageList.add(new Chat(model.get("message"), model.get("author")));
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
    public void clear() {
        messageList.clear();
    }
    @Override
    public int getCount() {
        return messageList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mLayoutInflater = myActivity.getLayoutInflater();
        Chat showMessage = messageList.get(position);
        SharedPreferences sharedPreferences = myActivity.getSharedPreferences(LoginActivity.SHARED_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        if(showMessage.getAuthor().equals(sharedPreferences.getString(LoginActivity.USERNAME_KEY, ""))) {
            //hasnt been made or is wrong type(Sent vs Receive)
            if(convertView == null || convertView.getTag().equals(MESSAGE_RECEIVED)) {
                convertView = mLayoutInflater.inflate(R.layout.chat_item_sent, parent, false);
                convertView.setTag(MESSAGE_SENT);
            }
        } else {
            if(convertView == null|| convertView.getTag().equals(MESSAGE_SENT)) {
                convertView = mLayoutInflater.inflate(R.layout.chat_item_rcv, parent, false);
                convertView.setTag(MESSAGE_RECEIVED);
            }
        }
        populateWithMessage(showMessage, convertView);
        return convertView;
    }

    private void populateWithMessage(Chat messageToPopulate, View holder) {
        ((TextView) holder.findViewById(R.id.lbl1)).setText(messageToPopulate.getMessage());
        TextView authorTextView = (TextView) holder.findViewById(R.id.lbl2);
        if(authorTextView != null) {
            authorTextView.setText(messageToPopulate.getAuthor()+ ":");
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Chat getItem(int position) {
        return messageList.get(position);
    }
}
