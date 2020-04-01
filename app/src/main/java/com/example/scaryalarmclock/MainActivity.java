package com.example.scaryalarmclock;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
    mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
    mDrawerLayout.addDrawerListener(mToggle);
    NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
    mToggle.syncState();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setupDrawerContent(nvDrawer);
    showAlarm();
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            if(myFragment instanceof Alarm){
                super.onBackPressed();
            }
            else{
                showAlarm();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


 private void showAlarm(){
        myFragment = new Alarm();
        if(myFragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        }
    }

    Fragment myFragment = null;
    public void selectItemDrawer(MenuItem menuItem){
        Class fragmentClass;
        switch(menuItem.getItemId()){
            case R.id.timer:
                fragmentClass = Timer.class;
                break;
            case R.id.reminder:
                fragmentClass = Reminder.class;
                break;
            case R.id.settings:
                fragmentClass = Settings.class;
                break;
            default:
                fragmentClass = Alarm.class;

        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch(Exception e){
            e.printStackTrace();
        }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            mDrawerLayout.closeDrawers();
    }




    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                selectItemDrawer(item);
                return true;
            }
        });
    }
}