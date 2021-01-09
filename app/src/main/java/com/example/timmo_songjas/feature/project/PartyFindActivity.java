package com.example.timmo_songjas.feature.project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.PartyFindResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.PARTY_TO_PROADD;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;


public class PartyFindActivity extends AppCompatActivity {


    private ImageView back_btn;
    private ImageView pic_partyfind;
    private TextView tv_name_partyfind;
    private TextView tv_email_partyfind;

    ConstraintLayout result_partyfind_true;
    TextView result_partyfind_false;

    //레트로핏
    RetrofitService service;

    @RequiresApi(api= Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_find);

        init();

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_partyfind);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //뒤로가기
        back_btn = (ImageView) mToolbar.findViewById(R.id.back_partyfind);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SearchView searchView = findViewById(R.id.search_view_partyfind);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false); //키보드 위로 올라오도록

        //SearchView의 검색 이벤트
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {  //키워드 입력 후 엔터 입력
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                load(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
    }

    void init(){
        //검색 결과 true/false
        result_partyfind_true = findViewById(R.id.result_partyfind_true);
        result_partyfind_false = findViewById(R.id.result_partyfind_false);

        pic_partyfind = (ImageView) findViewById(R.id.pic_partyfind);
        tv_name_partyfind = (TextView) findViewById(R.id.tv_name_partyfind);
        tv_email_partyfind = findViewById(R.id.tv_email_partyfind);
    }

    private void load(String query){
        service = RetrofitClient.getClient().create(RetrofitService.class);
        Call<PartyFindResponse> call = service.partyFind(USER_TOKEN, "application/json", query);

        call.enqueue(new Callback<PartyFindResponse>() {
            @Override
            public void onResponse(Call<PartyFindResponse> call, Response<PartyFindResponse> response) {
                if(response.isSuccessful()) {
                    PartyFindResponse result = response.body();
                    if(result.getStatus() == 200) {
                        if(result.getData()!= null){
                            Toast.makeText(getApplicationContext(), "검색결과 있음 ", Toast.LENGTH_SHORT).show();
                            result_partyfind_true.setVisibility(View.VISIBLE);
                            result_partyfind_false.setVisibility(View.GONE);
                            if(result.getData().getImg() != null){
                                Glide.with(getApplicationContext()).load(result.getData().getImg()).circleCrop().into(pic_partyfind);
                            }
                            tv_name_partyfind.setText(result.getData().getName());
                            tv_email_partyfind.setText(result.getData().getEmail());

                            //그리고 나온 결과를 눌렀을 때. id : result_partyfind_true
                            result_partyfind_true.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    //TODO: 전 페이지로 정보 한번에 인텐트
                                    //TODO: 성향은 false, true로 모든 정보 다 보내야 함
                                    Intent intent = getIntent();
                                    intent.putExtra("id", result.getData().getId());
                                    intent.putExtra("email", result.getData().getEmail());
                                    intent.putExtra("name", result.getData().getName());
                                    intent.putExtra("image", result.getData().getImg());
                                    setResult(PARTY_TO_PROADD, intent);
                                    finish();
                                }
                            });

                        }
                        else{
                            result_partyfind_true.setVisibility(View.GONE);
                            result_partyfind_false.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PartyFindResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
