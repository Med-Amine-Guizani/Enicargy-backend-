package org.example.backendenicargy.Services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @Service
    public class ConsommationScrapingService {

        public Map<String, List<Integer>> getConsommationDataFromHtml() {
            Map<String, List<Integer>> consommationData = new HashMap<>();
            List<Integer> electricite = new ArrayList<>();
            List<Integer> eau = new ArrayList<>();

            try {
                File input = new File("src/main/resources/statics/donnees_consommation.html");
                Document doc = Jsoup.parse(input, "UTF-8");

                Element table = doc.select("table").first();
                Elements rows = table.select("tr:gt(0)");

                for (Element row : rows) {
                    Elements cells = row.select("td");
                    if (cells.size() == 4) {
                        try {
                            electricite.add(Integer.parseInt(cells.get(2).text()));
                            eau.add(Integer.parseInt(cells.get(3).text()));
                        } catch (NumberFormatException e) {
                            System.err.println("Erreur de conversion de nombre : " + e.getMessage());
                        }
                    }
                }

                consommationData.put("electricite", electricite);
                consommationData.put("eau", eau);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return consommationData;
        }
    }













