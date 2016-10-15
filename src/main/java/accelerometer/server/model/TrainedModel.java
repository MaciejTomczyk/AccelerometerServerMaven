package accelerometer.server.model;

import javax.validation.constraints.NotNull;

public class TrainedModel {

    @NotNull
    public String user_id;

    @NotNull
    public String result;
    
    @NotNull
    public Double x;

    @NotNull
    public Double y;

    @NotNull
    public Double z;


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
