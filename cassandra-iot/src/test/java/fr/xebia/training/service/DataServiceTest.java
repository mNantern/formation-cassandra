package fr.xebia.training.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.xebia.training.repository.DataRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class DataServiceTest {

  @InjectMocks
  private DataService dataService;

  @Mock
  private DataRepository dataRepository;

  @Test
  public void testInsertInputData() throws Exception {
    //Given

    //When
    dataService.insertInputData(null);

    //Then
  }
}