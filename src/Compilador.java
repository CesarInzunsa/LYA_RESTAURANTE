
import ManejarArchivos.Directorio;
import VentanasSecundarias.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.CodeBlock;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static ManejarArchivos.ManejoArchivos.*;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;

public class Compilador extends javax.swing.JFrame {

    public String fuente;
    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textsColor;
    private Timer timerKeyReleased;
    private ArrayList<Production> identProd;
    private HashMap<String, String> identificadores;
    private boolean codeHasBeenCompiled = false;
    private String erroresLexico = "";

    private Directorio dir;

    /*
    UndoManager manager;
    Action undoAction;
    Action redoAction;
     */
    VentanaAutomata ventAutomata = new VentanaAutomata(this, true);
    AcercaDe acercaDe = new AcercaDe(this, true);
    ComponentesLexicos componentesLexicos = new ComponentesLexicos();
    GramaticaTotal gramaticaTotal = new GramaticaTotal();
    GramaticaUtilizada gramaticaUtilizada = new GramaticaUtilizada();
    //Configuraciones configuracion = new Configuraciones();

    AnalisisSintactico analisisSintactico;

    ArbolesDeExpresion analisisSemanticoArbolExpresion;
    AnalisisSemantico analisisSemantico;

    GeneradorCodigoIntermedio generadorCodigoIntermedio;
    OptimizadorCodigo optimizadorCodigo;
    GeneradorCodigoObjeto generadorCodigoObjeto;

    public Compilador() {
        initComponents();
        init();
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/logoRestaurante.png"));
        return retValue;
    }

    private void init() {

        //Pantalla completa
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        title = "Restaurante Automata";
        setLocationRelativeTo(null);
        setTitle(title);
        dir = new Directorio(this, txtSource, title, ".comp");
        //title = dir.getTitulo();
        directorio = new Directory(this, txtSource, title, ".comp");
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        Functions.setLineNumberOnJTextComponent(txtSource); //Pone los numeros de linea
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();

            int posicion = txtSource.getCaretPosition();
            txtSource.setText(txtSource.getText().replaceAll("[\r]+", ""));
            txtSource.setCaretPosition(posicion);

            colorAnalysis();

        });
        Functions.insertAsteriskInName(this, txtSource, () -> {
            timerKeyReleased.restart();
        });
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        textsColor = new ArrayList<>();
        identProd = new ArrayList<>();
        identificadores = new HashMap<>();
        Functions.setAutocompleterJTextComponent(new String[]{
            "iluminarcamino", "declararmenu", "verofertas", "vermenu", "realizarpedido",
            "solicitarmesero", "vermesa", "estadomesa", "tiempopedido", "for", "while", "if", "var",
            "print", "horaDelDia", "tipoComida", "numeroMesa", "pedido", "main", "prepararmesa",
            "numeroAsientos", "estado", "vegetariano", "regular", "pesqueteriano", "true", "false",
            "mañana", "tarde", "noche"}, txtSource, () -> {
            timerKeyReleased.restart();
        });

        /*
        manager = new UndoManager();
        txtSource.getDocument().addUndoableEditListener(manager);
        undoAction = new UndoAction(manager);
        redoAction = new RedoAction(manager);

        txtSource.registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
        txtSource.registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
         */
        //jTableCodigoIntermedio.setBounds(30, 40, 200, 300);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSource = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOutputConsole = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextAreaSemantico = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCodigoIntermedio = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableCodigoOptimizado = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextCodigoObjeto = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnNuevo = new javax.swing.JButton();
        btnAbrir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarC = new javax.swing.JButton();
        letrafuente = new javax.swing.JComboBox<>();
        btnConstruir = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        jButtonCargarCodigoPrueba1 = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(1200, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(714, 500));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(714, 301));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(714, 301));

        txtSource.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSource.setMaximumSize(new java.awt.Dimension(714, 500));
        txtSource.setMinimumSize(new java.awt.Dimension(714, 301));
        txtSource.setName(""); // NOI18N
        jScrollPane1.setViewportView(txtSource);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(714, 199));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(714, 199));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(714, 199));

        txtOutputConsole.setEditable(false);
        txtOutputConsole.setMaximumSize(new java.awt.Dimension(714, 199));
        txtOutputConsole.setMinimumSize(new java.awt.Dimension(714, 199));
        jScrollPane2.setViewportView(txtOutputConsole);

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(452, 554));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(452, 554));

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componente léxico", "Lexema", "[Línea, Columna]"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTokens.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblTokens);

        jTabbedPane1.addTab("Componentes Lexicos", jScrollPane3);

        jTextAreaSemantico.setEditable(false);
        jTextAreaSemantico.setColumns(20);
        jTextAreaSemantico.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jTextAreaSemantico.setRows(5);
        jScrollPane7.setViewportView(jTextAreaSemantico);

        jTabbedPane1.addTab("Analisis semantico", jScrollPane7);

        jTableCodigoIntermedio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTableCodigoIntermedio);

        jTabbedPane1.addTab("Codigo intermedio", jScrollPane4);

        jTableCodigoOptimizado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTableCodigoOptimizado);

        jTabbedPane1.addTab("Codigo optimizado", jScrollPane5);

        jTextCodigoObjeto.setColumns(20);
        jTextCodigoObjeto.setRows(5);
        jTextCodigoObjeto.setEnabled(false);
        jScrollPane6.setViewportView(jTextCodigoObjeto);

        jTabbedPane1.addTab("Codigo objeto", jScrollPane6);

        jToolBar1.setRollover(true);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGrandeNuevo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNuevo);

        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGrandeAbrir.png"))); // NOI18N
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAbrir);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGrandeGuardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardar);

        btnGuardarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGrandeGuardarComo.png"))); // NOI18N
        btnGuardarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardarC);

        letrafuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12", "14", "16", "18", "20", "22", "24" }));
        letrafuente.setMaximumSize(new java.awt.Dimension(50, 30));
        letrafuente.setMinimumSize(new java.awt.Dimension(50, 30));
        letrafuente.setName(""); // NOI18N
        letrafuente.setPreferredSize(new java.awt.Dimension(50, 30));
        letrafuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letrafuenteActionPerformed(evt);
            }
        });
        jToolBar1.add(letrafuente);

        btnConstruir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgConstruir.png"))); // NOI18N
        btnConstruir.setMaximumSize(new java.awt.Dimension(37, 37));
        btnConstruir.setMinimumSize(new java.awt.Dimension(37, 37));
        btnConstruir.setPreferredSize(new java.awt.Dimension(37, 37));
        btnConstruir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConstruirActionPerformed(evt);
            }
        });
        jToolBar1.add(btnConstruir);

        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGrandeCompilar.png"))); // NOI18N
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompilar);

        jButtonCargarCodigoPrueba1.setText("Codigo de prueba1");
        jButtonCargarCodigoPrueba1.setFocusable(false);
        jButtonCargarCodigoPrueba1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonCargarCodigoPrueba1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonCargarCodigoPrueba1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarCodigoPrueba1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonCargarCodigoPrueba1);

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(rootPanel);

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgNuevo.png"))); // NOI18N
        jMenuItem1.setText("Nuevo archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgAbrir.png"))); // NOI18N
        jMenuItem2.setText("Abrir archivo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGuardar.png"))); // NOI18N
        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgGuardarComo.png"))); // NOI18N
        jMenuItem4.setText("Guardar como");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator2);

        jMenuItem5.setText("Salir");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        barraMenu.add(jMenu1);

        jMenu5.setText("Run");

        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgCompilar.png"))); // NOI18N
        jMenuItem19.setText("Compilar");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgConstruir.png"))); // NOI18N
        jMenuItem20.setText("Construir");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem20);

        barraMenu.add(jMenu5);

        jMenu3.setText("Opciones");

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem11.setText("Componentes lexicos");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem14.setText("Automata");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem14);

        jMenu6.setText("Cambiar tamaño de la fuente");

        jMenuItem21.setText("12");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem21);

        jMenuItem22.setText("14");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem22);

        jMenuItem23.setText("16");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem23);

        jMenuItem24.setText("18");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem24);

        jMenuItem25.setText("20");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem25);

        jMenuItem26.setText("22");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem26);

        jMenuItem27.setText("24");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem27);

        jMenu3.add(jMenu6);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem6.setText("Gramatica total");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem7.setText("Gramatica utilizada");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        barraMenu.add(jMenu3);

        jMenu4.setText("Ayuda");

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgAyuda.png"))); // NOI18N
        jMenuItem15.setText("Documentación online y soporte");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/imgReportarError.png"))); // NOI18N
        jMenuItem16.setText("Reportar un error");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem16);

        jMenuItem17.setText("Acerca de");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem17);

        barraMenu.add(jMenu4);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        directorio.New();
        clearFields();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        if (directorio.Open()) {
            colorAnalysis();
            clearFields();
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (directorio.Save()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCActionPerformed
        if (directorio.SaveAs()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarCActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
            }
        } else {
            compile();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void btnConstruirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstruirActionPerformed
        /*
        btnCompilar.doClick();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede ejecutar el código ya que se encontró uno o más errores",
                        "Error en la compilación", JOptionPane.ERROR_MESSAGE);
            } else {
                CodeBlock codeBlock = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";");
                System.out.println(codeBlock);
                ArrayList<String> blocksOfCode = codeBlock.getBlocksOfCodeInOrderOfExec();
                System.out.println(blocksOfCode);

            }
        }
         */
    }//GEN-LAST:event_btnConstruirActionPerformed

    private void letrafuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_letrafuenteActionPerformed
        int indice = letrafuente.getSelectedIndex();
        cambiarTamFuente(indice, Integer.parseInt(letrafuente.getItemAt(indice)));
    }//GEN-LAST:event_letrafuenteActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        directorio.New();
        clearFields();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (directorio.Open()) {
            colorAnalysis();
            clearFields();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (directorio.Save()) {
            clearFields();
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (directorio.SaveAs()) {
            clearFields();
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://sites.google.com/ittepic.edu.mx/restaurante-automata/p%C3%A1gina-principal"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
            }
        } else {
            compile();
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        btnCompilar.doClick();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede ejecutar el código ya que se encontró uno o más errores",
                        "Error en la compilación", JOptionPane.ERROR_MESSAGE);
            } else {
                CodeBlock codeBlock = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";");
                System.out.println(codeBlock);
                ArrayList<String> blocksOfCode = codeBlock.getBlocksOfCodeInOrderOfExec();
                System.out.println(blocksOfCode);

            }
        }
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://forms.gle/TgFzdoDa4Rg2NT3z9"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        acercaDe.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        cambiarTamFuente(6, 24);
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        cambiarTamFuente(5, 22);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        cambiarTamFuente(4, 20);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        cambiarTamFuente(3, 18);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        cambiarTamFuente(2, 16);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        cambiarTamFuente(1, 14);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        cambiarTamFuente(0, 12);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        //Automata
        ventAutomata.setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        componentesLexicos.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        gramaticaTotal.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        gramaticaUtilizada.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jButtonCargarCodigoPrueba1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarCodigoPrueba1ActionPerformed
        // Codigo de prueba1
        String codigoPrueba = System.getProperty("user.dir") + "\\codigoPruebas\\prueba1.comp";

        if (dir.Open(codigoPrueba)) {
            colorAnalysis();
            clearFields();
        }

    }//GEN-LAST:event_jButtonCargarCodigoPrueba1ActionPerformed

    private void compile() {
        clearFields();
        lexicalAnalysis();
        fillTableTokens();
        syntacticAnalysis();
        semanticAnalysis();
        intermediateCodeGenerate();
        codeOptimization();
        codeGeneration();

        //printConsole();
        generarArchivos();
        codeHasBeenCompiled = true;
    }

    private void generarArchivos() {
        //La ruta donde guardar los archivos, en este caso lo hace en una carpeta del proyecto
        String ruta = "archivos";

        crearArchivo(ruta + "\\codigoFuente.comp");
        crearArchivo(ruta + "\\analisisLexico.csv");
        crearArchivo(ruta + "\\analisisSintactico.txt");
        crearArchivo(ruta + "\\analisisSemantico.txt");
        crearArchivo(ruta + "\\codigoIntermedio.csv");
        crearArchivo(ruta + "\\codigoOptimizado.csv");
        crearArchivo(ruta + "\\codigoObjeto.ino");

        //Lenguaje alto nivel (el del proyecto)
        escribirArchivo(ruta + "\\codigoFuente.comp", txtSource.getText());
        //lexico
        escribirArchivo(ruta + "\\analisisLexico.csv", obtenerLexicoTabla()); //Separado por %
        //sintactico
        escribirArchivo(ruta + "\\analisisSintactico.txt", gramaticaUtilizada.getGramaticaUtilizada());
        //semantico
        escribirArchivo(ruta + "\\analisisSemantico.txt", jTextAreaSemantico.getText());
        //codigo intermedio
        escribirArchivo(ruta + "\\codigoIntermedio.csv", obtenerCuadruplosTablaIntermedio());
        //codigo optimizado
        escribirArchivo(ruta + "\\codigoOptimizado.csv", obtenerCuadruplosTablaOptimizado());
        //codigo objeto
        escribirArchivo(ruta + "\\codigoObjeto.ino", jTextCodigoObjeto.getText());
    }

    private String obtenerLexicoTabla() {
        String cad = "Componente lexico%Lexema%Linea/Columna\n";
        for (Token token : tokens) {
            cad += token.getLexicalComp() + "%" + token.getLexeme() + "%[" + token.getLine() + "/" + token.getColumn() + "]\n";
        }
        return cad;
    }

    private String obtenerCuadruplosTablaIntermedio() {
        String cad = jTableCodigoIntermedio.getColumnName(0) + "," + jTableCodigoIntermedio.getColumnName(1) + "," + jTableCodigoIntermedio.getColumnName(2) + "," + jTableCodigoIntermedio.getColumnName(3) + "," + jTableCodigoIntermedio.getColumnName(4) + "\n";
        for (int i = 0; i < jTableCodigoIntermedio.getRowCount(); i++) {
            cad += jTableCodigoIntermedio.getValueAt(i, 0) + "," + jTableCodigoIntermedio.getValueAt(i, 1) + "," + jTableCodigoIntermedio.getValueAt(i, 2) + "," + jTableCodigoIntermedio.getValueAt(i, 3) + "," + jTableCodigoIntermedio.getValueAt(i, 4) + "\n";
        }
        return cad;
    }

    private String obtenerCuadruplosTablaOptimizado() {
        String cad = jTableCodigoOptimizado.getColumnName(0) + "," + jTableCodigoOptimizado.getColumnName(1) + "," + jTableCodigoOptimizado.getColumnName(2) + "," + jTableCodigoOptimizado.getColumnName(3) + "," + jTableCodigoOptimizado.getColumnName(4) + "\n";
        for (int i = 0; i < jTableCodigoOptimizado.getRowCount(); i++) {
            cad += jTableCodigoOptimizado.getValueAt(i, 0) + "," + jTableCodigoOptimizado.getValueAt(i, 1) + "," + jTableCodigoOptimizado.getValueAt(i, 2) + "," + jTableCodigoOptimizado.getValueAt(i, 3) + "," + jTableCodigoOptimizado.getValueAt(i, 4) + "\n";
        }
        return cad;
    }

    public void setGramaticaUtilizada(String gramaticausada) {
        gramaticaUtilizada.setGramaticaUtilizada(gramaticausada + "/n");
    }

    private void cambiarTamFuente(int indice, int tamFuente) {

        letrafuente.setSelectedIndex(indice);
        txtSource.setFont(new Font(txtSource.getFont().getName(), Font.PLAIN, tamFuente));
        txtOutputConsole.setFont(new Font(txtOutputConsole.getFont().getName(), Font.PLAIN, tamFuente));

    }

    private void lexicalAnalysis() {
        // Extraer tokens
        Lexer lexer;
        try {
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtSource.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }

        if (hayErrores()) {
            erroresLexico = obtenerErroresLexico();
            String txt = txtOutputConsole.getText();
            txtOutputConsole.setText(txt + "Analisis lexico:\n" + erroresLexico);
        }

    }

    private String obtenerErroresLexico() {
        String errores = "";
        for (Token token : tokens) {
            if (token.getLexicalComp().equals("ERROR_LEX")) {
                errores += mensajeErrorLexico(token);
            }
        }
        return errores;
    }

    /**
     * @return true si hay un error en el lexico
     */
    private boolean hayErrores() {

        for (Token token : tokens) {
            if (token.getLexicalComp().equals("ERROR_LEX")) {
                return true;
            }
        }

        return false;

    }

    private String mensajeErrorLexico(Token token) {
        return "ERROR EN: [ " + token.getLine() + ", " + token.getColumn() + " ]; El token : [" + token.getLexeme() + "] no pertenece al lenguaje.\n";
    }

    private void syntacticAnalysis() {

        analisisSintactico = new AnalisisSintactico(tokens, ventAutomata, this, txtOutputConsole.getText());
        analisisSintactico.realizarAnalisisSintactico();

    }

    private void semanticAnalysis() {

        //analisisSemanticoArbolExpresion = new ArbolesDeExpresion(tokens, this);
        analisisSemantico = new AnalisisSemantico(tokens, this, analisisSintactico.getErroresSintactico());
        analisisSemantico.realizarAnalisisSemantico();
    }

    private void intermediateCodeGenerate() {
        generadorCodigoIntermedio = new GeneradorCodigoIntermedio(tokens, this, txtOutputConsole.getText());
        generadorCodigoIntermedio.realizarCodigoIntermedio();

    }

    private void codeOptimization() {
        optimizadorCodigo = new OptimizadorCodigo(tokens, this, txtOutputConsole.getText());
        optimizadorCodigo.realizarOptimizacionCodigo();
    }

    private void codeGeneration() {
        generadorCodigoObjeto = new GeneradorCodigoObjeto(tokens, this, txtOutputConsole.getText());
        generadorCodigoObjeto.realizarCodigoObjeto();
    }

    private void colorAnalysis() {
        /* Limpiar el arreglo de colores */
        textsColor.clear();
        /* Extraer rangos de colores */
        LexerColor lexerColor;
        try {
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtSource.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexerColor = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexerColor.yylex();
                if (textColor == null) {
                    break;
                }
                textsColor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        Functions.colorTextPane(textsColor, txtSource, new Color(40, 40, 40));
    }

    private void fillTableTokens() {
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(tblTokens, data);
            Functions.addRowDataInTable(componentesLexicos.getTablaTokens(), data);
        });
    }

    private void printConsole() {

        DefaultCaret caret = new DefaultCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane2.setAutoscrolls(true);

        txtOutputConsole.setCaret(caret);

        String txt = txtOutputConsole.getText();
        txtOutputConsole.setText("");
        txtOutputConsole.setText(txt);
    }

    private void printConsolePrueba() {
        int sizeErrors = errors.size();
        if (sizeErrors > 0) {
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            for (ErrorLSSL error : errors) {
                String strError = String.valueOf(error);
                strErrors += strError + "\n";
            }
            txtOutputConsole.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
        } else {
            txtOutputConsole.setText("Compilación terminada...");
        }
        txtOutputConsole.setCaretPosition(0);
    }

    private void clearFields() {
        Functions.clearDataInTable(tblTokens);
        Functions.clearDataInTable(componentesLexicos.getTablaTokens());
        txtOutputConsole.setText("");
        tokens.clear();
        errors.clear();
        identProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;
        if (gramaticaUtilizada.getGramaticaUtilizada().length() > 0) {
            gramaticaUtilizada.setGramaticaUtilizada("");
        }
    }

    public String getTxtOutputConsole() {
        return txtOutputConsole.getText();
    }

    public void setTxtOutputConsole(String txt) {
        this.txtOutputConsole.setText(txt);
    }

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
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                System.out.println("LookAndFeel no soportado: " + ex);
            }
            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnConstruir;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarC;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton jButtonCargarCodigoPrueba1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    protected javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTableCodigoIntermedio;
    protected javax.swing.JTable jTableCodigoOptimizado;
    public javax.swing.JTextArea jTextAreaSemantico;
    protected javax.swing.JTextArea jTextCodigoObjeto;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JComboBox<String> letrafuente;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JTable tblTokens;
    protected javax.swing.JTextArea txtOutputConsole;
    private javax.swing.JTextPane txtSource;
    // End of variables declaration//GEN-END:variables
}
