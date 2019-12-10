package br.com.hbsis.linhas;

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
    private final ILinhaRepository iLinhaRepository;


    public LinhaService(ICategoriaRepository iCategoriaRepository, ILinhaRepository iLinhaRepository) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.iLinhaRepository     = iLinhaRepository;

    }

    public List<Linha> findAll() {
        return iLinhaRepository.findAll();
    }

    public List<Linha> saveAll(List<Linha> linha) {
        return iLinhaRepository.saveAll(linha);
    }

    public LinhaDTO save(LinhaDTO linhaDTO) {

        LOGGER.debug("Linha: {} ", linhaDTO);

        this.validate(linhaDTO);

        LOGGER.info("Salvando Linha!!!");
        LOGGER.debug("Linha: {}", linhaDTO);

        Linha linha = new Linha();
        linha.setNomeLinha(linhaDTO.getNomeLinha());
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
                linha.setNomeLinha(bean[2]);
                linha.setIdLinha(Long.parseLong(bean[0]));
                linha.setCategoria(iCategoriaRepository.findById(Long.parseLong(bean[1])).get());

                reading.add(linha);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return iLinhaRepository.saveAll(reading);
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

    public LinhaDTO findById(Long id) {
        Optional<Linha> lineOptional = this.iLinhaRepository.findById(id);

        if (lineOptional.isPresent()) {
        return LinhaDTO.of(lineOptional.get());
    }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
        }

    public LinhaDTO update(LinhaDTO linhaDTO, Long id){
        Optional<Linha> linhaOptional = this.iLinhaRepository.findById(id);

        if (linhaOptional.isPresent()){
            Linha linhaExistente = linhaOptional.get();

            validate(linhaDTO);

            LOGGER.info("Atualizando a linha categoria... id:{}", linhaExistente.getIdLinha());
            LOGGER.debug("Payload: {}", linhaDTO);
            LOGGER.debug("Linha categoria existente: {}", linhaExistente);

            linhaExistente.setCategoria(iCategoriaRepository.findById(linhaDTO.getIdCategoria()).get());
            linhaExistente.setNomeLinha(linhaDTO.getNomeLinha());

            linhaExistente = this.iLinhaRepository.save(linhaExistente);

            return linhaDTO.of(linhaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %S NAO EXISTE " ,  id));
    }

    public  void delete(Long id){
        LOGGER.info("Executando delete para LINHA de ID [{}]", id);
        this.iLinhaRepository.deleteById(id);
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

            String headerCSV[] = {"id_linha", "id_categoria", "nome_linha"};
            csvWriter.writeNext(headerCSV);

            for (Linha linha : iLinhaRepository.findAll()) {
                csvWriter.writeNext(new String[]{
                        linha.getIdLinha().toString(),
                        linha.getCategoria().getId().toString(),
                        linha.getNomeLinha()});

            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
    }

}