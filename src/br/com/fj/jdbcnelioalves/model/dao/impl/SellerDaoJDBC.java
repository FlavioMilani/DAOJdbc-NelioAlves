package br.com.fj.jdbcnelioalves.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fj.jdbcnelioalves.db.DataBase;
import br.com.fj.jdbcnelioalves.db.DbException;
import br.com.fj.jdbcnelioalves.model.dao.SellerDAO;
import br.com.fj.jdbcnelioalves.model.entities.Department;
import br.com.fj.jdbcnelioalves.model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
							"INSERT INTO seller "
							+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
							+ "VALUES "
							+ "(?, ?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getMail());
			st.setDate(3, new Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
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
	public void update(Seller seller) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
							"UPDATE seller "
							+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
							+ "WHERE Id = ?");
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getMail());
			st.setDate(3, new Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM seller "
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
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller " 
					+ "INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"
					);
					
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dp = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dp);
				return seller;
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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller "
					+ "INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dp = map.get(rs.getInt("DepartmentId"));
				
				if(dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				} 
				
				
				Seller seller = instantiateSeller(rs, dp);
				list.add(seller);
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
	
	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
	
		
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName("Name");
		seller.setMail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(dp);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller "
					+ "INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dp = map.get(rs.getInt("DepartmentId"));
				
				if(dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				} 
				
				
				Seller seller = instantiateSeller(rs, dp);
				list.add(seller);
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
}
