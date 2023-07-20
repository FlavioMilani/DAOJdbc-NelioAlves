package br.com.fj.jdbcnelioalves.model.dao;

import java.util.List;

import br.com.fj.jdbcnelioalves.model.entities.Department;
import br.com.fj.jdbcnelioalves.model.entities.Seller;

public interface SellerDAO {
	
	 void insert(Seller seller);
	 void update(Seller seller);
	 void deleteById(Integer id);
	 Seller findById(Integer id);
	 List<Seller> findAll();
	 List<Seller> findByDepartment(Department department);
}
