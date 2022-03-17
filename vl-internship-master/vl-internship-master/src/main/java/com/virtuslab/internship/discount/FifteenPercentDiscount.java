package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.Product.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt, Basket basket) {
        if (shouldApply(basket)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            List<String> discounts= receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Basket basket) {
    	List<Product> products = basket.getProducts();
    	int grainQuantity = 0;
    	for (int i = 0; i <products.size() ; i++) {
    		if(products.get(i).type()==Type.GRAINS) {
    			grainQuantity++;
    			}
    		}
    	
        return grainQuantity>=3;
    }
}

