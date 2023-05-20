package com.example.example.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Table(name = "order_table", uniqueConstraints=
@UniqueConstraint(columnNames={"external_id", "vendor_id"}))
@Entity
@ToString
@Accessors(chain = true)
public class OrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "status_order")
    @Enumerated(value = EnumType.STRING)
    private StatusOrder statusOrder;

    @Embedded
    private DateHistory dateHistory = new DateHistory();


    private BigDecimal sum;

    @Embedded
    private Person person;


    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(name = "postamat_id")
    private UUID postamatId;

    @Type(JsonType.class)
    @Column(name = "categories", columnDefinition = "jsonb")
    private List<String> categories;


    @Embedded
    private OrderMeta meta;

    @Column(name = "ref")
    private URL ref;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
