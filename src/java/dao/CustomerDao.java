/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Product;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;

/**
 *
 * @author Gasana
 */
public class CustomerDao extends GenericDao<Customer>{
    public Customer getPhone(String phone) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Customer f where f.phonenumber = ?");
		sql.setString(0, phone);
		Customer fa = (Customer) sql.uniqueResult();
		ss.close();
		return fa;
	}

	public Customer getNation(String nation) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Customer f where f.nationalID = ?");
		sql.setString(0, nation);
		Customer fa = (Customer) sql.uniqueResult();
		ss.close();
		return fa;
	}
        
        public  List<Customer> allCustomerActiveAccounts() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from customer";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Customer> activeAccs = q.list();
        s.close();
        return activeAccs;
        }

        
        
        
        
}
