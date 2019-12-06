package br.com.hbsis.categoria.produto;

import br.com.hbsis.fornecedor.IFornecedorRepository;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
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

    private static final Logger  LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    private final ICategoriaRepository iCategoriaRepository;
    private final IFornecedorRepository iFornecedorRepository;

    public CategoriaService(ICategoriaRepository iCategoriaRepository,
                            IFornecedorRepository iFornecedorRepository) {

        this.iCategoriaRepository = iCategoriaRepository;
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public List<Categoria
                > findAll() {
        return iCategoriaRepository.findAll();
    }

    public void exportCSV(HttpServletResponse response) throws Exception {
        try {
            String nomearquivo = "categorias.csv";
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + nomearquivo + "\"");

            PrintWriter writer = response.getWriter();

            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                    .withSeparator(';')
                    .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            String headerCSV[] = {"id_categoria_produto", "codigo_categoria_produto", "nome_categoria_produto", "id_fornecedor"};
            csvWriter.writeNext(headerCSV);

            for (Categoria linha : iCategoriaRepository.findAll()) {
                csvWriter.writeNext(new String[]{linha.getId().toString(), linha.getCodCategoria(), linha.getNomeCategoria(), linha.getFornecedor().getId().toString()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Categoria> readAll(MultipartFile file) throws Exception {

        InputStreamReader reader = new InputStreamReader(file.getInputStream());

        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();


        List<String[]> linhaString = csvReader.readAll();

        List<Categoria> reading = new ArrayList<>();

        for (String[] linha : linhaString) {
            try {

                String[] bean = linha[0].replaceAll("\"", "").split(";");

                Categoria categoria = new Categoria();

                categoria.setCodCategoria(bean[1]);
                categoria.setNomeCategoria(bean[2]);
                categoria.setFornecedor(iFornecedorRepository.findById(Long.parseLong(bean[3])).get());

                reading.add(categoria);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iCategoriaRepository.saveAll(reading);
    }

    public List<Categoria> saveAll(List<Categoria> categorias) throws Exception {

        return iCategoriaRepository.saveAll(categorias);
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {

        this.validate(categoriaDTO);

        LOGGER.info("Salvando Categoria Produto");
        LOGGER.debug("Categoria Produto: {}", categoriaDTO);

        Categoria categoria = new Categoria();

        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setCodCategoria(categoriaDTO.getCodCategoria());
        categoria.setFornecedor(iFornecedorRepository.findById(categoriaDTO.getFornecedorId()).get());

        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);

    }

    private void validate(CategoriaDTO categoriaDTO) {

        LOGGER.info("Validando Categoria Produto");

        if (categoriaDTO == null) {
            throw new IllegalArgumentException("Categoria Produto não deve ser nulo");
        }
        if (StringUtils.isEmpty(categoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(categoriaDTO.getCodCategoria())) {
            throw new IllegalArgumentException("Codigo não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(categoriaDTO.getFornecedorId().toString())) {
            throw new IllegalArgumentException("ID Fornecedor não deve ser nulo/vazio");

        }
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para categoria produto de ID> [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }

    public  CategoriaDTO findById(Long id) {
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


            categoriaExistente.setCodCategoria(categoriaDTO.getCodCategoria());
            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());
            categoriaExistente.setFornecedor(iFornecedorRepository.findById(categoriaDTO.getFornecedorId()).get());

            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return categoriaDTO.of(categoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }




}
