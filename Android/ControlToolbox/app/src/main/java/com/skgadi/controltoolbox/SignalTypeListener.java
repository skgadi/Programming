package com.skgadi.controltoolbox;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by gadis on 15-Feb-18.
 */

public class SignalTypeListener implements AdapterView.OnItemSelectedListener {
    public FunctionGenerator Signal;
    public SignalTypeListener(FunctionGenerator signal) {
        Signal = signal;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Signal.SetType(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Signal.Type = SignalType.values()[0];
    }
}
