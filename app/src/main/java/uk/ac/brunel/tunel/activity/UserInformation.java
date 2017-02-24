/*
 * Created by Mohamed Bushra on 20/02/17 14:40
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified 19/02/17 12:07.
 */

package uk.ac.brunel.tunel.activity;

/**
 * Created by Mohamed on 07/02/2017.
 */

public class UserInformation {

    public String first_name;
    public String last_name;
    public String studentID;
    public String course;
    public String course_year;
    public String usertype;

    public UserInformation(){

    }

    public UserInformation(String first_name, String last_name, String studentID, String course,
                           String course_year, String usertype) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.studentID = studentID;
        this.course = course;
        this.course_year = course_year;
        this.usertype = usertype;
    }
}
