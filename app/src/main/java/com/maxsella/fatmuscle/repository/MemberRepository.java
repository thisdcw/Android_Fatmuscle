package com.maxsella.fatmuscle.repository;

import androidx.lifecycle.MutableLiveData;

import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.Member;
import com.maxsella.fatmuscle.db.helper.MemberHelper;
import com.maxsella.fatmuscle.db.helper.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {


    public void saveMember(Member member) {
        member.setFamilyId(UserHelper.getInstance().getLoginUser().getUserId());
        LogUtil.d(member.toString());
        MemberHelper.getInstance().saveOrUpdate(member);
    }

    public void updateMember(Member member) {
        MemberHelper.getInstance().saveOrUpdate(member);
    }

    public MutableLiveData<List<Member>> getAllMembers() {
        MutableLiveData<List<Member>> members = new MutableLiveData<>();
        members.setValue(MemberHelper.getInstance().getMembersByFamilyId(UserHelper.getInstance().getLoginUser().getUserId()));
        LogUtil.d("member size: " + members.getValue().size());
        return members;
    }

    public Member getMemberById(int id) {
        return MemberHelper.getInstance().getMemberById(id);
    }

}
