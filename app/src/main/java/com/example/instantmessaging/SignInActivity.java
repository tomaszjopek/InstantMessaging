package com.example.instantmessaging;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText login;
    private TextInputEditText password;
    private FloatingActionButton logAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login = (TextInputEditText)findViewById(R.id.login_login);
        password = (TextInputEditText)findViewById(R.id.login_password);
        logAction = (FloatingActionButton) findViewById(R.id.login_send);

        logAction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.login_send:
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                break;
        }
    }
}
