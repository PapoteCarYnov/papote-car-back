package tp.popotecar.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tp.popotecar.model.City;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StepDTO {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private City city;

    private List<PriceDTO> prices;
}
