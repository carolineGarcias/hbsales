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

    public List<Categoria> readAll(MultipartFile file) throws Exception {

        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build();

        List<String[]> linhaString = csvReader.readAll();
        List<Categoria> reading = new ArrayList<>();

        for (String[] linha : linhaString) {
            try {

                String[] bean = linha[0].replaceAll("\"", "").split(";");

                Categoria categoria = new Categoria();
                Fornecedor fornecedor = new Fornecedor();
                FornecedorDTO fornecedorDTO = new FornecedorDTO();

                Optional<Categoria> optionalCategoria = this.iCategoriaRepository.findByCodCategoria(bean[0]);

                if (!optionalCategoria.isPresent()) {
                    categoria.setNomeCategoria(bean[1]);
                    categoria.setCodCategoria(bean[0]);
                    Optional<Fornecedor> optionalFornecedor = Optional.ofNullable(this.ifornecedorRepository.findByCnpj(bean[3]));

                    if (optionalFornecedor.isPresent()) {
                        fornecedorDTO = fornecedorService.findByCnpj(bean[3]);
                        bean[3] = String.valueOf(fornecedorDTO.getIdFornecedor());
                        fornecedor.setId(Long.parseLong(bean[3]));
                        categoria.setFornecedor(fornecedor);
                        reading.add(categoria);

                        iCategoriaRepository.saveAll(reading);
                    } else {
                        throw new IllegalArgumentException("Fornecedor não encontrado!!!!!!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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

            String headerCSV[] = {"id", "CÓDIGO CATEGORIA", "NOME", "CNPJ", "RAZÃO SOCIAL"};
            csvWriter.writeNext(headerCSV);

            for (Categoria linha : iCategoriaRepository.findAll()) {
                String formatarCNPJ = linha.getFornecedor()
                        .getCnpj().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");

                csvWriter.writeNext(new String[]{
                        String.valueOf(linha.getId()),
                        linha.getCodCategoria(),
                        linha.getNomeCategoria(),
                        formatarCNPJ,
                        linha.getFornecedor().getRazaoSocial()
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
            throw new IllegalArgumentException("Código  não deve ser maior que 3 dígitos");
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