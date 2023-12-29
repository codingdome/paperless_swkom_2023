package twentyseven.four.docservices.service.mapper;

import org.springframework.stereotype.Component;
import twentyseven.four.docservices.model.File;
import twentyseven.four.docservices.persistence.entity.FileEntity;

@Component
public class FileMapper extends AbstractMapper<FileEntity, File> {

    @Override
    public File fromEntity(FileEntity entity) {
        if (entity == null) {
            return null;
        }

        return File.builder()
                .name(entity.getName())
                .contentType(entity.getContentType())
                .size(entity.getSize())
                .ocrExtractedText(entity.getOcrExtractedText())
                .uploadTime(entity.getUploadTime())
                .changedTime(entity.getChangedTime())
                .build();
    }

    @Override
    public FileEntity toEntity(File dto) {
        if (dto == null) {
            return null;
        }

        return FileEntity.builder()
                .name(dto.getName())
                .contentType(dto.getContentType())
                .size(dto.getSize())
                .ocrExtractedText(dto.getOcrExtractedText())
                .uploadTime(dto.getUploadTime())
                .changedTime(dto.getChangedTime())
                .build();
    }
}