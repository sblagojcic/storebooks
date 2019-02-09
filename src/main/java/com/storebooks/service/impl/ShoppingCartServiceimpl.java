package com.storebooks.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storebooks.model.CartItem;
import com.storebooks.model.ShoppingCart;
import com.storebooks.repository.ShoppingCartRepository;
import com.storebooks.service.CartItemService;
import com.storebooks.service.ShoppingCartService;

@Service
public class ShoppingCartServiceimpl implements ShoppingCartService {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        BigDecimal cartTotal = new BigDecimal(0);
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem: cartItemList) {
            if (cartItem.getBook().getInStockNumber() > 0){
                cartItemService.updateCartItem(cartItem);
                cartTotal = cartTotal.add(cartItem.getSubTotal());
            }
        }
        shoppingCart.setTotal(cartTotal);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;

    }

   
    public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemService.save(cartItem);
		}
		
		shoppingCart.setTotal(new BigDecimal(0));
		
		shoppingCartRepository.save(shoppingCart);
	}

}