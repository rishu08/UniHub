package com.example.rishabh.unihub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FirebaseAuth.AuthStateListener {
    FirebaseUser firebaseUser;
    static int flag_check_main=0;
    private static final int RC_SIGN_IN = 100;

    ShowcaseView showcaseView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sRef = getPreferences(Context.MODE_PRIVATE);
        int value = sRef.getInt("Main_sref",0);

        Log.e("TAG", "onCreateView: "+value );
        if (value == 0)
        {
            SharedPreferences.Editor editor = sRef.edit();
            editor.putInt("Main_sref",1).apply();
            Log.e("TAG", "onCreateView: /*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*" );
            new ShowcaseView.Builder(this)
                    .setTarget(new PointTarget(40,25))
                    .setContentTitle("MENU")
                    .setContentText("Press here to explore more items")
                    .useDecorViewAsParent()
                    .hideOnTouchOutside()
                    .build();
        }



//        if (flag_check_main==0)
//        {
//            new ShowcaseView.Builder(this)
//                    .setTarget(new PointTarget(40,25))
////                .setTarget(new ActionViewTarget(this, )
//                    .setContentTitle("MENU")
//                    .setContentText("Press here to explore more items")
//                    .useDecorViewAsParent()
//                    .hideOnTouchOutside()
//                    .build();
//
//            flag_check_main=1;
//        }
//        new ShowcaseView.Builder(this)
//                .setTarget(new PointTarget(40,25))
////                .setTarget(new ActionViewTarget(this, )
//                .setContentTitle("MENU")
//                .setContentText("Press here to explore more items")
//                .useDecorViewAsParent()
//                .hideOnTouchOutside()
//                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,new AboutActivity())
                .commit();


    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            Toast.makeText(this, "Press Again!", Toast.LENGTH_SHORT).show();
//            super.onBackPressed();

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);




        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_signout) {
            AuthUI.getInstance()
                    .signOut(MainActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setAvailableProviders(Arrays.asList(
                                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
                                            .setIsSmartLockEnabled(false)
                                            .build(),
                                    RC_SIGN_IN);

                        }
                    });
            return true;
        }
        if (id== R.id.action_about)
        {
            Intent i = new Intent(MainActivity.this,AboutUs.class);
            startActivity(i);

//            View aboutPage = new AboutPage(MainActivity.this)
//                    .isRTL(false)
//                    .setImage(R.mipmap.ic_admin_photo)
//                    .setDescription("RISHABH GUPTA \n\n UniHub app provides an easy access to college students in a safe and secure manner." +
//                            ("It notifies user about various college events, provide teacher's notes and also helps to keep personalised notes to track their activities"))
//                    .addGroup("For contribution or bug related issue contact.")
//                    .addEmail("rg081999@gmail.com","Email Us")
//                    .addFacebook("rishabhgupta.08","Connect via Facebook")
//                    .addGitHub("rishu08")
//                    .addInstagram("_rishabh_gupta")
//                    .create();
//
//            setContentView(aboutPage);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();


        if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new AboutActivity())
                    .commit();

        }
        else if (id == R.id.nav_assigment) {

//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragmentContainer,new UploadDocsActivity())
//                    .commit();


           Intent sub = new Intent(MainActivity.this,SubjectSelect.class);
           startActivity(sub);
           


        }
        else if (id == R.id.nav_notice) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new NoticeActivity())
                    .commit();

        }
        else if (id == R.id.nav_note) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new NotesActivity())
                    .commit();
            }
        else if (id == R.id.nav_chat)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new ChatActivity())
                    .commit();
        }
        else if (id == R.id.nav_youtube_video)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new YoutubeActivity())
                    .commit();
            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if (firebaseAuth.getCurrentUser() == null) {


            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);

        }
        else {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View headerview = navigationView.getHeaderView(0);


            ImageView  user_image = headerview.findViewById(R.id.header_image);
            TextView user_name = headerview.findViewById(R.id.header_name);
            TextView user_detail = headerview.findViewById(R.id.header_detail);


            final String name,email;
            if (firebaseAuth.getCurrentUser().getDisplayName() != null)
            {

                name = firebaseAuth.getCurrentUser().getDisplayName();
                email = firebaseAuth.getCurrentUser().getEmail();
                user_detail.setText(firebaseAuth.getCurrentUser().getEmail());
                Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(user_image);


                user_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this,UserInfo.class);
                        i.putExtra("name",name);
                        i.putExtra("email",email);
                        startActivity(i);
                    }
                });



            }
            else
            {
                name = firebaseAuth.getCurrentUser().getPhoneNumber();
            }

            Log.e("TAG", "onAuthStateChanged:"+name );
            user_name.setText(name);


        }


    }
}
