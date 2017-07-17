/*
 * Test000.h
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

#ifndef RTW_HEADER_Test000_h_
#define RTW_HEADER_Test000_h_
#include <float.h>
#include <string.h>
#ifndef Test000_COMMON_INCLUDES_
# define Test000_COMMON_INCLUDES_
#include "rtwtypes.h"
#include "rtw_extmode.h"
#include "sysran_types.h"
#include "rtw_continuous.h"
#include "rtw_solver.h"
#include "dt_info.h"
#include "ext_work.h"
#include "sldrtdef.h"
#endif                                 /* Test000_COMMON_INCLUDES_ */

#include "Test000_types.h"

/* Shared type includes */
#include "multiword_types.h"

/* Macros for accessing real-time model data structure */
#ifndef rtmGetFinalTime
# define rtmGetFinalTime(rtm)          ((rtm)->Timing.tFinal)
#endif

#ifndef rtmGetRTWExtModeInfo
# define rtmGetRTWExtModeInfo(rtm)     ((rtm)->extModeInfo)
#endif

#ifndef rtmGetErrorStatus
# define rtmGetErrorStatus(rtm)        ((rtm)->errorStatus)
#endif

#ifndef rtmSetErrorStatus
# define rtmSetErrorStatus(rtm, val)   ((rtm)->errorStatus = (val))
#endif

#ifndef rtmGetStopRequested
# define rtmGetStopRequested(rtm)      ((rtm)->Timing.stopRequestedFlag)
#endif

#ifndef rtmSetStopRequested
# define rtmSetStopRequested(rtm, val) ((rtm)->Timing.stopRequestedFlag = (val))
#endif

#ifndef rtmGetStopRequestedPtr
# define rtmGetStopRequestedPtr(rtm)   (&((rtm)->Timing.stopRequestedFlag))
#endif

#ifndef rtmGetT
# define rtmGetT(rtm)                  ((rtm)->Timing.taskTime0)
#endif

#ifndef rtmGetTFinal
# define rtmGetTFinal(rtm)             ((rtm)->Timing.tFinal)
#endif

/* Block signals (auto storage) */
typedef struct {
  real_T AnalogInput;                  /* '<Root>/Analog Input' */
} B_Test000_T;

/* Block states (auto storage) for system '<Root>' */
typedef struct {
  void *AnalogInput_PWORK;             /* '<Root>/Analog Input' */
  struct {
    void *LoggedData;
  } Scope_PWORK;                       /* '<Root>/Scope' */
} DW_Test000_T;

/* Parameters (auto storage) */
struct P_Test000_T_ {
  real_T AnalogInput_MaxMissedTicks;   /* Mask Parameter: AnalogInput_MaxMissedTicks
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  real_T AnalogInput_YieldWhenWaiting; /* Mask Parameter: AnalogInput_YieldWhenWaiting
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  int32_T AnalogInput_Channels;        /* Mask Parameter: AnalogInput_Channels
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  int32_T AnalogInput_RangeMode;       /* Mask Parameter: AnalogInput_RangeMode
                                        * Referenced by: '<Root>/Analog Input'
                                        */
  int32_T AnalogInput_VoltRange;       /* Mask Parameter: AnalogInput_VoltRange
                                        * Referenced by: '<Root>/Analog Input'
                                        */
};

/* Real-time Model Data Structure */
struct tag_RTM_Test000_T {
  const char_T *errorStatus;
  RTWExtModeInfo *extModeInfo;

  /*
   * Sizes:
   * The following substructure contains sizes information
   * for many of the model attributes such as inputs, outputs,
   * dwork, sample times, etc.
   */
  struct {
    uint32_T checksums[4];
  } Sizes;

  /*
   * SpecialInfo:
   * The following substructure contains special information
   * related to other components that are dependent on RTW.
   */
  struct {
    const void *mappingInfo;
  } SpecialInfo;

  /*
   * Timing:
   * The following substructure contains information regarding
   * the timing information for the model.
   */
  struct {
    time_T taskTime0;
    uint32_T clockTick0;
    time_T stepSize0;
    time_T tFinal;
    boolean_T stopRequestedFlag;
  } Timing;
};

/* Block parameters (auto storage) */
extern P_Test000_T Test000_P;

/* Block signals (auto storage) */
extern B_Test000_T Test000_B;

/* Block states (auto storage) */
extern DW_Test000_T Test000_DW;

/* Model entry point functions */
extern void Test000_initialize(void);
extern void Test000_output(void);
extern void Test000_update(void);
extern void Test000_terminate(void);

/* Real-time Model object */
extern RT_MODEL_Test000_T *const Test000_M;

/* Simulink Desktop Real-Time specific functions */
time_T Test000_sldrtGetTaskTime(int_T tid);

/*-
 * The generated code includes comments that allow you to trace directly
 * back to the appropriate location in the model.  The basic format
 * is <system>/block_name, where system is the system number (uniquely
 * assigned by Simulink) and block_name is the name of the block.
 *
 * Use the MATLAB hilite_system command to trace the generated code back
 * to the model.  For example,
 *
 * hilite_system('<S3>')    - opens system 3
 * hilite_system('<S3>/Kp') - opens and selects block Kp which resides in S3
 *
 * Here is the system hierarchy for this model
 *
 * '<Root>' : 'Test000'
 */
#endif                                 /* RTW_HEADER_Test000_h_ */
