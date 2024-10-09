module disasterresponse {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens disasterresponse.models to javafx.base;
    opens disasterresponse.controllers to javafx.fxml;
    
    exports disasterresponse;
}
