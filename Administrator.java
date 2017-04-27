package com.wipro.medclaim.service;

import com.wipro.medclaim.bean.ClaimBean;
import com.wipro.medclaim.dao.ClaimDAO;
import com.wipro.medclaim.util.ExceededClaimLimitException;

public class Administrator 
{
	String addClaim(ClaimBean claimBean)
	{
		ClaimDAO cldao = new ClaimDAO();
		String temp="";
		if(claimBean==null)
			return "FAIL";
		if(claimBean.getUserId()==null || claimBean.getUserId().length()==0)
			return "FAIL";
		double totalAmount = cldao.fetchTotalClaim(claimBean.getUserId());
		if(totalAmount + claimBean.getAmount()<10000)
		{
			int success = cldao.insertClaim(claimBean);
			if(success>0)
			{
				return "SUCCESS";
			}
		}
		else if(totalAmount + claimBean.getAmount()>=10000)
		{
			try
			{
				throw new ExceededClaimLimitException();
			}
			catch(ExceededClaimLimitException e)
			{
				temp = e.toString();
			}
		}
		return temp;
	}
	ClaimBean viewClaim(int claimId)
	{
		ClaimBean cl = new ClaimBean();
		ClaimDAO cldao = new ClaimDAO();
		cl = cldao.fetchClaim(claimId);
		cl.setTotalClaim(cldao.fetchTotalClaim(cl.getUserId()));
		if(claimId == cl.getClaimId())
			return cl;
		else
			return null;
	}
}
