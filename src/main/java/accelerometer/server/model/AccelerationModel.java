package accelerometer.server.model;

import javax.validation.constraints.NotNull;

public class AccelerationModel {

    @NotNull
    public Long timestamp;

    @NotNull
    public Double x;

    @NotNull
    public Double y;

    @NotNull
    public Double z;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "AccelerationModel{timestamp=" + timestamp + ", x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
