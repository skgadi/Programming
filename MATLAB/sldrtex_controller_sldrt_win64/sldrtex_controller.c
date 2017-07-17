/*
 * sldrtex_controller.c
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
#include "sldrtex_controller_dt.h"

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
B_sldrtex_controller_T sldrtex_controller_B;

/* Continuous states */
X_sldrtex_controller_T sldrtex_controller_X;

/* Block states (auto storage) */
DW_sldrtex_controller_T sldrtex_controller_DW;

/* Real-time model */
RT_MODEL_sldrtex_controller_T sldrtex_controller_M_;
RT_MODEL_sldrtex_controller_T *const sldrtex_controller_M =
  &sldrtex_controller_M_;

/*
 * This function updates continuous states using the ODE5 fixed-step
 * solver algorithm
 */
static void rt_ertODEUpdateContinuousStates(RTWSolverInfo *si )
{
  /* Solver Matrices */
  static const real_T rt_ODE5_A[6] = {
    1.0/5.0, 3.0/10.0, 4.0/5.0, 8.0/9.0, 1.0, 1.0
  };

  static const real_T rt_ODE5_B[6][6] = {
    { 1.0/5.0, 0.0, 0.0, 0.0, 0.0, 0.0 },

    { 3.0/40.0, 9.0/40.0, 0.0, 0.0, 0.0, 0.0 },

    { 44.0/45.0, -56.0/15.0, 32.0/9.0, 0.0, 0.0, 0.0 },

    { 19372.0/6561.0, -25360.0/2187.0, 64448.0/6561.0, -212.0/729.0, 0.0, 0.0 },

    { 9017.0/3168.0, -355.0/33.0, 46732.0/5247.0, 49.0/176.0, -5103.0/18656.0,
      0.0 },

    { 35.0/384.0, 0.0, 500.0/1113.0, 125.0/192.0, -2187.0/6784.0, 11.0/84.0 }
  };

  time_T t = rtsiGetT(si);
  time_T tnew = rtsiGetSolverStopTime(si);
  time_T h = rtsiGetStepSize(si);
  real_T *x = rtsiGetContStates(si);
  ODE5_IntgData *id = (ODE5_IntgData *)rtsiGetSolverData(si);
  real_T *y = id->y;
  real_T *f0 = id->f[0];
  real_T *f1 = id->f[1];
  real_T *f2 = id->f[2];
  real_T *f3 = id->f[3];
  real_T *f4 = id->f[4];
  real_T *f5 = id->f[5];
  real_T hB[6];
  int_T i;
  int_T nXc = 1;
  rtsiSetSimTimeStep(si,MINOR_TIME_STEP);

  /* Save the state values at time t in y, we'll use x as ynew. */
  (void) memcpy(y, x,
                (uint_T)nXc*sizeof(real_T));

  /* Assumes that rtsiSetT and ModelOutputs are up-to-date */
  /* f0 = f(t,y) */
  rtsiSetdX(si, f0);
  sldrtex_controller_derivatives();

  /* f(:,2) = feval(odefile, t + hA(1), y + f*hB(:,1), args(:)(*)); */
  hB[0] = h * rt_ODE5_B[0][0];
  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0]);
  }

  rtsiSetT(si, t + h*rt_ODE5_A[0]);
  rtsiSetdX(si, f1);
  sldrtex_controller_output();
  sldrtex_controller_derivatives();

  /* f(:,3) = feval(odefile, t + hA(2), y + f*hB(:,2), args(:)(*)); */
  for (i = 0; i <= 1; i++) {
    hB[i] = h * rt_ODE5_B[1][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1]);
  }

  rtsiSetT(si, t + h*rt_ODE5_A[1]);
  rtsiSetdX(si, f2);
  sldrtex_controller_output();
  sldrtex_controller_derivatives();

  /* f(:,4) = feval(odefile, t + hA(3), y + f*hB(:,3), args(:)(*)); */
  for (i = 0; i <= 2; i++) {
    hB[i] = h * rt_ODE5_B[2][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1] + f2[i]*hB[2]);
  }

  rtsiSetT(si, t + h*rt_ODE5_A[2]);
  rtsiSetdX(si, f3);
  sldrtex_controller_output();
  sldrtex_controller_derivatives();

  /* f(:,5) = feval(odefile, t + hA(4), y + f*hB(:,4), args(:)(*)); */
  for (i = 0; i <= 3; i++) {
    hB[i] = h * rt_ODE5_B[3][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1] + f2[i]*hB[2] +
                   f3[i]*hB[3]);
  }

  rtsiSetT(si, t + h*rt_ODE5_A[3]);
  rtsiSetdX(si, f4);
  sldrtex_controller_output();
  sldrtex_controller_derivatives();

  /* f(:,6) = feval(odefile, t + hA(5), y + f*hB(:,5), args(:)(*)); */
  for (i = 0; i <= 4; i++) {
    hB[i] = h * rt_ODE5_B[4][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1] + f2[i]*hB[2] +
                   f3[i]*hB[3] + f4[i]*hB[4]);
  }

  rtsiSetT(si, tnew);
  rtsiSetdX(si, f5);
  sldrtex_controller_output();
  sldrtex_controller_derivatives();

  /* tnew = t + hA(6);
     ynew = y + f*hB(:,6); */
  for (i = 0; i <= 5; i++) {
    hB[i] = h * rt_ODE5_B[5][i];
  }

  for (i = 0; i < nXc; i++) {
    x[i] = y[i] + (f0[i]*hB[0] + f1[i]*hB[1] + f2[i]*hB[2] +
                   f3[i]*hB[3] + f4[i]*hB[4] + f5[i]*hB[5]);
  }

  rtsiSetSimTimeStep(si,MAJOR_TIME_STEP);
}

/* Model output function */
void sldrtex_controller_output(void)
{
  real_T *lastU;
  real_T rtb_Sum;
  real_T rtb_IdealDerivative;
  if (rtmIsMajorTimeStep(sldrtex_controller_M)) {
    /* set solver stop time */
    if (!(sldrtex_controller_M->Timing.clockTick0+1)) {
      rtsiSetSolverStopTime(&sldrtex_controller_M->solverInfo,
                            ((sldrtex_controller_M->Timing.clockTickH0 + 1) *
        sldrtex_controller_M->Timing.stepSize0 * 4294967296.0));
    } else {
      rtsiSetSolverStopTime(&sldrtex_controller_M->solverInfo,
                            ((sldrtex_controller_M->Timing.clockTick0 + 1) *
        sldrtex_controller_M->Timing.stepSize0 +
        sldrtex_controller_M->Timing.clockTickH0 *
        sldrtex_controller_M->Timing.stepSize0 * 4294967296.0));
    }
  }                                    /* end MajorTimeStep */

  /* Update absolute time of base rate at minor time step */
  if (rtmIsMinorTimeStep(sldrtex_controller_M)) {
    sldrtex_controller_M->Timing.t[0] = rtsiGetT
      (&sldrtex_controller_M->solverInfo);
  }

  /* SignalGenerator: '<Root>/Signal Generator' */
  rtb_Sum = sldrtex_controller_P.SignalGenerator_Frequency *
    sldrtex_controller_M->Timing.t[0];
  if (rtb_Sum - floor(rtb_Sum) >= 0.5) {
    rtb_Sum = sldrtex_controller_P.SignalGenerator_Amplitude;
  } else {
    rtb_Sum = -sldrtex_controller_P.SignalGenerator_Amplitude;
  }

  /* End of SignalGenerator: '<Root>/Signal Generator' */

  /* Sum: '<Root>/Sum4' incorporates:
   *  Constant: '<Root>/Setpoint'
   */
  sldrtex_controller_B.Sum4 = sldrtex_controller_P.Setpoint_Value + rtb_Sum;
  if (rtmIsMajorTimeStep(sldrtex_controller_M)) {
    /* S-Function (sldrtai): '<Root>/Analog Input' */
    /* S-Function Block: <Root>/Analog Input */
    {
      ANALOGIOPARM parm;
      parm.mode = (RANGEMODE) sldrtex_controller_P.AnalogInput_RangeMode;
      parm.rangeidx = sldrtex_controller_P.AnalogInput_VoltRange;
      RTBIO_DriverIO(0, ANALOGINPUT, IOREAD, 1,
                     &sldrtex_controller_P.AnalogInput_Channels,
                     &sldrtex_controller_B.AnalogInput, &parm);
    }
  }

  /* Sum: '<Root>/Sum' */
  rtb_Sum = sldrtex_controller_B.Sum4 - sldrtex_controller_B.AnalogInput;

  /* Gain: '<S1>/Derivative Gain' */
  sldrtex_controller_B.DerivativeGain = sldrtex_controller_P.PIDController_D *
    rtb_Sum;

  /* Derivative: '<S1>/Ideal Derivative' */
  if ((sldrtex_controller_DW.TimeStampA >= sldrtex_controller_M->Timing.t[0]) &&
      (sldrtex_controller_DW.TimeStampB >= sldrtex_controller_M->Timing.t[0])) {
    rtb_IdealDerivative = 0.0;
  } else {
    rtb_IdealDerivative = sldrtex_controller_DW.TimeStampA;
    lastU = &sldrtex_controller_DW.LastUAtTimeA;
    if (sldrtex_controller_DW.TimeStampA < sldrtex_controller_DW.TimeStampB) {
      if (sldrtex_controller_DW.TimeStampB < sldrtex_controller_M->Timing.t[0])
      {
        rtb_IdealDerivative = sldrtex_controller_DW.TimeStampB;
        lastU = &sldrtex_controller_DW.LastUAtTimeB;
      }
    } else {
      if (sldrtex_controller_DW.TimeStampA >= sldrtex_controller_M->Timing.t[0])
      {
        rtb_IdealDerivative = sldrtex_controller_DW.TimeStampB;
        lastU = &sldrtex_controller_DW.LastUAtTimeB;
      }
    }

    rtb_IdealDerivative = (sldrtex_controller_B.DerivativeGain - *lastU) /
      (sldrtex_controller_M->Timing.t[0] - rtb_IdealDerivative);
  }

  /* End of Derivative: '<S1>/Ideal Derivative' */

  /* Sum: '<S1>/Sum' incorporates:
   *  Gain: '<S1>/Proportional Gain'
   *  Integrator: '<S1>/Integrator'
   */
  sldrtex_controller_B.Sum = (sldrtex_controller_P.PIDController_P * rtb_Sum +
    sldrtex_controller_X.Integrator_CSTATE) + rtb_IdealDerivative;
  if (rtmIsMajorTimeStep(sldrtex_controller_M)) {
    /* S-Function (sldrtao): '<Root>/Analog Output' */
    /* S-Function Block: <Root>/Analog Output */
    {
      {
        ANALOGIOPARM parm;
        parm.mode = (RANGEMODE) sldrtex_controller_P.AnalogOutput_RangeMode;
        parm.rangeidx = sldrtex_controller_P.AnalogOutput_VoltRange;
        RTBIO_DriverIO(0, ANALOGOUTPUT, IOWRITE, 1,
                       &sldrtex_controller_P.AnalogOutput_Channels, ((real_T*) (
          &sldrtex_controller_B.Sum)), &parm);
      }
    }
  }

  /* Gain: '<S1>/Integral Gain' */
  sldrtex_controller_B.IntegralGain = sldrtex_controller_P.PIDController_I *
    rtb_Sum;
}

/* Model update function */
void sldrtex_controller_update(void)
{
  real_T *lastU;

  /* Update for Derivative: '<S1>/Ideal Derivative' */
  if (sldrtex_controller_DW.TimeStampA == (rtInf)) {
    sldrtex_controller_DW.TimeStampA = sldrtex_controller_M->Timing.t[0];
    lastU = &sldrtex_controller_DW.LastUAtTimeA;
  } else if (sldrtex_controller_DW.TimeStampB == (rtInf)) {
    sldrtex_controller_DW.TimeStampB = sldrtex_controller_M->Timing.t[0];
    lastU = &sldrtex_controller_DW.LastUAtTimeB;
  } else if (sldrtex_controller_DW.TimeStampA < sldrtex_controller_DW.TimeStampB)
  {
    sldrtex_controller_DW.TimeStampA = sldrtex_controller_M->Timing.t[0];
    lastU = &sldrtex_controller_DW.LastUAtTimeA;
  } else {
    sldrtex_controller_DW.TimeStampB = sldrtex_controller_M->Timing.t[0];
    lastU = &sldrtex_controller_DW.LastUAtTimeB;
  }

  *lastU = sldrtex_controller_B.DerivativeGain;

  /* End of Update for Derivative: '<S1>/Ideal Derivative' */
  if (rtmIsMajorTimeStep(sldrtex_controller_M)) {
    rt_ertODEUpdateContinuousStates(&sldrtex_controller_M->solverInfo);
  }

  /* Update absolute time for base rate */
  /* The "clockTick0" counts the number of times the code of this task has
   * been executed. The absolute time is the multiplication of "clockTick0"
   * and "Timing.stepSize0". Size of "clockTick0" ensures timer will not
   * overflow during the application lifespan selected.
   * Timer of this task consists of two 32 bit unsigned integers.
   * The two integers represent the low bits Timing.clockTick0 and the high bits
   * Timing.clockTickH0. When the low bit overflows to 0, the high bits increment.
   */
  if (!(++sldrtex_controller_M->Timing.clockTick0)) {
    ++sldrtex_controller_M->Timing.clockTickH0;
  }

  sldrtex_controller_M->Timing.t[0] = rtsiGetSolverStopTime
    (&sldrtex_controller_M->solverInfo);

  {
    /* Update absolute timer for sample time: [0.001s, 0.0s] */
    /* The "clockTick1" counts the number of times the code of this task has
     * been executed. The absolute time is the multiplication of "clockTick1"
     * and "Timing.stepSize1". Size of "clockTick1" ensures timer will not
     * overflow during the application lifespan selected.
     * Timer of this task consists of two 32 bit unsigned integers.
     * The two integers represent the low bits Timing.clockTick1 and the high bits
     * Timing.clockTickH1. When the low bit overflows to 0, the high bits increment.
     */
    if (!(++sldrtex_controller_M->Timing.clockTick1)) {
      ++sldrtex_controller_M->Timing.clockTickH1;
    }

    sldrtex_controller_M->Timing.t[1] = sldrtex_controller_M->Timing.clockTick1 *
      sldrtex_controller_M->Timing.stepSize1 +
      sldrtex_controller_M->Timing.clockTickH1 *
      sldrtex_controller_M->Timing.stepSize1 * 4294967296.0;
  }
}

/* Derivatives for root system: '<Root>' */
void sldrtex_controller_derivatives(void)
{
  XDot_sldrtex_controller_T *_rtXdot;
  _rtXdot = ((XDot_sldrtex_controller_T *) sldrtex_controller_M->derivs);

  /* Derivatives for Integrator: '<S1>/Integrator' */
  _rtXdot->Integrator_CSTATE = sldrtex_controller_B.IntegralGain;
}

/* Model initialize function */
void sldrtex_controller_initialize(void)
{
  /* Start for S-Function (sldrtao): '<Root>/Analog Output' */

  /* S-Function Block: <Root>/Analog Output */

  /* no initial value should be set */

  /* InitializeConditions for Integrator: '<S1>/Integrator' */
  sldrtex_controller_X.Integrator_CSTATE = sldrtex_controller_P.Integrator_IC;

  /* InitializeConditions for Derivative: '<S1>/Ideal Derivative' */
  sldrtex_controller_DW.TimeStampA = (rtInf);
  sldrtex_controller_DW.TimeStampB = (rtInf);
}

/* Model terminate function */
void sldrtex_controller_terminate(void)
{
  /* Terminate for S-Function (sldrtao): '<Root>/Analog Output' */

  /* S-Function Block: <Root>/Analog Output */

  /* no final value should be set */
}

/*========================================================================*
 * Start of Classic call interface                                        *
 *========================================================================*/

/* Solver interface called by GRT_Main */
#ifndef USE_GENERATED_SOLVER

void rt_ODECreateIntegrationData(RTWSolverInfo *si)
{
  UNUSED_PARAMETER(si);
  return;
}                                      /* do nothing */

void rt_ODEDestroyIntegrationData(RTWSolverInfo *si)
{
  UNUSED_PARAMETER(si);
  return;
}                                      /* do nothing */

void rt_ODEUpdateContinuousStates(RTWSolverInfo *si)
{
  UNUSED_PARAMETER(si);
  return;
}                                      /* do nothing */

#endif

void MdlOutputs(int_T tid)
{
  sldrtex_controller_output();
  UNUSED_PARAMETER(tid);
}

void MdlUpdate(int_T tid)
{
  sldrtex_controller_update();
  UNUSED_PARAMETER(tid);
}

void MdlInitializeSizes(void)
{
}

void MdlInitializeSampleTimes(void)
{
}

void MdlInitialize(void)
{
}

void MdlStart(void)
{
  sldrtex_controller_initialize();
}

void MdlTerminate(void)
{
  sldrtex_controller_terminate();
}

/* Registration function */
RT_MODEL_sldrtex_controller_T *sldrtex_controller(void)
{
  /* Registration code */

  /* initialize non-finites */
  rt_InitInfAndNaN(sizeof(real_T));

  /* initialize real-time model */
  (void) memset((void *)sldrtex_controller_M, 0,
                sizeof(RT_MODEL_sldrtex_controller_T));

  {
    /* Setup solver object */
    rtsiSetSimTimeStepPtr(&sldrtex_controller_M->solverInfo,
                          &sldrtex_controller_M->Timing.simTimeStep);
    rtsiSetTPtr(&sldrtex_controller_M->solverInfo, &rtmGetTPtr
                (sldrtex_controller_M));
    rtsiSetStepSizePtr(&sldrtex_controller_M->solverInfo,
                       &sldrtex_controller_M->Timing.stepSize0);
    rtsiSetdXPtr(&sldrtex_controller_M->solverInfo,
                 &sldrtex_controller_M->derivs);
    rtsiSetContStatesPtr(&sldrtex_controller_M->solverInfo, (real_T **)
                         &sldrtex_controller_M->contStates);
    rtsiSetNumContStatesPtr(&sldrtex_controller_M->solverInfo,
      &sldrtex_controller_M->Sizes.numContStates);
    rtsiSetNumPeriodicContStatesPtr(&sldrtex_controller_M->solverInfo,
      &sldrtex_controller_M->Sizes.numPeriodicContStates);
    rtsiSetPeriodicContStateIndicesPtr(&sldrtex_controller_M->solverInfo,
      &sldrtex_controller_M->periodicContStateIndices);
    rtsiSetPeriodicContStateRangesPtr(&sldrtex_controller_M->solverInfo,
      &sldrtex_controller_M->periodicContStateRanges);
    rtsiSetErrorStatusPtr(&sldrtex_controller_M->solverInfo, (&rtmGetErrorStatus
      (sldrtex_controller_M)));
    rtsiSetRTModelPtr(&sldrtex_controller_M->solverInfo, sldrtex_controller_M);
  }

  rtsiSetSimTimeStep(&sldrtex_controller_M->solverInfo, MAJOR_TIME_STEP);
  sldrtex_controller_M->intgData.y = sldrtex_controller_M->odeY;
  sldrtex_controller_M->intgData.f[0] = sldrtex_controller_M->odeF[0];
  sldrtex_controller_M->intgData.f[1] = sldrtex_controller_M->odeF[1];
  sldrtex_controller_M->intgData.f[2] = sldrtex_controller_M->odeF[2];
  sldrtex_controller_M->intgData.f[3] = sldrtex_controller_M->odeF[3];
  sldrtex_controller_M->intgData.f[4] = sldrtex_controller_M->odeF[4];
  sldrtex_controller_M->intgData.f[5] = sldrtex_controller_M->odeF[5];
  sldrtex_controller_M->contStates = ((real_T *) &sldrtex_controller_X);
  rtsiSetSolverData(&sldrtex_controller_M->solverInfo, (void *)
                    &sldrtex_controller_M->intgData);
  rtsiSetSolverName(&sldrtex_controller_M->solverInfo,"ode5");

  /* Initialize timing info */
  {
    int_T *mdlTsMap = sldrtex_controller_M->Timing.sampleTimeTaskIDArray;
    mdlTsMap[0] = 0;
    mdlTsMap[1] = 1;
    sldrtex_controller_M->Timing.sampleTimeTaskIDPtr = (&mdlTsMap[0]);
    sldrtex_controller_M->Timing.sampleTimes =
      (&sldrtex_controller_M->Timing.sampleTimesArray[0]);
    sldrtex_controller_M->Timing.offsetTimes =
      (&sldrtex_controller_M->Timing.offsetTimesArray[0]);

    /* task periods */
    sldrtex_controller_M->Timing.sampleTimes[0] = (0.0);
    sldrtex_controller_M->Timing.sampleTimes[1] = (0.001);

    /* task offsets */
    sldrtex_controller_M->Timing.offsetTimes[0] = (0.0);
    sldrtex_controller_M->Timing.offsetTimes[1] = (0.0);
  }

  rtmSetTPtr(sldrtex_controller_M, &sldrtex_controller_M->Timing.tArray[0]);

  {
    int_T *mdlSampleHits = sldrtex_controller_M->Timing.sampleHitArray;
    mdlSampleHits[0] = 1;
    mdlSampleHits[1] = 1;
    sldrtex_controller_M->Timing.sampleHits = (&mdlSampleHits[0]);
  }

  rtmSetTFinal(sldrtex_controller_M, 10.0);
  sldrtex_controller_M->Timing.stepSize0 = 0.001;
  sldrtex_controller_M->Timing.stepSize1 = 0.001;

  /* External mode info */
  sldrtex_controller_M->Sizes.checksums[0] = (2850704340U);
  sldrtex_controller_M->Sizes.checksums[1] = (2481948399U);
  sldrtex_controller_M->Sizes.checksums[2] = (813133166U);
  sldrtex_controller_M->Sizes.checksums[3] = (422616710U);

  {
    static const sysRanDType rtAlwaysEnabled = SUBSYS_RAN_BC_ENABLE;
    static RTWExtModeInfo rt_ExtModeInfo;
    static const sysRanDType *systemRan[1];
    sldrtex_controller_M->extModeInfo = (&rt_ExtModeInfo);
    rteiSetSubSystemActiveVectorAddresses(&rt_ExtModeInfo, systemRan);
    systemRan[0] = &rtAlwaysEnabled;
    rteiSetModelMappingInfoPtr(sldrtex_controller_M->extModeInfo,
      &sldrtex_controller_M->SpecialInfo.mappingInfo);
    rteiSetChecksumsPtr(sldrtex_controller_M->extModeInfo,
                        sldrtex_controller_M->Sizes.checksums);
    rteiSetTPtr(sldrtex_controller_M->extModeInfo, rtmGetTPtr
                (sldrtex_controller_M));
  }

  sldrtex_controller_M->solverInfoPtr = (&sldrtex_controller_M->solverInfo);
  sldrtex_controller_M->Timing.stepSize = (0.001);
  rtsiSetFixedStepSize(&sldrtex_controller_M->solverInfo, 0.001);
  rtsiSetSolverMode(&sldrtex_controller_M->solverInfo, SOLVER_MODE_SINGLETASKING);

  /* block I/O */
  sldrtex_controller_M->blockIO = ((void *) &sldrtex_controller_B);

  {
    sldrtex_controller_B.Sum4 = 0.0;
    sldrtex_controller_B.AnalogInput = 0.0;
    sldrtex_controller_B.DerivativeGain = 0.0;
    sldrtex_controller_B.Sum = 0.0;
    sldrtex_controller_B.IntegralGain = 0.0;
  }

  /* parameters */
  sldrtex_controller_M->defaultParam = ((real_T *)&sldrtex_controller_P);

  /* states (continuous) */
  {
    real_T *x = (real_T *) &sldrtex_controller_X;
    sldrtex_controller_M->contStates = (x);
    (void) memset((void *)&sldrtex_controller_X, 0,
                  sizeof(X_sldrtex_controller_T));
  }

  /* states (dwork) */
  sldrtex_controller_M->dwork = ((void *) &sldrtex_controller_DW);
  (void) memset((void *)&sldrtex_controller_DW, 0,
                sizeof(DW_sldrtex_controller_T));
  sldrtex_controller_DW.TimeStampA = 0.0;
  sldrtex_controller_DW.LastUAtTimeA = 0.0;
  sldrtex_controller_DW.TimeStampB = 0.0;
  sldrtex_controller_DW.LastUAtTimeB = 0.0;

  /* data type transition information */
  {
    static DataTypeTransInfo dtInfo;
    (void) memset((char_T *) &dtInfo, 0,
                  sizeof(dtInfo));
    sldrtex_controller_M->SpecialInfo.mappingInfo = (&dtInfo);
    dtInfo.numDataTypes = 14;
    dtInfo.dataTypeSizes = &rtDataTypeSizes[0];
    dtInfo.dataTypeNames = &rtDataTypeNames[0];

    /* Block I/O transition table */
    dtInfo.BTransTable = &rtBTransTable;

    /* Parameters transition table */
    dtInfo.PTransTable = &rtPTransTable;
  }

  /* Initialize Sizes */
  sldrtex_controller_M->Sizes.numContStates = (1);/* Number of continuous states */
  sldrtex_controller_M->Sizes.numPeriodicContStates = (0);/* Number of periodic continuous states */
  sldrtex_controller_M->Sizes.numY = (0);/* Number of model outputs */
  sldrtex_controller_M->Sizes.numU = (0);/* Number of model inputs */
  sldrtex_controller_M->Sizes.sysDirFeedThru = (0);/* The model is not direct feedthrough */
  sldrtex_controller_M->Sizes.numSampTimes = (2);/* Number of sample times */
  sldrtex_controller_M->Sizes.numBlocks = (13);/* Number of blocks */
  sldrtex_controller_M->Sizes.numBlockIO = (5);/* Number of block outputs */
  sldrtex_controller_M->Sizes.numBlockPrms = (17);/* Sum of parameter "widths" */
  return sldrtex_controller_M;
}

/*========================================================================*
 * End of Classic call interface                                          *
 *========================================================================*/
