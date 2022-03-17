package com.virtuslab.internship.discount;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import com.virtuslab.internship.receipt.ReceiptGenerator;

class FifteenPercentDiscountTest {

	@Test
	void shouldApplyDiscount() throws Exception {
		 var productDb = new ProductDb();
	     var cart = new Basket();
	     var milk = productDb.getProduct("Milk");
	     var bread = productDb.getProduct("Bread");
	     var cereals = productDb.getProduct("Cereals");
	     var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2)).add(milk.price().add(cereals.price())).multiply(BigDecimal.valueOf(0.85));
	       
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(cereals);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
        var discount = new FifteenPercentDiscount();
        var receiptAfterDiscount = discount.apply(receipt,cart);

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
	
	@Test
	void shouldNotApplyDiscount() throws Exception {
		 var productDb = new ProductDb();
	     var cart = new Basket();
	     var milk = productDb.getProduct("Milk");
	     var bread = productDb.getProduct("Bread");
	     var cereals = productDb.getProduct("Cereals");
	     var expectedTotalPrice = bread.price().add(milk.price().add(cereals.price()));
	       
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(cereals);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
        var discount = new FifteenPercentDiscount();
        var receiptAfterDiscount = discount.apply(receipt,cart);

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

}
