package it.raqb.androidapp.androhash.helpers;

import android.content.Context;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

import it.raqb.androidapp.androhash.MainActivity;

/**
 * Created by ramon on 5-7-17.
 */

public class FilePickerHelper {

    private static final String DEFAULT_DIR = "/mnt/sdcard";

    private static FilePickerDialog dialog;

    public static void setupFilePicker(Context context){
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DEFAULT_DIR);
        properties.error_dir = new File(DEFAULT_DIR);
        properties.offset = new File(DEFAULT_DIR);
        properties.extensions = null;

        dialog = new FilePickerDialog(context, properties);
        dialog.setTitle("Select a file to hash");
    }

    public static void showDialog() {
        dialog.show();
    }

    public static boolean dialogCreated(){
        return dialog != null;
    }

    public static void addListener(DialogSelectionListener listener){
        dialog.setDialogSelectionListener(listener);
    }
}
