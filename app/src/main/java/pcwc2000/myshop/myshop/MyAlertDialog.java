package pcwc2000.myshop.myshop;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by pichet_w on 1/3/2559.
 */
public class MyAlertDialog {
    public void myDialog(Context context, String strTitle, String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon((R.drawable.icon_question));
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
        //builder.setPositiveButton("OK", new DialogInterface.OnClickListener()){

        //}
        //        builder.setPositiveButton("OK", new DialogInterfa)
        //public void onc
    }
}   //MyAlertDialog
