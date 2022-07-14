package com.etnetera.jslibrary;

import com.etnetera.jslibrary.managers.FrameworkVersionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameworkVersionManagerTest extends AbstractTest {

    @Autowired FrameworkVersionManager frameworkVersionManager;

    @Test
    public void testSortListOfVersions(){
        List<String> listUnsorted = List.of("1.0.0", "0.0.1", "2.0.1", "1.5.0");
        List<String> result = frameworkVersionManager.sortListOfVersions(listUnsorted);
        assertEquals(List.of("0.0.1", "1.0.0", "1.5.0", "2.0.1"), result);
    }

}
