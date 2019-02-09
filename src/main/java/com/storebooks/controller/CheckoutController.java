package com.storebooks.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storebooks.model.BillingAddress;
import com.storebooks.model.CartItem;
import com.storebooks.model.Order;
import com.storebooks.model.Payment;
import com.storebooks.model.ShippingAddress;
import com.storebooks.model.ShoppingCart;
import com.storebooks.model.User;
import com.storebooks.service.CartItemService;
import com.storebooks.service.OrderService;
import com.storebooks.service.ShoppingCartService;
import com.storebooks.service.UserService;
import com.storebooks.utility.MailConstructor;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private Order order = new Order();
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private MailConstructor mailConstructor;

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public Order checkout(@RequestBody HashMap<String, Object> mapper, Principal principal) {

        ObjectMapper objectMapper = new ObjectMapper();

        ShippingAddress shippingAddress = objectMapper.convertValue(mapper.get("shippingAddress"), ShippingAddress.class);
        BillingAddress billingAddress = objectMapper.convertValue(mapper.get("billingAddress"), BillingAddress.class);
        Payment payment = objectMapper.convertValue(mapper.get("payment"), Payment.class);
        String shippingMethod = (String) mapper.get("shippingMethod");
        
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        User user = userService.findByUsername(principal.getName());
        Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user);
        
        mailSender.send(mailConstructor.constructOrderConfirmationEmail(user,order,Locale.ENGLISH));
        
        shoppingCartService.clearShoppingCart(shoppingCart);
        
        LocalDate today = LocalDate.now();
        LocalDate estimatedDeliveryDay;
        if (shippingMethod.equals("groundShipping")){
        	estimatedDeliveryDay= today.plusDays(5);
        	
        }else{
        	estimatedDeliveryDay= today.plusDays(3);
        }
        
        this.order = order;
        return order;
    }
}
