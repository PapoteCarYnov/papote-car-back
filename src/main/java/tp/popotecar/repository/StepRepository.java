package tp.popotecar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.popotecar.model.Step;

import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long> {

    Optional<Step> findByCity_IdAndRide_Id(Long cityId, Long rideId);
}
