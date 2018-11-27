package com.risk.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter  extends Formatter {


    @Override
    public String format(LogRecord record) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatMessage(record)).append(System.getProperty("line.separator"));
        if (null != record.getThrown()) {
            stringBuilder.append("Throwable occurred: "); //$NON-NLS-1$
            Throwable t = record.getThrown();
            PrintWriter writer = null;
            try {
                StringWriter sw = new StringWriter();
                writer = new PrintWriter(sw);
                t.printStackTrace(writer);
                stringBuilder.append(sw.toString());
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
