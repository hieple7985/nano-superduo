package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.widget.ListView;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainScreenFragment;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresAdapter;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link DayAppWidgetConfigureActivity DayAppWidgetConfigureActivity}
 */
public class DayAppWidget extends AppWidgetProvider {

    private MainScreenFragment viewFragment = new MainScreenFragment();

    // Services
    ContentResolver mContentResolver;

    // Football Data
    static ListView mLvFootballScores;
    static ScoresAdapter mScoresAdapter;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Load Widget's Settings
        CharSequence widgetText = DayAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.day_app_widget);
        // views.setTextViewText(R.id.appwidget_text, widgetText);

        // views.setRemoteAdapter();

        // TODO: Set parameter on date time based on settings
//        Date fragmentdate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
//        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");

        // TODO: Set data to RemoteView bind to list of football match in day.
        if (mScoresAdapter == null) {
            mScoresAdapter = new ScoresAdapter(context, null, 0);
        }

//        score_list.setAdapter(mAdapter);
//        getLoaderManager().initLoader(SCORES_LOADER, null, this);
//
//        mAdapter.detail_match_id = MainActivity.selected_match_id;
//        score_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ViewHolder selected = (ViewHolder) view.getTag();
//                mAdapter.detail_match_id = selected.match_id;
//                MainActivity.selected_match_id = (int) selected.match_id;
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        mContentResolver = context.getContentResolver();

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        mContentResolver = context.getContentResolver();

        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds)
        {
            DayAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
        mContentResolver = context.getContentResolver();
    }
}

