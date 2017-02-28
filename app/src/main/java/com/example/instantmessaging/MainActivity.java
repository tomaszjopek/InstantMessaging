package com.example.instantmessaging;

import android.content.Intent;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.email.*;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Message> adpter;
    private RelativeLayout activity_main;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        //checking if any user is logged in
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
        }
        else {
            Snackbar.make(activity_main, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_LONG).show();
        }

        displayMessage();
    }

    //displaying whole conversation
    private void displayMessage() {

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
}
