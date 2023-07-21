package br.com.fj.jdbcnelioalves.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fj.jdbcnelioalves.db.DataBase;
import br.com.fj.jdbcnelioalves.db.DbException;
import br.com.fj.jdbcnelioalves.model.dao.DepartmentDAO;
import br.com.fj.jdbcnelioalves.model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
						"INSERT INTO department "
						+ "(Name) "
						+ "VALUES "
						+ "(?)",
						Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, department.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}

				DataBase.closeResultSet(rs);
			} else {
				throw new DbException("Erro inexperado, nenhuma linha afetada");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DataBase.closeStatement(st);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
						"UPDATE department "
						+ "SET name = ? "
						+ "WHERE Id = ?");
			
			st.setString(1, department.getName());
			st.setInt(2, department.getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Atualizado!");
			} else {
				throw new DbException("Erro inexperado, nenhuma linha afetada");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DataBase.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DataBase.closeStatement(st);
		}
	}
		

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM department " 
					+ "WHERE Id = ?");
					
			st.setInt(1, id);
			rs = st.executeQuery();
				
			if(rs.next()) {
				Department dp = instantiateDepartment(rs);
				return dp;
			}
			return null;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DataBase.closeStatement(st);
			DataBase.closeResultSet(rs);
		}
		
		
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM department "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				
				Department department = instantiateDepartment(rs);
				list.add(department);
			}
			return list;
		} 
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DataBase.closeStatement(st);
			DataBase.closeResultSet(rs);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("Id"));
		dp.setName(rs.getString("Name"));
		return dp;
	}

}
