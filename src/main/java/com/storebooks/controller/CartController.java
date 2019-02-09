package com.storebooks.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storebooks.model.Book;
import com.storebooks.model.CartItem;
import com.storebooks.model.ShoppingCart;
import com.storebooks.model.User;
import com.storebooks.service.BookService;
import com.storebooks.service.CartItemService;
import com.storebooks.service.ShoppingCartService;
import com.storebooks.service.UserService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addItem(@RequestBody HashMap<String, String> mapper, Principal principal) {
        String bookId = mapper.get("id");
        String quantity = mapper.get("quantity");
        User user = userService.findByUsername(principal.getName());
        Book book = bookService.findOne(Long.parseLong(bookId));

        if (Integer.parseInt(quantity) > book.getInStockNumber()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        cartItemService.addBookToCartItem(book, user, Integer.parseInt(quantity));
        return new ResponseEntity( HttpStatus.OK);
    }

    @RequestMapping("/cartItemList")
    public List<CartItem> getCartItemList(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.update(shoppingCart);
        return cartItemList;
    }
    @RequestMapping("/shoppingCart")
    public ShoppingCart getShoppingCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCartService.update(shoppingCart);
        return shoppingCart;
    }

    @RequestMapping(value = "/removeCartItem", method = RequestMethod.POST)
    public ResponseEntity removeCartItem(@RequestBody String id) {
        CartItem cartItem = cartItemService.findById(Long.parseLong(id));
        cartItemService.removeCartItem(cartItem);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateCartItem", method = RequestMethod.POST)
    public ResponseEntity updateCartItem(@RequestBody HashMap<String, String> mapper) {
        String cartItemId = mapper.get("id");
        String quantity = mapper.get("quantity");
        CartItem cartItem = cartItemService.findById(Long.parseLong(cartItemId));
        cartItem.setQuantity(Integer.parseInt(quantity));
        cartItemService.updateCartItem(cartItem);
        return new ResponseEntity(HttpStatus.OK);
    }
}
