package cn.stu.cusview.ruiz.myapplication.picture.cache;

import android.graphics.Bitmap;

public class LruCache extends android.util.LruCache<String, Bitmap> {

    public LruCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {

        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }
}
