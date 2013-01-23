package com.xzjmt.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xzjmt.dao.CityDAO;
import com.xzjmt.entity.City;
import com.xzjmt.util.ipseek.IPSeeker;


@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class CityMng {

	@Autowired
	private CityDAO cityDAO;
	@Autowired
	private IPSeeker ipSeeker;
	
	public City findById(Integer id)
	{
		return cityDAO.findById(id);
	}
	
	public List<City> findAll()
	{
		return cityDAO.findAll();
	}
	
	public String getIpName(String country)
	{
		if(country.indexOf("省")!=-1)
		{
			String strs[] = country.split("省");
			String sub = strs[1];
			if(sub.indexOf("市")!=-1)
			{
				String subStrs[] = sub.split("市");
				return subStrs[0];
			}
			return sub;
		}
		country = country.replace("市", "");
		return country;
	}
	
	public City getCity(HttpServletRequest request)
	{
		String address = ipSeeker.getUserAddress(request);
		String ipName = getIpName(address);
		City city = cityDAO.findUniq("ipName", ipName);
		return city;
	}
	
	
	public City getDefaultCity()
	{
		return cityDAO.findById(1);
	}
}
