package com.iurirates.foldermonitor.service;

import com.iurirates.foldermonitor.dto.ClientDTO;
import com.iurirates.foldermonitor.dto.FileImportDTO;
import com.iurirates.foldermonitor.model.Client;
import com.iurirates.foldermonitor.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void saveClient(FileImportDTO fileImportDTO){
        fileImportDTO.getClients().forEach( client -> clientRepository.save(convertClientDtoToClient(client)));
    }

    public Integer getCountTotalClients(){
        Collection<Client> clients = (Collection<Client>) clientRepository.findAll();
        return clients.size();
    }

    public void deleteAllClients(){
        clientRepository.deleteAll();
    }

    private Client convertClientDtoToClient(ClientDTO clientDTO){
        return Client
                .builder()
                .cnpj(clientDTO.getCnpj())
                .name(clientDTO.getName())
                .businessArea(clientDTO.getBusinessArea())
                .build();
    }
}
