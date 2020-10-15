package com.irondev25.facultyachivementform.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementform.pojo.TeacherItem;
import com.irondev25.facultyachivementform.repository.TeacherListRepository;

import java.util.List;

public class TeacherListViewModel extends AndroidViewModel {
    private TeacherListRepository teacherListRepository;
    private LiveData<List<TeacherItem>> teacherListResponseLiveData;

    public TeacherListViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        teacherListRepository = new TeacherListRepository();
        teacherListResponseLiveData = teacherListRepository.getTeacherListResponseLiveData();
    }

    public void getTeacherList(@Nullable Integer dept_id,@Nullable String fName){
        teacherListRepository.getTeacherList(dept_id,fName);
    }

    public LiveData<List<TeacherItem>> getTeacherListResponseLiveData(){
        return teacherListResponseLiveData;
    }
}
