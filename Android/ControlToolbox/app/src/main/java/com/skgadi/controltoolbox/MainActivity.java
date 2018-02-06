package com.skgadi.controltoolbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


enum SCREENS {
    MAIN_SCREEN,
    PID,
    IDENTIFICATION0,
    IDENTIFICATION1,
    IDENTIFICATION2
}

public class MainActivity extends AppCompatActivity {

    public LineChart chart;
    private LinearLayout.LayoutParams DefaultLayoutParams;
    private LinearLayout[] Screens;
    private int PresentScreen;
    private boolean CloseApp;
    protected String[] ScreensList;

    @Override
    public void onBackPressed() {
        if (CloseApp)
            finish();
        //super.onBackPressed();
        if (PresentScreen == 0) {
            CloseApp = true;
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.EXIT_MESSAGE),
                    Toast.LENGTH_LONG).show();
        } else {
            CloseApp = false;
            SetScreenTo(SCREENS.MAIN_SCREEN);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--- Var vals
        Screens = new LinearLayout[SCREENS.values().length];
        Screens[0] = (LinearLayout) findViewById(R.id.Main);
        Screens[1] = (LinearLayout) findViewById(R.id.PID);
        Screens[2] = (LinearLayout) findViewById(R.id.IDENTIFICATION0);
        Screens[3] = (LinearLayout) findViewById(R.id.IDENTIFICATION1);
        Screens[4] = (LinearLayout) findViewById(R.id.IDENTIFICATION2);
        DefaultLayoutParams =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //--- Add buttons
        ScreensList = getResources().getStringArray(R.array.SCREENS_LIST);
        Button ButtonForMainScreen;
        for (int i=1; i<ScreensList.length; i++) {
            ButtonForMainScreen = new Button(this);
            ButtonForMainScreen.setText(ScreensList[i]);
            ButtonForMainScreen.setLayoutParams(DefaultLayoutParams);
            ButtonForMainScreen.setOnClickListener(new OnMainWindowButton(i));
            Screens[0].addView(ButtonForMainScreen);
        }
        SetScreenTo(SCREENS.MAIN_SCREEN);
    }

    public void DrawSine(View v) throws InterruptedException {
        chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);


        for (int i=0; i<(10*360); i++) {
            lineData.addEntry( new Entry(i/10.0f, (float)(Math.sin(i*Math.PI/1800))), 0);
            //chart.getData().addEntry(new Entry(i/10.0f, (float)(Math.sin(i*Math.PI/1800))), 0);
        }
        chart.setData(lineData);
        chart.invalidate();
    }

    private void SetScreenTo (SCREENS Screen) {
        for (int i=0; i<SCREENS.values().length; i++)
            Screens[i].setVisibility(View.GONE);
        PresentScreen = Screen.ordinal();
        Screens[PresentScreen].setVisibility(View.VISIBLE);
        switch (Screen){
            case MAIN_SCREEN:
                break;
            case PID:
                break;
            case IDENTIFICATION0:
                break;
            case IDENTIFICATION1:
                break;
            case IDENTIFICATION2:
                break;
            default:
                break;
        }
        setTitle(getResources().getString(R.string.app_name)
                + ": "
                +ScreensList[PresentScreen]);/**/
        Toast.makeText(getApplicationContext(), Screen.toString(), Toast.LENGTH_LONG).show();
    }



    public class OnMainWindowButton implements View.OnClickListener {
        int ScreenNumber;
        public OnMainWindowButton (int ScreenNumber) {
            this.ScreenNumber = ScreenNumber;
        }
        @Override
        public void onClick(View v) {
            SetScreenTo (SCREENS.values()[ScreenNumber]);
        }
    };

}













