package Helpers


import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

public class CsvParser {
    public static ArrayList<Map<String, String>> csvToCollection (File file) throws IOException {

        ArrayList<Map<String, String>> response = new ArrayList<>() ;


        InputStreamReader input = new InputStreamReader(new FileInputStream(file));
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(input);
        for (CSVRecord record : csvParser) {
            response.add(record.toMap());

        }
        return response;
    }

}

