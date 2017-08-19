package it.raqb.androidapp.androhash;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.angads25.filepicker.view.FilePickerDialog;

import it.raqb.androidapp.androhash.fragments.HashingPageFragment;
import it.raqb.androidapp.androhash.fragments.MainPageFragment;
import it.raqb.androidapp.androhash.fragments.ResultPageFragment;
import it.raqb.androidapp.androhash.helpers.FilePickerHelper;
import it.raqb.androidapp.androhash.views.LockedViewPager;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;

    private LockedViewPager vPager;

    private PagerAdapter vPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = (LockedViewPager) findViewById(R.id.pager);
        vPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(vPagerAdapter);

        FilePickerHelper.setupFilePicker(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_settings: {
                // TODO: Add Settings
                break;
            }
            case R.id.item_about: {
                // TODO: Add About
                break;
            }
            case R.id.item_donate: {
                // TODO: Add Donate
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO: Give popup on second page, maybe disallow on third one
        if(vPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            vPager.setCurrentItem(vPager.getCurrentItem() -1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(FilePickerHelper.dialogCreated())
                    {   //Show dialog if the read permission has been granted.
                        FilePickerHelper.showDialog();
                    }
                }
                else {
                    // Permission has not been granted.
                    // If on main screen (0), put snackbar in the coord_layout so the fab animates.
                    if(vPager.getCurrentItem() == 0){
                        Snackbar.make(findViewById(R.id.main_coord_layout), getString(R.string.sb_permission_storage), Snackbar.LENGTH_SHORT).show();
                    } else {
                        // Not on main screen, just use content window
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.sb_permission_storage), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {


        private ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new MainPageFragment();
                case 1: return new HashingPageFragment();
                case 2: return new ResultPageFragment();
                default: return new MainPageFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }
}
