/*
 * sldrtex_controller_data.c
 *
 * Code generation for model "sldrtex_controller".
 *
 * Model version              : 1.27
 * Simulink Coder version : 8.12 (R2017a) 16-Feb-2017
 * C source code generated on : Mon Jul 10 18:29:47 2017
 *
 * Target selection: sldrt.tlc
 * Note: GRT includes extra infrastructure and instrumentation for prototyping
 * Embedded hardware selection: Intel->x86-64 (Windows64)
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#include "sldrtex_controller.h"
#include "sldrtex_controller_private.h"

/* Block parameters (auto storage) */
P_sldrtex_controller_T sldrtex_controller_P = {
  0.08,                                /* Mask Parameter: PIDController_D
                                        * Referenced by: '<S1>/Derivative Gain'
                                        */
  40.0,                                /* Mask Parameter: PIDController_I
                                        * Referenced by: '<S1>/Integral Gain'
                                        */
  20.0,                                /* Mask Parameter: AnalogInput_MaxMissedTicks
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  20.0,                                /* Mask Parameter: AnalogOutput_MaxMissedTicks
                                        * Referenced by: '<Root>/Analog Output'
                                        */
  4.0,                                 /* Mask Parameter: PIDController_P
                                        * Referenced by: '<S1>/Proportional Gain'
                                        */
  0.0,                                 /* Mask Parameter: AnalogInput_YieldWhenWaiting
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  0.0,                                 /* Mask Parameter: AnalogOutput_YieldWhenWaiting
                                        * Referenced by: '<Root>/Analog Output'
                                        */
  0,                                   /* Mask Parameter: AnalogInput_Channels
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  0,                                   /* Mask Parameter: AnalogOutput_Channels
                                        * Referenced by: '<Root>/Analog Output'
                                        */
  0,                                   /* Mask Parameter: AnalogInput_RangeMode
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  0,                                   /* Mask Parameter: AnalogOutput_RangeMode
                                        * Referenced by: '<Root>/Analog Output'
                                        */
  0,                                   /* Mask Parameter: AnalogInput_VoltRange
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  0,                                   /* Mask Parameter: AnalogOutput_VoltRange
                                        * Referenced by: '<Root>/Analog Output'
                                        */
  0.5,                                 /* Expression: 0.5
                                        * Referenced by: '<Root>/Setpoint'
                                        */
  0.2,                                 /* Expression: 0.20000
                                        * Referenced by: '<Root>/Signal Generator'
                                        */
  0.477464829275686,                   /* Computed Parameter: SignalGenerator_Frequency
                                        * Referenced by: '<Root>/Signal Generator'
                                        */
  0.0                                  /* Expression: 0
                                        * Referenced by: '<S1>/Integrator'
                                        */
};
