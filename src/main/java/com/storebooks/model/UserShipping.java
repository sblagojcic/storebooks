package com.storebooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserShipping {

    public static final long serialVervionUID = 443655L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String receiverName;
    private String street;
    private String city;
    private String zipcode;
    private Boolean defaultShipping;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Boolean getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(Boolean defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}