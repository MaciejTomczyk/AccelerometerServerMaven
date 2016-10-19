package accelerometer.server.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accelerometer.server.model.GPS;
import accelerometer.server.model.GPSModel;

@RestController
@RequestMapping("/gps")
public class GPSController {

    private static final Logger log = LoggerFactory.getLogger(GPSController.class);
    Calculations calc = new Calculations();
    @Autowired
    private CassandraOperations cassandraTemplate;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity newAcceleration(@RequestBody @Valid GPSModel gpsModel) {

        if (log.isInfoEnabled()) {
            log.info("/POST /gps with values {}", gpsModel);
        }

        GPS gps = new GPS(gpsModel);
        cassandraTemplate.insert(gps);

        return status(CREATED).build();
    }
    
    @RequestMapping(value="/end",method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity calculateData(@RequestBody GPSModel gpsModel) {
    	ArrayList<Double> listLat = new ArrayList<Double>();
    	ArrayList<Double> listLon = new ArrayList<Double>();
    	List<GPS> gpsList= cassandraTemplate.select("select * from gps where userid='"+gpsModel.getUser_id()+"'", GPS.class);
    	for(GPS item: gpsList){
    		listLat.add(item.getLatitude());
    		listLat.add(item.getLongitude());
    	}
    	//TODO
    	if (log.isInfoEnabled()) {
            log.info("/POST /gps/end with values {}", gpsModel);
        }

      

        return status(CREATED).build();
    }

}
