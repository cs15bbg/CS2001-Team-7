/*
 * Created by Mohamed Bushra on 20/02/17 16:47
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified 20/02/17 16:47.
 */

package uk.ac.brunel.tunel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import uk.ac.brunel.tunel.R;

public class ResetPassActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth authRef;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        userEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        authRef = FirebaseAuth.getInstance();
        btnBack.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        }

    public void resetPassword()
    {
        String email = userEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        authRef.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Toast.makeText(ResetPassActivity.this, "We have sent you instructions to reset" +
                        " your password!", Toast.LENGTH_SHORT).show();
                // Launch login activity
                Intent loginIntent = new Intent(ResetPassActivity.this, SignInActivity.class);
                startActivity(loginIntent);
                finish();
            } else
            {
                Toast.makeText(ResetPassActivity.this, "Failed to send reset email!",
                        Toast.LENGTH_SHORT).show();
            }

            progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if ( v == btnReset)
        {
            resetPassword();
        }

        if( v == btnBack)
        {
            finish();
        }

    }


}



