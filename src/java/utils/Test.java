/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.CustomerDao;
import dao.CustomerOperationsDao;
import dao.ProductDao;
import domain.Customer;
import domain.CustomerOperations;
import domain.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Gasana
 */
public class Test {

    /**
     * @param args the command line arguments
     */
   //  ProductDao productDao = new ProductDao();
      
    public static void main(String[] args) {
   //Session s = HibernateUtil.getSessionFactory().openSession();
       
       
       Product product = new Product();
       ProductDao productDao = new ProductDao();
//       CustomerOperations customerOperations = new CustomerOperations();
//        CustomerOperationsDao customerOperationsDao = new CustomerOperationsDao();
//        List<CustomerOperations> customerOperationses= new ArrayList<>();
       List<Product> products = new ArrayList<>();
//       List<Customer> customers = new ArrayList<>();
//       Customer customer = new Customer();
//       CustomerDao customerDao = new CustomerDao();
//       
//       products = productDao.findAll(Product.class);
//       
//        System.out.println("LIST ALL PROD SIZE "+products.size());
//        
//        
//         if(!products.isEmpty()){
//            Product pro = products.get(products.size()-1);
//            System.out.println("Pro Name "+pro.getName());
//           }


//        String searchKey = "Red Suit";
//        customerOperations = customerOperationsDao.getOperationBySearchKey(searchKey);
//        
//        if(Objects.isNull(customerOperations)){
//            System.out.println("EMPTY");
//        }else{
//            System.out.println("NOT EMPTY");
//        }


//        CustomerOrder order = new CustomerOrder();
//        CustomerOrderDao customerOrderDao = new CustomerOrderDao();
//        
//        List<CustomerOrder>orders = new ArrayList<>();
//        
//        orders = customerOrderDao.findCustomerOrderByProducts("WI Shop");
//        
//        if(orders.isEmpty()){
//            System.out.println("EMPTY");
//        }else{
//            System.out.println("NOT EMPTY");
//        }
       
//    products = productDao.findAllProds();
//        
//    System.out.println("size "+products.size());
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    
//    for(int i=0; i<products.size(); i++) {
//                
//        HashMap<String, String> hashMap1 = new HashMap<String, String>();
//        
//        hashMap.put((i+""), products.get(i));
//      
//    }
//    
//    JSONObject json = new JSONObject(hashMap);
//        
//    System.out.println("Json "+json);
        
//     SendSMS s1 = new SendSMS();
//     String to_number = "+250781440051";
//     String from_number ="+18563910106";
//     String body = "\n TO :"+to_number +"\n"
//                + "YOU ORDER HAVE BEEN ACCEPTED SUCCESSFULLY";
//     s1.sendSMS(to_number,from_number, body);
    
    }
 
}
