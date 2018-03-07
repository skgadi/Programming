/*
 * Ver003.c
 *
 * Code generation for model "Ver003".
 *
 * Model version              : 1.16
 * Simulink Coder version : 8.12 (R2017a) 16-Feb-2017
 * C source code generated on : Thu Nov 16 16:58:07 2017
 *
 * Target selection: sldrtert.tlc
 * Embedded hardware selection: Intel->x86-64 (Windows64)
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#include "Ver003.h"
#include "Ver003_private.h"
#include "Ver003_dt.h"

/* options for Simulink Desktop Real-Time board 0 */
static double SLDRTBoardOptions0[] = {
  0.0,
  0.0,
  0.0,
  0.0,
  0.0,
  0.0,
  0.0,
};

/* list of Simulink Desktop Real-Time timers */
const int SLDRTTimerCount = 1;
const double SLDRTTimers[2] = {
  0.001, 0.0,
};

/* list of Simulink Desktop Real-Time boards */
const int SLDRTBoardCount = 1;
SLDRTBOARD SLDRTBoards[1] = {
  { "National_Instruments/PCIe-6363", 4294967295U, 7, SLDRTBoardOptions0 },
};

/* Block signals (auto storage) */
B_Ver003_T Ver003_B;

/* Continuous states */
X_Ver003_T Ver003_X;

/* Block states (auto storage) */
DW_Ver003_T Ver003_DW;

/* Real-time model */
RT_MODEL_Ver003_T Ver003_M_;
RT_MODEL_Ver003_T *const Ver003_M = &Ver003_M_;

/* Simulink Desktop Real-Time specific functions */
time_T Ver003_sldrtGetTaskTime(int_T tid)
{
  switch (tid) {
   case 0 :
   case 1 :
    return(Ver003_M->Timing.t[0]);
  }

  return(0.);
}

/*
 * This function updates continuous states using the ODE3 fixed-step
 * solver algorithm
 */
static void rt_ertODEUpdateContinuousStates(RTWSolverInfo *si )
{
  /* Solver Matrices */
  static const real_T rt_ODE3_A[3] = {
    1.0/2.0, 3.0/4.0, 1.0
  };

  static const real_T rt_ODE3_B[3][3] = {
    { 1.0/2.0, 0.0, 0.0 },

    { 0.0, 3.0/4.0, 0.0 },

    { 2.0/9.0, 1.0/3.0, 4.0/9.0 }
  };

  time_T t = rtsiGetT(si);
  time_T tnew = rtsiGetSolverStopTime(si);
  time_T h = rtsiGetStepSize(si);
  real_T *x = rtsiGetContStates(si);
  ODE3_IntgData *id = (ODE3_IntgData *)rtsiGetSolverData(si);
  real_T *y = id->y;
  real_T *f0 = id->f[0];
  real_T *f1 = id->f[1];
  real_T *f2 = id->f[2];
  real_T hB[3];
  int_T i;
  int_T nXc = 3;
  rtsiSetSimTimeStep(si,MINOR_TIME_STEP);

  /* Save the state values at time t in y, we'll use x as ynew. */
  (void) memcpy(y, x,
                (uint_T)nXc*sizeof(real_T));

  /* Assumes that rtsiSetT and ModelOutputs are up-to-date */
  /* f0 = f(t,y) */
  rtsiSetdX(si, f0);
  Ver003_derivatives();

  /* f(:,2) = feval(odefile, t + hA(1), y + f*hB(:,1), args(:)(*)); */
  hB[0] = h * rt_ODE3_B[0][0];
  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0]);
  }

  rtsiSetT(si, t + h*rt_ODE3_A[0]);
  rtsiSetdX(si, f1);
  Ver003_output();
  Ver003_derivatives();

  /* f(:,3) = feval(odefile, t + hA(2), y + f*hB(:,2), args(:)(*)); */
  for (i = 0; i <= 1; i++) {
    hB[i] = h * rt_ODE3_B[1][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1]);
  }

  rtsiSetT(si, t + h*rt_ODE3_A[1]);
  rtsiSetdX(si, f2);
  Ver003_output();
  Ver003_derivatives();

  /* tnew = t + hA(3);
     ynew = y + f*hB(:,3); */
  for (i = 0; i <= 2; i++) {
    hB[i] = h * rt_ODE3_B[2][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1] + f2[i]*hB[2]);
  }

  rtsiSetT(si, tnew);
  rtsiSetSimTimeStep(si,MAJOR_TIME_STEP);
}

/* Model output function */
void Ver003_output(void)
{
  real_T rtb_Sum;
  real_T u0;
  if (rtmIsMajorTimeStep(Ver003_M)) {
    /* set solver stop time */
    rtsiSetSolverStopTime(&Ver003_M->solverInfo,((Ver003_M->Timing.clockTick0+1)*
      Ver003_M->Timing.stepSize0));
  }                                    /* end MajorTimeStep */

  /* Update absolute time of base rate at minor time step */
  if (rtmIsMinorTimeStep(Ver003_M)) {
    Ver003_M->Timing.t[0] = rtsiGetT(&Ver003_M->solverInfo);
  }

  /* TransferFcn: '<S3>/Transfer Fcn' */
  Ver003_B.TransferFcn = 0.0;
  Ver003_B.TransferFcn += Ver003_P.TransferFcn_C * Ver003_X.TransferFcn_CSTATE;
  if (rtmIsMajorTimeStep(Ver003_M)) {
    /* S-Function (sldrtai): '<S3>/Analog Input' */
    /* S-Function Block: <S3>/Analog Input */
    {
      ANALOGIOPARM parm;
      parm.mode = (RANGEMODE) Ver003_P.AnalogInput_RangeMode;
      parm.rangeidx = Ver003_P.AnalogInput_VoltRange;
      RTBIO_DriverIO(0, ANALOGINPUT, IOREAD, 1, &Ver003_P.AnalogInput_Channels,
                     &Ver003_B.AnalogInput, &parm);
    }
  }

  /* Sum: '<Root>/Sum' incorporates:
   *  Constant: '<Root>/Reference'
   */
  rtb_Sum = Ver003_P.Reference_Value - Ver003_B.TransferFcn;

  /* Gain: '<S2>/Filter Coefficient' incorporates:
   *  Gain: '<S2>/Derivative Gain'
   *  Integrator: '<S2>/Filter'
   *  Sum: '<S2>/SumD'
   */
  Ver003_B.FilterCoefficient = (Ver003_P.PIDController_D * rtb_Sum -
    Ver003_X.Filter_CSTATE) * Ver003_P.PIDController_N;

  /* Sum: '<S2>/Sum' incorporates:
   *  Gain: '<S2>/Proportional Gain'
   *  Integrator: '<S2>/Integrator'
   */
  u0 = (Ver003_P.PIDController_P * rtb_Sum + Ver003_X.Integrator_CSTATE) +
    Ver003_B.FilterCoefficient;

  /* Saturate: '<S4>/Saturation' */
  if (u0 > Ver003_P.Saturation_UpperSat) {
    Ver003_B.Saturation = Ver003_P.Saturation_UpperSat;
  } else if (u0 < Ver003_P.Saturation_LowerSat) {
    Ver003_B.Saturation = Ver003_P.Saturation_LowerSat;
  } else {
    Ver003_B.Saturation = u0;
  }

  /* End of Saturate: '<S4>/Saturation' */
  if (rtmIsMajorTimeStep(Ver003_M)) {
    /* S-Function (sldrtao): '<S4>/Analog Output' */
    /* S-Function Block: <S4>/Analog Output */
    {
      {
        ANALOGIOPARM parm;
        parm.mode = (RANGEMODE) Ver003_P.AnalogOutput_RangeMode;
        parm.rangeidx = Ver003_P.AnalogOutput_VoltRange;
        RTBIO_DriverIO(0, ANALOGOUTPUT, IOWRITE, 1,
                       &Ver003_P.AnalogOutput_Channels, ((real_T*)
          (&Ver003_B.Saturation)), &parm);
      }
    }
  }

  /* Gain: '<S2>/Integral Gain' */
  Ver003_B.IntegralGain = Ver003_P.PIDController_I * rtb_Sum;
}

/* Model update function */
void Ver003_update(void)
{
  /* signal main to stop simulation */
  {                                    /* Sample time: [0.0s, 0.0s] */
    if ((rtmGetTFinal(Ver003_M)!=-1) &&
        !((rtmGetTFinal(Ver003_M)-((Ver003_M->Timing.clockTick1) * 0.001)) >
          ((Ver003_M->Timing.clockTick1) * 0.001) * (DBL_EPSILON))) {
      rtmSetErrorStatus(Ver003_M, "Simulation finished");
    }

    if (rtmGetStopRequested(Ver003_M)) {
      rtmSetErrorStatus(Ver003_M, "Simulation finished");
    }
  }

  if (rtmIsMajorTimeStep(Ver003_M)) {
    rt_ertODEUpdateContinuousStates(&Ver003_M->solverInfo);
  }

  /* Update absolute time for base rate */
  /* The "clockTick0" counts the number of times the code of this task has
   * been executed. The absolute time is the multiplication of "clockTick0"
   * and "Timing.stepSize0". Size of "clockTick0" ensures timer will not
   * overflow during the application lifespan selected.
   */
  ++Ver003_M->Timing.clockTick0;
  Ver003_M->Timing.t[0] = rtsiGetSolverStopTime(&Ver003_M->solverInfo);

  {
    /* Update absolute timer for sample time: [0.001s, 0.0s] */
    /* The "clockTick1" counts the number of times the code of this task has
     * been executed. The resolution of this integer timer is 0.001, which is the step size
     * of the task. Size of "clockTick1" ensures timer will not overflow during the
     * application lifespan selected.
     */
    Ver003_M->Timing.clockTick1++;
  }
}

/* Derivatives for root system: '<Root>' */
void Ver003_derivatives(void)
{
  XDot_Ver003_T *_rtXdot;
  _rtXdot = ((XDot_Ver003_T *) Ver003_M->derivs);

  /* Derivatives for TransferFcn: '<S3>/Transfer Fcn' */
  _rtXdot->TransferFcn_CSTATE = 0.0;
  _rtXdot->TransferFcn_CSTATE += Ver003_P.TransferFcn_A *
    Ver003_X.TransferFcn_CSTATE;
  _rtXdot->TransferFcn_CSTATE += Ver003_B.AnalogInput;

  /* Derivatives for Integrator: '<S2>/Integrator' */
  _rtXdot->Integrator_CSTATE = Ver003_B.IntegralGain;

  /* Derivatives for Integrator: '<S2>/Filter' */
  _rtXdot->Filter_CSTATE = Ver003_B.FilterCoefficient;
}

/* Model initialize function */
void Ver003_initialize(void)
{
  /* Registration code */

  /* initialize real-time model */
  (void) memset((void *)Ver003_M, 0,
                sizeof(RT_MODEL_Ver003_T));

  {
    /* Setup solver object */
    rtsiSetSimTimeStepPtr(&Ver003_M->solverInfo, &Ver003_M->Timing.simTimeStep);
    rtsiSetTPtr(&Ver003_M->solverInfo, &rtmGetTPtr(Ver003_M));
    rtsiSetStepSizePtr(&Ver003_M->solverInfo, &Ver003_M->Timing.stepSize0);
    rtsiSetdXPtr(&Ver003_M->solverInfo, &Ver003_M->derivs);
    rtsiSetContStatesPtr(&Ver003_M->solverInfo, (real_T **)
                         &Ver003_M->contStates);
    rtsiSetNumContStatesPtr(&Ver003_M->solverInfo,
      &Ver003_M->Sizes.numContStates);
    rtsiSetNumPeriodicContStatesPtr(&Ver003_M->solverInfo,
      &Ver003_M->Sizes.numPeriodicContStates);
    rtsiSetPeriodicContStateIndicesPtr(&Ver003_M->solverInfo,
      &Ver003_M->periodicContStateIndices);
    rtsiSetPeriodicContStateRangesPtr(&Ver003_M->solverInfo,
      &Ver003_M->periodicContStateRanges);
    rtsiSetErrorStatusPtr(&Ver003_M->solverInfo, (&rtmGetErrorStatus(Ver003_M)));
    rtsiSetRTModelPtr(&Ver003_M->solverInfo, Ver003_M);
  }

  rtsiSetSimTimeStep(&Ver003_M->solverInfo, MAJOR_TIME_STEP);
  Ver003_M->intgData.y = Ver003_M->odeY;
  Ver003_M->intgData.f[0] = Ver003_M->odeF[0];
  Ver003_M->intgData.f[1] = Ver003_M->odeF[1];
  Ver003_M->intgData.f[2] = Ver003_M->odeF[2];
  Ver003_M->contStates = ((X_Ver003_T *) &Ver003_X);
  rtsiSetSolverData(&Ver003_M->solverInfo, (void *)&Ver003_M->intgData);
  rtsiSetSolverName(&Ver003_M->solverInfo,"ode3");
  rtmSetTPtr(Ver003_M, &Ver003_M->Timing.tArray[0]);
  rtmSetTFinal(Ver003_M, -1);
  Ver003_M->Timing.stepSize0 = 0.001;

  /* External mode info */
  Ver003_M->Sizes.checksums[0] = (38472262U);
  Ver003_M->Sizes.checksums[1] = (4163946613U);
  Ver003_M->Sizes.checksums[2] = (3525697829U);
  Ver003_M->Sizes.checksums[3] = (3025306255U);

  {
    static const sysRanDType rtAlwaysEnabled = SUBSYS_RAN_BC_ENABLE;
    static RTWExtModeInfo rt_ExtModeInfo;
    static const sysRanDType *systemRan[1];
    Ver003_M->extModeInfo = (&rt_ExtModeInfo);
    rteiSetSubSystemActiveVectorAddresses(&rt_ExtModeInfo, systemRan);
    systemRan[0] = &rtAlwaysEnabled;
    rteiSetModelMappingInfoPtr(Ver003_M->extModeInfo,
      &Ver003_M->SpecialInfo.mappingInfo);
    rteiSetChecksumsPtr(Ver003_M->extModeInfo, Ver003_M->Sizes.checksums);
    rteiSetTPtr(Ver003_M->extModeInfo, rtmGetTPtr(Ver003_M));
  }

  /* block I/O */
  (void) memset(((void *) &Ver003_B), 0,
                sizeof(B_Ver003_T));

  /* states (continuous) */
  {
    (void) memset((void *)&Ver003_X, 0,
                  sizeof(X_Ver003_T));
  }

  /* states (dwork) */
  (void) memset((void *)&Ver003_DW, 0,
                sizeof(DW_Ver003_T));

  /* data type transition information */
  {
    static DataTypeTransInfo dtInfo;
    (void) memset((char_T *) &dtInfo, 0,
                  sizeof(dtInfo));
    Ver003_M->SpecialInfo.mappingInfo = (&dtInfo);
    dtInfo.numDataTypes = 14;
    dtInfo.dataTypeSizes = &rtDataTypeSizes[0];
    dtInfo.dataTypeNames = &rtDataTypeNames[0];

    /* Block I/O transition table */
    dtInfo.BTransTable = &rtBTransTable;

    /* Parameters transition table */
    dtInfo.PTransTable = &rtPTransTable;
  }

  /* Start for S-Function (sldrtao): '<S4>/Analog Output' */

  /* S-Function Block: <S4>/Analog Output */

  /* no initial value should be set */

  /* InitializeConditions for TransferFcn: '<S3>/Transfer Fcn' */
  Ver003_X.TransferFcn_CSTATE = 0.0;

  /* InitializeConditions for Integrator: '<S2>/Integrator' */
  Ver003_X.Integrator_CSTATE = Ver003_P.Integrator_IC;

  /* InitializeConditions for Integrator: '<S2>/Filter' */
  Ver003_X.Filter_CSTATE = Ver003_P.Filter_IC;
}

/* Model terminate function */
void Ver003_terminate(void)
{
  /* Terminate for S-Function (sldrtao): '<S4>/Analog Output' */

  /* S-Function Block: <S4>/Analog Output */
  {
    {
      ANALOGIOPARM parm;
      parm.mode = (RANGEMODE) Ver003_P.AnalogOutput_RangeMode;
      parm.rangeidx = Ver003_P.AnalogOutput_VoltRange;
      RTBIO_DriverIO(0, ANALOGOUTPUT, IOWRITE, 1,
                     &Ver003_P.AnalogOutput_Channels,
                     &Ver003_P.AnalogOutput_FinalValue, &parm);
    }
  }
}
