package com.iurirates.foldermonitor.service;

import com.iurirates.foldermonitor.dto.FileImportDTO;
import com.iurirates.foldermonitor.dto.SalesmanDTO;
import com.iurirates.foldermonitor.model.Sale;
import com.iurirates.foldermonitor.model.SaleItem;
import com.iurirates.foldermonitor.model.Salesman;
import com.iurirates.foldermonitor.repository.SaleRepository;
import com.iurirates.foldermonitor.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.TreeMap;

@Service
public class SalesmanService {

    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private SaleRepository saleRepository;

    public void saveSalesman(FileImportDTO fileImportDTO){
        fileImportDTO.getSalesman().forEach( salesman -> salesmanRepository.save(convertSalesmanDtoToSalesman(salesman)));
    }

    public Integer getCountTotalSellers(){
        Collection<Salesman> salesman = (Collection<Salesman>) salesmanRepository.findAll();
        return salesman.size();
    }

    public void deleteAllSalesman(){
        salesmanRepository.deleteAll();
    }

    public String getWorstSeller(){
        TreeMap<String, BigDecimal> listSortedSalesman = new TreeMap<String, BigDecimal>();

        for (Salesman salesman : (Collection<Salesman>) salesmanRepository.findAll()) {
            listSortedSalesman.put(salesman.getName(), BigDecimal.ZERO);
        }

        for (Sale sale : (Collection<Sale>) saleRepository.findAll()){
            BigDecimal totalSale = sale.getSaleItems().stream()
                    .map(SaleItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (listSortedSalesman.containsKey(sale.getSalesmanName())) {
                listSortedSalesman.put(sale.getSalesmanName(), listSortedSalesman.get(sale.getSalesmanName()).add(totalSale));
            } else {
                listSortedSalesman.put(sale.getSalesmanName(), totalSale);
            }
        }

        return listSortedSalesman.firstKey();
    }

    private Salesman convertSalesmanDtoToSalesman(SalesmanDTO salesmanDTO){
        return Salesman
                .builder()
                .cpf(salesmanDTO.getCpf())
                .name(salesmanDTO.getName())
                .salary(salesmanDTO.getSalary())
                .build();
    }
}
