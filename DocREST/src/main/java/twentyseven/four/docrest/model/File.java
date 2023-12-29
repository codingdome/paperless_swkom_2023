package twentyseven.four.docrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String name;
    private String contentType;
    private double size;
    private String ocrExtractedText;
    private Date uploadTime;
    private Date changedTime;
}
