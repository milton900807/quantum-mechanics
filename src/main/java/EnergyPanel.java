
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class EnergyPanel extends JPanel {
    JPanel control;
    EnergyBar ebar;
    Floyd machine;
    double prevMass;
    double prevLeng;
    TextField mass_text;
    TextField length_text;
    TextField gamma_text;
    TextField wv_text;
    Choice length_units;
    Choice mass_units;
    Pink _parent;

    public EnergyPanel() {
        this.initialize();
    }

    public EnergyPanel(Floyd machine, Pink _parent) {
        this.ebar = machine.box;
        this.machine = machine;
        this._parent = _parent;
        this.initialize();
    }

    private void initialize() {
        float initial_mass = 1.0F;
        float initial_length = 7.4F;
        this.setLayout(new BorderLayout());
        this.setFont(new Font("Helvetica", 0, 12));
        this.setBackground(Color.gray);
        this.setForeground(Color.white);
        Label mass_lab = new Label("Mass", 2);
        Label length_lab = new Label("length", 2);
        new Label("Gamma ");
        this.mass_text = new TextField("" + initial_mass);
        this.length_text = new TextField("" + initial_length);
        this.mass_units = new Choice();
        this.mass_units.addItem("kilograms");
        this.mass_units.addItem("grams");
        this.mass_units.addItem("amu");
        this.mass_units.addItem("electron");
        this.mass_units.select(3);
        this.length_units = new Choice();
        this.length_units.addItem("meters");
        this.length_units.addItem("centimeters");
        this.length_units.addItem("angstroms");
        this.length_units.select(2);
        this.prevMass = (double) initial_mass;
        this.prevLeng = (double) initial_length;
        this.control = new JPanel ();
        this.control.setBackground(Color.gray);
        this.control.setForeground(Color.black);
        this.control.setLayout(new GridLayout(2, 4));
        this.control.add(mass_lab);
        this.control.add(this.mass_text);
        this.control.add(this.mass_units);
        this.control.add(length_lab);
        this.control.add(this.length_text);
        this.control.add(this.length_units);
        this.add("Center", this.control);
    }

    public boolean calculate() {
        double massin = Double.valueOf(this.mass_text.getText()).doubleValue();
        double lengthin = Double.valueOf(this.length_text.getText()).doubleValue();
        if (massin >= 0.0D && lengthin >= 0.0D) {
            if (this.ebar.hasLevels()) {
                this.ebar.clearLevels();
            }

            int m = this.mass_units.getSelectedIndex();
            int l = this.length_units.getSelectedIndex();
            this.ebar.calculateEnergy(massin, lengthin, m, l, 2);
            this.prevMass = massin;
            this.prevLeng = lengthin;
            this.machine.box.setGamma(40.0D);
            this.machine.reRun();
            return true;
        } else {
            this.machine.prompt(" Values must be greater than zero");
            massin = this.prevMass;
            lengthin = this.prevLeng;
            this.mass_text.setText("" + this.prevMass);
            this.length_text.setText("" + this.prevLeng);
            return true;
        }
    }

    public boolean handleEvent(Event e) {
        switch (e.id) {
            case 201:
                System.exit(0);
                return true;
            case 1001:
                if (!(e.target instanceof TextField) && !(e.target instanceof Choice)) {
                    if (e.target instanceof Button) {
//                        if (e.target == this.floatButton) {
//                            if (this.floatButton.getLabel() == "float") {
//                                this.floatButton.setLabel("unfloat");
//                                this._parent.floatApp();
//                            } else if (this.floatButton.getLabel() == "unfloat") {
//                                this.floatButton.setLabel("float");
//                                this._parent.unfloatApp();
//                            }
//
//                            return true;
//                        }

                        return true;
                    }

                    return super.handleEvent(e);
                }

                return this.calculate();
            default:
                return super.handleEvent(e);
        }
    }
}
