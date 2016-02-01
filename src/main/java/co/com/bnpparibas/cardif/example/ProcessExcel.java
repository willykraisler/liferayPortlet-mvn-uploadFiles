package co.com.bnpparibas.cardif.example;

import co.com.bnpparibas.cardif.model.BogotaRow;
import co.com.bnpparibas.cardif.model.SessionBuilder;
import co.com.bnpparibas.cardif.reader.ReaderFile;
import co.com.bnpparibas.cardif.utils.Interval;
import co.com.bnpparibas.cardif.utils.UtilDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * Unit test for simple App.
 */
public class ProcessExcel {	
	
	public void loadExcel(Integer rejects) {

		SessionFactory sessionFactory = SessionBuilder.createSessionFactory();		
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		List<BogotaRow> rows =  new ArrayList<BogotaRow>();		
		try {
			ReaderFile.readXLSXFile(rows,rejects);
		} catch (IOException e) {			
			e.printStackTrace();
		} 		
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