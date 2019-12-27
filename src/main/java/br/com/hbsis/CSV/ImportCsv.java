package br.com.hbsis.CSV;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ImportCsv {

    public List<String[]> importCsv(MultipartFile file) throws IOException {

        InputStreamReader reader = new InputStreamReader(file.getInputStream());

        CSVReader cvsReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .build();

        List<String[]> readAll = cvsReader.readAll();

        return readAll;
    }
}
