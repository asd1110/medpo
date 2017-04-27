package com.wipro.medclaim.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import com.wipro.medclaim.bean.ClaimBean;
import com.wipro.medclaim.util.DBUtil;

public class ClaimDAO 
{
	Connection conn = DBUtil.getDBConnection();
	public int insertClaim(ClaimBean claimBean)
	{
		int claimId = 0;
		int success = 0;
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select Claim_id_seq.nextval from dual");
			if(rs.next())
			{
				claimId = rs.getInt(1);
			}
			PreparedStatement ps = conn.prepareStatement("insert into Claim_Tb values(?,?,?,?,?,?,?)");
			ps.setInt(1,claimId);
			ps.setString(2,claimBean.getUserId());
			ps.setString(3,claimBean.getNatureOfClaim());
			ps.setString(4,claimBean.getIllness());
			ps.setDouble(5,claimBean.getAmount());
			ps.setString(6,claimBean.getMonth());
			ps.setString(7,claimBean.getYear());
			
			success = ps.executeUpdate();
			
			return success;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return success;
	}
	
	public ClaimBean fetchClaim(int claimId)
	{
		ClaimBean cl = new ClaimBean();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Claim_Tb where CLAIMID=" + claimId);
			if(rs.next())
			{
				cl.setClaimId(rs.getInt(1));
				cl.setUserId(rs.getString(2));
				cl.setNatureOfClaim(rs.getString(3));
				cl.setIllness(rs.getString(4));
				cl.setAmount(rs.getDouble(5));
				cl.setMonth(rs.getString(6));
				cl.setYear(rs.getString(7));
			}
			return cl;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public double fetchTotalClaim(String userId)
	{
		try
		{
			Statement stmt = conn.createStatement();
			double totalAmount = 0;
			ResultSet rs = stmt.executeQuery("select AMOUNT from Claim_Tb where userId=" + userId + "and year='" + Calendar.YEAR + "'");
			while(rs.next())
			{
				totalAmount += rs.getDouble(1);
			}
			return totalAmount;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
}
