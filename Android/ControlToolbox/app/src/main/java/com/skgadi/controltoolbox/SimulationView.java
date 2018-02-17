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
    public abstract float[] RunAlgorithms(float[] Parameters,
                                          float[] Generated, float[] Generated1Delay, float[] Generated2Delay,
                                          float[] In, float[] In1Delay, float[] In2Delay,
                                          float[] Out1Delay, float[] Out2Delay);
    public abstract float[] OutGraphSignals (float[] Generated, float[] In, float[] Out);
}