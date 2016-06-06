package neos.haihack.com.demoneos.TouchGirdView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridLayout;

/**
 * Created by LinNH on 6/6/2016.
 */
public class TouchgridDemo extends GridLayout {

    public TouchgridDemo(Context context) {
        super(context);
    }

    public TouchgridDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
