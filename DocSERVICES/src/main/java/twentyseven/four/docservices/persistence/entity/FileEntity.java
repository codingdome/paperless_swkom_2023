package twentyseven.four.docservices.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String contentType;
    private double size;

    @Column(columnDefinition = "TEXT")
    private String ocrExtractedText;
    private Date uploadTime;
    private Date changedTime;

}
