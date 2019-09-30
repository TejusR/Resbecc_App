package com.example.delta_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class homeactivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadfragment(new postfragment());
    }

    private boolean loadfragment(Fragment fragment){
        if(fragment!=null){
            Bundle bundle = new Bundle();
            bundle.putString("token",getIntent().getStringExtra("token"));
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer,fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;
        if(menuItem.getItemId()==R.id.navigation_home)
        {
            fragment=new postfragment();

        }
        if(menuItem.getItemId()==R.id.navigation_notifications)
        {
            fragment=new friendsfragment();
        }
        if(menuItem.getItemId()==R.id.navigation_dashboard)
        {
            fragment=new messagesfragment();
        }
        if(menuItem.getItemId()==R.id.navprofile)
        {
            fragment=new profilefragment();
        }
        return loadfragment(fragment);
    }
}
