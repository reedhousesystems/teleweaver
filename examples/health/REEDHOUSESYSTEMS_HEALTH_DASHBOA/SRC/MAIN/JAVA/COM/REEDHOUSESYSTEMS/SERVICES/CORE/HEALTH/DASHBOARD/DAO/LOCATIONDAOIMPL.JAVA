package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.LocationDao;
import com.reedhousesystems.services.core.health.dashboard.dto.LocationDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Location;

@Repository("locationDao")
public class LocationDaoImpl extends BaseDao implements LocationDao {
	
	public void saveLocation(Location location) throws Exception {
		try {
			getSession().save(location);
		} catch (HibernateException e) {
			throw new Exception("An error occurred while adding or updating a location", e);
		}
	}
	
	public void deleteLocation(String locationId) throws Exception {
		Location locationToDelete = getLocationById(locationId);
		try {
			getSession().delete(locationToDelete);
		} catch (HibernateException e) {
			throw new Exception("An error occurred while deleting a location", e);
		}
	}
	
	public Location getLocationById(String locationId) throws Exception {
		Location location = null;
		try {
			Criteria criteria = getSession().createCriteria(Location.class)
				.add(Restrictions.eq("uuid", locationId));
			location = (Location) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting location", e);
		}
		return location;
	}

	public Location getLocation(LocationDTO locationDTO) {
		Criteria criteria = getSession().createCriteria(Location.class)
			.add(Restrictions.eq("province", locationDTO.getProvince()))
			.add(Restrictions.eq("district", locationDTO.getDistrict()))
			.add(Restrictions.eq("municipality", locationDTO.getMunicipality()))
			.add(Restrictions.eq("village", locationDTO.getVillage()));
		Location location = (Location) criteria.uniqueResult();
		return location;
	}
	
	public List<Location> getAllLocations() throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			Criteria criteria = getSession().createCriteria(Location.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			locationList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting all the locations", e);
		}
		return locationList;
	}
	
	public List<Location> getDistrictsByProvince(String province) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			Criteria criteria = getSession().createCriteria(Location.class)
				.add(Restrictions.eq("province", province))
				.setProjection(Projections.property("district"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			locationList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting location information by province", e);
		}
		return locationList;
	}
	
	public List<Location> getMunicipalitiesByDistrict(String district) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			Criteria criteria = getSession().createCriteria(Location.class);
			criteria.add(Restrictions.eq("district", district))
				.setProjection(Projections.property("municipality"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			locationList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting location information by district", e);
		}
		return locationList;
	}
	
	public List<Location> getVillagesByMunicipality(String municipality) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			Criteria criteria = getSession().createCriteria(Location.class);
			criteria.add(Restrictions.eq("municipality", municipality))
				.setProjection(Projections.property("village"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			locationList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting location information by municipality", e);
		}
		return locationList;
	}
	
}