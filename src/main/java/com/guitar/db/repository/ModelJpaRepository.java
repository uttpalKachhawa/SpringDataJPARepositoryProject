package com.guitar.db.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guitar.db.model.Model;

public interface ModelJpaRepository extends JpaRepository<Model, Long> ,ModelJpaRepositoryCustom{

	List<Model> findByPriceGreaterThanEqualsAndPriceLessThanEquals(BigDecimal lowest,BigDecimal highest);
	
	//can pass a ModelType object also in list in argument
	List<Model> findByModelTypeNameIn(List<String> types);
	List<Model> findByModelTypeNameNotIn(List<String> types);
	
	@Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood ")
	List<Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal lowest,
			@Param("highest") BigDecimal highest,
			@Param("wood") String wood);
	
//Performing pagination while selecting any record and returning to UI
	//Not: Pageable and Sort both interface cannot be used in same argument lists
	@Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood ")
	Page<Model> queryByPriceRangeAndWoodTypes(@Param("lowest") BigDecimal lowest,
			@Param("highest") BigDecimal highest,
			@Param("wood") String wood,Pageable pageable);
	
	List<Model> findAllModelsByType(@Param ("name") String name);
}
