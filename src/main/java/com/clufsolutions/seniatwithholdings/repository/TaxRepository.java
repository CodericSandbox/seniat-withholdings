package com.clufsolutions.seniatwithholdings.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.clufsolutions.seniatwithholdings.domain.Company;

public interface TaxRepository extends
		PagingAndSortingRepository<Company, Long> {

}
