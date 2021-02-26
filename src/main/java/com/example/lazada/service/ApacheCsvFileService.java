/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.lazada.service;

import com.example.lazada.logging.Loggers;
import com.example.lazada.logging.LoggingUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author waichun
 *
 * reason using apache common for csv file read and write,
 * because it provide good performance, and having good community support
 * however with the limitation of this library, it will append empty line at end of the file
 */
public class ApacheCsvFileService {

    private final static String ALLOW_DATA = "MYSUA";

    private final static String ZERO = "0";

    /**
     * replace to your desire source file path
     */
    private final static String SOURCE = "src/main/resources/package_data.csv";

    /**
     * replace to your desire target file path
     */
    private final static String RESULT = "src/main/resources/package_data_result.csv";

    /**
     * process method
     */
    public void process() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        File sourceFile = new File(SOURCE);
        String sourceFilePath = sourceFile.getAbsolutePath();

        File resultFile = new File(RESULT);
        String resultFilePath = resultFile.getAbsolutePath();

        int total = 0, skipped = 0, processed = 0;

        // with try resource method, we dont need to manual close file stream
        // over here i open reader & writer stream in same transaction,
        // of course, we can separate reader & writer into two different transaction,
        // it will depends on the requirement to made decision
        // since provided file is small, we can open two stream in same transaction.
        try (Reader in = new FileReader(sourceFilePath);
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(resultFilePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withEscape('\"').withQuoteMode(QuoteMode.NONE))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                total++;
                String columnA = record.get(0);
                String columnB = record.get(1);
                String columnC = record.get(2);
                String columnD = record.get(3);
                String columnE = record.get(4);
                String columnF = record.get(5);
                String columnG = record.get(6);

                // check record can be process
                if (!canProcess(columnA, columnB, columnC, columnD, columnE, columnF, columnG)) {
                    LoggingUtil.info(Loggers.LAZADA_SERVICE, "{0} - column {1} skipped!",
                            this.getClass().getSimpleName(), columnA);
                    skipped++;
                    continue;
                }
                // save record to result file
                csvPrinter.printRecord(record);
                LoggingUtil.info(Loggers.LAZADA_SERVICE, "{0} - column {1} processed!",
                        this.getClass().getSimpleName(), columnA);
                processed++;
            }
            csvPrinter.flush();
        } catch (Exception ex) {
            LoggingUtil.error(Loggers.LAZADA_SERVICE, ex.toString());
        } finally {
            stopWatch.stop();
            LoggingUtil.info(Loggers.LAZADA_SERVICE,
                    "{0} - service end in {1}ms, total record:{2}, processed:{3}, skipped:{4}",
                    this.getClass().getSimpleName(), stopWatch.getTotalTimeMillis(),  total, processed, skipped);
        }
    }

    /** check data can be process
     * @param argh
     * @return
     */
    private boolean canProcess(String... argh) {
        // condition one, Column B OR C which is start with MYSUA character
        boolean conditionOne = argh[1].startsWith(ALLOW_DATA) || argh[2].startsWith(ALLOW_DATA);

        // condition two, column D,E,F,G must have value where it is not zero value or empty
        boolean conditionTwo = isNotBlankOrZero(argh[3]) && isNotBlankOrZero(argh[4]) && isNotBlankOrZero(argh[5]) && isNotBlankOrZero(argh[6]);
        return conditionOne && conditionTwo;
    }

    /**
     * if @param s is blank or equal to zero, return false
     * @param s
     * @return
     */
    private boolean isNotBlankOrZero(String s) {
        return !(StringUtils.isBlank(s) || ZERO.equals(s));
    }

}
