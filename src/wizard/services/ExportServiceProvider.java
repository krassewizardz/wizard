package wizard.services;

import wizard.models.Report;
import wizard.models.User;

import java.nio.file.Path;


public interface ExportServiceProvider {
    void exportToFile(Report r, String s, User u) throws Exception;
}