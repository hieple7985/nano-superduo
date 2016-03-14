package barqsoft.footballscores.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilities;

/**
 * Created by Hiep Le on 3/14/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RemoteViewsService extends android.widget.RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FootballRemoteViewsFactory(getApplicationContext());
    }


    public class FootballRemoteViewsFactory implements RemoteViewsFactory {

        private Context mContext;
        private Cursor mCursor;

        public static final int COL_DATE = 1;
        public static final int COL_STATUS = 2;
        public static final int COL_MATCHTIME = 3;
        public static final int COL_HOME = 4;
        public static final int COL_AWAY = 5;
        // public static final int COL_LEAGUE = 6;
        public static final int COL_HOME_GOALS = 7;
        public static final int COL_AWAY_GOALS = 8;
        public static final int COL_ID = 9;
        // public static final int COL_MATCHDAY = 10;

        public FootballRemoteViewsFactory(Context context) {
            this.mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            if (mCursor != null)
                mCursor.close();
            mCursor = getMatchesWithScores();
        }

        @Override
        public void onDestroy() {
            if(mCursor != null)
                mCursor.close();
        }

        @Override
        public int getCount() {
            if (mCursor != null)
                return mCursor.getCount();
            else
                return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            String homeName = "";
            String awayName = "";
            String date = "";
            String time = "";
            String status = "";
            String score = "";

            if (mCursor.moveToPosition(position)) {
                homeName = mCursor.getString(COL_HOME);
                awayName = mCursor.getString(COL_AWAY);
                date = mCursor.getString(COL_DATE);
                time = mCursor.getString(COL_MATCHTIME);
                status = mCursor.getString(COL_STATUS);
                score = Utilities.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS));
            }

            Intent intent = new Intent();
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.match_item_widget);

            remoteViews.setTextViewText(R.id.widget_home_name, homeName);
            remoteViews.setTextViewText(R.id.widget_away_home, awayName);
            remoteViews.setTextViewText(R.id.widget_final_score, score);
            remoteViews.setTextViewText(R.id.widget_date, date);
            remoteViews.setTextViewText(R.id.widget_time, time);
            remoteViews.setTextViewText(R.id.widget_status, status);

            remoteViews.setOnClickFillInIntent(R.id.widget_item_container, intent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            if (mCursor != null)
                return mCursor.getLong(mCursor.getColumnIndex(DatabaseContract.scores_table.COL_MATCH_ID));
            else
                return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private Cursor getMatchesWithScores() {
            return mContext.getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, null, null, null, null);
        }
    }
}
