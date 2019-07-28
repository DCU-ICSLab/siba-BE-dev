package org.icslab.sibadev.devices.test.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ReservationListDTO {

    private List<ReservationDTO> reservationList;
}
