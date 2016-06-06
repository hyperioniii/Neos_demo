package neos.haihack.com.demoneos.touchImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import neos.haihack.com.demoneos.R;


public class MainActivityTouchImage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchimge);
        findViewById(R.id.single_touchimageview_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivityTouchImage.this, SingleTouchImageViewActivity.class));
			}
		});
        findViewById(R.id.viewpager_example_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivityTouchImage.this, ViewPagerExampleActivity.class));
			}
		});
        findViewById(R.id.mirror_touchimageview_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivityTouchImage.this, MirroringExampleActivity.class));
			}
		});
        findViewById(R.id.switch_image_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivityTouchImage.this, SwitchImageExampleActivity.class));
			}
		});
        findViewById(R.id.switch_scaletype_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivityTouchImage.this, SwitchScaleTypeExampleActivity.class));
			}
		});
    }
}
