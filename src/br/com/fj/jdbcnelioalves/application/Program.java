package br.com.fj.jdbcnelioalves.application;



import java.util.List;

import br.com.fj.jdbcnelioalves.model.dao.DaoFactory;
import br.com.fj.jdbcnelioalves.model.dao.SellerDAO;
import br.com.fj.jdbcnelioalves.model.entities.Department;
import br.com.fj.jdbcnelioalves.model.entities.Seller;

public class Program {

	public static void main(String[] args) {
	
		
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("===== Teste 1: Seller findById =====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n===== Teste 2: Seller findByDepartment =====");

		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n===== Teste 3: Seller findAll =====");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
	}

}
