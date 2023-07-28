package com.example.keepnotes.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainModel {
    @SerializedName("JSON")
    @Expose
    List<FakeModel> fakeModelList;

    public List<FakeModel> getFakeModelList() {
        return fakeModelList;
    }

    public void setFakeModelList(List<FakeModel> fakeModelList) {
        this.fakeModelList = fakeModelList;
    }
}
