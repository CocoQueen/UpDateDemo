package com.example.coco.updatedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by coco on 2017/7/21.
 */

public class DownLoader {

    public static void downLoadApk(Activity activity, String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = OkHttpUtils.getInstance().newCall(request);
        call.enqueue(new downLoadCallback(activity));

    }

    private static class downLoadCallback implements Callback {
        private Activity activity;

        private downLoadCallback(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
            if (response.isSuccessful()) {
                byte[] bytes = response.body().bytes();
                if (bytes != null && bytes.length > 0) {
                    File dir = new File(Environment.getExternalStorageDirectory() + "/update");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, "update.apk");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();
                    bytes = null;

                    InStallApk(activity, file);
                }

            }
        }

        private static void InStallApk(Activity activity, File file) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile("file://"+file.getAbsolutePath()),"application/vnd.android.package-archive");
            intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()),
                    "application/vnd.android.package-archive");
            activity.startActivity(intent);

        }
    }
}
