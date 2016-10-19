package accelerometer.server.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

@Table
public class GPS implements Serializable {

	@PrimaryKey
	@NotNull
	private String user_id;
	@Column
	@NotNull
	private double latitude;
	@Column
	@NotNull
	private double longitude;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public GPS(GPSModel gps) {
		this.user_id = gps.getUser_id();
		this.latitude = gps.getLatitude();
		this.longitude = gps.getLongitude();
	}

}