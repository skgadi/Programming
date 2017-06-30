package com.skgadi.progtransit001;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    private File folder;
    private LinearLayout MV_FilesList;
    private LinearLayout MV_AssignInputs;
    private RadioGroup FL_ProfilesList;
    private Button FL_ProfileSelectedBtn;
    private EditText FL_NewProfileEdtBox;

    //Defined By SKGadi
    enum GLOBAL_DEVICE_STATE {
        NONE,
        FILES_LIST,
        INPUTS,
        CYCLE_TYPES,
        STATES,
        PROGRAM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SKGadi edit
        MV_FilesList = (LinearLayout) findViewById(R.id.MV_FilesList);
        MV_AssignInputs = (LinearLayout) findViewById(R.id.MV_AssignInputs);
        FL_ProfilesList = (RadioGroup) findViewById(R.id.FL_ProfilesList);
        FL_ProfileSelectedBtn = (Button) findViewById(R.id.FL_ProfileSelectedBtn);
        FL_NewProfileEdtBox = (EditText) findViewById(R.id.FL_NewProfileEdtBox);
        //Initializing activities
        if (GetFolderHandle().exists())
            GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.FILES_LIST);
        else {
            GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.NONE);
            return;
        }
        ListAllFilesList();
    }
    public void FL_OC_RefreshList (View v) {
        ListAllFilesList ();
    }

    private void ListAllFilesList () {
        FL_ProfileSelectedBtn.setEnabled(false);
        FL_ProfilesList.removeAllViews();
        File List[] = folder.listFiles();
        RadioButton RadioItem;
        for (int i=0; i<List.length; i++) {
            if (List[i].length() < (3*1024)) {
                RadioItem = new RadioButton(this);
                RadioItem.setText(List[i].getName());
                RadioItem.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        FL_ProfileSelectedBtn.setEnabled(true);
                    }
                });
                FL_ProfilesList.addView(RadioItem);
            }
        }
    }

    private boolean CreateNewFileWithZeros() {
        File file = new File(folder, FL_NewProfileEdtBox.getText().toString());
        if (!file.exists()) {
            String string = "\0";
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(string.getBytes());
                outputStream.close();
                return true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to create the file", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
        } else
            Toast.makeText(getApplicationContext(), R.string.error_FileAlreadyExist, Toast.LENGTH_SHORT).show();
        return false;
    }
    private File GetFolderHandle () {
        folder = new File(getApplicationContext().getExternalFilesDir(
                null), "profiles");
        if (folder.exists())
            Toast.makeText(getApplicationContext(),"Folder found", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getApplicationContext(),"Folder not found", Toast.LENGTH_SHORT).show();
            if (!folder.mkdirs()) {
                Toast.makeText(getApplicationContext(), R.string.error_UnableToCreateFolder, Toast.LENGTH_LONG).show();
            }
        }
        return folder;
    }

    private void GLOBAL_SetViewState (GLOBAL_DEVICE_STATE state) {
        GLOBAL_SetInitialCondition();
        switch (state) {
            case FILES_LIST:
                MV_FilesList.setVisibility(View.VISIBLE);
                return;
            case INPUTS:
                MV_AssignInputs.setVisibility(View.VISIBLE);
                return;
            default:
                return;
        }
    }

    private void GLOBAL_SetInitialCondition() {
        MV_FilesList.setVisibility(View.GONE);
        MV_AssignInputs.setVisibility(View.GONE);
        FL_ProfileSelectedBtn.setEnabled(false);
        FL_ProfilesList.clearCheck();
    }

    public void FL_OC_AddNewProfile (View v) {
        if (CreateNewFileWithZeros())
            ListAllFilesList();
    }
    public void FL_OC_ProfileSelected (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.INPUTS);
    }
    public void TempButton (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.FILES_LIST);
    }
    /*------------------- Taken from 3rd party -------------------*/
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
