package com.etnetera.jslibrary;

import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.repository.FrameworkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrameworkRepositoryTest extends AbstractTest {

	@Autowired
	FrameworkRepository frameworkRepository;

	@BeforeEach
	void setUpTestCaseData(){
		Framework fw = new Framework("React", List.of("version"));
		frameworkRepository.insert(List.of(fw));
	}

	@Test
	void canCreateFWItem() {
		Framework fw = new Framework("Vue", List.of("version"));
		frameworkRepository.insert(fw);

		List<Framework> fwList = frameworkRepository.findAll();
		assertEquals(2, fwList.size(), "collection size does not match");
		assertTrue(fwList.contains(fw), "object not in collection");
	}

	@Test
	void canUpdateFWItem(){
		List<String> versions = List.of("1.0.0");

		Framework fw = frameworkRepository.findByName("React").orElseThrow();
		fw.setVersions(versions);
		frameworkRepository.save(fw);

		Framework updatedInstance = frameworkRepository.findByName("React").orElseThrow();
		assertEquals(updatedInstance.getVersions(), versions, "updated value does not match");
		assertEquals(1, frameworkRepository.findAll().size(), "collection size does not match");
	}

	@Test
	void canDeleteFWItem(){
		frameworkRepository.deleteByName("React");
		assertEquals(0, frameworkRepository.findAll().size());
	}

	@Test
	void throwsExceptionWhenCreatingObjectWithUniqueField(){
		assertThrows(DuplicateKeyException.class, () -> {
			Framework fw = new Framework("React", List.of("version"));
			frameworkRepository.insert(fw);
		});
	}

}
