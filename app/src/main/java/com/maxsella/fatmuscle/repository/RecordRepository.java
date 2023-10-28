package com.maxsella.fatmuscle.repository;

import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.Record;
import com.maxsella.fatmuscle.db.helper.RecordHelper;
import com.maxsella.fatmuscle.db.helper.UserHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository {

    private final RecordHelper helper = RecordHelper.getInstance();
    private final UserHelper userHelper = UserHelper.getInstance();


    public void deleteAllRecord(){
        helper.deleteAllRecord();
    }
    /**
     * 通过昵称类别筛选最新的记录
     *
     * @param nickname
     * @param item
     * @return
     */
    public Record getLatestRecordForCurrentUser(String nickname, String item) {
        Record record;
        record = helper.getLatestRecordForCurrentUser(nickname, item);
        return record;
    }

    /**
     * 通过昵称,测量部位和类别筛选
     *
     * @param nickname
     * @param item
     * @return
     */
    public List<Record> getAllRecordByItem(String nickname, String item, String mode) {
        List<Record> recordList;
        recordList = helper.getRecordByItem(nickname, item, mode);
        LogUtil.d("size: "+recordList.size());
        return recordList;
    }

    /**
     * 通过昵称和测量部位筛选
     *
     * @param nickname
     * @param item
     * @return
     */
    public List<Record> getAllRecordByItem(String nickname, String item) {

        List<Record> recordList;
        recordList = helper.getRecordByItem(nickname, item);
        return recordList;
    }

    /**
     * 保存或更新记录
     *
     * @param record
     */
    public void saveRecord(Record record) {
        helper.saveOrUpdate(record);
    }

    /**
     * 添加新记录
     *
     * @param record
     */
    public void addRecord(Record record) {
        Record record1 = helper.getLastRecordForFamily(userHelper.getLoginUser().getUserId());
        if (record1 == null) {
            record.setId(0);
        } else {
            LogUtil.d("上一个Record的id: "+record1.getId());
            record.setId(record1.getId() + 1);
        }
        record.setFamilyId(userHelper.getLoginUser().getUserId());
        record.setIfUpload(false);
        LogUtil.d(record.toString());
        helper.saveOrUpdate(record);
    }

    /**
     * 通过Id删除记录
     *
     * @param id
     */
    public void deleteById(int id) {
        helper.deleteById(id);
    }

    /**
     * 通过昵称获取所有记录
     *
     * @param name
     * @return
     */
    public List<Record> getAllRecordByNickname(String name) {
        List<Record> recordList;
        recordList = helper.getRecordByNickname(name);
        return recordList;
    }
}
