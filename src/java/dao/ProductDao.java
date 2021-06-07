/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;

/**
 *
 * @author Gasana
 */
public class ProductDao extends GenericDao<Product>{
      
    public  List<Product> findProductsByStaff(Integer id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && publishStatus='Activated'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
    
    public  List<Product> findFinishedProductsByStaff(Integer id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && publishStatus='Finished'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
    
     public  List<Product> findAllProds() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
     public  List<Product> findAllProdsByRoom301() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='301'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     public  List<Product> findAllProdsByRoom302() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='302'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
     public  List<Product> findAllProdsByRoom303() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='203'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
     public  List<Product> findAllProdsByRoom304() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='304'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
     public  List<Product> findAllProdsByRoom305() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='305'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
     public  List<Product> findAllProdsByRoom201() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='201'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     public  List<Product> findAllProdsByRoom202() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='202'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     public  List<Product> findAllProdsByRoom203() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='203'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
     public  List<Product> findAllProdsByRoom204() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='204'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     public  List<Product> findAllProdsByRoom205() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='205'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
      public  List<Product> findAllProdsByRoom101() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='101'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      
      public  List<Product> findAllProdsByRoom102() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='102'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      
      public  List<Product> findAllProdsByRoom103() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='103'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      
      public  List<Product> findAllProdsByRoom104() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='104'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      public  List<Product> findAllProdsByRoom105() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated' && roomNo='105'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
    
     public  List<Product> findProductsByStaffInactive(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && publishStatus='Deactivated'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
    public  List<Product> listOfFoodProductsBySeller(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && category='Food'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        } 
    
    public  List<Product> listOfDeviceProductsBySeller(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && category='Device'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        } 
    public  List<Product> listOfClothesProductsBySeller(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && category='Cloth'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        } 
    
    public  List<Product> listOfOtherProductsBySeller(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where staff_id='"+id+"' && category='Other'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        } 
    
    
    
     public  List<Product> findProductsById(Integer id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where id='"+id+"'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
      }
     
    
     
      public  List<Product> findProductsByCategory(String category) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where category='"+category+"' && publishStatus='Activated'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      
      public  List<Product> findProductsByFloor(String category1,String category2) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where publishStatus='Activated'  &&  category='"+category1+"' && category='"+category2+"'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      public  List<Product> findProductsByFloor1(String category1,String category2) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where  category='"+category1+"' && publishStatus='Activated' ||category='"+category2+"' && publishStatus='Activated' ";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      
       public  List<Product> findProductsFoodClothDevice(String category1,String category2,String category3) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where  category='"+category1+"' && publishStatus='Activated' ||category='"+category2+"' ||category='"+category3+"' && publishStatus='Activated' ";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
      
     public  List<Product> listFoodProducts() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where category='Food'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
     
    public  List<Product> listClothesProducts() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where category='Cloth'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
    public  List<Product> listDeviceProducts() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where category='Device'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
    
    public  List<Product> listOtherProducts() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from product where category='Other'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Product> products = q.list();
        s.close();
        return products;
        }
    
    public BigDecimal totalInvestmentBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalRegularPrice) as sp from product where shopName='"+name+"' && publishStatus='Activated' || publishStatus ='Deactivated' ";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalFoodQuantityBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(quantity) as q from product where shopName='"+name+"' && publishStatus='Activated' && category ='Food' ";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalDeviceQuantityBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(quantity) as q from product where shopName='"+name+"' && publishStatus='Activated' && category ='Device' ";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
     public BigDecimal totalClothesQuantityBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(quantity) as q from product where shopName='"+name+"' && publishStatus='Activated' && category ='Cloth' ";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
     
     
    public BigDecimal totalOtherQuantityBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(quantity) as q from product where shopName='"+name+"' && publishStatus='Activated' && category ='Other' ";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalQuantityBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(quantity) as q from product where shopName='"+name+"' && publishStatus='Activated'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalRegularPriceBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalRegularPrice) as q from product where shopName='"+name+"' && publishStatus='Activated'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalSalePriceBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalSalePrice) as q from product where shopName='"+name+"' && publishStatus='Activated'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    
 
    
    
   
     public Product getProductBySearchKey(String name) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Product co where co.searchKey = ?");
		sql.setString(0, name);
		Product fa = (Product) sql.uniqueResult();
		ss.close();
		return fa;
	}
     
     public Product getProductById(Integer id) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Product co where co.id = ?");
		sql.setInteger(0, id);
		Product fa = (Product) sql.uniqueResult();
		ss.close();
		return fa;
	}
    
     
     
}
