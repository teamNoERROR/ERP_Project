package kr.co.noerror.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.co.noerror.DTO.client_dto;
import kr.co.noerror.Service.client_service;

@Controller
@RequestMapping("/clients")
public class client_controller {

    @Autowired
    private client_service clientService;

    // 거래처 등록 폼 보여주기
    @GetMapping("/register")
    public String showRegisterPage() {
        return "clients"; // clients.html
    }

    // 거래처 등록 처리
    @PostMapping("/register")
    public String register(@ModelAttribute client_dto client) {
        clientService.insertClient(client);
        return "redirect:/clients/view"; // 등록 후 목록 페이지로 이동
    }

    // 거래처 목록 화면
    @GetMapping("/view")
    public String viewClients(Model model) {
        List<client_dto> clients = clientService.getClientList();
        model.addAttribute("clients", clients);
        return "client_list"; // client_list.html
    }

    // 거래처 검색 화면용
    @GetMapping("/search")
    public String searchClients(@RequestParam String keyword, Model model) {
        List<client_dto> searchResults = clientService.searchClient(keyword);
        model.addAttribute("clients", searchResults);
        return "client_list";
    }

    // JSON API: 전체 거래처 리스트
    @ResponseBody
    @GetMapping("/json")
    public List<client_dto> getAllClientsJson() {
        return clientService.getClientList();
    }

    // JSON API: 등록 처리
    @ResponseBody
    @PostMapping("/json")
    public String registerJson(@RequestBody client_dto client) {
        clientService.insertClient(client);
        return "등록 완료";
    }

    // JSON API: 수정
    @ResponseBody
    @PutMapping("/json")
    public String update(@RequestBody client_dto client) {
        clientService.updateClient(client);
        return "수정 완료";
    }

    // JSON API: 삭제
    @ResponseBody
    @DeleteMapping("/json/{id}")
    public String delete(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "삭제 완료";
    }

    // JSON API: 검색
    @ResponseBody
    @GetMapping("/json/search")
    public List<client_dto> searchJson(@RequestParam String keyword) {
        return clientService.searchClient(keyword);
    }
}
