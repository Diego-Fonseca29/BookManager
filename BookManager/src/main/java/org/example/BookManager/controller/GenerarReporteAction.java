package org.example.BookManager.controller;

import org.openxava.actions.*;
import org.openxava.model.*;
import java.util.*;

public class GenerarReporteAction extends ViewBaseAction {

    public void execute() throws Exception {
        Map<String, Object> valores = new HashMap<>();
        valores.put("nombreReporte", "Reporte Automatico - " + new java.util.Date());

        MapFacade.create("Reporte", valores);

        addMessage("Reporte generado exitosamente");
        getView().refresh();
    }
}