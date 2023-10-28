package com.maxsella.fatmuscle.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import com.azhon.appupdate.manager.DownloadManager;
import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.FragmentProfileBinding;
import com.maxsella.fatmuscle.model.UpdateInfo;
import com.maxsella.fatmuscle.common.base.BaseFragment;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.ui.activity.FeedBackActivity;
import com.maxsella.fatmuscle.ui.activity.InstructionActivity;
import com.maxsella.fatmuscle.ui.activity.SettingsActivity;
import com.maxsella.fatmuscle.ui.activity.UseProblemActivity;
import com.maxsella.fatmuscle.ui.activity.UserInfoActivity;
import com.maxsella.fatmuscle.ui.activity.UserManagerActivity;
import com.maxsella.fatmuscle.viewmodel.ProfileViewModel;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

public class ProfileFragment extends BaseFragment {
    private static final Object lock = new Object();
    private ProfileViewModel mViewModel;
    private static ProfileFragment instance;
    public FragmentProfileBinding profileBinding;
    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
    private final String apkName = "测试.apk";
    private final String apkSize = "88.88MB";

    public static ProfileFragment newInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ProfileFragment();
                }
            }
        }
        return instance;
    }

    public FragmentProfileBinding getProfileBinding() {
        return (FragmentProfileBinding) binding;
    }

    @Override
    public void initEventAndData() {
        profileBinding = getProfileBinding();
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        userInfoViewModel.setLifecycleOwner(this);
        userInfoViewModel.user.observe(this, user -> {
            profileBinding.setUserViewmodel(userInfoViewModel);
        });
        LogUtil.d("这是一条测试");
        initClick();
    }

    private void initClick() {
        profileBinding.lltUserInfo.setOnClickListener(v -> {
            navigateToActivity(requireActivity(), UserInfoActivity.class);
        });
        profileBinding.lltManager.setOnClickListener(v -> {
            navigateToActivity(requireActivity(), UserManagerActivity.class);
        });
        profileBinding.lltInstruction.setOnClickListener(v -> {
            navigateToActivity(requireActivity(), InstructionActivity.class);
        });
        profileBinding.lltProblem.setOnClickListener(v -> {
            navigateToActivity(requireActivity(), UseProblemActivity.class);
        });
        profileBinding.lltFeedback.setOnClickListener(v -> {
            navigateToActivity(requireActivity(), FeedBackActivity.class);
        });
        profileBinding.lltSettings.setOnClickListener(v -> {
            navigateToActivity(requireActivity(), SettingsActivity.class);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    public void showUpdateDialog(UpdateInfo updateInfo) {
        LogUtil.d("升级弹框执行中");
        DownloadManager manager = new DownloadManager.Builder(requireActivity()).apkUrl(updateInfo.getDownLoadUrl()).apkName(apkName).smallIcon(R.mipmap.ic_launcher).apkVersionCode(2).apkVersionName(updateInfo.getVersionName()).apkSize(apkSize).showNotification(true).showBgdToast(true).showNewerToast(true).apkDescription(updateInfo.getLog()).dialogProgressBarColor(R.color.arc23).forcedUpgrade(false).build();
        manager.download();
    }
}