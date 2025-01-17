package com.maxsella.fatmuscle.sdk.fat.manager;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.Constant;
import com.maxsella.fatmuscle.entity.ApiMemberInfo;
import com.maxsella.fatmuscle.entity.BodyParts;
import com.maxsella.fatmuscle.entity.ConfigParameter;
import com.maxsella.fatmuscle.entity.DeviceDefaultParams;
import com.maxsella.fatmuscle.sdk.fat.utils.BitmapUtil;
import com.maxsella.fatmuscle.sdk.fat.utils.SharedPreferencesUtil;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FatConfigManager {
    public static final int BOY = 1;
    public static final int DEFAULT_DEPTH_4CM = 40;
    public static final int DEFAULT_DEPTH_7CM = 70;
    public static final int DEFAULT_Z2_DEPTH_4CM = 37;
    public static final int DEFAULT_Z2_DEPTH_7CM = 56;
    public static final int DEPTH_OCXO_32_M = 32;
    public static final int FAT_MODE = 1;
    public static final int GIRL = 0;
    public static final int MUSCLE_MODE = 2;
    public static final int Z1_4CM_DEFAULT_DEPTH = 2;
    public static final int Z1_7CM_DEFAULT_DEPTH = 3;
    public static final int Z2_4CM_DEFAULT_DEPTH = 2;
    public static final int Z2_7CM_DEFAULT_DEPTH = 3;
    private static FatConfigManager mFatConfigManager;
    List<ConfigParameter> configParameterList;
    private int curBodyPositionIndex;
    private int curDeviceDepthLeve;
    private ApiMemberInfo curMeasureMember;
    private int defaultRssiValue;
    private int fangkeSex;
    private boolean isFangkeMode;
    private Context mContext;
    private int measureMode;
    private static Object obj = new Object();
    public static UNIT defaultUnit = UNIT.CM;
    private boolean isMeasure = false;
    private boolean isAutoMeasure = false;
    private boolean isReLoadData = true;

    public FatConfigManager() {
        this.curBodyPositionIndex = Constant.isProduct ? 6 : 0;
        this.configParameterList = null;
        this.curDeviceDepthLeve = 70;
        this.curMeasureMember = null;
        this.isFangkeMode = false;
        this.fangkeSex = 1;
        this.measureMode = Constant.isProduct ? 2 : 1;
        this.defaultRssiValue = -70;
    }

    public boolean isMeasure() {
        return this.isMeasure;
    }

    public void setMeasure(boolean z) {
        this.isMeasure = z;
    }

    public boolean isAutoMeasure() {
        return this.isAutoMeasure || !MxsellaDeviceManager.getInstance().isToBusinessVersion();
    }

    public void setAutoMeasure(boolean z) {
        this.isAutoMeasure = z;
    }

    public boolean isReLoadData() {
        return this.isReLoadData;
    }

    public void setReLoadData(boolean z) {
        this.isReLoadData = z;
    }

    public int getDefaultRssiValue() {
        return this.defaultRssiValue;
    }

    public void setDefaultRssiValue(int i) {
        this.defaultRssiValue = i;
    }

    /* loaded from: classes.dex */
    public enum UNIT {
        CM(0),
        IN(1),
        MM(2),
        FT(3),
        KG(4),
        LBS(5);

        int value;

        UNIT(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }
    }

    public static FatConfigManager getInstance() {
        if (mFatConfigManager == null) {
            synchronized (obj) {
                if (mFatConfigManager == null) {
                    mFatConfigManager = new FatConfigManager();
                }
            }
        }
        return mFatConfigManager;
    }

    public BodyParts getCurBodyParts() {
        return getCurBodyParts(this.curBodyPositionIndex);
    }

    public BodyParts getCurBodyParts(int i) {
        return getCurBodyParts(i, isBoy());
    }

    public BodyParts getCurBodyParts(int i, boolean z) {
        return getCurBodyParts(i, z, isFatMeasureMode());
    }

    public BodyParts getCurBodyParts(int i, boolean z, boolean z2) {
        BodyParts bodyParts = new BodyParts();
        bodyParts.setIndex(Integer.valueOf(i));
        updateStandard(bodyParts, z);
        /*
        int curDeviceCode = MarvotoDeviceManager.getInstance().getCurDeviceCode();

        if (i == 11) {
            bodyParts.setName(this.mContext.getString(C2077R.string.part_other_one));
            bodyParts.setMusclePartIcon(Integer.valueOf(C2077R.C2079drawable.other_guide));
            bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.other));
            bodyParts.setGuide(Integer.valueOf(C2077R.C2079drawable.other_guide));
            bodyParts.setGuideTip(Integer.valueOf(C2077R.string.other_guide_tip));
            bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.other_hui));
            bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.other_huang));
            bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_other);
            bodyParts.setTabIndex(5);
        } else if (i != 12) {
            switch (i) {
                case 0:
                    bodyParts.setName(this.mContext.getString(C2077R.string.yao_text));
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.yao_icon));
                    bodyParts.setGuide(Integer.valueOf(curDeviceCode == 0 ? C2077R.C2079drawable.yao_guide : C2077R.C2079drawable.z2_yao_guide));
                    bodyParts.setGuideVideo(Integer.valueOf(C2077R.raw.yaobu_guide));
                    bodyParts.setGuideTip(Integer.valueOf(C2077R.string.yao_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.yao_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.yao_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_yao);
                    bodyParts.setTabIndex(0);
                    bodyParts.setMusclePartIcon(Integer.valueOf(C2077R.C2079drawable.body_yaobu));
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.fuzhiji));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step1), Integer.valueOf(C2077R.string.fuzhiji_step1_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step2), Integer.valueOf(C2077R.string.fuzhiji_step2_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step3), Integer.valueOf(C2077R.string.fuzhiji_step3_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step4), Integer.valueOf(C2077R.string.fuzhiji_step4_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step4)});
                    break;
                case 1:
                    bodyParts.setName(this.mContext.getString(C2077R.string.lian_text));
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.lian_icon));
                    bodyParts.setGuide(Integer.valueOf(C2077R.C2079drawable.lianjia));
                    bodyParts.setGuideTip(Integer.valueOf(C2077R.string.lianjia_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.lian_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.lian_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_shou);
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.fuzhiji));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step1), Integer.valueOf(C2077R.string.fuzhiji_step1_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step2), Integer.valueOf(C2077R.string.fuzhiji_step2_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step3), Integer.valueOf(C2077R.string.fuzhiji_step3_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step4), Integer.valueOf(C2077R.string.fuzhiji_step4_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step4)});
                    break;
                case 2:
                    bodyParts.setName(this.mContext.getString(C2077R.string.shou_text));
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.shou_icon));
                    if (z2) {
                        bodyParts.setName(this.mContext.getString(C2077R.string.shou_text));
                        bodyParts.setGuide(Integer.valueOf(curDeviceCode == 0 ? C2077R.C2079drawable.shoubi_guide : C2077R.C2079drawable.z2_shoubi_guide));
                    } else {
                        bodyParts.setName(this.mContext.getString(C2077R.string.getj));
                        bodyParts.setGuide(Integer.valueOf(curDeviceCode == 0 ? C2077R.C2079drawable.getj_guide : C2077R.C2079drawable.z2_getj_guide));
                    }
                    bodyParts.setGuideVideo(Integer.valueOf(C2077R.raw.arm_guide));
                    bodyParts.setGuideTip(Integer.valueOf(C2077R.string.shangbi_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.shou_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.shou_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_shou);
                    bodyParts.setTabIndex(2);
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.getj));
                    bodyParts.setMusclePartIcon(Integer.valueOf(C2077R.C2079drawable.gongertouji_icon));
                    bodyParts.setGuideTitle(Integer.valueOf(C2077R.string.getj_title));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.getj_step1), Integer.valueOf(C2077R.string.getj_step1_tip), Integer.valueOf(C2077R.C2079drawable.gongertouji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.getj_step2), Integer.valueOf(C2077R.string.getj_step2_tip), Integer.valueOf(C2077R.C2079drawable.gongertouji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.getj_step3), Integer.valueOf(C2077R.string.getj_step3_tip), Integer.valueOf(C2077R.C2079drawable.gongertouji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.getj_step4), Integer.valueOf(C2077R.string.getj_step4_tip), Integer.valueOf(C2077R.C2079drawable.gongertouji_step4)});
                    break;
                case 3:
                    if (z2) {
                        bodyParts.setName(this.mContext.getString(C2077R.string.tui_text));
                    } else {
                        bodyParts.setName(this.mContext.getString(C2077R.string.guzhiji));
                    }
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.datui_icon));
                    bodyParts.setGuide(Integer.valueOf(curDeviceCode == 0 ? C2077R.C2079drawable.datui_guide : C2077R.C2079drawable.z2_datui_guide));
                    bodyParts.setGuideVideo(Integer.valueOf(C2077R.raw.datui_guide));
                    bodyParts.setGuideTip(Integer.valueOf(C2077R.string.yao_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.datui_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.datui_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_datui);
                    bodyParts.setTabIndex(3);
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.guzhiji));
                    bodyParts.setMusclePartIcon(Integer.valueOf(C2077R.C2079drawable.guzhiji_icon));
                    bodyParts.setGuideTitle(Integer.valueOf(C2077R.string.guzhiji_title));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.guzhiji_step1), Integer.valueOf(C2077R.string.guzhiji_step1_tip), Integer.valueOf(C2077R.C2079drawable.guzhiji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.guzhiji_step2), Integer.valueOf(C2077R.string.guzhiji_step2_tip), Integer.valueOf(C2077R.C2079drawable.guzhiji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.guzhiji_step3), Integer.valueOf(C2077R.string.guzhiji_step3_tip), Integer.valueOf(C2077R.C2079drawable.guzhiji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.guzhiji_step4), Integer.valueOf(C2077R.string.guzhiji_step4_tip), Integer.valueOf(C2077R.C2079drawable.guzhiji_step4)});
                    break;
                case 4:
                    bodyParts.setName(this.mContext.getString(C2077R.string.xiong_text));
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.xiong_icon));
                    isBoy();
                    bodyParts.setGuide(Integer.valueOf(C2077R.C2079drawable.xiong_women));
                    bodyParts.setGuideTip(Integer.valueOf(isBoy() ? C2077R.string.xiong_man_guide : C2077R.string.xiong_woman_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.xiong_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.xiong_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_tui);
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.fuzhiji));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step1), Integer.valueOf(C2077R.string.fuzhiji_step1_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step2), Integer.valueOf(C2077R.string.fuzhiji_step2_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step3), Integer.valueOf(C2077R.string.fuzhiji_step3_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step4), Integer.valueOf(C2077R.string.fuzhiji_step4_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step4)});
                    break;
                case 5:
                    if (z2) {
                        bodyParts.setName(this.mContext.getString(C2077R.string.xiaotui_text));
                    } else {
                        bodyParts.setName(this.mContext.getString(C2077R.string.feichangji));
                    }
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.xiaotui_icon));
                    bodyParts.setGuide(Integer.valueOf(curDeviceCode == 0 ? C2077R.C2079drawable.xiaotui_guide : C2077R.C2079drawable.z2_xiaotui_guide));
                    bodyParts.setGuideVideo(Integer.valueOf(C2077R.raw.xiaotui_guide));
                    bodyParts.setGuideTip(Integer.valueOf(C2077R.string.yao_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.xiaotui_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.xiaotui_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_tui);
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.feichangji));
                    bodyParts.setGuideTitle(Integer.valueOf(C2077R.string.feichangji_title));
                    bodyParts.setMusclePartIcon(Integer.valueOf(C2077R.C2079drawable.feichangji_icon));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.feichangji_step1), Integer.valueOf(C2077R.string.feichangji_step1_tip), Integer.valueOf(C2077R.C2079drawable.feichangji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.feichangji_step2), Integer.valueOf(C2077R.string.feichangji_step2_tip), Integer.valueOf(C2077R.C2079drawable.feichangji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.feichangji_step3), Integer.valueOf(C2077R.string.feichangji_step3_tip), Integer.valueOf(C2077R.C2079drawable.feichangji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.feichangji_step4), Integer.valueOf(C2077R.string.feichangji_step4_tip), Integer.valueOf(C2077R.C2079drawable.feichangji_step4)});
                    bodyParts.setTabIndex(4);
                    break;
                case 6:
                    if (z2) {
                        bodyParts.setName(this.mContext.getString(C2077R.string.fubu_text));
                    } else {
                        bodyParts.setName(this.mContext.getString(C2077R.string.fuzhiji));
                    }
                    bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.fubu_icon));
                    bodyParts.setGuide(Integer.valueOf(curDeviceCode == 0 ? C2077R.C2079drawable.fubu_guide : C2077R.C2079drawable.z2_fubu_guide));
                    bodyParts.setGuideVideo(Integer.valueOf(C2077R.raw.fb_guide));
                    bodyParts.setGuideTip(Integer.valueOf(C2077R.string.yao_guide));
                    bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.fubu_hui));
                    bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.fubu_huang));
                    bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_lian);
                    bodyParts.setTabIndex(1);
                    bodyParts.setMusclePartTip(Integer.valueOf(C2077R.string.fuzhiji));
                    bodyParts.setGuideTitle(Integer.valueOf(C2077R.string.fuzhiji_title));
                    bodyParts.setMusclePartIcon(Integer.valueOf(C2077R.C2079drawable.fuzhiji_icon));
                    bodyParts.setMuscleStep1Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step1), Integer.valueOf(C2077R.string.fuzhiji_step1_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step1)});
                    bodyParts.setMuscleStep2Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step2), Integer.valueOf(C2077R.string.fuzhiji_step2_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step2)});
                    bodyParts.setMuscleStep3Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step3), Integer.valueOf(C2077R.string.fuzhiji_step3_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step3)});
                    bodyParts.setMuscleStep4Guide(new Integer[]{Integer.valueOf(C2077R.string.fuzhiji_step4), Integer.valueOf(C2077R.string.fuzhiji_step4_tip), Integer.valueOf(C2077R.C2079drawable.fuzhiji_step4)});
                    break;
            }
        } else {
            bodyParts.setName(this.mContext.getString(C2077R.string.part_other_two));
            bodyParts.setIcon(this.mContext.getResources().getDrawable(C2077R.C2079drawable.other));
            bodyParts.setGuide(Integer.valueOf(C2077R.C2079drawable.other_guide));
            bodyParts.setGuideTip(Integer.valueOf(C2077R.string.other_guide_tip));
            bodyParts.setIconNormal(Integer.valueOf(C2077R.C2079drawable.other_hui));
            bodyParts.setIconSelect(Integer.valueOf(C2077R.C2079drawable.other_huang));
            bodyParts.setCurveMarkeIcon(C2077R.C2079drawable.app_main_curve_other);
            bodyParts.setTabIndex(6);
        }
        */
        return bodyParts;
    }

    public void init(Context context) {
        this.mContext = context;
//        int i = SharedPreferencesUtil.getInt(context, Constant.CUR_TEST_POSITION, this.curBodyPositionIndex);
//        this.curBodyPositionIndex = i;
//        if (i == 4 || i == 1) {
//            setCurBodyPositionIndex(0);
//        }
//        if (MxsellaDeviceManager.getInstance().getCurDeviceCode() == 0) {
//            this.curDeviceDepthLeve = SharedPreferencesUtil.getInt(this.mContext, "cur_depth_key_" + this.curBodyPositionIndex, this.curBodyPositionIndex == 2 ? 40 : 70);
//        } else {
//            this.curDeviceDepthLeve = SharedPreferencesUtil.getInt(this.mContext, "cur_depth_key_" + this.curBodyPositionIndex, this.curBodyPositionIndex == 2 ? 37 : 56);
//        }
//        try {
//            this.curMeasureMember = (ApiMemberInfo) Config.getObject(this.mContext, Constant.MEMBER_PERSONAL_INFO, ApiMemberInfo.class);
//        } catch (ClassCastException unused) {
//            setCurMeasureMember(null);
//        }
//        this.isAutoMeasure = Config.getBoolean(this.mContext, Constant.AUTO_MEASURE_MODE, false);
//        this.isFangkeMode = Config.getBoolean(this.mContext, Constant.FANGKE_MODE, false);
//        this.fangkeSex = Config.getInt(this.mContext, Constant.FANGKE_SEX, 1);
//        this.measureMode = Config.getInt(this.mContext, Constant.MEASURE_MODE, this.measureMode);
//        this.defaultRssiValue = Config.getInt(this.mContext, Constant.RSSI_FILTER, this.defaultRssiValue);
        initConfigParameter();
    }
/*

    public void setCurBodyPositionIndex(int i) {
        SharedPreferencesUtil.saveInt(this.mContext, Constant.CUR_TEST_POSITION, i);
        this.curBodyPositionIndex = i;
        DeviceDefaultParams depthAndGainParamByBodyPosition = getDepthAndGainParamByBodyPosition(i);
        if (depthAndGainParamByBodyPosition.getLeve().intValue() != this.curDeviceDepthLeve) {
            setCurDeviceDepthLeve(depthAndGainParamByBodyPosition.getLeve().intValue());
        }
    }
*/

    public int getCurBodyPositionIndex() {
        return this.curBodyPositionIndex;
    }

    public void setCurDeviceDepthLeve(int i) {
        SharedPreferencesUtil.saveInt(this.mContext, "cur_depth_key_" + this.curBodyPositionIndex, i);
        this.curDeviceDepthLeve = i;
        MxsellaDeviceManager.getInstance().setDeviceDefaultParams(i, null);
    }

    public int getCurDeviceDepthLeve() {
        return this.curDeviceDepthLeve;
    }

    public int getCurDeviceDepth() {
        return getInstance().getDepthAndGainParamByLeve(this.curDeviceDepthLeve).getDepth().intValue();
    }

    public ApiMemberInfo getCurMeasureMember() {
        return this.curMeasureMember;
    }

    public Integer getCurMeasureMemberId() {
        ApiMemberInfo apiMemberInfo = this.curMeasureMember;
        if (apiMemberInfo == null) {
            return null;
        }
        return apiMemberInfo.getId();
    }

    public void setCurMeasureMember(ApiMemberInfo apiMemberInfo) {
        this.curMeasureMember = apiMemberInfo;

    }

    public boolean isFangkeMode() {
        return this.isFangkeMode;
    }

    public void setFangkeMode(boolean z) {
        if (z) {
            setCurMeasureMember(null);
        }
        this.isFangkeMode = z;

    }

    public void setFangkeSex(int i) {
        this.fangkeSex = i;

    }

    public void setMeasureMode(int i) {
        this.measureMode = i;

    }

    public boolean isFatMeasureMode() {
        return this.measureMode == 1;
    }

    public boolean isBoy() {
        if (isFangkeMode()) {
            return this.fangkeSex == 1;
        }
        ApiMemberInfo apiMemberInfo = this.curMeasureMember;

        return apiMemberInfo.getSex() == null || this.curMeasureMember.getSex().intValue() == 1;
    }

    private void initConfigParameter() {
        XmlResourceParser xml = this.mContext.getResources().getXml(R.xml.params_value);
        try {
            ConfigParameter configParameter = null;
            ArrayList arrayList = null;
            DeviceDefaultParams deviceDefaultParams = null;
            ArrayList arrayList2 = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType != 2) {
                    if (eventType != 3) {
                        continue;
                    } else if ("item".equalsIgnoreCase(xml.getName())) {
                        configParameter.setDeviceDefaultParamsList(arrayList);
                        this.configParameterList.add(configParameter);
                    } else if ("leve".equalsIgnoreCase(xml.getName())) {
                        arrayList.add(deviceDefaultParams);
                    } else if ("gain-leve".equalsIgnoreCase(xml.getName())) {
                        deviceDefaultParams.setGainArray(arrayList2);
                    }
                } else if ("params".equalsIgnoreCase(xml.getName())) {
                    this.configParameterList = new ArrayList();
                } else if ("item".equalsIgnoreCase(xml.getName())) {
                    configParameter = new ConfigParameter();
                } else if ("data-length".equalsIgnoreCase(xml.getName())) {
                    configParameter.setDataLength(Integer.parseInt(xml.nextText()));
                } else if ("depth-leve".equalsIgnoreCase(xml.getName())) {
                    arrayList = new ArrayList();
                } else if ("leve".equalsIgnoreCase(xml.getName())) {
                    deviceDefaultParams = new DeviceDefaultParams();
                    deviceDefaultParams.setLeve(Integer.valueOf(Integer.parseInt(xml.getAttributeValue(null, "value"))));
                    String attributeValue = xml.getAttributeValue(null, "defaultbodyposition");
                    if (attributeValue != null) {
                        String[] split = attributeValue.split(",");
                        if (split.length > 0) {
                            if (!StringUtils.isEmpty(split[0])) {
                                int[] iArr = new int[split.length];
                                for (int i = 0; i < split.length; i++) {
                                    iArr[i] = Integer.parseInt(split[i]);
                                }
                                deviceDefaultParams.setDefaultBodyPositionArray(iArr);
                            }
                        }
                    }
                } else if ("depth".equalsIgnoreCase(xml.getName())) {
                    deviceDefaultParams.setDepth(Integer.valueOf(Integer.parseInt(xml.nextText())));
                } else if ("gain".equalsIgnoreCase(xml.getName())) {
                    deviceDefaultParams.setGain(Integer.valueOf(Integer.parseInt(xml.nextText())));
                } else if ("gain-leve".equalsIgnoreCase(xml.getName())) {
                    arrayList2 = new ArrayList();
                } else if ("gain-item".equalsIgnoreCase(xml.getName())) {
                    arrayList2.add(Integer.valueOf(Integer.parseInt(xml.nextText())));
                } else if ("sendcycle".equalsIgnoreCase(xml.getName())) {
                    deviceDefaultParams.setSendcycle(Integer.valueOf(Integer.parseInt(xml.nextText())));
                } else if ("dynamic".equalsIgnoreCase(xml.getName())) {
                    deviceDefaultParams.setDynamic(Integer.valueOf(Integer.parseInt(xml.nextText())));
                }
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void updateStandard(BodyParts bodyParts, boolean z) {
        /*
        if (bodyParts.getIndex().intValue() == 2) {
            if (!z) {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_arm_female_leve));
            } else {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_arm_male_leve));
            }
        } else if (bodyParts.getIndex().intValue() == 0) {
            if (!z) {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_waist_female_leve));
            } else {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_waist_male_leve));
            }
        } else if (bodyParts.getIndex().intValue() == 6) {
            if (!z) {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_fubu_female_leve));
            } else {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_fubu_male_leve));
            }
        } else if (bodyParts.getIndex().intValue() == 5) {
            if (!z) {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_xiaotui_female_leve));
            } else {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_xiaotui_male_leve));
            }
        } else if (bodyParts.getIndex().intValue() == 3) {
            if (!z) {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_thigh_female_leve));
            } else {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_thigh_male_leve));
            }
        } else if (bodyParts.getIndex().intValue() == 11 || bodyParts.getIndex().intValue() == 12) {
            if (!z) {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_thigh_female_leve));
            } else {
                bodyParts.setLeveArray(this.mContext.getResources().getIntArray(C2077R.array.part_thigh_male_leve));
            }
        }*/
        bodyParts.setMaxNormalValue(35);
        bodyParts.setMaxThicknessValue(55);
        bodyParts.setMaxThinValue(6);
    }

    public ConfigParameter getConfigParameter(int i) {
        for (ConfigParameter configParameter : this.configParameterList) {
            if (configParameter.getDataLength() == i) {
                return configParameter;
            }
        }
        return null;
    }

    public DeviceDefaultParams getDepthAndGainParamByLeve(int i) {
        ConfigParameter configParameter = getConfigParameter();
        if (configParameter == null) {
            return null;
        }
        for (DeviceDefaultParams deviceDefaultParams : configParameter.getDeviceDefaultParamsList()) {
            if (deviceDefaultParams.getLeve().intValue() == i) {
                return deviceDefaultParams;
            }
        }
        return configParameter.getDeviceDefaultParamsList().get(0);
    }

    public DeviceDefaultParams getDepthAndGainParamByLeve() {
        return getDepthAndGainParamByLeve(getCurDeviceDepthLeve());
    }

    public DeviceDefaultParams getDepthAndGainParamByBodyPosition(int i) {
        for (DeviceDefaultParams deviceDefaultParams : getConfigParameter().getDeviceDefaultParamsList()) {
            if (deviceDefaultParams.getDefaultBodyPositionArray() != null) {
                for (int i2 : deviceDefaultParams.getDefaultBodyPositionArray()) {
                    if (i2 == i) {
                        return deviceDefaultParams;
                    }
                }
                continue;
            }
        }
        return getDepthAndGainParamByLeve(this.curDeviceDepthLeve);
    }

    public ConfigParameter getConfigParameter() {
        return getConfigParameter(BitmapUtil.sBitmapHight);
    }

    public float getAlgoDepth(int i) {
        return getAlgoDepth(i, getCurDeviceDepth());
    }

    public float getAlgoDepth() {
        return getAlgoDepth(MxsellaDeviceManager.getInstance().getOcxo(), getCurDeviceDepth());
    }

    public float getAlgoDepth(int i, int i2) {
        return getAlgoDepth(i, i2, BitmapUtil.sBitmapHight);
    }

    public float getAlgoDepth(int i, int i2, int i3) {
        float f;
        float f2 = i2;
        float f3 = i;
        float f4 = i3;
        float f5 = ((((2.0f * f2) * 77.0f) / f3) / 100.0f) * f4;
        int blueSpeedRateLeve = MxsellaDeviceManager.getInstance().getBlueSpeedRateLeve();
        if (blueSpeedRateLeve == 2) {
            f = 4.0f;
        } else if (blueSpeedRateLeve != 3) {
            return f5;
        } else {
            f = 8.0f;
        }
        return ((((f2 * f) * 77.0f) / f3) / 100.0f) * f4;
    }

}
