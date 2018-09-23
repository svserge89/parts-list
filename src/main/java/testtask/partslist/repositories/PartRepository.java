package testtask.partslist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import testtask.partslist.entities.Part;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {
    Page<Part> findAllByNeedIsTrue(Pageable pageable);
    Page<Part> findAllByNeedIsFalse(Pageable pageable);
    Page<Part> findAllByNameContainingIgnoreCase(String name,
                                                 Pageable pageable);
    Page<Part> findAllByNeedIsTrueAndNameContainingIgnoreCase(
            String name, Pageable pageable);
    Page<Part> findAllByNeedIsFalseAndNameContainingIgnoreCase(
            String name, Pageable pageable);
    List<Part> findAllByNeedIsTrue();
    boolean existsByName(String name);
}
