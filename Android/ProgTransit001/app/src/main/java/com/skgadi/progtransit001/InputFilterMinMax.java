package com.skgadi.progtransit001;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

/**
 * Created by gadis on 18-Aug-17.
 */

public class InputFilterMinMax implements InputFilter {
    private int min, max;
    Context ctx;
    public InputFilterMinMax(Context ctx, int min, int max) {
        this.min = min;
        this.max = max;
        this.ctx = ctx;
    }

    public InputFilterMinMax(Context ctx, String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
        this.ctx = ctx;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
            // Add the new string in
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());
            //****Add this line (below) to allow Negative values***//
            if(newVal.equalsIgnoreCase("-") && min < 0)return null;
            int input = Integer.parseInt(newVal);
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        Toast.makeText(ctx,  ctx.getResources().getStringArray(R.array.Toasts)[2] +
                " ("+min+", "+max +")." , Toast.LENGTH_SHORT).show();
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
