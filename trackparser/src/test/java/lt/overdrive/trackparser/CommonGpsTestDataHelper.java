package lt.overdrive.trackparser;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lt.overdrive.trackparser.domain.Track;
import lt.overdrive.trackparser.domain.TrackPoint;
import lt.overdrive.trackparser.domain.Trail;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

public class CommonGpsTestDataHelper {
    public static final DateTimeZone TIME_ZONE = DateTimeZone.forOffsetMillis(0);
    public static final TrackPoint POINT_1 =
            new TrackPoint(54.709699, 25.245331, 165.282010, new DateTime(2012, 11, 24, 6, 42, 35, 0, TIME_ZONE));
    public static final TrackPoint POINT_2 =
            new TrackPoint(54.709651, 25.245321, 165.064160, new DateTime(2012, 11, 24, 6, 42, 36, 0, TIME_ZONE));
    public static final TrackPoint POINT_3 =
            new TrackPoint(54.709612, 25.245299, 164.895780, new DateTime(2012, 11, 24, 6, 42, 37, 0, TIME_ZONE));
    public static final TrackPoint POINT_4 =
            new TrackPoint(54.709577, 25.245242, 164.769670, new DateTime(2012, 11, 24, 6, 42, 38, 0, TIME_ZONE));
    public static final TrackPoint POINT_5 =
            new TrackPoint(54.709552, 25.245176, 164.748660, new DateTime(2012, 11, 24, 6, 42, 39, 0, TIME_ZONE));
    public static final TrackPoint POINT_6 =
            new TrackPoint(54.709523, 25.245106, 164.775070, new DateTime(2012, 11, 24, 6, 42, 40, 0, TIME_ZONE));

    public static Trail prepareTrail(List<TrackPoint> tracksPoints) {
        Track track = new Track(tracksPoints);
        return new Trail(ImmutableList.of(track));
    }

    public static Trail prepareTrailWithoutAltitude(List<TrackPoint> tracksPoints) {
        return prepareTrail(prepareTrackPointsWithoutAltitude(tracksPoints));
    }

    public static List<TrackPoint> prepareTrackPointsWithoutAltitude(List<TrackPoint> tracksPoints) {
        return Lists.transform(tracksPoints, new Function<TrackPoint, TrackPoint>() {
            @Override
            public TrackPoint apply(TrackPoint input) {
                return new TrackPoint(input.getLatitude(), input.getLongitude(), null, input.getTime());
            }
        });
    }

    public static Trail prepareTrailWithoutTime(List<TrackPoint> tracksPoints) {
        return prepareTrail(prepareTrackPointsWithoutTime(tracksPoints));
    }

    public static List<TrackPoint> prepareTrackPointsWithoutTime(List<TrackPoint> tracksPoints) {
        return Lists.transform(tracksPoints, new Function<TrackPoint, TrackPoint>() {
            @Override
            public TrackPoint apply(TrackPoint input) {
                return new TrackPoint(input.getLatitude(), input.getLongitude(), input.getAltitude(), null);
            }
        });
    }
}
