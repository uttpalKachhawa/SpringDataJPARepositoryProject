package com.guitar.db.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guitar.db.model.Manufacturer;

public interface ManufactureJpaRepository extends JpaRepository<Manufacturer, Long> {

	//Less Than Date
	List<Manufacturer> findByFoundedDateBefore(Date d);

	//Grater Than Date
	List<Manufacturer> findByFoundedDateAfter(Date d);
	
	//InBetween Dates
	List<Manufacturer> findByFoundedDateBetween(Date before,Date after);
	
	List<Manufacturer> findByActiveTrue();
	List<Manufacturer> findByActiveFalse();
	
	//using the @NamedNativeQuery in Manufacturer model mapping
	List<Manufacturer> getAllThatSellAcoustics(String name);
}
