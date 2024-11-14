package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.owner.id = :ownerId")
    List<Item> getItemsByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT i FROM Item i " +
            "WHERE (upper(i.name) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(i.description) LIKE upper(concat('%', ?1, '%'))) " +
            "AND i.available = true")
    List<Item> search(String text);
}
