package br.com.hbsis.linhas;

import br.com.hbsis.categoria.produto.Categoria;
import br.com.hbsis.categoria.produto.CategoriaService;
import br.com.hbsis.categoria.produto.ICategoriaRepository;
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
public class LinhaService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaService.class);

    private final ICategoriaRepository iCategoriaRepository;
    private final ILinhaRepository     iLinhaRepository;
    private final CategoriaService      categoriaService;

    public LinhaService(ICategoriaRepository iCategoriaRepository, ILinhaRepository iLinhaRepository, CategoriaService categoriaService) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.iLinhaRepository     = iLinhaRepository;
        this.categoriaService     = categoriaService;
    }

    public List<Linha> findAll() {
        return iLinhaRepository.findAll();
    }

    public LinhaDTO save(LinhaDTO linhaDTO) {

        this.validate(linhaDTO);

        LOGGER.info("Salvando Linha");
        LOGGER.debug("Linha: {}", linhaDTO);

        Linha linha = new Linha();

        String codigo = String.format("%1$10s", linhaDTO.getCodLinha());
        String upperCase = codigo.toUpperCase();
        codigo = codigo.replaceAll(" ", "0");

        linha.setNomeLinha(linhaDTO.getNomeLinha().toUpperCase());
        linha.setCodLinha(codigo);
        linha.setCategoria(iCategoriaRepository.findById(linhaDTO.getIdCategoria()).get());

        linha = this.iLinhaRepository.save(linha);

        return LinhaDTO.of(linha);
    }

    public List<Linha> readAll(MultipartFile file) throws Exception {

        InputStreamReader reader   = new InputStreamReader(file.getInputStream());
        CSVReader csvReader        = new CSVReaderBuilder(reader).withSkipLines(1).build();

        List<String[]> lineString  = csvReader.readAll();
        List<Linha> reading = new ArrayList<>();

        for (String[] linhas : lineString) {
            try {
                String[] bean = linhas[0].replaceAll("\"","").split(";");

                Linha linha = new Linha();
                Categoria categoria = new Categoria();

                String codigo = String.format("%1$10s", bean[0].replaceAll("[^a-zA-Z0-9]+", ""));
                codigo = codigo.replaceAll(" ", "0").toUpperCase();

                linha.setNomeLinha(bean[2]);
                linha.setCodLinha(codigo);
                linha.setCategoria(categoria);

                if(!(iLinhaRepository.existsByCodLinha(linha.getCodLinha()))){
                    save(LinhaDTO.of(linha));
                    reading.add(linha);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return reading;
    }

    public void validate(LinhaDTO linhaDTO) {
        LOGGER.info("VALIDANDO LINHA!!");

        if (linhaDTO == null) {
            throw new IllegalArgumentException("Linha não pode ser nulo.");
        }
        if (StringUtils.isEmpty(linhaDTO.getIdCategoria())){
            throw new IllegalArgumentException("ID Produto não pode ser nulo/vazio.");
        }
        if (StringUtils.isEmpty(linhaDTO.getNomeLinha())) {
            throw new IllegalArgumentException("Nome de Linha não poder ser nulo/vazio.");
        }
    }

    public LinhaDTO findById(Long idLinha) {
        Optional<Linha> linhaOptional = this.iLinhaRepository.findById(idLinha);

        if (linhaOptional.isPresent()) {

            return LinhaDTO.of(linhaOptional.get());

        }
        throw new IllegalArgumentException(String.format("ID %s não existe", idLinha));

    }

    public LinhaDTO update(LinhaDTO linhaDTO, Long idLinha){
        Optional<Linha> linhaOptional = this.iLinhaRepository.findById(idLinha);

        if (linhaOptional.isPresent()){
            Linha linhaExistente = linhaOptional.get();

            validate(linhaDTO);

            LOGGER.info("Atualizando a linha... id:{}", linhaExistente.getIdLinha());
            LOGGER.debug("Payload: {}", linhaDTO);
            LOGGER.debug("Linha existente: {}", linhaExistente);

            linhaExistente.setCategoria(iCategoriaRepository.findById(linhaDTO.getIdCategoria()).get());
            linhaExistente.setNomeLinha(linhaDTO.getNomeLinha().toUpperCase());

            linhaExistente = this.iLinhaRepository.save(linhaExistente);

            return linhaDTO.of(linhaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %S NAO EXISTE " ,  idLinha));
    }

    public  void delete(Long idLinha){
        LOGGER.info("Executando delete para linha de ID [{}]", idLinha);
        this.iLinhaRepository.deleteById(idLinha);
   }

    public void exportCSV(HttpServletResponse httpResponse) throws Exception {
        try {
            String fileName = "linhas.csv";
            httpResponse.setContentType("text/csv");
            httpResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; fileName=\"" + fileName + "\"");

            PrintWriter writer = httpResponse.getWriter();

            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                    .withSeparator(';')
                    .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            String headerCSV[] = {" ID LINHA "," CÓDIGO LINHA " ," ID CATEGORIA ", " NOME LINHA "};
            csvWriter.writeNext(headerCSV);

            for (Linha linha : iLinhaRepository.findAll()) {
                csvWriter.writeNext(new String[]{

                        linha.getIdLinha().toString(),
                        linha.getCodLinha().toUpperCase(),
                        linha.getCategoria().getId().toString(),
                        linha.getNomeLinha().toUpperCase()}
                );

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Linha findByCodLinha(String codLinha) {
        return this.iLinhaRepository.findByCodLinha(codLinha);
    }

    public boolean existsByCodLinha(String codLinha) {
    return  this.iLinhaRepository.existsByCodLinha(codLinha);
    }
}