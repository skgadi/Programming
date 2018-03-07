/*
 * File: Ver002_data.c
 *
 * Code generated for Simulink model 'Ver002'.
 *
 * Model version                  : 1.15
 * Simulink Coder version         : 8.12 (R2017a) 16-Feb-2017
 * C/C++ source code generated on : Thu Nov 16 10:56:49 2017
 *
 * Target selection: sldrtert.tlc
 * Embedded hardware selection: Intel->x86-64 (Windows64)
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#include "Ver002.h"
#include "Ver002_private.h"

/* Block parameters (auto storage) */
P_Ver002_T Ver002_P = {
  0.1,                                 /* Mask Parameter: PIDController_D
                                        * Referenced by: '<S2>/Derivative Gain'
                                        */
  0.0,                                 /* Mask Parameter: AnalogOutput_FinalValue
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  50.0,                                /* Mask Parameter: PIDController_I
                                        * Referenced by: '<S2>/Integral Gain'
                                        */
  10.0,                                /* Mask Parameter: AnalogInput_MaxMissedTicks
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  10.0,                                /* Mask Parameter: AnalogOutput_MaxMissedTicks
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  2000.0,                              /* Mask Parameter: PIDController_N
                                        * Referenced by: '<S2>/Filter Coefficient'
                                        */
  5.0,                                 /* Mask Parameter: PIDController_P
                                        * Referenced by: '<S2>/Proportional Gain'
                                        */
  0.0,                                 /* Mask Parameter: AnalogInput_YieldWhenWaiting
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  0.0,                                 /* Mask Parameter: AnalogOutput_YieldWhenWaiting
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  7,                                   /* Mask Parameter: AnalogInput_Channels
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  0,                                   /* Mask Parameter: AnalogOutput_Channels
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  0,                                   /* Mask Parameter: AnalogInput_RangeMode
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  0,                                   /* Mask Parameter: AnalogOutput_RangeMode
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  0,                                   /* Mask Parameter: AnalogInput_VoltRange
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  1,                                   /* Mask Parameter: AnalogOutput_VoltRange
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  -10.0,                               /* Computed Parameter: TransferFcn_A
                                        * Referenced by: '<S3>/Transfer Fcn'
                                        */
  10.0,                                /* Computed Parameter: TransferFcn_C
                                        * Referenced by: '<S3>/Transfer Fcn'
                                        */
  0.0,                                 /* Expression: 0
                                        * Referenced by: '<Root>/Reference'
                                        */
  0.0,                                 /* Expression: InitialConditionForIntegrator
                                        * Referenced by: '<S2>/Integrator'
                                        */
  0.0,                                 /* Expression: InitialConditionForFilter
                                        * Referenced by: '<S2>/Filter'
                                        */
  5.0,                                 /* Expression: 5
                                        * Referenced by: '<S4>/Saturation'
                                        */
  0.0                                  /* Expression: 0
                                        * Referenced by: '<S4>/Saturation'
                                        */
};

/*
 * File trailer for generated code.
 *
 * [EOF]
 */
