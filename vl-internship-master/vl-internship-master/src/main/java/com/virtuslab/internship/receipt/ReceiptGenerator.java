package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReceiptGenerator {
	
	int i;

    public Receipt generate(Basket basket) {
        var receipt = getReceipt(basket);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var receiptAfterDiscount1 = discount1.apply(receipt,basket);
        var receiptAfterDiscount2 = discount2.apply(receiptAfterDiscount1);
        
        return receiptAfterDiscount2;
    }

    private Receipt getReceipt(Basket basket) { 
       List<Product> products = basket.getProducts();
       List<ReceiptEntry> receiptEntries = new ArrayList<>(products.size()); 
  
       for (i = 0; i <products.size() ; i++) {
    		 var quantity=1;
    		 if(receiptEntries.stream() 
		                .filter(x -> products.get(i).name().equals(x.product().name()))        
		                .findAny()                                     
		                .orElse(null) == null) {
    			 receiptEntries.add(new ReceiptEntry(products.get(i),1));}
    		 else {
    			var index=receiptEntries.indexOf(new ReceiptEntry(products.get(i),quantity));
    			receiptEntries.set(index, new ReceiptEntry(products.get(i),quantity+1));
    		 }

    	 }
    	return new Receipt(receiptEntries);
    	
    }
    
}

