package ua.naiksoftware.examplesticky.adapter.action;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import ua.naiksoftware.android.adapter.actionhandler.action.BaseAction;
import ua.naiksoftware.android.util.ViewUtils;
import ua.naiksoftware.examplesticky.model.Team;

/**
 * Created by naik on 01.10.16.
 */

public class OpenTeamAction extends BaseAction<Team> {

    @Override
    public boolean isModelAccepted(Object model) {
        return model instanceof Team;
    }

    @Override
    public void onFireAction(Context context, @Nullable View view, @Nullable String actionType, @Nullable Team model) {
        ViewUtils.snackBar(model.getName(), view);
    }
}
