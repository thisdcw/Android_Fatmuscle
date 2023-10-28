package com.maxsella.fatmuscle.db.helper;

import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.Record;

import org.litepal.LitePal;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordHelper {

    private ExecutorService service = Executors.newSingleThreadExecutor();

    public static RecordHelper getInstance() {
        return RecordHelper.Holder.INSTANCE;
    }

    private static class Holder {
        private static RecordHelper INSTANCE = new RecordHelper();
    }

    public void saveOrUpdate(Record record) {
        service.execute(() -> record.saveOrUpdate("id = ?", String.valueOf(record.getId())));
    }

    /**
     * 通过familyId获取最新的一条记录
     *
     * @param familyId
     * @return
     */
    public Record getLastRecordForFamily(int familyId) {
        return LitePal.where("familyId = ?", String.valueOf(familyId))
                .findLast(Record.class);
    }

    /**
     * 通过Id删除记录
     *
     * @param id
     */
    public void deleteById(int id) {
        LitePal.delete(Record.class, id);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllRecord() {
        LitePal.deleteAll(Record.class);
    }

    /**
     * 获取当前测量用户所有记录
     *
     * @param nickname
     * @return
     */
    public List<Record> getRecordByNickname(String nickname) {
        return LitePal.where("nickname = ?", nickname).find(Record.class);
    }

    /**
     * 通过昵称和测量部位筛选
     *
     * @param nickname
     * @param item
     * @return
     */
    public List<Record> getRecordByItem(String nickname, String item) {
        return LitePal.where("nickname = ? and item = ?", nickname, item)
                .order("date desc")
                .find(Record.class);
    }

    /**
     * 通过昵称,部位,类别筛选,手臂测量记录专用
     *
     * @param nickname
     * @param item
     * @param mode
     * @return
     */
    public List<Record> getRecordByItem(String nickname, String item, String mode) {
        return LitePal.where("nickname = ? and item = ? and mode = ?", nickname, item, mode)
                .order("date desc")
                .find(Record.class);
    }

    /**
     * 通过昵称类别筛选最新的记录
     *
     * @param nickname
     * @param item
     * @return
     */
    public Record getLatestRecordForCurrentUser(String nickname, String item) {
        Record record = LitePal.where("nickname = ? and item = ?", nickname, item)
                .findLast(Record.class);
        return record;
    }

}
