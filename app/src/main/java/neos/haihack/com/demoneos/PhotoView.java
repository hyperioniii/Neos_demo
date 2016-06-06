package neos.haihack.com.demoneos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import neos.haihack.com.demoneos.adapter.PhotoAdapter;

/**
 * Created by LinNH on 5/31/2016.
 */
public class PhotoView extends Activity {
    public RecyclerView mRecyclerView;
    public GridLayoutManager mLayoutManager;
    public List<String> listImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        creatData();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 3);
//        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position == 0 ? 2 : 1;
//            }
//        });

        mRecyclerView.setLayoutManager(mLayoutManager);

        PhotoAdapter adapter = new PhotoAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setData(listImage);


    }

    public void creatData() {
        for (int i = 0; i < 30; i++) {
            if(i%2==0){
                listImage.add("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTtOJKsndFVg1nStYurZbpzITQc1m_VOw_Z5eWkK4F7vdQl5RGo");
            }else {
                listImage.add("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTpRMXgFXPGv_RcMJi0X2-2dafTvNOkNFYezZVOprzkXmiIN6c7C54cug");
            }
        }
    }

}
