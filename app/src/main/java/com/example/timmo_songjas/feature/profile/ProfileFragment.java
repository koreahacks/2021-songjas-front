package com.example.timmo_songjas.feature.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProfileResponse;
import com.example.timmo_songjas.feature.member.MemberAddActivity;
import com.example.timmo_songjas.feature.profile.ProfileApplyFragment;
import com.example.timmo_songjas.feature.profile.ProfileEditActivity;
import com.example.timmo_songjas.feature.profile.ProfileMemberFragment;
import com.example.timmo_songjas.feature.profile.ProfileProjectFragment;
import com.example.timmo_songjas.feature.project.ProjectAdd1Activity;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;


/*
TODO: /변수 값은 서버와 연결에서 DB에서 가져와야 함. Data 패키지 따로 만들어서 거기서 데이터들 정리하고, feature 패키지에서 데이터 가져와 사용하는 걸로
*/

public class ProfileFragment extends Fragment {
    Context context;
    ProfileApplyFragment applyFragment;
    ProfileProjectFragment projectFragment;
    ProfileMemberFragment memberFragment;
    ImageView plusImage;
    ImageView profileEdit;
    FragmentManager manager;

    TextView name;
    ImageView imageView;
    TextView appNum;
    TextView projectNum;
    TextView memberNum;

    RetrofitService service1;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.fl_profile, new ProfileProjectFragment()).commit();
        getFragmentManager().beginTransaction().replace(R.id.fl_profile, new ProfileMemberFragment()).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        //툴바영역
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_profile); //fragment xml 툴바 영역?
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        //프로필 수정
        profileEdit = (ImageView) toolbar.findViewById(R.id.profile_edit);
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        name = (TextView)rootView.findViewById(R.id.tv_name_profile);
        imageView = (ImageView) rootView.findViewById(R.id.iv_image_profile);
        plusImage = (ImageView)rootView.findViewById(R.id.iv_plus_profile);
        appNum = (TextView)rootView.findViewById(R.id.tv_applynum_profile);
        projectNum = (TextView)rootView.findViewById(R.id.tv_projectnum_profile);
        memberNum = (TextView)rootView.findViewById(R.id.tv_membernum_profile);

        //TODO: 서버에서 가져온 이미지 불러오기
        //이미지 동그랗게 자르기
        Glide.with(this).load(R.drawable.ic_bg_profie_64_dp).circleCrop().into(imageView);

        loadData();

        //fragment
        applyFragment = new ProfileApplyFragment();
        projectFragment = new ProfileProjectFragment();
        memberFragment = new ProfileMemberFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_profile, applyFragment).commit();

        //TODO: 각 탭에 존재하는 글 개수 받아오기

        TabLayout tab = (TabLayout)rootView.findViewById(R.id.tb_profile);
        TabItem applyTab = (TabItem)rootView.findViewById(R.id.ti_apply);
        TabItem projectTab = (TabItem) rootView.findViewById(R.id.ti_project);
        TabItem memberTab = (TabItem) rootView.findViewById(R.id.ti_member);

        //탭 이동
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 1){
                    selected=projectFragment;
                    plusImage.setVisibility(View.VISIBLE);
                    plusImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ProjectAdd1Activity.class);
                            startActivity(intent);
                        }
                    });
                }
                else if(position == 2){
                    selected=memberFragment;
                    plusImage.setVisibility(View.VISIBLE);
                    plusImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), MemberAddActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    selected=applyFragment;
                    plusImage.setVisibility(View.INVISIBLE);
                }

                getFragmentManager().beginTransaction().replace(R.id.fl_profile, selected).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
                        if(result.getUsers().getImg() != null && !result.getUsers().getImg().equals("")){
                            Glide.with(context).load(result.getUsers().getImg()).circleCrop().into(imageView);
                        }
                        name.setText(result.getUsers().getName());
                        appNum.setText(String.valueOf(result.getProjectApplicants().size()));
                        projectNum.setText(String.valueOf(result.getProjects().size()));
                        memberNum.setText(String.valueOf(result.getMembers().size()));
                        //Log.d("데이터 확인", String.valueOf(result.getMembers().size()));
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