package tablasverdad;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ventanaTablasVerdad extends javax.swing.JFrame {
    DefaultListModel modelo = new DefaultListModel();
    
    String simbolo = "";
    String caracter = "";
    String simbolo_pendiente = null;
    int tamanio = 99;
    
    String E[] = new String[tamanio];
//    String E = "";
    int contE = 0;
    String salida1 = "";
    
    public ventanaTablasVerdad() {
        initComponents();
        jlist_proceso.setModel(modelo);

        StyledDocument doc = textfield_titulo_proceso.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        //Por coordenadas
        setLocation(600, 300);
        //Centrada
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Logo_Tabla_Verdad.png")).getImage());
    }
    
    public void agregarSimbolo(String simbolo) {
//        E[contE] = simbolo.replace("¬","n");
        E[contE] = simbolo;
        salida1 = salida1 + " " + simbolo;
        textfield_expresion.setText(salida1);
//        E = E + " " + simbolo;
//        TextField_Expresion.setText(E);
        contE++;
    }
    
    public void eliminarSimbolo() {
        int UltPos = 0;
        UltPos = contE - 1;
        if(contE > 0) {
            E[UltPos] = null;
            salida1 = salida1.substring(0, salida1.length()-2);
            textfield_expresion.setText(salida1);
            contE--;
        } 
    }
    
    public void eliminarAll() {
        if(contE > 0) {
            for(int i = 0; i < E.length; i++) {
                if(E[i] != null) {
                    E[i] = null;
                }
            }
            salida1 = "";
            textfield_expresion.setText(salida1);
            contE = 0;
            
            for (int i = 0; i < 4; i++) {          
                for (int j = 0; j < 8; j++) {
                    table_result.setValueAt("", j, i);
                }
            }
            
            modelo.removeAllElements();
        } 
    }
    
    public void limpiarTabla() {
        for (int i = 0; i < 4; i++) {          
            for (int j = 0; j < 8; j++) {
                table_result.setValueAt("", j, i);
            }
        }
        modelo.removeAllElements();
    }
    
    public String[] resolucionOperacion(String ope1[], String interacion, String ope2[]) {
        String Result[] = new String[8];
        
        if(interacion == "n") {
            for(int k = 0; k <  ope1.length; k++) {
                if(ope1[k] != null) {
                    //interseccion: V - V = V, de resto falsas
                    if(ope1[k] == "V" && ope2[k] == "V"){
                        Result[k] = "V";
                    } else {
                        Result[k] = "F";
                    }
                }
            }
        } else if(interacion == "u"){
            for(int k = 0; k < ope1.length; k++) {
                if(ope1[k] != null) {
                    //union: F-F = F, de resto verdaderas
                    if(ope1[k] == "F" && ope2[k] == "F"){
                        Result[k] = "F";
                    } else {
                        Result[k] = "V";
                    }
                }
            }
        } else if(interacion == "→"){
            for(int k = 0; k < ope1.length; k++) {
                if(ope1[k] != null) {
                    //union: F-F = F, de resto verdaderas
                    if(ope1[k] == "V" && ope2[k] == "F"){
                        Result[k] = "F";
                    } else {
                        Result[k] = "V";
                    }
                }
            }
        } else if(interacion == "↔"){
            for(int k = 0; k < ope1.length; k++) {
                if(ope1[k] != null) {
                    //union: F-F = F, de resto verdaderas
                    if(ope1[k] == "V" && ope2[k] == "V"){
                        Result[k] = "V";
                    } else if(ope1[k] == "F" && ope2[k] == "F"){
                        Result[k] = "V";
                    } else {
                        Result[k] = "F";
                    }
                }
            }
        } else {
            return Result;
        }        
        return Result;
    }
    
    public String[] resolucionOperacionNegacion(String ope[]) {
        String Result[] = new String[8];

        for(int k = 0; k <  ope.length; k++) {
            if(ope[k] != null) {
                if(ope[k] == "V"){
                    // V = F
                    Result[k] = "F";
                } else if(ope[k] == "F") {
                    // F = V
                    Result[k] = "V";
                }
            }
        }
     
        return Result;
    }
    
    public void imprimirTabla(String E[], int pos) {
        for(int l = 0; l < E.length; l++) {
            if(E[l] != null) {
                table_result.setValueAt(E[l], l, pos);
            }
        }
    }
    
    public void imprimirProceso(String E[]) {
        System.out.println("");
        String texto = "";
        int j = 0;
        for (int i = 0; i < E.length; i++) {
            if(E[i] != null) {
                texto = texto + " " + E[i];
                System.out.print(E[i]);
            }
        }
        modelo.addElement(texto);
    }
            
    
    public String[] validacionExpresion(int posicArray, String Expresion []) {
        String ExpresionAux [] = new String[99];
        if(Expresion[0] != "(" || Expresion[posicArray-1] != ")") {
            int h = 0;
            for(int g = 0; g < Expresion.length; g++){
                if(Expresion[g] != null) {
                    if(h == 0) {
                        ExpresionAux[h] = "(";
                        h++;
                    }
                    ExpresionAux[h] = Expresion[g];
                    h++;
                } else {
                    break;
                }
            }
            int contExpNew = 0;
            for(int g = 0; g < ExpresionAux.length; g++){
                if(ExpresionAux[g] != null) {
                    contExpNew++;
                }
            }
            ExpresionAux[contExpNew] = ")";
        } else {
            ExpresionAux = Expresion;
        }
        
        return ExpresionAux;
    }
    
    public boolean solucionExpresion(String[] Expresion) {
        int p = 0, q = 0, r = 0, u = 0, n = 0, expo = 0, cantExp = 0, pos_paren_izq = 0, pos_paren_der = 0;
        int contarray = 0;

        for(int i = 0; i < Expresion.length; i++) {
            if(Expresion[i] == "p") {
                p++;
            } 
            
            if(Expresion[i] == "q") {
                q++;
            }
            
            if(Expresion[i] == "r") {
                r++;
            }
            
            if(Expresion[i] != null) {
                contarray++;
            }
        }
        
        if(contarray == 0) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese una expresión", "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Expresion = validacionExpresion(contarray, Expresion);
        
        if(p > 0){
            expo += 1;
        }
        if(q > 0) {
            expo += 1;
        }
        if(r > 0) {
            expo += 1;
        } 
//        if(expo == 1){
//            expo = 2;
//        }
        cantExp = (int) Math.pow(2, expo); 
        limpiarTabla();
        
        String P[]  = new String[cantExp];
        String PN[] = new String[cantExp];
        String Q[]  = new String[cantExp];
        String QN[]  = new String[cantExp];
        String R[]  = new String[cantExp]; 
        String RN[]  = new String[cantExp];
        String Result[] = new String[cantExp];
        String array_imp[] = new String[cantExp];
        String intArray[][] = new String[99][cantExp];

        if(cantExp == 2) {
            if(p > 0) {
                P[0] = "V";
                P[1] = "F";
                //NEGATIVO
                PN[0] = "F";
                PN[1] = "V";
            }
            if(q > 0) {
                Q[0] = "V";
                Q[1] = "F";
                //NEGATIVO
                QN[0] = "F";
                QN[1] = "V";
            }
            if(r > 0) {
                R[0] = "V";
                R[1] = "F";
                //NEGATIVO
                RN[0] = "F";
                RN[1] = "V";
            }
        } else if(cantExp == 4) { 
            if(p > 0) {
                P[0] = "V";
                P[1] = "V";
                P[2] = "F";
                P[3] = "F";
                //NEGATIVO
                PN[0] = "F";
                PN[1] = "F";
                PN[2] = "V";
                PN[3] = "V";
                if(q > 0) {
                    Q[0] = "V";
                    Q[1] = "F";
                    Q[2] = "V";
                    Q[3] = "F";
                    //NEGATIVO
                    QN[0] = "F";
                    QN[1] = "V";
                    QN[2] = "F";
                    QN[3] = "V";
                } else if(r > 0) {
                    R[0] = "V";
                    R[1] = "F";
                    R[2] = "V";
                    R[3] = "F";
                    //NEGATIVO
                    RN[0] = "F";
                    RN[1] = "V";
                    RN[2] = "F";
                    RN[3] = "V";
                }
            } else if(q > 0) {
                Q[0] = "V";
                Q[1] = "V";
                Q[2] = "F";
                Q[3] = "F";
                //NEGATIVO
                QN[0] = "F";
                QN[1] = "F";
                QN[2] = "V";
                QN[3] = "V";
                
                R[0] = "V";
                R[1] = "F";
                R[2] = "V";
                R[3] = "F";
                //NEGATIVO
                RN[0] = "F";
                RN[1] = "V";
                RN[2] = "F";
                RN[3] = "V";
            }
        } else if(cantExp == 8) {
            //P
            P[0] = "V";
            P[1] = "V";
            P[2] = "V";
            P[3] = "V";
            P[4] = "F";
            P[5] = "F";
            P[6] = "F";
            P[7] = "F";
            //NEGATIVO
            PN[0] = "F";
            PN[1] = "F";
            PN[2] = "F";
            PN[3] = "F";
            PN[4] = "V";
            PN[5] = "V";
            PN[6] = "V";
            PN[7] = "V";
            
            //Q
            Q[0] = "V";
            Q[1] = "V";
            Q[2] = "F";
            Q[3] = "F";
            Q[4] = "V";
            Q[5] = "V";
            Q[6] = "F";
            Q[7] = "F";
            //NEGATIVO
            QN[0] = "F";
            QN[1] = "F";
            QN[2] = "V";
            QN[3] = "V";
            QN[4] = "F";
            QN[5] = "F";
            QN[6] = "V";
            QN[7] = "V";
            
            //R
            R[0] = "V";
            R[1] = "F";
            R[2] = "V";
            R[3] = "F";
            R[4] = "V";
            R[5] = "F";
            R[6] = "V";
            R[7] = "F";
            //NEGATIVO
            RN[0] = "F";
            RN[1] = "V";
            RN[2] = "F";
            RN[3] = "V";
            RN[4] = "F";
            RN[5] = "V";
            RN[6] = "F";
            RN[7] = "V";
        }

        imprimirTabla(P, 0);
        imprimirTabla(Q, 1);
        imprimirTabla(R, 2);

        String array[];
        array = Expresion;
        int contResp = 0;
        int arrayRespu [] = null;
        
        System.out.println("");
        System.out.println("-------------------------------------------------");
        
        imprimirProceso(array);
        do{
            String aux1[] = new String[99];
            String ope[] = new String[8];
            String ope1[] = new String[8];
            String ope2[] = new String[8];
            String ope3[] = new String[8];
            
            int con = 0;
            int bandera = 0, bandera2 = 0;
            int j = 0, contGeneral = 0;
            
            String pos = "";
            String operador1 = "", operador2 = "", iteracion = "", operador = "";
            
            if(array[0] != "(") {
                //Validación si ya la expresion no tiene parentesis
                int c = 0;
                int contExp = 0;
                String auxParent[] = new String[99];
                
                for (int h = 0; h < array.length; h++) {
                    if(array[h] != null) {
                        contExp++;
                    }
                    if(array[h] != null) {
                        if(c == 0) {
                            auxParent[c] = "(";
                            c++;
                        }
                        auxParent[c] = array[h];
                        c++;
                    } else {
                        break;
                    }
                }

                auxParent[contExp+1] = ")";
                array = auxParent;
                imprimirProceso(array);
            }
            
            for (int v = 0; v < 10; v++) {
                if(array[v] != null) {
                    contGeneral++;
                }
            }
            
            if(contGeneral <= 3) {
                //Validación si ya no hay mas por resolver y hay parentesis
                if(array[0] == "(") {
                    int c = 0;
                    int contExp = 0;
                    String auxParent[] = new String[99];
                    
                    for (int h = 0; h < array.length; h++) { 
                        if(array[h] != null) {
                            if(array[h] != "(") {
                                auxParent[c] = array[h];
                                c++;
                            }
                        } else {
                            break;
                        }
                    }
                    auxParent[contExp+1] = null;
                    array = auxParent;

                    imprimirProceso(array);
                }
            }

            for (int i = 0; i < array.length; i++ ) {
                if(array[i] != null) {
                    contarray = 0;
                    pos = array[i];
                    j = i;
                    
                    if(contGeneral < 3) {
                        break;
                    }

                    if(pos  == ")" && bandera == 0) {  
                        if((array[j-1] == "p" || array[j-1]== "q" || array[j-1]== "r") || array[j-1] == "pn" || array[j-1]== "qn" || array[j-1]== "rn"){
                            try {
                                if(array[j-1] == "¬") {
                                    JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return false;
                                } else if(array[j-2] == "¬") {
                                    //Si el operador 2 tiene una negación 
                                    operador = array[j-1];
                                    if(operador  == "p"){
                                        operador  = "pn";
                                    } else if(operador  == "q"){
                                        operador  = "qn";
                                    } else if(operador  == "r"){
                                        operador  = "rn";
                                    } else {
                                        JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }      

                                    aux1[con-2] = operador;
                                    aux1[con-1] = array[j];
                                    bandera++;	
                                } else if(array[j-3] == "¬") {
                                    JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return false;
                                } else if(array[j-4] == "¬") {
                                    //Si el operador 1 tiene una negación 
                                    operador = array[j-3];
                                    if(operador  == "p"){
                                        operador  = "pn";
                                    } else if(operador  == "q"){
                                        operador  = "qn";
                                    } else if(operador  == "r"){
                                        operador  = "rn";
                                    } else {
                                        JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }      

                                    aux1[con-4] = operador;
                                    aux1[con-3] = array[j-2];
                                    aux1[con-2] = array[j-1];
                                    aux1[con-1] = array[j];
                                    bandera++;	
                                } else {
                                    operador1 = array[j-3];
                                    iteracion = array[j-2];
                                    operador2 = array[j-1];

//                                    if(iteracion == "↔" || iteracion == "→") {
//                                        //validación si se encuentra ↔, revisar si hay una operación antes
//                                        if(array[j-4] == "n" || array[j-4] == "u") {
//                                            operador1 = array[j-5];
//                                            iteracion = array[j-4];
//                                            operador2 = array[j-3];
//                                        }
//                                    }

                                    if(operador1  == "p"){
                                        ope1 = P;
                                    } else if(operador1  == "q"){
                                        ope1 = Q;
                                    } else if(operador1  == "r"){
                                        ope1 = R;
                                    } else if(operador1  == "pn"){
                                        ope1 = PN;
                                    } else if(operador1  == "qn"){
                                        ope1 = QN;
                                    } else if(operador1  == "rn"){
                                        ope1 = RN;
                                    } else {
                                        try {
                                            ope1 = intArray[Integer.parseInt(operador1)];
                                        } catch (Exception NumberFormatException) {
                                            JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }
                                    }

                                    if(operador2  == "p"){
                                        ope2 = P;
                                    } else if(operador2  == "q"){
                                        ope2 = Q;
                                    } else if(operador2  == "r"){
                                        ope2 = R;
                                    } else if(operador2  == "pn"){
                                        ope2  = PN;
                                    } else if(operador2  == "qn"){
                                        ope2  = QN;
                                    } else if(operador2  == "rn"){
                                        ope2  = RN;
                                    } else {
                                        try {
                                            ope2 = intArray[Integer.parseInt(operador2)];
                                        } catch (Exception NumberFormatException) {
                                            JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }
                                    }

                                    Result = resolucionOperacion(ope1, iteracion, ope2);
                                    if(Result[0] == null) {
                                        JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }

                                    for(int k = 0; k <  ope1.length; k++) {
                                        intArray[contResp][k] = Result[k];
                                    }

//                                    if(array[j-2] == "↔" || array[j-2] == "→") {
//                                        //validación si se encuentra ↔, revisar si hay una operación antes
//                                        if(array[j-4] == "n" || array[j-4] == "u") {
//                                            aux1[con-5] = Integer.toString(contResp);
//                                            aux1[con-4] = array[j-2];
//                                            aux1[con-3] = array[j-1];
//                                            aux1[con-2] = array[j];
//                                            aux1[con-1] = null;
//                                            con = con-1;
//
//                                            bandera2 = 1;
//                                        }
//                                    }

                                    if(bandera2 == 0) {
                                        if(aux1[con-4] == "("){
                                            aux1[con-4] = Integer.toString(contResp);
                                            aux1[con-3] = null;
                                            aux1[con-2] = null;
                                            aux1[con-1] = null;
                                            con = con-3;	
                                        } else {
                                            aux1[con-3] = Integer.toString(contResp);
                                            aux1[con-2] = array[j];
                                            aux1[con-1] = null;
                                            aux1[con]   = null;
                                            con = con-2;	
                                        }
                                    }
                                    bandera++;
                                    contResp++;
                                }
                            } catch (Exception ArrayIndexOutOfBoundsException) {
                                JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                        } else {
                            // posicion anterior es un array
                            try {
                                if(array[j-1] == "¬") {
                                    JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return false;
                                } else if(array[j-2] == "¬") {
                                    //Si el array en el operador 2 tiene una negación 
                                    operador = array[j-1];
                                    if(operador  == "p"){
                                        operador = "pn";
                                    } else if(operador  == "q"){
                                        operador = "qn";
                                    } else if(operador  == "r"){
                                        operador = "rn";
                                    } else {
                                        ope = intArray[Integer.parseInt(operador)];
                                        Result = resolucionOperacionNegacion(ope);

                                        if(Result[0] == null) {
                                            JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }

                                        for(int k = 0; k <  ope.length; k++) {
                                            intArray[Integer.parseInt(operador)][k] = Result[k];
                                        }
                                    }

                                    aux1[con-2] = operador;
                                    aux1[con-1] = array[j];
                                    bandera++;	
                                } else if(array[j-3] == "¬") {
                                    JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return false;
                                } else if(array[j-4] == "¬") {
                                    //Si el array en el operador 1 tiene una negación 
                                    operador = array[j-3];
                                    if(operador  == "p"){
                                        operador = "pn";
                                    } else if(operador  == "q"){
                                        operador = "qn";
                                    } else if(operador  == "r"){
                                        operador = "rn";
                                    } else {
                                        ope = intArray[Integer.parseInt(operador)];
                                        Result = resolucionOperacionNegacion(ope);

                                        if(Result[0] == null) {
                                            JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }

                                        for(int k = 0; k <  ope.length; k++) {
                                            intArray[Integer.parseInt(operador)][k] = Result[k];
                                        }
                                    }

                                    aux1[con-4] = operador;
                                    aux1[con-3] = array[j-2];
                                    aux1[con-2] = array[j-1];
                                    aux1[con-1] = array[j];
                                    bandera++;	
                                } else {
                                    operador1 = array[j-3];
                                    iteracion = array[j-2];
                                    operador2 = array[j-1];

                                    if(iteracion == "↔" || iteracion == "→") {
                                        //validación si se encuentra ↔, revisar si hay una operación antes
                                        if(array[j-4] == "n" || array[j-4] == "u") {
                                            operador1 = array[j-5];
                                            iteracion = array[j-4];
                                            operador2 = array[j-3];
                                        }
                                    }

                                    if(operador1  == "p"){
                                        ope1 = P;
                                    } else if(operador1  == "q"){
                                        ope1 = Q;
                                    } else if(operador1  == "r"){
                                        ope1 = R;
                                    } else if(operador1  == "pn"){
                                        ope1 = PN;
                                    } else if(operador1  == "qn"){
                                        ope1 = QN;
                                    } else if(operador1  == "rn"){
                                        ope1 = RN;
                                    } else {
                                        try {
                                            ope1 = intArray[Integer.parseInt(operador1)];
                                        } catch (Exception NumberFormatException) {
                                            JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }
                                    }

                                    if(operador2  == "p"){
                                        ope2 = P;
                                    } else if(operador2  == "q"){
                                        ope2 = Q;
                                    } else if(operador2  == "r"){
                                        ope2 = R;
                                    } else if(operador2  == "pn"){
                                        ope2  = PN;
                                    } else if(operador2  == "qn"){
                                        ope2  = QN;
                                    } else if(operador2  == "rn"){
                                        ope2  = RN;
                                    } else {
                                        try {
                                            ope2 = intArray[Integer.parseInt(operador2)];
                                        } catch (Exception NumberFormatException) {
                                            JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return false;
                                        }
                                    }

                                    Result = resolucionOperacion(ope1, iteracion, ope2);
                                    if(Result[0] == null) {
                                        JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }

                                    for(int k = 0; k <  ope1.length; k++) {
                                        intArray[contResp][k] = Result[k];
                                    }

                                    if(array[j-2] == "↔" || array[j-2] == "→") {
                                        //validación si se encuentra ↔, revisar si hay una operación antes
                                        if(array[j-4] == "n" || array[j-4] == "u") {
                                            aux1[con-5] = Integer.toString(contResp);
                                            aux1[con-4] = array[j-2];
                                            aux1[con-3] = array[j-1];
                                            aux1[con-2] = array[j];
                                            aux1[con-1] = null;
                                            con = con-1;

                                            bandera2 = 1;
                                        }
                                    }

                                    if(bandera2 == 0) {
                                        if(aux1[con-4] == "("){
                                            aux1[con-4] = Integer.toString(contResp);
                                            aux1[con-3] = null;
                                            aux1[con-2] = null;
                                            aux1[con-1] = null;
                                            con = con-3;	
                                        } else {
                                            aux1[con-3] = Integer.toString(contResp);
                                            aux1[con-2] = array[j];
                                            aux1[con-1] = null;
                                            aux1[con]   = null;
                                            con = con-2;	
                                        }
                                    }
                                    contResp++;
                                    bandera++;    
                                }
                            } catch (Exception ArrayIndexOutOfBoundsException) {
                                JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                        }
                    } else {
                        aux1[con] = pos;
                        con ++;
                    }
                }
            }

            array = aux1;
            imprimirProceso(array);
            for(int z = 0; z < array.length; z++) {
                if(array[z] != null) {
                    //System.out.print(array[z]);
                    contarray++;
                }
            }
        } while(contarray>2);
        
        int resPos = 0;
        
        try {
            resPos = Integer.parseInt(array[0]);
            array_imp = intArray[resPos];
        } catch (NumberFormatException excepcion) {
            if(array[0] == "p") {
                array_imp = P;
            } else if(array[0] == "q") {
                array_imp = Q;
            } else if(array[0] == "r") {
                array_imp = R;
            } else if(array[0] == "pn"){
                array_imp = PN;
            } else if(array[0] == "qn"){
                array_imp = QN;
            } else if(array[0] == "rn"){
                array_imp = RN;
            } else {
                JOptionPane.showMessageDialog(null, "La expresión ingresada es inválida", "Error!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        imprimirTabla(array_imp, 3);

        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        button_paren_dere = new javax.swing.JButton();
        button_n = new javax.swing.JButton();
        button_paren_izqui = new javax.swing.JButton();
        button_u = new javax.swing.JButton();
        button_nega = new javax.swing.JButton();
        button_p = new javax.swing.JButton();
        button_r = new javax.swing.JButton();
        button_q = new javax.swing.JButton();
        textfield_expresion = new javax.swing.JTextField();
        button_result = new javax.swing.JButton();
        button_borrar = new javax.swing.JButton();
        button_ac = new javax.swing.JButton();
        button_bicond = new javax.swing.JButton();
        button_cond = new javax.swing.JButton();
        textfield_texto = new javax.swing.JTextField();
        button_ingresar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_result = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlist_proceso = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        textfield_titulo_proceso = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tablas de verdad");

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)), "Ingreso expresión logica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Nirmala UI", 0, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        button_paren_dere.setBackground(new java.awt.Color(153, 255, 153));
        button_paren_dere.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_paren_dere.setText("(");
        button_paren_dere.setToolTipText("");
        button_paren_dere.setFocusPainted(false);
        button_paren_dere.setFocusable(false);
        button_paren_dere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_paren_dereActionPerformed(evt);
            }
        });

        button_n.setBackground(new java.awt.Color(153, 255, 153));
        button_n.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_n.setText("n");
        button_n.setFocusPainted(false);
        button_n.setFocusable(false);
        button_n.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_nActionPerformed(evt);
            }
        });

        button_paren_izqui.setBackground(new java.awt.Color(153, 255, 153));
        button_paren_izqui.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_paren_izqui.setText(")");
        button_paren_izqui.setFocusPainted(false);
        button_paren_izqui.setFocusable(false);
        button_paren_izqui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_paren_izquiActionPerformed(evt);
            }
        });

        button_u.setBackground(new java.awt.Color(153, 255, 153));
        button_u.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_u.setText("u");
        button_u.setFocusPainted(false);
        button_u.setFocusable(false);
        button_u.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_uActionPerformed(evt);
            }
        });

        button_nega.setBackground(new java.awt.Color(153, 255, 153));
        button_nega.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_nega.setText("¬");
        button_nega.setFocusPainted(false);
        button_nega.setFocusable(false);
        button_nega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_negaActionPerformed(evt);
            }
        });

        button_p.setBackground(new java.awt.Color(153, 255, 153));
        button_p.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_p.setText("p");
        button_p.setFocusPainted(false);
        button_p.setFocusable(false);
        button_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_pActionPerformed(evt);
            }
        });

        button_r.setBackground(new java.awt.Color(153, 255, 153));
        button_r.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_r.setText("r");
        button_r.setFocusPainted(false);
        button_r.setFocusable(false);
        button_r.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_rActionPerformed(evt);
            }
        });

        button_q.setBackground(new java.awt.Color(153, 255, 153));
        button_q.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_q.setText("q");
        button_q.setFocusPainted(false);
        button_q.setFocusable(false);
        button_q.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_qActionPerformed(evt);
            }
        });

        textfield_expresion.setEditable(false);
        textfield_expresion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        button_result.setBackground(new java.awt.Color(153, 255, 153));
        button_result.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_result.setText("Resolver");
        button_result.setFocusPainted(false);
        button_result.setFocusable(false);
        button_result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_resultActionPerformed(evt);
            }
        });

        button_borrar.setBackground(new java.awt.Color(153, 255, 153));
        button_borrar.setText("←");
        button_borrar.setFocusPainted(false);
        button_borrar.setFocusable(false);
        button_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_borrarActionPerformed(evt);
            }
        });

        button_ac.setBackground(new java.awt.Color(153, 255, 153));
        button_ac.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        button_ac.setText("AC");
        button_ac.setFocusPainted(false);
        button_ac.setFocusable(false);
        button_ac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_acActionPerformed(evt);
            }
        });

        button_bicond.setBackground(new java.awt.Color(153, 255, 153));
        button_bicond.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        button_bicond.setText("↔");
        button_bicond.setFocusPainted(false);
        button_bicond.setFocusable(false);
        button_bicond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_bicondActionPerformed(evt);
            }
        });

        button_cond.setBackground(new java.awt.Color(153, 255, 153));
        button_cond.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        button_cond.setText("→");
        button_cond.setFocusPainted(false);
        button_cond.setFocusable(false);
        button_cond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_condActionPerformed(evt);
            }
        });

        button_ingresar.setBackground(new java.awt.Color(153, 255, 153));
        button_ingresar.setText("►");
        button_ingresar.setFocusPainted(false);
        button_ingresar.setFocusable(false);
        button_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ingresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(button_paren_dere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button_cond, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                                    .addComponent(button_n, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(button_bicond, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(button_u, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(button_paren_izqui, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(button_r, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(button_q, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button_p, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(button_nega, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textfield_expresion, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(button_borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button_ac, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(button_result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(textfield_texto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_ingresar, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(textfield_texto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(button_borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(button_ac, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(button_paren_izqui, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_paren_dere, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_p, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(button_n, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_u, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_q, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(button_r, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_bicond, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_cond, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(textfield_expresion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_nega, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)), "Tabla de Verdad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Nirmala UI", 0, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)));

        table_result.setAutoCreateRowSorter(true);
        table_result.setBackground(new java.awt.Color(153, 255, 153));
        table_result.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        table_result.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        table_result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "P", "Q", "R", "Resultado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_result.setToolTipText("");
        table_result.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_result.setFocusable(false);
        table_result.setGridColor(new java.awt.Color(0, 0, 0));
        table_result.setIntercellSpacing(new java.awt.Dimension(1, 6));
        table_result.setMaximumSize(new java.awt.Dimension(2147483647, 140));
        table_result.setRowHeight(22);
        table_result.setSelectionBackground(new java.awt.Color(153, 255, 153));
        jScrollPane1.setViewportView(table_result);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
        );

        jlist_proceso.setBackground(new java.awt.Color(240, 240, 240));
        jlist_proceso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)));
        jlist_proceso.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(jlist_proceso);

        textfield_titulo_proceso.setEditable(false);
        textfield_titulo_proceso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)));
        textfield_titulo_proceso.setText("Proceso");
        textfield_titulo_proceso.setAutoscrolls(false);
        textfield_titulo_proceso.setFocusCycleRoot(false);
        textfield_titulo_proceso.setFocusable(false);
        textfield_titulo_proceso.setMargin(new java.awt.Insets(3, 3, 0, 3));
        jScrollPane2.setViewportView(textfield_titulo_proceso);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel1.setText("CREATED BY: ANDRES FELIPE DELGADO GÓMEZ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_acActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_acActionPerformed
        eliminarAll();
    }//GEN-LAST:event_button_acActionPerformed

    private void button_borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_borrarActionPerformed
        eliminarSimbolo();
    }//GEN-LAST:event_button_borrarActionPerformed

    private void button_resultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_resultActionPerformed
        solucionExpresion(E);
    }//GEN-LAST:event_button_resultActionPerformed

    private void button_qActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_qActionPerformed
        simbolo = "q";
        if(simbolo_pendiente != null) {
            simbolo = simbolo_pendiente+simbolo;
            simbolo_pendiente = null;
        } 
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_qActionPerformed

    private void button_rActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_rActionPerformed
        simbolo = "r";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_rActionPerformed

    private void button_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_pActionPerformed
        simbolo = "p";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_pActionPerformed

    private void button_negaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_negaActionPerformed
        simbolo = "¬";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_negaActionPerformed

    private void button_uActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_uActionPerformed
        simbolo = "u";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_uActionPerformed

    private void button_paren_izquiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_paren_izquiActionPerformed
        simbolo = ")";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_paren_izquiActionPerformed

    private void button_nActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_nActionPerformed
        simbolo = "n";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_nActionPerformed

    private void button_paren_dereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_paren_dereActionPerformed
        simbolo = "(";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_paren_dereActionPerformed

    private void button_condActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_condActionPerformed
        simbolo = "→";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_condActionPerformed

    private void button_bicondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_bicondActionPerformed
        simbolo = "↔";
        agregarSimbolo(simbolo);
    }//GEN-LAST:event_button_bicondActionPerformed

    private void button_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ingresarActionPerformed
        eliminarAll();
        String texto = "";
        texto = textfield_texto.getText();
        
        for (int x = 0; x < texto.length(); x++) {
            caracter = Character.toString(texto.charAt(x));

            if(caracter.equalsIgnoreCase("p")) {
                simbolo = "p";
            } else if(caracter.equalsIgnoreCase("q")) {
                simbolo = "q";
            } else if(caracter.equalsIgnoreCase("r")) {
                simbolo = "r";
            } else if(caracter.equals("n") || caracter.equals("∧")) {
                simbolo = "n";
            } else if(caracter.equals("u") || caracter.equals("∨") || caracter.equals("v")) {
                simbolo = "u";
            } else if(caracter.equals("(") || caracter.equals("[") || caracter.equals("{")) {
                simbolo = "(";
            } else if(caracter.equals(")") || caracter.equals("]") || caracter.equals("}")) {
                simbolo = ")";
            } else if(caracter.equals("¬") || caracter.equals("~") || caracter.equals("-")) {
                simbolo = "¬";
            } else if(caracter.equals("→")) {
                simbolo = "→";
            } else if(caracter.equals("↔")) {
                simbolo = "↔";
            }  else if(caracter.equals(" ")) {
                simbolo = "";
            } else {
                eliminarAll();
                JOptionPane.showMessageDialog(null, "El caracter " + caracter + " es inválido", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!caracter.equals(" ")) {
                agregarSimbolo(simbolo);
            }
        }
    }//GEN-LAST:event_button_ingresarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventanaTablasVerdad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaTablasVerdad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaTablasVerdad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaTablasVerdad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaTablasVerdad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_ac;
    private javax.swing.JButton button_bicond;
    private javax.swing.JButton button_borrar;
    private javax.swing.JButton button_cond;
    private javax.swing.JButton button_ingresar;
    private javax.swing.JButton button_n;
    private javax.swing.JButton button_nega;
    private javax.swing.JButton button_p;
    private javax.swing.JButton button_paren_dere;
    private javax.swing.JButton button_paren_izqui;
    private javax.swing.JButton button_q;
    private javax.swing.JButton button_r;
    private javax.swing.JButton button_result;
    private javax.swing.JButton button_u;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> jlist_proceso;
    private javax.swing.JTable table_result;
    private javax.swing.JTextField textfield_expresion;
    private javax.swing.JTextField textfield_texto;
    private javax.swing.JTextPane textfield_titulo_proceso;
    // End of variables declaration//GEN-END:variables
}
