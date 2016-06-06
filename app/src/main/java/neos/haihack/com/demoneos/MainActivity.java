package neos.haihack.com.demoneos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import jp.taizan.android.vjap.VTextLayout;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import neos.haihack.com.demoneos.TouchGirdView.ClickImageGrid;
import neos.haihack.com.demoneos.touchImage.MainActivityTouchImage;

public class MainActivity extends AppCompatActivity {

    VTextLayout vTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        final EditText input = (EditText) findViewById(R.id.input);
        Button btn = (Button) findViewById(R.id.btnSubmit);
        Button btnNext = (Button) findViewById(R.id.btnNext);
        vTextLayout = (VTextLayout) findViewById(R.id.vTextLayout);
        vTextLayout.initContent("", "");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vTextLayout.initContent("", input.getText().toString().length() > 0 ? input.getText().toString() : "");
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoBookActivity.class);
                startActivity(i);
            }
        });

        Button btnGallery = (Button) findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhotoPickerActivity.class);
                PhotoPickerIntent.setPhotoCount(intent, 9);
                PhotoPickerIntent.setColumn(intent, 4);
                startActivityForResult(intent, 9);
            }
        });

        Button showG = (Button) findViewById(R.id.btn_zoom_img);
        showG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PhotoView.class);
//                PhotoPickerIntent.setPhotoCount(intent, 9);
//                PhotoPickerIntent.setColumn(intent, 4);
//                startActivityForResult(intent, 9);
                startActivity(i);
            }
        });


        Button btnDemoTouch = (Button) findViewById(R.id. btn_demo_touch);
        btnDemoTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityTouchImage.class);
                startActivityForResult(intent, 9);
            }
        });

        Button bbtn_swipe_img = (Button) findViewById(R.id. btn_swipe_img);
        bbtn_swipe_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClickImageGrid.class);
                startActivityForResult(intent, 9);
            }
        });
    }


}
