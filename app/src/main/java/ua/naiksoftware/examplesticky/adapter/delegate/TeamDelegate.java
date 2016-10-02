package ua.naiksoftware.examplesticky.adapter.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.naiksoftware.android.adapter.actionhandler.listener.ActionClickListener;
import ua.naiksoftware.android.adapter.delegate.BaseAdapterDelegate;
import ua.naiksoftware.android.adapter.util.SimpleViewHolder;
import ua.naiksoftware.android.model.Model;
import ua.naiksoftware.examplesticky.R;
import ua.naiksoftware.examplesticky.model.Team;

/**
 * Created by naik on 01.10.16.
 */

public class TeamDelegate extends BaseAdapterDelegate<Model> {

    public TeamDelegate(ActionClickListener actionHandler, Context context) {
        super(actionHandler, context);
    }

    @Override
    public boolean isForViewType(@NonNull List<Model> items, int position) {
        return items.get(position) instanceof Team;
    }

    @NonNull
    @Override
    public SimpleViewHolder createHolder(LayoutInflater inflater, ViewGroup parent) {
        return new SimpleViewHolder(R.layout.item_team, parent, inflater)
                .findView(R.id.team_avatar)
                .findView(R.id.team_name);
    }

    @Override
    public void bindHolder(@NonNull List<Model> items, int position, @NonNull SimpleViewHolder holder) {
        Team team = (Team) items.get(position);

        ImageView avatar = holder.getView(R.id.team_avatar);
        avatar.setImageResource(team.getAvatar());

        TextView name = holder.getView(R.id.team_name);
        name.setText(team.getName());
    }

    @Override
    public long getItemId(List<Model> dataSource, int position) {
        return position;
    }
}
