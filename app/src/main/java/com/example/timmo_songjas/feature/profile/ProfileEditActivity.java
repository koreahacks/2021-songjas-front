package com.example.timmo_songjas.feature.profile;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProfileEditInputData;
import com.example.timmo_songjas.data.ProfileEditInputResponse;
import com.example.timmo_songjas.data.ProfileEditResponse;
import com.example.timmo_songjas.data.ProfileImageResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.PROFADD_IMAGE;
import static com.example.timmo_songjas.feature.utils.CommonValues.PROFILADD_IMAGE;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProfileEditActivity extends AppCompatActivity {
    ImageView ivProfile;
    int morningCount;
    int nightCount;
    int dawnCount;
    int planCount;
    int focusCount;
    int leaderCount;
    int followCount;
    int challCount;
    int realCount;

    private TextView tv_email_profile;
    private TextView tvProfile;
    private EditText etState;
    private EditText etCounty;
    private EditText etUniv;
    private  EditText etMjor;
    private EditText etGrade;

    private TextView tvMorning;
    private TextView tvNight;
    private TextView tvDawn;
    private TextView tvPlan;
    private TextView tvFocus;
    private TextView tvLeader;
    private TextView tvFollow;
    private TextView tvChall;
    private TextView tvReal;

    RetrofitService service1;
    RetrofitService service2;

    Bitmap img;
    Uri imageUri;
    String imagePath;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};

    //이미지 파일 열기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILADD_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    imageUri = data.getData();
                    in.close();

                    // 이미지 표시
                    ivProfile.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            imagePath = getPathEugene(getApplicationContext(), imageUri);
            //Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_LONG).show();
            Log.d("이미지 경로", imagePath);
            Log.d("이미지 경로", data.getData().toString());
            sendImage();
        }
    }

    final int REQUESTCODE2 = 151;

    private void requestPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            // if(ActivityCompat.)
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUESTCODE2);
        }

    }

    //new 절대경로
    public String getPathEugene(Context context, Uri uri){
        String filename = "unknown";
        Uri filePathUri = uri;

        if(uri.getScheme().toString().compareTo("content") == 0){
            Cursor cursor = context.getContentResolver().query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                int columnIndex  = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(columnIndex));
                filename = filePathUri.getLastPathSegment().toString();
            }
            cursor.close();
        }
        else if(uri.getScheme().compareTo("file") == 0){
            filename = filePathUri.getLastPathSegment().toString();
        }
        else{
            filename = filename + "_" + filePathUri.getLastPathSegment();
        }
        //return filename;
        return filePathUri.getPath().toString();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        requestPermission();

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_profileedit);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        //로그아웃
        TextView tvLogout = (TextView)findViewById(R.id.tv_logout_profileedit);
        //TODO: 토큰 삭제하기

        //프로필 이미지 선택
        ivProfile = (ImageView)findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, PROFILADD_IMAGE);
            }
        });
        //이메일
        tv_email_profile = findViewById(R.id.tv_email_profile);

        //닉네임
        tvProfile = (TextView) findViewById(R.id.tv_nickname_profile) ;

        //시/도
        etState = (EditText)findViewById(R.id.et_state_profile);

        //시/군/구
        etCounty = (EditText)findViewById(R.id.et_county_profile);

        //대학교
        etUniv = (EditText)findViewById(R.id.et_univ_prifile);

        //전공
        etMjor=(EditText)findViewById(R.id.et_major_profile);

        //학년
        etGrade= (EditText)findViewById(R.id.et_grade_profile);

        //개인성향 이벤트 처리
        //아침형
        tvMorning = (TextView) findViewById(R.id.tv_morning_profile);
        tvMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morningCount++;
                if(morningCount %2 == 1){
                    tvMorning.setTextColor(Color.WHITE);
                    tvMorning.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvMorning.setTextColor(Color.parseColor("#c5ccd6"));
                    tvMorning.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //저녁
        tvNight = (TextView) findViewById(R.id.tv_night_profile);
        tvNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nightCount++;
                if(nightCount %2 == 1){
                    tvNight.setTextColor(Color.WHITE);
                    tvNight.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvNight.setTextColor(Color.parseColor("#c5ccd6"));
                    tvNight.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //새벽
        tvDawn = (TextView) findViewById(R.id.tv_dawn_profile);
        tvDawn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dawnCount++;
                if(dawnCount %2 == 1){
                    tvDawn.setTextColor(Color.WHITE);
                    tvDawn.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvDawn.setTextColor(Color.parseColor("#c5ccd6"));
                    tvDawn.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //계획
        tvPlan = (TextView) findViewById(R.id.tv_plan_profile);
        tvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planCount++;
                if(planCount %2 == 1){
                    tvPlan.setTextColor(Color.WHITE);
                    tvPlan.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvPlan.setTextColor(Color.parseColor("#c5ccd6"));
                    tvPlan.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //몰입
        tvFocus = (TextView) findViewById(R.id.tv_focus_profile);
        tvFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusCount++;
                if(focusCount %2 == 1){
                    tvFocus.setTextColor(Color.WHITE);
                    tvFocus.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvFocus.setTextColor(Color.parseColor("#c5ccd6"));
                    tvFocus.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //리더
        tvLeader = (TextView) findViewById(R.id.tv_leader_profile);
        tvLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderCount++;
                if(leaderCount %2 == 1){
                    tvLeader.setTextColor(Color.WHITE);
                    tvLeader.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvLeader.setTextColor(Color.parseColor("#c5ccd6"));
                    tvLeader.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //팔로워
        tvFollow = (TextView) findViewById(R.id.tv_follow_profile);
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followCount++;
                if(followCount %2 == 1){
                    tvFollow.setTextColor(Color.WHITE);
                    tvFollow.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvFollow.setTextColor(Color.parseColor("#c5ccd6"));
                    tvFollow.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //도전
        tvChall = (TextView) findViewById(R.id.btv_challenge_profile);
        tvChall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challCount++;
                if(challCount %2 == 1){
                    tvChall.setTextColor(Color.WHITE);
                    tvChall.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvChall.setTextColor(Color.parseColor("#c5ccd6"));
                    tvChall.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //현실
        tvReal = (TextView) findViewById(R.id.tv_real_profile);
        tvReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realCount++;
                if(realCount %2 == 1){
                    tvReal.setTextColor(Color.WHITE);
                    tvReal.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvReal.setTextColor(Color.parseColor("#c5ccd6"));
                    tvReal.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });

        load();

        //완료 버튼 클릭 이벤트
        Button btnFinish = (Button)findViewById(R.id.btn_finish_profile);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String state = etState.getText().toString();
                String county = etCounty.getText().toString();
                String univ = etUniv.getText().toString();
                String major = etMjor.getText().toString();
                int grade = Integer.parseInt(etGrade.getText().toString());
                boolean moring = (morningCount%2 !=0);
                boolean night = (nightCount%2 != 0);
                boolean dawn = (dawnCount%2 != 0);
                boolean plan=(planCount%2 != 0);
                boolean focus=(focusCount%2 != 0);
                boolean leader=(leaderCount%2 != 0);
                boolean follow=(followCount %2 != 0);
                boolean chall=(challCount%2 != 0);
                boolean real=(realCount%2 != 0);
                Log.d("값: ", state+county+univ+major+String.valueOf(grade)+String.valueOf(moring));
                Log.d("값: ",  String.valueOf(night)+String.valueOf(chall));



                //이미지 제외 나머지 데이터 전송
                ProfileEditInputData data = new ProfileEditInputData(
                        state, county, univ, major, grade, null,
                        moring, night, dawn, plan, focus, leader, follow, chall, real);
                sendData(data);
                //TODO: 이미지 전송

                finish();

            }
        });

    }

    //유저 정보 불러오기
    private void load(){
        //retrofilt2 연결
        service1 = RetrofitClient.getClient().create(RetrofitService.class);
        Call<ProfileEditResponse> call = service1.userProfile(USER_TOKEN, "application/json");

        call.enqueue(new Callback<ProfileEditResponse>() {
            @Override
            public void onResponse(Call<ProfileEditResponse> call, Response<ProfileEditResponse> response) {
                if (response.isSuccessful()) {
                    ProfileEditResponse result = response.body();
                    if (result.getStatus() == 200) {
                        //Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                        if(result.getData().getImg() != null && !result.getData().getImg().equals("")){
                            Glide.with(getApplicationContext()).load(result.getData().getImg()).circleCrop().into(ivProfile);
                        }
                        tvProfile.setText(result.getData().getName());
                        tv_email_profile.setText(result.getData().getEmail());
                        etState.setText(result.getData().getLargeAddress());
                        etCounty.setText(result.getData().getSmallAddress());
                        etUniv.setText(result.getData().getUniv());
                        etMjor.setText(result.getData().getMajor());
                        etGrade.setText(String.valueOf(result.getData().getGrade()));
                        morningCount = btnColor(result.getData().getMorning(), tvMorning);
                        nightCount = btnColor(result.getData().getNight(), tvNight);
                        dawnCount = btnColor(result.getData().getDawn(), tvDawn);
                        planCount = btnColor(result.getData().getPlan(), tvPlan);
                        focusCount = btnColor(result.getData().getCramming(), tvFocus);
                        leaderCount = btnColor(result.getData().getLeader(), tvLeader);
                        followCount = btnColor(result.getData().getFollower(), tvFollow);
                        challCount = btnColor(result.getData().getChallenge(), tvChall);
                        realCount = btnColor(result.getData().getRealistic(), tvReal);
                    }
                }
                else {
                    //Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileEditResponse> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //개인 성향 버튼 색 변경
    protected int btnColor(boolean selected, TextView tv){
        int count = 0;
        if(selected ==true){
            count = 1;
            tv.setTextColor(Color.WHITE);
            tv.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

        }
        else{
            count = 0;
            tv.setTextColor(Color.parseColor("#c5ccd6"));
            tv.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
        }
        return count;
    }



    //프로필 수정 정보 보내기
    public void sendData(ProfileEditInputData data){
        service2 = RetrofitClient.getClient().create(RetrofitService.class);
        Call<ProfileEditInputResponse> call = service2.profileEdit(USER_TOKEN, data);
        call.enqueue(new Callback<ProfileEditInputResponse>() {
            @Override
            public void onResponse(Call<ProfileEditInputResponse> call, Response<ProfileEditInputResponse> response) {
                if (response.isSuccessful()) {
                    //메인 스레드에서 작업하는 부분 UI 작업 가능
                    ProfileEditInputResponse result = response.body();
                    if (result.getStatus() == 201) {
                        //Toast.makeText(getApplicationContext(), String.valueOf(result.getStatus()), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d("오류 출력", response.message());
                    //Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileEditInputResponse> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO: 프로필 이미지 수정 보내기
    //이미지 전송하기
    private void sendImage(){
        //retrofilt2 연결
        service1 = RetrofitClient.getClient().create(RetrofitService.class);
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);//"image/*""multipart/form-data"
        //MultipartBody.Part body = MultipartBody.Part.create(requestFile);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));


        Call<ProfileImageResponse> call = service1.profileImage(USER_TOKEN,  body);
        call.enqueue(new Callback<ProfileImageResponse>() {
            @Override
            public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                if (response != null && response.isSuccessful()) {
                    //메인 스레드에서 작업하는 부분 UI 작업 가능
                    ProfileImageResponse result = response.body();
                    if (result.getStatus() == 201) {
                        //Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                        Log.d("plz complete", response.body().getData().getImg());
                    }
                    else{
                        //Tost.makeText(getApplicationContext(), result.getStatus().toString(), Toast.LENGTH_SHORT).show();
                        Log.d("error code", response.headers().toString());
                    }
                }
                else if(response == null){
                    //Toast.makeText(getApplicationContext(), "널 값임", Toast.LENGTH_LONG).show();
                    Log.d("사진 없음",  response.headers().toString());
                }
                else {
                    Log.d("연결 실패",  response.headers().toString());
                    Log.d("연결 실패",  response.toString());
                }
            }

            @Override
            public void onFailure(Call<ProfileImageResponse> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });

    }



    //툴바
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}