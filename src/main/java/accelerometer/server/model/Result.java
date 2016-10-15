package accelerometer.server.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import java.io.Serializable;

@Table
public class Result implements Serializable {

    @PrimaryKey
    private UserTimestamp userTimestamp;

    private String prediction;

    public Result() {}

    public UserTimestamp getUserTimestamp() {
        return userTimestamp;
    }

    public void setUserTimestamp(UserTimestamp userTimestamp) {
        this.userTimestamp = userTimestamp;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}