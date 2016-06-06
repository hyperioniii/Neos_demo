package neos.haihack.com.demoneos;

import java.util.ArrayList;
import java.util.List;

public class MyPhoto {
    private List<Integer> drawableRes = new ArrayList<>();

    public MyPhoto(List<Integer> drawableRes) {
        this.drawableRes = drawableRes;
    }

    public List<Integer> getInterests() {
        return drawableRes;
    }
}