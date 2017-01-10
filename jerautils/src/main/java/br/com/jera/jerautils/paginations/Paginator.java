package br.com.jera.jerautils.paginations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.jera.jerautils.paginations.adapters.PaginatedAdapter;
import br.com.jera.jerautils.paginations.adapters.PaginatedRecyclerViewAdapter;
import br.com.jera.jerautils.paginations.adapters.PaginationViewProvider;
import br.com.jera.jerautils.paginations.adapters.SimplePaginationViewProvider;
import br.com.jera.jerautils.paginations.interfaces.DataSource;
import br.com.jera.jerautils.paginations.interfaces.DataSourceCallback;
import br.com.jera.jerautils.paginations.interfaces.PaginationError;
import br.com.jera.jerautils.paginations.interfaces.PaginationInfo;

/**
 * Created by daividsilverio on 30/11/16.
 */

public class Paginator extends RecyclerView.OnScrollListener implements DataSourceCallback {

    protected final static int DEFAULT_STARTING_PAGE = 1;
    protected final static int DEFAULT_PAGE_SIZE = 20;

    private final DataSource dataSource;
    private PaginationViewProvider viewProvider;
    private RecyclerView recyclerView;
    private Integer currentPage;
    private Integer pageSize;
    private boolean isLoading = false;
    private boolean hasError = false;

    private Paginator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static PaginatorBuilder with(DataSource dataSource) {
        return new PaginatorBuilder(dataSource);
    }

    public void start() {
        detachPaginator();
        PaginatedRecyclerViewAdapter recyclerViewAdapter = new PaginatedRecyclerViewAdapter(recyclerView.getAdapter(), viewProvider);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (shouldRequestMore()) {
                    paginate();
                }
            }
        });
        paginate();
    }

    private void paginate() {
        if (isLoading) return;
        isLoading = true;
        hasError = false;
        if (isWrappedAdapter()) {
            ((PaginatedRecyclerViewAdapter) recyclerView.getAdapter()).setLoading(true);
            recyclerView.getAdapter().notifyDataSetChanged();
            dataSource.fetchData(currentPage, pageSize, this);
        }
    }

    private boolean isWrappedAdapter() {
        return recyclerView.getAdapter() != null && recyclerView.getAdapter() instanceof PaginatedRecyclerViewAdapter;
    }

    private boolean shouldRequestMore() {
        if (isLoading || hasError) return false;
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition + visibleItemCount > totalItemCount ||
                    totalItemCount == 0) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public int getCurrentPage() {
        return currentPage;
    }

    @NonNull
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void onSuccess(@NonNull List items, @Nullable PaginationInfo paginationInfo) {
        isLoading = false;
        if (isWrappedAdapter()) {
            ((PaginatedRecyclerViewAdapter) recyclerView.getAdapter()).setLoading(isLoading);
        }
        if (!items.isEmpty()) {
            addItemsToAdapter(items);
            updatePaginationInfo(paginationInfo);
        } else {
            detachPaginator();
        }
    }

    @Override
    public void onFailure(PaginationError paginationError, @Nullable PaginationInfo paginationInfo) {
        isLoading = false;
        hasError = true;
        if (isWrappedAdapter()) {
            ((PaginatedRecyclerViewAdapter) recyclerView.getAdapter())
                    .setError(hasError, paginationError, new TryAgainCallback() {
                        @Override
                        public void tryAgain() {
                            paginate();
                        }
                    });
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void updatePaginationInfo(@Nullable PaginationInfo paginationInfo) {
        if (paginationInfo != null) {
            currentPage = paginationInfo.getNextPage();
            if (currentPage > paginationInfo.getTotalPages()) {
                detachPaginator();
            }
        } else {
            currentPage += 1;
        }
    }

    public void detachPaginator() {
        if (recyclerView != null) {
            recyclerView.clearOnScrollListeners();
            recyclerView.getAdapter().notifyDataSetChanged();
            if (recyclerView.getAdapter() instanceof PaginatedRecyclerViewAdapter) {
                PaginatedRecyclerViewAdapter adapter = (PaginatedRecyclerViewAdapter) recyclerView.getAdapter();
                recyclerView.swapAdapter(adapter.getWrappedAdapter(), false);
            }
        }
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        if (recyclerView != null) {
            if (recyclerView.getAdapter() instanceof PaginatedRecyclerViewAdapter) {
                return ((PaginatedRecyclerViewAdapter) recyclerView.getAdapter()).getWrappedAdapter();
            }
        }
        return null;
    }

    private void addItemsToAdapter(List items) {
        if (recyclerView != null && isWrappedAdapter()) {
            ((PaginatedRecyclerViewAdapter) recyclerView.getAdapter()).addItems(items);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public interface TryAgainCallback {
        public void tryAgain();
    }

    public static class PaginatorBuilder {
        Paginator paginator;

        public PaginatorBuilder(@NonNull DataSource dataSource) {
            paginator = new Paginator(dataSource);
        }

        public PaginatorBuilder fromPage(@NonNull Integer page) {
            this.paginator.currentPage = page;
            return this;
        }

        public PaginatorBuilder withPageSize(@NonNull Integer size) {
            this.paginator.pageSize = size;
            return this;
        }

        public PaginatorBuilder over(@NonNull RecyclerView recyclerView) {
            this.paginator.recyclerView = recyclerView;
            return this;
        }

        public PaginatorBuilder usingPaginationViewProvider(@NonNull PaginationViewProvider viewProvider) {
            paginator.viewProvider = viewProvider;
            return this;
        }

        @NonNull
        public Paginator build() {
            crashIfInvalid();
            fillDefaults();
            return paginator;
        }

        @NonNull
        public Paginator start() {
            Paginator newPaginator = build();
            newPaginator.start();
            return newPaginator;
        }

        private void crashIfInvalid() {
            if (paginator.recyclerView == null || paginator.recyclerView.getAdapter() == null) {
                throw new RuntimeException("You must the recyclerView with an Adapter to Paginate this");
            } else if (!(paginator.recyclerView.getAdapter() instanceof PaginatedAdapter)) {
                throw new RuntimeException("Your recyclerview adapter must implement PaginatedAdapter");
            }
            if (paginator.dataSource == null) {
                throw new RuntimeException("You must set a dataSource to Paginate something");
            }
        }

        private void fillDefaults() {
            if (paginator.viewProvider == null) {
                paginator.viewProvider = new SimplePaginationViewProvider();
            }
            if (paginator.currentPage == null) {
                paginator.currentPage = DEFAULT_STARTING_PAGE;
            }
            if (paginator.pageSize == null) {
                paginator.pageSize = DEFAULT_PAGE_SIZE;
            }
        }
    }

}
