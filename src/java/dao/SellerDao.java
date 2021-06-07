/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Seller;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;

/**
 *
 * @author Gasana
 */
public class SellerDao extends GenericDao<Seller>{
    public Seller getPhone(String phone) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Seller f where f.phonenumber = ?");
		sql.setString(0, phone);
		Seller fa = (Seller) sql.uniqueResult();
		ss.close();
		return fa;
	}
    
    

	public Seller getNation(String nation) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Seller f where f.nationalID = ?");
		sql.setString(0, nation);
		Seller fa = (Seller) sql.uniqueResult();
		ss.close();
		return fa;
	}
}
