package it.raqb.androidapp.androhash.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.github.angads25.filepicker.controller.DialogSelectionListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;

import it.raqb.androidapp.androhash.R;
import it.raqb.androidapp.androhash.events.OptionsSubmittedEvent;
import it.raqb.androidapp.androhash.helpers.FilePickerHelper;

/**
 * Created by ramon on 5-7-17.
 */

public class MainPageFragment extends Fragment {

    private EventBus bus;

    private FloatingActionButton fab;

    private EditText fileInput;

    private String pickedFile;

    private CheckBox cb_md5, cb_sha1, cb_sha256, cb_sha512;

    private int checkboxesChecked;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Getting default bus
        bus = EventBus.getDefault();

        // If there is a saved state, load all data back
        if(savedInstanceState != null) {
            pickedFile = savedInstanceState.getString(getString(R.string.key_saved_instance_file));
        }

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_main, container, false);

        fab = rootView.findViewById(R.id.floatingActionButton);
        setupFAB(rootView);

        fileInput = rootView.findViewById(R.id.input_file);

        setupFilePicker();

        checkboxesChecked = 1;

        cb_md5 = rootView.findViewById(R.id.cb_md5);
        cb_sha1 = rootView.findViewById(R.id.cb_sha1);
        cb_sha256 = rootView.findViewById(R.id.cb_sha256);
        cb_sha512 = rootView.findViewById(R.id.cb_sha512);

        setupCheckboxes();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.key_saved_instance_file), pickedFile);
        super.onSaveInstanceState(outState);
    }

    private void setupFAB(final ViewGroup rootView) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager vp = getActivity().findViewById(R.id.pager);

                // No file is picked, give error snack
                if(pickedFile == null){
                    Snackbar.make(rootView.findViewById(R.id.main_coord_layout), getString(R.string.sb_file_select), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // No checkbox is selected, give error snack
                if(checkboxesChecked == 0){
                    Snackbar.make(rootView.findViewById(R.id.main_coord_layout), getString(R.string.sb_checkbox_select), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Everything is good, post options to event bus and flip page

                HashMap<String, Boolean> checkboxStates = new HashMap<String, Boolean>();
                checkboxStates.put(getString(R.string.hash_md5), cb_md5.isChecked());
                checkboxStates.put(getString(R.string.hash_sha1), cb_sha1.isChecked());
                checkboxStates.put(getString(R.string.hash_sha256), cb_sha256.isChecked());
                checkboxStates.put(getString(R.string.hash_sha512), cb_sha512.isChecked());

                bus.post(new OptionsSubmittedEvent(pickedFile, checkboxStates));
                vp.setCurrentItem(vp.getCurrentItem() + 1);
            }
        });

    }

    void setupFilePicker(){
        fileInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilePickerHelper.showDialog();
            }
        });

        fileInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FilePickerHelper.showDialog();
                return true;
            }
        });

        FilePickerHelper.addListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                // Getting the file's path (0 cause there should only be one selection)
                String path = files[0];
                pickedFile = path;

                // Getting the actual file's name (latest segment when splitting by '/'
                String[] pathSegments = path.split("/");

                fileInput.setText(pathSegments[pathSegments.length - 1]);
            }
        });
    }

    void setupCheckboxes(){
        CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                // Keeping track of amount of checkboxes checked
                if(!b)
                    checkboxesChecked--;
                else
                    checkboxesChecked++;

            }
        };

        // Adding check listener to all checkboxes
        cb_md5.setOnCheckedChangeListener(checkedListener);
        cb_sha1.setOnCheckedChangeListener(checkedListener);
        cb_sha256.setOnCheckedChangeListener(checkedListener);
        cb_sha512.setOnCheckedChangeListener(checkedListener);

    }
}
