package tp.popotecar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tp.popotecar.model.Ride;
import tp.popotecar.model.Step;

import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long>, JpaSpecificationExecutor<Ride> {

    @Override
    Optional<Ride> findById(Long id);

    Optional<Ride> findRideByStepsContains(Step step);
}
