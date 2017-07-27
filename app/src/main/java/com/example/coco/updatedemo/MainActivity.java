package com.example.coco.updatedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String apkUrl = "https://guaju.github.io/versioninfo.json";
    private String ver;
    private String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button mBtn = (Button) findViewById(R.id.mBtn);

                checkUpDate();



    }

    private void checkUpDate() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(apkUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    pareseJson(json);
                }
            }
        });
    }

    private void pareseJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        Gson gson = new Gson();
        UpBean bean = gson.fromJson(json, UpBean.class);
        if ("200".equals(bean.getStatus())) {
            final UpBean.DataBean data = bean.getData();
            ver = data.getVersion();
            versionName = PackageUtils.getVersionName(MainActivity.this);

            if (!TextUtils.isEmpty(ver)) {
                if (!ver.equals(versionName)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                         //   DialogUtils.showUpDateDialog("更新提示", data.getNotes(), MainActivity.this);
                            DialogUtils.showUpDateDialog("更新提示",data.getInfo(),MainActivity.this,data.getAppurl());
                        }
                    });
                }
            }


        }

    }

}
