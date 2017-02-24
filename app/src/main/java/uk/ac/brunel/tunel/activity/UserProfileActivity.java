/*
 * Created by Mohamed Bushra on 20/02/17 14:40
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified 20/02/17 14:36.
 */

package uk.ac.brunel.tunel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.brunel.tunel.R;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText first_name;
    private EditText last_name;
    private TextView useremail;
    private EditText studentID;
    private Button btnLogout, btnSaveInfo;
    private FirebaseAuth authRef;
    private FirebaseUser userRef;
    private DatabaseReference databaseReference;
    private Spinner course;
    private Spinner course_year;
    private Spinner usertype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        useremail = (TextView) findViewById(R.id.view_user_email);
        course = (Spinner) findViewById(R.id.course_spinner);
        course_year = (Spinner) findViewById(R.id.courseyear_spinner);
        usertype = (Spinner) findViewById(R.id.gtapal_spinner);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        authRef = FirebaseAuth.getInstance();
        btnLogout.setOnClickListener(this);

        //Create an ArrayAdapter using the string array and a default spinner layout
        // Create an ArrayAdapter using the string array and a default spinner layout
        // Apply the adapter to the spinner
        ArrayAdapter<CharSequence> course_adapter = ArrayAdapter.createFromResource(this,
                R.array.course, android.R.layout.simple_spinner_item);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(course_adapter);

        ArrayAdapter<CharSequence> courseyear_adapter = ArrayAdapter.createFromResource(this,
                R.array.course_year, android.R.layout.simple_spinner_item);
        courseyear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_year.setAdapter(courseyear_adapter);

        ArrayAdapter<CharSequence> gtapal_adapter = ArrayAdapter.createFromResource(this,
                R.array.gta_pal, android.R.layout.simple_spinner_item);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(gtapal_adapter);

        //Checking if the user is not logged in

        if(authRef.getCurrentUser() == null){
            //If user is logged in, close this activity
            // and direct user to the sign in page
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        useremail = (TextView) findViewById(R.id.view_user_email);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        course = (Spinner) findViewById(R.id.course_spinner);
        course_year = (Spinner) findViewById(R.id.courseyear_spinner);
        usertype = (Spinner) findViewById(R.id.gtapal_spinner);
        studentID = (EditText) findViewById(R.id.student_ID);
        btnSaveInfo = (Button) findViewById(R.id.save_info);
        btnSaveInfo.setOnClickListener(this);

        userRef = authRef.getCurrentUser();
        useremail.setText("Hello "+ userRef.getEmail());

    }

    private void createUser()
    {
        String fname = first_name.getText().toString().trim();
        String lname = last_name.getText().toString().trim();
        String usercourse = course.getSelectedItem().toString().trim();
        String usercourse_year = course_year.getSelectedItem().toString().trim();
        String student_id = studentID.getText().toString().trim();
        String user_type = usertype.getSelectedItem().toString().trim();

        // creating user object
        UserInformation userInformation = new UserInformation(fname, lname, student_id, usercourse,
                usercourse_year, user_type);

        //Creating new user node, which returns the unique key value
        //new user node would be /users/$uid/
        userRef = authRef.getCurrentUser();

        // pushing userinformation to 'users' node using the userUID
        //If changes will be made by the user, we get the UID so no new node is created
        databaseReference.child(userRef.getUid()).setValue(userInformation);

        Toast.makeText(this, "Saving information....", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {

        if(v ==btnLogout) {

            authRef.signOut();
            finish();
            //Send user to sign in screen
            startActivity(new Intent(this, SignInActivity.class));

        }

        if(v == btnSaveInfo)
        {
            createUser();
        }

    }
}

