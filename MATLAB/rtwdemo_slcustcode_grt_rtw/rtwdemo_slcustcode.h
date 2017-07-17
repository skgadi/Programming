/*
 * rtwdemo_slcustcode.h
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

#ifndef RTW_HEADER_rtwdemo_slcustcode_h_
#define RTW_HEADER_rtwdemo_slcustcode_h_
#include <stddef.h>
#include <string.h>
#ifndef rtwdemo_slcustcode_COMMON_INCLUDES_
# define rtwdemo_slcustcode_COMMON_INCLUDES_
#include "rtwtypes.h"
#include "rtw_continuous.h"
#include "rtw_solver.h"
#endif                                 /* rtwdemo_slcustcode_COMMON_INCLUDES_ */

#include "rtwdemo_slcustcode_types.h"

/* Shared type includes */
#include "multiword_types.h"

/* Macros for accessing real-time model data structure */
#ifndef rtmGetErrorStatus
# define rtmGetErrorStatus(rtm)        ((rtm)->errorStatus)
#endif

#ifndef rtmSetErrorStatus
# define rtmSetErrorStatus(rtm, val)   ((rtm)->errorStatus = (val))
#endif

/* Block states (auto storage) for system '<Root>' */
typedef struct {
  real_T X;                            /* '<Root>/X' */
} DW_rtwdemo_slcustcode_T;

/* Zero-crossing (trigger) state */
typedef struct {
  ZCSigState Amplifier_Trig_ZCE;       /* '<Root>/Amplifier' */
} PrevZCX_rtwdemo_slcustcode_T;

/* External inputs (root inport signals with auto storage) */
typedef struct {
  int32_T Input;                       /* '<Root>/Input' */
} ExtU_rtwdemo_slcustcode_T;

/* External outputs (root outports fed by signals with auto storage) */
typedef struct {
  int32_T Output;                      /* '<Root>/Output' */
} ExtY_rtwdemo_slcustcode_T;

/* Real-time Model Data Structure */
struct tag_RTM_rtwdemo_slcustcode_T {
  const char_T *errorStatus;
};

/* Block states (auto storage) */
extern DW_rtwdemo_slcustcode_T rtwdemo_slcustcode_DW;

/* External inputs (root inport signals with auto storage) */
extern ExtU_rtwdemo_slcustcode_T rtwdemo_slcustcode_U;

/* External outputs (root outports fed by signals with auto storage) */
extern ExtY_rtwdemo_slcustcode_T rtwdemo_slcustcode_Y;

/* Model entry point functions */
extern void rtwdemo_slcustcode_initialize(void);
extern void rtwdemo_slcustcode_step(void);
extern void rtwdemo_slcustcode_terminate(void);

/* Real-time Model object */
extern RT_MODEL_rtwdemo_slcustcode_T *const rtwdemo_slcustcode_M;

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
 * '<Root>' : 'rtwdemo_slcustcode'
 * '<S1>'   : 'rtwdemo_slcustcode/Amplifier'
 */
#endif                                 /* RTW_HEADER_rtwdemo_slcustcode_h_ */
