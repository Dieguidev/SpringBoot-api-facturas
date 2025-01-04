package com.dieguidev.api_gestion_facturas.service.impl;

import com.dieguidev.api_gestion_facturas.constantes.FacturaConstantes;
import com.dieguidev.api_gestion_facturas.dao.FacturaDAO;
import com.dieguidev.api_gestion_facturas.pojo.Factura;
import com.dieguidev.api_gestion_facturas.security.jwt.JwtFilter;
import com.dieguidev.api_gestion_facturas.service.FacturaService;
import com.dieguidev.api_gestion_facturas.util.FacturaUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
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
            if(validateRequestMap(requestMap)){
                if(requestMap.containsKey("isGenerate") && !(Boolean)requestMap.get("isGenerate")){
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = FacturaUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertInvoice(requestMap);
                }

                String data = "Nombre : " + requestMap.get("name") + "\nNumero de contacto : " + requestMap.get("contactNumber") +
                    "\n" + "Email : " + requestMap.get("email") + "\n" + "Metodo de pago : " + requestMap.get("paymenthMethod");

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(FacturaConstantes.STORE_LOCATION + "\\" + fileName + ".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph paragraphHeader = new Paragraph("Gestion de categorias y productos", getFont("Header"));
                paragraphHeader.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraphHeader);

                PdfPTable pdfTable = new PdfPTable(5);
                pdfTable.setWidthPercentage(100);
                addTableHeader(pdfTable);

                JSONArray jsonArray = FacturaUtils.getJsonArrayFromString((String)requestMap.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(pdfTable, FacturaUtils.getMapFromJson(jsonArray.toString(i)));
                }
                document.add(pdfTable);

                Paragraph footer = new Paragraph("Total : " + requestMap.get("total") + "\n" +
                        "Gracias por visitarnos, vuelva pronto", getFont("Data"));
                document.add(footer);

                document.close();

                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}", HttpStatus.OK);
            }
            return FacturaUtils.getResponseentity("Datos requeridos no encontrados", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void insertInvoice(Map<String, Object> requestMap) {
        try {
            Factura factura = new Factura();
            factura.setUuid((String) requestMap.get("uuid"));
            factura.setName((String) requestMap.get("name"));
            factura.setEmail((String) requestMap.get("email"));
            factura.setContactNumber((String) requestMap.get("contactNumber"));
            factura.setPaymentMethod((String) requestMap.get("paymenthMethod"));
            factura.setTotal(Integer.parseInt((String) requestMap.get("total")));
            factura.setProductDetails((String) requestMap.get("productDetails"));
            factura.setCreatedBy(jwtFilter.getCurrentUser());
            facturaDAO.save(factura);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymenthMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("total");
    }


    private void addTableHeader(PdfPTable pdfPTable) {
        log.info("Dentro del addTableHeader");
        Stream.of("Nombre", "Categoria", "Cantidad", "Precio", "Sub Total")
                .forEach(columnTitle -> {
                    PdfPCell pdfPCell = new PdfPCell();
                    pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    pdfPCell.setBorderWidth(2);
                    pdfPCell.setPhrase(new Phrase(columnTitle));
                    pdfPCell.setBackgroundColor(BaseColor.YELLOW);
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPTable.addCell(pdfPCell);
                });
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Dentro de setRectangleInPdf");
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);
    }

    private Font getFont(String type) {
        log.info("Dentro de getFont");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void addRows(PdfPTable pdfPTable, Map<String, Object> data) {
        log.info("Dentro de addRows");
        pdfPTable.addCell((String) data.get("name"));
        pdfPTable.addCell((String) data.get("category"));
        pdfPTable.addCell((String) data.get("quantity"));
        pdfPTable.addCell(Double.toString((Double) data.get("price")));
        pdfPTable.addCell(Double.toString((Double) data.get("total")));
    }
}
