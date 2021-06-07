/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Gasana
 */
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    private String name;
    
    private Integer quantity;
 
    private Integer regularPrice;
   
    private Integer salePrice;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date publishedDate;

    private String unit;
  
    private String category;
    private String mainPhotoUrl;
   
    private String sidePhoto1Url;
   
    private String sidePhoto2Url;
   
    private String sidePhoto3Url;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date manufacturedDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expireDate;
    @ManyToOne
    private Staff staff;
    @Column(nullable = false)
    private String shopName;
    private String publishStatus;
    private String searchKey;
    private Integer totalRegularPrice;
    private Integer totalSalePrice;
    private String roomNo;
    
    
 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

   

    public Integer getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(Date manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public String getSidePhoto1Url() {
        return sidePhoto1Url;
    }

    public void setSidePhoto1Url(String sidePhoto1Url) {
        this.sidePhoto1Url = sidePhoto1Url;
    }

    public String getSidePhoto2Url() {
        return sidePhoto2Url;
    }

    public void setSidePhoto2Url(String sidePhoto2Url) {
        this.sidePhoto2Url = sidePhoto2Url;
    }

    public String getSidePhoto3Url() {
        return sidePhoto3Url;
    }

    public void setSidePhoto3Url(String sidePhoto3Url) {
        this.sidePhoto3Url = sidePhoto3Url;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

  
}
