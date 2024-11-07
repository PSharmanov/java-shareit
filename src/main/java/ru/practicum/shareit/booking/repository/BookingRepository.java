package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.booker.id = :bookerId " +
            "AND b.item.id = :itemId " +
            "AND b.status = 'APPROVED' ")
    boolean existsApprovedBooking(@Param("bookerId") Long bookerId,
                                  @Param("itemId") Long itemId);

    Booking findByItemIdAndBookerId(Long itemId, Long bookerId);
}
