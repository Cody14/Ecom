package dao;

import domain.Staff;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;



public class StaffDao extends GenericDao<Staff> {
	public Staff getPhone(String phone) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Staff s where s.phonenumber = ?");
		sql.setString(0, phone);
		Staff st = (Staff) sql.uniqueResult();
		ss.close();
		return st;
	}
        
       


	public Staff getNation(String nation) {
                
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Staff s where s.nationalID = ?");
		sql.setString(0, nation);
		Staff sta = (Staff) sql.uniqueResult();
		ss.close();
		return sta;
	}
        
        
       public  List<Staff> allSellerActiveAccounts() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from staff where isState=true";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Staff> activeAccs = q.list();
        s.close();
        return activeAccs;
        }
         
        public  List<Staff> allSellerInactiveAccounts() {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from staff where isState=false";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Staff> activeAccs = q.list();
        s.close();
        return activeAccs;
        } 

}
