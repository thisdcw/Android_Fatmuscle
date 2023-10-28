package com.maxsella.fatmuscle.ui.fragment.item;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseFragment;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.Constant;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.FragmentAbdomenBinding;
import com.maxsella.fatmuscle.ui.activity.RecordActivity;
import com.maxsella.fatmuscle.ui.activity.ReportActivity;
import com.maxsella.fatmuscle.viewmodel.AbdomenViewModel;
import com.maxsella.fatmuscle.viewmodel.RecordViewModel;

import java.util.ArrayList;

public class AbdomenFragment extends BaseFragment {

    private AbdomenViewModel mViewModel;

    private FragmentAbdomenBinding abdomenBinding;

    public static AbdomenFragment newInstance() {
        return new AbdomenFragment();
    }

    private LineChart lineChart;

    private RecordViewModel recordViewModel = new RecordViewModel();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_abdomen;
    }

    @Override
    public void initEventAndData() {
        mViewModel = new ViewModelProvider(this).get(AbdomenViewModel.class);
        abdomenBinding = (FragmentAbdomenBinding) binding;
        lineChart = abdomenBinding.lineChart;
        recordViewModel.getNewRecord(Config.getChooseMember(), Constant.ABDOMEN);
        recordViewModel.lastRecord.observe(this, record -> {
            if (record != null) {
                LogUtil.d(record.toString());
            }
            abdomenBinding.setViewModel(recordViewModel);
        });
        initLineChart();
        initClick();
    }

    private void initClick() {
        abdomenBinding.lltRecord.setOnClickListener(v -> {
            String nickname = Config.getChooseMember();
            recordViewModel.getAllRecordByItem(nickname, Constant.ABDOMEN);
            Bundle bundle = new Bundle();
            bundle.putString("item", Constant.ABDOMEN);
            //跳转历史记录页面
            navigateToActivity(requireActivity(), RecordActivity.class, bundle);
        });
        abdomenBinding.btnReport.setOnClickListener(v -> {
            //生成报告
            navigateToActivity(requireActivity(), ReportActivity.class);
        });
        abdomenBinding.btnWeek.setOnClickListener(v -> {
            LogUtil.d("选择周分类");
        });
        abdomenBinding.btnMonth.setOnClickListener(v -> {
            LogUtil.d("选择月分类");
        });
        abdomenBinding.btnYear.setOnClickListener(v -> {
            LogUtil.d("选择年分类");
        });
    }


    public void initLineChart() {
        // 创建一个数据集
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1, 2.3F));
        entries.add(new Entry(2, 3.5F));
        entries.add(new Entry(3, 1.8F));
        entries.add(new Entry(4, 2.4F));

        // 获取 X 轴
        XAxis xAxis = lineChart.getXAxis();

        // 设置 X 轴的间隔大小
        xAxis.setGranularity(1f); // 设置间隔大小为1单位
        // 创建一个数据集的标签
        LineDataSet dataSet = new LineDataSet(entries, "腹部");

        // 创建一个数据集的集合
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        // 创建一个折线数据
        LineData lineData = new LineData(dataSets);

        // 设置 LineChart 数据
        lineChart.setData(lineData);

        // 隐藏描述
        lineChart.getDescription().setEnabled(false);

        // 禁用缩放和拖动
        lineChart.setPinchZoom(false);
        lineChart.setDragEnabled(false);

        // 隐藏 X 轴和 Y 轴的网格线
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);

        // 隐藏右边的 Y 轴
        lineChart.getAxisRight().setEnabled(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 将 X 轴标签放在底部位置

        // 更新图表
        lineChart.invalidate();
    }
}