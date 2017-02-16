;/////////////////////////////////////////////////////////////////////////////////
;// Code Generator: BoostC Compiler - http://www.sourceboost.com
;// Version       : 7.03
;// License Type  : Pro License
;// Limitations   : PIC18 max code size:Unlimited, max RAM banks:Unlimited
;/////////////////////////////////////////////////////////////////////////////////

	include "P18F14K50.inc"
__HEAPSTART                      EQU	0x000002B4 ; Start address of heap 
__HEAPEND                        EQU	0x000002FF ; End address of heap 
memcpy8_00000_arg_dst            EQU	0x0000027D ; bytes:2
memcpy8_00000_arg_src            EQU	0x000002A8 ; bytes:2
memcpy8_00000_arg_len            EQU	0x0000027F ; bytes:1
CompTempVarRet412                EQU	0x000002AE ; bytes:2
memcpy8_00000_1_dst2             EQU	0x000002AA ; bytes:2
memcpy8_00000_1_src2             EQU	0x000002AC ; bytes:2
CompTempVar413                   EQU	0x000002AE ; bytes:1
gbl_status                       EQU	0x00000FD8 ; bytes:1
gbl_prodl                        EQU	0x00000FF3 ; bytes:1
gbl_prodh                        EQU	0x00000FF4 ; bytes:1
gbl_14_LSR                       EQU	0x000000FC ; bytes:4
gbl_float_detect_tininess        EQU	0x0000005F ; bytes:1
gbl_float_rounding_mode          EQU	0x0000024A ; bytes:1
gbl_float_exception_flags        EQU	0x0000024B ; bytes:1
gbl_15_gbl_aSig                  EQU	0x00000220 ; bytes:4
gbl_15_gbl_bSig                  EQU	0x00000224 ; bytes:4
gbl_15_gbl_zSig                  EQU	0x00000228 ; bytes:4
gbl_15_gbl_aExp                  EQU	0x0000024C ; bytes:1
gbl_15_gbl_bExp                  EQU	0x0000024D ; bytes:1
gbl_15_gbl_zExp                  EQU	0x000001FE ; bytes:2
gbl_15_gbl_aSign                 EQU	0x0000024E ; bytes:1
gbl_15_gbl_bSign                 EQU	0x0000024F ; bytes:1
gbl_15_gbl_zSign                 EQU	0x00000250 ; bytes:1
gbl_15_gbl_zSigZero              EQU	0x00000251 ; bytes:1
gbl_15_gbl_ret                   EQU	0x0000022C ; bytes:4
gbl_uep0                         EQU	0x00000F53 ; bytes:1
gbl_uep1                         EQU	0x00000F54 ; bytes:1
gbl_uep2                         EQU	0x00000F55 ; bytes:1
gbl_uep3                         EQU	0x00000F56 ; bytes:1
gbl_uep4                         EQU	0x00000F57 ; bytes:1
gbl_uep5                         EQU	0x00000F58 ; bytes:1
gbl_uep6                         EQU	0x00000F59 ; bytes:1
gbl_uep7                         EQU	0x00000F5A ; bytes:1
gbl_ueie                         EQU	0x00000F5B ; bytes:1
gbl_uaddr                        EQU	0x00000F5C ; bytes:1
gbl_ufrml                        EQU	0x00000F5D ; bytes:1
gbl_ufrmh                        EQU	0x00000F5E ; bytes:1
gbl_ueir                         EQU	0x00000F5F ; bytes:1
gbl_uie                          EQU	0x00000F60 ; bytes:1
gbl_ucfg                         EQU	0x00000F61 ; bytes:1
gbl_uir                          EQU	0x00000F62 ; bytes:1
gbl_ustat                        EQU	0x00000F63 ; bytes:1
gbl_ucon                         EQU	0x00000F64 ; bytes:1
gbl_srcon0                       EQU	0x00000F68 ; bytes:1
gbl_srcon1                       EQU	0x00000F69 ; bytes:1
gbl_cm2con0                      EQU	0x00000F6B ; bytes:1
gbl_cm2con1                      EQU	0x00000F6C ; bytes:1
gbl_cm1con0                      EQU	0x00000F6D ; bytes:1
gbl_sspmsk                       EQU	0x00000F6F ; bytes:1
gbl_slrcon                       EQU	0x00000F76 ; bytes:1
gbl_wpua                         EQU	0x00000F77 ; bytes:1
gbl_wpub                         EQU	0x00000F78 ; bytes:1
gbl_ioca                         EQU	0x00000F79 ; bytes:1
gbl_iocb                         EQU	0x00000F7A ; bytes:1
gbl_ansel                        EQU	0x00000F7E ; bytes:1
gbl_anselh                       EQU	0x00000F7F ; bytes:1
gbl_porta                        EQU	0x00000F80 ; bytes:1
gbl_portb                        EQU	0x00000F81 ; bytes:1
gbl_portc                        EQU	0x00000F82 ; bytes:1
gbl_lata                         EQU	0x00000F89 ; bytes:1
gbl_latb                         EQU	0x00000F8A ; bytes:1
gbl_latc                         EQU	0x00000F8B ; bytes:1
gbl_ddra                         EQU	0x00000F92 ; bytes:1
gbl_trisa                        EQU	0x00000F92 ; bytes:1
gbl_ddrb                         EQU	0x00000F93 ; bytes:1
gbl_trisb                        EQU	0x00000F93 ; bytes:1
gbl_ddrc                         EQU	0x00000F94 ; bytes:1
gbl_trisc                        EQU	0x00000F94 ; bytes:1
gbl_osctune                      EQU	0x00000F9B ; bytes:1
gbl_pie1                         EQU	0x00000F9D ; bytes:1
gbl_pir1                         EQU	0x00000F9E ; bytes:1
gbl_ipr1                         EQU	0x00000F9F ; bytes:1
gbl_pie2                         EQU	0x00000FA0 ; bytes:1
gbl_pir2                         EQU	0x00000FA1 ; bytes:1
gbl_ipr2                         EQU	0x00000FA2 ; bytes:1
gbl_eecon1                       EQU	0x00000FA6 ; bytes:1
gbl_eecon2                       EQU	0x00000FA7 ; bytes:1
gbl_eedata                       EQU	0x00000FA8 ; bytes:1
gbl_eeadr                        EQU	0x00000FA9 ; bytes:1
gbl_eeadrh                       EQU	0x00000FAA ; bytes:1
gbl_rcsta                        EQU	0x00000FAB ; bytes:1
gbl_txsta                        EQU	0x00000FAC ; bytes:1
gbl_txreg                        EQU	0x00000FAD ; bytes:1
gbl_rcreg                        EQU	0x00000FAE ; bytes:1
gbl_spbrg                        EQU	0x00000FAF ; bytes:1
gbl_spbrgh                       EQU	0x00000FB0 ; bytes:1
gbl_t3con                        EQU	0x00000FB1 ; bytes:1
gbl_tmr3l                        EQU	0x00000FB2 ; bytes:1
gbl_tmr3h                        EQU	0x00000FB3 ; bytes:1
gbl_eccp1as                      EQU	0x00000FB6 ; bytes:1
gbl_pwm1con                      EQU	0x00000FB7 ; bytes:1
gbl_baudcon                      EQU	0x00000FB8 ; bytes:1
gbl_baudctl                      EQU	0x00000FB8 ; bytes:1
gbl_pstrcon                      EQU	0x00000FB9 ; bytes:1
gbl_refcon0                      EQU	0x00000FBA ; bytes:1
gbl_vrefcon0                     EQU	0x00000FBA ; bytes:1
gbl_refcon1                      EQU	0x00000FBB ; bytes:1
gbl_vrefcon1                     EQU	0x00000FBB ; bytes:1
gbl_refcon2                      EQU	0x00000FBC ; bytes:1
gbl_vrefcon2                     EQU	0x00000FBC ; bytes:1
gbl_ccp1con                      EQU	0x00000FBD ; bytes:1
gbl_ccpr1                        EQU	0x00000FBE ; bytes:1
gbl_ccpr1l                       EQU	0x00000FBE ; bytes:1
gbl_ccpr1h                       EQU	0x00000FBF ; bytes:1
gbl_adcon2                       EQU	0x00000FC0 ; bytes:1
gbl_adcon1                       EQU	0x00000FC1 ; bytes:1
gbl_adcon0                       EQU	0x00000FC2 ; bytes:1
gbl_adres                        EQU	0x00000FC3 ; bytes:1
gbl_adresl                       EQU	0x00000FC3 ; bytes:1
gbl_adresh                       EQU	0x00000FC4 ; bytes:1
gbl_sspcon2                      EQU	0x00000FC5 ; bytes:1
gbl_sspcon1                      EQU	0x00000FC6 ; bytes:1
gbl_sspstat                      EQU	0x00000FC7 ; bytes:1
gbl_sspadd                       EQU	0x00000FC8 ; bytes:1
gbl_sspbuf                       EQU	0x00000FC9 ; bytes:1
gbl_t2con                        EQU	0x00000FCA ; bytes:1
gbl_pr2                          EQU	0x00000FCB ; bytes:1
gbl_tmr2                         EQU	0x00000FCC ; bytes:1
gbl_t1con                        EQU	0x00000FCD ; bytes:1
gbl_tmr1l                        EQU	0x00000FCE ; bytes:1
gbl_tmr1h                        EQU	0x00000FCF ; bytes:1
gbl_rcon                         EQU	0x00000FD0 ; bytes:1
gbl_wdtcon                       EQU	0x00000FD1 ; bytes:1
gbl_osccon2                      EQU	0x00000FD2 ; bytes:1
gbl_osccon                       EQU	0x00000FD3 ; bytes:1
gbl_t0con                        EQU	0x00000FD5 ; bytes:1
gbl_tmr0l                        EQU	0x00000FD6 ; bytes:1
gbl_tmr0h                        EQU	0x00000FD7 ; bytes:1
gbl_fsr2l                        EQU	0x00000FD9 ; bytes:1
gbl_fsr2h                        EQU	0x00000FDA ; bytes:1
gbl_plusw2                       EQU	0x00000FDB ; bytes:1
gbl_preinc2                      EQU	0x00000FDC ; bytes:1
gbl_postdec2                     EQU	0x00000FDD ; bytes:1
gbl_postinc2                     EQU	0x00000FDE ; bytes:1
gbl_indf2                        EQU	0x00000FDF ; bytes:1
gbl_bsr                          EQU	0x00000FE0 ; bytes:1
gbl_fsr1l                        EQU	0x00000FE1 ; bytes:1
gbl_fsr1h                        EQU	0x00000FE2 ; bytes:1
gbl_plusw1                       EQU	0x00000FE3 ; bytes:1
gbl_preinc1                      EQU	0x00000FE4 ; bytes:1
gbl_postdec1                     EQU	0x00000FE5 ; bytes:1
gbl_postinc1                     EQU	0x00000FE6 ; bytes:1
gbl_indf1                        EQU	0x00000FE7 ; bytes:1
gbl_wreg                         EQU	0x00000FE8 ; bytes:1
gbl_fsr0l                        EQU	0x00000FE9 ; bytes:1
gbl_fsr0h                        EQU	0x00000FEA ; bytes:1
gbl_plusw0                       EQU	0x00000FEB ; bytes:1
gbl_preinc0                      EQU	0x00000FEC ; bytes:1
gbl_postdec0                     EQU	0x00000FED ; bytes:1
gbl_postinc0                     EQU	0x00000FEE ; bytes:1
gbl_indf0                        EQU	0x00000FEF ; bytes:1
gbl_intcon3                      EQU	0x00000FF0 ; bytes:1
gbl_intcon2                      EQU	0x00000FF1 ; bytes:1
gbl_intcon                       EQU	0x00000FF2 ; bytes:1
gbl_prod                         EQU	0x00000FF3 ; bytes:1
gbl_tablat                       EQU	0x00000FF5 ; bytes:1
gbl_tblptr                       EQU	0x00000FF6 ; bytes:1
gbl_tblptrl                      EQU	0x00000FF6 ; bytes:1
gbl_tblptrh                      EQU	0x00000FF7 ; bytes:1
gbl_tblptru                      EQU	0x00000FF8 ; bytes:1
gbl_pc                           EQU	0x00000FF9 ; bytes:1
gbl_pcl                          EQU	0x00000FF9 ; bytes:1
gbl_pclath                       EQU	0x00000FFA ; bytes:1
gbl_pclatu                       EQU	0x00000FFB ; bytes:1
gbl_stkptr                       EQU	0x00000FFC ; bytes:1
gbl_tos                          EQU	0x00000FFD ; bytes:1
gbl_tosl                         EQU	0x00000FFD ; bytes:1
gbl_tosh                         EQU	0x00000FFE ; bytes:1
gbl_tosu                         EQU	0x00000FFF ; bytes:1
gbl_FCV_READINPUT                EQU	0x00000238 ; bytes:2
gbl_FCV_OUTSTR                   EQU	0x00000252 ; bytes:1
gbl_FCV_OPERATION                EQU	0x00000253 ; bytes:1
gbl_FCV_OUTSTRING                EQU	0x0000023A ; bytes:2
gbl_MX_USBNAME                   EQU	0x0000016E ; bytes:40
gbl_MX_USBMFR                    EQU	0x00000140 ; bytes:46
gbl_MX_SERIAL                    EQU	0x000000E0 ; bytes:10
gbl_usb_state                    EQU	0x00000254 ; bytes:1
gbl_usb_address                  EQU	0x00000255 ; bytes:1
gbl_usb_sdp                      EQU	0x0000019F ; bytes:8
gbl_control_mode                 EQU	0x00000256 ; bytes:1
gbl_bd0out                       EQU	0x00000200 ; bytes:4
gbl_bd0in                        EQU	0x00000204 ; bytes:4
gbl_bd1out                       EQU	0x00000208 ; bytes:4
gbl_bd1in                        EQU	0x0000020C ; bytes:4
gbl_bd2out                       EQU	0x00000210 ; bytes:4
gbl_bd2in                        EQU	0x00000214 ; bytes:4
gbl_bd3out                       EQU	0x00000218 ; bytes:4
gbl_bd3in                        EQU	0x0000021C ; bytes:4
gbl_buffer_0_out                 EQU	0x00000280 ; bytes:8
gbl_buffer_0_in                  EQU	0x00000288 ; bytes:8
gbl_buffer_2_in                  EQU	0x00000290 ; bytes:8
gbl_buffer_3_in                  EQU	0x000002A0 ; bytes:8
gbl_buffer_3_out                 EQU	0x00000298 ; bytes:8
gbl_ep_in_bd_location            EQU	0x000001A7 ; bytes:8
gbl_ep_out_bd_location           EQU	0x000001AF ; bytes:8
gbl_ep_in_buffer_location        EQU	0x000001B7 ; bytes:8
gbl_ep_out_buffer_location       EQU	0x000001BF ; bytes:8
gbl_ep_in_buffer_size            EQU	0x000001C7 ; bytes:8
gbl_ep_out_buffer_size           EQU	0x000001CF ; bytes:8
CompGblVar60                     EQU	0x00000257 ; bytes:1
CompGblVar61                     EQU	0x00000258 ; bytes:1
CompGblVar62                     EQU	0x00000259 ; bytes:1
CompGblVar63                     EQU	0x0000025A ; bytes:1
CompGblVar64                     EQU	0x0000025B ; bytes:1
gbl_delivery_bytes_to_send       EQU	0x0000023C ; bytes:2
gbl_delivery_bytes_max_send      EQU	0x0000023E ; bytes:2
gbl_delivery_bytes_sent          EQU	0x00000240 ; bytes:2
gbl_delivery_ptr                 EQU	0x00000242 ; bytes:2
gbl_delivery_buffer_size         EQU	0x0000025C ; bytes:1
gbl_delivery_buffer              EQU	0x00000244 ; bytes:2
gbl_delivery_bd                  EQU	0x00000246 ; bytes:2
gbl_usb_status                   EQU	0x0000025D ; bytes:1
gbl_buffer_byte                  EQU	0x0000025E ; bytes:1
gbl_cdc_tx_buffer                EQU	0x00000060 ; bytes:64
gbl_cdc_tx_start                 EQU	0x0000025F ; bytes:1
gbl_cdc_tx_end                   EQU	0x00000260 ; bytes:1
gbl_cdc_rx_buffer                EQU	0x000000A0 ; bytes:64
gbl_cdc_rx_start                 EQU	0x00000261 ; bytes:1
gbl_cdc_rx_end                   EQU	0x00000262 ; bytes:1
gbl_class_data                   EQU	0x000001D7 ; bytes:8
gbl_my_device_descriptor         EQU	0x00000048 ; bytes:18
gbl_my_config                    EQU	0x000000EA ; bytes:9
gbl_my_comm_interface            EQU	0x000000F3 ; bytes:9
gbl_my_header                    EQU	0x0000005A ; bytes:5
gbl_my_ACM                       EQU	0x00000230 ; bytes:4
gbl_my_union                     EQU	0x000001F4 ; bytes:5
gbl_my_call_mgt                  EQU	0x000001F9 ; bytes:5
gbl_my_notification_ep           EQU	0x000001DF ; bytes:7
gbl_my_data_interface            EQU	0x00000196 ; bytes:9
gbl_my_data_out_ep               EQU	0x000001E6 ; bytes:7
gbl_my_data_in_ep                EQU	0x000001ED ; bytes:7
gbl_complete_serial_config_00000 EQU	0x00000005 ; bytes:67
gbl_string_00                    EQU	0x00000234 ; bytes:4
gbl_CDC_USB_status               EQU	0x00000263 ; bytes:1
gbl_CDC_USB_Rx_String            EQU	0x00000100 ; bytes:64
CompGblVar65                     EQU	0x00000264 ; bit:0
CompGblVar66                     EQU	0x00000264 ; bit:1
gbl_old_tris                     EQU	0x00000265 ; bytes:1
gbl_tris_mask                    EQU	0x00000266 ; bytes:1
gbl_tris_reg                     EQU	0x00000248 ; bytes:2
FCD_USBSer_00076_1_position      EQU	0x00000267 ; bytes:1
FCD_USBSer_00076_1_length        EQU	0x00000268 ; bytes:1
Wdt_Delay__00039_arg_delay       EQU	0x0000026C ; bytes:1
Wdt_Delay__00039_1_i             EQU	0x0000026D ; bytes:1
usb_send_d_00052_arg_ep          EQU	0x0000027F ; bytes:1
usb_send_d_00052_arg_data        EQU	0x000002A8 ; bytes:2
usb_send_d_00052_arg_send_count  EQU	0x000002AA ; bytes:1
usb_send_d_00052_arg_first       EQU	0x000002AB ; bit:0
usb_send_d_00052_1_count         EQU	0x000002AC ; bytes:1
usb_send_d_00052_1_bd            EQU	0x000002AD ; bytes:2
usb_send_d_00052_1_buffer        EQU	0x000002AF ; bytes:2
CompTempVar2242                  EQU	0x000002B1 ; bytes:1
CompTempVar2245                  EQU	0x000002B1 ; bytes:1
CompTempVar2248                  EQU	0x000002B1 ; bytes:1
CompTempVar2249                  EQU	0x000002B2 ; bytes:1
CompTempVar2250                  EQU	0x000002B1 ; bytes:1
usb_handle_00058_arg_data        EQU	0x00000277 ; bytes:2
usb_handle_00058_arg_count       EQU	0x00000279 ; bytes:2
usb_handle_00058_1_my_lc         EQU	0x0000027B ; bytes:2
usb_handle_00059_arg_sdp         EQU	0x00000277 ; bytes:8
usb_get_de_0005A_arg_descr_0005B EQU	0x000002A9 ; bytes:1
usb_get_de_0005A_arg_descr_0005C EQU	0x000002AA ; bytes:1
usb_get_de_0005A_arg_rtn_d_0005D EQU	0x000002AB ; bytes:2
usb_get_de_0005A_arg_rtn_d_0005E EQU	0x000002AD ; bytes:2
usb_get_de_0005A_1_descrip_0005F EQU	0x000002AF ; bytes:2
usb_get_de_0005A_1_descrip_00060 EQU	0x000002B1 ; bytes:2
CompTempVar2265                  EQU	0x000002B3 ; bytes:1
CompTempVar2266                  EQU	0x000002B3 ; bytes:1
CompTempVar2267                  EQU	0x000002B3 ; bytes:1
CompTempVar2268                  EQU	0x000002B3 ; bytes:1
CompTempVar2269                  EQU	0x000002B3 ; bytes:1
CompTempVar2270                  EQU	0x000002B3 ; bytes:1
CompTempVar2271                  EQU	0x000002B3 ; bytes:1
usb_ep_dat_00061_arg_end_point   EQU	0x00000279 ; bytes:1
usb_ep_dat_00061_arg_buffer      EQU	0x0000027A ; bytes:2
usb_ep_dat_00061_arg_byte_count  EQU	0x0000027C ; bytes:2
usb_ep_dat_00061_1_cdc_rx_next   EQU	0x0000027E ; bytes:1
usb_ep_dat_00061_2_count         EQU	0x0000027F ; bytes:1
CompTempVar2274                  EQU	0x000002A8 ; bytes:1
CompTempVar2275                  EQU	0x000002A9 ; bytes:1
usb_ep_dat_00062_arg_end_point   EQU	0x00000279 ; bytes:1
usb_ep_dat_00062_arg_byte_count  EQU	0x0000027A ; bytes:2
usb_SOF_ca_00050_arg_frame       EQU	0x00000275 ; bytes:2
usb_send_d_00054_1_count         EQU	0x000002AA ; bytes:1
CompTempVar2258                  EQU	0x000002AB ; bytes:1
usb_send_o_00066_arg_data        EQU	0x000002A9 ; bytes:1
usb_handle_00068_arg_sdp         EQU	0x00000277 ; bytes:8
usb_handle_00068_1_descrip_00069 EQU	0x0000027F ; bytes:1
usb_handle_00068_1_descrip_0006A EQU	0x000002A8 ; bytes:1
usb_handle_0004D_1_end_point     EQU	0x00000275 ; bytes:1
usb_handle_0004D_1_pid           EQU	0x00000276 ; bytes:1
CompTempVar2215                  EQU	0x00000277 ; bytes:1
usb_handle_0004D_64_bd           EQU	0x00000277 ; bytes:2
CompTempVar2224                  EQU	0x00000279 ; bytes:1
CompTempVar2227                  EQU	0x00000279 ; bytes:1
CompTempVar2234                  EQU	0x0000027E ; bytes:1
CompTempVar2237                  EQU	0x00000279 ; bytes:1
CompTempVar2239                  EQU	0x0000027A ; bytes:2
usb_cdc_pu_0006B_arg_c           EQU	0x00000272 ; bytes:1
usb_cdc_pu_0006B_1_cdc_tx_next   EQU	0x00000273 ; bytes:1
usb_cdc_pu_0006B_1_my_store_gie  EQU	0x00000274 ; bit:0
usb_cdc_ha_00063_1_cdc_tx_next   EQU	0x0000027C ; bytes:1
usb_cdc_ha_00063_1_count         EQU	0x0000027D ; bytes:1
usb_cdc_ha_00063_1_buffer_size   EQU	0x0000027E ; bytes:2
usb_cdc_ha_00063_1_buffer        EQU	0x000002A8 ; bytes:2
usb_cdc_ha_00063_1_bd            EQU	0x000002AA ; bytes:2
usb_cdc_ha_00063_1_store_gie     EQU	0x000002AC ; bit:0
CompTempVar2281                  EQU	0x000002AD ; bytes:1
CompTempVar2282                  EQU	0x000002AE ; bytes:1
CompTempVar2283                  EQU	0x000002AD ; bytes:1
CompTempVarRet2291               EQU	0x0000026C ; bytes:1
CompTempVarRet2292               EQU	0x0000026E ; bytes:1
usb_cdc_pr_0006E_arg_str         EQU	0x0000026F ; bytes:2
usb_cdc_pr_0006E_1_count         EQU	0x00000271 ; bytes:1
usb_cdc_se_0006F_1_my_lc         EQU	0x00000269 ; bytes:2
CompTempVar2300                  EQU	0x0000026B ; bytes:3
usb_ep_get_00070_arg_length      EQU	0x0000026D ; bytes:1
usb_ep_get_00070_arg_buffer      EQU	0x0000026E ; bytes:2
usb_ep_get_00070_1_count         EQU	0x00000270 ; bytes:1
CompTempVar2303                  EQU	0x00000271 ; bytes:1
CompTempVar2304                  EQU	0x00000272 ; bytes:1
CompTempVarRet2305               EQU	0x00000269 ; bytes:1
FCD_USBSer_00074_arg_byte        EQU	0x00000269 ; bytes:1
CompTempVarRet2306               EQU	0x0000026C ; bytes:1
FCD_USBSer_00074_1_timeout       EQU	0x0000026A ; bytes:2
FCD_USBSer_00075_arg_String      EQU	0x00000269 ; bytes:2
FCD_USBSer_00075_arg_MSZ_String  EQU	0x0000026B ; bytes:1
CompTempVarRet2309               EQU	0x0000026E ; bytes:1
FCD_USBSer_00075_1_timeout       EQU	0x0000026C ; bytes:2
FCD_USBSer_00076_arg_timeout_ms  EQU	0x00000269 ; bytes:1
CompTempVarRet2312               EQU	0x0000026C ; bytes:1
FCD_USBSer_00076_1_waitforever   EQU	0x0000026A ; bytes:1
FCD_USBSer_00076_1_retval        EQU	0x0000026B ; bytes:1
FC_CAL_Ena_00079_arg_Channel     EQU	0x0000026B ; bytes:1
FC_CAL_Ena_00079_arg_Conv_Speed  EQU	0x0000026C ; bytes:1
FC_CAL_Ena_00079_arg_Vref        EQU	0x0000026D ; bytes:1
FC_CAL_Ena_00079_arg_T_Charge    EQU	0x0000026E ; bytes:1
CompTempVar2317                  EQU	0x0000026F ; bytes:1
CompTempVar2318                  EQU	0x0000026F ; bytes:1
CompTempVar2319                  EQU	0x0000026F ; bytes:1
FC_CAL_Sam_0007A_arg_Sample_Mode EQU	0x0000026B ; bytes:1
CompTempVarRet2320               EQU	0x0000026E ; bytes:2
FC_CAL_Sam_0007A_1_iRetVal       EQU	0x0000026C ; bytes:2
CompTempVar2323                  EQU	0x0000026E ; bytes:1
CompTempVarRet2327               EQU	0x0000026B ; bytes:2
FCD_ADC0_R_0007E_1_retVal        EQU	0x00000269 ; bytes:2
CompTempVarRet2354               EQU	0x0000026A ; bytes:1
FCD_ADC1_R_00089_1_retVal        EQU	0x00000269 ; bytes:1
delay_us_00000_arg_del           EQU	0x0000026F ; bytes:1
delay_ms_00000_arg_del           EQU	0x0000026E ; bytes:1
Int1Context                      EQU	0x00000001 ; bytes:4
	ORG 0x00000000
	GOTO	_startup
	ORG 0x00000008
	GOTO	interrupt
	ORG 0x0000000C
delay_us_00000
; { delay_us ; function begin
label1
	DECFSZ delay_us_00000_arg_del, F, 1
	BRA	label1
	RETURN
; } delay_us function end

	ORG 0x00000012
delay_ms_00000
; { delay_ms ; function begin
	MOVF delay_ms_00000_arg_del, F, 1
	NOP
	BNZ	label2
	RETURN
label2
	MOVLW 0xF9
label3
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	ADDLW 0xFF
	BTFSS STATUS,Z
	BRA	label3
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	NOP
	DECFSZ delay_ms_00000_arg_del, F, 1
	BRA	label2
	RETURN
; } delay_ms function end

	ORG 0x0000004A
usb_send_d_00054
; { usb_send_data_chunk ; function begin
	MOVLB 0x02
	CLRF usb_send_d_00054_1_count, 1
label4
	MOVF gbl_delivery_buffer_size, W, 1
	CPFSLT usb_send_d_00054_1_count, 1
	BRA	label7
	MOVF gbl_delivery_bytes_to_send+D'1', W, 1
	SUBWF gbl_delivery_bytes_sent+D'1', W, 1
	BNZ	label5
	MOVF gbl_delivery_bytes_to_send, W, 1
	SUBWF gbl_delivery_bytes_sent, W, 1
label5
	BC	label7
	MOVF gbl_delivery_bytes_max_send+D'1', W, 1
	SUBWF gbl_delivery_bytes_sent+D'1', W, 1
	BNZ	label6
	MOVF gbl_delivery_bytes_max_send, W, 1
	SUBWF gbl_delivery_bytes_sent, W, 1
label6
	BC	label7
	MOVF gbl_delivery_ptr+D'1', W, 1
	MOVWF FSR0H
	MOVF gbl_delivery_ptr, W, 1
	MOVWF FSR0L
	MOVF INDF0, W
	MOVWF CompTempVar2258, 1
	MOVF gbl_delivery_buffer+D'1', W, 1
	MOVWF FSR0H
	MOVF gbl_delivery_buffer, W, 1
	ADDWF usb_send_d_00054_1_count, W, 1
	MOVWF FSR0L
	MOVF CompTempVar2258, W, 1
	MOVWF INDF0
	INFSNZ gbl_delivery_ptr, F, 1
	INCF gbl_delivery_ptr+D'1', F, 1
	INFSNZ gbl_delivery_bytes_sent, F, 1
	INCF gbl_delivery_bytes_sent+D'1', F, 1
	INCF usb_send_d_00054_1_count, F, 1
	BRA	label4
label7
	MOVF gbl_delivery_buffer_size, W, 1
	CPFSLT usb_send_d_00054_1_count, 1
	BRA	label10
	MOVF gbl_delivery_bytes_max_send, W, 1
	CPFSEQ gbl_delivery_bytes_sent, 1
	BRA	label8
	MOVF gbl_delivery_bytes_max_send+D'1', W, 1
	CPFSEQ gbl_delivery_bytes_sent+D'1', 1
	BRA	label8
	BRA	label9
label8
	MOVF gbl_delivery_bytes_to_send, W, 1
	CPFSEQ gbl_delivery_bytes_sent, 1
	BRA	label10
	MOVF gbl_delivery_bytes_to_send+D'1', W, 1
	CPFSEQ gbl_delivery_bytes_sent+D'1', 1
	BRA	label10
label9
	MOVLW 0x05
	MOVWF gbl_control_mode, 1
label10
	MOVF usb_send_d_00054_1_count, W, 1
	MOVWF gbl_bd0in+D'1', 1
	MOVLW HIGH(gbl_buffer_0_in+D'0')
	MOVWF gbl_bd0in+D'3', 1
	MOVLW LOW(gbl_buffer_0_in+D'0')
	MOVWF gbl_bd0in+D'2', 1
	BTFSS gbl_bd0in,6, 1
	BRA	label11
	BCF gbl_bd0in,6, 1
	BRA	label12
label11
	BSF gbl_bd0in,6, 1
label12
	BCF gbl_bd0in,5, 1
	BCF gbl_bd0in,4, 1
	BSF gbl_bd0in,3, 1
	BCF gbl_bd0in,2, 1
	BCF gbl_bd0in,1, 1
	BCF gbl_bd0in,0, 1
	BSF gbl_bd0in,7, 1
	RETURN
; } usb_send_data_chunk function end

	ORG 0x000000DC
usb_stall__00055
; { usb_stall_ep0 ; function begin
	MOVLB 0x02
	BSF gbl_bd0in,2, 1
	BSF gbl_bd0in,7, 1
	BSF gbl_bd0out,2, 1
	BSF gbl_bd0out,7, 1
	RETURN
; } usb_stall_ep0 function end

	ORG 0x000000E8
usb_send_o_00066
; { usb_send_one_byte ; function begin
	MOVLW 0x08
	MOVWF gbl_delivery_buffer_size, 1
	CLRF gbl_delivery_bytes_sent, 1
	CLRF gbl_delivery_bytes_sent+D'1', 1
	MOVLW 0x01
	MOVWF gbl_delivery_bytes_to_send, 1
	CLRF gbl_delivery_bytes_to_send+D'1', 1
	CLRF gbl_delivery_bytes_max_send, 1
	CLRF gbl_delivery_bytes_max_send+D'1', 1
	MOVF usb_send_o_00066_arg_data, W, 1
	MOVWF gbl_buffer_byte, 1
	MOVLW HIGH(gbl_buffer_byte+D'0')
	MOVWF gbl_delivery_ptr+D'1', 1
	MOVLW LOW(gbl_buffer_byte+D'0')
	MOVWF gbl_delivery_ptr, 1
	MOVLW HIGH(gbl_bd0in+D'0')
	MOVWF gbl_delivery_bd+D'1', 1
	MOVLW LOW(gbl_bd0in+D'0')
	MOVWF gbl_delivery_bd, 1
	MOVLW HIGH(gbl_buffer_0_in+D'0')
	MOVWF gbl_delivery_buffer+D'1', 1
	MOVLW LOW(gbl_buffer_0_in+D'0')
	MOVWF gbl_delivery_buffer, 1
	BCF gbl_bd0in,6, 1
	CALL usb_send_d_00054
	RETURN
; } usb_send_one_byte function end

	ORG 0x0000011E
usb_send_e_00053
; { usb_send_empty_data_pkt ; function begin
	MOVLW 0x08
	MOVLB 0x02
	MOVWF gbl_delivery_buffer_size, 1
	MOVLW HIGH(gbl_bd0in+D'0')
	MOVWF gbl_delivery_bd+D'1', 1
	MOVLW LOW(gbl_bd0in+D'0')
	MOVWF gbl_delivery_bd, 1
	MOVLW HIGH(gbl_buffer_0_in+D'0')
	MOVWF gbl_delivery_buffer+D'1', 1
	MOVLW LOW(gbl_buffer_0_in+D'0')
	MOVWF gbl_delivery_buffer, 1
	CLRF gbl_delivery_bytes_sent, 1
	CLRF gbl_delivery_bytes_sent+D'1', 1
	CLRF gbl_delivery_bytes_to_send, 1
	CLRF gbl_delivery_bytes_to_send+D'1', 1
	CLRF gbl_delivery_bytes_max_send, 1
	CLRF gbl_delivery_bytes_max_send+D'1', 1
	CLRF gbl_delivery_ptr, 1
	CLRF gbl_delivery_ptr+D'1', 1
	BCF gbl_bd0in,6, 1
	CALL usb_send_d_00054
	RETURN
; } usb_send_empty_data_pkt function end

	ORG 0x0000014C
usb_send_d_00052
; { usb_send_data ; function begin
	MOVLB 0x01
	LFSR 0x00, gbl_ep_in_buffer_location
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_send_d_00052_arg_ep, W, 1
	MOVWF CompTempVar2242, 1
	BCF STATUS,C
	RLCF CompTempVar2242, W, 1
	ADDWF FSR0L, F
	MOVF POSTINC0, W
	MOVWF usb_send_d_00052_1_buffer, 1
	MOVF INDF0, W
	MOVWF usb_send_d_00052_1_buffer+D'1', 1
	MOVLB 0x01
	LFSR 0x00, gbl_ep_in_bd_location
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_send_d_00052_arg_ep, W, 1
	MOVWF CompTempVar2245, 1
	BCF STATUS,C
	RLCF CompTempVar2245, W, 1
	ADDWF FSR0L, F
	MOVF POSTINC0, W
	MOVWF usb_send_d_00052_1_bd, 1
	MOVF INDF0, W
	MOVWF usb_send_d_00052_1_bd+D'1', 1
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	BTFSC INDF0,7
	RETURN
	CLRF usb_send_d_00052_1_count, 1
label13
	MOVF usb_send_d_00052_arg_send_count, W, 1
	CPFSLT usb_send_d_00052_1_count, 1
	BRA	label14
	MOVF usb_send_d_00052_arg_data+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_arg_data, W, 1
	MOVWF FSR0L
	MOVF usb_send_d_00052_1_count, W, 1
	MOVWF CompTempVar2248, 1
	MOVF CompTempVar2248, W, 1
	ADDWF FSR0L, F
	MOVF INDF0, W
	MOVWF CompTempVar2249, 1
	MOVF usb_send_d_00052_1_buffer+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_buffer, W, 1
	ADDWF usb_send_d_00052_1_count, W, 1
	MOVWF FSR0L
	MOVF CompTempVar2249, W, 1
	MOVWF INDF0
	INCF usb_send_d_00052_1_count, F, 1
	BRA	label13
label14
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	INCF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	MOVF usb_send_d_00052_1_count, W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVLW 0x02
	ADDWF FSR0L, F
	INCF FSR0L, F
	MOVF usb_send_d_00052_1_buffer, W, 1
	MOVWF CompTempVar2250, 1
	MOVF usb_send_d_00052_1_buffer+D'1', W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2250, W, 1
	MOVWF INDF0
	BTFSS usb_send_d_00052_arg_first,0, 1
	BRA	label15
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	BCF INDF0,6
label15
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	BTFSS INDF0,6
	BRA	label16
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	BCF INDF0,6
	BRA	label17
label16
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	BSF INDF0,6
label17
	MOVF usb_send_d_00052_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_send_d_00052_1_bd, W, 1
	MOVWF FSR0L
	BCF INDF0,5
	BCF INDF0,4
	BSF INDF0,3
	BCF INDF0,2
	BCF INDF0,1
	BCF INDF0,0
	BSF INDF0,7
	RETURN
; } usb_send_data function end

	ORG 0x00000228
usb_get_de_0005A
; { usb_get_descriptor_callback ; function begin
	CLRF usb_get_de_0005A_1_descrip_0005F, 1
	CLRF usb_get_de_0005A_1_descrip_0005F+D'1', 1
	DECF usb_get_de_0005A_arg_descr_0005B, W, 1
	BZ	label20
	MOVLW 0x02
	CPFSEQ usb_get_de_0005A_arg_descr_0005B, 1
	BRA	label18
	BRA	label21
label18
	MOVLW 0x03
	CPFSEQ usb_get_de_0005A_arg_descr_0005B, 1
	BRA	label19
	BRA	label22
label19
	MOVLW 0x06
	CPFSEQ usb_get_de_0005A_arg_descr_0005B, 1
	BRA	label26
	BRA	label26
label20
	MOVLW HIGH(gbl_my_device_descriptor+D'0')
	MOVWF CompTempVar2265, 1
	MOVLW LOW(gbl_my_device_descriptor+D'0')
	MOVWF usb_get_de_0005A_1_descrip_0005F, 1
	MOVF CompTempVar2265, W, 1
	MOVWF usb_get_de_0005A_1_descrip_0005F+D'1', 1
	MOVLW 0x12
	MOVWF usb_get_de_0005A_1_descrip_00060, 1
	CLRF usb_get_de_0005A_1_descrip_00060+D'1', 1
	BRA	label26
label21
	MOVLW HIGH(gbl_complete_serial_config_00000+D'0')
	MOVWF CompTempVar2266, 1
	MOVLW LOW(gbl_complete_serial_config_00000+D'0')
	MOVWF usb_get_de_0005A_1_descrip_0005F, 1
	MOVF CompTempVar2266, W, 1
	MOVWF usb_get_de_0005A_1_descrip_0005F+D'1', 1
	MOVLW 0x43
	MOVWF usb_get_de_0005A_1_descrip_00060, 1
	CLRF usb_get_de_0005A_1_descrip_00060+D'1', 1
	BRA	label26
label22
	MOVF usb_get_de_0005A_arg_descr_0005C, F, 1
	BZ	label23
	DECF usb_get_de_0005A_arg_descr_0005C, W, 1
	BZ	label24
	MOVLW 0x02
	CPFSEQ usb_get_de_0005A_arg_descr_0005C, 1
	BRA	label26
	BRA	label25
label23
	MOVLW 0x04
	MOVWF usb_get_de_0005A_1_descrip_00060, 1
	CLRF usb_get_de_0005A_1_descrip_00060+D'1', 1
	MOVLW HIGH(gbl_string_00+D'0')
	MOVWF CompTempVar2267, 1
	MOVLW LOW(gbl_string_00+D'0')
	MOVWF usb_get_de_0005A_1_descrip_0005F, 1
	MOVF CompTempVar2267, W, 1
	MOVWF usb_get_de_0005A_1_descrip_0005F+D'1', 1
	BRA	label26
label24
	MOVLW 0x2E
	MOVWF usb_get_de_0005A_1_descrip_00060, 1
	CLRF usb_get_de_0005A_1_descrip_00060+D'1', 1
	MOVLW HIGH(gbl_MX_USBMFR+D'0')
	MOVWF CompTempVar2268, 1
	MOVLW LOW(gbl_MX_USBMFR+D'0')
	MOVWF usb_get_de_0005A_1_descrip_0005F, 1
	MOVF CompTempVar2268, W, 1
	MOVWF usb_get_de_0005A_1_descrip_0005F+D'1', 1
	BRA	label26
label25
	MOVLW 0x28
	MOVWF usb_get_de_0005A_1_descrip_00060, 1
	CLRF usb_get_de_0005A_1_descrip_00060+D'1', 1
	MOVLW HIGH(gbl_MX_USBNAME+D'0')
	MOVWF CompTempVar2269, 1
	MOVLW LOW(gbl_MX_USBNAME+D'0')
	MOVWF usb_get_de_0005A_1_descrip_0005F, 1
	MOVF CompTempVar2269, W, 1
	MOVWF usb_get_de_0005A_1_descrip_0005F+D'1', 1
label26
	MOVF usb_get_de_0005A_arg_rtn_d_0005D+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_get_de_0005A_arg_rtn_d_0005D, W, 1
	MOVWF FSR0L
	INCF FSR0L, F
	MOVF usb_get_de_0005A_1_descrip_0005F, W, 1
	MOVWF CompTempVar2270, 1
	MOVF usb_get_de_0005A_1_descrip_0005F+D'1', W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2270, W, 1
	MOVWF INDF0
	MOVF usb_get_de_0005A_arg_rtn_d_0005E+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_get_de_0005A_arg_rtn_d_0005E, W, 1
	MOVWF FSR0L
	INCF FSR0L, F
	MOVF usb_get_de_0005A_1_descrip_00060, W, 1
	MOVWF CompTempVar2271, 1
	MOVF usb_get_de_0005A_1_descrip_00060+D'1', W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2271, W, 1
	MOVWF INDF0
	RETURN
; } usb_get_descriptor_callback function end

	ORG 0x000002EC
usb_device_00064
; { usb_device_configured_callback ; function begin
	MOVLW 0x01
	MOVLB 0x02
	MOVWF gbl_CDC_USB_status, 1
	RETURN
; } usb_device_configured_callback function end

	ORG 0x000002F4
usb_config_00065
; { usb_configure_endpoints ; function begin
	MOVLB 0x0F
	BSF gbl_uep2,4, 1
	BCF gbl_uep2,2, 1
	BSF gbl_uep2,1, 1
	BSF gbl_uep2,3, 1
	MOVLB 0x02
	BSF gbl_bd2in,6, 1
	BCF gbl_bd2in,5, 1
	BCF gbl_bd2in,4, 1
	BCF gbl_bd2in,3, 1
	BCF gbl_bd2in,2, 1
	BCF gbl_bd2in,1, 1
	BCF gbl_bd2in,0, 1
	BCF gbl_bd2in,7, 1
	MOVLB 0x0F
	BSF gbl_uep3,4, 1
	BSF gbl_uep3,2, 1
	BSF gbl_uep3,1, 1
	BSF gbl_uep3,3, 1
	MOVLB 0x02
	BSF gbl_bd3in,6, 1
	BCF gbl_bd3in,5, 1
	BCF gbl_bd3in,4, 1
	BCF gbl_bd3in,3, 1
	BCF gbl_bd3in,2, 1
	BCF gbl_bd3in,1, 1
	BCF gbl_bd3in,0, 1
	BCF gbl_bd3in,7, 1
	MOVLW 0x08
	MOVWF gbl_bd3out+D'1', 1
	MOVLW 0x98
	MOVWF gbl_bd3out+D'2', 1
	MOVLW 0x02
	MOVWF gbl_bd3out+D'3', 1
	BCF gbl_bd3out,6, 1
	BCF gbl_bd3out,5, 1
	BCF gbl_bd3out,4, 1
	BCF gbl_bd3out,3, 1
	BCF gbl_bd3out,2, 1
	BCF gbl_bd3out,1, 1
	BCF gbl_bd3out,0, 1
	BSF gbl_bd3out,7, 1
	RETURN
; } usb_configure_endpoints function end

	ORG 0x0000034A
usb_cdc_ha_00063
; { usb_cdc_handle_tx ; function begin
	MOVLB 0x01
	MOVF gbl_ep_in_bd_location+D'6', W, 1
	MOVLB 0x02
	MOVWF usb_cdc_ha_00063_1_bd, 1
	MOVLB 0x01
	MOVF gbl_ep_in_bd_location+D'7', W, 1
	MOVLB 0x02
	MOVWF usb_cdc_ha_00063_1_bd+D'1', 1
	MOVF usb_cdc_ha_00063_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_ha_00063_1_bd, W, 1
	MOVWF FSR0L
	BTFSC INDF0,7
	RETURN
	MOVLB 0x01
	MOVF gbl_ep_in_buffer_size+D'6', W, 1
	MOVLB 0x02
	MOVWF usb_cdc_ha_00063_1_buffer_size, 1
	MOVLB 0x01
	MOVF gbl_ep_in_buffer_size+D'7', W, 1
	MOVLB 0x02
	MOVWF usb_cdc_ha_00063_1_buffer_size+D'1', 1
	MOVLB 0x01
	MOVF gbl_ep_in_buffer_location+D'6', W, 1
	MOVLB 0x02
	MOVWF usb_cdc_ha_00063_1_buffer, 1
	MOVLB 0x01
	MOVF gbl_ep_in_buffer_location+D'7', W, 1
	MOVLB 0x02
	MOVWF usb_cdc_ha_00063_1_buffer+D'1', 1
	MOVF gbl_cdc_tx_start, W, 1
	CPFSEQ gbl_cdc_tx_end, 1
	BRA	label27
	RETURN
label27
	BCF usb_cdc_ha_00063_1_store_gie,0, 1
	BTFSC gbl_intcon,7
	BSF usb_cdc_ha_00063_1_store_gie,0, 1
label28
	BCF gbl_intcon,7
	BTFSC gbl_intcon,7
	BRA	label28
	CLRF usb_cdc_ha_00063_1_count, 1
label29
	MOVF gbl_cdc_tx_start, W, 1
	CPFSEQ gbl_cdc_tx_end, 1
	CPFSEQ gbl_cdc_tx_start, 1
	BRA	label31
	MOVF usb_cdc_ha_00063_1_buffer_size, W, 1
	CPFSLT usb_cdc_ha_00063_1_count, 1
	MOVF usb_cdc_ha_00063_1_buffer_size+D'1', W, 1
	BZ	label31
	INCF gbl_cdc_tx_start, W, 1
	MOVWF usb_cdc_ha_00063_1_cdc_tx_next, 1
	MOVLW 0x40
	CPFSEQ usb_cdc_ha_00063_1_cdc_tx_next, 1
	BRA	label30
	CLRF usb_cdc_ha_00063_1_cdc_tx_next, 1
label30
	MOVLB 0x00
	LFSR 0x00, gbl_cdc_tx_buffer
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF gbl_cdc_tx_start, W, 1
	MOVWF CompTempVar2281, 1
	MOVF CompTempVar2281, W, 1
	ADDWF FSR0L, F
	MOVF INDF0, W
	MOVWF CompTempVar2282, 1
	MOVF usb_cdc_ha_00063_1_buffer+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_ha_00063_1_buffer, W, 1
	ADDWF usb_cdc_ha_00063_1_count, W, 1
	MOVWF FSR0L
	MOVF CompTempVar2282, W, 1
	MOVWF INDF0
	INCF usb_cdc_ha_00063_1_count, F, 1
	MOVF usb_cdc_ha_00063_1_cdc_tx_next, W, 1
	MOVWF gbl_cdc_tx_start, 1
	BRA	label29
label31
	MOVLW 0x00
	CPFSGT usb_cdc_ha_00063_1_count, 1
	BRA	label34
	MOVF usb_cdc_ha_00063_1_bd+D'1', W, 1
	MOVWF FSR0H
	INCF usb_cdc_ha_00063_1_bd, W, 1
	MOVWF FSR0L
	MOVF usb_cdc_ha_00063_1_count, W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVLW 0x02
	ADDWF FSR0L, F
	INCF FSR0L, F
	MOVF usb_cdc_ha_00063_1_buffer, W, 1
	MOVWF CompTempVar2283, 1
	MOVF usb_cdc_ha_00063_1_buffer+D'1', W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2283, W, 1
	MOVWF INDF0
	MOVF usb_cdc_ha_00063_1_bd, W, 1
	MOVWF FSR0L
	BTFSS INDF0,6
	BRA	label32
	MOVF usb_cdc_ha_00063_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_ha_00063_1_bd, W, 1
	MOVWF FSR0L
	BCF INDF0,6
	BRA	label33
label32
	MOVF usb_cdc_ha_00063_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_ha_00063_1_bd, W, 1
	MOVWF FSR0L
	BSF INDF0,6
label33
	MOVF usb_cdc_ha_00063_1_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_ha_00063_1_bd, W, 1
	MOVWF FSR0L
	BCF INDF0,5
	BCF INDF0,4
	BSF INDF0,3
	BCF INDF0,2
	BCF INDF0,1
	BCF INDF0,0
	BSF INDF0,7
label34
	BTFSC usb_cdc_ha_00063_1_store_gie,0, 1
	BSF gbl_intcon,7
	BTFSS usb_cdc_ha_00063_1_store_gie,0, 1
	BCF gbl_intcon,7
	RETURN
; } usb_cdc_handle_tx function end

	ORG 0x0000044A
memcpy8_00000
; { memcpy8 ; function begin
	MOVF memcpy8_00000_arg_dst, W, 1
	MOVWF memcpy8_00000_1_dst2, 1
	MOVF memcpy8_00000_arg_dst+D'1', W, 1
	MOVWF memcpy8_00000_1_dst2+D'1', 1
	MOVF memcpy8_00000_arg_src, W, 1
	MOVWF memcpy8_00000_1_src2, 1
	MOVF memcpy8_00000_arg_src+D'1', W, 1
	MOVWF memcpy8_00000_1_src2+D'1', 1
label35
	MOVLW 0x00
	CPFSGT memcpy8_00000_arg_len, 1
	BRA	label36
	MOVF memcpy8_00000_1_src2+D'1', W, 1
	MOVWF FSR0H
	MOVF memcpy8_00000_1_src2, W, 1
	MOVWF FSR0L
	INFSNZ memcpy8_00000_1_src2, F, 1
	INCF memcpy8_00000_1_src2+D'1', F, 1
	MOVF INDF0, W
	MOVWF CompTempVar413, 1
	MOVF memcpy8_00000_1_dst2+D'1', W, 1
	MOVWF FSR0H
	MOVF memcpy8_00000_1_dst2, W, 1
	MOVWF FSR0L
	INFSNZ memcpy8_00000_1_dst2, F, 1
	INCF memcpy8_00000_1_dst2+D'1', F, 1
	MOVF CompTempVar413, W, 1
	MOVWF INDF0
	DECF memcpy8_00000_arg_len, F, 1
	BRA	label35
label36
	MOVF memcpy8_00000_arg_dst, W, 1
	MOVWF CompTempVarRet412, 1
	MOVF memcpy8_00000_arg_dst+D'1', W, 1
	MOVWF CompTempVarRet412+D'1', 1
	RETURN
; } memcpy8 function end

	ORG 0x0000048E
usb_prime__00067
; { usb_prime_ep0_out ; function begin
	MOVLW 0x08
	MOVLB 0x02
	MOVWF gbl_bd0out+D'1', 1
	MOVLW 0x80
	MOVWF gbl_bd0out+D'2', 1
	MOVLW 0x02
	MOVWF gbl_bd0out+D'3', 1
	BSF gbl_bd0out,6, 1
	BCF gbl_bd0out,5, 1
	BCF gbl_bd0out,4, 1
	BSF gbl_bd0out,3, 1
	BCF gbl_bd0out,2, 1
	BCF gbl_bd0out,1, 1
	BCF gbl_bd0out,0, 1
	BSF gbl_bd0out,7, 1
	RETURN
; } usb_prime_ep0_out function end

	ORG 0x000004AE
usb_handle_00068
; { usb_handle_standard_request ; function begin
	MOVLW 0x06
	CPFSEQ usb_handle_00068_arg_sdp+D'1', 1
	BRA	label37
	BRA	label41
label37
	MOVLW 0x05
	CPFSEQ usb_handle_00068_arg_sdp+D'1', 1
	BRA	label38
	BRA	label44
label38
	MOVLW 0x09
	CPFSEQ usb_handle_00068_arg_sdp+D'1', 1
	BRA	label39
	BRA	label45
label39
	MOVLW 0x0A
	CPFSEQ usb_handle_00068_arg_sdp+D'1', 1
	BRA	label40
	BRA	label46
label40
	MOVF usb_handle_00068_arg_sdp+D'1', F, 1
	BZ	label47
	BRA	label48
label41
	MOVF usb_handle_00068_arg_sdp+D'3', W, 1
	MOVWF usb_handle_00068_1_descrip_00069, 1
	MOVF usb_handle_00068_arg_sdp+D'2', W, 1
	MOVWF usb_handle_00068_1_descrip_0006A, 1
	MOVF usb_handle_00068_1_descrip_00069, W, 1
	MOVWF usb_get_de_0005A_arg_descr_0005B, 1
	MOVF usb_handle_00068_1_descrip_0006A, W, 1
	MOVWF usb_get_de_0005A_arg_descr_0005C, 1
	MOVLW HIGH(gbl_delivery_ptr+D'0')
	MOVWF usb_get_de_0005A_arg_rtn_d_0005D+D'1', 1
	MOVLW LOW(gbl_delivery_ptr+D'0')
	MOVWF usb_get_de_0005A_arg_rtn_d_0005D, 1
	MOVLW HIGH(gbl_delivery_bytes_to_send+D'0')
	MOVWF usb_get_de_0005A_arg_rtn_d_0005E+D'1', 1
	MOVLW LOW(gbl_delivery_bytes_to_send+D'0')
	MOVWF usb_get_de_0005A_arg_rtn_d_0005E, 1
	CALL usb_get_de_0005A
	MOVF gbl_delivery_ptr, F, 1
	BNZ	label42
	MOVF gbl_delivery_ptr+D'1', F, 1
	BZ	label43
label42
	MOVLW 0x03
	MOVWF gbl_control_mode, 1
	MOVF usb_handle_00068_arg_sdp+D'6', W, 1
	MOVWF gbl_delivery_bytes_max_send, 1
	MOVF usb_handle_00068_arg_sdp+D'7', W, 1
	MOVWF gbl_delivery_bytes_max_send+D'1', 1
	CLRF gbl_delivery_bytes_sent, 1
	CLRF gbl_delivery_bytes_sent+D'1', 1
	MOVLW 0x08
	MOVWF gbl_delivery_buffer_size, 1
	MOVLW HIGH(gbl_bd0in+D'0')
	MOVWF gbl_delivery_bd+D'1', 1
	MOVLW LOW(gbl_bd0in+D'0')
	MOVWF gbl_delivery_bd, 1
	MOVLW 0x88
	MOVWF gbl_delivery_buffer, 1
	MOVLW 0x02
	MOVWF gbl_delivery_buffer+D'1', 1
	BCF gbl_bd0in,6, 1
	CALL usb_send_d_00054
	RETURN
label43
	CALL usb_stall__00055
	RETURN
label44
	MOVF usb_handle_00068_arg_sdp+D'2', W, 1
	MOVWF gbl_usb_address, 1
	MOVLW 0x01
	MOVWF gbl_usb_status, 1
	CALL usb_send_e_00053
	MOVLW 0x06
	MOVWF gbl_control_mode, 1
	RETURN
label45
	MOVLW 0x06
	MOVWF gbl_control_mode, 1
	CALL usb_send_e_00053
	MOVLW 0x03
	MOVWF gbl_usb_state, 1
	CALL usb_config_00065
	CALL usb_device_00064
	RETURN
label46
	MOVLW 0x03
	MOVWF gbl_control_mode, 1
	MOVLW 0x01
	MOVWF usb_send_o_00066_arg_data, 1
	CALL usb_send_o_00066
label47
	CLRF usb_send_o_00066_arg_data, 1
	CALL usb_send_o_00066
	RETURN
label48
	CALL usb_stall__00055
	RETURN
; } usb_handle_standard_request function end

	ORG 0x00000574
usb_handle_00059
; { usb_handle_class_request ; function begin
	MOVLW 0x20
	CPFSEQ usb_handle_00059_arg_sdp+D'1', 1
	BRA	label49
	BRA	label51
label49
	MOVLW 0x21
	CPFSEQ usb_handle_00059_arg_sdp+D'1', 1
	BRA	label50
	BRA	label52
label50
	MOVLW 0x22
	CPFSEQ usb_handle_00059_arg_sdp+D'1', 1
	RETURN
	BRA	label53
label51
	MOVLW 0x02
	MOVWF gbl_control_mode, 1
	RETURN
label52
	MOVLW 0x04
	MOVWF gbl_control_mode, 1
	CLRF usb_send_d_00052_arg_ep, 1
	MOVLW HIGH(gbl_class_data+D'0')
	MOVWF usb_send_d_00052_arg_data+D'1', 1
	MOVLW LOW(gbl_class_data+D'0')
	MOVWF usb_send_d_00052_arg_data, 1
	MOVLW 0x08
	MOVWF usb_send_d_00052_arg_send_count, 1
	BSF usb_send_d_00052_arg_first,0, 1
	CALL usb_send_d_00052
	RETURN
label53
	MOVLW 0x06
	MOVWF gbl_control_mode, 1
	CALL usb_send_e_00053
	RETURN
; } usb_handle_class_request function end

	ORG 0x000005B6
usb_handle_00058
; { usb_handle_ctrl_write_class ; function begin
	MOVLW 0x20
	MOVLB 0x01
	CPFSEQ gbl_usb_sdp+D'1', 1
	RETURN
	MOVLW HIGH(gbl_class_data+D'0')
	MOVLB 0x02
	MOVWF memcpy8_00000_arg_dst+D'1', 1
	MOVLW LOW(gbl_class_data+D'0')
	MOVWF memcpy8_00000_arg_dst, 1
	MOVF usb_handle_00058_arg_data, W, 1
	MOVWF memcpy8_00000_arg_src, 1
	MOVF usb_handle_00058_arg_data+D'1', W, 1
	MOVWF memcpy8_00000_arg_src+D'1', 1
	MOVF usb_handle_00058_arg_count, W, 1
	MOVWF memcpy8_00000_arg_len, 1
	CALL memcpy8_00000
	MOVLW 0x06
	MOVWF gbl_control_mode, 1
	CALL usb_send_e_00053
	MOVLW HIGH(gbl_class_data+D'0')
	MOVWF usb_handle_00058_1_my_lc+D'1', 1
	MOVLW LOW(gbl_class_data+D'0')
	MOVWF usb_handle_00058_1_my_lc, 1
	RETURN
; } usb_handle_ctrl_write_class function end

	ORG 0x000005EA
usb_handle_00057
; { usb_handle_ctrl_read_class ; function begin
	MOVLW 0x21
	MOVLB 0x01
	CPFSEQ gbl_usb_sdp+D'1', 1
	RETURN
	MOVLW 0x05
	MOVLB 0x02
	MOVWF gbl_control_mode, 1
	RETURN
; } usb_handle_ctrl_read_class function end

	ORG 0x000005FA
usb_ep_dat_00062
; { usb_ep_data_in_callback ; function begin
	CALL usb_cdc_ha_00063
	RETURN
; } usb_ep_data_in_callback function end

	ORG 0x00000600
usb_ep_dat_00061
; { usb_ep_data_out_callback ; function begin
	MOVLW 0x03
	CPFSEQ usb_ep_dat_00061_arg_end_point, 1
	RETURN
	CLRF usb_ep_dat_00061_2_count, 1
label54
	MOVF usb_ep_dat_00061_arg_byte_count, W, 1
	CPFSLT usb_ep_dat_00061_2_count, 1
	MOVF usb_ep_dat_00061_arg_byte_count+D'1', W, 1
	BTFSC STATUS,Z
	RETURN
	INCF gbl_cdc_rx_end, W, 1
	MOVWF usb_ep_dat_00061_1_cdc_rx_next, 1
	MOVLW 0x40
	CPFSEQ usb_ep_dat_00061_1_cdc_rx_next, 1
	BRA	label55
	CLRF usb_ep_dat_00061_1_cdc_rx_next, 1
label55
	MOVF gbl_cdc_rx_start, W, 1
	CPFSEQ usb_ep_dat_00061_1_cdc_rx_next, 1
	CPFSEQ gbl_cdc_rx_start, 1
	BRA	label56
	MOVF usb_ep_dat_00061_arg_buffer+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_ep_dat_00061_arg_buffer, W, 1
	MOVWF FSR0L
	MOVF usb_ep_dat_00061_2_count, W, 1
	MOVWF CompTempVar2274, 1
	MOVF CompTempVar2274, W, 1
	ADDWF FSR0L, F
	MOVF INDF0, W
	MOVWF CompTempVar2275, 1
	MOVLB 0x00
	LFSR 0x00, gbl_cdc_rx_buffer
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF gbl_cdc_rx_end, W, 1
	ADDWF FSR0L, F
	MOVF CompTempVar2275, W, 1
	MOVWF INDF0
	MOVF usb_ep_dat_00061_1_cdc_rx_next, W, 1
	MOVWF gbl_cdc_rx_end, 1
label56
	INCF usb_ep_dat_00061_2_count, F, 1
	BRA	label54
; } usb_ep_data_out_callback function end

	ORG 0x00000654
usb_handle_0004F
; { usb_handle_stall ; function begin
	RETURN
; } usb_handle_stall function end

	ORG 0x00000656
usb_handle_0004E
; { usb_handle_reset ; function begin
	MOVLB 0x02
	CLRF gbl_usb_address, 1
	CLRF gbl_control_mode, 1
	CLRF gbl_usb_status, 1
	BCF gbl_uir,3
	BCF gbl_uir,3
	BCF gbl_uir,3
	BCF gbl_uir,3
	MOVLW 0x08
	MOVWF gbl_bd0out+D'1', 1
	MOVLW 0x80
	MOVWF gbl_bd0out+D'2', 1
	MOVLW 0x02
	MOVWF gbl_bd0out+D'3', 1
	BCF gbl_bd0out,6, 1
	BCF gbl_bd0out,5, 1
	BCF gbl_bd0out,4, 1
	BCF gbl_bd0out,3, 1
	BCF gbl_bd0out,2, 1
	BCF gbl_bd0out,1, 1
	BCF gbl_bd0out,0, 1
	BSF gbl_bd0out,7, 1
	MOVLW 0x08
	MOVWF gbl_bd0in+D'1', 1
	MOVLW 0x88
	MOVWF gbl_bd0in+D'2', 1
	MOVLW 0x02
	MOVWF gbl_bd0in+D'3', 1
	BCF gbl_bd0in,6, 1
	BCF gbl_bd0in,5, 1
	BCF gbl_bd0in,4, 1
	BCF gbl_bd0in,2, 1
	BCF gbl_bd0in,1, 1
	BCF gbl_bd0in,0, 1
	BCF gbl_bd0in,7, 1
	RETURN
; } usb_handle_reset function end

	ORG 0x0000069E
usb_handle_0004D
; { usb_handle_transaction ; function begin
	RRCF gbl_ustat, W
	MOVLB 0x02
	MOVWF usb_handle_0004D_1_end_point, 1
	RRCF usb_handle_0004D_1_end_point, F, 1
	RRCF usb_handle_0004D_1_end_point, F, 1
	MOVLW 0x1F
	ANDWF usb_handle_0004D_1_end_point, F, 1
	BTFSS gbl_ustat,2
	BRA	label57
	RRCF gbl_bd0in, W, 1
	MOVWF usb_handle_0004D_1_pid, 1
	RRCF usb_handle_0004D_1_pid, F, 1
	MOVLW 0x3F
	ANDWF usb_handle_0004D_1_pid, F, 1
	MOVLW 0x0F
	ANDWF usb_handle_0004D_1_pid, F, 1
	BRA	label58
label57
	RRCF gbl_bd0out, W, 1
	MOVWF CompTempVar2215, 1
	RRCF CompTempVar2215, F, 1
	MOVLW 0x3F
	ANDWF CompTempVar2215, F, 1
	MOVLW 0x0F
	ANDWF CompTempVar2215, W, 1
	MOVWF usb_handle_0004D_1_pid, 1
label58
	MOVF usb_handle_0004D_1_end_point, F, 1
	BTFSS STATUS,Z
	BRA	label72
	MOVLW 0x0D
	CPFSEQ usb_handle_0004D_1_pid, 1
	BRA	label61
	MOVLW HIGH(gbl_usb_sdp+D'0')
	MOVWF memcpy8_00000_arg_dst+D'1', 1
	MOVLW LOW(gbl_usb_sdp+D'0')
	MOVWF memcpy8_00000_arg_dst, 1
	MOVLW HIGH(gbl_buffer_0_out+D'0')
	MOVWF memcpy8_00000_arg_src+D'1', 1
	MOVLW LOW(gbl_buffer_0_out+D'0')
	MOVWF memcpy8_00000_arg_src, 1
	MOVLW 0x08
	MOVWF memcpy8_00000_arg_len, 1
	CALL memcpy8_00000
	CALL usb_prime__00067
	MOVLB 0x01
	BTFSC gbl_usb_sdp,6, 1
	BRA	label59
	BTFSC gbl_usb_sdp,5, 1
	BRA	label59
	MOVF gbl_usb_sdp+D'7', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'7', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'6', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'6', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'5', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'5', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'4', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'4', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'3', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'3', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'2', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'2', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'1', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp+D'1', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp, W, 1
	MOVLB 0x02
	MOVWF usb_handle_00068_arg_sdp, 1
	CALL usb_handle_00068
	BRA	label60
label59
	BTFSC gbl_usb_sdp,6, 1
	BRA	label60
	BTFSS gbl_usb_sdp,5, 1
	BRA	label60
	MOVF gbl_usb_sdp+D'7', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'7', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'6', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'6', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'5', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'5', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'4', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'4', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'3', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'3', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'2', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'2', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp+D'1', W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp+D'1', 1
	MOVLB 0x01
	MOVF gbl_usb_sdp, W, 1
	MOVLB 0x02
	MOVWF usb_handle_00059_arg_sdp, 1
	CALL usb_handle_00059
label60
	BCF gbl_ucon,4
	RETURN
label61
	MOVLW 0x09
	CPFSEQ usb_handle_0004D_1_pid, 1
	BRA	label65
	MOVLW 0x03
	CPFSEQ gbl_control_mode, 1
	BRA	label62
	CALL usb_send_d_00054
	RETURN
label62
	MOVLW 0x06
	CPFSEQ gbl_control_mode, 1
	BRA	label63
	CLRF gbl_control_mode, 1
	DECF gbl_usb_status, W, 1
	BTFSS STATUS,Z
	RETURN
	MOVLW 0x02
	MOVWF gbl_usb_state, 1
	MOVF gbl_usb_address, W, 1
	MOVLB 0x0F
	MOVWF gbl_uaddr, 1
	MOVLB 0x02
	CLRF gbl_usb_status, 1
	RETURN
label63
	MOVLW 0x05
	CPFSEQ gbl_control_mode, 1
	BRA	label64
	NOP
	RETURN
label64
	MOVLW 0x04
	CPFSEQ gbl_control_mode, 1
	RETURN
	CALL usb_handle_00057
	RETURN
label65
	MOVLW 0x02
	CPFSEQ usb_handle_0004D_1_pid, 1
	BRA	label68
	MOVLW 0x03
	CPFSEQ gbl_control_mode, 1
	BRA	label66
	CALL usb_send_d_00054
	RETURN
label66
	MOVLW 0x04
	CPFSEQ gbl_control_mode, 1
	BRA	label67
	CALL usb_handle_00057
	RETURN
label67
	MOVLW 0x06
	CPFSEQ gbl_control_mode, 1
	RETURN
	CLRF gbl_control_mode, 1
	RETURN
label68
	DECF usb_handle_0004D_1_pid, W, 1
	BTFSS STATUS,Z
	RETURN
	MOVLW 0x05
	CPFSEQ gbl_control_mode, 1
	BRA	label69
	CLRF gbl_control_mode, 1
	BRA	label71
label69
	MOVLW 0x02
	CPFSEQ gbl_control_mode, 1
	BRA	label70
	MOVLW HIGH(gbl_buffer_0_out+D'0')
	MOVWF usb_handle_00058_arg_data+D'1', 1
	MOVLW LOW(gbl_buffer_0_out+D'0')
	MOVWF usb_handle_00058_arg_data, 1
	MOVF gbl_bd0out+D'1', W, 1
	MOVWF usb_handle_00058_arg_count, 1
	CLRF usb_handle_00058_arg_count+D'1', 1
	CALL usb_handle_00058
	BRA	label71
label70
	MOVLW 0x03
	CPFSEQ gbl_control_mode, 1
	BRA	label71
	CLRF gbl_control_mode, 1
label71
	CALL usb_prime__00067
	RETURN
label72
	BTFSS gbl_ustat,2
	BRA	label73
	MOVLB 0x01
	LFSR 0x00, gbl_ep_in_bd_location
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_handle_0004D_1_end_point, W, 1
	MOVWF CompTempVar2224, 1
	BCF STATUS,C
	RLCF CompTempVar2224, W, 1
	ADDWF FSR0L, F
	MOVF POSTINC0, W
	MOVWF usb_handle_0004D_64_bd, 1
	MOVF INDF0, W
	MOVWF usb_handle_0004D_64_bd+D'1', 1
	MOVF usb_handle_0004D_1_end_point, W, 1
	MOVWF usb_ep_dat_00062_arg_end_point, 1
	MOVF usb_handle_0004D_64_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_handle_0004D_64_bd, W, 1
	MOVWF FSR0L
	MOVF PREINC0, W
	MOVWF usb_ep_dat_00062_arg_byte_count, 1
	CLRF usb_ep_dat_00062_arg_byte_count+D'1', 1
	CALL usb_ep_dat_00062
	RETURN
label73
	MOVLB 0x01
	LFSR 0x00, gbl_ep_out_bd_location
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_handle_0004D_1_end_point, W, 1
	MOVWF CompTempVar2227, 1
	BCF STATUS,C
	RLCF CompTempVar2227, W, 1
	ADDWF FSR0L, F
	MOVF POSTINC0, W
	MOVWF usb_handle_0004D_64_bd, 1
	MOVF INDF0, W
	MOVWF usb_handle_0004D_64_bd+D'1', 1
	MOVF usb_handle_0004D_1_end_point, W, 1
	MOVWF usb_ep_dat_00061_arg_end_point, 1
	MOVLB 0x01
	LFSR 0x00, gbl_ep_out_buffer_location
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_handle_0004D_1_end_point, W, 1
	MOVWF CompTempVar2234, 1
	BCF STATUS,C
	RLCF CompTempVar2234, W, 1
	ADDWF FSR0L, F
	MOVF POSTINC0, W
	MOVWF usb_ep_dat_00061_arg_buffer, 1
	MOVF INDF0, W
	MOVWF usb_ep_dat_00061_arg_buffer+D'1', 1
	MOVF usb_handle_0004D_64_bd+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_handle_0004D_64_bd, W, 1
	MOVWF FSR0L
	MOVF PREINC0, W
	MOVWF usb_ep_dat_00061_arg_byte_count, 1
	CLRF usb_ep_dat_00061_arg_byte_count+D'1', 1
	CALL usb_ep_dat_00061
	MOVLB 0x01
	LFSR 0x00, gbl_ep_out_buffer_size
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_handle_0004D_1_end_point, W, 1
	MOVWF CompTempVar2237, 1
	BCF STATUS,C
	RLCF CompTempVar2237, F, 1
	MOVF CompTempVar2237, W, 1
	ADDWF FSR0L, F
	MOVF POSTINC0, W
	MOVWF CompTempVar2239, 1
	MOVF INDF0, W
	MOVWF CompTempVar2239+D'1', 1
	MOVF usb_handle_0004D_64_bd+D'1', W, 1
	MOVWF FSR0H
	INCF usb_handle_0004D_64_bd, W, 1
	MOVWF FSR0L
	MOVF CompTempVar2239, W, 1
	MOVWF INDF0
	DECF FSR0L, F
	BCF INDF0,6
	BCF INDF0,5
	BCF INDF0,4
	BCF INDF0,3
	BCF INDF0,2
	BCF INDF0,1
	BCF INDF0,0
	BSF INDF0,7
	RETURN
; } usb_handle_transaction function end

	ORG 0x000008FE
usb_SOF_ca_00050
; { usb_SOF_callback ; function begin
	CALL usb_cdc_ha_00063
	RETURN
; } usb_SOF_callback function end

	ORG 0x00000904
usb_handle_0004C
; { usb_handle_isr ; function begin
	BTFSS gbl_pir2,2
	RETURN
	BCF gbl_pir2,2
	BTFSC gbl_uir,4
	BCF gbl_uir,4
	BTFSS gbl_uir,3
	BRA	label74
	CALL usb_handle_0004D
	BCF gbl_uir,3
label74
	BTFSS gbl_uir,0
	BRA	label75
	CALL usb_handle_0004E
	BCF gbl_uir,0
label75
	BTFSS gbl_uir,5
	BRA	label76
	CALL usb_handle_0004F
	BCF gbl_uir,5
label76
	BTFSS gbl_uir,6
	RETURN
	MOVLB 0x0F
	MOVF gbl_ufrml, W, 1
	MOVLB 0x02
	MOVWF usb_SOF_ca_00050_arg_frame, 1
	CLRF usb_SOF_ca_00050_arg_frame+D'1', 1
	CALL usb_SOF_ca_00050
	BCF gbl_uir,6
	RETURN
; } usb_handle_isr function end

	ORG 0x00000942
usb_setup_00000
; { usb_setup ; function begin
	MOVLB 0x02
	CLRF gbl_usb_state, 1
	BCF gbl_ucfg,3
	BSF gbl_ucfg,2
	BSF gbl_ucfg,4
	BCF gbl_ucfg,1
	BCF gbl_ucfg,0
	MOVLW HIGH(gbl_bd0in+D'0')
	MOVLB 0x01
	MOVWF gbl_ep_in_bd_location+D'1', 1
	MOVLW LOW(gbl_bd0in+D'0')
	MOVWF gbl_ep_in_bd_location, 1
	MOVLW HIGH(gbl_bd1in+D'0')
	MOVWF gbl_ep_in_bd_location+D'3', 1
	MOVLW LOW(gbl_bd1in+D'0')
	MOVWF gbl_ep_in_bd_location+D'2', 1
	MOVLW HIGH(gbl_bd2in+D'0')
	MOVWF gbl_ep_in_bd_location+D'5', 1
	MOVLW LOW(gbl_bd2in+D'0')
	MOVWF gbl_ep_in_bd_location+D'4', 1
	MOVLW HIGH(gbl_bd3in+D'0')
	MOVWF gbl_ep_in_bd_location+D'7', 1
	MOVLW LOW(gbl_bd3in+D'0')
	MOVWF gbl_ep_in_bd_location+D'6', 1
	MOVLW HIGH(gbl_bd0out+D'0')
	MOVWF gbl_ep_out_bd_location+D'1', 1
	MOVLW LOW(gbl_bd0out+D'0')
	MOVWF gbl_ep_out_bd_location, 1
	MOVLW HIGH(gbl_bd1out+D'0')
	MOVWF gbl_ep_out_bd_location+D'3', 1
	MOVLW LOW(gbl_bd1out+D'0')
	MOVWF gbl_ep_out_bd_location+D'2', 1
	MOVLW HIGH(gbl_bd2out+D'0')
	MOVWF gbl_ep_out_bd_location+D'5', 1
	MOVLW LOW(gbl_bd2out+D'0')
	MOVWF gbl_ep_out_bd_location+D'4', 1
	MOVLW HIGH(gbl_bd3out+D'0')
	MOVWF gbl_ep_out_bd_location+D'7', 1
	MOVLW LOW(gbl_bd3out+D'0')
	MOVWF gbl_ep_out_bd_location+D'6', 1
	MOVLB 0x0F
	BSF gbl_uep0,4, 1
	BSF gbl_uep0,2, 1
	BSF gbl_uep0,1, 1
	BCF gbl_uep0,3, 1
	BSF gbl_uie,5
	BSF gbl_uie,3
	BSF gbl_uie,0
	BSF gbl_pie2,2
	BSF gbl_uie,6
	RETURN
; } usb_setup function end

	ORG 0x000009A8
usb_cdc_se_0006F
; { usb_cdc_setup ; function begin
	MOVLW HIGH(gbl_class_data+D'0')
	MOVLB 0x02
	MOVWF usb_cdc_se_0006F_1_my_lc+D'1', 1
	MOVLW LOW(gbl_class_data+D'0')
	MOVWF usb_cdc_se_0006F_1_my_lc, 1
	MOVF usb_cdc_se_0006F_1_my_lc+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_se_0006F_1_my_lc, W, 1
	MOVWF FSR0L
	MOVLW 0x03
	ADDWF FSR0L, F
	MOVLW 0x00
	MOVWF CompTempVar2300, 1
	MOVWF CompTempVar2300+D'1', 1
	MOVLW 0x09
	MOVWF CompTempVar2300+D'2', 1
	MOVLW 0x60
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2300+D'2', W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2300+D'1', W, 1
	MOVWF INDF0
	DECF FSR0L, F
	MOVF CompTempVar2300, W, 1
	MOVWF INDF0
	MOVF usb_cdc_se_0006F_1_my_lc, W, 1
	MOVWF FSR0L
	MOVLW 0x04
	ADDWF FSR0L, F
	MOVLW 0x00
	MOVWF INDF0
	MOVF usb_cdc_se_0006F_1_my_lc, W, 1
	MOVWF FSR0L
	MOVLW 0x06
	ADDWF FSR0L, F
	MOVLW 0x08
	MOVWF INDF0
	MOVF usb_cdc_se_0006F_1_my_lc, W, 1
	MOVWF FSR0L
	MOVLW 0x05
	ADDWF FSR0L, F
	MOVLW 0x00
	MOVWF INDF0
	RETURN
; } usb_cdc_setup function end

	ORG 0x00000A04
usb_cdc_pu_0006B
; { usb_cdc_putc ; function begin
	INCF gbl_cdc_tx_end, W, 1
	MOVWF usb_cdc_pu_0006B_1_cdc_tx_next, 1
	MOVLW 0x40
	CPFSEQ usb_cdc_pu_0006B_1_cdc_tx_next, 1
	BRA	label77
	CLRF usb_cdc_pu_0006B_1_cdc_tx_next, 1
label77
	BTFSC gbl_intcon,7
	BRA	label78
	MOVF gbl_cdc_tx_start, W, 1
	CPFSEQ usb_cdc_pu_0006B_1_cdc_tx_next, 1
	BRA	label78
	RETURN
label78
	MOVF gbl_cdc_tx_start, W, 1
	CPFSEQ usb_cdc_pu_0006B_1_cdc_tx_next, 1
	BRA	label79
	BRA	label78
label79
	BCF usb_cdc_pu_0006B_1_my_store_gie,0, 1
	BTFSC gbl_intcon,7
	BSF usb_cdc_pu_0006B_1_my_store_gie,0, 1
label80
	BCF gbl_intcon,7
	BTFSC gbl_intcon,7
	BRA	label80
	MOVLB 0x00
	LFSR 0x00, gbl_cdc_tx_buffer
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF gbl_cdc_tx_end, W, 1
	ADDWF FSR0L, F
	MOVF usb_cdc_pu_0006B_arg_c, W, 1
	MOVWF INDF0
	MOVF usb_cdc_pu_0006B_1_cdc_tx_next, W, 1
	MOVWF gbl_cdc_tx_end, 1
	BTFSC usb_cdc_pu_0006B_1_my_store_gie,0, 1
	BSF gbl_intcon,7
	BTFSS usb_cdc_pu_0006B_1_my_store_gie,0, 1
	BCF gbl_intcon,7
	RETURN
; } usb_cdc_putc function end

	ORG 0x00000A50
usb_ep_get_00070
; { usb_ep_get_rx_buffer ; function begin
	MOVLW 0x40
	CPFSGT usb_ep_get_00070_arg_length, 1
	BRA	label81
	MOVLW 0x40
	MOVWF usb_ep_get_00070_arg_length, 1
label81
	CLRF usb_ep_get_00070_1_count, 1
label82
	MOVF usb_ep_get_00070_arg_length, W, 1
	CPFSLT usb_ep_get_00070_1_count, 1
	BRA	label83
	MOVLB 0x00
	LFSR 0x00, gbl_cdc_rx_buffer
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF usb_ep_get_00070_1_count, W, 1
	MOVWF CompTempVar2303, 1
	MOVF CompTempVar2303, W, 1
	ADDWF FSR0L, F
	MOVF INDF0, W
	MOVWF CompTempVar2304, 1
	MOVF usb_ep_get_00070_arg_buffer+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_ep_get_00070_arg_buffer, W, 1
	ADDWF usb_ep_get_00070_1_count, W, 1
	MOVWF FSR0L
	MOVF CompTempVar2304, W, 1
	MOVWF INDF0
	INCF usb_ep_get_00070_1_count, F, 1
	BRA	label82
label83
	MOVF gbl_cdc_rx_start, W, 1
	MOVWF gbl_cdc_rx_end, 1
	RETURN
; } usb_ep_get_rx_buffer function end

	ORG 0x00000A90
usb_enable_00051
; { usb_enable_module ; function begin
	CLRF gbl_uir
	BSF gbl_ucon,3
	MOVLW 0x01
	MOVLB 0x02
	MOVWF gbl_usb_state, 1
	RETURN
; } usb_enable_module function end

	ORG 0x00000A9C
usb_config_00071
; { usb_configure_structures ; function begin
	MOVLW 0x12
	MOVWF gbl_my_device_descriptor
	MOVLW 0x01
	MOVWF gbl_my_device_descriptor+D'1'
	MOVLW 0x10
	MOVWF gbl_my_device_descriptor+D'2'
	MOVLW 0x01
	MOVWF gbl_my_device_descriptor+D'3'
	MOVLW 0x02
	MOVWF gbl_my_device_descriptor+D'4'
	CLRF gbl_my_device_descriptor+D'5'
	CLRF gbl_my_device_descriptor+D'6'
	MOVLW 0x08
	MOVWF gbl_my_device_descriptor+D'7'
	MOVLW 0xBF
	MOVWF gbl_my_device_descriptor+D'8'
	MOVLW 0x12
	MOVWF gbl_my_device_descriptor+D'9'
	MOVLW 0x10
	MOVWF gbl_my_device_descriptor+D'10'
	MOVLW 0xF0
	MOVWF gbl_my_device_descriptor+D'11'
	CLRF gbl_my_device_descriptor+D'12'
	MOVLW 0x01
	MOVWF gbl_my_device_descriptor+D'13'
	MOVWF gbl_my_device_descriptor+D'14'
	MOVLW 0x02
	MOVWF gbl_my_device_descriptor+D'15'
	CLRF gbl_my_device_descriptor+D'16'
	MOVLW 0x01
	MOVWF gbl_my_device_descriptor+D'17'
	MOVLW 0x09
	MOVLB 0x00
	MOVWF gbl_my_config, 1
	MOVLW 0x02
	MOVWF gbl_my_config+D'1', 1
	MOVLW 0x43
	MOVWF gbl_my_config+D'2', 1
	CLRF gbl_my_config+D'3', 1
	MOVLW 0x02
	MOVWF gbl_my_config+D'4', 1
	MOVLW 0x01
	MOVWF gbl_my_config+D'5', 1
	CLRF gbl_my_config+D'6', 1
	MOVLW 0x80
	MOVWF gbl_my_config+D'7', 1
	MOVLW 0x64
	MOVWF gbl_my_config+D'8', 1
	MOVLW 0x09
	MOVWF gbl_my_comm_interface, 1
	MOVLW 0x04
	MOVWF gbl_my_comm_interface+D'1', 1
	CLRF gbl_my_comm_interface+D'2', 1
	CLRF gbl_my_comm_interface+D'3', 1
	MOVLW 0x01
	MOVWF gbl_my_comm_interface+D'4', 1
	MOVLW 0x02
	MOVWF gbl_my_comm_interface+D'5', 1
	MOVWF gbl_my_comm_interface+D'6', 1
	MOVLW 0x01
	MOVWF gbl_my_comm_interface+D'7', 1
	CLRF gbl_my_comm_interface+D'8', 1
	MOVLW 0x05
	MOVWF gbl_my_header
	MOVLW 0x24
	MOVWF gbl_my_header+D'1'
	CLRF gbl_my_header+D'2'
	MOVLW 0x10
	MOVWF gbl_my_header+D'3'
	MOVLW 0x01
	MOVWF gbl_my_header+D'4'
	MOVLW 0x04
	MOVLB 0x02
	MOVWF gbl_my_ACM, 1
	MOVLW 0x24
	MOVWF gbl_my_ACM+D'1', 1
	MOVLW 0x02
	MOVWF gbl_my_ACM+D'2', 1
	MOVWF gbl_my_ACM+D'3', 1
	MOVLW 0x05
	MOVLB 0x01
	MOVWF gbl_my_union, 1
	MOVLW 0x24
	MOVWF gbl_my_union+D'1', 1
	MOVLW 0x06
	MOVWF gbl_my_union+D'2', 1
	CLRF gbl_my_union+D'3', 1
	MOVLW 0x01
	MOVWF gbl_my_union+D'4', 1
	MOVLW 0x05
	MOVWF gbl_my_call_mgt, 1
	MOVLW 0x24
	MOVWF gbl_my_call_mgt+D'1', 1
	MOVLW 0x01
	MOVWF gbl_my_call_mgt+D'2', 1
	CLRF gbl_my_call_mgt+D'3', 1
	MOVLW 0x01
	MOVWF gbl_my_call_mgt+D'4', 1
	MOVLW 0x07
	MOVWF gbl_my_notification_ep, 1
	MOVLW 0x05
	MOVWF gbl_my_notification_ep+D'1', 1
	MOVLW 0x82
	MOVWF gbl_my_notification_ep+D'2', 1
	MOVLW 0x03
	MOVWF gbl_my_notification_ep+D'3', 1
	MOVLW 0x08
	MOVWF gbl_my_notification_ep+D'4', 1
	CLRF gbl_my_notification_ep+D'5', 1
	MOVLW 0x02
	MOVWF gbl_my_notification_ep+D'6', 1
	MOVLW 0x09
	MOVWF gbl_my_data_interface, 1
	MOVLW 0x04
	MOVWF gbl_my_data_interface+D'1', 1
	MOVLW 0x01
	MOVWF gbl_my_data_interface+D'2', 1
	CLRF gbl_my_data_interface+D'3', 1
	MOVLW 0x02
	MOVWF gbl_my_data_interface+D'4', 1
	MOVLW 0x0A
	MOVWF gbl_my_data_interface+D'5', 1
	CLRF gbl_my_data_interface+D'6', 1
	CLRF gbl_my_data_interface+D'7', 1
	CLRF gbl_my_data_interface+D'8', 1
	MOVLW 0x07
	MOVWF gbl_my_data_out_ep, 1
	MOVLW 0x05
	MOVWF gbl_my_data_out_ep+D'1', 1
	MOVLW 0x03
	MOVWF gbl_my_data_out_ep+D'2', 1
	MOVLW 0x02
	MOVWF gbl_my_data_out_ep+D'3', 1
	MOVLW 0x08
	MOVWF gbl_my_data_out_ep+D'4', 1
	CLRF gbl_my_data_out_ep+D'5', 1
	CLRF gbl_my_data_out_ep+D'6', 1
	MOVLW 0x07
	MOVWF gbl_my_data_in_ep, 1
	MOVLW 0x05
	MOVWF gbl_my_data_in_ep+D'1', 1
	MOVLW 0x83
	MOVWF gbl_my_data_in_ep+D'2', 1
	MOVLW 0x02
	MOVWF gbl_my_data_in_ep+D'3', 1
	MOVLW 0x08
	MOVWF gbl_my_data_in_ep+D'4', 1
	CLRF gbl_my_data_in_ep+D'5', 1
	CLRF gbl_my_data_in_ep+D'6', 1
	MOVLB 0x00
	MOVF gbl_my_config+D'8', W, 1
	MOVWF gbl_complete_serial_config_00000+D'8'
	MOVF gbl_my_config+D'7', W, 1
	MOVWF gbl_complete_serial_config_00000+D'7'
	MOVF gbl_my_config+D'6', W, 1
	MOVWF gbl_complete_serial_config_00000+D'6'
	MOVF gbl_my_config+D'5', W, 1
	MOVWF gbl_complete_serial_config_00000+D'5'
	MOVF gbl_my_config+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'4'
	MOVF gbl_my_config+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'3'
	MOVF gbl_my_config+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'2'
	MOVF gbl_my_config+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'1'
	MOVF gbl_my_config, W, 1
	MOVWF gbl_complete_serial_config_00000
	MOVF gbl_my_comm_interface+D'8', W, 1
	MOVWF gbl_complete_serial_config_00000+D'17'
	MOVF gbl_my_comm_interface+D'7', W, 1
	MOVWF gbl_complete_serial_config_00000+D'16'
	MOVF gbl_my_comm_interface+D'6', W, 1
	MOVWF gbl_complete_serial_config_00000+D'15'
	MOVF gbl_my_comm_interface+D'5', W, 1
	MOVWF gbl_complete_serial_config_00000+D'14'
	MOVF gbl_my_comm_interface+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'13'
	MOVF gbl_my_comm_interface+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'12'
	MOVF gbl_my_comm_interface+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'11'
	MOVF gbl_my_comm_interface+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'10'
	MOVF gbl_my_comm_interface, W, 1
	MOVWF gbl_complete_serial_config_00000+D'9'
	MOVF gbl_my_header+D'4', W
	MOVWF gbl_complete_serial_config_00000+D'22'
	MOVF gbl_my_header+D'3', W
	MOVWF gbl_complete_serial_config_00000+D'21'
	MOVF gbl_my_header+D'2', W
	MOVWF gbl_complete_serial_config_00000+D'20'
	MOVF gbl_my_header+D'1', W
	MOVWF gbl_complete_serial_config_00000+D'19'
	MOVF gbl_my_header, W
	MOVWF gbl_complete_serial_config_00000+D'18'
	MOVLB 0x02
	MOVF gbl_my_ACM+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'26'
	MOVF gbl_my_ACM+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'25'
	MOVF gbl_my_ACM+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'24'
	MOVF gbl_my_ACM, W, 1
	MOVWF gbl_complete_serial_config_00000+D'23'
	MOVLB 0x01
	MOVF gbl_my_union+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'31'
	MOVF gbl_my_union+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'30'
	MOVF gbl_my_union+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'29'
	MOVF gbl_my_union+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'28'
	MOVF gbl_my_union, W, 1
	MOVWF gbl_complete_serial_config_00000+D'27'
	MOVF gbl_my_call_mgt+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'36'
	MOVF gbl_my_call_mgt+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'35'
	MOVF gbl_my_call_mgt+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'34'
	MOVF gbl_my_call_mgt+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'33'
	MOVF gbl_my_call_mgt, W, 1
	MOVWF gbl_complete_serial_config_00000+D'32'
	MOVF gbl_my_notification_ep+D'6', W, 1
	MOVWF gbl_complete_serial_config_00000+D'43'
	MOVF gbl_my_notification_ep+D'5', W, 1
	MOVWF gbl_complete_serial_config_00000+D'42'
	MOVF gbl_my_notification_ep+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'41'
	MOVF gbl_my_notification_ep+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'40'
	MOVF gbl_my_notification_ep+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'39'
	MOVF gbl_my_notification_ep+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'38'
	MOVF gbl_my_notification_ep, W, 1
	MOVWF gbl_complete_serial_config_00000+D'37'
	MOVF gbl_my_data_interface+D'8', W, 1
	MOVWF gbl_complete_serial_config_00000+D'52'
	MOVF gbl_my_data_interface+D'7', W, 1
	MOVWF gbl_complete_serial_config_00000+D'51'
	MOVF gbl_my_data_interface+D'6', W, 1
	MOVWF gbl_complete_serial_config_00000+D'50'
	MOVF gbl_my_data_interface+D'5', W, 1
	MOVWF gbl_complete_serial_config_00000+D'49'
	MOVF gbl_my_data_interface+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'48'
	MOVF gbl_my_data_interface+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'47'
	MOVF gbl_my_data_interface+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'46'
	MOVF gbl_my_data_interface+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'45'
	MOVF gbl_my_data_interface, W, 1
	MOVWF gbl_complete_serial_config_00000+D'44'
	MOVF gbl_my_data_out_ep+D'6', W, 1
	MOVWF gbl_complete_serial_config_00000+D'59'
	MOVF gbl_my_data_out_ep+D'5', W, 1
	MOVWF gbl_complete_serial_config_00000+D'58'
	MOVF gbl_my_data_out_ep+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'57'
	MOVF gbl_my_data_out_ep+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'56'
	MOVF gbl_my_data_out_ep+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'55'
	MOVF gbl_my_data_out_ep+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'54'
	MOVF gbl_my_data_out_ep, W, 1
	MOVWF gbl_complete_serial_config_00000+D'53'
	MOVF gbl_my_data_in_ep+D'6', W, 1
	MOVWF gbl_complete_serial_config_00000+D'66'
	MOVF gbl_my_data_in_ep+D'5', W, 1
	MOVWF gbl_complete_serial_config_00000+D'65'
	MOVF gbl_my_data_in_ep+D'4', W, 1
	MOVWF gbl_complete_serial_config_00000+D'64'
	MOVF gbl_my_data_in_ep+D'3', W, 1
	MOVWF gbl_complete_serial_config_00000+D'63'
	MOVF gbl_my_data_in_ep+D'2', W, 1
	MOVWF gbl_complete_serial_config_00000+D'62'
	MOVF gbl_my_data_in_ep+D'1', W, 1
	MOVWF gbl_complete_serial_config_00000+D'61'
	MOVF gbl_my_data_in_ep, W, 1
	MOVWF gbl_complete_serial_config_00000+D'60'
	RETURN
; } usb_configure_structures function end

	ORG 0x00000CDA
usb_cdc_tx_0006D
; { usb_cdc_tx_empty ; function begin
	MOVLB 0x02
	CLRF CompTempVarRet2292, 1
	MOVF gbl_cdc_tx_end, W, 1
	CPFSEQ gbl_cdc_tx_start, 1
	RETURN
	INCF CompTempVarRet2292, F, 1
	RETURN
; } usb_cdc_tx_empty function end

	ORG 0x00000CE8
usb_cdc_rx_0006C
; { usb_cdc_rx_avail ; function begin
	MOVLB 0x02
	MOVF gbl_cdc_rx_start, W, 1
	SUBWF gbl_cdc_rx_end, W, 1
	MOVWF CompTempVarRet2291, 1
	RETURN
; } usb_cdc_rx_avail function end

	ORG 0x00000CF2
usb_cdc_pr_0006E
; { usb_cdc_print_str ; function begin
	CLRF usb_cdc_pr_0006E_1_count, 1
label84
	MOVF usb_cdc_pr_0006E_arg_str+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_pr_0006E_arg_str, W, 1
	ADDWF usb_cdc_pr_0006E_1_count, W, 1
	MOVWF FSR0L
	MOVF INDF0, F
	BTFSC STATUS,Z
	RETURN
	MOVF usb_cdc_pr_0006E_arg_str+D'1', W, 1
	MOVWF FSR0H
	MOVF usb_cdc_pr_0006E_arg_str, W, 1
	ADDWF usb_cdc_pr_0006E_1_count, W, 1
	MOVWF FSR0L
	MOVF INDF0, W
	MOVWF usb_cdc_pu_0006B_arg_c, 1
	CALL usb_cdc_pu_0006B
	INCF usb_cdc_pr_0006E_1_count, F, 1
	BRA	label84
; } usb_cdc_print_str function end

	ORG 0x00000D1A
Wdt_Delay__00039
; { Wdt_Delay_Ms ; function begin
	CLRF Wdt_Delay__00039_1_i, 1
label85
	MOVF Wdt_Delay__00039_arg_delay, W, 1
	CPFSLT Wdt_Delay__00039_1_i, 1
	RETURN
	CLRWDT
	MOVLW 0x01
	MOVWF delay_ms_00000_arg_del, 1
	CALL delay_ms_00000
	INCF Wdt_Delay__00039_1_i, F, 1
	BRA	label85
; } Wdt_Delay_Ms function end

	ORG 0x00000D30
FC_CAL_Sam_0007A
; { FC_CAL_Sample_ADC ; function begin
	MOVLW 0x02
	IORWF gbl_adcon0, W
	MOVWF gbl_adcon0
label86
	BTFSC gbl_adcon0,1
	BRA	label86
	MOVF FC_CAL_Sam_0007A_arg_Sample_Mode, F, 1
	BZ	label87
	MOVF gbl_adresh, W
	MOVWF FC_CAL_Sam_0007A_1_iRetVal, 1
	CLRF FC_CAL_Sam_0007A_1_iRetVal+D'1', 1
	RLCF FC_CAL_Sam_0007A_1_iRetVal, F, 1
	RLCF FC_CAL_Sam_0007A_1_iRetVal+D'1', F, 1
	RLCF FC_CAL_Sam_0007A_1_iRetVal, F, 1
	RLCF FC_CAL_Sam_0007A_1_iRetVal+D'1', F, 1
	MOVLW 0xFC
	ANDWF FC_CAL_Sam_0007A_1_iRetVal, F, 1
	RRCF gbl_adresl, W
	MOVWF CompTempVar2323, 1
	RRCF CompTempVar2323, F, 1
	SWAPF CompTempVar2323, F, 1
	MOVLW 0x03
	ANDWF CompTempVar2323, W, 1
	IORWF FC_CAL_Sam_0007A_1_iRetVal, F, 1
	MOVF FC_CAL_Sam_0007A_1_iRetVal+D'1', F, 1
	BRA	label88
label87
	MOVF gbl_adresh, W
	MOVWF FC_CAL_Sam_0007A_1_iRetVal, 1
	CLRF FC_CAL_Sam_0007A_1_iRetVal+D'1', 1
label88
	MOVF FC_CAL_Sam_0007A_1_iRetVal, W, 1
	MOVWF CompTempVarRet2320, 1
	MOVF FC_CAL_Sam_0007A_1_iRetVal+D'1', W, 1
	MOVWF CompTempVarRet2320+D'1', 1
	RETURN
; } FC_CAL_Sample_ADC function end

	ORG 0x00000D72
FC_CAL_Ena_00079
; { FC_CAL_Enable_ADC ; function begin
	CLRF gbl_adcon1
	MOVLW 0x03
	CPFSEQ FC_CAL_Ena_00079_arg_Channel, 1
	BRA	label89
	BRA	label90
label89
	MOVLW 0x04
	CPFSEQ FC_CAL_Ena_00079_arg_Channel, 1
	BRA	label92
	BRA	label91
label90
	MOVLW 0x10
	MOVWF gbl_tris_mask, 1
	MOVLW HIGH(gbl_trisa+D'0')
	MOVWF gbl_tris_reg+D'1', 1
	MOVLW LOW(gbl_trisa+D'0')
	MOVWF gbl_tris_reg, 1
	MOVLW 0x08
	MOVWF gbl_ansel
label91
	MOVLW 0x01
	MOVWF gbl_tris_mask, 1
	MOVLW HIGH(gbl_trisc+D'0')
	MOVWF CompTempVar2317, 1
	MOVLW LOW(gbl_trisc+D'0')
	MOVWF gbl_tris_reg, 1
	MOVF CompTempVar2317, W, 1
	MOVWF gbl_tris_reg+D'1', 1
	MOVLW 0x10
	MOVWF gbl_ansel
label92
	MOVLW 0x07
	ANDWF FC_CAL_Ena_00079_arg_Conv_Speed, W, 1
	MOVWF gbl_adcon2
	MOVF FC_CAL_Ena_00079_arg_Vref, F, 1
	BTFSS STATUS,Z
	BSF gbl_adcon1,2
	MOVF gbl_tris_reg+D'1', W, 1
	MOVWF FSR0H
	MOVF gbl_tris_reg, W, 1
	MOVWF FSR0L
	MOVF INDF0, W
	MOVWF gbl_old_tris, 1
	MOVF gbl_tris_mask, W, 1
	IORWF gbl_old_tris, W, 1
	MOVWF CompTempVar2318, 1
	MOVWF INDF0
	MOVF FC_CAL_Ena_00079_arg_Channel, W, 1
	MOVWF CompTempVar2319, 1
	RLCF CompTempVar2319, F, 1
	RLCF CompTempVar2319, F, 1
	MOVLW 0xFC
	ANDWF CompTempVar2319, W, 1
	IORLW 0x01
	MOVWF gbl_adcon0
	MOVF FC_CAL_Ena_00079_arg_T_Charge, W, 1
	MOVWF delay_us_00000_arg_del, 1
	CALL delay_us_00000
	RETURN
; } FC_CAL_Enable_ADC function end

	ORG 0x00000DE2
FC_CAL_Dis_0007B
; { FC_CAL_Disable_ADC ; function begin
	MOVLB 0x02
	MOVF gbl_tris_reg+D'1', W, 1
	MOVWF FSR0H
	MOVF gbl_tris_reg, W, 1
	MOVWF FSR0L
	MOVF gbl_old_tris, W, 1
	MOVWF INDF0
	CLRF gbl_ansel
	CLRF gbl_adcon0
	CLRF gbl_anselh
	RETURN
; } FC_CAL_Disable_ADC function end

	ORG 0x00000DF8
FCD_USBSer_00072
; { FCD_USBSerial0_configure_system ; function begin
label93
	BCF gbl_intcon,7
	BTFSC gbl_intcon,7
	BRA	label93
	CALL usb_cdc_se_0006F
	CALL usb_setup_00000
	BSF gbl_intcon,6
	BSF gbl_intcon,7
	RETURN
; } FCD_USBSerial0_configure_system function end

	ORG 0x00000E0C
FCD_USBSer_00076
; { FCD_USBSerial0_ReadByte ; function begin
	CLRF FCD_USBSer_00076_1_waitforever, 1
	BTFSC CompGblVar65,0, 1
	BRA	label94
	CLRF FCD_USBSer_00076_1_position, 1
	BSF CompGblVar65,0, 1
label94
	BTFSC CompGblVar66,1, 1
	BRA	label95
	CLRF FCD_USBSer_00076_1_length, 1
	BSF CompGblVar66,1, 1
label95
	SETF FCD_USBSer_00076_1_retval, 1
	INCF FCD_USBSer_00076_arg_timeout_ms, W, 1
	BNZ	label96
	MOVLW 0x01
	MOVWF FCD_USBSer_00076_1_waitforever, 1
label96
	MOVF FCD_USBSer_00076_1_length, W, 1
	CPFSEQ FCD_USBSer_00076_1_position, 1
	BRA	label101
	CLRF FCD_USBSer_00076_1_position, 1
	CALL usb_cdc_rx_0006C
	MOVF CompTempVarRet2291, W, 1
	MOVWF FCD_USBSer_00076_1_length, 1
label97
	MOVF FCD_USBSer_00076_1_length, F, 1
	BNZ	label99
	MOVF FCD_USBSer_00076_arg_timeout_ms, F, 1
	BNZ	label98
	MOVF FCD_USBSer_00076_1_waitforever, F, 1
	BZ	label99
label98
	MOVLW 0x01
	MOVWF Wdt_Delay__00039_arg_delay, 1
	CALL Wdt_Delay__00039
	CALL usb_cdc_rx_0006C
	MOVF CompTempVarRet2291, W, 1
	MOVWF FCD_USBSer_00076_1_length, 1
	DECF FCD_USBSer_00076_arg_timeout_ms, W, 1
	MOVWF FCD_USBSer_00076_arg_timeout_ms, 1
	BRA	label97
label99
	MOVF FCD_USBSer_00076_1_length, F, 1
	BNZ	label100
	MOVF FCD_USBSer_00076_1_retval, W, 1
	MOVWF CompTempVarRet2312, 1
	RETURN
label100
	MOVF FCD_USBSer_00076_1_length, W, 1
	MOVWF usb_ep_get_00070_arg_length, 1
	MOVLW HIGH(gbl_CDC_USB_Rx_String+D'0')
	MOVWF usb_ep_get_00070_arg_buffer+D'1', 1
	MOVLW LOW(gbl_CDC_USB_Rx_String+D'0')
	MOVWF usb_ep_get_00070_arg_buffer, 1
	CALL usb_ep_get_00070
label101
	MOVLB 0x01
	LFSR 0x00, gbl_CDC_USB_Rx_String
	MOVF FSR0L, W
	MOVLB 0x02
	MOVF FCD_USBSer_00076_1_position, W, 1
	ADDWF FSR0L, F
	MOVF INDF0, W
	MOVWF FCD_USBSer_00076_1_retval, 1
	INCF FCD_USBSer_00076_1_position, W, 1
	MOVWF FCD_USBSer_00076_1_position, 1
	MOVF FCD_USBSer_00076_1_retval, W, 1
	MOVWF CompTempVarRet2312, 1
	RETURN
; } FCD_USBSerial0_ReadByte function end

	ORG 0x00000E90
FCD_USBSer_00075
; { FCD_USBSerial0_SendString ; function begin
	CLRF FCD_USBSer_00075_1_timeout, 1
	CLRF FCD_USBSer_00075_1_timeout+D'1', 1
label102
	CALL usb_cdc_tx_0006D
	MOVF CompTempVarRet2292, F, 1
	BNZ	label104
	MOVLW 0x7D
	SUBWF FCD_USBSer_00075_1_timeout+D'1', W, 1
	BNZ	label103
	MOVLW 0x00
	SUBWF FCD_USBSer_00075_1_timeout, W, 1
label103
	BC	label104
	MOVF FCD_USBSer_00075_1_timeout, F, 1
	MOVF FCD_USBSer_00075_1_timeout+D'1', F, 1
	INCF FCD_USBSer_00075_1_timeout, F, 1
	BTFSC STATUS,Z
	INCF FCD_USBSer_00075_1_timeout+D'1', F, 1
	BRA	label102
label104
	MOVLW 0x00
	CPFSEQ FCD_USBSer_00075_1_timeout, 1
	BRA	label105
	MOVLW 0x7D
	CPFSEQ FCD_USBSer_00075_1_timeout+D'1', 1
	BRA	label105
	MOVLW 0x01
	MOVWF CompTempVarRet2309, 1
	RETURN
label105
	MOVF FCD_USBSer_00075_arg_String, W, 1
	MOVWF usb_cdc_pr_0006E_arg_str, 1
	MOVF FCD_USBSer_00075_arg_String+D'1', W, 1
	MOVWF usb_cdc_pr_0006E_arg_str+D'1', 1
	CALL usb_cdc_pr_0006E
	CLRF CompTempVarRet2309, 1
	RETURN
; } FCD_USBSerial0_SendString function end

	ORG 0x00000ED6
FCD_USBSer_00074
; { FCD_USBSerial0_SendByte ; function begin
	CLRF FCD_USBSer_00074_1_timeout, 1
	CLRF FCD_USBSer_00074_1_timeout+D'1', 1
label106
	CALL usb_cdc_tx_0006D
	MOVF CompTempVarRet2292, F, 1
	BNZ	label108
	MOVLW 0x7D
	SUBWF FCD_USBSer_00074_1_timeout+D'1', W, 1
	BNZ	label107
	MOVLW 0x00
	SUBWF FCD_USBSer_00074_1_timeout, W, 1
label107
	BC	label108
	MOVF FCD_USBSer_00074_1_timeout, F, 1
	MOVF FCD_USBSer_00074_1_timeout+D'1', F, 1
	INCF FCD_USBSer_00074_1_timeout, F, 1
	BTFSC STATUS,Z
	INCF FCD_USBSer_00074_1_timeout+D'1', F, 1
	BRA	label106
label108
	MOVLW 0x00
	CPFSEQ FCD_USBSer_00074_1_timeout, 1
	BRA	label109
	MOVLW 0x7D
	CPFSEQ FCD_USBSer_00074_1_timeout+D'1', 1
	BRA	label109
	MOVLW 0x01
	MOVWF CompTempVarRet2306, 1
	RETURN
label109
	MOVF FCD_USBSer_00074_arg_byte, W, 1
	MOVWF usb_cdc_pu_0006B_arg_c, 1
	CALL usb_cdc_pu_0006B
	CLRF CompTempVarRet2306, 1
	RETURN
; } FCD_USBSerial0_SendByte function end

	ORG 0x00000F18
FCD_USBSer_00073
; { FCD_USBSerial0_Initialise_Serial ; function begin
	CALL usb_config_00071
	CALL FCD_USBSer_00072
	CALL usb_enable_00051
label110
	MOVF gbl_CDC_USB_status, F, 1
	BZ	label110
	CLRF CompTempVarRet2305, 1
	RETURN
; } FCD_USBSerial0_Initialise_Serial function end

	ORG 0x00000F2C
FCD_ADC1_R_00089
; { FCD_ADC1_ReadAsByte ; function begin
	MOVLW 0x04
	MOVLB 0x02
	MOVWF FC_CAL_Ena_00079_arg_Channel, 1
	MOVLW 0x03
	MOVWF FC_CAL_Ena_00079_arg_Conv_Speed, 1
	CLRF FC_CAL_Ena_00079_arg_Vref, 1
	MOVLW 0x28
	MOVWF FC_CAL_Ena_00079_arg_T_Charge, 1
	CALL FC_CAL_Ena_00079
	CLRF FC_CAL_Sam_0007A_arg_Sample_Mode, 1
	CALL FC_CAL_Sam_0007A
	MOVF CompTempVarRet2320, W, 1
	MOVWF FCD_ADC1_R_00089_1_retVal, 1
	CALL FC_CAL_Dis_0007B
	MOVF FCD_ADC1_R_00089_1_retVal, W, 1
	MOVWF CompTempVarRet2354, 1
	RETURN
; } FCD_ADC1_ReadAsByte function end

	ORG 0x00000F54
FCD_ADC0_R_0007E
; { FCD_ADC0_ReadAsInt ; function begin
	MOVLW 0x03
	MOVLB 0x02
	MOVWF FC_CAL_Ena_00079_arg_Channel, 1
	MOVWF FC_CAL_Ena_00079_arg_Conv_Speed, 1
	CLRF FC_CAL_Ena_00079_arg_Vref, 1
	MOVLW 0x28
	MOVWF FC_CAL_Ena_00079_arg_T_Charge, 1
	CALL FC_CAL_Ena_00079
	MOVLW 0x01
	MOVWF FC_CAL_Sam_0007A_arg_Sample_Mode, 1
	CALL FC_CAL_Sam_0007A
	MOVF CompTempVarRet2320, W, 1
	MOVWF FCD_ADC0_R_0007E_1_retVal, 1
	MOVF CompTempVarRet2320+D'1', W, 1
	MOVWF FCD_ADC0_R_0007E_1_retVal+D'1', 1
	CALL FC_CAL_Dis_0007B
	MOVF FCD_ADC0_R_0007E_1_retVal, W, 1
	MOVWF CompTempVarRet2327, 1
	MOVF FCD_ADC0_R_0007E_1_retVal+D'1', W, 1
	MOVWF CompTempVarRet2327+D'1', 1
	RETURN
; } FCD_ADC0_ReadAsInt function end

	ORG 0x00000F84
main
; { main ; function begin
	CLRF gbl_ansel
	CLRF gbl_anselh
	CALL FCD_USBSer_00073
label111
	MOVLW 0x0A
	MOVWF FCD_USBSer_00076_arg_timeout_ms, 1
	CALL FCD_USBSer_00076
	MOVF CompTempVarRet2312, W, 1
	MOVWF gbl_FCV_OPERATION, 1
	MOVLW 0x31
	CPFSEQ gbl_FCV_OPERATION, 1
	BRA	label112
	BRA	label113
label112
	MOVLW 0x32
	CPFSEQ gbl_FCV_OPERATION, 1
	BRA	label111
	BRA	label114
label113
	CALL FCD_ADC0_R_0007E
	MOVF CompTempVarRet2327, W, 1
	MOVWF gbl_FCV_READINPUT, 1
	MOVF CompTempVarRet2327+D'1', W, 1
	MOVWF gbl_FCV_READINPUT+D'1', 1
	MOVLW 0x01
	IORWF gbl_FCV_READINPUT, F, 1
	MOVF gbl_FCV_READINPUT+D'1', W, 1
	IORLW 0x10
	MOVWF gbl_FCV_READINPUT+D'1', 1
	MOVF gbl_FCV_READINPUT, W, 1
	MOVWF gbl_FCV_OUTSTRING, 1
	MOVF gbl_FCV_READINPUT+D'1', W, 1
	MOVWF gbl_FCV_OUTSTRING+D'1', 1
	MOVLW HIGH(gbl_FCV_OUTSTRING+D'0')
	MOVWF FCD_USBSer_00075_arg_String+D'1', 1
	MOVLW LOW(gbl_FCV_OUTSTRING+D'0')
	MOVWF FCD_USBSer_00075_arg_String, 1
	MOVLW 0x02
	MOVWF FCD_USBSer_00075_arg_MSZ_String, 1
	CALL FCD_USBSer_00075
	BRA	label111
label114
	CALL FCD_ADC1_R_00089
	MOVF CompTempVarRet2354, W, 1
	MOVWF gbl_FCV_OUTSTR, 1
	MOVF gbl_FCV_OUTSTR, W, 1
	MOVWF FCD_USBSer_00074_arg_byte, 1
	CALL FCD_USBSer_00074
	BRA	label111
; } main function end

	ORG 0x00000FEA
_startup
	MOVLW 0xD5
	MOVLB 0x00
	MOVWF gbl_14_LSR, 1
	MOVLW 0xC4
	MOVWF gbl_14_LSR+D'1', 1
	MOVLW 0xBB
	MOVWF gbl_14_LSR+D'2', 1
	MOVLW 0xDC
	MOVWF gbl_14_LSR+D'3', 1
	MOVLB 0x02
	CLRF gbl_15_gbl_aSig, 1
	CLRF gbl_15_gbl_aSig+D'1', 1
	CLRF gbl_15_gbl_aSig+D'2', 1
	CLRF gbl_15_gbl_aSig+D'3', 1
	CLRF gbl_15_gbl_bSig, 1
	CLRF gbl_15_gbl_bSig+D'1', 1
	CLRF gbl_15_gbl_bSig+D'2', 1
	CLRF gbl_15_gbl_bSig+D'3', 1
	CLRF gbl_15_gbl_zSig, 1
	CLRF gbl_15_gbl_zSig+D'1', 1
	CLRF gbl_15_gbl_zSig+D'2', 1
	CLRF gbl_15_gbl_zSig+D'3', 1
	CLRF gbl_15_gbl_aExp, 1
	CLRF gbl_15_gbl_bExp, 1
	MOVLB 0x01
	CLRF gbl_15_gbl_zExp, 1
	CLRF gbl_15_gbl_zExp+D'1', 1
	MOVLB 0x02
	CLRF gbl_15_gbl_aSign, 1
	CLRF gbl_15_gbl_bSign, 1
	CLRF gbl_15_gbl_zSign, 1
	CLRF gbl_15_gbl_zSigZero, 1
	CLRF gbl_15_gbl_ret, 1
	CLRF gbl_15_gbl_ret+D'1', 1
	CLRF gbl_15_gbl_ret+D'2', 1
	CLRF gbl_15_gbl_ret+D'3', 1
	CLRF gbl_float_rounding_mode, 1
	CLRF gbl_float_exception_flags, 1
	CLRF gbl_float_detect_tininess
	BCF CompGblVar65,0, 1
	BCF CompGblVar66,1, 1
	MOVLW 0x28
	MOVLB 0x01
	MOVWF gbl_MX_USBNAME, 1
	MOVLW 0x03
	MOVWF gbl_MX_USBNAME+D'1', 1
	MOVLW 0x46
	MOVWF gbl_MX_USBNAME+D'2', 1
	CLRF gbl_MX_USBNAME+D'3', 1
	MOVLW 0x6C
	MOVWF gbl_MX_USBNAME+D'4', 1
	CLRF gbl_MX_USBNAME+D'5', 1
	MOVLW 0x6F
	MOVWF gbl_MX_USBNAME+D'6', 1
	CLRF gbl_MX_USBNAME+D'7', 1
	MOVLW 0x77
	MOVWF gbl_MX_USBNAME+D'8', 1
	CLRF gbl_MX_USBNAME+D'9', 1
	MOVLW 0x63
	MOVWF gbl_MX_USBNAME+D'10', 1
	CLRF gbl_MX_USBNAME+D'11', 1
	MOVLW 0x6F
	MOVWF gbl_MX_USBNAME+D'12', 1
	CLRF gbl_MX_USBNAME+D'13', 1
	MOVLW 0x64
	MOVWF gbl_MX_USBNAME+D'14', 1
	CLRF gbl_MX_USBNAME+D'15', 1
	MOVLW 0x65
	MOVWF gbl_MX_USBNAME+D'16', 1
	CLRF gbl_MX_USBNAME+D'17', 1
	MOVLW 0x20
	MOVWF gbl_MX_USBNAME+D'18', 1
	CLRF gbl_MX_USBNAME+D'19', 1
	MOVLW 0x55
	MOVWF gbl_MX_USBNAME+D'20', 1
	CLRF gbl_MX_USBNAME+D'21', 1
	MOVLW 0x53
	MOVWF gbl_MX_USBNAME+D'22', 1
	CLRF gbl_MX_USBNAME+D'23', 1
	MOVLW 0x42
	MOVWF gbl_MX_USBNAME+D'24', 1
	CLRF gbl_MX_USBNAME+D'25', 1
	MOVLW 0x20
	MOVWF gbl_MX_USBNAME+D'26', 1
	CLRF gbl_MX_USBNAME+D'27', 1
	MOVLW 0x53
	MOVWF gbl_MX_USBNAME+D'28', 1
	CLRF gbl_MX_USBNAME+D'29', 1
	MOVLW 0x65
	MOVWF gbl_MX_USBNAME+D'30', 1
	CLRF gbl_MX_USBNAME+D'31', 1
	MOVLW 0x72
	MOVWF gbl_MX_USBNAME+D'32', 1
	CLRF gbl_MX_USBNAME+D'33', 1
	MOVLW 0x69
	MOVWF gbl_MX_USBNAME+D'34', 1
	CLRF gbl_MX_USBNAME+D'35', 1
	MOVLW 0x61
	MOVWF gbl_MX_USBNAME+D'36', 1
	CLRF gbl_MX_USBNAME+D'37', 1
	MOVLW 0x6C
	MOVWF gbl_MX_USBNAME+D'38', 1
	CLRF gbl_MX_USBNAME+D'39', 1
	MOVLW 0x2E
	MOVWF gbl_MX_USBMFR, 1
	MOVLW 0x03
	MOVWF gbl_MX_USBMFR+D'1', 1
	MOVLW 0x4D
	MOVWF gbl_MX_USBMFR+D'2', 1
	CLRF gbl_MX_USBMFR+D'3', 1
	MOVLW 0x61
	MOVWF gbl_MX_USBMFR+D'4', 1
	CLRF gbl_MX_USBMFR+D'5', 1
	MOVLW 0x74
	MOVWF gbl_MX_USBMFR+D'6', 1
	CLRF gbl_MX_USBMFR+D'7', 1
	MOVLW 0x72
	MOVWF gbl_MX_USBMFR+D'8', 1
	CLRF gbl_MX_USBMFR+D'9', 1
	MOVLW 0x69
	MOVWF gbl_MX_USBMFR+D'10', 1
	CLRF gbl_MX_USBMFR+D'11', 1
	MOVLW 0x78
	MOVWF gbl_MX_USBMFR+D'12', 1
	CLRF gbl_MX_USBMFR+D'13', 1
	MOVLW 0x20
	MOVWF gbl_MX_USBMFR+D'14', 1
	CLRF gbl_MX_USBMFR+D'15', 1
	MOVLW 0x4D
	MOVWF gbl_MX_USBMFR+D'16', 1
	CLRF gbl_MX_USBMFR+D'17', 1
	MOVLW 0x75
	MOVWF gbl_MX_USBMFR+D'18', 1
	CLRF gbl_MX_USBMFR+D'19', 1
	MOVLW 0x6C
	MOVWF gbl_MX_USBMFR+D'20', 1
	CLRF gbl_MX_USBMFR+D'21', 1
	MOVLW 0x74
	MOVWF gbl_MX_USBMFR+D'22', 1
	CLRF gbl_MX_USBMFR+D'23', 1
	MOVLW 0x69
	MOVWF gbl_MX_USBMFR+D'24', 1
	CLRF gbl_MX_USBMFR+D'25', 1
	MOVLW 0x6D
	MOVWF gbl_MX_USBMFR+D'26', 1
	CLRF gbl_MX_USBMFR+D'27', 1
	MOVLW 0x65
	MOVWF gbl_MX_USBMFR+D'28', 1
	CLRF gbl_MX_USBMFR+D'29', 1
	MOVLW 0x64
	MOVWF gbl_MX_USBMFR+D'30', 1
	CLRF gbl_MX_USBMFR+D'31', 1
	MOVLW 0x69
	MOVWF gbl_MX_USBMFR+D'32', 1
	CLRF gbl_MX_USBMFR+D'33', 1
	MOVLW 0x61
	MOVWF gbl_MX_USBMFR+D'34', 1
	CLRF gbl_MX_USBMFR+D'35', 1
	MOVLW 0x20
	MOVWF gbl_MX_USBMFR+D'36', 1
	CLRF gbl_MX_USBMFR+D'37', 1
	MOVLW 0x4C
	MOVWF gbl_MX_USBMFR+D'38', 1
	CLRF gbl_MX_USBMFR+D'39', 1
	MOVLW 0x74
	MOVWF gbl_MX_USBMFR+D'40', 1
	CLRF gbl_MX_USBMFR+D'41', 1
	MOVLW 0x64
	MOVWF gbl_MX_USBMFR+D'42', 1
	CLRF gbl_MX_USBMFR+D'43', 1
	MOVLW 0x2E
	MOVWF gbl_MX_USBMFR+D'44', 1
	CLRF gbl_MX_USBMFR+D'45', 1
	MOVLW 0x0A
	MOVLB 0x00
	MOVWF gbl_MX_SERIAL, 1
	MOVLW 0x03
	MOVWF gbl_MX_SERIAL+D'1', 1
	MOVLW 0x30
	MOVWF gbl_MX_SERIAL+D'2', 1
	CLRF gbl_MX_SERIAL+D'3', 1
	MOVLW 0x30
	MOVWF gbl_MX_SERIAL+D'4', 1
	CLRF gbl_MX_SERIAL+D'5', 1
	MOVLW 0x30
	MOVWF gbl_MX_SERIAL+D'6', 1
	CLRF gbl_MX_SERIAL+D'7', 1
	MOVLW 0x31
	MOVWF gbl_MX_SERIAL+D'8', 1
	CLRF gbl_MX_SERIAL+D'9', 1
	MOVLW HIGH(gbl_buffer_0_in+D'0')
	MOVLB 0x02
	MOVWF CompGblVar60, 1
	MOVLW LOW(gbl_buffer_0_in+D'0')
	MOVLB 0x01
	MOVWF gbl_ep_in_buffer_location, 1
	MOVLB 0x02
	MOVF CompGblVar60, W, 1
	MOVLB 0x01
	MOVWF gbl_ep_in_buffer_location+D'1', 1
	CLRF gbl_ep_in_buffer_location+D'2', 1
	CLRF gbl_ep_in_buffer_location+D'3', 1
	MOVLW HIGH(gbl_buffer_2_in+D'0')
	MOVLB 0x02
	MOVWF CompGblVar61, 1
	MOVLW LOW(gbl_buffer_2_in+D'0')
	MOVLB 0x01
	MOVWF gbl_ep_in_buffer_location+D'4', 1
	MOVLB 0x02
	MOVF CompGblVar61, W, 1
	MOVLB 0x01
	MOVWF gbl_ep_in_buffer_location+D'5', 1
	MOVLW HIGH(gbl_buffer_3_in+D'0')
	MOVLB 0x02
	MOVWF CompGblVar62, 1
	MOVLW LOW(gbl_buffer_3_in+D'0')
	MOVLB 0x01
	MOVWF gbl_ep_in_buffer_location+D'6', 1
	MOVLB 0x02
	MOVF CompGblVar62, W, 1
	MOVLB 0x01
	MOVWF gbl_ep_in_buffer_location+D'7', 1
	MOVLW HIGH(gbl_buffer_0_out+D'0')
	MOVLB 0x02
	MOVWF CompGblVar63, 1
	MOVLW LOW(gbl_buffer_0_out+D'0')
	MOVLB 0x01
	MOVWF gbl_ep_out_buffer_location, 1
	MOVLB 0x02
	MOVF CompGblVar63, W, 1
	MOVLB 0x01
	MOVWF gbl_ep_out_buffer_location+D'1', 1
	CLRF gbl_ep_out_buffer_location+D'2', 1
	CLRF gbl_ep_out_buffer_location+D'3', 1
	CLRF gbl_ep_out_buffer_location+D'4', 1
	CLRF gbl_ep_out_buffer_location+D'5', 1
	MOVLW HIGH(gbl_buffer_3_out+D'0')
	MOVLB 0x02
	MOVWF CompGblVar64, 1
	MOVLW LOW(gbl_buffer_3_out+D'0')
	MOVLB 0x01
	MOVWF gbl_ep_out_buffer_location+D'6', 1
	MOVLB 0x02
	MOVF CompGblVar64, W, 1
	MOVLB 0x01
	MOVWF gbl_ep_out_buffer_location+D'7', 1
	MOVLW 0x08
	MOVWF gbl_ep_in_buffer_size, 1
	CLRF gbl_ep_in_buffer_size+D'1', 1
	CLRF gbl_ep_in_buffer_size+D'2', 1
	CLRF gbl_ep_in_buffer_size+D'3', 1
	MOVLW 0x08
	MOVWF gbl_ep_in_buffer_size+D'4', 1
	CLRF gbl_ep_in_buffer_size+D'5', 1
	MOVLW 0x08
	MOVWF gbl_ep_in_buffer_size+D'6', 1
	CLRF gbl_ep_in_buffer_size+D'7', 1
	MOVLW 0x08
	MOVWF gbl_ep_out_buffer_size, 1
	CLRF gbl_ep_out_buffer_size+D'1', 1
	CLRF gbl_ep_out_buffer_size+D'2', 1
	CLRF gbl_ep_out_buffer_size+D'3', 1
	CLRF gbl_ep_out_buffer_size+D'4', 1
	CLRF gbl_ep_out_buffer_size+D'5', 1
	MOVLW 0x08
	MOVWF gbl_ep_out_buffer_size+D'6', 1
	CLRF gbl_ep_out_buffer_size+D'7', 1
	MOVLB 0x02
	CLRF gbl_usb_state, 1
	CLRF gbl_cdc_tx_start, 1
	CLRF gbl_cdc_tx_end, 1
	CLRF gbl_cdc_rx_start, 1
	CLRF gbl_cdc_rx_end, 1
	MOVLW 0x04
	MOVWF gbl_string_00, 1
	MOVLW 0x03
	MOVWF gbl_string_00+D'1', 1
	MOVLW 0x09
	MOVWF gbl_string_00+D'2', 1
	MOVLW 0x04
	MOVWF gbl_string_00+D'3', 1
	CLRF gbl_CDC_USB_status, 1
	GOTO	main
	ORG 0x00001222
interrupt
; { interrupt ; function begin
	MOVFF FSR0H,  Int1Context
	MOVFF FSR0L,  Int1Context+D'1'
	MOVFF PRODH,  Int1Context+D'2'
	MOVFF PRODL,  Int1Context+D'3'
	CALL usb_handle_0004C
	MOVFF Int1Context+D'3',  PRODL
	MOVFF Int1Context+D'2',  PRODH
	MOVFF Int1Context+D'1',  FSR0L
	MOVFF Int1Context,  FSR0H
	RETFIE 1
; } interrupt function end

	ORG 0x00300000
	DW 0x3238
	DW 0x1E1F
	DW 0x08FF
	DW 0xFF88
	DW 0xC003
	DW 0xE003
	DW 0x4003
	END
