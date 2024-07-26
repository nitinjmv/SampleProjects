package dev.jmv.basic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BASICS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
}
