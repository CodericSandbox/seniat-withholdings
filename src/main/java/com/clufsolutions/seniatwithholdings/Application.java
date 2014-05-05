package com.clufsolutions.seniatwithholdings;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.clufsolutions.seniatwithholdings.domain.Company;
import com.clufsolutions.seniatwithholdings.domain.Document;
import com.clufsolutions.seniatwithholdings.domain.Tax;
import com.clufsolutions.seniatwithholdings.domain.Vendor;
import com.clufsolutions.seniatwithholdings.domain.Withholding;
import com.clufsolutions.seniatwithholdings.domain.embeddable.Address;
import com.clufsolutions.seniatwithholdings.domain.embeddable.Rif;
import com.clufsolutions.seniatwithholdings.repository.CompanyRepository;
import com.clufsolutions.seniatwithholdings.repository.DocumentRepository;
import com.clufsolutions.seniatwithholdings.repository.TaxRepository;
import com.clufsolutions.seniatwithholdings.repository.VendorRepository;
import com.clufsolutions.seniatwithholdings.repository.WithholdingRepository;

@Configuration
@ComponentScan
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private VendorRepository vendorRepository;
	@Autowired
	private WithholdingRepository withholdingRepository;
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private TaxRepository taxRepository;

	private Vendor v1;
	private Vendor v2;
	private Company c1;
	private Withholding w1;
	private Document d1;
	private Document d2;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		deletesAll();
		createCompany();
		createVendors();
		createWithholdings();
	}

	private void deletesAll() {
		taxRepository.deleteAll();
		documentRepository.deleteAll();
		withholdingRepository.deleteAll();
		vendorRepository.deleteAll();
		companyRepository.deleteAll();
	}

	private void createWithholdings() {
		w1 = new Withholding(c1, v1, Withholding.Type.IVA, Withholding.Operation.C);
		d1 = new Document(Document.Type.INV, "ljkasd515", "54asf564", new Date(), 20000d, 10000d);
		d2 = new Document(Document.Type.INV, "lkj5s4df", "354s65sd65f4", new Date(), 10000d, 5000d);

		d1.setWithholding(w1);
		d2.setWithholding(w1);

		w1.getDocuments().add(d1);
		w1.getDocuments().add(d2);

		withholdingRepository.save(w1);
	}

	private void createCompany() {
		c1 = new Company("Cluf Consulting C.A", new Address("Calle 2 Casa 2", "Las Morochas", "4146711769", Address.State.ZU, Address.City.OJEDA), new Tax(
				"IVA", 12f, true, false));
		companyRepository.save(c1);
	}

	private void createVendors() {
		v1 = new Vendor("Margaret Luis", new Rif(Rif.Type.V, "179963096"), 75d, new Address("Calle 1 Casa 2", "Las Morochas", "4146711769", Address.State.ZU,
				Address.City.OJEDA));
		v2 = new Vendor("Alejandro Caceres", new Rif(Rif.Type.V, "178254035"), 100d, new Address("Calle 2 Casa 1", "Las Morochas", "4146711769",
				Address.State.ZU, Address.City.OJEDA));
		vendorRepository.save(Arrays.asList(v1, v2));
	}
}
