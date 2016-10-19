package accelerometer.server.controller;

import java.util.ArrayList;
import java.util.List;

import accelerometer.server.model.Acceleration;
import accelerometer.server.model.Trained;
import accelerometer.server.model.TrainedModel;
import accelerometer.server.model.TrainingAcceleration;
import accelerometer.server.model.TrainingAccelerationModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
    Calculations calc = new Calculations();
    @Autowired
    private CassandraOperations cassandraTemplate;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity newAcceleration(@RequestBody @Valid TrainingAccelerationModel trainingAccelerationModel) {

        if (log.isInfoEnabled()) {
            log.info("/POST /training with values {}", trainingAccelerationModel);
        }

        TrainingAcceleration trainingAcceleration = new TrainingAcceleration(trainingAccelerationModel);
        cassandraTemplate.insert(trainingAcceleration);

        return status(CREATED).build();
    }
    
    @RequestMapping(value="/end",method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity calculateData(@RequestBody TrainingAccelerationModel trainingAccelerationModel) {
    	ArrayList<Double> listX = new ArrayList<Double>();
    	ArrayList<Double> listY = new ArrayList<Double>();
    	ArrayList<Double> listZ = new ArrayList<Double>();
    	List<TrainingAcceleration> accelerationList= cassandraTemplate.select("select * from trainingAcceleration where userid='"+trainingAccelerationModel.getUserID()+"' and activity='"+trainingAccelerationModel.getActivity()+"'", TrainingAcceleration.class);
        
    	if (log.isInfoEnabled()) {
            log.info("/POST /training/end with values {}", trainingAccelerationModel);
        }
    	for (TrainingAcceleration item:accelerationList){
    		listX.add(item.getX());
    		listY.add(item.getY());
    		listZ.add(item.getZ());
    	}
    	Double resultX = calc.getStdDev(listX);
    	Double resultY = calc.getStdDev(listY);
    	Double resultZ = calc.getStdDev(listZ);
    	TrainedModel tm = new TrainedModel();
    	tm.setUser(trainingAccelerationModel.getUserID());
    	tm.setResult(trainingAccelerationModel.getActivity());
    	tm.setX(resultX);
    	tm.setY(resultY);
    	tm.setZ(resultZ);
    	Trained t = new Trained(tm);
    	cassandraTemplate.insert(t);
    	cassandraTemplate.execute("delete from trainingAcceleration where userid='"+trainingAccelerationModel.getUserID()+"' and activity='"+trainingAccelerationModel.getActivity()+"'");

      

        return status(CREATED).build();
    }

}
