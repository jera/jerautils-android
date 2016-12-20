package br.com.jera.example.paginations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.jera.example.R;
import br.com.jera.jerautils.paginations.Paginator;
import br.com.jera.jerautils.paginations.adapters.PaginatedAdapter;


/**
 * Created by jera on 20/12/16.
 */

public class JavaPaginationExample extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Paginator paginator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagiantion_example);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleAdapter());
        paginator = Paginator.with(new FakeRequesterDataSource())
                .fromPage(1)
                .withPageSize(20)
                .over(recyclerView)
                .start();
    }

    public class SampleAdapter extends RecyclerView.Adapter<ViewHolder> implements PaginatedAdapter<Thing> {

        private ArrayList<Thing> things;

        public SampleAdapter() {
            things = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.format(things.get(position));
        }

        @Override
        public int getItemCount() {
            return things.size();
        }

        @Override
        public void addItems(List<Thing> list) {
            things.addAll(list);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void format(Thing thing) {
            ((TextView) itemView).setText(thing.getName());
        }
    }

}
