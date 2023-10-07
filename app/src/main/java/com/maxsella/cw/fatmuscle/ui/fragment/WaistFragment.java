package com.maxsella.cw.fatmuscle.ui.fragment;

import androidx.databinding.DataBindingUtil;
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
import com.maxsella.cw.fatmuscle.R;
import com.maxsella.cw.fatmuscle.databinding.FragmentWaistBinding;
import com.maxsella.cw.fatmuscle.viewmodel.WaistViewModel;

import java.util.ArrayList;

public class WaistFragment extends Fragment {

    private WaistViewModel mViewModel;
    private FragmentWaistBinding waistBinding;
    private LineChart lineChart;

    public static WaistFragment newInstance() {
        return new WaistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        waistBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_waist, container, false);
        return waistBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WaistViewModel.class);
        lineChart = waistBinding.lineChart;
        initLineChart();
    }

    public void initLineChart() {
        // 创建一个数据集
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1, 0.3F));
        entries.add(new Entry(2, 0.5F));
        entries.add(new Entry(3, 0.8F));
        entries.add(new Entry(4, 0.4F));

        // 获取 X 轴
        XAxis xAxis = lineChart.getXAxis();

        // 设置 X 轴的间隔大小
        xAxis.setGranularity(1f); // 设置间隔大小为1单位
        // 创建一个数据集的标签
        LineDataSet dataSet = new LineDataSet(entries, "My Data");

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