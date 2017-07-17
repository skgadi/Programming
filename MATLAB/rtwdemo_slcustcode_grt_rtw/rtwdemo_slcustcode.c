/*
 * rtwdemo_slcustcode.c
 *
 * Code generation for model "rtwdemo_slcustcode".
 *
 * Model version              : 1.243
 * Simulink Coder version : 8.12 (R2017a) 16-Feb-2017
 * C source code generated on : Mon Jul 10 18:52:43 2017
 *
 * Target selection: grt.tlc
 * Note: GRT includes extra infrastructure and instrumentation for prototyping
 * Embedded hardware selection: Specified
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#include "rtwdemo_slcustcode.h"
#include "rtwdemo_slcustcode_private.h"

/* user code (top of source file) */
/* Declared from Configration Parameters Code Generation Custom Code Page (Source file) */
int_T GLOBAL_INT2 = -1;

/* System '<Root>' */
/* Declared from Model Source Block */
int_T GLOBAL_INT1 = 0;

/* Block states (auto storage) */
DW_rtwdemo_slcustcode_T rtwdemo_slcustcode_DW;

/* Previous zero-crossings (trigger) states */
PrevZCX_rtwdemo_slcustcode_T rtwdemo_slcustcode_PrevZCX;

/* External inputs (root inport signals with auto storage) */
ExtU_rtwdemo_slcustcode_T rtwdemo_slcustcode_U;

/* External outputs (root outports fed by signals with auto storage) */
ExtY_rtwdemo_slcustcode_T rtwdemo_slcustcode_Y;

/* Real-time model */
RT_MODEL_rtwdemo_slcustcode_T rtwdemo_slcustcode_M_;
RT_MODEL_rtwdemo_slcustcode_T *const rtwdemo_slcustcode_M =
  &rtwdemo_slcustcode_M_;

/* Model step function */
void rtwdemo_slcustcode_step(void)
{
  real_T rtb_sum_out;
  boolean_T rtb_equal_to_count;

  /* Sum: '<Root>/Sum' incorporates:
   *  Constant: '<Root>/INC'
   *  UnitDelay: '<Root>/X'
   */
  rtb_sum_out = 1.0 + rtwdemo_slcustcode_DW.X;

  /* RelationalOperator: '<Root>/RelOpt' incorporates:
   *  Constant: '<Root>/INC'
   *  Constant: '<Root>/LIMIT'
   *  Sum: '<Root>/Sum'
   *  UnitDelay: '<Root>/X'
   */
  rtb_equal_to_count = (1.0 + rtwdemo_slcustcode_DW.X != 16.0);

  /* Outputs for Triggered SubSystem: '<Root>/Amplifier' incorporates:
   *  TriggerPort: '<S1>/Trigger'
   */
  if (rtb_equal_to_count && (rtwdemo_slcustcode_PrevZCX.Amplifier_Trig_ZCE !=
       POS_ZCSIG)) {
    {
      /* user code (Output function Header) */

      /* System '<Root>/Amplifier' */
      /* Declared from Subsystem Outputs Custom Code Block */
      int_T *intPtr = &GLOBAL_INT1;

      /* user code (Output function Body) */

      /* System '<Root>/Amplifier' */

      /* Set from Subsystem Outputs Custom Code Block */
      *intPtr = -1;

      /* Outport: '<Root>/Output' incorporates:
       *  Gain: '<S1>/Gain'
       *  Inport: '<Root>/Input'
       */
      rtwdemo_slcustcode_Y.Output = rtwdemo_slcustcode_U.Input << 1;

      /* user code (Output function Trailer) */

      /* System '<Root>/Amplifier' */

      /* Set from Subsystem Outputs Custom Code Block */
      *intPtr = 0;
    }
  }

  rtwdemo_slcustcode_PrevZCX.Amplifier_Trig_ZCE = rtb_equal_to_count;

  /* End of Outputs for SubSystem: '<Root>/Amplifier' */

  /* Switch: '<Root>/Switch' */
  if (rtb_equal_to_count) {
    /* Update for UnitDelay: '<Root>/X' */
    rtwdemo_slcustcode_DW.X = rtb_sum_out;
  } else {
    /* Update for UnitDelay: '<Root>/X' incorporates:
     *  Constant: '<Root>/RESET'
     */
    rtwdemo_slcustcode_DW.X = 0.0;
  }

  /* End of Switch: '<Root>/Switch' */
}

/* Model initialize function */
void rtwdemo_slcustcode_initialize(void)
{
  /* Registration code */

  /* initialize error status */
  rtmSetErrorStatus(rtwdemo_slcustcode_M, (NULL));

  /* states (dwork) */
  (void) memset((void *)&rtwdemo_slcustcode_DW, 0,
                sizeof(DW_rtwdemo_slcustcode_T));

  /* external inputs */
  rtwdemo_slcustcode_U.Input = 0;

  /* external outputs */
  rtwdemo_slcustcode_Y.Output = 0;
  rtwdemo_slcustcode_PrevZCX.Amplifier_Trig_ZCE = POS_ZCSIG;

  /* user code (Initialize function Body) */

  /* Declared from Configuration Parameters Code Generation Custom Code Page (Initialize function) */
  GLOBAL_INT2 = 1;

  /* InitializeConditions for UnitDelay: '<Root>/X' */
  rtwdemo_slcustcode_DW.X = 0.0;

  /* SystemInitialize for Triggered SubSystem: '<Root>/Amplifier' */

  /* SystemInitialize for Outport: '<Root>/Output' incorporates:
   *  SystemInitialize for Outport: '<S1>/Out'
   */
  rtwdemo_slcustcode_Y.Output = 0;

  /* End of SystemInitialize for SubSystem: '<Root>/Amplifier' */
}

/* Model terminate function */
void rtwdemo_slcustcode_terminate(void)
{
  /* (no terminate code required) */
}
