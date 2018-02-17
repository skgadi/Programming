package com.skgadi.controltoolbox;

import android.support.annotation.DrawableRes;

import com.warkiz.widget.IndicatorSeekBar;

/**
 * Created by gadis on 13-Feb-18.
 */

public abstract class SimulationView {
    public int[] Images;
    public String[] ImageNames;
    public String[] Ports; // 0-Out others are input
    public String[] SignalGenerators;
    public Figure Figures [];
    public Parameter[] Parameters;
    public float T_S;
    public float[] OutPut;
    public int NoOfInputs;
    public int NoOfOutputs;
    public int NoOfPastInputsRequired;
    public int NoOfPastOuputsRequired;
    public int NoOfPastGeneratedValuesRequired;
    public abstract float[] RunAlgorithms(
            float[] Parameters,
            float[][] Generated,
            float[][] Input,
            float[][] Output
    );
    public abstract float[] OutGraphSignals (
            float[] Parameters,
            float[][] Generated,
            float[][] Input,
            float[][] Output
    );
}