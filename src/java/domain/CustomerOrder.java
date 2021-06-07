/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Gasana
 */
@Entity
public class CustomerOrder implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String productName;
    private Integer quantity;
    private Integer totalPrice;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderedDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderConfirmedDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderRejectedDate;
    
    private String unit;
    private Integer custId;
    private Integer proId;
    private String customerPhoneNumber;
    private String sellerPhoneNumber;
    private String orderStatus;
    private String payCode;
    private String customerName;
    private String sellerName;
    private String mainPhotoUrl;
    private String shopName;
    private Integer sellerId;
    private String category;
    private Integer totalRegularPrice;
    private Integer totalSalePrice;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Date getOrderConfirmedDate() {
        return orderConfirmedDate;
    }

    public void setOrderConfirmedDate(Date orderConfirmedDate) {
        this.orderConfirmedDate = orderConfirmedDate;
    }

   

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public void setSellerPhoneNumber(String sellerPhoneNumber) {
        this.sellerPhoneNumber = sellerPhoneNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Date getOrderRejectedDate() {
        return orderRejectedDate;
    }

    public void setOrderRejectedDate(Date orderRejectedDate) {
        this.orderRejectedDate = orderRejectedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalRegularPrice() {
        return totalRegularPrice;
    }

    public void setTotalRegularPrice(Integer totalRegularPrice) {
        this.totalRegularPrice = totalRegularPrice;
    }

    public Integer getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(Integer totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

 
    
}
