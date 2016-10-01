package ua.naiksoftware.examplesticky.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.naiksoftware.android.adapter.util.SimpleViewHolder;

/**
 * Simple universal ViewHolder
 *
 * Created by naik on 07.03.16.
 */
public class AdvancedViewHolder extends SimpleViewHolder {

    private final SparseArray<View> mViewSparseArray = new SparseArray<>();

    public AdvancedViewHolder(@LayoutRes int itemLayout, ViewGroup parent, LayoutInflater layoutInflater) {
        super(layoutInflater.inflate(itemLayout, parent, false));
    }

    public AdvancedViewHolder useView(@IdRes int viewId) {
        View v = itemView.findViewById(viewId);
        if (v == null) {
            throw new IllegalArgumentException("View with id=" + viewId
                    + " not found in this ViewHolder root view");
        }
        mViewSparseArray.put(viewId, v);
        return this;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        T view = (T) mViewSparseArray.get(viewId);
        if (view == null) {
            throw new IllegalArgumentException("View with id=" + viewId
                    + " not found, try call useView(viewId) before");
        }
        return view;
    }
}
