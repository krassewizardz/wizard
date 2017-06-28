package wizard.pdf;

import wizard.models.*;
import wizard.services.ExportServiceProvider;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LeifHarlinghausen on 27.06.2017.
 */
class PdfHeaderAndFooter extends PdfPageEventHelper {
    private String datum;
    private int seitenzahl;
    private User user;
    public PdfHeaderAndFooter(User u)  {
        seitenzahl = 0;
        this.user = u;
        Date aktuellesDatum = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        datum = dt.format(aktuellesDatum);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Font headerFont = FontFactory.getFont(FontFactory.TIMES, 9, BaseColor.BLACK);
        Phrase header = new Phrase("Seite "+ ++seitenzahl, headerFont);
        Phrase footer = new Phrase("Erstellt am "+datum+" von "+user.getUsername(), headerFont);
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                header,
                document.right(),
                document.top() + 5, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                footer,
                document.right(),
                document.bottom() - 10, 0);

        BaseColor lineColor = new BaseColor(166,166,166);
        cb.setColorStroke(lineColor);
        cb.moveTo(Utilities.millimetersToPoints(198), 806);
        cb.lineTo(36, 806);
        cb.closePathStroke();
    }
}

public class PdfGenerator implements ExportServiceProvider {
    private Report report;
    private Document document;
    private PdfPTable annualPlan;
    private Field field;
    private ArrayList<wizard.models.Field> fields;

    @Override
    public void exportToFile(Report r, String s, User u) throws Exception {

        try{
            s = s+"\\PDF.pdf";
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(s));
            PdfHeaderAndFooter event = new PdfHeaderAndFooter(u);
            writer.setPageEvent(event);

            document.open();
            document.addAuthor(u.getName());
            document.addTitle("Report");

            initializeAnnualPlanTable();
            createProfessionHeader();
            for(int i =0; i< report.getAnnualPlan().getSubjects().size(); i++){

                for (Subject subject: report.getAnnualPlan().getSubjects()){
                    fields = (ArrayList<Field>) subject.getFields();
                    for(Field field : fields) {
                        for(Situation situation: field.getSituations()){

                        }
                    }
                }
            }
            document.close();
        } catch (Exception e){
            document.close( );
            throw e;
        }
    }

    public void createCell(int spaltenzahl) {
        BaseColor lineColor = new BaseColor(166,166,166);
        PdfPCell leereCell = new PdfPCell();
        leereCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        leereCell.setColspan(spaltenzahl);
        leereCell.setBorderColor(lineColor);
        annualPlan.addCell(leereCell);
    }
    public void initializeAnnualPlanTable() throws DocumentException {
        annualPlan = new PdfPTable(12);
        annualPlan.setWidthPercentage(100);
    }

    public void createProfessionHeader() throws DocumentException {
        document.open();
        BaseColor lineColor = new BaseColor(166,166,166);

        int fullCollSPan = report.getAnnualPlan().getSubjects().size();
        Font regularFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 6f, Font.NORMAL, BaseColor.BLACK);

        PdfPCell unterrichtswochenZeile = new PdfPCell(new Phrase("Unterrichtswochen", regularFont));
        unterrichtswochenZeile.setColspan(fullCollSPan);
        unterrichtswochenZeile.setBorderColor(lineColor);
        unterrichtswochenZeile.setBackgroundColor(BaseColor.WHITE);
        annualPlan.addCell(unterrichtswochenZeile);


        if (report.getAnnualPlan().getSubjects().equals("Blockunterricht")) {
            PdfPCell cell1 = new PdfPCell(new Phrase(new Chunk("01", regularFont)));
            cell1.setColspan(1);
            cell1.setBackgroundColor(BaseColor.WHITE);
            cell1.setBorderColor(lineColor);
            annualPlan.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Phrase("02", regularFont));
            cell2.setColspan(1);
            cell2.setBackgroundColor(BaseColor.WHITE);
            cell2.setBorderColor(lineColor);
            annualPlan.addCell(cell2);
            PdfPCell cell3 = new PdfPCell(new Phrase("03", regularFont));
            cell3.setColspan(1);
            cell3.setBackgroundColor(BaseColor.WHITE);
            cell3.setBorderColor(lineColor);
            annualPlan.addCell(cell3);
            PdfPCell cell4 = new PdfPCell(new Phrase("04", regularFont));
            cell4.setColspan(1);
            cell4.setBackgroundColor(BaseColor.WHITE);
            cell4.setBorderColor(lineColor);
            annualPlan.addCell(cell4);
            PdfPCell cell5 = new PdfPCell(new Phrase("05", regularFont));
            cell5.setColspan(1);
            cell5.setBackgroundColor(BaseColor.WHITE);
            cell5.setBorderColor(lineColor);
            annualPlan.addCell(cell5);
            PdfPCell cell6 = new PdfPCell(new Phrase("06", regularFont));
            cell6.setColspan(1);
            cell6.setBackgroundColor(BaseColor.WHITE);
            cell6.setBorderColor(lineColor);
            annualPlan.addCell(cell6);
            PdfPCell cell7 = new PdfPCell(new Phrase("07", regularFont));
            cell7.setColspan(1);
            cell7.setBackgroundColor(BaseColor.WHITE);
            cell7.setBorderColor(lineColor);
            annualPlan.addCell(cell7);
            PdfPCell cell8 = new PdfPCell(new Phrase("08", regularFont));
            cell8.setColspan(1);
            cell8.setBackgroundColor(BaseColor.WHITE);
            cell8.setBorderColor(lineColor);
            annualPlan.addCell(cell8);
            PdfPCell cell9 = new PdfPCell(new Phrase("09", regularFont));
            cell9.setColspan(1);
            cell9.setBackgroundColor(BaseColor.WHITE);
            cell9.setBorderColor(lineColor);
            annualPlan.addCell(cell9);
            PdfPCell cell10 = new PdfPCell(new Phrase("10", regularFont));
            cell10.setColspan(1);
            cell10.setBackgroundColor(BaseColor.WHITE);
            cell10.setBorderColor(lineColor);
            annualPlan.addCell(cell10);
            PdfPCell cell11 = new PdfPCell(new Phrase("11", regularFont));
            cell11.setColspan(1);
            cell11.setBackgroundColor(BaseColor.WHITE);
            cell11.setBorderColor(lineColor);
            annualPlan.addCell(cell11);
            PdfPCell cell12 = new PdfPCell(new Phrase("12", regularFont));
            cell12.setColspan(1);
            cell12.setBackgroundColor(BaseColor.WHITE);
            cell12.setBorderColor(lineColor);
            annualPlan.addCell(cell12);
        }
    }
}
