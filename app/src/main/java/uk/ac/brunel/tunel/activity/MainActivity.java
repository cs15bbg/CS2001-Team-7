/*
 * Created by Mohamed Bushra on 20/02/17 14:40
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified 20/02/17 14:11.
 */

package uk.ac.brunel.tunel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.ac.brunel.tunel.R;

import static uk.ac.brunel.tunel.R.id.sign_up;

public class MainActivity extends AppCompatActivity {

    private Button ButtonSignIn;
    private Button ButtonSignUp;
    private FirebaseAuth mAuth;
    private FirebaseUser userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseAuth.getInstance().getCurrentUser();

        // Check if user is already logged in & verified
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(MainActivity.this,"Welcome back "+ mAuth.getCurrentUser().getEmail(),
                    Toast.LENGTH_LONG).show();
            //If user is logged in, close this activity
            // and direct user to the forum
            finish();
            if(userRef.isEmailVerified()) {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
            }

            else
            {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        }

        //Assigning the 'Sign in' button to a click listener
        // so it takes the user to the next screen
        ButtonSignIn = (Button) findViewById(R.id.sign_in);
        ButtonSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Setting a new intent which takes the user from the current screen
                // to the next (Main --> Sign in screen)
                Intent SignInIntent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(SignInIntent);
            }

        });

        ButtonSignUp = (Button)findViewById(sign_up);
        ButtonSignUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Setting a new intent which takes the user from the current screen
                // to the next (Sign in --> Sign up screen)
                Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);

            }
        });
    }


}
