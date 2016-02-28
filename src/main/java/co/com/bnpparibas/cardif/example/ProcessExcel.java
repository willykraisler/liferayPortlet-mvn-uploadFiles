package co.com.bnpparibas.cardif.example;


import co.com.bnpparibas.cardif.exception.ReaderException;
import co.com.bnpparibas.cardif.model.BogotaRow;
import co.com.bnpparibas.cardif.model.SessionBuilder;
import co.com.bnpparibas.cardif.reader.ReaderFile;
import co.com.bnpparibas.cardif.utils.Interval;
import co.com.bnpparibas.cardif.utils.UtilDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Unit test for simple App.
 */
public class ProcessExcel {	
	
	final static Logger logger = LoggerFactory.getLogger(ProcessExcel.class);
	
	public void loadExcel(Integer rejects) throws IOException{

		SessionFactory sessionFactory = SessionBuilder.createSessionFactory();		
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		List<BogotaRow> rows =  new ArrayList<BogotaRow>();
		ReaderFile.readXLSXFile(rows,rejects);		
		for (BogotaRow r : rows){
			session.save(r);			
		}		
		session.getTransaction().commit();		
		session.close();
		
	}
	
	public List<BogotaRow> consultRows(){
		
		SessionFactory sessionFactory = SessionBuilder.createSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		Interval inter = UtilDate.getInterval();
		
		Query query = session.getNamedQuery("@ROWS_BOGOTA");
		query.setParameter("stDate", inter.getInitDate());
		query.setParameter("edDate", inter.getFinalDate());
		List<BogotaRow> rows = query.list();		
		session.close();
		return rows;
		
	}
	

	
	
}