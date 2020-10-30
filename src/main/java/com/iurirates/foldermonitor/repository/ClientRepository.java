package com.iurirates.foldermonitor.repository;

import com.iurirates.foldermonitor.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
