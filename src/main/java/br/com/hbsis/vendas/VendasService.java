package br.com.hbsis.vendas;

import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VendasService {
        private final Logger LOGGER = LoggerFactory.getLogger(br.com.hbsis.vendas.VendasService.class);

        private final IVendasRepository     iVendasRepository;
        private final FornecedorService     fornecedorService;
        private final IFornecedorRepository ifornecedorRepositoy;

    public VendasService(IVendasRepository iVendasRepository,
                         FornecedorService fornecedorService,
                         IFornecedorRepository ifornecedorRepositoy) {

        this.iVendasRepository = iVendasRepository;
        this.fornecedorService = fornecedorService;
        this.ifornecedorRepositoy = ifornecedorRepositoy;
    }

    public VendasDTO save(VendasDTO vendasDTO){

        this.validate(vendasDTO);

        LOGGER.info("Salvando Periodo Vendas");
        LOGGER.debug("Payload [{}]", vendasDTO);

        Vendas vendas = new Vendas();

        vendas.setInicioVendas(vendasDTO.getInicioVendas());
        vendas.setFimVendas(vendasDTO.getFimVendas());
        vendas.setRetiradaPedido(vendasDTO.getRetiradaPedido());
        vendas.setFornecedor(fornecedorService.findByIdFornecedor(vendasDTO.getFornecedorId()));
        vendas.setDescricao(vendasDTO.getDescricao().toUpperCase());

        vendas = this.iVendasRepository.save(vendas);

        return VendasDTO.of(vendas);
    }

    public VendasDTO update(VendasDTO vendasDTO, Long id){

        Optional<Vendas> VendaOptional = this.iVendasRepository.findById(id);

        if(VendaOptional.isPresent()){
            Vendas vendasExistente = VendaOptional.get();

            LOGGER.info("Atualizando Periodo Venda do Fornecedor...[{}]", VendaOptional.get().getFornecedor().getIdFornecedor());
            LOGGER.debug("Payload... [{}]", vendasDTO);
            LOGGER.debug("Periodo existente... [{}]", vendasExistente);

            vendasExistente.setId(vendasDTO.getId());
            vendasExistente.setFornecedor(fornecedorService.findByIdFornecedor(VendaOptional.get().getFornecedor().getIdFornecedor()));
            vendasExistente.setInicioVendas(vendasDTO.getInicioVendas());
            vendasExistente.setFimVendas(vendasDTO.getFimVendas());
            vendasExistente.setRetiradaPedido(vendasDTO.getRetiradaPedido());
            vendasExistente.setDescricao(vendasDTO.getDescricao().toLowerCase());

            this.iVendasRepository.save(vendasExistente);
            return VendasDTO.of(vendasExistente);
        }
        throw new IllegalArgumentException(String.format("Não foi possível atualizar o período de venda do fornecedor {}.", id));
    }

    public void delete(Long id){


        LOGGER.info("Excluindo periodo de vendas de ID: [{}]", id);

        this.iVendasRepository.deleteById(id);
    }

    public void validate(VendasDTO vendasDTO){
        LOGGER.info("Validando Venda");

        if (vendasDTO == null) {
            throw new IllegalArgumentException("Vendas não pode ser nulo.");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getInicioVendas()))){
            throw  new  IllegalArgumentException("Inicio vendas não pode ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getFimVendas()))){
            throw new  IllegalArgumentException("Fim vendas não pode ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getRetiradaPedido()))){
            throw  new IllegalArgumentException("Retirada pedido não deve ser nulo");
        }
        if (StringUtils.isEmpty(vendasDTO.getDescricao())){
            throw  new  IllegalArgumentException("Descrição não deve ser nula");
        }
        if (StringUtils.isEmpty(String.valueOf(vendasDTO.getFornecedorId()))){
            throw new  IllegalArgumentException("Fornecedor Id não deve ser nulo");
        }
        if (vendasDTO.getInicioVendas().isBefore(LocalDate.now())
                || vendasDTO.getFimVendas().isBefore(LocalDate.now()) || vendasDTO.getFimVendas().isBefore(LocalDate.now())) {
            throw new  IllegalArgumentException("Inicio Vendas não podem ser inferiores ao dia de HOJE");
        }
       /* if (iVendasRepository.existVendasHoje(vendasDTO.getInicioVendas(), vendasDTO.getFornecedorId()) >= 1){
            throw new  IllegalArgumentException("Fornecedor não pode ter duas vendas ao mesmo tempo");
        }*/
        if (vendasDTO.getFimVendas().isBefore(vendasDTO.getInicioVendas()))  {
            throw  new  IllegalArgumentException("Fim vendas não pode ser inferior a data de inicio vendas");
        }

    }

    public VendasDTO findById(Long id){
        Optional<Vendas> vendaOptional = this.iVendasRepository.findById(id);

        if(vendaOptional.isPresent()){
            return VendasDTO.of(vendaOptional.get());
        }
        throw new  IllegalArgumentException(String.format("Periodo de vendas de ID {} não encontrado.", id));
    }
}