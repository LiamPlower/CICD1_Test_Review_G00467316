package ie.atu.sample.service;

import ie.atu.sample.dto.BookingRequest;
import ie.atu.sample.dto.BookingResponse;
import ie.atu.sample.exception.CapacityExceededException;
import ie.atu.sample.exception.ResourceNotFoundException;
import ie.atu.sample.model.Booking;
import ie.atu.sample.model.Room;
import ie.atu.sample.repository.BookingRepository;
import ie.atu.sample.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public BookingResponse createBooking(BookingRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room not found with id " + request.getRoomId()));

        if (request.getAttendeeCount() > room.getCapacity()) {
            throw new CapacityExceededException(
                    "Attendee count " + request.getAttendeeCount()
                            + " exceeds room capacity of " + room.getCapacity());
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setOrganiserName(request.getOrganiserName());
        booking.setAttendeeCount(request.getAttendeeCount());

        return toResponse(bookingRepository.save(booking));
    }

    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id " + id));
        return toResponse(booking);
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getRoom().getId(),
                booking.getRoom().getName(),
                booking.getOrganiserName(),
                booking.getAttendeeCount(),
                booking.getStatus());
    }
}
