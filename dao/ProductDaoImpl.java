package dao;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Product;
import util.HibernateUtil;

public class ProductDaoImpl implements ProductDao{
    private static Logger log = Logger.getLogger(ProductDaoImpl.class);

	@Override
	public Long create(Product product) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Long id = null;
        try {
            session.beginTransaction();
            id = (Long)session.save(product);
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
	public Product read(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Product product = null;
        try {
            product = (Product)session.get(Product.class, id);
        } catch (HibernateException e) {
            log.error("Transaction failed");
        } finally {
            session.close();
        }
        return product;
	}

	@Override
	public void update(Product product) {
		//update(product) с помощью запроса HQL (Query)
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			//В запросе там где передается String нужно обязательно ставить одинарніе кавычки ''
			Query query = session.createQuery("update Product set name='"+ product.getName() + "', barcode='" + product.getBarcode() + "' where id=" + product.getId());
			query.executeUpdate(); // выполнение запроса что выше
			session.getTransaction().commit();
		} catch (HibernateException e) {
			log.error("Transaction failed");
            session.getTransaction().rollback();
		} finally {
			if (session != null) session.close();
		}
	}

	@Override
	public void delete(Product product) {
		// delete(product) с помощью запроса SQL (SQLQuery)
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query  = session.createSQLQuery("delete from product where id=" + product.getId());
			query.executeUpdate(); // выполнение запроса что выше
			session.getTransaction().commit();
		} catch (HibernateException e) {
			log.error("Transaction failed");
			session.getTransaction().rollback();
		} finally {
			if (session != null) session.close();
		}
		
		
	}

	@Override
	public List<Product> findAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Product");
        return query.list();
	}

	@Override
	public List<Product> findProductsByBeginString(String begin) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Product where name like '" + begin + "%'");
		return query.list();
	}

}
