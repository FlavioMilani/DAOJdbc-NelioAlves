package br.com.fj.jdbcnelioalves.model.dao;

import br.com.fj.jdbcnelioalves.db.DataBase;
import br.com.fj.jdbcnelioalves.model.dao.impl.DepartmentDaoJDBC;
import br.com.fj.jdbcnelioalves.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDAO createSellerDao() {
		return new SellerDaoJDBC(DataBase.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDao() {
		return new DepartmentDaoJDBC(DataBase.getConnection());
	}
}
