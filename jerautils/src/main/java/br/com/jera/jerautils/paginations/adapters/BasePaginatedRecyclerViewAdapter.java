package br.com.jera.jerautils.paginations.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class BasePaginatedRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_LOADING = Integer.MAX_VALUE / 2;

    private final RecyclerView.Adapter wrappedAdapter;
    private boolean isLoading = false;

    public BasePaginatedRecyclerViewAdapter(@NonNull RecyclerView.Adapter wrappedAdapter) {
        super();
        this.wrappedAdapter = wrappedAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            //call something to create a loading view
            return null;
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadingView(position)) {
            //call something to
        } else {
            wrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return wrappedAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadingView(position)) {
            return VIEW_TYPE_LOADING;
        } else {
            return wrappedAdapter.getItemViewType(position);
        }
    }

    public boolean isLoadingView(int position) {
        return isLoading && position == getLoadingViewPosition();
    }

    private int getLoadingViewPosition() {
        return isLoading ? getItemCount() - 1 : -1;
    }
}
