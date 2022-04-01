package main.java.com.koblan.cryptoCurrencyTool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CSVController {

    @Autowired
    LastPriceService priceService;

    @RequestMapping(path = "/cryptocurrencies/csv")
    public void generateCSVReportFile(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"cryproReport.csv\"");
        priceService.generateCsvReport(servletResponse.getWriter());
    }

}
