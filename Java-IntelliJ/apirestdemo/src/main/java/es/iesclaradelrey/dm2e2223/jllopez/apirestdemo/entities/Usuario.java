package es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Data
@Entity

@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;
    @Column(nullable = false)
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
}
