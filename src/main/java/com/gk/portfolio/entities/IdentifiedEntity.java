package com.gk.portfolio.entities;

import javax.persistence.*;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class IdentifiedEntity<T> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    T id;


    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
