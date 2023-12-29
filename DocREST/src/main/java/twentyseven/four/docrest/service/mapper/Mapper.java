package twentyseven.four.docrest.service.mapper;

public interface Mapper<E, T> {

    T fromEntity(E entity);

    E toEntity(T dto);

}
