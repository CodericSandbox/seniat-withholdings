package com.clufsolutions.seniatwithholdings.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.clufsolutions.seniatwithholdings.domain.Vendor;

@RepositoryRestResource
public interface VendorRepository extends PagingAndSortingRepository<Vendor, Long> {

}
