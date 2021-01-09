package com.example.timmo_songjas.feature.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProfileResponse;
import com.example.timmo_songjas.feature.project.ProjectDetailActivity;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;


public class ProfileProjectFragment extends Fragment {

    RecyclerView recyclerView;
    ProfileProjectAdapter profileProjectAdapter;

    ArrayList<ProfileProjectItem> profileList = new ArrayList<>();

    RetrofitService service1;

    public ProfileProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile_project, container, false);

        //리싸이클러뷰 불러오기
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_profileproject);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //어답터를 통해 내용 추가하기
        profileProjectAdapter = new ProfileProjectAdapter(container.getContext());

        loadData();
        for(int i= 0; i < profileList.size(); i++){
            profileProjectAdapter.addItem(profileList.get(i));
        }
        //profileProjectAdapter.addItem(new ProfileProjectItem("대전광역시", "D-7", "코리아해커톤 준비 뒤질 것 같아ㅇㅅㅇ", "해커톤", "IT/개발", "프란트2/백엔드3"));
        recyclerView.setAdapter(profileProjectAdapter);


        //리싸이클뷰가 비었을 경우 글자 설정
        TextView emptyText = (TextView) rootView.findViewById(R.id.tv_empty_profileproject);
        if(profileProjectAdapter.getItemCount() == 0){
            emptyText.setText("아직 팀모글이 없습니다.");
            emptyText.setVisibility(View.VISIBLE);
        }

        //클릭 이벤트 처리
        profileProjectAdapter.setOnItemClickListener(new ProfileProjectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ProfileProjectAdapter.ViewHolder holder, View view, int position) {
                ProfileProjectItem item = profileProjectAdapter.getItem(position);
                //화면 이동
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    //데이터 받아오기
    public void loadData(){
        service1 = RetrofitClient.getClient().create(RetrofitService.class);
        Call<ProfileResponse> call = service1.mainProfile(USER_TOKEN);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    ProfileResponse result = response.body();
                    if (result.getStatus() == 200) {
                        Log.d("연결 성공: ", result.getMessage());
                        for(int i= 0; i < result.getProjects().size(); i++){
                            String state = result.getProjects().get(i).getLargeAddress();
                            String endDate = result.getProjects().get(i).getEndDate();
                            String title = result.getProjects().get(i).getTitle();
                            String type = result.getProjects().get(i).getType();
                            String field = result.getProjects().get(i).getField();
                            String member ="";
                            for(int j= 0; j < result.getProjects().get(i).getProjectPositions().size(); i++){
                                String tmp = result.getProjects().get(i).getProjectPositions().get(j).getPosition()
                                        +String.valueOf(result.getProjects().get(i).getProjectPositions().get(j).getHeadCount())
                                        +"명";
                                member += tmp;
                                if(j != result.getProjects().get(i).getProjectPositions().size()-1) member +="/";
                            }
                            profileList.add(new ProfileProjectItem(state, endDate, title, type, field, member));
                        }


                    }
                    else {
                        Log.d("각종 오류: ", result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("실패: ", "연결 실패");
            }
        });
    }
}