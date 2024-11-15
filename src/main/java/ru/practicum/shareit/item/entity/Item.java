package ru.practicum.shareit.item.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.user.entity.User;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean available;

    @ElementCollection
    @CollectionTable(name = "comments", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "id")
    private List<Long> comments;
}
