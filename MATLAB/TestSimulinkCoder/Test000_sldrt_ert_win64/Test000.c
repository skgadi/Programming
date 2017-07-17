/*
 * Test000.c
 *
 * Code generation for model "Test000".
 *
 * Model version              : 1.1
 * Simulink Coder version : 8.12 (R2017a) 16-Feb-2017
 * C source code generated on : Tue Jul 11 09:28:53 2017
 *
 * Target selection: sldrtert.tlc
 * Embedded hardware selection: Intel->x86-64 (Windows64)
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#include "Test000.h"
#include "Test000_private.h"
#include "Test000_dt.h"

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
B_Test000_T Test000_B;

/* Block states (auto storage) */
DW_Test000_T Test000_DW;

/* Real-time model */
RT_MODEL_Test000_T Test000_M_;
RT_MODEL_Test000_T *const Test000_M = &Test000_M_;

/* Simulink Desktop Real-Time specific functions */
time_T Test000_sldrtGetTaskTime(int_T tid)
{
  switch (tid) {
   case 0 :
    return(Test000_M->Timing.taskTime0);
  }

  return(0.);
}

/* Model output function */
void Test000_output(void)
{
  /* S-Function (sldrtai): '<Root>/Analog Input' */
  /* S-Function Block: <Root>/Analog Input */
  {
    ANALOGIOPARM parm;
    parm.mode = (RANGEMODE) Test000_P.AnalogInput_RangeMode;
    parm.rangeidx = Test000_P.AnalogInput_VoltRange;
    RTBIO_DriverIO(0, ANALOGINPUT, IOREAD, 1, &Test000_P.AnalogInput_Channels,
                   &Test000_B.AnalogInput, &parm);
  }
}

/* Model update function */
void Test000_update(void)
{
  /* signal main to stop simulation */
  {                                    /* Sample time: [0.001s, 0.0s] */
    if ((rtmGetTFinal(Test000_M)!=-1) &&
        !((rtmGetTFinal(Test000_M)-Test000_M->Timing.taskTime0) >
          Test000_M->Timing.taskTime0 * (DBL_EPSILON))) {
      rtmSetErrorStatus(Test000_M, "Simulation finished");
    }

    if (rtmGetStopRequested(Test000_M)) {
      rtmSetErrorStatus(Test000_M, "Simulation finished");
    }
  }

  /* Update absolute time for base rate */
  /* The "clockTick0" counts the number of times the code of this task has
   * been executed. The absolute time is the multiplication of "clockTick0"
   * and "Timing.stepSize0". Size of "clockTick0" ensures timer will not
   * overflow during the application lifespan selected.
   */
  Test000_M->Timing.taskTime0 =
    (++Test000_M->Timing.clockTick0) * Test000_M->Timing.stepSize0;
}

/* Model initialize function */
void Test000_initialize(void)
{
  /* Registration code */

  /* initialize real-time model */
  (void) memset((void *)Test000_M, 0,
                sizeof(RT_MODEL_Test000_T));
  rtmSetTFinal(Test000_M, 10.0);
  Test000_M->Timing.stepSize0 = 0.001;

  /* External mode info */
  Test000_M->Sizes.checksums[0] = (456286917U);
  Test000_M->Sizes.checksums[1] = (285957304U);
  Test000_M->Sizes.checksums[2] = (2112537417U);
  Test000_M->Sizes.checksums[3] = (999337961U);

  {
    static const sysRanDType rtAlwaysEnabled = SUBSYS_RAN_BC_ENABLE;
    static RTWExtModeInfo rt_ExtModeInfo;
    static const sysRanDType *systemRan[1];
    Test000_M->extModeInfo = (&rt_ExtModeInfo);
    rteiSetSubSystemActiveVectorAddresses(&rt_ExtModeInfo, systemRan);
    systemRan[0] = &rtAlwaysEnabled;
    rteiSetModelMappingInfoPtr(Test000_M->extModeInfo,
      &Test000_M->SpecialInfo.mappingInfo);
    rteiSetChecksumsPtr(Test000_M->extModeInfo, Test000_M->Sizes.checksums);
    rteiSetTPtr(Test000_M->extModeInfo, rtmGetTPtr(Test000_M));
  }

  /* block I/O */
  (void) memset(((void *) &Test000_B), 0,
                sizeof(B_Test000_T));

  /* states (dwork) */
  (void) memset((void *)&Test000_DW, 0,
                sizeof(DW_Test000_T));

  /* data type transition information */
  {
    static DataTypeTransInfo dtInfo;
    (void) memset((char_T *) &dtInfo, 0,
                  sizeof(dtInfo));
    Test000_M->SpecialInfo.mappingInfo = (&dtInfo);
    dtInfo.numDataTypes = 14;
    dtInfo.dataTypeSizes = &rtDataTypeSizes[0];
    dtInfo.dataTypeNames = &rtDataTypeNames[0];

    /* Block I/O transition table */
    dtInfo.BTransTable = &rtBTransTable;

    /* Parameters transition table */
    dtInfo.PTransTable = &rtPTransTable;
  }
}

/* Model terminate function */
void Test000_terminate(void)
{
  /* (no terminate code required) */
}
