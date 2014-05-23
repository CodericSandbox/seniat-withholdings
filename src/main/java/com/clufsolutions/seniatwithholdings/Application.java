package com.clufsolutions.seniatwithholdings;

import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
import com.clufsolutions.seniatwithholdings.domain.IslrConcept;
import com.clufsolutions.seniatwithholdings.domain.Tax;
import com.clufsolutions.seniatwithholdings.domain.Vendor;
import com.clufsolutions.seniatwithholdings.domain.Withholding;
import com.clufsolutions.seniatwithholdings.embeddable.Address;
import com.clufsolutions.seniatwithholdings.embeddable.Rif;
import com.clufsolutions.seniatwithholdings.repository.CompanyRepository;
import com.clufsolutions.seniatwithholdings.repository.DocumentRepository;
import com.clufsolutions.seniatwithholdings.repository.IslrConceptRepository;
import com.clufsolutions.seniatwithholdings.repository.TaxRepository;
import com.clufsolutions.seniatwithholdings.repository.VendorRepository;
import com.clufsolutions.seniatwithholdings.repository.WithholdingRepository;
import com.clufsolutions.seniatwithholdings.xml.XmlDocument;
import com.clufsolutions.seniatwithholdings.xml.XmlWithholding;

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
	@Autowired
	private IslrConceptRepository conceptRepository;

	private Vendor v1;
	private Vendor v2;
	private Company c1;
	private Withholding w1;
	private Document d1;
	private Document d2;
	private IslrConcept cn1;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		deletesAll();
		createConcepts();
		createCompany();
		createVendors();
		createWithholdings();

		marshallThings();
	}

	private void marshallThings() {
		try {
			Marshaller m = JAXBContext.newInstance(XmlWithholding.class, XmlDocument.class).createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(new XmlWithholding(w1, w1.getCompany().getTaxes().get("IVA").getAlicuote()), System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private void createConcepts() {
		cn1 = new IslrConcept("0000", "Declaración Retenciones Sin Operaciones", 0f);
		conceptRepository.save(Arrays.asList(cn1));
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
		d1 = new Document(Document.Type.INV, "ljkasd515", 20000d, 10000d);
		d2 = new Document(Document.Type.INV, "lkj5s4df", 10000d, 5000d);

		d1.setWithholding(w1);
		d2.setWithholding(w1);

		w1.getDocuments().add(d1);
		w1.getDocuments().add(d2);

		withholdingRepository.save(w1);
	}

	private void createCompany() {
		c1 = new Company(new Rif(Rif.Type.V, "11289937"), "Cluf Consulting C.A", new Address("Calle 2 Casa 2", "Las Morochas", "4146711769", Address.State.ZU,
				Address.City.OJEDA), 1l, 1l);
		c1.getTaxes().put("IVA", new Tax(c1, "IVA", 12f, true, false));
		
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