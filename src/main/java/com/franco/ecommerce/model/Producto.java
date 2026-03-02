package com.franco.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long idProducto;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private Double precio;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VentaDetalle> ventaDetalles;

}
