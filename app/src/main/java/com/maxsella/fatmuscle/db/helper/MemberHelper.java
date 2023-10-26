package com.maxsella.fatmuscle.db.helper;

import com.maxsella.fatmuscle.db.bean.Member;
import com.maxsella.fatmuscle.db.bean.User;

import org.litepal.LitePal;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemberHelper {

    private ExecutorService service = Executors.newSingleThreadExecutor();

    public static MemberHelper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static MemberHelper INSTANCE = new MemberHelper();
    }

    public void saveOrUpdate(Member member) {
        service.execute(() -> member.saveOrUpdate("id = ?", String.valueOf(member.getId())));
    }

    public List<Member> getMembersByFamilyId(int familyId) {
        return LitePal.where("familyId = ?", String.valueOf(familyId)).find(Member.class);
    }

    public Member getMemberById(int id) {
        return LitePal.find(Member.class, id);
    }
}