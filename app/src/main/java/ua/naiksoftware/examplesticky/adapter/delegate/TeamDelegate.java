package ua.naiksoftware.examplesticky.adapter.delegate;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.naiksoftware.android.adapter.actionhandler.listener.ActionClickListener;
import ua.naiksoftware.android.adapter.delegate.BaseAdapterDelegate;
import ua.naiksoftware.android.adapter.util.SimpleViewHolder;
import ua.naiksoftware.android.model.Model;
import ua.naiksoftware.examplesticky.R;
import ua.naiksoftware.examplesticky.adapter.AdvancedViewHolder;
import ua.naiksoftware.examplesticky.model.Team;

/**
 * Created by naik on 01.10.16.
 */

public class TeamDelegate extends BaseAdapterDelegate<List<Model>> {

    private final ItemViewsLayoutInflaterFactory mItemViewsFactory;
    private LayoutInflater mLayoutInflater;

    public TeamDelegate(ActionClickListener actionHandler, Activity activity) {
        mLayoutInflater = LayoutInflater.from(activity).cloneInContext(activity);
        mItemViewsFactory = new ItemViewsLayoutInflaterFactory(activity, actionHandler);
        LayoutInflaterCompat.setFactory(mLayoutInflater, mItemViewsFactory);
    }

    @Override
    public boolean isForViewType(@NonNull List<Model> items, int position) {
        return items.get(position) instanceof Team;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AdvancedViewHolder(R.layout.item_team, parent, mLayoutInflater)
                .useView(R.id.team_avatar)
                .useView(R.id.team_name);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Model> items, int position, @NonNull SimpleViewHolder holder) {
        AdvancedViewHolder advancedHolder = (AdvancedViewHolder) holder;
        Team team = (Team) items.get(position);

        mItemViewsFactory.setActionModel(holder.itemView, team);

        ImageView avatar = advancedHolder.getView(R.id.team_avatar);
        avatar.setImageResource(team.getAvatar());

        TextView name = advancedHolder.getView(R.id.team_name);
        name.setText(team.getName());
    }

    @Override
    public long getItemId(List<Model> dataSource, int position) {
        return position;
    }
}
