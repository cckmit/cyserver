package ltd.moore.ctravel.experience.model;

import java.io.Serializable;


/**
 * Destination对象
 * @author caicai
 * @version 1.0
 */

public class DestinationVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 *目的地ID
	 */
	private String destinationId;
	
	/*
	 *目的地
	 */
	private String destination;
	
	public DestinationVO(){}
	
	public String getDestinationId() {
		return  destinationId;
	}

	public void setDestinationId(String  destinationId) {
		 this.destinationId = destinationId;
	}
	
	public String getDestination() {
		return  destination;
	}

	public void setDestination(String  destination) {
		 this.destination = destination;
	}
	
}