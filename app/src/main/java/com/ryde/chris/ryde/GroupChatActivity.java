package com.ryde.chris.ryde;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;


public class GroupChatActivity extends AppCompatActivity {
    private static final String FIREBASE_URL = "https://shining-heat-1936.firebaseio.com";

    private Firebase mFireBase;
    private EditText messageToSend;
    private MessageListAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        mFireBase = new Firebase(FIREBASE_URL).child("chat");
        messageToSend = (EditText)findViewById(R.id.message_to_send);
        messageAdapter = new MessageListAdapter(this, mFireBase.limitToFirst(50));
        messageToSend.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ListView messagesList  = (ListView)findViewById(R.id.message_list);
        messagesList.setAdapter(messageAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        messageAdapter.clear();
    }

    private void sendMessage() {
        String textToSend = messageToSend.getText().toString();
        if (!textToSend.equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
            String userName = sharedPreferences.getString(LoginActivity.USERNAME_KEY, "");
            // Create our 'model', a Chat object
            Chat message = new Chat(textToSend, userName);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFireBase.push().setValue(message);
            messageToSend.setText("");
        }
    }
}
