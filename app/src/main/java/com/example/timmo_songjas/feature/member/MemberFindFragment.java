package com.example.timmo_songjas.feature.member;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.MemberFindResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;


public class MemberFindFragment extends Fragment {

    RetrofitService service;


    private ImageView filter_btn;
    private ImageView subtitle_memberfind;

    public ArrayList<MemberFindItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private MemberFindAdapter memberFindAdapter;


    private  String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api= Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_member_find, container, false);


        service = RetrofitClient.getClient().create(RetrofitService.class);
        Call<MemberFindResponse> call = service.memberFind(USER_TOKEN, "application/json");

        call.enqueue(new Callback<MemberFindResponse>() {
            @Override
            public void onResponse(Call<MemberFindResponse> call, Response<MemberFindResponse> response) {
                if(response.isSuccessful()){
                    MemberFindResponse result = response.body();
                    if(result.getStatus() == 200){
                        //Toast.makeText(getActivity(), "팀 목록 조회 성공", Toast.LENGTH_SHORT).show();
                        List<MemberFindResponse.Datum> member_list = result.getData();

                        list.clear();
                        Log.e("ckt1",list.toString());

                        for(int i=0; i <member_list.size();i++){

                            int m_id = member_list.get(i).getId();
                            String l_addr = member_list.get(i).getLargeAddress();
                            String s_addr = member_list.get(i).getSmallAddress();
                            String profile = member_list.get(i).getImg();

                            //TODO: 프래그먼트 리싸이클러뷰의 아이템의 이미지 뷰에 어떻게 박아야 하는 거야??????
                            //view.findViewById(iv_member)

                            /*if(member_list.get(i).getImg() != null){
                                //Glide.with(getActivity()).load(member_list.get(i).getImg()).circleCrop().into(iv_member);
                            }*/

                            List<MemberFindResponse.Datum.Member> timegles = member_list.get(i).getMembers();
                            for(int j =0; j<timegles.size();j++) {
                                String title = timegles.get(j).getTitle();
                                String type = timegles.get(j).getType();
                                String field = timegles.get(j).getField();

                                List<MemberFindResponse.Datum.Member.MemberPosition> positions = timegles.get(j).getMemberPositions();

                                String position ="";
                                for(int k = 0;k<positions.size();k++){
                                    position = position + positions.get(k).getPosition();
                                    if (j != positions.size() - 1) position = position + " / ";
                                } //position에 목록이 들어간다.

                                list.add(new MemberFindItem(m_id, profile, l_addr, s_addr, title, type, field, position));
                            }

                            //리싸이클러뷰 어댑터 만들고 추가
                            recyclerView = view.findViewById(R.id.rv_memberfind);
                            memberFindAdapter = new MemberFindAdapter(list, getContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(memberFindAdapter);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<MemberFindResponse> call, Throwable t) {

            }
        });



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
                //Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
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