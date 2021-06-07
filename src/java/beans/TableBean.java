package beans;


import dao.CustomerDao;
import dao.ProductDao;
import dao.StaffDao;
import dao.UserDao;
import domain.Customer;
import domain.Product;
import domain.Staff;
import domain.Users;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "tableBean")
@SessionScoped
public class TableBean {
    private Users user;
    private UserDao userdao;
    private Staff staff;
    private StaffDao staffdao;
    private Customer customer;
    private CustomerDao customerDao;
    private Product product;
    private Product ProductDetails;
    private ProductDao productDao;
    private List<Product> products = new ArrayList<>();
    private Integer searchId;
    public String username;
    public Integer calc;
    
    
     public TableBean() {
        user = new Users();
        userdao = new UserDao();
        staff = new Staff();
        staffdao = new StaffDao();
        customerDao = new CustomerDao();
        customer = new Customer();
        productDao = new ProductDao();
    }

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public Product getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(Product ProductDetails) {
        this.ProductDetails = ProductDetails;
    }
    
    

   
    public UserDao getUserdao() {
        return userdao;
    }

    public void setUserdao(UserDao userdao) {
        this.userdao = userdao;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public StaffDao getStaffdao() {
        return staffdao;
    }

    public void setStaffdao(StaffDao staffdao) {
        this.staffdao = staffdao;
    }
    
     public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
       

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
   
 
    public String getImageLength() {
        return imageLength;
    }
 
    public void setImageLength(String imageLength) {
        this.imageLength = imageLength;
    }
    private String imageLength;
 
    public List<Product> getAllProducts(){
        
        try {
         products = productDao.findAllProds();
        Collections.reverse(products);
        return products; 
        } catch (Exception e){ 
            System.out.print("ALL PRODUCTS "+e);
            return null;
        }
        
        
    }
    
      
    
   
   public String searchById(Integer id){
       products = productDao.findProductsById(id);
       product = productDao.getProductById(id);
       return "product-details.xhtml";

   }
   
   public List<Product> getAllProductsByMixed(){
       String category1 = "Device";
       String category2 = "Food";
       String category3 = "Cloth";
       List<Product> mixedProducts = productDao.findProductsFoodClothDevice(category1, category2,category3);
       Collections.reverse(mixedProducts);
       return  mixedProducts;
   }
   

   public String searchByIdSeller(Integer id){
       products = productDao.findProductsById(id);     
       return "update-product.xhtml?faces=redirect=true";
   }
   
   public List<Staff> getAllSellers(){
       return staffdao.allSellerActiveAccounts();
   }
   
    public List<Customer> getAllCustomers(){
       return customerDao.allCustomerActiveAccounts();
   }
   
    
   public List<Product> getOneProduct(){
       return products;
   }
   
   public List<Product> getAllProductsByFoodCategory(){
       String category = "Food";
       List<Product> foodProducts = productDao.findProductsByCategory(category);
       Collections.reverse(foodProducts);
       return  foodProducts;
   }
   public List<Product> getAllProductsByDeviceCategory(){
       String category = "Device";
       List<Product> deviceProducts = productDao.findProductsByCategory(category);
       Collections.reverse(deviceProducts);
       return  deviceProducts;
   }
   public List<Product> getAllProductsByClothCategory(){
       String category = "Cloth";
       List<Product> clothProducts = productDao.findProductsByCategory(category);
       Collections.reverse(clothProducts);
       return  clothProducts;
   }
   
   
          

   public List<Product> getAllProductsByOtherCategory(){
       String category = "Other";
       List<Product> otherProducts = productDao.findProductsByCategory(category);
       Collections.reverse(otherProducts);
       return  otherProducts;
   }
   
    public List<Product> getAllProductsByFloor1(){
       String category1 = "Other";
       String category2 = "Food";
       List<Product> mixedFoodAndOtherProducts = productDao.findProductsByFloor1(category1, category2);
       Collections.reverse(mixedFoodAndOtherProducts);
       return  mixedFoodAndOtherProducts;
   }
    
   public List<Product> getAllProductsByFloor2(){
       String category1 = "Device";
       String category2 = "Device";
        List<Product> deviceProducts = productDao.findProductsByFloor(category1, category2);
       Collections.reverse(deviceProducts);
       return  deviceProducts;
   } 
   
    public List<Product> getAllProductsByFloor3(){
       String category1 = "Cloth";
       String category2 = "Cloth";
       List<Product> clothProducts = productDao.findProductsByFloor(category1, category2);
       Collections.reverse(clothProducts);
       return  clothProducts;
   } 

    public Integer getCalc() {
        return calc;
    }

    public void setCalc(Integer calc) {
        this.calc = calc;
    }

   
   
}