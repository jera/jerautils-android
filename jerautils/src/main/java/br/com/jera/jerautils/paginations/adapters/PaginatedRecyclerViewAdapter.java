package br.com.jera.jerautils.paginations.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import br.com.jera.jerautils.paginations.Paginator;
import br.com.jera.jerautils.paginations.interfaces.PaginationError;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class PaginatedRecyclerViewAdapter<T extends BaseRecyclerViewAdapter> extends RecyclerView.Adapter implements PaginatedAdapter {

    private static final int VIEW_TYPE_LOADING = Integer.MAX_VALUE / 2;
    private static final int VIEW_TYPE_ERROR = Integer.MAX_VALUE / 2 - 1;

    private final T wrappedAdapter;
    private final PaginationViewProvider provider;
    private boolean isLoading = false;
    private boolean hasError = false;
    private PaginationError error;
    private Paginator.TryAgainCallback errorCallback;

    public PaginatedRecyclerViewAdapter(@NonNull T wrappedAdapter, PaginationViewProvider provider) {
        super();
        this.wrappedAdapter = wrappedAdapter;
        this.provider = provider;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return provider.onCreateLoadingViewHolder(parent);
        } else if (viewType == VIEW_TYPE_ERROR) {
            return provider.onCreateErrorViewHolder(parent);
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_LOADING) {
            provider.onBindLoadingViewHolder(holder);
        } else if (viewType == VIEW_TYPE_ERROR) {
            provider.onBindErrorViewHolder(holder, error, errorCallback);
        } else {
            wrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return wrappedAdapter.getItemCount() + (hasExtraView() ? 1 : 0);
    }

    private boolean hasExtraView() {
        return isLoading || hasError;
    }

    @Override
    public int getItemViewType(int position) {
        if (isExtraView(position)) {
            if (isLoading) {
                return VIEW_TYPE_LOADING;
            } else {
                return VIEW_TYPE_ERROR;
            }
        } else {
            return wrappedAdapter.getItemViewType(position);
        }
    }

    public boolean isExtraView(int position) {
        return hasExtraView() && position == getExtraViewPosition();
    }

    private int getExtraViewPosition() {
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
        this.hasError = false;
    }

    public void setError(boolean hasError, PaginationError paginationError, Paginator.TryAgainCallback tryAgainCallback) {
        this.hasError = hasError;
        this.error = paginationError;
        this.errorCallback = tryAgainCallback;
        this.isLoading = false;
    }
}
