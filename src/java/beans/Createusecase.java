package beans;

import dao.CustomerDao;
import dao.ProductDao;
import dao.StaffDao;
import dao.UserDao;
import domain.Customer;
import domain.Product;
import domain.Staff;
import domain.Users;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//import org.apache.catalina.connector.Request;



@ManagedBean(name = "cu")
@SessionScoped
public class Createusecase {
	private Users user;
	private Users users;
	private Staff staff;
        private Customer customer;
        private CustomerDao customerDao = new CustomerDao();
        private Product product;
        private ProductDao productDao;
        private String bookRoom;
	private UserDao userdao;
	private StaffDao staffdao;
	private String nationalId;
	private String password;
	private String location;
	private String locatemud;
	private Date dob = null;
	private Staff rachid;
	private boolean renderBoard;
	private String login;
	private String myhouse;
        String category;
	private List<Staff> stafflist = new ArrayList<>();
   // Constructor
	public Createusecase() {
		user = new Users();
		staff = new Staff();
		rachid = new Staff();
		userdao = new UserDao();
		staffdao = new StaffDao();
		customerDao = new CustomerDao();
                customer = new Customer();
                product = new Product();
                productDao = new ProductDao();
 
	}
	
	@PostConstruct
	public void init() {
		stafflist = staffdao.getAll("FROM Staff");
	}
//Method to find the details of any staff.
	public void staffdetails(Staff stk) {
		this.rachid = stk;
	}
	public void updateStaffInfo(Staff cro) {
		rachid = new Staff();
		this.rachid = cro;
	}
	
	public String editProfile() {
		if (null != login && !login.isEmpty()) {
			Users use = userdao.getUsername(login);
			if (null != use.getStaff()) {
						staffdao.updateObject(rachid);
						successMessage("Profile well Updated");
			} else {
				errorMessage("Please login as staff");
			}
		} else {
			errorMessage("Please login first");
		}
		return "staffs.xhtml";
	}
	public String activateAndDisactivate() {
		if (null != login && !login.isEmpty()){
			Users use = userdao.getUsername(login);
			if(login.equalsIgnoreCase("ADMIN")) {
				if(rachid.isIsState()) {
					rachid.setIsState(false);
					staffdao.updateObject(rachid);
					successMessage("Action well done");
					sendMessage(rachid.getPhonenumber(),
							"Your account has been disctivated, plz contact Administrator for activation.");
				}else if(!rachid.isIsState()){
					rachid.setIsState(true);
					staffdao.updateObject(rachid);
					successMessage("Action well done");
					sendMessage(rachid.getPhonenumber(),
							"Your account has been Activated, plz contact Administrator for the way forward.");
				}
				return "staffpage.xhtml";
			}
			else if (null != use.getStaff()) {
				if(rachid.isIsState()) {
					rachid.setIsState(false);
					staffdao.updateObject(rachid);
					successMessage("Action well done");
					sendMessage(rachid.getPhonenumber(),
							"Your account has been disctivated, plz contact Executif secretary for activation.");
				}else if(!rachid.isIsState()){
					rachid.setIsState(true);
					staffdao.updateObject(rachid);
					successMessage("Action well done");
					sendMessage(rachid.getPhonenumber(),
							"Your account has been Activated, plz contact Executif secretary for the way forward.");
				} 
				return "staffs.xhtml";
						
			} else {
				errorMessage("Please login as staff");
			}
		} else {
			errorMessage("Please login first");
		}
		return "";
	}

	public List<Staff> stafflist() {
//            try {
//               List<Staff> list = new ArrayList<Staff>();
//		for (Staff sta : staffdao.getAll("FROM Staff")) {
//			list.add(sta);
//		}
//		return list; 
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return new StaffDao().getAll(login);
		
	}

	
	public boolean userexitence() {
		Users user = userdao.getUsername(this.user.getUsername());
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}
//method to ckeck the passowrd
	public boolean checkPassword() {
		if (user.getPassword().matches(password)) {
			return true;
		} else {
			return false;
		}
	}
//method to validate the phonenumber
	public boolean validatePhone(String phone) {
		if (phone.length() == 13) {
			if (phone.startsWith("+25078") || phone.startsWith("+25072") || phone.startsWith("+25073")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
//method to validate email
	public boolean validateEmail(String email) {
		if (email.contains("@") && email.contains(".")) {
			String test[] = email.split("@");
			if (test[1].length() > 0 && test[0].length() > 0) {
				String another[] = test[1].replace(".", "|").split("|");
				if (another[0].length() > 0 && another[1].length() > 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
//method to validate national ID number
	@SuppressWarnings("unused")
	public boolean validateId(String id, String dob) {
		if (!id.isEmpty() || id.length() > 0 || id != null || id != "") {
			if (id.startsWith("1")) {
				if (id.trim().replace(" ", "").length() == 16) {
					String check = id.trim().replace(" ", "").substring(1, 5);
					String val[] = dob.split("/");
					if (check.equals(val[2])) {
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}
			} else {
				return false;
			}
		} else
			return false;
	}

	//Method for successful messageS
	public void successMessage(String summary) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, ""));
	}
	
	//Method for error messages

	public void errorMessage(String summary) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ""));
	}

	//Method to send a message to a phone number
	public String sendMessage(String receiver, String content) {
		String response;
		try {
			String recipient = receiver;
			String username = "admin";
			String password = "motta";
			String requestUrl = "http://127.0.0.1:9501/api?action=sendmessage&" + "username="
					+ URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8")
					+ "&recipient=" + URLEncoder.encode(recipient, "UTF-8") + "&messagetype=SMS:TEXT" + "&messagedata="
					+ URLEncoder.encode(content, "UTF-8") + "&originator=" + URLEncoder.encode("+2507862588=167", "UTF-8")
					+ "&serviceprovider=MTN" + "&responseformat=html";

			URL url = new URL(requestUrl);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();

			System.out.println(uc.getResponseMessage());
			uc.disconnect();
			response = "Message Sent";
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			response = "Message Not Sent";

		}
		return response;
	}

	
	public void createStaff() {
		//if (userexitence()) {
		
			
			Staff see = staffdao.getNation(this.staff.getNationalID());
			if (see == null) {
//				if (validateId(staff.getNationalID(), new SimpleDateFormat("dd/MM/yyyy").format(dob))) {
					Staff sl = staffdao.getPhone(this.staff.getPhonenumber());
					if (sl == null) {
						if (validatePhone(staff.getPhonenumber())) {
							staff.setIsState(true);
							//staff.setCategory(category);
							staff.setCategory("Seller");
                                                        staff.setRoomNo(bookRoom);
							staffdao.recordObject(staff);
                                                        
							sendMessage(staff.getPhonenumber(),
							"You have been registered so please visit the Online Citizen"
							+ " Management System to create account");

							staff = new Staff();
							successMessage("Staff well created");
						} else {
							errorMessage("Invalid phone number format");
						}
					}else if(bookRoom==null){
                                            errorMessage("Room is booked choose another");
                                        }
                                        else {
						errorMessage("Phone number duplication not allowed");
					}
				} else {
					errorMessage("Invalid nationalId format");
				}

			
	}
        
        public String createCustomer() {
            try {
                customer.setIsState(false);
                customerDao.recordObject(customer);
                successMessage("Customer created Well");
                return "customer-validate.xhtml?faces-redirect=true";
            } catch (Exception e) {
                errorMessage("Customer Failed  to be Created : "+e);
                return "customer-register.xhtml?faces-redirect=true";
            }		
	}
        
        
       
		


	//Method to create chief of village
	public String createmud() {
		//if (userexitence()) {
		if (!login.isEmpty() && null != login) {
			Users use = userdao.getUsername(login);
			//Cell ce=use.getStaff().getCell();
		
			Staff see = staffdao.getNation(this.staff.getNationalID());
			if (see == null) {
				if (validateId(staff.getNationalID(), new SimpleDateFormat("dd/MM/yyyy").format(dob))) {
					Staff sl = staffdao.getPhone(this.staff.getPhonenumber());
					if (sl == null) {
						if (validatePhone(staff.getPhonenumber())) {
							staff.setIsState(true);
							staff.setCategory("MUDUGUDU");
							
							staffdao.recordObject(staff);
							sendMessage(staff.getPhonenumber(),
							"You have been registered so please visit the Online Citizen"
							+ " Management System to create account");

							staff = new Staff();
							successMessage("Mudugudu well created");
						} else {
							errorMessage("Invalid phone number format");
						}
					} else {
						errorMessage("Phone number duplication not allowed");
					}
				} else {
					errorMessage("Invalid nationalId format");
				}
			} else {
				errorMessage("National Id number duplication not allowed");
			}
			} else {
				errorMessage("Village specified not found");
			}
		
		return "staffs.xhtml";
		
	//	} else {
			//errorMessage("Account already exists");
		//}
	}
	
	//Method to creaate new Resident
//	public void createResident() {
//            try {
//               if (userexitence()) {
//		Resident see = residentdao.getNation(this.resident.getNationalID());
//		if (see == null) {
//			if (validateId(resident.getNationalID(), new SimpleDateFormat("dd/MM/yyyy").format(dob))) {
//				Resident sl = residentdao.getPhone(this.resident.getPhonenumber());
//				if (sl == null) {
//					if (validatePhone(resident.getPhonenumber())) {
//						resident.setUuid(UUID.randomUUID().toString());
//						resident.setIsState(false);
//                                                
//						residentdao.recordObject(resident);
//                                                
//						resident = new Resident();
//                                                
//						successMessage("Resident well created");
//					} else {
//						errorMessage("Invalid phone number format");
//					}
//				} else {
//					errorMessage("Phone number duplication not allowed");
//				}
//			} else {
//				errorMessage("Invalid nationalId format");
//			}
//		} else {
//			errorMessage("National Id number duplication not allowed");
//		}
//		 } else {
//		 errorMessage("Account already exists");
//		 }  
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//		
//	}

        
                
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public StaffDao getStaffdao() {
		return staffdao;
	}

	public void setStaffdao(StaffDao staffdao) {
		this.staffdao = staffdao;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Staff getRachid() {
		return rachid;
	}

	public void setRachid(Staff rachid) {
		this.rachid = rachid;
	}
	public List<Staff> getStafflist() {
		return stafflist;
	}
	public void setStafflist(List<Staff> stafflist) {
		this.stafflist = stafflist;
	}
	public String getLocatemud() {
		return locatemud;
	}
	public void setLocatemud(String locatemud) {
		this.locatemud = locatemud;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public String getMyhouse() {
		return myhouse;
	}
	public void setMyhouse(String myhouse) {
		this.myhouse = myhouse;
	}
	public boolean isRenderBoard() {
		return renderBoard;
	}
	public void setRenderBoard(boolean renderBoard) {
		this.renderBoard = renderBoard;
	}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
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

    public String getBookRoom() {
        return bookRoom;
    }

    public void setBookRoom(String bookRoom) {
        this.bookRoom = bookRoom;
    }

   

}
