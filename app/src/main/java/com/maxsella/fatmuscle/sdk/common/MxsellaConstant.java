package com.maxsella.fatmuscle.sdk.common;

import android.os.Environment;

import com.maxsella.fatmuscle.sdk.VolAlg;

public class MxsellaConstant {
    public static final int DEVICE_DLPF_M_VALUE = 4288;

    public static final int DEVICE_RXATE_DELAY = 4301;

    public static final int DEVICE_DLPF_PARA = 4289;

    public static final int DEVICE_LINE_CYCLE = 4293;

    public static final int DEVICE_DR_PARA = 4291;

    public static final String ACCESS_TOKEN = "access_token";
    public static final int DEVICE_CLEAN_FTP_FILEDIR = 24737;
    public static final int DEVICE_CLEAR_WLAN_MSG_ID = 165;
    public static final int DEVICE_CONFIG_MSG_ID = 4280;
    public static final int DEVICE_CONNECTED_MSG_ID = 0;
    public static final int DEVICE_DISCONNECTED_MSG_ID = -1;
    public static final int DEVICE_FACTORY_INFO_MSG_ID = 4263;
    public static final String DEVICE_IP = "10.0.0.1";
    public static final int DEVICE_MAC_INFO_MSG_ID = 4257;
    public static final int DEVICE_REAL_B = 65278;
    public static final int DEVICE_REAL_RF = 65021;
    public static final int DEVICE_REBOOT_MSG_ID = 167;
    public static final int DEVICE_SETUP_WLAN_MSG_ID = 163;
    public static final int DEVICE_SHUTDOWN_MSG_ID = 169;
    public static final int DEVICE_START_UPDATE_MSG_ID = 24739;
    public static final int DEVICE_SWICH_CHANNEL_MSG_ID = 168;
    public static final int DEVICE_SYNC_TIME_MSG_ID = 161;
    public static final String DEVICE_UDP_CONNECTED_MSG_ID = "CONNECTED";
    public static final String DEVICE_UDP_DISCONNECTED_MSG_ID = "DISCONNECTED";
    public static final String DEVICE_UDP_ONLINE_BROADCAST_MSG_ID = "device_online_status_broadcast";
    public static final int DEVICE_UPDATE_STATUS = 24741;
    public static final String IMAGE_STATUS = "param_status";
    public static final String IMAGE_TYPE = "shooting_mode";
    public static final String LOGIN_TIME = "login_time";
    public static final int QUERY_DEVICE_STATUS_MSG_ID = 4261;
    public static final int QUERY_DEVICE_VER_MSG_ID = 4276;
    public static final int TCP_IMAGE_PORT = 8888;
    public static final int TCP_PORT = 5002;
    public static final int UDP_PORT = 5001;
    public static final int UDP_STATUS_MSG_ID = 1;
    public static final String UITRAL_DEC_VALUE = "dec";
    public static final String UITRAL_INC_VALUE = "inc";
    public static final int ULTRA_DEHP_MSG_ID = 28841;
    public static final int ULTRA_DEPTH_MSG_ID = 28858;
    public static final int ULTRA_DRANGE_MSG_ID = 28859;
    public static final int ULTRA_EDAGE_ENHANCE_MSG_ID = 28867;
    public static final int ULTRA_EXIT_BRIGHTNESS_MSG_ID = 28854;
    public static final int ULTRA_EXIT_CONTRAST_MSG_ID = 28855;
    public static final int ULTRA_EXIT_SLEEP_MSG_ID = 28853;
    public static final int ULTRA_FILE_TCP_PORT = 6768;
    public static final int ULTRA_FOCUSPOS_MSG_ID = 28866;
    public static final int ULTRA_FREEZE_MSG_ID = 28838;
    public static final int ULTRA_GAIN_MSG_ID = 28857;
    public static final int ULTRA_GRAY_MSG_ID = 28860;
    public static final int ULTRA_PERSIST_MSG_ID = 28865;
    public static final int ULTRA_REBOOT_DEVICE_MSG_ID = 167;
    public static final int ULTRA_REHECTION_MSG_ID = 28862;
    public static final int ULTRA_SLEEP_MSG_ID = 28852;
    public static final int ULTRA_SMOOTH_MSG_ID = 28864;
    public static final int ULTRA_SRI_MSG_ID = 28863;
    public static final int ULTRA_SWICH_CHANNEL_MSG_ID = 28856;
    public static final int ULTRA_SWITCH_3D_MSG_ID = 28842;
    public static final int ULTRA_SWITCH_4D_MSG_ID = 28843;
    public static final int ULTRA_SYSNTHETIC_MSG_ID = 28861;
    public static final int ULTRA_SYSTEM_BUSY_MSG_ID = 17476;
    public static final int ULTRA_TCP_PORT = 6767;
    public static final int ULTRA_TINT_MSG_ID = 28868;
    public static final int ULTRA_TO_B_MSG_ID = 28850;
    public static final int ULTRA_VOI_DOWN_MSG_ID = 28845;
    public static final int ULTRA_VOI_LEFT_MSG_ID = 28846;
    public static final int ULTRA_VOI_RIGHT_MSG_ID = 28847;
    public static final int ULTRA_VOI_UP_MSG_ID = 28844;
    public static final int ULTRA_VOI_VALUE_MSG_ID = 28851;
    public static final int ULTRA_VOI_ZOOM_IN_MSG_ID = 28848;
    public static final int ULTRA_VOI_ZOOM_OUT_MSG_ID = 28849;
    public static final int ULTRA_VOLUME_3D_MSG_ID = 28839;
    public static final int ULTRA_VOLUME_4D_MSG_ID = 28840;
    public static String configFilePath = "sdcard/Android/usl/";
    public static final String MARVOTO_PATH = Environment.getExternalStorageDirectory() + "/DCIM/Marvoto";
    public static final String APP_SECRET_KEY = VolAlg.VolGetCode();

}
