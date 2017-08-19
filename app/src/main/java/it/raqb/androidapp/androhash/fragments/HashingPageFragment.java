package it.raqb.androidapp.androhash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import it.raqb.androidapp.androhash.R;
import it.raqb.androidapp.androhash.events.OptionsSubmittedEvent;
import it.raqb.androidapp.androhash.tasks.OnHashingTaskCompleted;

/**
 * Created by ramon on 5-7-17.
 */

public class HashingPageFragment extends Fragment implements OnHashingTaskCompleted {

    private EventBus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Getting default bus
        bus = EventBus.getDefault();

        // Registering class as event listener
        bus.register(this);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_hashing, container, false);

        return rootView;
    }

    @Subscribe
    public void onOptionsSubmitted(OptionsSubmittedEvent event) {
//        new MD5Task(this).execute(new File(event.pickedFile));
        for (int i = 0; i < event.checkboxStates.size(); i++) {
            createHashingElement((String)event.checkboxStates.keySet().toArray()[i]);
        }
    }

    @Override
    public void onTaskCompleted(String tag, String digest) {
        Log.d(this.getClass().getName(), digest);
        Log.d(tag, digest);
    }

    void createHashingElement(String type) {
        LinearLayout card_container = getView().findViewById(R.id.card_container);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.hashing_progress,null,false);
        card_container.addView(inflatedLayout);
    }
}
