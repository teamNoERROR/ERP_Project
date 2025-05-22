package kr.co.noerror.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.noerror.Mapper.client_mapper;
import kr.co.noerror.DTO.client_dto;

@Service
public class client_service {

    @Autowired
    private client_mapper clientMapper;

    public void insertClient(client_dto client) {
        clientMapper.insertClient(client);
    }

    public List<client_dto> getClientList() {
        return clientMapper.getClientList();
    }

    public void updateClient(client_dto client) {
        clientMapper.updateClient(client);
    }

    public void deleteClient(Long id) {
        clientMapper.deleteClient(id);
    }

    public List<client_dto> searchClient(String keyword) {
        return clientMapper.searchClient(keyword);
    }
}
