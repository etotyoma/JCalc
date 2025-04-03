module net.etotyoma.jcalc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.logging;

    opens net.etotyoma.jcalc to javafx.fxml;
    exports net.etotyoma.jcalc;
    exports net.etotyoma.jcalc.controller;
    opens net.etotyoma.jcalc.controller to javafx.fxml;
    exports net.etotyoma.jcalc.core;
    opens net.etotyoma.jcalc.core to javafx.fxml;
}