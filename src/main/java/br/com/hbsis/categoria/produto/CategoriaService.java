package br.com.hbsis.categoria.produto;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import com.google.common.net.HttpHeaders;
import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final ICategoriaRepository iCategoriaRepository;
    private final FornecedorService fornecedorService;
    private final IFornecedorRepository ifornecedorRepository;

    public CategoriaService(ICategoriaRepository iCategoriaRepository,
                            FornecedorService fornecedorService,
                            IFornecedorRepository ifornecedorRepository) {

        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
        this.ifornecedorRepository = ifornecedorRepository;
    }

    public List<Categoria> findAll() {
        return iCategoriaRepository.findAll();
    }

    public List<CategoriaDTO> readAll(MultipartFile file) throws Exception {

        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build();


        List<String[]> linhaString = csvReader.readAll();
        List<CategoriaDTO> reading = new ArrayList<>();

        for (String[] linha : linhaString) {
            try {

                String[] bean = linha[0].replaceAll("\"", "").split(";");
                CategoriaDTO categoriaDTO = new CategoriaDTO();
                FornecedorDTO fornecedorDTO = this.fornecedorService.findByCnpj(bean[3].replaceAll("[-/.]", ""));
                categoriaDTO.setCodCategoria(bean[0]);
                categoriaDTO.setNomeCategoria(bean[1]);
                categoriaDTO.setFornecedorId(fornecedorDTO.getIdFornecedor());

                if (!(iCategoriaRepository.existsCategoriaByCodCategoria(categoriaDTO.getCodCategoria())) ||
                        !(iCategoriaRepository.existsCategoriaByCodCategoria(codeContrutor(categoriaDTO.getCodCategoria(), categoriaDTO.getFornecedorId())))) {
                    save(categoriaDTO);
                    reading.add(categoriaDTO);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reading;
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
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(fornecedorService.findByIdFornecedor(categoriaDTO.getFornecedorId()));
        categoria.setCodCategoria("CAT" + categoria.getFornecedor().getCnpj().substring(10, 14) + cont);
        categoria.getFornecedor().getRazaoSocial();
        System.out.println(categoria.getCodCategoria());

        Categoria save = this.iCategoriaRepository.save(categoria);
        return CategoriaDTO.of(save);
    }

   public void exportCSV(HttpServletResponse httpServletResponse) {
        try {
            String filename = "categorias.csv";
            httpServletResponse.setContentType("text/csv");
            httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");

            PrintWriter writer = httpServletResponse.getWriter();
            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                    .withSeparator(';')
                    .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            String headerCSV[] = {"codigo_categoria", "nome_categoria", "cnpj", "razao_social"};
            csvWriter.writeNext(headerCSV);

            for (Categoria linha : iCategoriaRepository.findAll()) {
                String formatarCNPJ = linha.getFornecedor()
                        .getCnpj().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");

                csvWriter.writeNext(new String[]{
                        linha.getCodCategoria(),
                        linha.getNomeCategoria(),
                        formatarCNPJ
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String codeContrutor(String codigo, Long id) {

        String codigoCat = null;
        FornecedorDTO fornecedorDto = fornecedorService.findById(id);
        if (codigo.length() < 3) {
            codigo = String.format("%1$3s", codigo);
            codigo = codigo.replaceAll(" ", "0");
        }
        codigoCat = fornecedorDto.getCnpj().substring(10, 14) + codigo;

        return "CAT" + codigoCat;
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
            throw new IllegalArgumentException("Codigo  não deve ser maior que 3 dígitos");
        }
        if (StringUtils.isEmpty(categoriaDTO.getFornecedorId())) {
            throw new IllegalArgumentException("ID Fornecedor não deve ser nulo/vazio");
        }
    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaDTO findByCodCategoria(String codCategoria){
        Optional<Categoria> categoriaOpcional = (Optional<Categoria>) Optional.ofNullable(this.iCategoriaRepository.findByCodCategoria(codCategoria));

        if (categoriaOpcional.isPresent()) {
            return CategoriaDTO.of(categoriaOpcional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", codCategoria));
    }

    public Categoria findByIdCategoria(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
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

            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return categoriaDTO.of(categoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para categoria de ID> [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }

    public List<Categoria> listar() {
        List<Categoria> categoria;
        categoria = this.iCategoriaRepository.findAll();
        return categoria;
    }
}