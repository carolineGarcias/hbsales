package br.com.hbsis.vendas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class VendasRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendasRest.class);
    private final VendasService vendasService;

    @Autowired
    public VendasRest(VendasService vendasService) {
        this.vendasService = vendasService;
    }

    @PostMapping
    public VendasDTO save(@RequestBody VendasDTO vendasDTO) {

        return vendasService.save(vendasDTO);
    }

    @PutMapping("/{id}")
    public VendasDTO update(@PathVariable("id") Long id, @RequestBody VendasDTO vendasDTO) {
        return this.vendasService.update(vendasDTO, id);
    }

    @GetMapping("/{id}")
    public VendasDTO findById(@PathVariable("id") Long id) {
        LOGGER.info("Procurando periodo de vendas de ID: [{}]", id);

        return this.vendasService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.vendasService.delete(id);
    }
}