package br.com.fj.jdbcnelioalves.model.dao;

import java.util.List;

import br.com.fj.jdbcnelioalves.model.entities.Department;

public interface DepartmentDAO {
	
	 void insert(Department department);
	 void update(Department department);
	 void deleteById(Integer id);
	 Department findById(Integer id);
	 List<Department> findAll();
}
