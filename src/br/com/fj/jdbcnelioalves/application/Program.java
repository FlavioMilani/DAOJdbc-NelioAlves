package br.com.fj.jdbcnelioalves.application;


import java.util.Date;

import br.com.fj.jdbcnelioalves.model.entities.Department;
import br.com.fj.jdbcnelioalves.model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department department = new Department(1, "books");
		Seller seller = new Seller(21, "Bob", "bob@dominio.com", new Date(), 3000.0, department);
		
		System.out.println(seller);
	}

}
