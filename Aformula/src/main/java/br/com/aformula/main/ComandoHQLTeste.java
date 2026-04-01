package br.com.aformula.main;
import java.util.List;
import org.hibernate.Session;
import br.com.aformula.util.HibernateUtil;

public class ComandoHQLTeste {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
//		String hql = "from Book as bk order by bk.bookName, bk.writer desc";
		String hql ="SELECT producao.data_dispensacao, producao.tipo, SUM(producao.quantidade), producao.status  FROM Producao producao WHERE producao.status <> 'DISPENSADO' GROUP BY producao.data_dispensacao, producao.tipo, producao.status ORDER BY producao.data_dispensacao ASC";
		List<?> list = session.createQuery(hql).list();
		for(int i=0; i<list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			System.out.println(row[0]+", "+ row[1]+", "+ row[2]+", "+ row[3]);
		}		
		session.close();
	}
	
/*	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "select bk.writer, max(bk.price) from Book as bk group by bk.writer having avg(bk.price) > 100";
		List<?> list = session.createQuery(hql).list();
		for(int i=0; i<list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			System.out.println(row[0]+", "+ row[1]);
		}
		session.close();
	}*/
}
