package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* ****************************************************************************************
 
Please remove all bugs from the code below to get the following output:

<pre>

*******Order 1*******
1 book: 13.74
1 music CD: 16.49
1 chocolate bar: 0.94
Sales Tax: 2.83
Total: 31.16
*******Order 2*******
1 imported box of chocolate: 11.50
1 imported bottle of perfume: 54.63
Sales Tax: 8.63
Total: 66.13
*******Order 3*******
1 Imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 10.72
1 box of imported chocolates: 12.94
Sales Tax: 8.76
Total: 76.74
Sum of orders: 174.03
 
</pre>
 
******************************************************************************************** */

/*
 * represents an item, contains a price and a description.
 *
 */
class Item {

	private final String description;
	private final float price;

	public Item(String description, float price) {
		super();
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}
}

/*
 * represents an order line which contains the @link Item and the quantity.
 *
 */
class OrderLine {

	private int quantity;
	private Item item;

	/*
	 * @param item Item of the order
	 * 
	 * @param quantity Quantity of the item
	 */
	public OrderLine(Item item, int quantity) throws Exception {
		if (item == null) {
			System.err.println("ERROR - Item is NULL");
			throw new Exception("Item is NULL");
		}
		assert quantity > 0;
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}

class Order {
	private String orderName;
	private List<OrderLine> orderLines;

	public Order(String orderName) {
		this.orderName = orderName;
		orderLines = new ArrayList<>();
	}

	public void add(OrderLine o) {
		if (o == null) {
			System.err.println("ERROR - Order is NULL");
			throw new IllegalArgumentException("Order is NULL");
		}
		orderLines.add(o);
	}

	public int size() {
		return orderLines.size();
	}

	public OrderLine get(int i) {
		return orderLines.get(i);
	}

	public String getOrderName() {
		return orderName;
	}

	public BigDecimal calculate() {

		System.out.println("*******" + this.getOrderName() + "*******");

		BigDecimal totalTax = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);

		// Iterate through the items in the order
		for (int i = 0; i < this.size(); i++) {

			// Calculate the taxes
			BigDecimal tax;

			Item item = this.get(i).getItem();
			float price = item.getPrice();
			if (item.getDescription().contains("imported") || item.getDescription().contains("Imported")) {
				tax =rounding(price).multiply(new BigDecimal(0.15)); // Extra 5% tax on
				// imported items
			} else {
				tax = rounding(price).multiply(new BigDecimal(0.10));
			}

			// Calculate the total price
			BigDecimal totalPrice = rounding(price).add(tax);

			// Print out the item's total price
			System.out.println(this.get(i).getQuantity() + " " + item.getDescription() + ": " + rounding(totalPrice.doubleValue()));

			// Keep a running total
			totalTax = totalTax.add(tax );
			total  = total.add(totalPrice);
		}

		// Print out the total taxes
		System.out.println("Sales Tax: " + rounding(totalTax.doubleValue()));


		// Print out the total amount
		System.out.println("Total: " + rounding(total.doubleValue()));

		return total;
	}

	public static BigDecimal rounding(double value) {
		BigDecimal returnValue = new BigDecimal(value);
		return returnValue.setScale(2, RoundingMode.HALF_UP);
	}
}

public class Foo {

	public static void main(String[] args) throws Exception {
		BigDecimal grandTotal = new BigDecimal(0);
		Order c = new Order("Order 1");
		c.add(new OrderLine(new Item("book", (float) 12.49), 1));
		c.add(new OrderLine(new Item("music CD", (float) 14.99), 1));
		c.add(new OrderLine(new Item("chocolate bar", (float) 0.85), 1));
		grandTotal = grandTotal.add(c.calculate());

		Order d = new Order("Order 2");
		d.add(new OrderLine(new Item("imported box of chocolate", 10), 1));
		d.add(new OrderLine(new Item("imported bottle of perfume", (float) 47.50), 1));
		grandTotal = grandTotal.add(d.calculate());

		Order e = new Order("Order 3");
		e.add(new OrderLine(new Item("Imported bottle of perfume", (float) 27.99), 1));
		e.add(new OrderLine(new Item("bottle of perfume", (float) 18.99), 1));
		e.add(new OrderLine(new Item("packet of headache pills", (float) 9.75), 1));
		e.add(new OrderLine(new Item("box of imported chocolates", (float) 11.25), 1));
		grandTotal = grandTotal.add(e.calculate());

		System.out.println("Sum of orders: " + Order.rounding(grandTotal.doubleValue()));

	}
}
