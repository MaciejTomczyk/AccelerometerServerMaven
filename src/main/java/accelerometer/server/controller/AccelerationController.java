package accelerometer.server.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import accelerometer.server.model.AccelerationModel;
import accelerometer.server.model.Acceleration;
import accelerometer.server.model.Result;
import accelerometer.server.model.Trained;
import accelerometer.server.model.TrainingAcceleration;
import accelerometer.server.model.TrainingAccelerationModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acceleration")
public class AccelerationController {

    private static final Logger logger = LoggerFactory.getLogger(AccelerationController.class);
    Calculations calc = new Calculations();
    @Autowired
    public CassandraOperations cassandraTemplate;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity newAcceleration(@RequestBody @Valid TrainingAccelerationModel accelerationModel) {

        Acceleration acceleration = new Acceleration(accelerationModel.getUserID(),accelerationModel.getAcceleration());

        if (logger.isInfoEnabled()) {
            logger.info("/POST /acceleration with values {}", acceleration);
        }

        cassandraTemplate.insert(acceleration);
        return status(CREATED).build();
    }
    
    @RequestMapping(value="/{user}",method = RequestMethod.GET)
    public String getLastPrediction(@PathVariable String user) {
    	ArrayList<Double> listX = new ArrayList<Double>();
    	ArrayList<Double> listY = new ArrayList<Double>();
    	ArrayList<Double> listZ = new ArrayList<Double>();
    	List<Acceleration> accelerationList= cassandraTemplate.select("select * from acceleration where user_id='"+user+"'", Acceleration.class);
    	cassandraTemplate.execute("delete from acceleration where user_id='"+user+"'");
    	List<Trained> trainedList= cassandraTemplate.select("select * from trained where user_id='"+user+"'", Trained.class);
    	
    	for (Acceleration item:accelerationList){
    		listX.add(item.getX());
    		listY.add(item.getY());
    		listZ.add(item.getZ());
    	}
    	Double resultX = calc.getStdDev(listX);
    	Double resultY = calc.getStdDev(listY);
    	Double resultZ = calc.getStdDev(listZ);
    	String result ="";
    	Double current =0.0;
    	for (Trained item: trainedList){
    		Double diffX = item.getX()-resultX;
    		Double diffY = item.getY()-resultY;	
    		Double diffZ = item.getZ()-resultZ;
    		Double diffNorm = diffX + diffY+ diffZ;
    		diffNorm = Math.abs(diffNorm);
    		if(result.equals("") || diffNorm < current){
    			current = diffNorm;
    			result = item.getResult();
    		}
    	}
		return result;
    }
    
    

}
