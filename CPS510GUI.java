import com.sun.javafx.scene.traversal.ContainerTabOrder;
import org.omg.CORBA.DATA_CONVERSION;


import java.awt.*;
import java.awt.event.*;
import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class CPS510GUI implements ActionListener, WindowListener {

    String selectedTable;
    SQLDATA sqldata;
    static JFrame frame;
    JPanel macroCommandPanel;
    JLabel macro;
    JButton btnCreateAllTables;
    JButton btnDropAllTables;
    JButton btnInsertDummyData;
    JButton btnRunAllQueries;
    JButton btnShowTableNames;

    JButton btnSearchGames;

    FlowLayout layout;
    FlowLayout jframeLayout;

    JTable table;
    Vector<Object> tableData;
    Vector<Object> colNames;
    Connection connection;
    DefaultTableModel tableModel;

    JButton btnBackToWelcome;
    JPanel variableCommandPanel;

    JPanel weclomePanel;
    JLabel welcomeLabel;

    JButton btnSearchInDevs;
    JTextField tfdSearchInGames;
    JTextField tfdSearchInDevs;

    JPanel tableSelectedPanel;
    JLabel tableSelectedLabel;
    JButton btnDropSelected;

    JButton btnShowSelectedTableData;

    JPanel tableDataShowingOptionsPanel;
    JLabel lblTableTitle;
    JButton btnDeleteRow;
    JButton btnUpdateElement;


    final static String WELCOMEPANEL = "welcome";
    final static String TABLELISTPANEL = "tablelist";
    final static String TABLEDATAOPTIONSPANEL= "tableoptions";
    final static String RUNQUERIES = "runqueries";

    static int tableMode = 0;
    static int welcomeViewMode = 0;
    static int tableViewMode = 1;
    static int dataViewMode = 2;

    JPanel pnlRunQueries;
    JLabel lblRunQueries;
    JButton btnNextQuery;
    JTextArea txtQueryRun;
    JLabel lblQueryRun;

    int selectedRow = 0;
    int selectedCol = 0;

    CardLayout customPanelLayout;
    JPanel bigPanel;
    JPanel tablePanel;

    JButton getBtnBackToWelcome;

    //static volatile boolean goToNext = true;
    static int nextQuery = 0;

    public void addComponentToPane(Container pane){
        sqldata = new SQLDATA();
        try {
            this.connection = OracleConnection.connect();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        /*
         * Macro commands
         * */

        this.tableData = new Vector<>();
        this.colNames = new Vector<>();
        colNames.addElement("Welcome");
        layout = new FlowLayout();
        jframeLayout = new FlowLayout();

        this.macroCommandPanel = new JPanel();
        this.macroCommandPanel.setLayout(layout);
        this.macroCommandPanel.setBackground(Color.WHITE);
        this.macroCommandPanel.setPreferredSize(new Dimension(200, 500));

        this.macro = new JLabel("Macro Commands");
        this.macroCommandPanel.add(macro);
        this.btnCreateAllTables = new JButton("Create Tables");
        this.btnCreateAllTables.addActionListener(this);
        this.macroCommandPanel.add(btnCreateAllTables);

        this.btnDropAllTables = new JButton("Drop Tables");
        this.btnDropAllTables.addActionListener(this);
        this.macroCommandPanel.add(btnDropAllTables);

        this.btnInsertDummyData = new JButton("Insert Dummy Data");
        this.btnInsertDummyData.addActionListener(this);
        this.macroCommandPanel.add(btnInsertDummyData);

        this.btnRunAllQueries = new JButton("Run Queries");
        this.btnRunAllQueries.addActionListener(this);
        this.macroCommandPanel.add(btnRunAllQueries);

        this.btnShowTableNames = new JButton("Show Table Names");
        this.btnShowTableNames.addActionListener(this);
        this.macroCommandPanel.add(btnShowTableNames);

        btnBackToWelcome = new JButton("Back to welcome");
        btnBackToWelcome.addActionListener(this);
        this.macroCommandPanel.add(btnBackToWelcome);

        customPanelLayout = new CardLayout();
        this.variableCommandPanel = new JPanel(customPanelLayout);
        this.variableCommandPanel.setPreferredSize(new Dimension(200, 500));

        weclomePanel = new JPanel(new FlowLayout());
        weclomePanel.setPreferredSize(new Dimension(150, 500));
        welcomeLabel = new JLabel("Welcome");
        tfdSearchInGames = new JTextField("Search GAMES");
        btnSearchGames = new JButton("Search Games");
        btnSearchGames.addActionListener(this);
        tfdSearchInDevs = new JTextField("Search Devs");
        btnSearchInDevs = new JButton("Search Developers");
        btnSearchInDevs.addActionListener(this);

        weclomePanel.add(welcomeLabel);
        weclomePanel.add(tfdSearchInGames);
        weclomePanel.add(btnSearchGames);
        weclomePanel.add(tfdSearchInDevs);
        weclomePanel.add(btnSearchInDevs);

        tableSelectedPanel = new JPanel(new FlowLayout());
        tableSelectedPanel.setPreferredSize(new Dimension(200, 500));
        tableSelectedLabel = new JLabel("TABLES");
        btnDropSelected = new JButton("DROP SELECTED TABLE");
        btnDropSelected.addActionListener(this);

        btnShowSelectedTableData = new JButton("Show table data");
        btnShowSelectedTableData.addActionListener(this);

        tableSelectedPanel.add(tableSelectedLabel);
        tableSelectedPanel.add(btnDropSelected);

        tableSelectedPanel.add(btnShowSelectedTableData);

        tableDataShowingOptionsPanel = new JPanel(new FlowLayout());
        btnDeleteRow = new JButton("Delete Row");
        btnDeleteRow.addActionListener(this);
        btnUpdateElement = new JButton("Update element");
        btnUpdateElement.addActionListener(this);
        lblTableTitle = new JLabel("Table");

        tableDataShowingOptionsPanel.add(lblTableTitle);
        tableDataShowingOptionsPanel.add(btnDeleteRow);
        tableDataShowingOptionsPanel.add(btnUpdateElement);





        pnlRunQueries = new JPanel(new FlowLayout());
        lblRunQueries = new JLabel("Running Queries");
        btnNextQuery = new JButton("Next Query");
        btnNextQuery.addActionListener(this);
        txtQueryRun = new JTextArea("Query Run");
        lblQueryRun = new JLabel("Query Run");

        pnlRunQueries.add(lblRunQueries);
        pnlRunQueries.add(btnNextQuery);
        pnlRunQueries.add(lblQueryRun);
        pnlRunQueries.add(txtQueryRun);


        this.table = new JTable(new DefaultTableModel(this.tableData, this.colNames));
        this.tableModel = (DefaultTableModel)this.table.getModel();
        this.table.setBounds(30,40,200,300);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane sp = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.table.setCellSelectionEnabled(true);
        ListSelectionModel select= this.table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        select.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String Data = null;
                int[] row = table.getSelectedRows();
                int[] columns = table.getSelectedColumns();
                if (row.length > 0 && tableMode == tableViewMode) {
                    Data = table.getValueAt(row[0], columns[0]).toString();
                    System.out.println("Table element selected is: " + Data);
                    tableSelectedLabel.setText(Data);
                    btnShowSelectedTableData.setEnabled(true);
                    btnDropSelected.setEnabled(true);

                    tableSelectedPanel.update(tableSelectedPanel.getGraphics());
                    selectedTable = Data;
                }else if(row.length > 0 && tableMode == dataViewMode){
                    selectedRow = row[0];
                    selectedCol = columns[0];
                    System.out.println(selectedCol);
                    Data = table.getValueAt(row[0], columns[0]).toString();
                    System.out.println(Data);
                }
            }
        });


        //  Container cp = getContentPane();
        //cp.setLayout(new BorderLayout());
        bigPanel = new JPanel();
        tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        tablePanel.add(sp);
        bigPanel.setPreferredSize(new Dimension(500, 500));
        variableCommandPanel.add(weclomePanel, WELCOMEPANEL);
        variableCommandPanel.add(tableSelectedPanel, TABLELISTPANEL);
        variableCommandPanel.add(tableDataShowingOptionsPanel, TABLEDATAOPTIONSPANEL);
        variableCommandPanel.add(pnlRunQueries, RUNQUERIES);
        bigPanel.add(macroCommandPanel);
        bigPanel.add(variableCommandPanel);

        pane.add(tablePanel, BorderLayout.CENTER);
        pane.add(bigPanel, BorderLayout.LINE_END);

        //adds the drawcanvas and JPanel to the Container
        //cp.add(macroCommandPanel, BorderLayout.EAST); //right of container
        //cp.add(userCommandPanel, BorderLayout.WEST);
    }

    public static void createGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        CPS510GUI testing = new CPS510GUI();
        testing.addComponentToPane(frame.getContentPane());
        frame.setTitle("ONLINE GAME STORE DBMS");
        // pack all the components
        frame.pack();
        frame.setVisible(true);
    }

    public void adjustColumnWidths(){
        // taken from https://tips4java.wordpress.com/2008/11/10/table-column-adjuster/
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

        for (int column = 0; column < table.getColumnCount(); column++)
        {

            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getPreferredWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth( preferredWidth);
        }
    }


    public void actionPerformed(ActionEvent actionEvent){
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        String[] name;
        try (Statement stmt = connection.createStatement()) {
            if(actionEvent.getSource() == btnDropAllTables) {
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                this.colNames.addElement("Drop Messages");
                tableMode = welcomeViewMode;
                for(String drop: this.sqldata.dropArray){
                    Vector row = new Vector(1);
                    name = drop.split(" ");
                    try {
                        stmt.executeUpdate(drop);
                        row.addElement("Table " + name[2] + " dropped successfully");
                    }catch (SQLSyntaxErrorException e){
                        row.addElement(e.toString().substring(34));
                    }
                    this.tableData.addElement(row);
                }
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
            }else if(actionEvent.getSource() == btnCreateAllTables) {
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                tableMode = welcomeViewMode;
                this.customPanelLayout.show(this.variableCommandPanel, WELCOMEPANEL);
                this.colNames.addElement("Create Messages");
                for(String create: this.sqldata.createsArray){
                    Vector row = new Vector(1);
                    name = create.split(" ");
                    try {
                        stmt.executeUpdate(create);
                        row.addElement("Table " + name[2] + " created successfully");
                    }catch(SQLSyntaxErrorException e){
                        row.addElement(e.toString().substring(34));
                        //System.out.println(e.toString().substring(34));
                    }
                    this.tableData.addElement(row);

                }
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
            }else if(actionEvent.getSource() == btnInsertDummyData){
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                tableMode = welcomeViewMode;
                colNames.addElement("Insert Messages");
                for(String insert: this.sqldata.inserts){
                    Vector row = new Vector();
                    try {
                        stmt.executeUpdate(insert);
                        row.addElement("Insert successfully");
                    }catch(SQLException e){
                        row.addElement(e.toString().substring(51));
                        System.out.println(e.toString().substring(51));
                    }
                    tableData.addElement(row);
                }
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
            }else if(actionEvent.getSource() == btnRunAllQueries){
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                String query = this.sqldata.selects[nextQuery];
                tableMode = welcomeViewMode;
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();
                int rows = this.tableModel.getRowCount();
                for (int i = rows - 1; i >= 0; i--) {
                    this.tableModel.removeRow(i);
                }
                for (int i = 1; i <= cols; i++) {
                    this.colNames.addElement(md.getColumnName(i));
                }
                while (rs.next()) {
                    Vector row = new Vector(cols);
                    for (int i = 1; i <= cols; i++) {
                        row.addElement(rs.getObject(i));
                    }
                    this.tableData.addElement(row);
                }
                //this.table.setRowSelectionAllowed(true);
                //this.table.setCellSelectionEnabled(false);
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
              //  this.adjustColumnWidths();
                this.customPanelLayout.show(this.variableCommandPanel, RUNQUERIES);
                txtQueryRun.setText(query);
                txtQueryRun.setPreferredSize(new Dimension(150, 400));
                txtQueryRun.setLineWrap(true);
                pnlRunQueries.update(pnlRunQueries.getGraphics());
            }else if(actionEvent.getSource() == btnShowTableNames){
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                tableMode = tableViewMode;
                this.runShowAllTables(stmt);
                this.customPanelLayout.show(this.variableCommandPanel, TABLELISTPANEL);
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
            }else if(actionEvent.getSource() == btnDropSelected){
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                Vector row = new Vector(1);
                this.colNames.addElement("Drop messages");
                for(String drop: this.sqldata.dropArray){
                    if(drop.contains(selectedTable)){
                        try {
                            stmt.executeUpdate(drop);
                            row.addElement("Table "  + selectedTable + " dropped successfully");
                            break;
                        }catch (SQLException e){
                            row.addElement(e.toString().substring(23));
                        }
                    }
                }
                btnDropSelected.setEnabled(false);
                btnShowSelectedTableData.setEnabled(false);

                tableSelectedPanel.update(tableSelectedPanel.getGraphics());
                this.tableData.addElement(row);
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
            }else if(actionEvent.getSource() == btnShowSelectedTableData){
                selectedRow = 0;
                System.out.println("test");
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                tableMode = dataViewMode;
                this.customPanelLayout.show(variableCommandPanel, TABLEDATAOPTIONSPANEL);
                String query = "SELECT * FROM " + selectedTable;
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();
                int rows = this.tableModel.getRowCount();
                for(int i = rows - 1; i >= 0; i--){
                    this.tableModel.removeRow(i);
                }
                for(int i = 1; i <= cols; i++){
                    this.colNames.addElement(md.getColumnName(i));
                }
                while(rs.next()) {
                    Vector row = new Vector(cols);
                    for(int i = 1; i <= cols; i++){
                        row.addElement(rs.getObject(i));
                    }
                    this.tableData.addElement(row);
                }
                //this.table.setRowSelectionAllowed(true);
                //this.table.setCellSelectionEnabled(false);
                lblTableTitle.setText(selectedTable);
                tableDataShowingOptionsPanel.update(tableDataShowingOptionsPanel.getGraphics());
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
                this.adjustColumnWidths();
            }else if(actionEvent.getSource() == btnDeleteRow){
                String title = (String)this.colNames.get(0);
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                String delete = "DELETE FROM " + selectedTable + " WHERE " + title + " = "
                        + this.table.getValueAt(selectedRow, 0);
                try {
                    stmt.executeUpdate(delete);
                }catch (SQLException e){
                    System.out.println(e);
                }
                btnShowSelectedTableData.doClick();
            }else if(actionEvent.getSource() == btnUpdateElement){
                String value = this.table.getValueAt(selectedRow, selectedCol).toString();
                System.out.println(value);
                String query = "UPDATE " + selectedTable + " SET " + this.colNames.get(selectedCol) +
                        " = '" + value + "' WHERE " + this.colNames.get(0) + " = " + this.table.getValueAt(selectedRow, 0).toString();
                System.out.println(query);
                try{
                    stmt.executeUpdate(query);
                }catch (Exception e){
                    System.out.println(e);
                }
                btnShowSelectedTableData.doClick();
            }else if(actionEvent.getSource() == btnNextQuery) {
                nextQuery += 1;
                if(nextQuery >= this.sqldata.selects.length){
                    nextQuery = 0;
                }
                btnRunAllQueries.doClick();
            }else if(actionEvent.getSource() == btnBackToWelcome){
                this.customPanelLayout.show(this.variableCommandPanel, WELCOMEPANEL);
                tableMode = welcomeViewMode;
            }else if(actionEvent.getSource() == btnSearchInDevs){
                String query = "SELECT * FROM DEVELOPERS WHERE name LIKE '%" + tfdSearchInDevs.getText() + "%'";
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                tableMode = welcomeViewMode;
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();
                int rows = this.tableModel.getRowCount();
                for (int i = rows - 1; i >= 0; i--) {
                    this.tableModel.removeRow(i);
                }
                for (int i = 1; i <= cols; i++) {
                    this.colNames.addElement(md.getColumnName(i));
                }
                while (rs.next()) {
                    Vector row = new Vector(cols);
                    for (int i = 1; i <= cols; i++) {
                        row.addElement(rs.getObject(i));
                    }
                    this.tableData.addElement(row);
                }
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
                this.adjustColumnWidths();
            }else if(actionEvent.getSource() == btnSearchGames){
                String query = "SELECT * FROM GAMES1 WHERE title LIKE '%" + tfdSearchInGames.getText() + "%'";
                this.tableData = new Vector<>();
                this.colNames = new Vector<>();
                tableMode = welcomeViewMode;
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();
                int rows = this.tableModel.getRowCount();
                for (int i = rows - 1; i >= 0; i--) {
                    this.tableModel.removeRow(i);
                }
                for (int i = 1; i <= cols; i++) {
                    this.colNames.addElement(md.getColumnName(i));
                }
                while (rs.next()) {
                    Vector row = new Vector(cols);
                    for (int i = 1; i <= cols; i++) {
                        row.addElement(rs.getObject(i));
                    }
                    this.tableData.addElement(row);
                }
                this.table.setModel(new DefaultTableModel(this.tableData, this.colNames));
                this.adjustColumnWidths();
            }
            if(actionEvent.getSource() != btnNextQuery && actionEvent.getSource() != btnRunAllQueries){
                nextQuery = 0;
            }
        } catch (SQLException e) {
            System.out.println("error");
            System.out.println(e);
        }
    }

    public void runShowAllTables(Statement stmt) throws SQLException{
        tableSelectedLabel.setText("Tables");
        btnDropSelected.setEnabled(false);
        btnShowSelectedTableData.setEnabled(false);

        tableSelectedPanel.update(tableSelectedPanel.getGraphics());
        System.out.println("tables");
        String q = "SELECT table_name as \"TABLE NAMES\" FROM user_tables ORDER BY table_name";
        ResultSet rs = stmt.executeQuery(q);
        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();
        int rows = this.tableModel.getRowCount();
        for(int i = rows - 1; i >= 0; i--){
            this.tableModel.removeRow(i);
        }
        for(int i = 1; i <= cols; i++){
            this.colNames.addElement(md.getColumnName(i));
        }
        while(rs.next()) {
            Vector row = new Vector(cols);
            for(int i = 1; i <= cols; i++){
                row.addElement(rs.getObject(i));
            }
            this.tableData.addElement(row);
        }
        System.out.println(this.tableData);


    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
