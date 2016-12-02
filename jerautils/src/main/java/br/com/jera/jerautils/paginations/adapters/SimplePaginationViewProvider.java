package br.com.jera.jerautils.paginations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.jera.jerautils.R;

/**
 * Created by daividsilverio on 01/12/16.
 */

public class SimplePaginationViewProvider implements PaginationViewProvider<SimplePaginationViewProvider.LoadingViewHolder> {

    @Override
    public LoadingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jera_utils_view_loading, parent, false));
    }

    @Override
    public void onBindViewHolder(LoadingViewHolder holder, int position) {
        //just in case
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
