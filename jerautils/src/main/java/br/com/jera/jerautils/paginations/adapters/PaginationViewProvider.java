package br.com.jera.jerautils.paginations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import br.com.jera.jerautils.paginations.Paginator;
import br.com.jera.jerautils.paginations.interfaces.PaginationError;

/**
 * Created by daividsilverio on 01/12/16.
 */

public interface PaginationViewProvider<LVH extends RecyclerView.ViewHolder, EVH extends RecyclerView.ViewHolder> {

    LVH onCreateLoadingViewHolder(ViewGroup parent);

    EVH onCreateErrorViewHolder(ViewGroup parent);

    void onBindLoadingViewHolder(LVH holder);

    void onBindErrorViewHolder(EVH holder, PaginationError position, Paginator.TryAgainCallback errorCallback);

}