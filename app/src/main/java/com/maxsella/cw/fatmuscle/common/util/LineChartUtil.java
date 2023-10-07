package com.maxsella.cw.fatmuscle.common.util;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChartUtil {


    private LineChart lineChart;
    public void initLineChart() {
        // 创建一个数据集
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 10));
        entries.add(new Entry(2, 20));
        entries.add(new Entry(3, 15));
        entries.add(new Entry(4, 30));

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

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 将 X 轴标签放在底部位置

        // 更新图表
        lineChart.invalidate();
    }
}
