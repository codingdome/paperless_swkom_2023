package twentyseven.four.docservices.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import twentyseven.four.docservices.persistence.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findByName(String name);
}
