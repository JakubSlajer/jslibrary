package com.etnetera.jslibrary;

import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.repository.FrameworkRepository;
import com.etnetera.jslibrary.service.FrameworkService;
import com.etnetera.jslibrary.utils.VersionNumberGenerator;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JslibraryApplication.class)
public class FrameworkServiceTest {

    @MockBean
    FrameworkRepository frameworkRepository;

    @Autowired
    FrameworkService frameworkService;
    @Autowired
    VersionNumberGenerator versionNumberGenerator;


    @BeforeEach
    public void setUp(){
        List<Framework> fwList = new ArrayList<>();
        List<String> fwNames = List.of("React", "Vue", "AngularJS");

        for (var name: fwNames){
            Framework framework = new Framework();
            framework.setName(name);
            var count = versionNumberGenerator.randInt(1,4);
            framework.setVersions(versionNumberGenerator.generateRandomVersionList(count));
            fwList.add(framework);
        }

        Mockito.when(frameworkRepository.findAll()).thenReturn(fwList);
        Mockito.when(frameworkRepository.findByName(any())).thenReturn(fwList.stream().findAny());
    }

    @Test
    public void testFindAll(){
        frameworkService.findAll();
        verify(frameworkRepository).findAll();
        verifyNoMoreInteractions(frameworkRepository);
    }

    @Test
    public void testFindByName(){
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        var name = "name";
        frameworkService.findByName(name);
        verify(frameworkRepository).findByName(argumentCaptor.capture());
        verifyNoMoreInteractions(frameworkRepository);
        assertEquals(name, argumentCaptor.getValue());
    }

    @Test
    public void testUpdateName(){
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Framework> instanceCaptor = ArgumentCaptor.forClass(Framework.class);
        try{
            frameworkService.updateName("React", "ReactJS");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        verify(frameworkRepository).findByName(argumentCaptor.capture());
        verify(frameworkRepository).save(instanceCaptor.capture());
        assertEquals("React", argumentCaptor.getValue());
        assertEquals(instanceCaptor.getValue().getName(), "ReactJS");
        verifyNoMoreInteractions(frameworkRepository);
    }

    @Test
    public void testDelete(){
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        try {
            frameworkService.delete("React");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        verify(frameworkRepository).findByName(any());
        verify(frameworkRepository).deleteByName(argumentCaptor.capture());
        verifyNoMoreInteractions(frameworkRepository);
        assertEquals("React", argumentCaptor.getValue());
    }

    @Test
    public void testAddVersion(){
        ArgumentCaptor<Framework> instanceCaptor = ArgumentCaptor.forClass(Framework.class);
        frameworkService.addVersion("React", "1.0.0");
        verify(frameworkRepository).findByName("React");
        verify(frameworkRepository).save(instanceCaptor.capture());
        verifyNoMoreInteractions(frameworkRepository);
        assertTrue(instanceCaptor.getValue().getVersions().contains("1.0.0"));
    }
}
