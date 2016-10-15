package accelerometer.server.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Trained {

	@PrimaryKey
	@NotNull
	private String user_id;

	@Column
	@NotNull
	private String result;

	@Column
	@NotNull
	private Double x;

	@Column
	@NotNull
	private Double y;

	@Column
	@NotNull
	private Double z;

	public Trained() {
	}

	public Trained(TrainedModel trainedModel) {
		user_id = trainedModel.getUser();
		result = trainedModel.getResult();
		x = trainedModel.getX();
		y = trainedModel.getY();
		z = trainedModel.getZ();
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

	public String getUser() {
		return user_id;
	}

	public void setUser(String user_id) {
		this.user_id = user_id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}