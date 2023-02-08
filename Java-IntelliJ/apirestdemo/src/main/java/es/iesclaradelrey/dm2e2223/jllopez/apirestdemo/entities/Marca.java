package es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;
    @Column(nullable = false)
    private String nombre;
}
