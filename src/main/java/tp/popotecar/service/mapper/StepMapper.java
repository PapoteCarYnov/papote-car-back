package tp.popotecar.service.mapper;

import org.mapstruct.Mapper;
import tp.popotecar.model.Step;
import tp.popotecar.service.dto.StepDTO;

@Mapper(componentModel = "spring", uses = {PriceMapper.class})
public interface StepMapper extends EntityMapper<StepDTO, Step> {

}
