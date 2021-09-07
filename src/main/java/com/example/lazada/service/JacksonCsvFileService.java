package com.example.lazada.service;

import com.example.lazada.logging.Loggers;
import com.example.lazada.logging.LoggingUtil;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;

public class JacksonCsvFileService {

    private final static String SOURCE = "src/main/resources/package_data.csv";

    public void process() {

        File sourceFile = new File(SOURCE);
        String sourceFilePath = sourceFile.getAbsolutePath();

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvPojo.class);

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(sourceFilePath))) {

            MappingIterator<CsvPojo> it = mapper
                    .readerFor(CsvPojo.class)
                    .with(schema)
                    .readValues(reader);

            while (it.hasNextValue()) {
                CsvPojo p = it.nextValue();
                LoggingUtil.info(Loggers.LAZADA_SERVICE, p.toString());
            }
        }catch (Exception e){
            LoggingUtil.error(Loggers.LAZADA_SERVICE, e.toString());
        }
    }
}
