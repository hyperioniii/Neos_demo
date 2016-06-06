package neos.haihack.com.demoneos;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yalantis.flipviewpager.adapter.BaseFlipAdapter;
import com.yalantis.flipviewpager.utils.FlipSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PhotoBookActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_book);
        final ListView photos = (ListView) findViewById(R.id.myPhotos);

        List<MyPhoto> myPhotos = new ArrayList<>();
        myPhotos.add(new MyPhoto(new ArrayList<>(Arrays.asList(
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a8,
                R.drawable.a7, R.drawable.a4, R.drawable.a5, R.drawable.a6,
                R.drawable.a7, R.drawable.a8, R.drawable.a2, R.drawable.a3, R.drawable.a1))));

        FlipSettings settings = new FlipSettings.Builder().defaultPage(0).build();
        photos.setAdapter(new PhotosAdapter(this, myPhotos, settings));
        photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPhoto f = (MyPhoto) photos.getAdapter().getItem(position);
            }
        });
    }

    class PhotosAdapter extends BaseFlipAdapter {

        private List<MyPhoto> photos;

        public PhotosAdapter(Context context, List<MyPhoto> photos, FlipSettings settings) {
            super(context, photos, settings);
            this.photos = photos;
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Object friend1, Object friend2) {
            final FriendsHolder holder;
            if (convertView == null) {
                holder = new FriendsHolder();
                convertView = getLayoutInflater().inflate(R.layout.two_pages, parent, false);
                holder.leftImage = (ImageView) convertView.findViewById(R.id.first);
                holder.rightImage = (ImageView) convertView.findViewById(R.id.second);
                convertView.setTag(holder);
            } else {
                holder = (FriendsHolder) convertView.getTag();
            }
            holder.leftImage.setImageResource(((MyPhoto) friend1).getInterests().get(position * 2));
            holder.rightImage.setImageResource(((MyPhoto) friend1).getInterests().get(position * 2 + 1));
            return convertView;
        }

        @Override
        public int getPagesCount() {
            return photos.get(0).getInterests().size() / 2;
        }

        class FriendsHolder {
            ImageView leftImage;
            ImageView rightImage;
        }
    }
}