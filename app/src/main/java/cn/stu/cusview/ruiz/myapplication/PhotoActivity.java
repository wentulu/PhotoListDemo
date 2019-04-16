package cn.stu.cusview.ruiz.myapplication;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.stu.cusview.ruiz.myapplication.picture.adapter.SamplePictureListAdapter;
import cn.stu.cusview.ruiz.myapplication.picture.bean.PictureInfo;

public class PhotoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "PhotoActivity";

    private String[] PROJECTION = new String[]{
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.HEIGHT,
            MediaStore.Images.ImageColumns.WIDTH,
            MediaStore.Images.ImageColumns.ORIENTATION,
            MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC
    };

    private RecyclerView mRecyclerView;
    private SamplePictureListAdapter adapter;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initView();
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.pic_list_recycler);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        adapter = new SamplePictureListAdapter(null, this);
        mRecyclerView.setAdapter(adapter);
        imageView = findViewById(R.id.imageView);
    }


    /*===========================Loader回调区域=START=======================================*/
    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null, null);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        List<PictureInfo> dataList = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME);
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int widthIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH);
            int heightIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT);
            int orientationIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION);
            int thumbIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC);
            Log.e(TAG, "Picture Name" + cursor.getString(index) + " data=" + cursor.getString(dataIndex)
                    + " w*H=" + cursor.getInt(widthIndex) + "*" + cursor.getInt(heightIndex)
                    +" orientation="+cursor.getInt(orientationIndex)

                );
            PictureInfo pictureInfo = new PictureInfo();
            pictureInfo.setHeight(cursor.getInt(heightIndex));
            pictureInfo.setWidth(cursor.getInt(widthIndex));
            pictureInfo.setPicName(cursor.getString(index));
            pictureInfo.setPicUrl(cursor.getString(dataIndex));
            pictureInfo.setOrentation(cursor.getInt(orientationIndex));
            pictureInfo.setThumb(cursor.getString(thumbIndex));
            dataList.add(pictureInfo);
        }
        adapter.setData(dataList);
//        imageView.setImageBitmap(BitmapFactory.decodeFile(dataList.get(3).getPicUrl()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
    /*===========================Loader回调区域=END=======================================*/
}
