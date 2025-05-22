package kr.co.noerror.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.noerror.DTO.client_dto;

@Mapper
public interface client_mapper {
    void insertClient(client_dto client);
    List<client_dto> getClientList();
    void updateClient(client_dto client);
    void deleteClient(Long id);
    List<client_dto> searchClient(String keyword);
}
