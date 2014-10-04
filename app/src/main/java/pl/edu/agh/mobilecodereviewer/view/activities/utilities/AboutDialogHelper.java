package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import pl.edu.agh.mobilecodereviewer.R;

public class AboutDialogHelper {

    public static void showDialog(Activity activity){
            LayoutInflater li = LayoutInflater.from(activity);
            View aboutView = li.inflate(R.layout.layout_about, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

            alertDialogBuilder.setView(aboutView);

            PackageInfo pinfo = null;
            try {
                pinfo = activity.getApplicationContext().getPackageManager().getPackageInfo(activity.getApplicationContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if(pinfo != null) {
                int versionCode = pinfo.versionCode;
                String versionName = pinfo.versionName;

                TextView versionNameView = (TextView) aboutView.findViewById(R.id.versionName);
                TextView versionCodeView = (TextView) aboutView.findViewById(R.id.versionCode);

                versionNameView.setText(versionName);
                versionCodeView.setText(String.valueOf(versionCode));

            }

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
    }

}
