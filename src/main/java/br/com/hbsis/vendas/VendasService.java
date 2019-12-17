package br.com.hbsis.vendas;

import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class VendasService {
        private final Logger LOGGER = LoggerFactory.getLogger(br.com.hbsis.vendas.VendasService.class);

        private final IVendasRepository     iVendasRepository;
        private final IFornecedorRepository ifornecedorRepository;

    public VendasService(IVendasRepository iVendasRepository, IFornecedorRepository ifornecedorRepository) {
        this.iVendasRepository     = iVendasRepository;
        this.ifornecedorRepository = ifornecedorRepository;
    }

    public VendasDTO save(VendasDTO vendasDTO){

        LOGGER.info("Salvando Periodo Vendas");
        LOGGER.debug("Payload [{}]", vendasDTO);

        this.validate(vendasDTO);

        Vendas vendas = new Vendas();

        vendas.setFornecedor(ifornecedorRepository.findById(vendasDTO.getFornecedorId()).get());
        vendas.setInicioVendas(vendasDTO.getInicioVendas());
        vendas.setFimVendas(vendasDTO.getFimVendas());
        vendas.setRetiradaPedido(vendasDTO.getRetiradaPedido());
        vendas.setDescricao(vendasDTO.getDescricao().toUpperCase());

        this.iVendasRepository.save(vendas);

        return VendasDTO.of(vendas);
    }

    public void validate(VendasDTO periodoVendaDTO){
        LOGGER.info("Validando Periodo Venda");

        if(periodoVendaDTO == null){
            throw new IllegalArgumentException("Periodo Venda não pode ser nulo.");
        }
    }

    public VendasDTO update(VendasDTO vendasDTO, Long id){

        Optional<Vendas> VendaOptional = this.iVendasRepository.findById(id);

        if(VendaOptional.isPresent()){
            Vendas vendasExistente = VendaOptional.get();

            LOGGER.info("Atualizando Periodo Venda do Fornecedor...[{}]", VendaOptional.get().getFornecedor().getIdFornecedor());
            LOGGER.debug("Payload... [{}]", vendasDTO);
            LOGGER.debug("Periodo existente... [{}]", vendasExistente);

            vendasExistente.setId(vendasDTO.getId());
            vendasExistente.setFornecedor(ifornecedorRepository.findById(VendaOptional.get().getFornecedor().getIdFornecedor()).get());
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

  public boolean vendaAtivo(FornecedorDTO fornecedorDTO) {
      LocalDate diaHoje = LocalDate.now();

      Optional<Vendas> periodoVendaOptional = this.iVendasRepository.findByIdVendasFornecedor(fornecedorDTO.getIdFornecedor());

      if(periodoVendaOptional.isPresent()){
          Vendas vendas = periodoVendaOptional.get();

          if(diaHoje.compareTo(vendas.getInicioVendas()) >= 1  && diaHoje.compareTo(vendas.getFimVendas()) <= 0){
              return true;
          }
          else{
              return false;
          }
      }
      throw new IllegalArgumentException(String.format("Não foi possível verificar o período de compras."));
  }

    public VendasDTO findById(Long id){
        Optional<Vendas> vendaOptional = this.iVendasRepository.findById(id);

        if(vendaOptional.isPresent()){
            return VendasDTO.of(vendaOptional.get());
        }
        throw new  IllegalArgumentException(String.format("Periodo de vendas de ID {} não encontrado.", id));
    }
}