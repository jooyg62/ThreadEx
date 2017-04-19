package mockipp;

import org.mockito.stubbing.OngoingStubbing;

public interface PriceManager {
	
	int getPrice(String id);
	boolean isOnePlusOneApplicable(String id);

	

}
