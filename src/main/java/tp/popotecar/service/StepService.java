package tp.popotecar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tp.popotecar.model.Ride;
import tp.popotecar.model.Step;
import tp.popotecar.model.City;
import tp.popotecar.repository.StepRepository;
import tp.popotecar.service.dto.StepCreateDTO;
import tp.popotecar.service.mapper.StepCreateMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepService {

    private final StepRepository stepRepository;

    private final StepCreateMapper stepCreateMapper;

    private final CityService cityService;
    public void addStep(StepCreateDTO stepCreateDTO, Ride ride) {
        Step stepToAdd = stepCreateMapper.toEntity(stepCreateDTO);
        Optional<City> city = cityService.getById(stepCreateDTO.getCityId());
        if (city.isPresent()) {
            stepToAdd.setCity(city.get());
            stepToAdd.setRide(ride);
            stepRepository.save(stepToAdd);
        }
    }

    public Optional<Step> getByCityIdAndRideId(Long cityId, Long rideId) {
        return stepRepository.findByCity_IdAndRide_Id(cityId, rideId);
    }

    public Optional<Step> getById(Long id) {
        return stepRepository.findById(id);
    }
}
