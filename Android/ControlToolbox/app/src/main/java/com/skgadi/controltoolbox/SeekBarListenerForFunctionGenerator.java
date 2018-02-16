package com.skgadi.controltoolbox;


import com.warkiz.widget.IndicatorSeekBar;

/**
 * Created by gadis on 15-Feb-18.
 */

public class SeekBarListenerForFunctionGenerator implements IndicatorSeekBar.OnSeekBarChangeListener {
    public FunctionGenerator Signal;
    public int Index;
    public SeekBarListenerForFunctionGenerator(FunctionGenerator signal, int index) {
        Signal = signal;
        Index = index;
    }
    @Override
    public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
        switch (Index) {
            case 0:
                Signal.Frequency = progressFloat;
                break;
            case 1:
                Signal.MaximumAmplitude = progressFloat;
                break;
            case 2:
                Signal.StartAt = progressFloat;
                break;
            case 3:
                Signal.DutyCycle = progressFloat;
                break;
            case 4:
                Signal.OffSet = progressFloat;
                break;
        }
    }

    @Override
    public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

    }

    @Override
    public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

    }

    @Override
    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

    }
}