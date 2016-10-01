package ua.naiksoftware.examplesticky.model;

import android.support.annotation.DrawableRes;

import ua.naiksoftware.android.model.Model;
import ua.naiksoftware.examplesticky.decorator.FirstLetterStickyHolder;

/**
 * Created by naik on 01.10.16.
 */

public class Team implements Model, FirstLetterStickyHolder.ModelWithName {

    private String name;
    private @DrawableRes int avatar;

    public Team(String name, int avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAvatar() {
        return avatar;
    }
}
