package hotelstructure.demo.Repository;

import hotelstructure.demo.Entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
        boolean existsByNumber(Integer number);

        Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);
}
