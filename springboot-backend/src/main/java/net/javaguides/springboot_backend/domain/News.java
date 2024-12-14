package net.javaguides.springboot_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sitenews")
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int post_id;
    private Timestamp create_time;
    private String title;
    private String description;
}

