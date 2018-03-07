/*
 * Ver002.h
 *
 * Code generation for model "Ver002".
 *
 * Model version              : 1.15
 * Simulink Coder version : 8.12 (R2017a) 16-Feb-2017
 * C source code generated on : Thu Nov 16 10:56:49 2017
 *
 * Target selection: sldrtert.tlc
 * Embedded hardware selection: Intel->x86-64 (Windows64)
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#ifndef RTW_HEADER_Ver002_h_
#define RTW_HEADER_Ver002_h_
#include <float.h>
#include <string.h>
#ifndef Ver002_COMMON_INCLUDES_
# define Ver002_COMMON_INCLUDES_
#include "rtwtypes.h"
#include "rtw_extmode.h"
#include "sysran_types.h"
#include "rtw_continuous.h"
#include "rtw_solver.h"
#include "dt_info.h"
#include "ext_work.h"
#include "sldrtdef.h"
#endif                                 /* Ver002_COMMON_INCLUDES_ */

#include "Ver002_types.h"

/* Shared type includes */
#include "multiword_types.h"

/* Macros for accessing real-time model data structure */
#ifndef rtmGetBlkStateChangeFlag
# define rtmGetBlkStateChangeFlag(rtm) ((rtm)->blkStateChange)
#endif

#ifndef rtmSetBlkStateChangeFlag
# define rtmSetBlkStateChangeFlag(rtm, val) ((rtm)->blkStateChange = (val))
#endif

#ifndef rtmGetContStateDisabled
# define rtmGetContStateDisabled(rtm)  ((rtm)->contStateDisabled)
#endif

#ifndef rtmSetContStateDisabled
# define rtmSetContStateDisabled(rtm, val) ((rtm)->contStateDisabled = (val))
#endif

#ifndef rtmGetContStates
# define rtmGetContStates(rtm)         ((rtm)->contStates)
#endif

#ifndef rtmSetContStates
# define rtmSetContStates(rtm, val)    ((rtm)->contStates = (val))
#endif

#ifndef rtmGetDerivCacheNeedsReset
# define rtmGetDerivCacheNeedsReset(rtm) ((rtm)->derivCacheNeedsReset)
#endif

#ifndef rtmSetDerivCacheNeedsReset
# define rtmSetDerivCacheNeedsReset(rtm, val) ((rtm)->derivCacheNeedsReset = (val))
#endif

#ifndef rtmGetFinalTime
# define rtmGetFinalTime(rtm)          ((rtm)->Timing.tFinal)
#endif

#ifndef rtmGetIntgData
# define rtmGetIntgData(rtm)           ((rtm)->intgData)
#endif

#ifndef rtmSetIntgData
# define rtmSetIntgData(rtm, val)      ((rtm)->intgData = (val))
#endif

#ifndef rtmGetOdeF
# define rtmGetOdeF(rtm)               ((rtm)->odeF)
#endif

#ifndef rtmSetOdeF
# define rtmSetOdeF(rtm, val)          ((rtm)->odeF = (val))
#endif

#ifndef rtmGetOdeY
# define rtmGetOdeY(rtm)               ((rtm)->odeY)
#endif

#ifndef rtmSetOdeY
# define rtmSetOdeY(rtm, val)          ((rtm)->odeY = (val))
#endif

#ifndef rtmGetPeriodicContStateIndices
# define rtmGetPeriodicContStateIndices(rtm) ((rtm)->periodicContStateIndices)
#endif

#ifndef rtmSetPeriodicContStateIndices
# define rtmSetPeriodicContStateIndices(rtm, val) ((rtm)->periodicContStateIndices = (val))
#endif

#ifndef rtmGetPeriodicContStateRanges
# define rtmGetPeriodicContStateRanges(rtm) ((rtm)->periodicContStateRanges)
#endif

#ifndef rtmSetPeriodicContStateRanges
# define rtmSetPeriodicContStateRanges(rtm, val) ((rtm)->periodicContStateRanges = (val))
#endif

#ifndef rtmGetRTWExtModeInfo
# define rtmGetRTWExtModeInfo(rtm)     ((rtm)->extModeInfo)
#endif

#ifndef rtmGetZCCacheNeedsReset
# define rtmGetZCCacheNeedsReset(rtm)  ((rtm)->zCCacheNeedsReset)
#endif

#ifndef rtmSetZCCacheNeedsReset
# define rtmSetZCCacheNeedsReset(rtm, val) ((rtm)->zCCacheNeedsReset = (val))
#endif

#ifndef rtmGetdX
# define rtmGetdX(rtm)                 ((rtm)->derivs)
#endif

#ifndef rtmSetdX
# define rtmSetdX(rtm, val)            ((rtm)->derivs = (val))
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
# define rtmGetT(rtm)                  (rtmGetTPtr((rtm))[0])
#endif

#ifndef rtmGetTFinal
# define rtmGetTFinal(rtm)             ((rtm)->Timing.tFinal)
#endif

/* Block signals (auto storage) */
typedef struct {
  real_T TransferFcn;                  /* '<S3>/Transfer Fcn' */
  real_T AnalogInput;                  /* '<S3>/Analog Input' */
  real_T FilterCoefficient;            /* '<S2>/Filter Coefficient' */
  real_T Saturation;                   /* '<S4>/Saturation' */
  real_T IntegralGain;                 /* '<S2>/Integral Gain' */
} B_Ver002_T;

/* Block states (auto storage) for system '<Root>' */
typedef struct {
  struct {
    void *LoggedData;
  } Scope_PWORK;                       /* '<Root>/Scope' */

  void *AnalogInput_PWORK;             /* '<S3>/Analog Input' */
  void *AnalogOutput_PWORK;            /* '<S4>/Analog Output' */
} DW_Ver002_T;

/* Continuous states (auto storage) */
typedef struct {
  real_T TransferFcn_CSTATE;           /* '<S3>/Transfer Fcn' */
  real_T Integrator_CSTATE;            /* '<S2>/Integrator' */
  real_T Filter_CSTATE;                /* '<S2>/Filter' */
} X_Ver002_T;

/* State derivatives (auto storage) */
typedef struct {
  real_T TransferFcn_CSTATE;           /* '<S3>/Transfer Fcn' */
  real_T Integrator_CSTATE;            /* '<S2>/Integrator' */
  real_T Filter_CSTATE;                /* '<S2>/Filter' */
} XDot_Ver002_T;

/* State disabled  */
typedef struct {
  boolean_T TransferFcn_CSTATE;        /* '<S3>/Transfer Fcn' */
  boolean_T Integrator_CSTATE;         /* '<S2>/Integrator' */
  boolean_T Filter_CSTATE;             /* '<S2>/Filter' */
} XDis_Ver002_T;

#ifndef ODE3_INTG
#define ODE3_INTG

/* ODE3 Integration Data */
typedef struct {
  real_T *y;                           /* output */
  real_T *f[3];                        /* derivatives */
} ODE3_IntgData;

#endif

/* Parameters (auto storage) */
struct P_Ver002_T_ {
  real_T PIDController_D;              /* Mask Parameter: PIDController_D
                                        * Referenced by: '<S2>/Derivative Gain'
                                        */
  real_T AnalogOutput_FinalValue;      /* Mask Parameter: AnalogOutput_FinalValue
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  real_T PIDController_I;              /* Mask Parameter: PIDController_I
                                        * Referenced by: '<S2>/Integral Gain'
                                        */
  real_T AnalogInput_MaxMissedTicks;   /* Mask Parameter: AnalogInput_MaxMissedTicks
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  real_T AnalogOutput_MaxMissedTicks;  /* Mask Parameter: AnalogOutput_MaxMissedTicks
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  real_T PIDController_N;              /* Mask Parameter: PIDController_N
                                        * Referenced by: '<S2>/Filter Coefficient'
                                        */
  real_T PIDController_P;              /* Mask Parameter: PIDController_P
                                        * Referenced by: '<S2>/Proportional Gain'
                                        */
  real_T AnalogInput_YieldWhenWaiting; /* Mask Parameter: AnalogInput_YieldWhenWaiting
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  real_T AnalogOutput_YieldWhenWaiting;/* Mask Parameter: AnalogOutput_YieldWhenWaiting
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  int32_T AnalogInput_Channels;        /* Mask Parameter: AnalogInput_Channels
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  int32_T AnalogOutput_Channels;       /* Mask Parameter: AnalogOutput_Channels
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  int32_T AnalogInput_RangeMode;       /* Mask Parameter: AnalogInput_RangeMode
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  int32_T AnalogOutput_RangeMode;      /* Mask Parameter: AnalogOutput_RangeMode
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  int32_T AnalogInput_VoltRange;       /* Mask Parameter: AnalogInput_VoltRange
                                        * Referenced by: '<S3>/Analog Input'
                                        */
  int32_T AnalogOutput_VoltRange;      /* Mask Parameter: AnalogOutput_VoltRange
                                        * Referenced by: '<S4>/Analog Output'
                                        */
  real_T TransferFcn_A;                /* Computed Parameter: TransferFcn_A
                                        * Referenced by: '<S3>/Transfer Fcn'
                                        */
  real_T TransferFcn_C;                /* Computed Parameter: TransferFcn_C
                                        * Referenced by: '<S3>/Transfer Fcn'
                                        */
  real_T Reference_Value;              /* Expression: 0
                                        * Referenced by: '<Root>/Reference'
                                        */
  real_T Integrator_IC;                /* Expression: InitialConditionForIntegrator
                                        * Referenced by: '<S2>/Integrator'
                                        */
  real_T Filter_IC;                    /* Expression: InitialConditionForFilter
                                        * Referenced by: '<S2>/Filter'
                                        */
  real_T Saturation_UpperSat;          /* Expression: 5
                                        * Referenced by: '<S4>/Saturation'
                                        */
  real_T Saturation_LowerSat;          /* Expression: 0
                                        * Referenced by: '<S4>/Saturation'
                                        */
};

/* Real-time Model Data Structure */
struct tag_RTM_Ver002_T {
  const char_T *errorStatus;
  RTWExtModeInfo *extModeInfo;
  RTWSolverInfo solverInfo;
  X_Ver002_T *contStates;
  int_T *periodicContStateIndices;
  real_T *periodicContStateRanges;
  real_T *derivs;
  boolean_T *contStateDisabled;
  boolean_T zCCacheNeedsReset;
  boolean_T derivCacheNeedsReset;
  boolean_T blkStateChange;
  real_T odeY[3];
  real_T odeF[3][3];
  ODE3_IntgData intgData;

  /*
   * Sizes:
   * The following substructure contains sizes information
   * for many of the model attributes such as inputs, outputs,
   * dwork, sample times, etc.
   */
  struct {
    uint32_T checksums[4];
    int_T numContStates;
    int_T numPeriodicContStates;
    int_T numSampTimes;
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
    uint32_T clockTick0;
    time_T stepSize0;
    uint32_T clockTick1;
    time_T tFinal;
    SimTimeStep simTimeStep;
    boolean_T stopRequestedFlag;
    time_T *t;
    time_T tArray[2];
  } Timing;
};

/* Block parameters (auto storage) */
extern P_Ver002_T Ver002_P;

/* Block signals (auto storage) */
extern B_Ver002_T Ver002_B;

/* Continuous states (auto storage) */
extern X_Ver002_T Ver002_X;

/* Block states (auto storage) */
extern DW_Ver002_T Ver002_DW;

/* Model entry point functions */
extern void Ver002_initialize(void);
extern void Ver002_output(void);
extern void Ver002_update(void);
extern void Ver002_terminate(void);

/* Real-time Model object */
extern RT_MODEL_Ver002_T *const Ver002_M;

/* Simulink Desktop Real-Time specific functions */
time_T Ver002_sldrtGetTaskTime(int_T tid);

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
 * '<Root>' : 'Ver002'
 * '<S1>'   : 'Ver002/MGSet'
 * '<S2>'   : 'Ver002/PID Controller'
 * '<S3>'   : 'Ver002/MGSet/Gennerator'
 * '<S4>'   : 'Ver002/MGSet/Motor'
 */
#endif                                 /* RTW_HEADER_Ver002_h_ */
