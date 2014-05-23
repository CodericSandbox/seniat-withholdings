package com.clufsolutions.seniatwithholdings.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import com.clufsolutions.seniatwithholdings.domain.Withholding;

@RepositoryRestResource
public interface WithholdingRepository extends
		PagingAndSortingRepository<Withholding, Long> {

	Set<Withholding> findByCreatedDateBetween(
			@DateTimeFormat(pattern = "dd-MM-yyyy") @Param("from") Date from,
			@DateTimeFormat(pattern = "dd-MM-yyyy") @Param("to") Date to);

}