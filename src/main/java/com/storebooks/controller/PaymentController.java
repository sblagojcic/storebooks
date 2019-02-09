package com.storebooks.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storebooks.model.User;
import com.storebooks.model.UserBilling;
import com.storebooks.model.UserPayment;
import com.storebooks.service.UserPaymentService;
import com.storebooks.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPaymentService userPaymentService;
	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addNewUserPayment(@RequestBody UserPayment userPayment, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        UserBilling userBilling = userPayment.getUserBilling();
        userService.updateUserPayment(userPayment, userBilling, user);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping("/getUserPaymentList")
    public List<UserPayment> getUserPaymentList(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UserPayment> userPaymentList = user.getUserPaymentList();
        return userPaymentList;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity removeUserPayment(@RequestBody String id, Principal principal) {
    userPaymentService.removeById(Long.parseLong(id));
    return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    public ResponseEntity setDefaultPayment(@RequestBody String userPaymentId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        userService.setDefaultUserPayment(Long.parseLong(userPaymentId), user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
