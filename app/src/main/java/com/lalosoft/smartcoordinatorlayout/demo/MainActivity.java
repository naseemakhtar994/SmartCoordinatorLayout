package com.lalosoft.smartcoordinatorlayout.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lalosoft.smartcoordinatorlayout.SmartCoordinatorLayout;
import com.lalosoft.smartcoordinatorlayout.components.recyclerview.SmartRecyclerView;
import com.lalosoft.smartcoordinatorlayout.demo.simple.SimpleSmartComponentsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ITEM_SIMPLE_SMART_COMPONENTS = 0;
    private static final int ITEM_COMPLEX_SMART_COMPONENTS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        // bind the root of view of this activity
        ViewGroup rootView = (ViewGroup) findViewById(R.id.activity_base_root);

        CustomSmartRecyclerView smartRecyclerView = new CustomSmartRecyclerView();

        // build SmartCoordinatorLayout
        SmartCoordinatorLayout
                smartCoordinatorLayout = new SmartCoordinatorLayout.Builder(this)
                .buildWithView(rootView)
                .addSmartComponent(smartRecyclerView)
                .build();

        smartCoordinatorLayout.setup();
    }

    private List<String> createStringList() {
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.simple_smart_components));
        list.add(getString(R.string.complex_smart_components));
        return list;
    }

    private void openActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    private class CustomSmartRecyclerView extends SmartRecyclerView {

        @Override
        protected RecyclerView.Adapter provideAdapter() {
            return new MainAdapter(createStringList(), new OnItemSelectedListener() {
                @Override
                public void onItemClick(int position) {
                    switch (position) {
                        case ITEM_SIMPLE_SMART_COMPONENTS:
                            openActivity(SimpleSmartComponentsActivity.class);
                            break;
                        case ITEM_COMPLEX_SMART_COMPONENTS:
                            // TODO
                            break;
                    }
                }
            });
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        private final OnItemSelectedListener listener;
        private List<String> list;

        public MainAdapter(List<String> list, OnItemSelectedListener listener) {
            this.list = list;
            this.listener = listener;
        }

        @Override
        public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_item, parent, false);
            return new MainAdapter.MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MainAdapter.MainViewHolder holder, int position) {
            holder.text.setText(list.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MainViewHolder extends RecyclerView.ViewHolder {
            TextView text;

            public MainViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.item_text);
            }
        }
    }
}
