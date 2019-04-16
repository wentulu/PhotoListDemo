package cn.stu.cusview.ruiz.myapplication;

import android.os.Bundle;
import android.os.HandlerThread;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;

import cn.stu.cusview.ruiz.myapplication.adapter.RecyclerViewAdapter;
import cn.stu.cusview.ruiz.myapplication.decoration.ItemDivederDecoration;
import cn.stu.cusview.ruiz.myapplication.decoration.StaggedItemDecoration;


public class MainActivity extends AppCompatActivity {


    HandlerThread mHandlerThread;

    ;

    RecyclerView recycler_view,recycler_view1,recycler_view2,recycler_view3,recycler_view4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view1 = findViewById(R.id.recycler_view1);
        recycler_view2 = findViewById(R.id.recycler_view2);
        recycler_view3 = findViewById(R.id.recycler_view3);
        recycler_view4 = findViewById(R.id.recycler_view4);


        recycler_view.setLayoutManager(getStaggeredGridLayoutManager());
        recycler_view1.setLayoutManager(new LinearLayoutManager(this));
        recycler_view2.setLayoutManager(getStaggeredGridLayoutManager());
        recycler_view3.setLayoutManager(new LinearLayoutManager(this));
        recycler_view4.setLayoutManager(getStaggeredGridLayoutManager());

        recycler_view.addItemDecoration(new StaggedItemDecoration(this,StaggeredGridLayoutManager.VERTICAL));
        recycler_view1.addItemDecoration(new ItemDivederDecoration(this,ItemDivederDecoration.ORIENTATION_VERTICAL));
        recycler_view2.addItemDecoration(new StaggedItemDecoration(this,StaggeredGridLayoutManager.VERTICAL));
        recycler_view3.addItemDecoration(new ItemDivederDecoration(this,ItemDivederDecoration.ORIENTATION_VERTICAL));
        recycler_view4.addItemDecoration(new StaggedItemDecoration(this,StaggeredGridLayoutManager.VERTICAL));

        recycler_view.setAdapter(new RecyclerViewAdapter());
        recycler_view1.setAdapter(new RecyclerViewAdapter());
        recycler_view2.setAdapter(new RecyclerViewAdapter());
        recycler_view3.setAdapter(new RecyclerViewAdapter());
        recycler_view4.setAdapter(new RecyclerViewAdapter());
    }

    private StaggeredGridLayoutManager getStaggeredGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
