package com.example.timmo_songjas.feature.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;

public class ProjectFindFragment extends Fragment {


    private ImageView filter_btn;
    private ImageView subtitle_projectfind;

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
                //TODO: 네트워킹 필요
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //검색창에서 키워드 변경이 있을 때마다 호출
                return false;
            }
        });

        //리싸이클러뷰 어댑터 만들고 추가


        //필터 버튼
        filter_btn = (ImageView) toolbar.findViewById(R.id.filter_projectfind);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), FilterActivity.class);
                //startActivityForResult(intent, 1004);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}