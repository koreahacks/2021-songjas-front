package com.example.timmo_songjas.feature.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;


public class MemberFindFragment extends Fragment {

    RetrofitService service;


    private ImageView filter_btn;
    private ImageView subtitle_memberfind;

    public ArrayList<MemberFindItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private MemberFindAdapter memberFindAdapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_member_find, container, false);

        //툴바영역
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_2); //fragment xml 툴바 영역?
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        //프래그먼트에 키보드 입력 시 하단 올라오지 않게
        ((MainActivity) getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        SearchView searchView = view.findViewById(R.id.search_view_memberfind);

        searchView.setMaxWidth(Integer.MAX_VALUE);

        //SearchView의 검색 이벤트
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {  //키워드 입력 후 엔터 입력
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                //서버로 스트링 보내고 받으면 리싸이클러뷰로 반영?
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //검색창에서 키워드 변경이 있을 때마다 호출
                return false;
            }
        });
        //필터 버튼
        filter_btn = (ImageView) toolbar.findViewById(R.id.filter_memberfind);
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