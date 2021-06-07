package dao;

import domain.Users;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.HibernateUtil;



public class UserDao extends GenericDao<Users> {
	public Users getUsername(String username) {
		Session ss = HibernateUtil.getSessionFactory().openSession();
		Query sql = ss.createQuery("FROM Users u where u.username = ?");
		sql.setString(0, username);
		Users use = (Users) sql.uniqueResult();
		ss.close();
		return use;
	}
        
        
        public  List<Users> allCustomerActiveAccounts() { 
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from users where active=true && category='Customer'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Users> activeAccs = q.list();
        s.close();
        return activeAccs;
        }
        
        public  List<Users> allCustomerInactiveAccounts() { 
        Session s = HibernateUtil.getSessionFactory().openSession();
        String sql="select * from users where active=false && category='Customer'";
        Query q = s.createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Users> activeAccs = q.list();
        s.close();
        return activeAccs;
        }
}
