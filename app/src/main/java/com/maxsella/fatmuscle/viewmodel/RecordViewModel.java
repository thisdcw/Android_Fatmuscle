package com.maxsella.fatmuscle.viewmodel;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maxsella.fatmuscle.BR;
import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.common.util.RandomUtil;
import com.maxsella.fatmuscle.db.bean.Record;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.repository.RecordRepository;

import java.util.List;

public class RecordViewModel extends BaseViewModel {

    private RecordRepository recordRepository = new RecordRepository();

    private MutableLiveData<List<Record>> _records;

    private MutableLiveData<Record> _lastRecord = new MutableLiveData<>();

    public LiveData<Record> lastRecord = get_lastRecord();

    public LiveData<List<Record>> records = getRecordsData();

    public MutableLiveData<Record> get_lastRecord() {
        return _lastRecord;
    }

    private LiveData<List<Record>> getRecordsData() {
        if (_records == null) {
            _records = getLiveData("records");
        }
        return _records;
    }

    /**
     * 获取最新数据
     *
     * @param nickname
     * @param item
     * @return
     */
    public void getNewRecord(String nickname, String item) {
        LogUtil.d("nickname: " + nickname + ",item: " + item);
        _lastRecord.setValue(recordRepository.getLatestRecordForCurrentUser(nickname, item));
    }

    public void getAllRecordByItem(String nickname, String item) {
        _records.setValue(recordRepository.getAllRecordByItem(nickname, item));
    }

    public void getAllRecordByModeItem(String nickname, String item, String mode) {
        _records.postValue(recordRepository.getAllRecordByItem(nickname, item, mode));
    }

    /**
     * 模拟添加测试记录
     *
     * @param item
     * @param mode
     */
    public void imitateAddRecord(String item, String mode) {
        if (Config.isDebug) {
            Record record = new Record();
            record.setDate(RandomUtil.imitateDate());
            record.setItem(item);
            record.setMode(mode);
            record.setThickness(RandomUtil.imitateData());
            record.setNickname(Config.getChooseMember());
            recordRepository.addRecord(record);
        }
    }

    /**
     * 模拟删除所有记录
     */
    public void imitateDeleteAllRecord() {
        if (Config.isDebug) {

            recordRepository.deleteAllRecord();

        }
    }

    /**
     * 模拟删除数据
     *
     * @param id
     */
    public void imitateDeleteRecord(int id) {
        if (Config.isDebug) {
            recordRepository.deleteById(id);
        }
    }


}
