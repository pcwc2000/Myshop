package pcwc2000.myshop.myshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request Database
        myManage = new MyManage(this);

        //test add valu
        testAddValue();
    }   //Main Method

    private void testAddValue() {
        myManage.addUser("testUser", "1234", "โดรามอน");
        myManage.addFood("ไข่เจียว", "100", "urlFood");

    }

}   //Main Class
