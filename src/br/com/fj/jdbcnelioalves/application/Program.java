package br.com.fj.jdbcnelioalves.application;


import java.util.Date;

import br.com.fj.jdbcnelioalves.model.dao.DaoFactory;
import br.com.fj.jdbcnelioalves.model.dao.SellerDAO;
import br.com.fj.jdbcnelioalves.model.entities.Department;
import br.com.fj.jdbcnelioalves.model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department department = new Department(1, "books");
		Seller seller = new Seller(21, "Bob", "bob@dominio.com", new Date(), 3000.0, department);
		
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(seller);
	}

}
