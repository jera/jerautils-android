package br.com.jera.jerautils.paginations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.jera.jerautils.R;
import br.com.jera.jerautils.paginations.Paginator;
import br.com.jera.jerautils.paginations.interfaces.PaginationError;

/**
 * Created by daividsilverio on 01/12/16.
 */

public class SimplePaginationViewProvider implements PaginationViewProvider<SimplePaginationViewProvider.LoadingViewHolder, SimplePaginationViewProvider.ErrorViewHolder> {

    @Override
    public LoadingViewHolder onCreateLoadingViewHolder(ViewGroup parent) {
        return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jera_utils_view_loading, parent, false));
    }

    @Override
    public ErrorViewHolder onCreateErrorViewHolder(ViewGroup parent) {
        return new ErrorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jera_utils_view_error, parent, false));
    }

    @Override
    public void onBindLoadingViewHolder(LoadingViewHolder holder) {

    }

    @Override
    public void onBindErrorViewHolder(ErrorViewHolder holder, PaginationError error, Paginator.TryAgainCallback errorCallback) {
        holder.format(error, errorCallback);
    }


    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ErrorViewHolder extends RecyclerView.ViewHolder {

        ErrorViewHolder(View itemView) {
            super(itemView);
        }

        protected void format(PaginationError error, final Paginator.TryAgainCallback callback) {
            ((TextView) itemView.findViewById(R.id.try_again_text)).setText(error.getErrorMessage());
            itemView.findViewById(R.id.try_again_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.tryAgain();
                    }
                }
            });
        }
    }
}
