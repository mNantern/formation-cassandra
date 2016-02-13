package fr.xebia.training.domain.mapping;

import fr.xebia.extras.selma.Mapper;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.InputData;

@Mapper(withIgnoreFields = "id")
public interface InputDataMapper {

  Data asData(InputData in);
}
