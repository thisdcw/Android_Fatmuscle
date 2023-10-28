package com.maxsella.fatmuscle.common.base;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maxsella.fatmuscle.common.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class BaseViewModel extends ViewModel {
    public String failed;
    private Map<String, MutableLiveData> liveDataMap = new HashMap<>();
    protected LifecycleOwner owner;

    public void setLifecycleOwner(LifecycleOwner owner) {
        this.owner = owner;
    }

    protected <T> MutableLiveData getLiveData(String key) {
        if (!liveDataMap.containsKey(key)) {
            liveDataMap.put(key, new MutableLiveData<>());
            LogUtil.d("push key " + key);
        }
        return liveDataMap.get(key);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (MutableLiveData liveData : liveDataMap.values()) {
            liveData.removeObservers(owner);
            LogUtil.d("remove ==> " + liveData);
        }
        LogUtil.d("clear liveDataMap");
        liveDataMap.clear();
    }
}
