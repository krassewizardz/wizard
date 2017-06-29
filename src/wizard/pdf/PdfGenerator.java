package wizard.pdf;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import wizard.models.*;
import wizard.services.ExportServiceProvider;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 *  @author Leif Harlinghausen
 */

public class PdfGenerator implements ExportServiceProvider {
    private Report report;
    private Document document;
    private PdfPTable yearlyTable;
    private ArrayList<wizard.models.Field> fields;
    private Configuration config;

    public PdfGenerator(Configuration config){
        this.config = config;
    }

    @Override
    public void exportToFile(Report r, String s, User user) throws Exception {

        report = r;
        fields = new ArrayList<>();
        try{
            s = s+"\\PDF.pdf";
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(s));
            PdfHeaderAndFooter event = new PdfHeaderAndFooter(user);
            writer.setPageEvent(event);
            document.open();

            createTable();
            createHeader();
            createYearlyViewHeader();

            for(Subject subject: r.getProfession().getSubjects()){
               createSubject(subject.getName());
               for(Field field : subject.getFields()){
                   int min = 1;
                   int max = 12;
                   for(Situation situation : field.getSituations()){
                       if(situation.getEnd() > max){
                           max = situation.getEnd();
                       }
                       if(situation.getStart() < min){
                           min = situation.getStart();
                       }
                   }
                   fields.add(field);
                   createYearlyFieldView(field, min, max);

               }
           }
            document.add(yearlyTable);
            document.newPage();
            for(Subject subject : r.getProfession().getSubjects()){
              for (Field field : fields) {
                  for (Situation situation : field.getSituations()) {
                      createDetailSituationView(situation, field, subject);
                      document.newPage();
                  }
              }
            }

            document.close();
        } catch (Exception e){
            document.close( );
            throw e;
        }
    }

    public void createEmptyCell(int cells) {
        BaseColor lineColor = new BaseColor(166,166,166);
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        emptyCell.setColspan(cells);
        emptyCell.setBorderColor(lineColor);
        yearlyTable.addCell(emptyCell);
    }
    public void createDetailSituationView(Situation situation, Field field, Subject subject) throws DocumentException {
        document.open();
        createDetailHeader("Detailanzeige Lernsituation");
        BaseColor lineColor = new BaseColor(166,166,166);


        PdfPTable situationTable = new PdfPTable(10);
        situationTable.setWidthPercentage(100);
        Font regularFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 9f, Font.NORMAL, BaseColor.BLACK);
        Font boldFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 9f, Font.BOLD, BaseColor.BLACK);

        Phrase subjectPhrase = new Phrase();
        subjectPhrase.add(new Chunk("Fach: ",boldFont));
        subjectPhrase.add(new Chunk(subject.getName(), regularFont));
        PdfPCell subjectCell = new PdfPCell(new Phrase(subjectPhrase));
        subjectCell.setColspan(10);
        subjectCell.setBorderColor(lineColor);
        situationTable.addCell(subjectCell);

        Phrase fieldPhrase = new Phrase();
        fieldPhrase.add(new Chunk("Lernfeld "+field.getId()+": ",boldFont));
        fieldPhrase.add(new Chunk(situation.getName(), regularFont));
        PdfPCell fieldCell = new PdfPCell(new Phrase(fieldPhrase));
        fieldCell.setColspan(10);
        fieldCell.setBorderColor(lineColor);
        situationTable.addCell(fieldCell);


        Phrase situationPhrase = new Phrase();
        situationPhrase.add(new Chunk("Lernsituation "+situation.getId()+": ",boldFont));
        situationPhrase.add(new Chunk(situation.getName(), regularFont));
        PdfPCell situationCell = new PdfPCell(new Phrase(situationPhrase));
        situationCell.setColspan(6);
        situationCell.setBorderColor(lineColor);
        situationTable.addCell(situationCell);

        Phrase durationPhrase = new Phrase();
        durationPhrase.add(new Chunk("Dauer: ",boldFont));
        durationPhrase.add(new Chunk(situation.getDuration()+"UStd", regularFont));
        PdfPCell durationCell = new PdfPCell(new Phrase(durationPhrase));
        durationCell.setColspan(2);
        durationCell.setBorderColor(lineColor);
        situationTable.addCell(durationCell);

        Phrase idPhrase = new Phrase("ID: "+situation.getId(),regularFont);
        PdfPCell idCell = new PdfPCell(new Phrase(idPhrase));
        idCell.setColspan(1);
        idCell.setBorderColor(lineColor);
        situationTable.addCell(idCell);

        PdfPCell fillerCell = new PdfPCell();
        fillerCell.setColspan(1);
        fillerCell.setBorderColor(lineColor);
        situationTable.addCell(fillerCell);


            Phrase scenarioPhrase = new Phrase();
            scenarioPhrase.add(new Chunk("Einstiegsszenario:\n", boldFont));
            scenarioPhrase.add(new Chunk(htmlToText(situation.getScenario()), regularFont));
            PdfPCell scenarioCell = new PdfPCell(scenarioPhrase);
            scenarioCell.setColspan(10);
            scenarioCell.setBorderColor(lineColor);
            situationTable.addCell(scenarioCell);

            Phrase outcomePhrase = new Phrase();
            outcomePhrase.add(new Chunk("Handlungsprodukt/Lernergebnis:\n", boldFont));
            outcomePhrase.add(new Chunk(htmlToText(situation.getOutcome()), regularFont));
            PdfPCell outcomeCell = new PdfPCell(outcomePhrase);
            outcomeCell.setColspan(10);
            outcomeCell.setBorderColor(lineColor);
            situationTable.addCell(outcomeCell);

            Phrase competencesPhrase = new Phrase();
            competencesPhrase.add(new Chunk("Wesentliche Kompetenzen:\n", boldFont));
            competencesPhrase.add(new Chunk(htmlToText(situation.getCompetences()), regularFont));
            PdfPCell competencesCell = new PdfPCell(competencesPhrase);
            competencesCell.setColspan(10);
            competencesCell.setBorderColor(lineColor);
            situationTable.addCell(competencesCell);

            Phrase contentPhrase = new Phrase();
            contentPhrase.add(new Chunk("Inhalte:\n", boldFont));
            contentPhrase.add(new Chunk(htmlToText(situation.getContent()), regularFont));
            PdfPCell contentCell = new PdfPCell(contentPhrase);
            contentCell.setColspan(10);
            contentCell.setBorderColor(lineColor);
            situationTable.addCell(contentCell);



            Phrase materialPhrase = new Phrase();
            materialPhrase.add(new Chunk("Unterrichtsmaterialien:\n", boldFont));
            materialPhrase.add(new Chunk(htmlToText(situation.getMaterials()), regularFont));
            PdfPCell materialCell = new PdfPCell(materialPhrase);
            materialCell.setColspan(10);
            materialCell.setBorderColor(lineColor);
            situationTable.addCell(materialCell);

        if(situation.getComments() != null){
            Phrase organisationPhrase = new Phrase();
            organisationPhrase.add(new Chunk("Organisatorische Hinweise:\n", boldFont));
            organisationPhrase.add(new Chunk(htmlToText(situation.getComments()), regularFont));
            PdfPCell organisationCell = new PdfPCell(organisationPhrase);
            organisationCell.setColspan(10);
            organisationCell.setBorderColor(lineColor);
            situationTable.addCell(organisationCell);
        }

        //if (template.isLerntechnik()) {
            Phrase techniquePhrase = new Phrase();
            String techniqueString= "";
            for (Technique technique: situation.getTechniques()) {
                if(technique != null)
                    techniqueString += technique +"\n";
            }
            techniquePhrase.add(new Chunk("Lern- und Arbeitstechniken:\n", boldFont));
            techniquePhrase.add(new Chunk(htmlToText(techniqueString.toString()), regularFont));
            PdfPCell techniqueCell = new PdfPCell(techniquePhrase);
            techniqueCell.setColspan(10);
            techniqueCell.setBorderColor(lineColor);
            situationTable.addCell(techniqueCell);
        //}

        //if (template.isLeistungsnachweis()) {
            Phrase archievementPhrase = new Phrase();
            String archievementStr = "";
            for (Achievement achievement: situation.getAchievements()) {
                if(achievement != null)
                    archievementStr += achievement+"\n";
            }
            archievementPhrase.add(new Chunk("Einstiegsszenario:\n", boldFont));
            archievementPhrase.add(new Chunk(htmlToText(archievementStr), regularFont));
            PdfPCell archievementCell = new PdfPCell(archievementPhrase);
            archievementCell.setColspan(10);
            archievementCell.setBorderColor(lineColor);
            situationTable.addCell(archievementCell);
        //}
        document.add(situationTable);
    }


    public String htmlToText(String html){
        String stringGefiltert = Jsoup.clean(html, Whitelist.basic());
        String stringGefilter2 = stringGefiltert.replaceAll("(&nbsp;){3,100}", "\\\u2022");
        String content = Jsoup.parse(stringGefilter2).text().replaceAll("\\u2022","\n\\\u2022");
        return content;
    }





    public void createTable() throws DocumentException {
        document.open(  );
        yearlyTable = new PdfPTable(12);
        yearlyTable.setWidthPercentage(100);
    }

      private void createYearlyFieldView(Field field, int min, int max) {
          document.open();
          BaseColor lineColor = new BaseColor(166,166,166);
          Font regularFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 6f, Font.NORMAL, BaseColor.BLACK);
          PdfPCell fieldCell = new PdfPCell(new Phrase("LF "+field.getId()+": "+field.getName()
                  +" ("+field.getDuration()+" UStd)", regularFont));
          Situation situation;

          fieldCell.setColspan(max - min+1);
          fieldCell.setBorderColor(lineColor);
          fieldCell.setBackgroundColor(BaseColor.WHITE);
          yearlyTable.addCell(fieldCell);

          if (max!= 12){
              createEmptyCell(12-max);
          }
          for (int i = 0; i < field.getSituations().size(); i++) {
              Situation s = field.getSituations().get(i);
              int j = i;
              if (s.getStart() != 1) {
                  if (j != 0 && s.getStart() - 1 != field.getSituations().get(j - 1).getEnd()) {
                      //createEmptyCell((ls.getStart() - 1) - field.getSituations().get(j - 1).getEnd());
                      createEmptyCell((s.getStart()-1) );
                  }
              }
              createYearlySituationView(s,field);
              if (s.getStart() != 1) {
                  if (j + 1 < field.getSituations().size() && field.getSituations().get(j + 1).getStart() < s.getEnd() && s.getEnd() != 12) {
                      createEmptyCell(12 - s.getEnd());
                  }
                  if (i == (field.getSituations().size() - 1) && s.getEnd() != 12) {
                      createEmptyCell(12 - s.getEnd());
                  }
              }
          }
      }

    private void createYearlySituationView(Situation situation, Field field){
        document.open();
        BaseColor lineColor = new BaseColor(166,166,166);
        Font regularFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 6f, Font.NORMAL, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase("LS "+field.getId()+"."+situation.getId()+": "
                +situation.getName()+" ("+situation.getDuration()+" UStd)", regularFont));
        cell.setColspan(situation.getEnd() - situation.getStart() + 1);
        cell.setBorderColor(lineColor);
        yearlyTable.addCell(cell);
    }
    public void createDetailHeader(String title) throws DocumentException {
        document.open();
        BaseColor textColor = new BaseColor(120, 163, 232);
        Font subtitleFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9f, Font.NORMAL, textColor);
        Font titleFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14f, Font.BOLD, textColor);
        Paragraph titel = new Paragraph(title,titleFont);
        titel.setAlignment(Element.ALIGN_CENTER);
        document.add(titel);

        Paragraph obligatoryInformation = new Paragraph("\nAbteilung: " + report.getDepartment() + "\nAusbildungsberuf: " + report.getProfession().getName()
                + "\nAusbildungsjahr: " + report.getProfession().getYearOfTraining() + "\nUnterrichtsform: " + report.getTeachingForm() + "\nBildungsgangleitung: "+"\n\n", subtitleFont);
                /*+ report.getProfession().+"\n\n",subtitleFont)*/
        document.add(obligatoryInformation);
    }
    public void createHeader() throws DocumentException{
        document.open();
        BaseColor textColor = new BaseColor(120, 163, 232);
        Font titleFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14f, Font.BOLD, textColor);
        Paragraph title = new Paragraph("Jahresübersicht",titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
    }

    public void createYearlyViewHeader() throws DocumentException {


        BaseColor lineColor = new BaseColor(166,166,166);

        int fullCollSPan = report.getProfession().getSubjects().size();
        Font regularFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 6f, Font.NORMAL, BaseColor.BLACK);

        PdfPCell weekRow = new PdfPCell(new Phrase("Unterrichtswochen", regularFont));
        weekRow.setColspan(12);
        weekRow.setBorderColor(lineColor);
        weekRow.setBackgroundColor(BaseColor.WHITE);
        yearlyTable.addCell(weekRow);



        if (report.getTeachingForm().equals("Blockunterricht")) {
            PdfPCell cell1 = new PdfPCell(new Phrase(new Chunk("01", regularFont)));
            cell1.setColspan(1);
            cell1.setBackgroundColor(BaseColor.WHITE);
            cell1.setBorderColor(lineColor);
            yearlyTable.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Phrase("02", regularFont));
            cell2.setColspan(1);
            cell2.setBackgroundColor(BaseColor.WHITE);
            cell2.setBorderColor(lineColor);
            yearlyTable.addCell(cell2);
            PdfPCell cell3 = new PdfPCell(new Phrase("03", regularFont));
            cell3.setColspan(1);
            cell3.setBackgroundColor(BaseColor.WHITE);
            cell3.setBorderColor(lineColor);
            yearlyTable.addCell(cell3);
            PdfPCell cell4 = new PdfPCell(new Phrase("04", regularFont));
            cell4.setColspan(1);
            cell4.setBackgroundColor(BaseColor.WHITE);
            cell4.setBorderColor(lineColor);
            yearlyTable.addCell(cell4);
            PdfPCell cell5 = new PdfPCell(new Phrase("05", regularFont));
            cell5.setColspan(1);
            cell5.setBackgroundColor(BaseColor.WHITE);
            cell5.setBorderColor(lineColor);
            yearlyTable.addCell(cell5);
            PdfPCell cell6 = new PdfPCell(new Phrase("06", regularFont));
            cell6.setColspan(1);
            cell6.setBackgroundColor(BaseColor.WHITE);
            cell6.setBorderColor(lineColor);
            yearlyTable.addCell(cell6);
            PdfPCell cell7 = new PdfPCell(new Phrase("07", regularFont));
            cell7.setColspan(1);
            cell7.setBackgroundColor(BaseColor.WHITE);
            cell7.setBorderColor(lineColor);
            yearlyTable.addCell(cell7);
            PdfPCell cell8 = new PdfPCell(new Phrase("08", regularFont));
            cell8.setColspan(1);
            cell8.setBackgroundColor(BaseColor.WHITE);
            cell8.setBorderColor(lineColor);
            yearlyTable.addCell(cell8);
            PdfPCell cell9 = new PdfPCell(new Phrase("09", regularFont));
            cell9.setColspan(1);
            cell9.setBackgroundColor(BaseColor.WHITE);
            cell9.setBorderColor(lineColor);
            yearlyTable.addCell(cell9);
            PdfPCell cell10 = new PdfPCell(new Phrase("10", regularFont));
            cell10.setColspan(1);
            cell10.setBackgroundColor(BaseColor.WHITE);
            cell10.setBorderColor(lineColor);
            yearlyTable.addCell(cell10);
            PdfPCell cell11 = new PdfPCell(new Phrase("11", regularFont));
            cell11.setColspan(1);
            cell11.setBackgroundColor(BaseColor.WHITE);
            cell11.setBorderColor(lineColor);
            yearlyTable.addCell(cell11);
            PdfPCell cell12 = new PdfPCell(new Phrase("12", regularFont));
            cell12.setColspan(1);
            cell12.setBackgroundColor(BaseColor.WHITE);
            cell12.setBorderColor(lineColor);
            yearlyTable.addCell(cell12);
        }
    }

    private void createSubject(String subject) throws DocumentException {
        BaseColor tableColor = new BaseColor(89, 148, 242);
        BaseColor textColor = new BaseColor(71,71,71);
        BaseColor lineColor = new BaseColor(166,166,166);
        Font greyFont = FontFactory.getFont("c:/windows/fonts/Calibri.ttf", 10f, Font.BOLD, textColor);
        document.open();
        PdfPCell subjectContent = new PdfPCell(new Phrase(subject, greyFont));
        subjectContent.setColspan(12);
        subjectContent.setBackgroundColor(tableColor);
        subjectContent.setBorderColor(lineColor);
        yearlyTable.addCell(subjectContent);
    }
}
