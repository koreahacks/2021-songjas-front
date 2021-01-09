package com.example.timmo_songjas.feature.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.TimmoFilterResponse;
import com.example.timmo_songjas.feature.FilterActivity;
import com.example.timmo_songjas.feature.sign.SignInActivity;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProjectFindFragment extends Fragment {

    RetrofitService service;
    private Map<String,String> s_q ;
    private Map<String,Boolean> b_q;

    public ArrayList<ProjectFindItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProjectFindAdapter projectFindAdapter;

    private ImageView filter_btn;
    private ImageView subtitle_projectfind;

    private  String title;

    private void initDataset() {
        list.clear();
        //레트로핏 정보 받아오는 곳. 일단은 dummy
        list.add(new ProjectFindItem(1,
                "자대", "나는 특별하다..","D-7",
                "코리아 핵 해커톤 같이 할 사람 구합니다. \n" + "초심자도 환영입니다!",
                "공모전","IT/소프트웨어","디자인 1명/개발자 2명"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_find, container, false);

        //툴바영역
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_1); //fragment xml 툴바 영역?
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        //프래그먼트에 키보드 입력 시 하단 올라오지 않게
        ((MainActivity) getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        SearchView searchView = view.findViewById(R.id.search_view_projectfind);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        //searchView.setIconifiedByDefault(false);

        //SearchView의 검색 이벤트
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {  //키워드 입력 후 엔터 입력
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                title =query;
                //TODO: 네트워킹 필요
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //검색창에서 키워드 변경이 있을 때마다 호출
                return false;
            }
        });

        //리싸이클러뷰 어댑터 만들고 추가
        recyclerView = view.findViewById(R.id.rv_projectfind);
        projectFindAdapter = new ProjectFindAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(projectFindAdapter);


        //필터 버튼
        filter_btn = (ImageView) toolbar.findViewById(R.id.filter_projectfind);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                intent.putExtra("search_title",title);
                startActivityForResult(intent, 1004);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1004){
            //필터 정보 넘어옴
           s_q =  (Map<String, String>) data.getSerializableExtra("s_q");
           b_q =  (Map<String, Boolean>) data.getSerializableExtra("b_q");

           service.timmoFilter(USER_TOKEN , s_q , b_q).enqueue(new Callback<TimmoFilterResponse>() {
               @Override
               public void onResponse(Call<TimmoFilterResponse> call, Response<TimmoFilterResponse> response) {
                   TimmoFilterResponse result = response.body();
                   if(response.isSuccessful()){

                       Log.d("필터1 : ", result.getMessage().toString());

                   }
                   else {

                       Log.d("필터1 : ", String.valueOf(response.errorBody()));
                   }
               }
               @Override
               public void onFailure(Call<TimmoFilterResponse> call, Throwable t) {
                   Log.e("필터3 에러 발생", t.getMessage().toString());


               }
           });



        }

    }
}