package br.com.fj.jdbcnelioalves.application;

import br.com.fj.jdbcnelioalves.model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "books");
		
		System.out.println(obj);
	}

}
