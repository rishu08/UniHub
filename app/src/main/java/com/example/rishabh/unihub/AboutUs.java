package com.example.rishabh.unihub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        View aboutPage = new AboutPage(AboutUs.this)
                .isRTL(false)
                .setImage(R.mipmap.ic_admin_photo)
                .setDescription("RISHABH GUPTA \n\n UniHub app provides an easy access to college students in a safe and secure manner." +
                        ("It notifies user about various college events, provide teacher's notes and also helps to keep personalised notes to track their activities"))
                .addGroup("For contribution or bug related issue contact.")
                .addEmail("rg081999@gmail.com","Email Us")
                .addFacebook("rishabhgupta.08","Connect via Facebook")
                .addGitHub("rishu08")
                .addInstagram("_rishabh_gupta")
                .create();

        setContentView(aboutPage);


    }
}
