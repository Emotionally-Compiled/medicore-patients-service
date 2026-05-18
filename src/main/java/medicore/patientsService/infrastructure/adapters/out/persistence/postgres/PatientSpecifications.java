package medicore.patientsService.infrastructure.adapters.out.persistence.postgres;

import jakarta.persistence.criteria.Predicate;
import medicore.patientsService.domain.models.FilterSearchPatient;
import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity.PatientEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PatientSpecifications {
    public static Specification<PatientEntity> build(FilterSearchPatient filter){
        if (filter == null){
            return  null;
        }
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isBlank()) {
                String likePattern = "%" + filter.name().toLowerCase() + "%";
                Predicate firstNameMatch = cb.like(cb.lower(root.get("firstName")), likePattern);
                Predicate lastNameMatch = cb.like(cb.lower(root.get("lastName")), likePattern);
                predicates.add(cb.or(firstNameMatch, lastNameMatch));
            }

            if(filter.identityDocument() != null && !filter.identityDocument().isBlank()){
                predicates.add(cb.equal(root.get("identityDocument"),filter.identityDocument()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
