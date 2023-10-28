package com.maxsella.fatmuscle.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseFragment;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.FragmentSurveyBinding;
import com.maxsella.fatmuscle.databinding.SelectSlideTemplateBinding;
import com.maxsella.fatmuscle.db.bean.Member;
import com.maxsella.fatmuscle.ui.activity.DeviceInfoActivity;
import com.maxsella.fatmuscle.ui.activity.WorkActivity;
import com.maxsella.fatmuscle.ui.adapter.RecordAdapter;
import com.maxsella.fatmuscle.ui.adapter.SelectAdapter;
import com.maxsella.fatmuscle.ui.dialog.SelectItemDialog;
import com.maxsella.fatmuscle.ui.dialog.SelectUserDialog;
import com.maxsella.fatmuscle.view.EasyPickerView;
import com.maxsella.fatmuscle.view.dialog.SlideDialog;
import com.maxsella.fatmuscle.viewmodel.MemberViewModel;
import com.maxsella.fatmuscle.viewmodel.RecordViewModel;
import com.maxsella.fatmuscle.viewmodel.SurveyViewModel;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurveyFragment extends BaseFragment {

    private String TAG = "SurveyFragment";

    private SurveyViewModel mViewModel = new SurveyViewModel();

    private FragmentSurveyBinding surveyBinding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
    private SelectUserDialog selectUserDialog;
    private SelectItemDialog selectItemDialog;

    private SelectAdapter selectAdapter = new SelectAdapter();

    private MemberViewModel memberViewModel = new MemberViewModel();

    private List<Member> memberList = new ArrayList<>();

    private RecordViewModel recordViewModel = new RecordViewModel();

    public static SurveyFragment newInstance() {
        return new SurveyFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_survey;
    }

    @Override
    public void initEventAndData() {
        surveyBinding = (FragmentSurveyBinding) binding;
        mViewModel = new ViewModelProvider(this).get(SurveyViewModel.class);
        userInfoViewModel.setLifecycleOwner(this);
        userInfoViewModel.user.observe(this, user -> {
            surveyBinding.setUserViewModel(userInfoViewModel);
        });
        memberViewModel.members.observe(this, members -> {
            memberList = members;
            LogUtil.d("members[0]: ");
        });

        LogUtil.d("这是一条测试");
        initDialog();
        surveyBinding.selectUserBtn.setOnClickListener(v -> {
//            selectUserDialog.show();
            show();
            LogUtil.d("onClick: 点击了selectUserBtn");
        });
        surveyBinding.currentMode.setOnClickListener(v -> {
            selectItemDialog.show();
            surveyBinding.currentMode.setText(Config.getSelectMode());
            LogUtil.d("onClick: 点击了selectItemBtn");
        });
        surveyBinding.deviceInfo.setOnClickListener(v -> {
            navigateToActivity(getActivity(), DeviceInfoActivity.class);
        });
        surveyBinding.toWork.setOnClickListener(v -> {
            navigateToActivity(getActivity(), WorkActivity.class);
        });
        surveyBinding.deleteAllRecords.setOnClickListener(v -> {
            LogUtil.d("模拟删除所有数据");
            recordViewModel.imitateDeleteAllRecord();
        });
    }

    private void initDialog() {
        selectItemDialog = new SelectItemDialog(requireContext(), R.layout.dialog_select_item);
    }

    private void show() {
        List<String> lists = new ArrayList<>();
        lists.add(userInfoViewModel.user.getValue().getNickname());
        if (memberList.size() > 0) {
            for (int i = 0; i < memberList.size(); i++) {
                lists.add(memberList.get(i).getNickname());
            }
        }
        SlideDialog slideDialog = new SlideDialog(requireContext(), lists, false, false);
        slideDialog.setOnSelectClickListener(new SlideDialog.OnSelectListener() {
            @Override
            public void onCancel() {
                showMsg("未选择");
            }

            @Override
            public void onAgree(String txt) {
                LogUtil.d("选择的是: " + txt);
                Config.saveOrUpdateChooseMember(txt);
                showMsg("当前选择的是: " + Config.getChooseMember());
            }
        });
        slideDialog.show();

    }
}