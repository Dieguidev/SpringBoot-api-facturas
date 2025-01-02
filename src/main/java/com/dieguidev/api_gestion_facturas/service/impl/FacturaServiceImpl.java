package com.dieguidev.api_gestion_facturas.service.impl;

import com.dieguidev.api_gestion_facturas.dao.FacturaDAO;
import com.dieguidev.api_gestion_facturas.security.jwt.JwtFilter;
import com.dieguidev.api_gestion_facturas.service.FacturaService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaDAO facturaDAO;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("dentro del metodo generar reporte");
        try {
            String fileName;
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addTableHeader(PdfPTable pdfPTable){
        log.info("Dentro del addTableHeader");
        Stream.of("Nombre", "Categoria", "Cantidad", "Precio", "Sub Total")
                .forEach(columnTitle ->{
                    PdfPCell pdfPCell = new PdfPCell();
                    pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    pdfPCell.setBorderWidth(2);
                    pdfPCell.setPhrase(new Phrase(columnTitle));
                    pdfPCell.setBackgroundColor(BaseColor.YELLOW);
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPTable.addCell(pdfPCell);
                });
    }

    private void addRows(PdfPTable pdfPTable, Map<String,Object> data) {
        log.info("Dentro de addRows");
        pdfPTable.addCell((String)data.get("name"));
        pdfPTable.addCell((String)data.get("category"));
        pdfPTable.addCell((String)data.get("quantity"));
        pdfPTable.addCell(Double.toString((Double) data.get("price")) );
        pdfPTable.addCell(Double.toString((Double) data.get("total")) );
    }
}
