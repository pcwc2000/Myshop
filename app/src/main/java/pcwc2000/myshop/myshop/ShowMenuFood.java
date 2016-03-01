package pcwc2000.myshop.myshop;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class ShowMenuFood extends AppCompatActivity {
    //Explicit
    private TextView showOfficerTextView;
    private Spinner deskSpinner;
    private ListView foodListView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu_food);

        //Bind Widget
        bindWidget();

        createFoodListView();



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } //Main Method

    private void createFoodListView() {
        // Read all Data from SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE,null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+MyManage.food_table,null);
        cursor.moveToFirst();

        String[] foodStrings = new String[cursor.getCount()];
        String[] priceStrings = new String[cursor.getCount()];
        String[] sourceStrings = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {

            foodStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_food));
            priceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_price));
            sourceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_source));

            cursor.moveToNext();
        }   //for
        cursor.close();



    }   //createFoodListView

    private void bindWidget() {
        showOfficerTextView = (TextView) findViewById(R.id.textView2);
        deskSpinner = (Spinner) findViewById(R.id.spinner);
        foodListView = (ListView) findViewById(R.id.listView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShowMenuFood Page", // TODO: Define a title for the content shown.
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
                "ShowMenuFood Page", // TODO: Define a title for the content shown.
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
