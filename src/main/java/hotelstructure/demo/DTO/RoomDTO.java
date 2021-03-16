package hotelstructure.demo.DTO;

import lombok.Data;

@Data
public class RoomDTO {
    private Integer number;
    private Integer floor;
    private Double size;

    // hotel
    private Integer hotelId;
}
