package beans;

import dao.CustomerDao;
import dao.CustomerOperationsDao;
import dao.CustomerOrderDao;
import dao.MessageDao;
import dao.ProductDao;
import dao.SellerDao;
import dao.StaffDao;
import dao.UserDao;
import domain.Customer;
import domain.CustomerOperations;
import domain.CustomerOrder;
import domain.Message;
import domain.Product;
import domain.Seller;
import domain.Staff;
import domain.Users;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Temporal;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;
import utils.SendSMS;




@ManagedBean
@SessionScoped
public class Loginusecase {

	private Staff staff;
        private Customer customer;
        private Seller seller;
	private Users user;
	private Users usertoupdate;
        private SendSMS sendSMS = new SendSMS();
        private CustomerOperations customerOperations;
        private List<CustomerOperations> CustomerOperationss = new ArrayList<>();
        private CustomerOrder customerOrder;
        private List<CustomerOrder> customerOrders = new ArrayList<>();
        private List<Product> products = new ArrayList<>();
        private Message message;
        private MessageDao messageDao;
	private StaffDao staffdao;
	private UserDao userdao;
        private CustomerDao customerDao;
        private SellerDao sellerDao;
        private Product product;
        private ProductDao productDao;
        private CustomerOperationsDao customerOperationsDao;
        private CustomerOrderDao customerOrderDao;

	private String names;
        private String name;
        private String fn;
        private String ln;
	private String national;
	private String newPassword;
	private String confirmPassword;
        public String username;
        private Integer qty;
        private String businessName;
        private Integer number;
        private String productName;
        private Integer quantity;
        private Integer salePrice;
        private Integer regularPrice;
        private String shopName;
        private String category;
        private String searchKey;
        @Temporal(javax.persistence.TemporalType.DATE)
        private Date manufacturedDate;
        @Temporal(javax.persistence.TemporalType.DATE)
        private Date expireDate;
        private String unit;
        private String phoneNumbers;
        private String roomNo;
        private final String from_number ="+18563910106";
        private final String ACCOUNT_SID = "ACd41a133cb7e5d241f25c106bcaa6a538";
        private final String AUTH_TOKEN = "1356c2d34d5b48e094031a976dfacbe6";
        private String to_number;
        private String bodyMessage;
        


	public Loginusecase() {
		staff = new Staff();
		user = new Users();
		usertoupdate = new Users();
                customerOperations = new CustomerOperations();
                customerOrder = new CustomerOrder();
		staffdao = new StaffDao();
		userdao = new UserDao();
                customerDao = new CustomerDao();
                sellerDao = new SellerDao();
                product = new Product();
                productDao = new ProductDao();
                customerOperationsDao = new CustomerOperationsDao();
                customerOrderDao = new CustomerOrderDao();
                messageDao = new MessageDao();
                message = new Message();
               
	}

	public void successMessage(String summary) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, ""));
	}

	public void errorMessage(String summary) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ""));
	}
        
        public void warningMessage(String summary) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, ""));
	}

	public String logout() {
		user = new Users();
		usertoupdate = new Users();
		return "login.xhtml?faces-redirect=true";
	}

	public String login() {
		if (!user.getUsername().equalsIgnoreCase("ADMIN") && !user.getPassword().equalsIgnoreCase("ADMIN")) {
			Users us = userdao.getUsername(user.getUsername());
			if (us != null) {
				usertoupdate = us;
				if (us.getPassword().matches(createAnonymous(user.getPassword()))) {
					//if (us.getPassword().matches(user.getPassword())) {
						if (us.getStaff() != null) {
							if(us.getStaff().isIsState()) {
								staff = us.getStaff();
								names = staff.getFirstname() + " " + staff.getLastname();
								if(staff.getCategory().equalsIgnoreCase("supervisor")) {
									return "supervisor.xhtml?faces-redirect=true";	
								}
                                                                else if (staff.getCategory().equalsIgnoreCase("technician")) {
									return "technician.xhtml?faces-redirect=true";	
								}
                                                                else if (staff.getCategory().equalsIgnoreCase("donor")) {
									return "donor.xhtml?faces-redirect=true";	
								}
                                                                
                                                       
                                                                else {
									return "approverequest.xhtml?faces-redirect=true";
								}
							}else {
								errorMessage("Sorry you are blocked");
								return "loginn";
							}
						} 
						else {
                                                       // customer = us.getCustomer();
                                                       // seller = us.getSeller();
							//resident = us.getResident();
							//names = resident.getFirstname() + " " + resident.getLastname();
                                                        names = customer.getFirstname()+ " "+ customer.getLastname();
							return "donor.xhtml?faces-redirect=true";
						}
				} else {
					errorMessage("Invalid username or password");
					return "login";
				}
			} else {
				errorMessage("Invalid username or password");
				return "login";
			}
		} else {
			names = "ADMIN";
			return "admin-dashboard.xhtml?faces-redirect=true";
		}
	}


	public String loginAll() throws IOException {
             ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		if (!user.getUsername().equalsIgnoreCase("ADMIN") && !user.getPassword().equalsIgnoreCase("ADMIN")) {
			Users us = userdao.getUsername(user.getUsername());
			if (us != null) {
				usertoupdate = us;
				if (us.getPassword().matches(createAnonymous(user.getPassword()))) {
					//if (us.getPassword().matches(user.getPassword())) {
						
							if(us.getCategory().equalsIgnoreCase("Seller") && us.getStaff().isIsState()==true) {
								staff = us.getStaff();
								names = staff.getFirstname() + " " + staff.getLastname();
                                                                fn = staff.getFirstname();
                                                                ln = staff.getLastname();
                                                                businessName = staff.getBusinessName();
								return "seller-dashboard.xhtml?faces-redirect=true";	   
							}else if(us.getCategory().equalsIgnoreCase("Customer") && us.isActive()==true)
                                                        {
                                                                customer = us.getCustomer();
								names = customer.getFirstname() + " " + customer.getLastname();
                                                                phoneNumbers = customer.getPhonenumber();
                                                               
                                                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
								ec.redirect(ec.getRequestContextPath() + "/faces/customer-1/customer-dashboard.xhtml");
                                                                return "/faces/customer-1/customer-dashboard.xhtml";
							}
						
						else {
                                                     
							return "login.xhtml?faces-redirect=true";
						}
				} else {
					errorMessage("Invalid username or password");
					return "login.xhtml";
				}
			} else {
				errorMessage("Invalid username or password");
				return "login";
			}
		} else {
			names = "ADMIN";
                        System.out.println("AAAAAAAAAAAADMIN");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                        ec.redirect(ec.getRequestContextPath() + "/faces/admin/admin-dashboard.xhtml");
			return   "faces/admin/admin-dashboard.xhtml?faces-redirect=true";	
		}
         
	}


        public void resetPassword() {
		if (!user.getUsername().isEmpty() && user.getUsername() != null && !user.getPassword().isEmpty()
				&& user.getPassword() != null && !confirmPassword.isEmpty() && confirmPassword != null) {
			Users use = userdao.getUsername(user.getUsername());
			if (use != null) {
				if (user.getPassword().matches(confirmPassword)) {
					userdao.updateObject(user);
					user = new Users();
					confirmPassword = new String();
					successMessage("Your password reset Successfully");
				} else {
					errorMessage("Passwords not matched");
				}
			} else {
				errorMessage("Invalid username");
			}
		} else {
			errorMessage("Please fill all the fields first");
		}
	}
		public static String createAnonymous(String input) {

	        String md5 = null;

	        if (null == input) {
	            return null;
	        }

	        try {

	            //Create MessageDigest object for MD5
	            MessageDigest digest = MessageDigest.getInstance("MD5");

	            //Update input string in message digest
	            digest.update(input.getBytes(), 0, input.length());

	            //Converts message digest value in base 16 (hex) 
	            md5 = new BigInteger(1, digest.digest()).toString(20);

	        } catch (NoSuchAlgorithmException e) {

	            e.printStackTrace();
	        }
	        //System.out.println("encrypted password..."+md5);
	        return md5;
	    }

	public void signup() {
		if (!national.isEmpty() && national != null && !user.getUsername().isEmpty() && user.getUsername() != null
				&& !user.getPassword().isEmpty() && user.getPassword() != null && !newPassword.isEmpty()
				&& newPassword != null) {
                        Customer cust = customerDao.getNation(national);
                        Seller seller = sellerDao.getNation(national);
			Staff sta = staffdao.getNation(national);
			Users use = userdao.getUsername(this.user.getUsername());

			if (cust != null) {
				if (use == null) {
					if (this.user.getPassword().matches(newPassword)) {
						this.user.setPassword(createAnonymous(user.getPassword()));
						//this.user.setResident(fam);
                                               // this.user.setCustomer(cust);
                                               // this.user.setSeller(seller);
						this.user.setActive(true);
						userdao.recordObject(this.user);
						successMessage("Account well created");
					} else {
						errorMessage("Passwords not matched");
					}
				} else {
					errorMessage("Account already exists");
				}
			} else if (sta != null) {
				if (use == null) {
					if (this.user.getPassword().matches(newPassword)) {
						this.user.setPassword(createAnonymous(user.getPassword()));
						this.user.setStaff(sta);
						this.user.setActive(true);
						userdao.recordObject(this.user);
						successMessage("Account well created");
					} else {
						errorMessage("Passwords not matched");
					}
				} else {
					errorMessage("Account already exists");
				}
			} else {
				errorMessage("Please you are not recognised");
			}
			national = new String();
			user = new Users();
			newPassword = new String();
		} else {
			errorMessage("Please fill all the fields as required");
		}
	}
        
        
        
        public void signupAsStaff() {
		if (!national.isEmpty() && national != null && !user.getUsername().isEmpty() && user.getUsername() != null
				&& !user.getPassword().isEmpty() && user.getPassword() != null && !newPassword.isEmpty()
				&& newPassword != null) {
                         seller = sellerDao.getNation(national);
			Staff sta = staffdao.getNation(national);
			Users use = userdao.getUsername(this.user.getUsername());

			if (seller != null) {
				if (use == null) {
					if (this.user.getPassword().matches(newPassword)) {
						this.user.setPassword(createAnonymous(user.getPassword()));
                                               // this.user.setSeller(seller);
						this.user.setActive(true);
						userdao.recordObject(this.user);
						successMessage("Account well created");
					} else {
						errorMessage("Passwords not matched");
					}
				} else {
					errorMessage("Account already exists");
				}
			} else if (sta != null) {
				if (use == null) {
					if (this.user.getPassword().matches(newPassword)) {
						this.user.setPassword(createAnonymous(user.getPassword()));
                                                this.user.setCategory("Seller");
						this.user.setStaff(sta);
						this.user.setActive(true);
						userdao.recordObject(this.user);
						successMessage("Account well created");
					} else {
						errorMessage("Passwords not matched");
					}
				} else {
					errorMessage("Account already exists");
				}
			} else {
				errorMessage("Please you are not recognised");
			}
			national = new String();
			user = new Users();
			newPassword = new String();
		} else {
			errorMessage("Please fill all the fields as required");
		}
	}
        
        
         public void signupAsCustomer() {
		if (!national.isEmpty() && national != null && !user.getUsername().isEmpty() && user.getUsername() != null
				&& !user.getPassword().isEmpty() && user.getPassword() != null && !newPassword.isEmpty()
				&& newPassword != null) {
                        customer = customerDao.getNation(national);
			Staff sta = staffdao.getNation(national);
			Users use = userdao.getUsername(this.user.getUsername());

			if (customer != null) {
				if (use == null) {
					if (this.user.getPassword().matches(newPassword)) {
						this.user.setPassword(createAnonymous(user.getPassword()));
                                                this.user.setCustomer(customer);
                                                this.user.setCategory("Customer");
						this.user.setActive(true);
						userdao.recordObject(this.user);
						successMessage("Account well created");
					}else{
                                            errorMessage("Failed To validate Customer");
                                        } 
				}else{
                                    errorMessage("User exist");
                                }
			} else {
				errorMessage("Please you are not recognised");
			}
			national = new String();
			user = new Users();
			newPassword = new String();
		} else {
			errorMessage("Please fill all the fields as required");
		}
	}
         
         
         public String sendMessage(){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            Date todayDate = new Date();
            message.setCustomerName(names);
            message.setCustomerPhone(phoneNumbers);
            message.setSendDate(todayDate);
            message.setStatus("closed");
            messageDao.recordObject(message);
             return "contact-us.xhtml";
         }
         
         public String searchRoom(){
             
             if(roomNo.equalsIgnoreCase("101")){
                 return "room-101.xhtml";
             }else if(roomNo.equalsIgnoreCase("102")){
                 return "room-102.xhtml";
                 
             }else if(roomNo.equalsIgnoreCase("102")){
                 return "room-102.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("103")){
                 return "room-103.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("104")){
                 return "room-104.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("105")){
                 return "room-105.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("201")){
                 return "room-201.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("202")){
                 return "room-202.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("203")){
                 return "room-203.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("204")){
                 return "room-204.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("205")){
                 return "room-205.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("301")){
                 return "room-301.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("302")){
                 return "room-302.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("303")){
                 return "room-303.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("304")){
                 return "room-304.xhtml";
                 
             }
             else if(roomNo.equalsIgnoreCase("305")){
                 return "room-305.xhtml";
                 
             }
             else{
                 return "customer-dashboard.xhtml";
             }
                     
         }
         
         public List<Product> productsByRoom301(){
             products = productDao.findAllProdsByRoom301();
             return products;
         }
         public Integer productsByRoom301Count(){
             Integer c = productDao.findAllProdsByRoom301().size();
             return c;
         }
         public List<Product> productsByRoom302(){
             products = productDao.findAllProdsByRoom302();
             return products;
         }
         public Integer productsByRoom302Count(){
             Integer c = productDao.findAllProdsByRoom302().size();
             return c;
         }
         public List<Product> productsByRoom303(){
             products = productDao.findAllProdsByRoom303();
             return products;
         }
         public Integer productsByRoom303Count(){
             Integer c = productDao.findAllProdsByRoom303().size();
             return c;
         }
         public List<Product> productsByRoom304(){
             products = productDao.findAllProdsByRoom304();
             return products;
         }
         public Integer productsByRoom304Count(){
             Integer c = productDao.findAllProdsByRoom304().size();
             return c;
         }
         public List<Product> productsByRoom305(){
             products = productDao.findAllProdsByRoom305();
             return products;
         }
         public Integer productsByRoom305Count(){
             Integer c = productDao.findAllProdsByRoom305().size();
             return c;
         }
         
         public List<Product> productsByRoom201(){
             products = productDao.findAllProdsByRoom201();
             return products;
         }
         
         public Integer productsByRoom201Count(){
             Integer c = productDao.findAllProdsByRoom201().size();
             return c;
         }
         public List<Product> productsByRoom202(){
             products = productDao.findAllProdsByRoom202();
             return products;
         }
         
         public Integer productsByRoom202Count(){
             Integer c = productDao.findAllProdsByRoom202().size();
             return c;
         }
         
         public List<Product> productsByRoom203(){
             products = productDao.findAllProdsByRoom203();
             return products;
         }
         
         public Integer productsByRoom203Count(){
             Integer c = productDao.findAllProdsByRoom203().size();
             return c;
         }
         public List<Product> productsByRoom204(){
             products = productDao.findAllProdsByRoom204();
             return products;
         }
         public Integer productsByRoom204Count(){
             Integer c = productDao.findAllProdsByRoom204().size();
             return c;
         }
         public List<Product> productsByRoom205(){
             products = productDao.findAllProdsByRoom205();
             return products;
         }
         public Integer productsByRoom205Count(){
             Integer c = productDao.findAllProdsByRoom205().size();
             return c;
         }
         public List<Product> productsByRoom101(){
             products = productDao.findAllProdsByRoom101();
             return products;
         }
         public Integer productsByRoom101Count(){
             Integer c = productDao.findAllProdsByRoom101().size();
             return c;
         }
         public List<Product> productsByRoom102(){
             products = productDao.findAllProdsByRoom102();
             return products;
         }
         public Integer productsByRoom102Count(){
             Integer c = productDao.findAllProdsByRoom102().size();
             return c;
         }
         public List<Product> productsByRoom103(){
             products = productDao.findAllProdsByRoom103();
             return products;
         }
         public Integer productsByRoom103Count(){
             Integer c = productDao.findAllProdsByRoom103().size();
             return c;
         }
         public List<Product> productsByRoom104(){
             products = productDao.findAllProdsByRoom104();
             return products;
         }
         public Integer productsByRoom104Count(){
             Integer c = productDao.findAllProdsByRoom104().size();
             return c;
         }
         
         public List<Product> productsByRoom105(){
             products = productDao.findAllProdsByRoom105();
             return products;
         }
         public Integer productsByRoom105Count(){
             Integer c = productDao.findAllProdsByRoom105().size();
             return c;
         }
         
         public List<CustomerOrder> acceptedOrders(){
             customerOrders = customerOrderDao.findOrdersBySellerAccepted();
             return customerOrders;
         }
         
         public String recordProduct(){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            Date todayDate = new Date();  
    
             
             
             try {
                 
               Users usr = userdao.getUsername(this.user.getUsername());
               product.setStaff(usr.getStaff());
               product.setPublishedDate(todayDate);
               product.setRoomNo(usr.getStaff().getRoomNo());
               product.setPublishStatus("Activated");
               product.setShopName(staff.getBusinessName());
               product.setSearchKey("P-"+product.getId()+"-S-"+usr.getStaff().getId());
               productDao.recordObject(product);
               product.setSearchKey("P-"+product.getId()+"-S-"+usr.getStaff().getId());
               product.setTotalRegularPrice(product.getRegularPrice()*product.getQuantity());
               product.setTotalSalePrice(product.getSalePrice()*product.getQuantity());
               productDao.updateObject(product);
               successMessage(product.getName()+" Is Saved Successfully!");
                 
                 return "record-product.xhtml";
             } catch (Exception e) {
                 System.out.println("RECORD PRODUCT ERRORRRR "+e);
                 errorMessage("Failed To Be Saved!");
                 return "record-product.xhtml";
             }
         }
         
         public String updateProduct(Integer id){
           Users usr = userdao.getUsername(this.user.getUsername());
           staff = usr.getStaff();
           
           product = productDao.findOne(Product.class, id);
             try {
                product.setName(productName);
           product.setCategory(category);
           product.setRegularPrice(regularPrice);
           product.setSalePrice(salePrice);
           product.setManufacturedDate(manufacturedDate);
           product.setExpireDate(expireDate);
           product.setUnit(unit);
           product.setPublishStatus("Activated");
           productDao.updateObject(product);
           
           Integer q = product.getQuantity();
           Integer nq = q+qty;
           Integer trp = product.getRegularPrice()*nq;
           Integer tsp = product.getSalePrice()*nq;
           
           product.setTotalRegularPrice(trp);
           product.setTotalSalePrice(tsp);
           product.setQuantity(nq);
           productDao.updateObject(product);
                 successMessage(product.getName()+" Is Updated Successfully!");
            return "update-product.xhtml"; 
             } catch (Exception e) {
                 errorMessage(product.getName()+" Fill All The Fields");
                 return "update-product.xhtml"; 
             }
           
         }
         
         public String deactivateProductById(Integer id){
             product = productDao.findOne(Product.class, id);
             product.setPublishStatus("Deactivated");
             productDao.updateObject(product);
             successMessage(product.getName()+" Is Deactivated From Other Products!");
             return "active-products.xhtml";
         }
         
          public String deactivateSellerAccount(Integer id){
         
             staff = staffdao.findOne(Staff.class, id);
             staff.setIsState(false);
             staffdao.updateObject(staff);
             successMessage(staff.getFirstname()+" "+staff.getFirstname()+" Account Is Deactivated");
             return "seller-accounts";
            }
          
          public String deactivateCustomerAccount(Integer id){
             user = userdao.findOne(Users.class, id);
             user.setActive(false);
             userdao.updateObject(user);
             successMessage(user.getCustomer().getFirstname()+" "+user.getCustomer().getLastname()+" Account Is Deactivated");
             return "customer-accounts";
            }
          
           public String activateCustomerAccount(Integer id){
             user = userdao.findOne(Users.class, id);
             user.setActive(true);
             userdao.updateObject(user);
             successMessage(user.getCustomer().getFirstname()+" "+user.getCustomer().getLastname()+" Account Is Activated");
             return "customer-accounts";
            }
          
          public String activateSellerAccount(Integer id){
         
             staff = staffdao.findOne(Staff.class, id);
             staff.setIsState(true);
             staffdao.updateObject(staff);
             successMessage(staff.getFirstname()+" "+staff.getFirstname()+" Account Is Activated");
             return "seller-accounts-deactive.xhtml";
            }
         
         public String activateProductById(Integer id){
             product = productDao.findOne(Product.class, id);
             product.setPublishStatus("Activated");
             productDao.updateObject(product);
             successMessage(product.getName()+" Is Now Activated!");
             return "inactive-products.xhtml";
         }
         
         public Integer getTotalOrders(){
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             customerOrders = customerOrderDao.totalOrders(shopName);
             Integer totalOrder = customerOrders.size();
             return totalOrder;
        }
        
        public Integer getTotalAcceptedOrders(){
             customerOrders = customerOrderDao.totalAcceptedOrders();
             Integer totalAcceptedOrders = customerOrders.size();
             return totalAcceptedOrders;
        }
        public Integer getTotalRejectedOrders(){
             customerOrders = customerOrderDao.totalRjectedOrders();
             Integer totalAcceptedOrders = customerOrders.size();
             return totalAcceptedOrders;
        }
        
        public String totalPriceAcceptedOrdersBySellerOrders(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = customerOrderDao.totalAmountAcceptedOrdersBySeller();
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
         
         public String totalPriceRejectedOrdersBySellerOrders(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = customerOrderDao.totalAmountRejectedOrdersBySeller();
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
         
         public Integer totalAmountFoodOrderAccepted(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             
             Integer k =0;
             Integer kk=0;
             try {
                BigDecimal fq = customerOrderDao.totalPriceOrderAcceptedByFood();
                Integer d = fq.intValue();
               // k = decimalFormat.format(d);
                return d;
            } catch (Exception e) {
                 return kk;
            }
        }
         
          public Integer totalAmountClothOrderAccepted(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             
             Integer k =0;
             Integer kk=0;
             try {
                BigDecimal fq = customerOrderDao.totalPriceOrderAcceptedByCloth();
                Integer d = fq.intValue();
               // k = decimalFormat.format(d);
                return d;
            } catch (Exception e) {
                 return kk;
            }
        }
          
         public Integer totalAmountDeviceOrderAccepted(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             
             Integer k =0;
             Integer kk=0;
             try {
                BigDecimal fq = customerOrderDao.totalPriceOrderAcceptedByDevice();
                Integer d = fq.intValue();
               // k = decimalFormat.format(d);
                return d;
            } catch (Exception e) {
                 return kk;
            }
        } 
         
          public Integer totalAmountOtherOrderAccepted(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             
             Integer k =0;
             Integer kk=0;
             try {
                BigDecimal fq = customerOrderDao.totalPriceOrderAcceptedByOther();
                Integer d = fq.intValue();
               // k = decimalFormat.format(d);
                return d;
            } catch (Exception e) {
                 return kk;
            }
        }
         
        public String totalFoodQuantity(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalFoodQuantityBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        } 
        
        
         public String totalDeviceQuantity(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalDeviceQuantityBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        } 
         
       public String totalClothesQuantity(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalClothesQuantityBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        } 
       
       public String totalOtherQuantity(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalOtherQuantityBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
       
       
       public String totalQuantityProdutcsBySeller(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalQuantityBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
       
       
        public String totalProductCount(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
 
             String k ="";
             String kk="0";
             try {
                Integer fq = productDao.findAllProds().size();
               
                k = decimalFormat.format(fq);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        public String totalProductCountFloor1(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            String category1 = "Other";
            String category2 = "Food";
 
             String k ="";
             String kk="0";
             try {
                Integer fq = productDao.findProductsByFloor1(category1, category2).size();
               
                k = decimalFormat.format(fq);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        
        public String totalProductCountFloor2(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            String category1 = "Device";
            String category2 = "Device";
 
             String k ="";
             String kk="0";
             try {
                Integer fq = productDao.findProductsByFloor(category1, category2).size();
               
                k = decimalFormat.format(fq);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        public String totalProductCountFloor3(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            String category1 = "Cloth";
            String category2 = "Cloth";
 
             String k ="";
             String kk="0";
             try {
                Integer fq = productDao.findProductsByFloor(category1, category2).size();
               
                k = decimalFormat.format(fq);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }

       
       public String totalQuantityBySellerOrders(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = customerOrderDao.totalQuantityBySellerOrders(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
       
        public String totalRegularPriceBySellerOrders(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = customerOrderDao.totalRegularPriceBySellerOrders(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        public String totalSalePriceBySellerOrders(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = customerOrderDao.totalSalePriceBySellerOrders(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        public String totalCartAmount(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             customer = usr.getCustomer();
             Integer custId = customer.getId();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = customerOperationsDao.totalCartAmount(custId);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        
        public String totalNetProfitBySellerOrders(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal tr = customerOrderDao.totalRegularPriceBySellerOrders(shopName); 
                BigDecimal ts = customerOrderDao.totalSalePriceBySellerOrders(shopName);
                Integer d = tr.intValue();
                Integer dd = ts.intValue();
                Integer o = dd-d;
                k = decimalFormat.format(o);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
        
        
        
       
       public String totalRegularPriceProdutcsBySeller(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalRegularPriceBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
       
       
       public String totalSalePriceProdutcsBySeller(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal fq = productDao.totalSalePriceBySeller(shopName);
                Integer d = fq.intValue();
                k = decimalFormat.format(d);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
       
       
       public String totalNetProfitProdutcsBySeller(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k ="";
             String kk="0";
             try {
                BigDecimal tr = productDao.totalRegularPriceBySeller(shopName); 
                BigDecimal ts = productDao.totalSalePriceBySeller(shopName);
                
                Integer d = tr.intValue();
                Integer dd = ts.intValue();
                Integer o = dd-d;
                k = decimalFormat.format(o);
                return k;
            } catch (Exception e) {
                 return kk;
            }
        }
       
       
         
        public String totalInvestmentBySeller(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            Integer totalInvestment=0;
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k="";
             String kk="0";
             try {
             BigDecimal totalInvestments = productDao.totalInvestmentBySeller(shopName);
             Integer d = totalInvestments.intValue();
              k = decimalFormat.format(d);
             return  ""+k;
            } catch (Exception e) {
                 return kk;
            }
               
        }
        
        public String totalSalesBySeller(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            
            
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             String k="";
             String kk="0";
             try {
             BigDecimal totalSales = customerOrderDao.totalSalesBySeller(shopName);
             Integer d = totalSales.intValue();
              k = decimalFormat.format(d);
             return ""+k;   
            } catch (Exception e) {
                 return kk;
            }
            
        }
        
        public Integer totalInvestmentBySellerPercentage(){
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             Integer p=0;
             try {
             BigDecimal totalInvestment = productDao.totalInvestmentBySeller(shopName);
             totalInvestment.intValue();
             Integer tt=100000000;
             Integer percentage;
             percentage = (100*totalInvestment.intValue())/tt;
             return percentage;  
            } catch (Exception e) {
                 return p;
            }
              
        }
        
        public Integer totalSalesBySellerPercentage(){
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             Integer p=0;
             try {
             BigDecimal totalInvestment = productDao.totalInvestmentBySeller(shopName);
             BigDecimal totalSales = customerOrderDao.totalSalesBySeller(shopName);
             totalSales.intValue();
             Integer tt=totalInvestment.intValue();
             Integer percentage;
             percentage = (100*totalSales.intValue())/tt;
             return percentage;  
            } catch (Exception e) {
                return p;
            }
             
              
        }
        
         public String totalRenaining(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
             
             
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             
             String remm="0";
             try{
             BigDecimal totalInvestment = productDao.totalInvestmentBySeller(shopName);
             BigDecimal totalSales = customerOrderDao.totalSalesBySeller(shopName);
             totalSales.intValue();
             Integer ti=totalInvestment.intValue();
             Integer ts =totalSales.intValue();
             
             Integer remainings =ti-ts;
             String rem = decimalFormat.format(remainings);
             return ""+rem;   
             }catch(Exception e){
                 return remm;
             }
              
        }
         
        public Integer totalRenainingPercentage(){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
             
             
             Users usr = userdao.getUsername(this.user.getUsername());
             staff = usr.getStaff();
             shopName = staff.getBusinessName();
             Integer remP=0;
             try {
             BigDecimal totalInvestment = productDao.totalInvestmentBySeller(shopName);
             BigDecimal totalSales = customerOrderDao.totalSalesBySeller(shopName);
             totalSales.intValue();
             Integer ti=totalInvestment.intValue();
             Integer ts =totalSales.intValue();
             Integer percentage = (100*ts)/ti;
             Integer totP = 100-percentage;
             return totP;  
            } catch (Exception e) {
                 return remP;
            }
             
              
        } 
        
        
        
     
         
         
         public List<Product> getAllProductsByStaff(){
            Users usr = userdao.getUsername(this.user.getUsername());
            Integer id = usr.getStaff().getId();
            List<Product> pds = productDao.findProductsByStaff(id);
           return pds;
         }
         
         public List<Product> getAllFinishedProductsByStaff(){
            Users usr = userdao.getUsername(this.user.getUsername());
            Integer id = usr.getStaff().getId();
            List<Product> pds = productDao.findFinishedProductsByStaff(id);
           return pds;
         }
         
         public List<Product> getAllProductsByStaffInactive(){
            Users usr = userdao.getUsername(this.user.getUsername());
            Integer id = usr.getStaff().getId();
            List<Product> pds = productDao.findProductsByStaffInactive(id);
           return pds;
         }
         
         public List<CustomerOrder> getAllOrdersByCustomer(){
            Users usr = userdao.getUsername(this.user.getUsername());
            Integer id = usr.getCustomer().getId();
            customerOrders = customerOrderDao.findCustomerOrderByCustomerId(id);
           return customerOrders ;
         }
         
          public List<CustomerOrder> getAllOrdersByCustomerAccepted(){
            Users usr = userdao.getUsername(this.user.getUsername());
            Integer id = usr.getCustomer().getId();
            customerOrders = customerOrderDao.findCustomerOrderByCustomerIdAccepted(id);
            Collections.reverse(customerOrders);
           return customerOrders ;
         }
          
           public List<CustomerOrder> getAllOrdersByCustomerRejected(){
            Users usr = userdao.getUsername(this.user.getUsername());
            Integer id = usr.getCustomer().getId();
            customerOrders = customerOrderDao.findCustomerOrderByCustomerIdRejected(id);
            Collections.reverse(customerOrders);
           return customerOrders ;
         }
         
         public List<CustomerOrder> getAllOrders(){
            Users usr = userdao.getUsername(this.user.getUsername());
            String shopName = usr.getStaff().getBusinessName();
            customerOrders = customerOrderDao.findOrdersBySellerShop(shopName);
            return customerOrders;
         }
         
         public List<CustomerOperations> getLikedProdsBySellerShop(){
            Users usr = userdao.getUsername(this.user.getUsername());
             shopName = usr.getStaff().getBusinessName();
            CustomerOperationss = customerOperationsDao.findLikedBySellerShop(shopName);
            return CustomerOperationss;
         }
         
          public List<CustomerOrder> getAllOrdersAccepted(){
            Users usr = userdao.getUsername(this.user.getUsername());
            String shopName = usr.getStaff().getBusinessName();
            customerOrders = customerOrderDao.findOrdersBySellerShopAccepted(shopName);
            return customerOrders;
         }
          
          public List<CustomerOrder> getAllOrdersRejected(){
            Users usr = userdao.getUsername(this.user.getUsername());
            String shopName = usr.getStaff().getBusinessName();
            customerOrders = customerOrderDao.findOrdersBySellerShopRejected(shopName);
            return customerOrders;
         } 
         
         public String acceptOrderFromCustomer(Integer id,Integer orderId){
            product = productDao.findOne(Product.class, id);
            customerOrder = customerOrderDao.findOne(CustomerOrder.class, orderId);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            Date todayDate = new Date();
            Users usr = userdao.getUsername(this.user.getUsername());
            staff = usr.getStaff();
           
            customerOrder.setSellerName(staff.getFirstname()+" "+staff.getLastname());
            customerOrder.setPayCode(staff.getPayCode()+product.getId()+"*"+customerOrder.getId()+"#");
            customerOrder.setSellerPhoneNumber(staff.getPhonenumber());
            customerOrder.setOrderConfirmedDate(todayDate);
            customerOrder.setOrderStatus("Accepted");
            customerOrder.setCategory(product.getCategory());
            customerOrderDao.updateObject(customerOrder);
            to_number  = customerOrder.getCustomerPhoneNumber();
            if(to_number.startsWith("+25")){
                 bodyMessage = "YOUR ORDER HAVE BEEN ACCEPTED FROM "+customerOrder.getShopName().toUpperCase() +"\n"
                    + "PRODUCT NAME  : "+customerOrder.getProductName()+"\n"
                    + "PRODUCT QTY   : "+customerOrder.getQuantity()+"\n"
                    + "AMOUNT TO PAY : "+customerOrder.getTotalPrice()+"\n"
                    + "PAYMENT CODE  : "+customerOrder.getPayCode();
            sendSMS.sendSMS(to_number, from_number, bodyMessage);
             
             successMessage(customerOrder.getProductName()+" Order Is Accepted!");
            return "ordered-products.xhtml?faces=redirect=true";
                
            }else{
              String start="+25";
              bodyMessage = "YOUR ORDER HAVE BEEN ACCEPTED FROM "+customerOrder.getShopName().toUpperCase() +"\n"
                    + "PRODUCT NAME  : "+customerOrder.getProductName()+"\n"
                    + "PRODUCT QTY   : "+customerOrder.getQuantity()+"\n"
                    + "AMOUNT TO PAY : "+customerOrder.getTotalPrice()+"\n"
                    + "PAYMENT CODE  : "+customerOrder.getPayCode();
            sendSMS.sendSMS(start+to_number, from_number, bodyMessage);
             
             successMessage(customerOrder.getProductName()+" Order Is Accepted!");
                return "ordered-products.xhtml?faces=redirect=true";
            }
           
        }
         
        public String rejectOrderFromCustomer(Integer id,Integer orderId){
            product = productDao.findOne(Product.class, id);
            customerOrder = customerOrderDao.findOne(CustomerOrder.class, orderId);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
            Date todayDate = new Date();
            Users usr = userdao.getUsername(this.user.getUsername());
            staff = usr.getStaff();
            customerOrder.setOrderRejectedDate(todayDate);
            customerOrder.setSellerName("Hidden");
            customerOrder.setPayCode("Hidden");
            customerOrder.setSellerPhoneNumber("Hidden");
            customerOrder.setOrderStatus("Rejected");
            to_number = customerOrder.getCustomerPhoneNumber();
            String startN = "+25";
            bodyMessage = customerOrder.getShopName().toUpperCase() +" REJECTED YOUR "+customerOrder.getProductName().toUpperCase()+" ORDER";
            if(to_number.startsWith("+25")){
                sendSMS.sendSMS(to_number, from_number, bodyMessage);
            }else{
                sendSMS.sendSMS(startN+to_number, from_number, bodyMessage);
            }
            if(product.getQuantity()==0){
            qty =  customerOrder.getQuantity();
            System.out.println(" customer order qty 1 "+customerOrder.getQuantity());
            System.out.println(" product qty 2 "+product.getQuantity());
            product.setQuantity(qty);
            product.setPublishStatus("Activated");
            productDao.updateObject(product);
            customerOrderDao.updateObject(customerOrder);
            
            return "ordered-products.xhtml?faces=redirect=true";
            }else{
            qty = product.getQuantity() + customerOrder.getQuantity();
            System.out.println(" customer order qty "+customerOrder.getQuantity());
            System.out.println(" product qty "+product.getQuantity());
            product.setQuantity(qty);
            product.setPublishStatus("Activated");
            productDao.updateObject(product);
            customerOrderDao.updateObject(customerOrder);
            return "ordered-products.xhtml?faces=redirect=true";
            }
           
        }
        
        public String cancelOrderFromCustomer(Integer id,Integer orderId){
            product = productDao.findOne(Product.class, id);
            customerOrder = customerOrderDao.findOne(CustomerOrder.class, orderId);
            qty = product.getQuantity()+customerOrder.getQuantity();
            product.setQuantity(qty);
            productDao.updateObject(product);
            customerOrderDao.deleteObject(customerOrder);
            return "my-orders.xhtml?faces=redirect=true";
        } 
         
         public String likeProductByIdDetails(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
                successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "product-details.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "product-details.xhtml?faces=redirect=true";
           }
        }
         
         
        public String likeProductByIdRoom101(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-101.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-101.xhtml";
           }
        } 
        
        public String likeProductByIdRoom202(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-202.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-202.xhtml";
           }
        }
        
        public String likeProductByIdRoom201(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-201.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-201.xhtml";
           }
        }
        public String likeProductByIdRoom103(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-103.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-103.xhtml";
           }
        } 
        public String likeProductByIdRoom104(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-104.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-104.xhtml";
           }
        } 
        public String likeProductByIdRoom105(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-105.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-105.xhtml";
           }
        } 
        public String likeProductByIdRoom205(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-205.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-205.xhtml";
           }
        } 
        public String likeProductByIdRoom204(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-204.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-204.xhtml";
           }
        } 
        public String likeProductByIdRoom203(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-203.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-203.xhtml";
           }
        }
        
        public String likeProductByIdRoom304(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-304.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-304.xhtml";
           }
        }
        
        public String likeProductByIdRoom305(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-305.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-305.xhtml";
           }
        }
        
         public String likeProductByIdRoom303(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-303.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-303.xhtml";
           }
        } 
        
        public String likeProductByIdRoom302(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-302.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-302.xhtml";
           }
        } 
        
        public String likeProductByIdRoom301(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-301.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-301.xhtml";
           }
        } 
        
        public String likeProductByIdRoom102(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "room-102.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "room-102.xhtml";
           }
        } 
       public String likeProductById(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "customer-dashboard.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "customer-dashboard.xhtml";
           }
        }
       
       public String likeProductByIdShop(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();
        //String searchKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
        
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
           
      
            try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
       
       successMessage(customerOperations.getName()+" Is Added To Wish List!");
       
          return "shop.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "shop.xhtml";
           }
        }
       
       
       public String likeProductByIdFoodCategory(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);  
               successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "category-food.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "category-food.xhtml?faces=redirect=true";
           }
       
        
     
       
        
        }
       
        public String likeProductByIdDeviceCategory(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "category-device.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "category-device.xhtml?faces=redirect=true";
           }
 
        
        }
        
        
        public String likeProductByIdClothCategory(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "category-cloth.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "category-cloth.xhtml?faces=redirect=true";
           }
       
 
        }
        
        
        public String likeProductByIdOtherCategory(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "category-other.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "category-other.xhtml?faces=redirect=true";
           }
        
        }
        
        public String likeProductByIdFloor1(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "floor-1.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "floor-1.xhtml";
           }
       
        }
        
        public String likeProductByIdFloor2(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations); 
               successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "floor-2.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Wish List!");
               return "floor-2.xhtml";
           }
          
        }
        public String likeProductByIdFloor3(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("liked");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Wish List!");
          return "floor-3.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already To Wish List!");
               return "floor-3.xhtml";
           }
 
        }
        
        public String removeProductFromLikedList(Integer id){
            product = productDao.findOne(Product.class, id);
            Users usr = userdao.getUsername(this.user.getUsername());
            customer = usr.getCustomer();
            String searchKey = "P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked";
            customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
            customerOperationsDao.deleteObject(customerOperations);
            successMessage(customerOperations.getName()+" Is Removed From Wish List!");
            return "E-com/faces/customer-1/my-wishlist.xhtml?faces=redirect=true";
        }
        
        public String removeProductFromAddedList(Integer id){
            product = productDao.findOne(Product.class, id);
            Users usr = userdao.getUsername(this.user.getUsername());
            customer = usr.getCustomer();
            String searchKey = "P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
            customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
            customerOperationsDao.deleteObject(customerOperations);
            return "my-cart.xhtml";
        }
        
        public String calculateProductFromAddedList(Integer id){
            product = productDao.findOne(Product.class, id);
            Users usr = userdao.getUsername(this.user.getUsername());
            customer = usr.getCustomer();
            String searchKey = "P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
            customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
            if(qty==null || qty==0){
               return "my-cart.xhtml"; 
            }
            else if(qty<=product.getQuantity()){
            customerOperations.setTotalPrice(customerOperations.getSalePrice()*qty);
            customerOperations.setMyQuantity(qty);
            customerOperationsDao.updateObject(customerOperations);
            return "my-cart.xhtml";
            }else{
            return "my-cart.xhtml";
            }
            
        }
        
        
        
        
        public String refreshProductFromAddedList(Integer id){
            product = productDao.findOne(Product.class, id);
            Users usr = userdao.getUsername(this.user.getUsername());
            customer = usr.getCustomer();
            searchKey = "P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
            customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
            customerOperations.setTotalPrice(0);
            customerOperations.setMyQuantity(0);
            customerOperationsDao.updateObject(customerOperations);
            return "cart-list.xhtml?faces=redirect=true";    
        }
        
        public String addProductToCartList(Integer id){
            product = productDao.findOne(Product.class, id);
            Users usr = userdao.getUsername(this.user.getUsername());
            customer = usr.getCustomer();
            String searchKey = "P-"+product.getId()+"-C-"+customer.getId()+"-S-Liked";
            customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
            String updatedKey="P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
            try {
            customerOperations.setOperationName("added");
            customerOperations.setSearchKey(updatedKey);
            customerOperationsDao.updateObject(customerOperations);
            successMessage(customerOperations.getOperationName()+" Is Added To Cart!");
            return "E-Com/faces/customer-1/my-wishlist.xhtml?faces=redirect=true";
            } catch (Exception e) {
                System.out.println("ALready Exist "+e);
                return "E-Com/faces/customer-1/my-wishlist.xhtml?faces=redirect=true";
            }
            
        }
        
        
        public String addProductToCartListDetails(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations); 
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "product-details.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "product-details.xhtml?faces=redirect=true";
           }
           
        
        }
        
        
        public String addProductToOrderFromCartList(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       String searchKey = "P-"+product.getId()+"-C-"+customer.getId()+"-S-Added";
       customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
       
       if(customerOperations.getMyQuantity()>0){
           
           
           
            try {
               //CUSTOMER ORDER OPERATIONS
       //---------------------------
       customerOrder.setCustId(customer.getId());
       customerOrder.setProId(product.getId());
       customerOrder.setOrderedDate(todayDate);
       customerOrder.setCustomerPhoneNumber(customer.getPhonenumber());
       customerOrder.setOrderStatus("Pending");
       customerOrder.setPayCode("Hidden");
       customerOrder.setProductName(product.getName());
       customerOrder.setQuantity(customerOperations.getMyQuantity());
       customerOrder.setUnit(customerOperations.getUnit());
       customerOrder.setSellerPhoneNumber("Hidden");
       customerOrder.setSellerName("Hidden");
       customerOrder.setSellerId(product.getStaff().getId());
       customerOrder.setCustomerName(customer.getFirstname()+" "+customer.getLastname());
       customerOrder.setTotalPrice(customerOperations.getTotalPrice());
       customerOrder.setMainPhotoUrl(customerOperations.getMainPhotoUrl());
       customerOrder.setShopName(customerOperations.getShopName());
       customerOrder.setTotalRegularPrice(customerOperations.getTotalRegularPrice());
       customerOrder.setTotalSalePrice(customerOperations.getTotalSalePrice());
       // REDUCTING QTY ON PRODUCT
       
       Integer prodQty=product.getQuantity();
       Integer rQty = prodQty-qty;
       product.setQuantity(rQty);
       if(product.getQuantity()==0){
           
           product.setPublishStatus("Finished");
           productDao.updateObject(product); 
           customerOrderDao.recordObject(customerOrder);
           customerOperationsDao.deleteObject(customerOperations);
           return "my-cart.xhtml";  
       }else{
           
           to_number = product.getStaff().getPhonenumber();
           String strN = "+25";
           bodyMessage =customer.getFirstname().toUpperCase()+" "+customer.getLastname().toUpperCase()+" ORDERED "+qty+" "+product.getName()+" "+" QUICKLY"
                   + " GO AND ACCEPT THE ORDER TO BE PAID";
           if(to_number.startsWith(strN)){
               sendSMS.sendSMS(to_number, from_number, bodyMessage);
           }else{
               sendSMS.sendSMS(strN+to_number, from_number, bodyMessage);
           }
           
       product.setQuantity(rQty);  
       productDao.updateObject(product);          
       customerOrderDao.recordObject(customerOrder);
       customerOperationsDao.deleteObject(customerOperations);
       return "my-cart.xhtml";  
       }
         
           }
            catch (Exception e) {
                
               errorMessage("This Product is Already Added "+e);
               return "my-cart.xhtml";
           }
           
       }else{
           return "my-cart.xhtml";
       }
     
    }
        
        public String addProductToCartListRoom202(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-202.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-202.xhtml";
           }
           
        
        }
        public String addProductToCartListRoom201(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-201.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-201.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom103(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-103.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-103.xhtml";
           }
           
        
        }
        public String addProductToCartListRoom104(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-104.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-104.xhtml";
           }
           
        
        }
        public String addProductToCartListRoom105(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-105.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-105.xhtml";
           }
           
        
        }
        
         public String addProductToCartListRoom205(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-205.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-205.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom204(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-204.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-204.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom203(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-203.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-203.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom304(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-304.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-304.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom305(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-305.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-305.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom303(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-303.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-303.xhtml";
           }
           
        
        }
        
         public String addProductToCartListRoom302(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-302.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-302.xhtml";
           }
           
        
        }
        
         public String addProductToCartListRoom301(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-301.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-301.xhtml";
           }
           
        
        }
        
        public String addProductToCartListRoom102(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-102.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-102.xhtml";
           }
           
        
        }
       
         public String addProductToCartListRoom101(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "room-101.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "room-101.xhtml";
           }
           
        
        }
        
        public String addProductToCartListDash(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "customer-dashboard.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "customer-dashboard.xhtml";
           }
           
        
        }
        
        
        public String addProductToCartListShop(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
       
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
        
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "shop.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "shop.xhtml";
           }
           
        
        }
        
        public String addProductToCartListFoodCategory(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "category-food.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "category-food.xhtml?faces=redirect=true";
           }
           
        
        }
        
         public String addProductToCartListDeviceCategory(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations); 
        successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "category-device.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "category-device.xhtml?faces=redirect=true";
           }
           
        
        }
         
         public String addProductToCartListClothCategory(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "category-cloth.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "category-cloth.xhtml?faces=redirect=true";
           }
           
        
        }
         
        public String addProductToCartListOtherCategory(Integer id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations); 
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "category-other.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "category-other.xhtml?faces=redirect=true";
           }
           
        
        } 
        
        public String addProductToCartListFloor1(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "floor-1.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "floor-1.xhtml";
           }
           
        
        }  
        public String addProductToCartListFloor2(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "floor-2.xhtml?faces=redirect=true";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "floor-2.xhtml?faces=redirect=true";
           }
           
        
        } 
        public String addProductToCartListFloor3(Integer id){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date todayDate = new Date();    
           
           
       Users usr = userdao.getUsername(this.user.getUsername());
       customer = usr.getCustomer();
       product = productDao.findOne(Product.class, id);
       
           try {
               //CUSTOMER OPERATIONS
       //---------------------------
       customerOperations.setOperationName("added");
       customerOperations.setCustId(customer.getId());
       customerOperations.setName(product.getName());
       customerOperations.setCategory(product.getCategory());
       customerOperations.setExpireDate(product.getExpireDate());
       customerOperations.setMainPhotoUrl(product.getMainPhotoUrl());
       customerOperations.setManufacturedDate(product.getManufacturedDate());
       customerOperations.setQuantity(product.getQuantity());
       customerOperations.setSalePrice(product.getSalePrice());
       customerOperations.setUnit(product.getUnit());
       customerOperations.setProId(product.getId());
       customerOperations.setShopName(product.getShopName());
       customerOperations.setPayCode(product.getStaff().getPayCode());
       customerOperations.setSellerId(product.getStaff().getId());
       customerOperations.setSearchKey("P-"+product.getId()+"-C-"+customer.getId()+"-S-Added");
       customerOperations.setTotalRegularPrice(product.getTotalRegularPrice());
       customerOperations.setTotalSalePrice(product.getTotalSalePrice());
       customerOperationsDao.recordObject(customerOperations);
               successMessage(customerOperations.getName()+" Is Added To Cart!");
          return "floor-3.xhtml";     
           } catch (Exception e) {
               warningMessage("This Product is Already Added To Cart!");
               return "floor-3.xhtml";
           }
           
        
        }
        
        public Integer getNumberProds(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
 
           CustomerOperationss = customerOperationsDao.findAddedProductsByCustomer(customer.getId());
           number = CustomerOperationss.size();
           return number;
        }
        
       public List<CustomerOperations> getAllLikedProds(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
           CustomerOperationss = customerOperationsDao.findLikedProductsByCustomer(customer.getId());
           Collections.reverse(CustomerOperationss);
           return CustomerOperationss ;
       }
       
       public List<CustomerOperations> getAllLikedProdsByFoodCategory(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
           CustomerOperationss = customerOperationsDao.findLikedProductsByCustomerFood(customer.getId());
           Collections.reverse(CustomerOperationss);
           return CustomerOperationss ;
       }
       
       public List<CustomerOperations> getAllLikedProdsByDeviceCategory(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
           CustomerOperationss = customerOperationsDao.findLikedProductsByCustomerDevice(customer.getId());
           Collections.reverse(CustomerOperationss);
           return CustomerOperationss ;
       }
       
       public List<CustomerOperations> getAllLikedProdsByClothCategory(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
           CustomerOperationss = customerOperationsDao.findLikedProductsByCustomerCloth(customer.getId());
           Collections.reverse(CustomerOperationss);
           return CustomerOperationss ;
       }
       
       public List<CustomerOperations> getAllLikedProdsByOtherCategory(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
           
           CustomerOperationss = customerOperationsDao.findLikedProductsByCustomerOther(customer.getId());
           Collections.reverse(CustomerOperationss);
           return CustomerOperationss ;
       }
       
       public List<CustomerOperations> getAllAddedProds(){
           Users usr = userdao.getUsername(this.user.getUsername());
           customer = usr.getCustomer();
           CustomerOperationss = customerOperationsDao.findAddedProductsByCustomer(customer.getId());
           Collections.reverse(CustomerOperationss);
           return CustomerOperationss ;
       }
       
       public List<Product> foodProductList(){
          products = productDao.listFoodProducts();
          Collections.reverse(products);
          return products;
       }
       
       public Integer foodProductsBySeller(){
           Users usr = userdao.getUsername(this.user.getUsername());
           staff = usr.getStaff();
           products = productDao.listOfFoodProductsBySeller(staff.getId());
           Integer size = products.size();
           return size;
       }
       
       public Integer deviceProductsBySeller(){
           Users usr = userdao.getUsername(this.user.getUsername());
           staff = usr.getStaff();
           products = productDao.listOfDeviceProductsBySeller(staff.getId());
           Integer size = products.size();
           return size;
       }
       
       public Integer clothesProductsBySeller(){
           Users usr = userdao.getUsername(this.user.getUsername());
           staff = usr.getStaff();
           products = productDao.listOfClothesProductsBySeller(staff.getId());
           Integer size = products.size();
           return size;
       }
       
       public Integer otherProductsBySeller(){
           Users usr = userdao.getUsername(this.user.getUsername());
           staff = usr.getStaff();
           products = productDao.listOfOtherProductsBySeller(staff.getId());
           Integer size = products.size();
           return size;
       }
       
       public List<Product> clothProductList(){
          products = productDao.listClothesProducts();
          Collections.reverse(products);
          return products;
       }
       
        public List<Product> deviceProductList(){
          products = productDao.listDeviceProducts();
          Collections.reverse(products);
          return products;
       }
        
        public List<Product> otherProductList(){
          products = productDao.listOtherProducts();
          Collections.reverse(products);
          return products;
       }
        
        

        public void reset()
        {
            if (getUsername().matches(user.getUsername()))
            {
                if(getNewPassword().matches(getConfirmPassword()))
                {
                 userdao.updateObject(user);
                successMessage("password changed");    
                }
                else{
                    errorMessage("password not mached");
                }  
            }
            else{
                errorMessage("password not changed");
            }
            
        }
        
        
        public List<Staff> getAllStaff(){
            return staffdao.allSellerActiveAccounts();
        }
        
        public List<Users> getAllCustomerAccounts(){
            return userdao.allCustomerActiveAccounts();
        }
        
        public List<Users> getDeactivatedCustomerAccounts(){
            return userdao.allCustomerInactiveAccounts();
        }
        
        public List<Staff> getDeactivatedSellerAccounts(){
            return staffdao.allSellerInactiveAccounts();
        }
        
       

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Users getUsertoupdate() {
		return usertoupdate;
	}

	public void setUsertoupdate(Users usertoupdate) {
		this.usertoupdate = usertoupdate;
	}

	public StaffDao getStaffdao() {
		return staffdao;
	}

	public void setStaffdao(StaffDao staffdao) {
		this.staffdao = staffdao;
	}

	

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public SellerDao getSellerDao() {
        return sellerDao;
    }

    public void setSellerDao(SellerDao sellerDao) {
        this.sellerDao = sellerDao;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public CustomerOperations getCustomerOperations() {
        return customerOperations;
    }

    public void setCustomerOperations(CustomerOperations customerOperations) {
        this.customerOperations = customerOperations;
    }

    public CustomerOperationsDao getCustomerOperationsDao() {
        return customerOperationsDao;
    }

    public void setCustomerOperationsDao(CustomerOperationsDao customerOperationsDao) {
        this.customerOperationsDao = customerOperationsDao;
    }

    public List<CustomerOperations> getCustomerOperationss() {
        return CustomerOperationss;
    }

    public void setCustomerOperationss(List<CustomerOperations> CustomerOperationss) {
        this.CustomerOperationss = CustomerOperationss;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public CustomerOrderDao getCustomerOrderDao() {
        return customerOrderDao;
    }

    public void setCustomerOrderDao(CustomerOrderDao customerOrderDao) {
        this.customerOrderDao = customerOrderDao;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    
    
    public String todayDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        return ""+dtf.format(localDate);
    }
    
    public String todayTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        return ""+dtf.format(localTime);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public SendSMS getSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(SendSMS sendSMS) {
        this.sendSMS = sendSMS;
    }

    public String getFrom_number() {
        return from_number;
    }

    public String getACCOUNT_SID() {
        return ACCOUNT_SID;
    }

    public String getAUTH_TOKEN() {
        return AUTH_TOKEN;
    }

    public String getTo_number() {
        return to_number;
    }

    public void setTo_number(String to_number) {
        this.to_number = to_number;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }
    
    
    
    
}
