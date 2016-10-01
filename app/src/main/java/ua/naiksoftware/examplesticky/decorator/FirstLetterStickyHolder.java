package ua.naiksoftware.examplesticky.decorator;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ua.naiksoftware.examplesticky.*;

/**
 * Draw first letter as sticky header
 *
 * Created by naik on 30.09.16.
 */

public class FirstLetterStickyHolder<T extends FirstLetterStickyHolder.ModelWithName> extends StickyDecorator.StickyViewHolder<T> {

    private View mRootView;
    private TextView mLetterTextView;

    public FirstLetterStickyHolder(Context context, @LayoutRes int layout) {
        mRootView = LayoutInflater.from(context).inflate(layout, null, false);
        mLetterTextView = (TextView) mRootView.findViewById(R.id.first_letter_text);
    }

    @Override
    protected View bindView(ModelWithName item) {
        String name = item.getName();
        if (name != null && name.length() > 0) mLetterTextView.setText(String.valueOf(name.charAt(0)));
        return mRootView;
    }

    @Override
    protected boolean isTheSameCategory(ModelWithName model, ModelWithName otherModel) {
        String name1 = model.getName();
        String name2 = otherModel.getName();
        if (name1 != null && !name1.isEmpty() && name2 != null && !name2.isEmpty()) {
            return name1.charAt(0) == name2.charAt(0);
        }
        return false;
    }

    public interface ModelWithName {
        String getName();
    }
}
