package accelerometer.server.model;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@PrimaryKeyClass
public class UserActivityTimestamp implements Serializable {

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @NotNull
    private String userID;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @NotNull
    private String activity;

    @NotNull
    private Long timestamp;

    public UserActivityTimestamp() {}

    public UserActivityTimestamp(String userID, String activity, Long timestamp) {
        this.userID = userID;
        this.activity = activity;
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
