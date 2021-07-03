package com.example.versioncheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private Retrofit retrofit;

    int versionCode;
    String versionName;
    int m_versioncode;
    String m_versionname;
    String m_dialogtext;
    String m_url;
    private final String BASE_URL = "https://taewoooh88.cafe24.com/";


    private static ArrayList<Versionitem> itemArrayList;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Device();
        Maket("");


    }


    public void Maket(String tablecode) { // 서버 데이터를 가지고 온다 파라미터는 불러올 테이블 이름


        init();
        Version gitHub = retrofit.create(Version.class);
        Call<List<Versionitem>> call = gitHub.contributors(tablecode);
        call.enqueue(new Callback<List<Versionitem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            // 성공시
            public void onResponse(Call<List<Versionitem>> call, Response<List<Versionitem>> response) {
                List<Versionitem> contributors = response.body();
                // 받아온 리스트를 순회하면서
                //Log.e("Test8888", response.body().toString());

                for (Versionitem contributor : contributors) {

                    m_versioncode = contributor.versioncode;
                    m_versionname = contributor.versionname;
                    m_dialogtext = contributor.dialogtext;
                    m_url = contributor.url;


                    //Log.e("마켓 버전체크", "" + m_versioncode + " / " + m_versionname + " / " + m_dialogtext + " / " + m_url);


                }

                if (versionCode != m_versioncode) {

                    ShowDialog();
                    Log.e("업데이트 필요", "" + "디바이스 버전 : " + versionCode + " / " + "마켓 버전 : " + m_versioncode);


                } else {

                    Log.e("업데이트 불필요", "" + "디바이스 버전 : " + versionCode + " / " + "마켓 버전 : " + m_versioncode);
                }


            }

            @Override
            // 실패시
            public void onFailure(Call<List<Versionitem>> call, Throwable t) {

                Log.d("deberg", "------->" + t.toString());
                Toast.makeText(MainActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                        .show();
            }
        });


    }


    public void Device() {


        PackageInfo pi = null;

        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }


        versionCode = pi.versionCode;
        versionName = pi.versionName;

        //Log.e("단말기 버전체크", "" + versionCode + " / " + versionName);


    }

    public void init() {



        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void ShowDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("알림");
        alertDialogBuilder.setMessage("새로운 버전이 있어요.");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("업데이트", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(m_url));
                startActivity(intent);
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }


}