package com.guitar.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.guitar.db.model.Location;
import com.guitar.db.repository.LocationJpaRepository;
import com.guitar.db.repository.LocationRepository;

@ContextConfiguration(locations={"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationPersistenceTests {
	/*@Autowired
	private LocationRepository locationRepository;*/

	@Autowired
	private LocationJpaRepository locationJpaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Test
	public void testJpaFind(){
		
		List<Location> loc=locationJpaRepository.findAll();
		Assert.assertNotNull(loc);
	}
	
	
	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Location location = new Location();
		location.setCountry("Canada");
		location.setState("British Columbia");
		/*location = locationRepository.create(location);*/
		location=locationJpaRepository.saveAndFlush(location);
		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		/*Location otherLocation = locationRepository.find(location.getId());*/
		
		Location otherLocation=locationJpaRepository.findOne(location.getId());
		
		assertEquals("Canada", otherLocation.getCountry());
		assertEquals("British Columbia", otherLocation.getState());
		
		//delete BC location now
		/*locationRepository.delete(otherLocation);*/
		locationJpaRepository.delete(otherLocation);
	}

	@Test
	public void testFindWithLike() throws Exception {
		/*List<Location> locs = locationRepository.getLocationByStateName("New");*/
		List<Location> locs = locationJpaRepository.findByStateLike("New%");
		assertEquals(4, locs.size());
	}
	
	@Test
	public void testFindWithOrderAscORDesc() throws Exception {
		/*List<Location> locs = locationRepository.getLocationByStateName("New");*/
		List<Location> locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		assertEquals(46, locs.size());
		
		locs.forEach((location)->{
		System.out.println(location.getState());
	});
	}
	
	public void testFindWithStartsIgnoreCase() throws Exception {
		/*List<Location> locs = locationRepository.getLocationByStateName("New");*/
		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseStartingWith("new");
		assertEquals(4, locs.size());
	}
	
	public void testFindFirstStateStartsWith() throws Exception {
		/*List<Location> locs = locationRepository.getLocationByStateName("New");*/
		Location locs = locationJpaRepository.findFirstByStateIgnoreCaseStartingWith("a");
		assertEquals("Albama", locs.getState());
	}

	@Test
	public void testFindWithNotLike() throws Exception {
		/*List<Location> locs = locationRepository.getLocationByStateName("New");*/
		List<Location> locs = locationJpaRepository.findByStateNotLike("New%");
		assertEquals(46, locs.size());
	}

	
	
	@Test
	@Transactional  //note this is needed because we will get a lazy load exception unless we are in a tx
	public void testFindWithChildren() throws Exception {
	/*	Location arizona = locationRepository.find(3L);*/
		Location arizona = locationJpaRepository.findOne(3L);
		assertEquals("United States", arizona.getCountry());
		assertEquals("Arizona", arizona.getState());
		
		assertEquals(1, arizona.getManufacturers().size());
		
		assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
	}
	
	@Test
	public void testFindWithStateAndCountryJpa() throws Exception{
		List<Location> locations=locationJpaRepository.findByStateAndCountry("Utah", "United States");
		Assert.assertNotNull(locations);
		Assert.assertEquals("Utah",locations.get(0).getState());
		
	}
	
	@Test
	public void testFindBYStateNot() throws Exception{
		List<Location> locations=locationJpaRepository.findByStateNot("Utah");
		Assert.assertNotNull(locations);
		Assert.assertNotSame("Utah",locations.get(0).getState());
		
	}
	
	@Test
	public void testFindWithStateORCountryJpa() throws Exception{
		List<Location> locations=locationJpaRepository.findByStateOrCountry("Utah", "Utah");
		Assert.assertNotNull(locations);
		Assert.assertEquals("Utah",locations.get(0).getState());
		
	}
	
	@Test
	public void testFindWithStateORCountryJpaEquals() throws Exception{
		List<Location> locations=locationJpaRepository.findByStateOrCountryEquals("Utah", "Utah");
		Assert.assertNotNull(locations);
		Assert.assertEquals("Utah",locations.get(0).getState());
		
	}
	
	
}
