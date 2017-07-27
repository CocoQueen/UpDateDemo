package com.example.coco.updatedemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by coco on 2017/7/21.
 * 对话框的工具类
 */

public class DialogUtils {

    public static void showUpDateDialog(String title, String message, final Activity activity, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downLoadAndInstall(activity, url);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static void downLoadAndInstall(Activity activity, String url) {
        DownLoader.downLoadApk(activity, url);
    }
}
