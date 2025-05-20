package kr.co.noerror.client;

import kr.co.noerror.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "client_list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("client", new Client());
        return "client_form";
    }

    @PostMapping
    public String saveClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        return "redirect:/clients";
    }
}