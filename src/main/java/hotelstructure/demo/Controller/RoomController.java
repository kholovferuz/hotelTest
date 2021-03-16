package hotelstructure.demo.Controller;

import hotelstructure.demo.DTO.RoomDTO;
import hotelstructure.demo.Entity.Hotel;
import hotelstructure.demo.Entity.Room;
import hotelstructure.demo.Repository.HotelRepository;
import hotelstructure.demo.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    //READ all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }

    // CREATE a new room
    @PostMapping
    public String addNewRoom(@RequestBody RoomDTO roomDTO) {
        Room room = new Room();
        boolean byNumber = roomRepository.existsByNumber(roomDTO.getNumber());
        if (!byNumber) {
            room.setNumber(roomDTO.getNumber());
            room.setFloor(roomDTO.getFloor());
            room.setSize(roomDTO.getSize());
        } else {
            return "This room number already exists";
        }
        // hotel
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotelId());
        if (optionalHotel.isPresent()) {
            room.setHotel(optionalHotel.get());
        } else {
            return "Hotel with this id is not found";
        }
        roomRepository.save(room);
        return "Room added";
    }

    // UPDATE room by id
    @PutMapping("/{id}")
    public String updateRoom(@PathVariable Integer id, @RequestBody RoomDTO roomDTO) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        boolean update = false;
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            // room
            boolean byNumber = roomRepository.existsByNumber(roomDTO.getNumber());
            if (!byNumber) {
                room.setNumber(roomDTO.getNumber());
                room.setFloor(roomDTO.getFloor());
                room.setSize(roomDTO.getSize());
            } else {
                return "This room number already exists";
            }


            // hotel
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotelId());
            if (optionalHotel.isPresent()) {
                room.setHotel(optionalHotel.get());
            } else {
                return "Hotel not found";
            }

            roomRepository.save(room);
            update = true;

        }
        return update ? "Room updated" : "Room with this id is not found";
    }

    // DELETE room by id
    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        List<Room> roomList = roomRepository.findAll();
        boolean delete = false;
        for (Room room : roomList) {
            if (room.getId() == id) {
                roomRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Room deleted" : "Room with this id is not found";
    }

    // HOTEL id orqali mehmonhonaga tegishli xonalar royxati
    @GetMapping("/roomsByHotel/{hotel_id}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotel_id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAllByHotelId(hotel_id, pageable);
        return roomPage;
    }
}
