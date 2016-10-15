package accelerometer.server.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import accelerometer.server.model.AccelerationModel;
import accelerometer.server.model.Acceleration;
import accelerometer.server.model.Result;

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
    @Autowired
    public CassandraOperations cassandraTemplate;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity newAcceleration(@RequestBody @Valid AccelerationModel accelerationModel) {

        Acceleration acceleration = new Acceleration(accelerationModel);

        if (logger.isInfoEnabled()) {
            logger.info("/POST /acceleration with values {}", acceleration);
        }

        cassandraTemplate.insert(acceleration);
        return status(CREATED).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getLastPrediction() {
        Result prediction = cassandraTemplate.select("select * from trainingAcceleration limit 1", Result.class).get(0);
        if (logger.isInfoEnabled()) {
            logger.info("/GET /acceleration with values {}", "DUPA");
        }


        return  "DUPA";
    }
    
    @RequestMapping(value="/{user}",method = RequestMethod.GET)
    public String getLastPrediction(@PathVariable String user) {
    	try{Result prediction = cassandraTemplate.select("select prediction from result where user_id='"+user+"'", Result.class).get(0);
        if (logger.isInfoEnabled()) {
            logger.info("/GET /acceleration with values {}", prediction);
        }


        return  prediction.getPrediction();
        }catch(Exception e){
        	
        }
		return "NOTFOUND";
    }
    
    

}
