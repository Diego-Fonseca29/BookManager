package org.example.BookManager.model;

import org.openxava.annotations.*;

@View(members = "titulo, autor, totalPrestamos")
public class LibroPopular {

    private String titulo;
    private String autor;
    private Integer totalPrestamos;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Integer getTotalPrestamos() { return totalPrestamos; }
    public void setTotalPrestamos(Integer totalPrestamos) { this.totalPrestamos = totalPrestamos; }
}