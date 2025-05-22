package kr.co.noerror.DAO;


import java.util.List;
import kr.co.noerror.DTO.client_dto;

public interface client_dao {
    void insertClient(client_dto client);
    List<client_dto> getClientList();
    void updateClient(client_dto client);
    void deleteClient(Long id);
    List<client_dto> searchClient(String keyword);
}
