/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.CustomerOrder;
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
public class CustomerOrderDao extends GenericDao<CustomerOrder>{
    
    public  List<CustomerOrder> findCustomerOrderByCustomerId(Integer id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where custId='"+id+"' && orderStatus='Pending'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
    
     public  List<CustomerOrder> findCustomerOrderByCustomerIdAccepted(Integer id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where custId='"+id+"' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
     
     public  List<CustomerOrder> findCustomerOrderByCustomerIdRejected(Integer id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where custId='"+id+"' && orderStatus='Rejected'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }

    
     public  List<CustomerOrder> findOrdersBySellerShop(String shopName) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where shopName='"+shopName+"' && orderStatus='Pending'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
        public  List<CustomerOrder> findOrdersBySellerShopAccepted(String shopName) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where shopName='"+shopName+"' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
        public  List<CustomerOrder> findOrdersBySellerAccepted() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
    public  List<CustomerOrder> findOrdersBySellerShopRejected(String shopName) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where shopName='"+shopName+"' && orderStatus='Rejected'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        } 
     
     public CustomerOrder getOrderBySearchKey2(String name) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM CustomerOrder co where co.searchKey2 = ?");
		sql.setString(0, name);
		CustomerOrder fa = (CustomerOrder) sql.uniqueResult();
		ss.close();
		return fa;
	}
 
    public  List<CustomerOrder> totalOrders(String name) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where shopName='"+name+"'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
    
    public  List<CustomerOrder> totalAcceptedOrders() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
     public  List<CustomerOrder> totalRjectedOrders() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customerorder where orderStatus='Rejected'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<CustomerOrder> orders = q.list();
        s.close();
        return orders;
        }
    
    public BigDecimal totalSalesBySeller(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as tp from customerorder where shopName='"+name+"' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalAmountAcceptedOrdersBySeller(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as tp from customerorder where orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    public BigDecimal totalAmountRejectedOrdersBySeller(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as tp from customerorder where orderStatus='Rejected'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    
    
    public BigDecimal totalQuantityBySellerOrders(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(quantity) as q from customerorder where shopName='"+name+"' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalRegularPriceBySellerOrders(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalRegularPrice) as q from customerorder where shopName='"+name+"' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalSalePriceBySellerOrders(String name){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalSalePrice) as q from customerorder where shopName='"+name+"' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
    public BigDecimal totalPriceOrderAcceptedByFood(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as q from customerorder where category='Food' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    public BigDecimal totalPriceOrderAcceptedByCloth(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as q from customerorder where category='Cloth' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    public BigDecimal totalPriceOrderAcceptedByDevice(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as q from customerorder where category='Device' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    public BigDecimal totalPriceOrderAcceptedByOther(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select SUM(totalPrice) as q from customerorder where category='Other' && orderStatus='Accepted'";
        Query q = s.createSQLQuery(sql);
        BigDecimal total = (BigDecimal) q.uniqueResult();
        s.close();
        return total;
		
    }
    
}
