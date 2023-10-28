package com.maxsella.fatmuscle.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maxsella.fatmuscle.common.base.BaseViewModel;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.db.bean.Member;
import com.maxsella.fatmuscle.repository.MemberRepository;

import org.litepal.LitePal;

import java.util.List;

public class MemberViewModel extends BaseViewModel {

    private MemberRepository memberRepository = new MemberRepository();

    private MutableLiveData<List<Member>> _members = memberRepository.getAllMembers();

    //Member数据列表
    public LiveData<List<Member>> members = get_members();

    private MutableLiveData<Member> _member = new MutableLiveData<>();

    //添加的新Member
    public LiveData<Member> member = get_member();

    private MutableLiveData<Member> _editMember = new MutableLiveData<>();
    //修改的Member
    public LiveData<Member> editMember = get_editMember();

    public String msg = "";

    public MutableLiveData<Member> get_editMember() {
        return _editMember;
    }

    public MutableLiveData<List<Member>> get_members() {
        return _members;
    }

    public MutableLiveData<Member> get_member() {
        return _member;
    }

    //更新新Member
    public void initMember(Member member) {
        _member.setValue(member);
    }

    //修改Member
    public void changeEditMember(Member member) {
        _editMember.setValue(member);
    }

    //更新数据库
    public void updateMember(Member member) {
        memberRepository.updateMember(member);
    }

    //往数据库添加新Member
    public boolean addMember(Member member) {
        Member lastMember = LitePal.findLast(Member.class);
        if (lastMember == null) {
            member.setId(1);
        } else {
            member.setId(lastMember.getId() + 1);
        }
        if (member.getNickname().equals("")) {
            failed = "请输入昵称";
            return false;
        }
        if (memberRepository.verifyName(member.getNickname())) {
            failed = "昵称重复,请重新输入";
            return false;
        }
        msg = "添加成功";
        memberRepository.saveMember(member);
        return true;
    }

    //通过id从数据库获取Member
    public void getMemberById(int id) {
        LogUtil.d("editMember: " + memberRepository.getMemberById(id).toString());
        Member member = memberRepository.getMemberById(id);
        _editMember.setValue(member);
        LogUtil.d("livedata editMember: " + editMember.getValue().toString());
    }

}
