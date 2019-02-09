package com.storebooks.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storebooks.model.BillingAddress;
import com.storebooks.model.Book;
import com.storebooks.model.CartItem;
import com.storebooks.model.Order;
import com.storebooks.model.Payment;
import com.storebooks.model.ShippingAddress;
import com.storebooks.model.ShoppingCart;
import com.storebooks.model.User;
import com.storebooks.repository.BillingAddressRepository;
import com.storebooks.repository.OrderRepository;
import com.storebooks.repository.PaymentRepository;
import com.storebooks.repository.ShippingAddressRepository;
import com.storebooks.service.BookService;
import com.storebooks.service.CartItemService;
import com.storebooks.service.OrderService;
import com.storebooks.utility.MailConstructor;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private BillingAddressRepository billingAddressRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private BookService bookService;
    @Autowired
    private MailConstructor mailConstructor;


    @Override
    public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress, Payment payment,String shippingMethod, User user) {

        Order order = new Order();
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setPayment(payment);
        order.setShippingMethod(shippingMethod);
        order.setOrderStatus("created");

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem: cartItemList) {
            Book book = cartItem.getBook();
            cartItem.setOrder(order);
            book.setInStockNumber(book.getInStockNumber() - cartItem.getQuantity());
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        order.setCartItemList(cartItemList);
        order.setOrderDate(timeFormatter.format(localDateTime));
        order.setOrderTotal(shoppingCart.getTotal().intValue());
        shippingAddress.setOrder(order);
        billingAddress.setOrder(order);
        payment.setOrder(order);
        order.setUser(user);

        order = orderRepository.save(order);

        return order;
    }

    @Override
    public Order findOne(int id) {
        return orderRepository.findOne(id);
    }


}