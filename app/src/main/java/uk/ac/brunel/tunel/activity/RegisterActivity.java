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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignup, btnBack;
    private EditText userEmail, userPassword, userPasswordVal;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEmail = (EditText) findViewById(R.id.reg_email);
        userPassword = (EditText) findViewById(R.id.reg_pass);
        userPasswordVal = (EditText) findViewById(R.id.reg_pass_val);
        btnSignup = (Button) findViewById(R.id.reg_button);
        btnBack = (Button) findViewById(R.id.btn_back);

        mAuth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    private void registerUser() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String password_val = userPasswordVal.getText().toString().trim();


        //Check if the fields email and/or password are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter a valid email!",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password) || password.length()<6){
            Toast.makeText(this,"Please enter a valid password of min. 6 characters!",Toast
                    .LENGTH_LONG).show();
            return;
        }

        //Validate the password entered by the user
        if(!password_val.equals(password))
        {
            Toast.makeText(this,"Passwords don't match, please try again!",Toast.LENGTH_LONG).show();
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
        if(!(isStudentID.length() == 7 && isStudentID.contains(regex)))
        {
            Toast.makeText(this, "Email entered is incorrect! your student ID is 7 digits.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /*
                        Checking if user has successfully registered
                         */
                        if(task.isSuccessful()){

                            // If the user has successfully registered
                            // a verification email will be sent to them
                            userRef = FirebaseAuth.getInstance().getCurrentUser();
                            userRef.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,"Successfully registered! verify " +
                                                        "your email",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            // Launch login activity
                            Intent loginIntent = new Intent(
                                    RegisterActivity.this,
                                    SignInActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }else{

                            Toast.makeText(RegisterActivity.this,"Registration failed",
                                    Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignup)
        {
            registerUser();
        }

        if(v == btnBack)
        {
            finish();

        }

    }




}
