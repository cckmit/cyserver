package ltd.moore.ctravel.hotdestinations.model;



/**
 * HotDestinations对象
 * @author Cocouzx
 * @version 1.0
 */

public class HotDestinationsVO {


	
	//热门目的地ID
	private String hotDestinationsId;
	
	//目的地ID
	private String destinationId;
	

	public String getHotDestinationsId() {
		return  hotDestinationsId;
	}

	public void setHotDestinationsId(String  hotDestinationsId) {
		 this.hotDestinationsId = hotDestinationsId;
	}
	
	public String getDestinationId() {
		return  destinationId;
	}

	public void setDestinationId(String  destinationId) {
		 this.destinationId = destinationId;
	}
	
}