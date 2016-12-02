package br.com.jera.jerautils.paginations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by daividsilverio on 01/12/16.
 */

public interface PaginationViewProvider<VH extends RecyclerView.ViewHolder> {
    VH onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(VH holder, int position);
}