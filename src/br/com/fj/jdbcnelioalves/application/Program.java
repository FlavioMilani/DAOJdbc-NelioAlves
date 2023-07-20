package br.com.fj.jdbcnelioalves.application;



import br.com.fj.jdbcnelioalves.model.dao.DaoFactory;
import br.com.fj.jdbcnelioalves.model.dao.SellerDAO;
import br.com.fj.jdbcnelioalves.model.entities.Seller;

public class Program {

	public static void main(String[] args) {
	
		
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
	}

}
