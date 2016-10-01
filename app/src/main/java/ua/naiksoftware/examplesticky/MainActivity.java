package ua.naiksoftware.examplesticky;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ua.naiksoftware.android.adapter.DelegatesAdapter;
import ua.naiksoftware.android.adapter.actionhandler.ActionHandler;
import ua.naiksoftware.android.adapter.actionhandler.listener.ActionClickListener;
import ua.naiksoftware.android.adapter.util.ListConfig;
import ua.naiksoftware.android.model.Model;
import ua.naiksoftware.examplesticky.adapter.ActionType;
import ua.naiksoftware.examplesticky.adapter.action.OpenTeamAction;
import ua.naiksoftware.examplesticky.adapter.delegate.TeamDelegate;
import ua.naiksoftware.examplesticky.model.util.Generator;

public class MainActivity extends AppCompatActivity {

	private DelegatesAdapter<Model> mAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_select );
        setupToolbar();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		createListConfig().applyConfig(recyclerView);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private ListConfig createListConfig() {
        ActionClickListener actionHandler = createActionHandler();
		mAdapter = new DelegatesAdapter<Model>(Generator.createTeams(40), new TeamDelegate(actionHandler, this));
		return new ListConfig.Builder(mAdapter)
				.build(this);
	}

    private ActionClickListener createActionHandler() {
        return new ActionHandler.Builder()
                .addAction(ActionType.OPEN, new OpenTeamAction())
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}