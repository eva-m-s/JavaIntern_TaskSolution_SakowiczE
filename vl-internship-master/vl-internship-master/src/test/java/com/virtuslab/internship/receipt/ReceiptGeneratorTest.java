package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptGeneratorTest {

    @Test
    void testReceiptGeneration() throws Exception {
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
       var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(2)).add(bread.price().add(apple.price()));
       
        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
       
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }
    @Test
    void testReceiptGenerationFifteenPercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var cart = new Basket();
        var apple = productDb.getProduct("Apple");
        var bread = productDb.getProduct("Bread");
        var cereal = productDb.getProduct("Cereals");
        var expectedTotalPrice = cereal.price().multiply(BigDecimal.valueOf(2)).add(bread.price().add(apple.price())).multiply(BigDecimal.valueOf(0.85));
        
        cart.addProduct(cereal);
        cart.addProduct(cereal);
        cart.addProduct(bread);
        cart.addProduct(apple);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
       
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void testReceiptGenerationTenPercentDiscount() throws Exception {
        var productDb = new ProductDb();
        var cart = new Basket();
        var onion = productDb.getProduct("Onion");
        var orange=productDb.getProduct("Orange");
        var pork=productDb.getProduct("Pork");
        var steak = productDb.getProduct("Steak");
        var expectedTotalPrice = onion.price().add(orange.price()).add(pork.price()).add(steak.price()).multiply(BigDecimal.valueOf(0.9));
        
        cart.addProduct(onion);
        cart.addProduct(orange);
        cart.addProduct(pork);
        cart.addProduct(steak);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
        
        assertNotNull(receipt);
        assertEquals(4, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }
    
    @Test
    void testReceiptGenerationBothDiscounts() throws Exception {
        var productDb = new ProductDb();
        var cart = new Basket();
        var cereal = productDb.getProduct("Cereals");
        var bread=productDb.getProduct("Bread");
        var butter=productDb.getProduct("Butter");
        var steak = productDb.getProduct("Steak");
        var firstDiscount=bread.price().multiply(BigDecimal.valueOf(2)).add(cereal.price().add(butter.price().add(steak.price()))).multiply(BigDecimal.valueOf(0.85));
        var expectedTotalPrice = firstDiscount.multiply(BigDecimal.valueOf(0.9));
        
        cart.addProduct(cereal);
        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(butter);
        cart.addProduct(steak);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
        
        assertNotNull(receipt);
        assertEquals(4, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(2, receipt.discounts().size());
    }


}