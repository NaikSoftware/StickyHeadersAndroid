package ua.naiksoftware.examplesticky.decorator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;
import ua.naiksoftware.android.util.LogHelper;
import static ua.naiksoftware.android.util.LogHelper.*;

public class StickyDecorator<T> extends RecyclerView.ItemDecoration {

    private static final String TAG = LogHelper.makeLogTag(StickyDecorator.class);

    private final SparseBooleanArray mOffsetIndex = new SparseBooleanArray();
    private final List<StickyArea<T>> mIndex = new ArrayList<>();
    private final Rect mOffsets;
    private StickyViewHolder<T> mStickyViewHolder;
    private Class<T> mStickyHolderClass;
    private DisplayMode mDisplayMode;

    private class StickyArea<I> {

        int startPos = 0;
        int endPos = 0;
        I item;

        @Override
        public String toString() {
            return "Item: " + item + " -> (" + startPos + ", " + endPos + ")";
        }
    }

    public enum DisplayMode {
        LEFT, TOP, BOTTOM, RIGHT
		}

    public StickyDecorator(DisplayMode displayMode, StickyViewHolder<T> stickyViewHolder, Class<T> itemClass) {
        mOffsets = new Rect();
        mDisplayMode = displayMode;
        mStickyViewHolder = stickyViewHolder;
        mStickyHolderClass = itemClass;
    }

    // Call it always
    public void notifyDataSetChanged(List<?> dataSet) {
        LOGD(TAG, "Update data set (size) -> " + dataSet.size());
        mOffsetIndex.clear();
        mIndex.clear();
        StickyArea<T> stickyArea = new StickyArea<T>();
        for (int i = 0; i < dataSet.size(); i++) {
            Object item = dataSet.get(i);
            if (item.getClass().isAssignableFrom(mStickyHolderClass)) {
                LOGD(TAG, "React on item -> " + item);
                mOffsetIndex.put(i, true);

                if (stickyArea.item != null && mStickyViewHolder.isTheSameCategory(stickyArea.item, (T) item)) {
                    stickyArea.item = (T) item;
                    stickyArea.endPos = i;
                    LOGD(TAG, "Same category: " + stickyArea.item + " and " + item);
                } else {
                    stickyArea = new StickyArea<T>();
                    stickyArea.item = (T) item;
                    stickyArea.startPos = i;
                    stickyArea.endPos = i;
                    mIndex.add(stickyArea);
                    LOGD(TAG, "New category: " + stickyArea.item);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        LinearLayoutManager layoutManager = parent.getLayoutManager() instanceof LinearLayoutManager ? ((LinearLayoutManager) parent.getLayoutManager()) : null;
        if (layoutManager == null) return;
        final int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        final int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int topOffset, bottomOffset;
        for (StickyArea<T> stickyArea : mIndex) {
            topOffset = bottomOffset = -1;
            if (stickyArea.startPos <= lastVisibleItemPosition && stickyArea.endPos >= firstVisibleItemPosition) {

//                for (int j = 0; j < layoutManager.getChildCount(); j++) {
                for (int j = 0; j < parent.getChildCount(); j++) {
//                    final View child = layoutManager.getChildAt(j);
                    final View child = parent.getChildAt(j);
                    final int adapterPosition = parent.getChildAdapterPosition(child);
                    if (stickyArea.startPos <= adapterPosition && stickyArea.endPos >= adapterPosition) {
                        if (child.getAlpha() == 0) break; // not visible yet
                        if (topOffset < 0) {
                            if (adapterPosition == stickyArea.startPos && child.getTop() > 0) {
                                topOffset = child.getTop() + (int) child.getTranslationY();
                            } else {
                                topOffset = (int) child.getTranslationY();
                            }
                        }
                        bottomOffset = child.getBottom() + (int) child.getTranslationY();
                    } else if (topOffset >= 0 && bottomOffset >= 0) {
                        break;
                    }
                }
                if (topOffset < 0) topOffset = 0;
                if (bottomOffset > 0) {
                    drawStickyItem(c, parent, topOffset, bottomOffset, stickyArea);
                }
            }
        }
    }

    private void drawStickyItem(Canvas c, RecyclerView parent, int topOffset, int bottomOffset, StickyArea<T> stickyArea) {
        mStickyViewHolder.prepareView(stickyArea.item, mOffsets, mDisplayMode);
        int measuredHeight = mStickyViewHolder.getMeasuredHeight();
        int top;
        if (bottomOffset - topOffset < measuredHeight) {
            top = bottomOffset - measuredHeight;
        } else {
            top = topOffset;
        }
        c.save();
        c.translate(0, top);
        mStickyViewHolder.draw(c);
        c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOffsetIndex.get(parent.getChildAdapterPosition(view))) {
            outRect.set(mStickyViewHolder.getItemOffsets());
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    public static abstract class StickyViewHolder<T> {

        private int mMeasuredHeight;
        private View mRootView;
        private Rect mOffsets = new Rect();

        private void measure(View rootView, DisplayMode displayMode, Rect offsets) {
            int widthSpec = LayoutParams.WRAP_CONTENT;
            int heightSpec = LayoutParams.WRAP_CONTENT;
//            switch (displayMode) {
//                case LEFT:
//                case RIGHT:
//                    widthSpec = MeasureSpec.makeMeasureSpec(offsets.width(), MeasureSpec.EXACTLY);
//                    heightSpec = LayoutParams.WRAP_CONTENT;
//                    break;
//                case TOP:
//                case BOTTOM:
//                    widthSpec = LayoutParams.WRAP_CONTENT;
//                    heightSpec = MeasureSpec.makeMeasureSpec(offsets.height(), MeasureSpec.EXACTLY);
//                    break;
//            }
            rootView.measure(widthSpec, heightSpec);
            mMeasuredHeight = rootView.getMeasuredHeight();
            int measuredWidth = rootView.getMeasuredWidth();
            rootView.layout(0, 0, measuredWidth, mMeasuredHeight);
            mOffsets.set(0, 0, 0, 0);
            switch (displayMode) {
                case LEFT: mOffsets.left = measuredWidth; break;
                case RIGHT: mOffsets.right = measuredWidth; break;
                case TOP: mOffsets.top = mMeasuredHeight; break;
                case BOTTOM: mOffsets.bottom = mMeasuredHeight; break;
            }
        }

        public int getMeasuredHeight() {
            return mMeasuredHeight;
        }

        private void prepareView(T item, Rect offsets, DisplayMode displayMode) {
//            if (mItem != null && item != null && item.isTheSameCategory(mItem)) return;
            mRootView = bindView(item);
            measure(mRootView, displayMode, offsets);
        }

        private Rect getItemOffsets() {
			return mOffsets;
        }

        protected abstract View bindView(T item);

        protected abstract boolean isTheSameCategory(T model, T otherModel);

        private void draw(Canvas c) {
            if (mRootView != null) mRootView.draw(c);
        }
    }
}
