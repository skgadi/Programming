package com.skgadi.controltoolbox;

import android.support.annotation.DrawableRes;

import com.warkiz.widget.IndicatorSeekBar;

/**
 * Created by gadis on 13-Feb-18.
 */

public abstract class SimulationView {
    public int[] Images;
    public String[] Ports; // 0-Out others are input
    public String[] SignalGenerators;
    public Figure Figures [];
    public Parameter[] Parameters;
    public float T_S;
    public abstract float[] RunAlgorithms(float[] Parameters,
                                          float[] In, float[] In1Delay, float[] In2Delay,
                                          float[] Out1Delay, float[] Out2Delay);
}