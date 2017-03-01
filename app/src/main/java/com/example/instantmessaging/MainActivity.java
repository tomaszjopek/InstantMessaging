package com.example.instantmessaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;

import com.example.instantmessaging.Model.Message;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Message> adapter;
    private RelativeLayout activity_main;
    private FloatingActionButton fab;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);

        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        input = (EditText) findViewById(R.id.input_text);

        //checking if any user is logged in
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
        }
        else {
            Snackbar.make(activity_main, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_LONG).show();
        }

        fab.setOnClickListener(view -> {
            Message tmpMessage = new Message(input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
            FirebaseDatabase.getInstance().getReference().push().setValue(tmpMessage);
            input.setText("");
            displayMessage();
        });

        displayMessage();
    }

    //displaying whole conversation
    private void displayMessage() {
        ListView listView = (ListView) findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessage());
                messageUser.setText(model.getUser());
                messageTime.setText(DateFormat.format("HH:mm:ss dd-MM-yyyy", model.getTime()));
            }
        };

        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Snackbar.make(activity_main, "Successfully logged in!", Snackbar.LENGTH_LONG).show();
                displayMessage();
            }
            else {
                Snackbar.make(activity_main, "Couldn't sign in. Try later.", Snackbar.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_sign_out: FirebaseAuth.getInstance().signOut();
                break;
        }

        return true;
    }
}
