package it.raqb.androidapp.androhash.events;

import java.util.HashMap;

/**
 * Created by ramon on 6-7-17.
 */

public class OptionsSubmittedEvent {
    public String pickedFile;
    public HashMap<String, Boolean> checkboxStates;

    public OptionsSubmittedEvent(String newPickedFile, HashMap<String, Boolean> newCheckboxStates){
        this.pickedFile = newPickedFile;
        this.checkboxStates = newCheckboxStates;
    }
}
