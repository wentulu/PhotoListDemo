package cn.stu.cusview.ruiz.myapplication.picture.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.stu.cusview.ruiz.myapplication.R;
import cn.stu.cusview.ruiz.myapplication.picture.bean.PictureInfo;

public class SamplePictureListAdapter extends BasePictureListAdapter<PictureInfo> {
    private static final String TAG = "SamplePictureListAdapte";

    private ExecutorService mExecutorService;
    Context mContext;
    int displayWith;

    public SamplePictureListAdapter(List<PictureInfo> dataList, Context context) {
        super(dataList);
        mContext = context;
        displayWith = mContext.getResources().getDisplayMetrics().widthPixels/4;
        mExecutorService = Executors.newFixedThreadPool(4);
    }

    @Override
    protected RecyclerView.ViewHolder inflateViewHolder(ViewGroup viewGroup, int i) {
        return new SampleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.picture_list_sample_item, viewGroup, false));
    }


    public void setData(List<PictureInfo> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(RecyclerView.ViewHolder viewHolder, int index, PictureInfo pictureInfo) {
        SampleViewHolder holder = (SampleViewHolder) viewHolder;
//        Bitmap bitmap = mLruCache.get(pictureInfo.getPicUrl());
//        if (bitmap == null) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 5;
//            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
//            bitmap = BitmapFactory.decodeFile(pictureInfo.getPicUrl(), options);
//            mLruCache.put(pictureInfo.getPicUrl(), bitmap);
//        }
//        Log.e(TAG, "original =" + pictureInfo.getWidth() + "*" + pictureInfo.getHeight());
//        Log.e(TAG, "bitmap =" + bitmap.getWidth() + "*" + bitmap.getHeight());
        CardView.LayoutParams params = (CardView.LayoutParams) holder.mImageView.getLayoutParams();
        if (pictureInfo.getOrentation() != 0) {
            params.height = (int) (pictureInfo.getWidth() * ((float) displayWith / pictureInfo.getHeight()));
            params.width = displayWith;
        } else {
            params.height = (int) (pictureInfo.getHeight() * ((float) displayWith / pictureInfo.getWidth()));
            params.width = displayWith;
        }
//        Matrix matrix = new Matrix();
//        matrix.postRotate(pictureInfo.getOrentation());
//        Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
//        Log.e(TAG, "res =" + res.getWidth() + "*" + res.getHeight());
        holder.mImageView.setLayoutParams(params);
        mExecutorService.execute(new ImageLoaderRunnable(holder.mImageView,pictureInfo.getPicUrl(),pictureInfo));
    }


    class SampleViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public SampleViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            mImageView = view.findViewById(R.id.pic_list_sample_item);
        }

    }



    class ImageLoaderRunnable implements Runnable {

        private volatile ImageView mImageView;
        private volatile String url;
        private PictureInfo pictureInfo;

        public ImageLoaderRunnable(ImageView imageView, String url, PictureInfo pictureInfo) {
            mImageView = imageView;
            this.url = url;
            this.pictureInfo = pictureInfo;
            imageView.setImageResource(R.mipmap.icon_loading);
            imageView.setTag(url);
        }

        @Override
        public void run() {
            Bitmap bitmap = mLruCache.get(pictureInfo.getPicUrl());
            if (bitmap == null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                bitmap = BitmapFactory.decodeFile(pictureInfo.getPicUrl(), options);
                mLruCache.put(pictureInfo.getPicUrl(), bitmap);
            }
            Log.e(TAG, "original =" + pictureInfo.getWidth() + "*" + pictureInfo.getHeight());
            Log.e(TAG, "bitmap =" + bitmap.getWidth() + "*" + bitmap.getHeight());
            Matrix matrix = new Matrix();
            matrix.postRotate(pictureInfo.getOrentation());
            Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            Log.e(TAG, "res =" + res.getWidth() + "*" + res.getHeight());
            if (url.equalsIgnoreCase((String) mImageView.getTag())){
                Message message = Message.obtain();
                message.what = 1;
                message.obj = res;
                mHandler.sendMessage(message);
            }
        }

        Handler mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        mImageView.setImageBitmap((Bitmap) msg.obj);
                        break;
                }
            }
        };
    }

}
