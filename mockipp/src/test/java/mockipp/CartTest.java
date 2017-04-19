package mockipp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;

public class CartTest {

	@Test
	public void createCart() {
		Cart cart = new Cart();
		int n = cart.getTotalNumberOfItemsInCart();
		assertEquals(0, n);
	}
	
	@Test
	public void putOneItemInCart() {
		Cart cart = new Cart();
		cart.put("1234");
		
		int n1 = cart.getTotalNumberOfItemsInCart();
		assertEquals(1, n1);
		int n2 = cart.getTotalNumberOfItemsInCart("1234");
		assertEquals(1, n2);
	}
	
	@Test
	public void putMultipleItemInCart() {
		Cart cart = new Cart();
		cart.put("1234");
		cart.put("1234");
		cart.put("4567");
		
		int n1 = cart.getTotalNumberOfItemsInCart();
		assertEquals(3, n1);
		int n2 = cart.getTotalNumberOfItemsInCart("1234");
		assertEquals(2, n2);
	}
	
	@Test
	public void computeTotalPrice() {
		
		//Arrange Part..
		Cart cart = new Cart();
		PriceManager pm = mock(PriceManager.class);
		// getPrice("1234") -> thenReturn 동작지정
		// 동작을 지정하는것을 Stubbing이라고 한다.
		when(pm.getPrice("1234")).thenReturn(1000);
		cart.setPriceManager(pm);
		cart.put("1234");
		//..end
		
		//Act Part..
		int price = cart.totalPrice();
		//..end
		
		//Assert Part..
		assertEquals(1000, price);	
		//..end
	}
	
	@Test
	public void computeTotalPriceWhenMultipleItemExist() {
		
		Cart cart = new Cart();
		PriceManager pm = mock(PriceManager.class);
		when(pm.getPrice("1234")).thenReturn(1000);
		when(pm.getPrice("7891")).thenReturn(800);
		cart.setPriceManager(pm);
		cart.put("1234");
		cart.put("7891");
		
		int price = cart.totalPrice();
		
		assertEquals(1800, price);	

	}
	
	@Mock PriceManager pm;
	@InjectMocks Cart cart;
	@Test
	public void computeTotalPriceWhenDiscountAppliesForEven() {
		
//		Cart cart = new Cart();
//		PriceManager pm = mock(PriceManager.class);
		MockitoAnnotations.initMocks(this);
		when(pm.getPrice("1234")).thenReturn(1000);
		when(pm.getPrice("3456")).thenReturn(2500);
		when(pm.getPrice("7891")).thenReturn(800);
		when(pm.isOnePlusOneApplicable("1234")).thenReturn(false);
		when(pm.isOnePlusOneApplicable("3456")).thenReturn(true);
		when(pm.isOnePlusOneApplicable("7891")).thenReturn(false);
//		cart.setPriceManager(pm);
		cart.put("1234");
		cart.put("3456");
		cart.put("3456");
		cart.put("7891");
		
		int price = cart.totalPrice();
		
		assertEquals(4300, price);	
		
	}
	
	@Test
	public void computeTotalPriceWhenDiscountAppliesForOdd() {
		
		Cart cart = new Cart();
		PriceManager pm = mock(PriceManager.class);
		when(pm.getPrice("1234")).thenReturn(1000);
		when(pm.getPrice("3456")).thenReturn(2500);
		when(pm.getPrice("7891")).thenReturn(800);
		when(pm.isOnePlusOneApplicable("1234")).thenReturn(false);
		when(pm.isOnePlusOneApplicable("3456")).thenReturn(true);
		when(pm.isOnePlusOneApplicable("7891")).thenReturn(false);
		cart.setPriceManager(pm);
		cart.put("1234");
		cart.put("3456");
		cart.put("3456");
		cart.put("3456");
		cart.put("7891");
		
		int price = cart.totalPrice();
		
		assertEquals(6800, price);	
		
	}

}
