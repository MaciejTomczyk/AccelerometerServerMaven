package accelerometer.server.model;

import javax.validation.constraints.NotNull;

public class GPSModel {

    @NotNull
    public String user_id;

    @NotNull
    public Double latitude;

    @NotNull
    public Double longitude;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



}
