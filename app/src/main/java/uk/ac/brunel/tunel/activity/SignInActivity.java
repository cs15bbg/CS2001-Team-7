/*
 * Created by Mohamed Bushra on 20/02/17 16:44
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified 20/02/17 16:43.
 */

package uk.ac.brunel.tunel.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.ac.brunel.tunel.R;

import static uk.ac.brunel.tunel.R.id.forgotpassword;
import static uk.ac.brunel.tunel.R.id.signup_button;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private Button btnSignup;
    private Button btnResetPass;
    private EditText userEmail;
    private EditText userPassword;
    private ProgressDialog pDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userEmail = (EditText) findViewById(R.id.log_email);
        userPassword = (EditText) findViewById(R.id.log_pass);
        btnLogin = (Button) findViewById(R.id.log_button);
        btnSignup = (Button) findViewById(signup_button);
        btnResetPass = (Button) findViewById(forgotpassword);

        btnLogin.setOnClickListener(this);
        btnResetPass.setOnClickListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(this);


    }
    private void userSignIn()
    {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        //Check if the fields email and/or password are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password) || password.length()<6){
            Toast.makeText(this,"Please enter a valid password",Toast.LENGTH_LONG).show();
            return;
        }

        String emailBrunel = email;
        String segment[] = emailBrunel.split("@");
        String isEmailBrunel = segment[1];
        String isStudentID = segment[0];
        String regex = "[0-9]+";

        //Validate that the user email ends with "@my.brunel.ac.uk"
        if(!isEmailBrunel.contains("my.brunel.ac.uk"))
        {
            Toast.makeText(this,"You haven't entered your Brunel email, please try again!",Toast.LENGTH_LONG).show();
            return;
        }

        //Validate the user email contains 7 characters before the "@"
        if(!(isStudentID.length() == 7) && !isStudentID.contains(regex))
        {
            Toast.makeText(this, "Email entered is incorrect! your student ID is 7 digits",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        pDialog.setMessage("Login in...");
        pDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pDialog.dismiss();
                        // If the user is verified
                        // we direct them to the forum activity
                        userRef = FirebaseAuth.getInstance().getCurrentUser();
                        if(task.isSuccessful() && userRef.isEmailVerified()){
                            Toast.makeText(SignInActivity.this,"Welcome back "+
                                    mAuth.getCurrentUser().getEmail(),
                                    Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),
                                    UserProfileActivity.class));
                        }

                        else if(!userRef.isEmailVerified())
                        {
                            Toast.makeText(SignInActivity.this,"Your email has not been verified!",
                                    Toast.LENGTH_LONG).show();
                        }

                        else if(!task.isSuccessful())
                        {
                            Toast.makeText(SignInActivity.this,"Login failed!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            userSignIn();
        }

        else if ( v == btnResetPass)
        {
            Intent resetPassIntent = new Intent(SignInActivity.this, ResetPassActivity.class);
            startActivity(resetPassIntent);
        }

        else if (v == btnSignup)
        {
            /* Setting a new intent which takes the user from the current screen
                to the next (Sign in --> Sign up screen)
                 */
            Intent RegisterIntent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(RegisterIntent);
        }


    }

}



