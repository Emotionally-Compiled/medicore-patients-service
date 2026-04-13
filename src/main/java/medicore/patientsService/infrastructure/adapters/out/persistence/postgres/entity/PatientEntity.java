package medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "patients")
@Table(name = "patients", indexes = {
        @Index(name = "idx_email", columnList = "email", unique = true),
        @Index(name = "idx_identity_document", columnList = "identityDocument", unique = true),
        @Index(name = "idx_lastname_firstname", columnList = "lastName,firstName")
})
@NoArgsConstructor
public class PatientEntity {

    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "identity_document", nullable = false, unique = true)
    private String identityDocument;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true )
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    @JdbcTypeCode(SqlTypes.DATE)
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true, nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime updatedAt;



}
