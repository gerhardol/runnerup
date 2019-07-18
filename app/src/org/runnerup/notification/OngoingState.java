package org.runnerup.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import org.runnerup.R;
import org.runnerup.common.util.Constants;
import org.runnerup.util.Formatter;
import org.runnerup.view.RunActivity;
import org.runnerup.workout.Scope;
import org.runnerup.workout.WorkoutInfo;


public class OngoingState implements NotificationState {
    private final Formatter formatter;
    private final WorkoutInfo workoutInfo;
    private final Context context;
    private final NotificationCompat.Builder builder;

    public OngoingState(Formatter formatter, WorkoutInfo workoutInfo, Context context) {
        this.formatter = formatter;
        this.workoutInfo = workoutInfo;
        this.context = context;

        String chanId = NotificationStateManager.getChannelId(context);

        Intent i = new Intent(context, RunActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra(Constants.Intents.FROM_NOTIFICATION, true);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);

        builder = new NotificationCompat.Builder(context, chanId);
        builder.setTicker(context.getString(R.string.RunnerUp_activity_started));
        builder.setContentIntent(pi);
        builder.setContentTitle(context.getString(R.string.Activity_ongoing));
        builder.setSmallIcon(R.drawable.ic_stat_notify);
        builder.setOngoing(true);
        builder.setOnlyAlertOnce(true);
        builder.setLocalOnly(true);
        if (Build.VERSION.SDK_INT >= 21) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setCategory(Notification.CATEGORY_SERVICE);
        }
    }

    @Override
    public Notification createNotification() {
        String distance = formatter.formatDistance(Formatter.Format.TXT_SHORT,
                Math.round(workoutInfo.getDistance(Scope.ACTIVITY)));
        String time = formatter.formatElapsedTime(Formatter.Format.TXT_LONG,
                Math.round(workoutInfo.getTime(Scope.ACTIVITY)));
        String pace = formatter.formatPaceSpeed(Formatter.Format.TXT_SHORT,
                workoutInfo.getSpeed(Scope.ACTIVITY));

        String content = String.format("%s: %s %s: %s %s: %s",
                context.getString(R.string.distance), distance,
                context.getString(R.string.time), time,
                context.getString(R.string.pace), pace);
        builder.setContentText(content);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
        bigTextStyle.setBigContentTitle(context.getString(R.string.Activity_ongoing));
        bigTextStyle.bigText(String.format("%s: %s,\n%s: %s\n%s: %s",
                context.getString(R.string.distance), distance,
                context.getString(R.string.time), time,
                context.getString(R.string.pace), pace));
        builder.setStyle(bigTextStyle);

        return builder.build();
    }
}
