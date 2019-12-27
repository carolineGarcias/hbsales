package br.com.hbsis.CSV;

import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportCsv {

    public void exportarCSV(HttpServletResponse httpServletResponse, String filename, String headerCSV[]) throws IOException {

        httpServletResponse.setContentType("text/csv");
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        PrintWriter writer = httpServletResponse.getWriter();
        ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        csvWriter.writeNext(headerCSV);
    }
}