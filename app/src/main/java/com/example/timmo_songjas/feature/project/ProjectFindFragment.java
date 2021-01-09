package com.example.timmo_songjas.feature.project;

import android.content.Intent;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProjectFindResponse;
import com.example.timmo_songjas.data.TimmoFilterResponse;
import com.example.timmo_songjas.feature.FilterActivity;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProjectFindFragment extends Fragment {

    RetrofitService service;
    private Map<String,String> s_q ;
    private Map<String,Boolean> b_q;

    public ArrayList<ProjectFindItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProjectFindAdapter projectFindAdapter;

    private int project_id;

    //xml id
    ImageView filter_btn;
    ImageView subtitle_projectfind;


    private  String title;

    public String getDday(String target) {
        try{
            String[] arr = target.split("-");

            Calendar todayCal = Calendar.getInstance(); //현재 날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //디데이 초기화

            int year = Integer.parseInt(arr[0]);
            int moth = Integer.parseInt(arr[1]) -1;
            int day = Integer.parseInt(arr[2]);
            ddayCal.set(year, moth, day);

            //밀리 초 -> 초로 변환
            final int ONE_DAY = 24 * 60 * 60 * 1000;
            long dday = ddayCal.getTimeInMillis()/ ONE_DAY;
            long today = todayCal.getTimeInMillis()/ ONE_DAY;

            long result = dday - today;

            String mStrFormat;

            if(result > 0)
                mStrFormat = "D-%d";
            else if(result == 0)
                mStrFormat = "D-DAY";
            else {
                result *= -1;
                mStrFormat = "D+%d";
            }
            return String.format(mStrFormat, result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api= Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //팀모 목록 조회


    }

    @RequiresApi(api= Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_find, container, false);

        service = RetrofitClient.getClient().create(RetrofitService.class);
        Call<ProjectFindResponse> call = service.projectFind(USER_TOKEN, "application/json");

        call.enqueue(new Callback<ProjectFindResponse>() {
            @Override
            public void onResponse(Call<ProjectFindResponse> call, Response<ProjectFindResponse> response) {
                if(response.isSuccessful()){
                    ProjectFindResponse result = response.body();
                    if (result.getStatus() == 200) {
                        Toast.makeText(getActivity(), "팀모 목록 조회 성공", Toast.LENGTH_SHORT).show();
                        List<ProjectFindResponse.Datum> project_list = result.getData();

                        list.clear();
                        Log.e("ckt1",list.toString());

                        for(int i=0; i <project_list.size();i++){
                            //변수에 결과 집어넣고. list.add

                            int p_id = project_list.get(i).getId();
                            String univ = project_list.get(i).getLimitUniv();
                            String l_addr = project_list.get(i).getLargeAddress();
                            String s_addr = project_list.get(i).getSmallAddress();
                            String end_date = project_list.get(i).getEndDate();

                            String d_day = getDday(end_date); //변수 타입
                            String title = project_list.get(i).getTitle();
                            String type = project_list.get(i).getType();
                            String field = project_list.get(i).getField();

                            String position ="";
                            List<ProjectFindResponse.Datum.ProjectPositions> position_list = project_list.get(i).getProjectPositions();
                            for(int j=0; j<position_list.size();j++) {
                                position = position + position_list.get(j).getPosition() + " " + position_list.get(j).getHeadCount() + "명";
                                if (j != position_list.size() - 1) position = position + " / ";
                            }//pos에 포지션 목록이 들어갔는데

                            list.add(new ProjectFindItem(p_id, univ, l_addr, s_addr, d_day, title, type, field, position));
                        }

                        //리싸이클러뷰 어댑터 만들고 추가
                        recyclerView = view.findViewById(R.id.rv_projectfind);
                        projectFindAdapter = new ProjectFindAdapter(list, getContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(projectFindAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ProjectFindResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });

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
                //TODO: 검색 네트워킹
                title = query;
                //TODO: 네트워킹 필요
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //검색창에서 키워드 변경이 있을 때마다 호출
                return false;
            }
        });


        //필터 버튼
        filter_btn = (ImageView) toolbar.findViewById(R.id.filter_projectfind);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("필터 출발 : ", " 버튼 누름");

                Intent intent = new Intent(getActivity(), FilterActivity.class);
                //intent.putExtra("search_title",title);
                startActivityForResult(intent, 1004);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1004 && resultCode== RESULT_OK){
            Log.d("필터0 : ", "빽 옴");

            //필터 정보 넘어옴
           s_q =  (Map<String, String>) data.getSerializableExtra("s_q");
           b_q =  (Map<String, Boolean>) data.getSerializableExtra("b_q");

            if(title != null)
                s_q.put("title",title);

           for(Map.Entry<String,String>i : s_q.entrySet()){
               Log.d("필터s_q : ", i.getKey()+" "+i.getValue() );

           }

            for(Map.Entry<String,Boolean>i : b_q.entrySet()){
                Log.d("필터b_q : ", i.getKey()+" "+i.getValue() );

            }

           service.timmoFilter(USER_TOKEN , s_q , b_q).enqueue(new Callback<TimmoFilterResponse>() {
               @Override
               public void onResponse(Call<TimmoFilterResponse> call, Response<TimmoFilterResponse> response) {
                   TimmoFilterResponse result = response.body();
                   if(response.isSuccessful()){
                       Log.d("필터1 : ", result.getMessage().toString());
                       Log.d("필터1 : ", String.valueOf(result.getData().size()));
                       if(result.getData().size() >0)
                           Log.d("필터1 : ", String.valueOf(result.getData().get(0).getId()));


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