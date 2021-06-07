package dao;

import domain.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

import org.hibernate.Session;
import utils.HibernateUtil;

public class GenericDao<T> {

	public void recordObject(T t) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(t);
		s.getTransaction().commit();
		s.close();
	}

	public void updateObject(T t) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(t);
		s.getTransaction().commit();
		s.close();
	}

	public void deleteObject(T t) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.delete(t);
		s.getTransaction().commit();
		s.close();
	}
        
        	@SuppressWarnings("unchecked")
	public List<T>getAll(String query) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		List<T> list = new ArrayList<T>();
		list = s.createQuery(query).list();
		s.close();
		return list;
	}

         public List<T> findAll(Class c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("from " + c.getName() + " s");
        List<T> l = q.list();
        s.close();
        return l;
        }
         
       
         
         
        
      
        
         public T findOne(Class x, Serializable id) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        T o = (T) s.get(x.getName(), id);
        s.close();
        return o;
        
    }
         
       public T findOneByString(Class x, String name) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        T o = (T) s.get(x.getName(), name);
        s.close();
        return o;
        
        }

}
