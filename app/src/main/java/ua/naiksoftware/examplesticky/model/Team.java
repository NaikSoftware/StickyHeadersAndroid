package ua.naiksoftware.examplesticky.model;

import android.support.annotation.DrawableRes;

import ua.naiksoftware.android.adapter.delegate.autobind.DrawableId;
import ua.naiksoftware.android.adapter.delegate.autobind.Text;
import ua.naiksoftware.android.model.BaseModel;
import ua.naiksoftware.android.model.Model;
import ua.naiksoftware.examplesticky.R;
import ua.naiksoftware.examplesticky.decorator.FirstLetterStickyHolder;

/**
 * Created by naik on 01.10.16.
 */

public class Team implements BaseModel, FirstLetterStickyHolder.ModelWithName {

    @Text(R.id.team_name)
    private String name;

    @DrawableId(R.id.team_avatar)
    @DrawableRes
    private int avatar;

    private long id;

    public Team(long id, String name, int avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAvatar() {
        return avatar;
    }
}
