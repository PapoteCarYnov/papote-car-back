package tp.popotecar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tp.popotecar.model.Price;
import tp.popotecar.model.Step;
import tp.popotecar.repository.PriceRepository;
import tp.popotecar.service.dto.PriceDTO;
import tp.popotecar.service.mapper.PriceMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    private final StepService stepService;

    private final PriceMapper priceMapper;

    private final RideService rideService;

    public void addPrice(PriceDTO priceDTO) {
        Price price = priceMapper.toEntity(priceDTO);
        Optional<Step> startStep = stepService.getById(priceDTO.getStartStepId());
        Optional<Step> endStep = stepService.getById(priceDTO.getEndStepId());
        if (startStep.isPresent() && endStep.isPresent()) {
            rideService.changeRideStatus(startStep.get());
            price.setStartStep(startStep.get());
            price.setEndStep(endStep.get());
            priceRepository.save(price);
        }
    }


}
