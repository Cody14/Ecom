/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.CustomerOperations;
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
public class CustomerOperationsDao extends GenericDao<CustomerOperations>{ 
    
    
     public  List<CustomerOperations> findLikedProductsByCustomer(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        //String sql="select * from product where staff_id='"+id+"'";
        String sqll="select * from customeroperations where custId='"+id+"' && operationName='liked'";
        Query q = s.createSQLQuery(sqll);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> customerOperationses = q.list();
        s.close();
        return customerOperationses;
        }
        public  List<CustomerOperations> findLikedProductsByCustomerFood(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        //String sql="select * from product where staff_id='"+id+"'";
        String sqll="select * from customeroperations where custId='"+id+"' && operationName='liked' && category='Food'";
        Query q = s.createSQLQuery(sqll);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> customerOperationses = q.list();
        s.close();
        return customerOperationses;
        }
        
        public  List<CustomerOperations> findLikedProductsByCustomerDevice(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        //String sql="select * from product where staff_id='"+id+"'";
        String sqll="select * from customeroperations where custId='"+id+"' && operationName='liked' && category='Device'";
        Query q = s.createSQLQuery(sqll);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> customerOperationses = q.list();
        s.close();
        return customerOperationses;
        }
        
        public  List<CustomerOperations> findLikedProductsByCustomerCloth(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        //String sql="select * from product where staff_id='"+id+"'";
        String sqll="select * from customeroperations where custId='"+id+"' && operationName='liked' && category='Cloth'";
        Query q = s.createSQLQuery(sqll);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> customerOperationses = q.list();
        s.close();
        return customerOperationses;
        }
        
        public  List<CustomerOperations> findLikedProductsByCustomerOther(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        //String sql="select * from product where staff_id='"+id+"'";
        String sqll="select * from customeroperations where custId='"+id+"' && operationName='liked' && category='Other'";
        Query q = s.createSQLQuery(sqll);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> customerOperationses = q.list();
        s.close();
        return customerOperationses;
        }
        
        
     
     
      public  List<CustomerOperations> findAddedProductsByCustomer(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        //String sql="select * from product where staff_id='"+id+"'";
        String sqll="select * from customeroperations where custId='"+id+"' && operationName='added'";
        Query q = s.createSQLQuery(sqll);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> customerOperationses = q.list();
        s.close();
        return customerOperationses;
        }
      
  
     
     
     
     
     
      public CustomerOperations getOperationBySearchKey(String name) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM CustomerOperations co where co.searchKey = ?");
		sql.setString(0, name);
		CustomerOperations fa = (CustomerOperations) sql.uniqueResult();
		ss.close();
		return fa;
	}
      
     public  List<CustomerOperations> findLikedBySellerShop(String shopName) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customeroperations where shopName='"+shopName+"' && operationName='liked'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOperations> orders = q.list();
        s.close();
        return orders;
        }
     
     
     public BigDecimal totalCartAmount(Integer custId){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(salePrice) as tp from customeroperations where custId='"+custId+"' && operationName='added'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }

}
