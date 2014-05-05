package com.clufsolutions.seniatwithholdings.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.clufsolutions.seniatwithholdings.domain.Company;

@RepositoryRestResource
public interface CompanyRepository extends
		PagingAndSortingRepository<Company, Long> {

}
