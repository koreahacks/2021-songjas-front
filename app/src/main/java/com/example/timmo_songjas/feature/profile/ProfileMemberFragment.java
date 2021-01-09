package com.example.timmo_songjas.feature.profile;

import android.content.Context;
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
import android.widget.Toast;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProfileResponse;
import com.example.timmo_songjas.feature.member.MemberDetailActivity;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProfileMemberFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    ProfileMemberAdapter profileMemberAdapter;

    ArrayList<ProfileMemberItem> memeberList = new ArrayList<>();

    RetrofitService service1;

    public ProfileMemberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        //리싸이클러뷰 불러오기
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_profilemember);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //어답터를 통해 내용 추가하기
        profileMemberAdapter = new ProfileMemberAdapter(container.getContext());

        //profileMemberAdapter.addItem(new ProfileMemberItem("서울특별시", "나야나", "공모전", "컴퓨터그래픽", "그래픽개발자"));

        loadData();
        profileMemberAdapter.addItems(memeberList);
        recyclerView.setAdapter(profileMemberAdapter);


        //리싸이클뷰가 비었을 경우 글자 설정
        TextView emptyText = (TextView) rootView.findViewById(R.id.tv_empty_profilemember);
        if(profileMemberAdapter.getItemCount() == 0){
            emptyText.setText("아직 팀글이 없습니다.");
            emptyText.setVisibility(View.VISIBLE);
        }

        //클릭 이벤트 처리
        profileMemberAdapter.setOnItemClickListener(new ProfileMemberAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ProfileMemberAdapter.ViewHolder holder, View view, int position) {
                ProfileMemberItem item = profileMemberAdapter.getItem(position);
                //화면 이동
                Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
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
                        Log.d("팀글 연결 성공: ", result.getMessage());
                        memeberList.clear();
                        for(int i= 0; i < result.getMembers().size(); i++){
                            String state = result.getUsers().getLargeAddress();
                            String title = result.getMembers().get(i).getTitle();
                            String type = result.getMembers().get(i).getType();
                            String field = result.getMembers().get(i).getField();
                            String positon = result.getMembers().get(i).getMemberPositions().get(0).getPosition();
                            //profileMemberAdapter.addItem(new ProfileMemberItem(state, title, type, field, positon));
                            memeberList.add(new ProfileMemberItem(state, title, type, field, positon));
                        }
                        Toast.makeText(context, String.valueOf(memeberList.size()), Toast.LENGTH_LONG).show();
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