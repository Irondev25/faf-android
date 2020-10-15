package com.irondev25.facultyachivementform.repository;

import android.app.Application;
import android.content.res.Resources;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import com.irondev25.facultyachivementform.APIInterface;
import com.irondev25.facultyachivementform.GlobalVars;
import com.irondev25.facultyachivementform.R;
import com.irondev25.facultyachivementform.pojo.TeacherItem;


import java.util.List;

public class TeacherListRepository {
    private static final String API_URL = GlobalVars.API_URL;
    private APIInterface apiInterface;
    private MutableLiveData<List<TeacherItem>> teacherListLiveData;

    public TeacherListRepository(){
        teacherListLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        apiInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface.class);
    }

    public void getTeacherList(@Nullable Integer dept_id, @Nullable String fName){
        apiInterface.getTeacherList(dept_id,fName).enqueue(new Callback<List<TeacherItem>>() {
            @Override
            public void onResponse(Call<List<TeacherItem>> call, Response<List<TeacherItem>> response) {
                if(response.body()!=null){
                    teacherListLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TeacherItem>> call, Throwable t) {
                teacherListLiveData.postValue(null);
            }
        });
    }

    public LiveData<List<TeacherItem>> getTeacherListResponseLiveData(){
        return teacherListLiveData;
    }
}
