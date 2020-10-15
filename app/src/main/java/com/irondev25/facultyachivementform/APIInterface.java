package com.irondev25.facultyachivementform;

import com.irondev25.facultyachivementform.pojo.TeacherItem;
import com.irondev25.facultyachivementform.pojo.TeacherSignIn;
import com.irondev25.facultyachivementform.pojo.TokenPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/teacher/teachers")
    Call<List<TeacherItem>> getTeacherList(@Query("dept") Integer dept_id,@Query("first_name") String fName);

    @POST("/api-auth-token")
    Call<TokenPojo> loginRequest(@Body TeacherSignIn teacherSignIn);

//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}

