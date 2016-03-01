package pcwc2000.myshop.myshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private MyManage myManage;

    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Widget
        initialWidget();

        //Request Database
        myManage = new MyManage(this);

        //test add valu
        //testAddValue();
        deleteAllSQLite();
        //sychorize

        synJSONtoSQLite();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }   //Main Method

    private void initialWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);


    }   //intialWidget

    private void synJSONtoSQLite() {
        //connected http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        int intTimes = 0;
        while (intTimes <= 1) {

            //1. Create InoutStream
            InputStream inputStream = null;
            String[] urlJSON = new String[2];
            urlJSON[0] = "http://swiftcodingthai.com/29feb/php_get_user_pw.php";
            urlJSON[1] = "http://swiftcodingthai.com/29feb/php_get_food_pw.php";
            HttpPost httpPost = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(urlJSON[intTimes]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (Exception e) {
                Log.d("benha", "InputStream ==>" + e.toString());

            }
            //2. Create JSON String
            String strJSON = null;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(strLine);
                }   //while
                inputStream.close();
                strJSON = stringBuilder.toString();

            } catch (Exception e) {
                Log.d("benja", "JSON Strint ==>" + e.toString());

            }

            //3. Update to SQLite
            try {
                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    switch (intTimes) {
                        case 0:
                            // For userTABLE
                            String strUser = jsonObject.getString(MyManage.column_user);
                            String strPass = jsonObject.getString(MyManage.column_pass);
                            String strName = jsonObject.getString(MyManage.column_name);

                            myManage.addUser(strUser, strPass, strName);

                            break;
                        case 1:
                            // For foodTABLE
                            String strFood = jsonObject.getString(MyManage.column_food);
                            String strPrice = jsonObject.getString(MyManage.column_price);
                            String strSource = jsonObject.getString(MyManage.column_source);
                            myManage.addFood(strFood, strPrice, strSource);

                            break;
                    }   //switch
                }   //for

            } catch (Exception e) {
                Log.d("benja", "Update ==>" + e.toString());
            }

            intTimes += 1;
        }   //while


    }   //synJSONtoSQLite

    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(myManage.food_table, null, null);
        sqLiteDatabase.delete(myManage.user_table, null, null);
    }

    private void testAddValue() {
        myManage.addUser("testUser", "1234", "โดรามอน");
        myManage.addFood("ไข่เจียว", "100", "urlFood");

    }

    public void clickLogin(View view) {
        String userString = userEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();

        //Check Zero
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            errorDialog("มีช่องว่าง", "หรุณากรอกให้ครบทุกช่อง");

        }else {
            //No Space
            checkUser(userString ,passwordString);

           // checkUserPassword(strUser, strPassword);

        }
    }   //ClickLogin

    private void checkUser(String userString, String passwordString) {
        try {
            String[] myResultStrings = myManage.searchUserPassword(userString);
            Log.d("banja", "Name =" + myResultStrings[3]);
            
            //check password
            if (passwordString.equals(myResultStrings[2])) {
                welcome(myResultStrings[3]);
            }else {
                errorDialog("Password false", "Please try again");
            }

        }catch (Exception e) {
            errorDialog("User False","Please Try Again"+ userString);

            //MyAlertDialog myAlertDialog = new MyAlertDialog();

        }

    }   //checkUser

    private void welcome(String myResultString) {
        Toast.makeText(MainActivity.this,"ยินดีตอนรับ" + myResultString,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, ShowMenuFood.class);
        intent.putExtra("Officer", myResultString);
        startActivity(intent);
        finish();
    }

    private void checkUserPassword(String strUser, String strPassword) {


    }   //checkUserPassword

    private void errorDialog(String strTitle, String strMessage) {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_question);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();


    }   //errorDialog

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://pcwc2000.myshop.myshop/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://pcwc2000.myshop.myshop/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}   //Main Class
