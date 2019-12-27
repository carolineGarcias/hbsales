package br.com.hbsis.categoria.produto;

import br.com.hbsis.CSV.ExportCsv;
import br.com.hbsis.CSV.ImportCsv;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final FornecedorService fornecedorService;
    private final ICategoriaRepository iCategoriaRepository;

    public CategoriaService(FornecedorService fornecedorService, ICategoriaRepository iCategoriaRepository) {
        this.fornecedorService = fornecedorService;
        this.iCategoriaRepository = iCategoriaRepository;
    }

    public List<Categoria> findAll() {
        return iCategoriaRepository.findAll();
    }

    public List<CategoriaDTO> importarCategoria(MultipartFile file) throws Exception {
        ImportCsv importarCSV = new ImportCsv();

        List<CategoriaDTO> reading = new ArrayList<>();

        for (String[] linha : importarCSV.importCsv(file)) {
            try {

                String[] bean = linha[0].replaceAll("\"", "").split(";");
                CategoriaDTO categoriaDTO = new CategoriaDTO();
                Fornecedor fornecedor = this.fornecedorService.findByCnpj(bean[3].replaceAll("[-/.]", ""));

                if (!(existsCategoriaByCodCategoria(categoriaDTO.getCodCategoria().toUpperCase())) ||
                        !(existsCategoriaByCodCategoria(codeContrutor(categoriaDTO.getCodCategoria()
                                .toUpperCase(), categoriaDTO.getFornecedorId())))) {
                    save(categoriaDTO);
                    reading.add(categoriaDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reading;
    }

   /* public void exportCategoriaCsv(HttpServletResponse httpServletResponse ) throws IOException {

        ExportCsv exportCsv = new ExportCsv();
        exportCsv.exportarCSV( httpServletResponse, "categorias.csv",  "codigo categoria", "nome categoria", "cnpj", "razão social");

        for (Categoria categoria : findAll()) {
            String cnpjFormatado = categoria.getFornecedor().getCnpj().replaceAll
                    ("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");

            exportCsv.exportarCSV(httpServletResponse, "categorias.csv",
                    categoria.getCodCategoria(),
                    categoria.getNomeCategoria(),
                    cnpjFormatado,
                    categoria.getFornecedor().getRazaoSocial());
        }
    }*/

    public String codeContrutor(String codigo, Long id) {
        String codigoCAT;
        FornecedorDTO fornecedorDTO = fornecedorService.findById(id);
        if (codigo.length() < 3) {
            codigo = String.format("%1$3s", codigo).toUpperCase();
            codigo = codigo.replaceAll(" ", "0");
        }
        codigoCAT = fornecedorDTO.getCnpj().substring(10, 14) + codigo;

        return "CAT" + codigoCAT;
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        this.validate(categoriaDTO);

        LOGGER.info("Salvando Categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();
        String cont = String.valueOf(categoriaDTO.getCodCategoria());

        for (; cont.length() < 3; ) {
            cont = "0" + cont;
        }

        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria().toUpperCase());
        categoria.setFornecedor(fornecedorService.findByIdFornecedor(categoriaDTO.getFornecedorId()));
        categoria.setCodCategoria(codeContrutor(categoriaDTO.getCodCategoria(), categoria.getFornecedor().getIdFornecedor()));
        categoria.getFornecedor().getRazaoSocial().toUpperCase();

        categoria = this.iCategoriaRepository.save(categoria);
        return CategoriaDTO.of(categoria);
    }

    private void validate(CategoriaDTO categoriaDTO) {

        LOGGER.info("Validando Categoria");

        if (categoriaDTO == null) {
            throw new IllegalArgumentException("Categoria não deve ser nulo");
        }
        if (StringUtils.isEmpty(categoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(categoriaDTO.getCodCategoria())) {
            throw new IllegalArgumentException("Codigo não deve ser nulo/vazio");
        }
        if (categoriaDTO.getCodCategoria().length() > 3) {
            throw new IllegalArgumentException("Código  não deve ser maior que 3 dígitos");
        }
        if (StringUtils.isEmpty(categoriaDTO.getFornecedorId())) {
            throw new IllegalArgumentException("ID Fornecedor não deve ser nulo/vazio");
        }
    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {

        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando o fornecedor... id:{}", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Fornecedor Existente: {}", categoriaExistente);

            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());
            categoriaExistente.setFornecedor(fornecedorService.fromDto(fornecedorService.findById(categoriaDTO.getFornecedorId()), new Fornecedor()));
            categoriaExistente.setCodCategoria(codeContrutor(categoriaDTO.getCodCategoria(), categoriaDTO.getFornecedorId()));

            return categoriaDTO.of(categoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para categoria de ID> [{}]", id);

        delete(id);
    }

    public List<Categoria> listar() {
        List<Categoria> categoria;
        categoria = this.iCategoriaRepository.findAll();

        return categoria;
    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Categoria findByIdCategoria(Long idCategoria) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(idCategoria);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", idCategoria));
    }

    public boolean existsCategoriaByCodCategoria(String codCategoria) {
        return existsCategoriaByCodCategoria(codCategoria);
    }

    public void exportCategoriaCsv(HttpServletResponse httpResponse) {
    }
}