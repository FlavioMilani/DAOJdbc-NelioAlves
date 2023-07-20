package br.com.fj.jdbcnelioalves.model.dao;

import java.util.List;

import br.com.fj.jdbcnelioalves.model.entities.Seller;

public interface SellerDAO {
	
	 void insert(Seller seller);
	 void update(Seller seller);
	 void deleteById(Integer id);
	 void findById(Integer id);
	 List<Seller> findAll();
}
