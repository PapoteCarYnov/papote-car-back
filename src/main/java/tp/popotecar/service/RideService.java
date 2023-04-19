package tp.popotecar.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tp.popotecar.model.Ride;
import tp.popotecar.model.Step;
import tp.popotecar.model.User;
import tp.popotecar.model.enumeration.Status;
import tp.popotecar.repository.RideRepository;
import tp.popotecar.repository.specification.RideSpecification;
import tp.popotecar.service.dto.RideCreateDTO;
import tp.popotecar.service.dto.RideDTO;
import tp.popotecar.service.dto.criteria.RideCriteria;
import tp.popotecar.service.mapper.RideMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideService {

    private static final Logger log = LoggerFactory.getLogger(RideService.class);

    private final RideRepository rideRepository;

    private final UserService userService;

    private final StepService stepService;

    private final RideMapper rideMapper;

    public RideDTO createRide(RideCreateDTO rideCreateDTO) {
        Optional<User> currentUser = userService.getConnectedUser();
        if (currentUser.isPresent()) {
            Ride ride = new Ride();
            ride.setMaxPassenger(rideCreateDTO.getMaxPassenger());
            ride.setCreationDate(LocalDate.now());
            ride.setDriver(currentUser.get());
            ride.setStatus(Status.BROUILLON);

            Ride rideSaved = rideRepository.save(ride);

            rideCreateDTO.getSteps().stream().forEach(stepCreateDTO -> {
                stepService.addStep(stepCreateDTO, rideSaved);
            });

            return getRideDTOById(rideSaved.getId());
        }

        return null;
    }

    public RideDTO getRideDTOById(Long id) {
        Optional<Ride> ride = rideRepository.findById(id);
        return ride.map(rideMapper::toDto).orElse(null);

    }

    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id);

    }

    public List<RideDTO> getRidesByCriteria(RideCriteria rideCriteria) {
        List<Ride> rides = rideRepository.findAll(RideSpecification.specificationFromCriteria(rideCriteria));
        rides.stream().forEach(ride -> ride.setSteps(filterSteps(ride.getSteps(), rideCriteria.getStartCityId(), rideCriteria.getEndCityId(), ride.getId())));
        return rideMapper.toDto(rides);
    }

    public List<Step> filterSteps(List<Step> steps, Long startCityId, Long endCityId, Long rideId) {
        Optional<Step> startStep = stepService.getByCityIdAndRideId(startCityId, rideId);
        Optional<Step> endStep = stepService.getByCityIdAndRideId(endCityId, rideId);
        if (startStep.isPresent() && endStep.isPresent()) {
            return steps.stream().filter(step ->
                    (step.getTime().isAfter(startStep.get().getTime()) && step.getTime().isBefore(endStep.get().getTime())) ||
                            step.getTime().equals(startStep.get().getTime()) || step.getTime().equals(endStep.get().getTime())
            ).toList();
        }
        return steps;
    }

    public void changeRideStatus(Step step) {
        Optional<Ride> rideOptional = rideRepository.findRideByStepsContains(step);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            ride.setStatus(Status.A_VENIR);
            rideRepository.save(ride);
        }
    }


}
