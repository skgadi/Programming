package com.skgadi.controltoolbox;

import android.widget.CompoundButton;

/**
 * Created by gadis on 15-Feb-18.
 */

public class SignalComplimentListener implements CompoundButton.OnCheckedChangeListener {
    public FunctionGenerator Signal;
    SignalComplimentListener (FunctionGenerator signal) {
        Signal = signal;
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Signal.Compliment = b;
    }
}
