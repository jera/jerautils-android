package br.com.jera.jerautils.paginations.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class PaginatedRecyclerViewAdapter<T extends BaseRecyclerViewAdapter> extends RecyclerView.Adapter implements PaginatedAdapter {

    private static final int VIEW_TYPE_LOADING = Integer.MAX_VALUE / 2;

    private final T wrappedAdapter;
    private final PaginationViewProvider provider;
    private boolean isLoading = false;

    public PaginatedRecyclerViewAdapter(@NonNull T wrappedAdapter, PaginationViewProvider provider) {
        super();
        this.wrappedAdapter = wrappedAdapter;
        this.provider = provider;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return provider.onCreateViewHolder(parent, viewType);
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PaginationViewProvider) {
            provider.onBindViewHolder(holder, position);
        } else {
            wrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return wrappedAdapter.getItemCount() + (isLoading ? 1 : 0);
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
        return getItemCount() > 0 ? getItemCount() - 1 : 0;
    }

    public T getWrappedAdapter() {
        return wrappedAdapter;
    }

    @Override
    public void addItems(List items) {
        wrappedAdapter.addItems(items);
        notifyDataSetChanged();
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }
}
