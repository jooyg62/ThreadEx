package mockipp;

import java.util.HashMap;
import java.util.Iterator;

public class Cart {

	private PriceManager pm;

	private HashMap<String, Integer> h = new HashMap<String, Integer>();
	private int count = 0;

	public int getTotalNumberOfItemsInCart() {
		// TODO Auto-generated method stub
		return count;
	}

	public void put(String id) {
		int n = 0;
		if (h.containsKey(id)) {
			n = h.get(id);
		}
		h.put(id, n + 1);
		count++;
	}

	public int getTotalNumberOfItemsInCart(String id) {
		return h.get(id);
	}

	public void setPriceManager(PriceManager pm) {
		this.pm = pm;

	}

	public int totalPrice() {
		int total = 0;
		

		Iterator itr = h.keySet().iterator();
		while (itr.hasNext()) {
			String id = (String) itr.next();	
			int n = h.get(id);
			
			if (pm.isOnePlusOneApplicable(id)) n = (n+1)/2;
			
			total += pm.getPrice(id) * n;
		}
		return total;
	}

}
