package com.example.timmo_songjas.feature.project;

import androidx.annotation.Nullable;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProjectAdd1Response;
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

import static com.example.timmo_songjas.feature.utils.CommonValues.PROADD_IMAGE;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProjectAdd1Activity extends AppCompatActivity {

    Spinner spType;
    String typeString;
    Spinner spField;
    String fieldString;
    String[] type = {"공모전", "해커톤", "교내 팀플", "졸업작품", "경진대회", "유형을 입력하세요."};
    String[] field = {"경영", "경제", "디자인", "IT", "무역", "홍보/마케팅", "유형을 입력하세요."};
    boolean isTime;
    ImageView ivImage;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    Bitmap img;
    Uri imageUri;
    String imagePath;

    RetrofitService service1;

    //이미지 파일 열기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROADD_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    imageUri = data.getData();
                    in.close();

                    // 이미지 표시
                    ivImage.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            imagePath = getPathEugene(getApplicationContext(), imageUri);
            Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_LONG).show();
            Log.d("이미지 경로", imagePath);
            Log.d("이미지 경로", data.getData().toString());
            sendImage();
        }
    }

    //권한 받기
    final int REQUESTCODE = 150;
    private void requestPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUESTCODE);
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_add1);

        requestPermission();

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timmoadd1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        //이미지 가져오기
        ivImage = (ImageView)findViewById(R.id.iv_image_project);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, PROADD_IMAGE);
            }
        });

        //제목 입력 받기
        EditText etTitle = (EditText) findViewById(R.id.et_title_project);

        //스피너구현(유형)
        spType = (Spinner)findViewById(R.id.sp_type_project);
        ArrayAdapter<String> typeAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, type){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("유형을 입력하세요"); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        typeAapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spType.setAdapter(typeAapter);
        spType.setSelection(typeAapter.getCount());

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeString = type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //스피너구현(분야)
        spField = (Spinner)findViewById(R.id.sp_field_project);
        ArrayAdapter<String> feildAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, field){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("유형을 입력하세요"); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        feildAapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spField.setAdapter(feildAapter);
        spField.setSelection(feildAapter.getCount());

        spField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldString = field[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //모집기간 입력 받기
        EditText etTerm = (EditText)findViewById(R.id.et_term_project);

        //모집 내용 입력
        EditText etContent = (EditText)findViewById(R.id.et_collect_project);

        //다음 버튼 눌러 데이터 전송하기
        Button nextBtn = (Button)findViewById(R.id.btn_next_project);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProjectAdd2Activity.class);
                //입력 데이터 유효성 검사
                try {
                    //보낼 데이터
                    intent.putExtra("title", etTitle.getText().toString());
                    intent.putExtra("type", typeString);
                    intent.putExtra("feild", fieldString);
                    String[] term = etTerm.getText().toString().split("~");
                    intent.putExtra("startDate", term[0]);
                    intent.putExtra("endDate", term[1]);
                    intent.putExtra("content", etContent.getText().toString());

                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Log.d("에러", e.toString());
                }

            }
        });
    }

    //이미지 전송
    private void sendImage(){
        //retrofilt2 연결
        service1 = RetrofitClient.getClient().create(RetrofitService.class);
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);//"image/*""multipart/form-data"
        //MultipartBody.Part body = MultipartBody.Part.create(requestFile);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));


        Call<ProjectAdd1Response> call = service1.projectImage(USER_TOKEN,  body);
        call.enqueue(new Callback<ProjectAdd1Response>() {
            @Override
            public void onResponse(Call<ProjectAdd1Response> call, Response<ProjectAdd1Response> response) {
                if (response != null && response.isSuccessful()) {
                    //메인 스레드에서 작업하는 부분 UI 작업 가능
                    ProjectAdd1Response result = response.body();
                    if (result.getStatus() == 201) {
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                        Log.d("plz complete", response.body().getData().getImg());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), result.getStatus().toString(), Toast.LENGTH_SHORT).show();
                        Log.d("error code", response.headers().toString());
                    }
                }
                else if(response == null){
                    Toast.makeText(getApplicationContext(), "널 값임", Toast.LENGTH_LONG).show();
                    Log.d("사진 없음",  response.headers().toString());
                }
                else {
                    Log.d("연결 실패",  response.headers().toString());
                    Log.d("연결 실패",  response.toString());
                }
            }

            @Override
            public void onFailure(Call<ProjectAdd1Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
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