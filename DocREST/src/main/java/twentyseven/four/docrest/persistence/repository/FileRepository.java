package twentyseven.four.docrest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import twentyseven.four.docrest.persistence.entity.FileEntity;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByName(String name);
}
