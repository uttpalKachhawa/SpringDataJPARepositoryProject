package com.guitar.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guitar.db.model.Location;

public interface LocationJpaRepository extends JpaRepository<Location, Long>{
	List<Location> findByStateLike(String stateName);
	List<Location> findByStateStartingWith(String stateName);
	List<Location> findByStateEndingWith(String stateName);
	List<Location> findByStateContaining(String stateName);
	List<Location> findByStateOrCountry(String value1,String value2);
	List<Location> findByStateOrCountryEquals(String value1,String value2);
	List<Location> findByStateAndCountry(String valu1,String value2);
	List<Location> findByStateNot(String state);
	List<Location> findByStateNotLike(String state);
	List<Location> findByStateIgnoreCaseStartingWith(String state);
	List<Location> findByStateIgnoreCase(String state);
	List<Location> findByStateNotLikeOrderByStateAsc(String state);
	List<Location> findByStateNotLikeOrderByStateDesc(String state);
	Location findFirstByStateIgnoreCaseStartingWith(String state);
	
	
}
