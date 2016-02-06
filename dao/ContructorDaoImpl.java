package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Contructor;
import util.HibernateUtil;

public class ContructorDaoImpl implements ContructorDao {
	 private static Logger log = Logger.getLogger(ContructorDaoImpl.class);
	
	@Override
	public Long create(Contructor contructor) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		Long id = null;
        try {
            session.beginTransaction();
            id = (Long)session.save(contructor);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Transaction failed");
            session.getTransaction().rollback();
        } finally {
           if(session != null) session.close();
        }
        return id;
	}

	@Override
	public Contructor read(Long id) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		Contructor contructor = null;
        try {
            contructor = (Contructor)session.get(Contructor.class, id);
        } catch (HibernateException e) {
            log.error("Transaction failed");
        } finally {
            session.close();
        }
        return contructor;
	}

	@Override
	public void update(Contructor contructor) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.update(contructor);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			log.error("Transaction failed");
			session.getTransaction().rollback();
		} finally {
			if (session!=null) session.close();
		}
	}

	@Override
	public void delete(Contructor contructor) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.delete(contructor);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			log.error("Transaction failed");
			session.getTransaction().rollback();
		} finally {
			if (session!=null) session.close();
		}
	} 

	@Override
	public List<Contructor> findAll() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Contructor");
        return query.list();
	}

	@Override
	public List<Contructor> findContructorsByBeginString(String begin) {
		// TODO Auto-generated method stub
		return null;
	}

}
