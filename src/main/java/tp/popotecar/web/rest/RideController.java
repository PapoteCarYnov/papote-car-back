package tp.popotecar.web.rest;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.popotecar.service.RideService;
import tp.popotecar.service.dto.RideCreateDTO;
import tp.popotecar.service.dto.RideDTO;
import tp.popotecar.service.dto.StepCreateDTO;
import tp.popotecar.service.dto.criteria.RideCriteria;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private static final Logger log = LoggerFactory.getLogger(RideController.class);

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideDTO> createRide(
            @RequestBody RideCreateDTO rideCreateDTO
            ) {
        return ResponseEntity.ok(rideService.createRide(rideCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDTO> getRide(
            @PathVariable(value = "id") final Long id
            ) {
        return ResponseEntity.ok(rideService.getRideDTOById(id));
    }

    @GetMapping
    public ResponseEntity<List<RideDTO>> getRidesByCriteria(
            @Parameter RideCriteria rideCriteria
    ) {
        return ResponseEntity.ok(rideService.getRidesByCriteria(rideCriteria));
    }
}
