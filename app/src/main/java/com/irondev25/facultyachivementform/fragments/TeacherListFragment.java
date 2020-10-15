package com.irondev25.facultyachivementform.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irondev25.facultyachivementform.APIClient;
import com.irondev25.facultyachivementform.APIInterface;
import com.irondev25.facultyachivementform.R;
import com.irondev25.facultyachivementform.ViewModel.TeacherListViewModel;
import com.irondev25.facultyachivementform.adapters.item.TeacherItemAdapater;
import com.irondev25.facultyachivementform.pojo.TeacherItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherListFragment extends Fragment {
    private TeacherListViewModel viewModel;
    private TeacherItemAdapater adapater;

    private Spinner deptListSpinner;
    private EditText firstName;
    private Button button;
    private Integer deptVal = 0;
    private String firstNameString;

    private HashMap<String, Integer> deptPairList = new HashMap<>();
    List<String> deptList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deptPairList.put("All",0);
        deptPairList.put("Civil Engineering",1);
        deptPairList.put("Mechanical Engineering",2);
        deptPairList.put("Electrical and Electronics Engineering",3);
        deptPairList.put("Electronics and Communication Engineering",4);
        deptPairList.put("Industrial Engineering and Management",5);
        deptPairList.put("Computer Science and Engineering",6);
        deptPairList.put("Electronics and Telecommunication Engineering",7);
        deptPairList.put("Information Science and Engineering",8);
        deptPairList.put("Electronics and Instrumentation Engineering",9);
        deptPairList.put("Medical Electronics",10);
        deptPairList.put("Chemical Engineering",11);
        deptPairList.put("Bio-Technology",12);
        deptPairList.put("Computer Applications (MCA)",13);
        deptPairList.put("Management Studies and Research Centre",14);
        deptPairList.put("Mathematics Department",15);
        deptPairList.put("Physics Department",16);
        deptPairList.put("Chemistry Department",17);
        deptPairList.put("Aerospace Engineering",18);

        deptPairList.forEach((k,v)->{
            deptList.add(k);
        });

        adapater = new TeacherItemAdapater();

        viewModel = ViewModelProviders.of(this).get(TeacherListViewModel.class);
        viewModel.init();

        viewModel.getTeacherListResponseLiveData().observe(this, new Observer<List<TeacherItem>>() {
            @Override
            public void onChanged(List<TeacherItem> teacherItems) {
                if(teacherItems!=null){
                    adapater.setResults(teacherItems);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_list_frag,container,false);

        deptListSpinner = view.findViewById(R.id.deptList);
        deptListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptVal = deptPairList.get(deptList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,deptList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptListSpinner.setAdapter(aa);


        firstName = view.findViewById(R.id.serach_by_first_name);
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    firstNameString = firstName.getText().toString();
                }
            }
        });
        button = view.findViewById(R.id.search_button);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapater);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = firstName.getText().toString();
                Pattern pattern = Pattern.compile("[A-Za-z]*");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()){
                    firstName.setError("Invalid name input (must only contain aplhabets)");
                    return;
                }
                firstNameString = text;
                performSearch();
            }
        });
        return view;
    }

    public void performSearch(){
        Integer deptId = null;
        if(deptVal != 0){
            deptId = deptVal;
        }
        String fName = null;
        if(firstNameString !=null && firstNameString.length() > 0){
            fName = firstNameString;
        }
        viewModel.getTeacherList(deptId,fName);
    }
}
