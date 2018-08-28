package com.example.admin.myapplication.RecyclerViewClasses;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class PageScrollListner extends RecyclerView.OnScrollListener {

    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    int pastVisibleItems;

    public PageScrollListner(LinearLayoutManager linearLayoutManager)
    {
        this.linearLayoutManager=linearLayoutManager;
    }

    public PageScrollListner(GridLayoutManager gridLayoutManager)
    {
        this.gridLayoutManager=gridLayoutManager;
    }

    public PageScrollListner(StaggeredGridLayoutManager staggeredGridLayoutManager)
    {
        this.staggeredGridLayoutManager=staggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = staggeredGridLayoutManager.getChildCount();
        int totalItemCount = staggeredGridLayoutManager.getItemCount();
        int[] firstVisibleItems = null;
        firstVisibleItems= staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);

        if(firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount)
            {
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
