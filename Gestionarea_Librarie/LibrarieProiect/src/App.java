import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class App {
    
    private static Connection connection;
    private static DefaultTableModel tableModel;

public static void main(String[] args) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/librarie";
    String username = "root";
    String password = "";

    try {
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Conectat");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createLoginGUI();
            }
        });
    } catch (SQLException e) {
        System.out.println("Neconectat. Cauza: " + e.getMessage());
    }
}

private static void createLoginGUI() {
    JFrame loginFrame = new JFrame("Login");
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Crearea etichetelor
    JLabel lblUsername = new JLabel("Utilizator:");
    JLabel lblPassword = new JLabel("Parola:");

    // Crearea câmpurilor de text
    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();

    // Crearea butonului de login
    JButton btnLogin = new JButton("Login");
    btnLogin.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if (authenticateUser(username, password)) {
                loginFrame.setVisible(false);
                createAndShowGUI();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Utilizator sau parolă incorecte!");
            }
        }
    });

    // Setarea layout-ului ferestrei de login
    loginFrame.setLayout(new GridLayout(3, 2));
    loginFrame.add(lblUsername);
    loginFrame.add(txtUsername);
    loginFrame.add(lblPassword);
    loginFrame.add(txtPassword);
    loginFrame.add(new JLabel());
    loginFrame.add(btnLogin);

    // Ajustarea dimensiunii și centrarea ferestrei de login pe ecran
    loginFrame.setSize(300, 150);
    loginFrame.setLocationRelativeTo(null);

    // Afisarea ferestrei de login
    loginFrame.setVisible(true);
}

private static boolean authenticateUser(String username, String password) {
    // Verificarea autentificarii pentru username și password
    if (username.equals("test") && (password.equals("test"))) {
        return true;
    } else {
        return false;
    }
}



private static void createAndShowGUI() {


    JFrame frame = new JFrame("Administrare");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JButton btnAfiseazaAngajati = new JButton("Afiseaza Angajati");
        btnAfiseazaAngajati.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAngajatiFrame();
        }
    });

    JButton btnStergeProdus = new JButton("Sterge Produs");
    btnStergeProdus.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            createStergeProdus();
            
        }
    });


    JButton btnAdaugaAngajati = new JButton("Adauga angajat");
    btnAdaugaAngajati.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        createAdaugaAngajat();
    }
});



    JButton btnModificaSalariu = new JButton("Modifica Salariu");
    btnModificaSalariu.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        
        createModificaSalariu();
    }
});



    JButton btnModificaCantitatea = new JButton("Modifica cantitatea");
    btnModificaCantitatea.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        

        createModificaCantitate();
    }
});




    JButton btnAfiseazaProduse = new JButton("Afiseaza Produse");
    btnAfiseazaProduse.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        
        showProduseFrame();
    }
});




    JButton btnAdaugaProdus = new JButton("Adauga Produs");
    btnAdaugaProdus.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        
        createAdaugaProduse();
    }
});



    btnAdaugaProdus.setBounds(470, 420, 150, 40);
    btnAdaugaAngajati.setBounds(470, 50, 150, 40);
    btnAfiseazaAngajati.setBounds(170, 50, 150, 40);
    btnAfiseazaProduse.setBounds(170, 420, 150, 40);
    btnModificaCantitatea.setBounds(470, 170, 150, 40);
    btnModificaSalariu.setBounds(170, 170, 150, 40);
    btnStergeProdus.setBounds(170, 300, 150, 40);;




    frame.getContentPane().setLayout(null); 
    frame.getContentPane().add(btnAfiseazaAngajati);
    frame.getContentPane().add(btnAdaugaAngajati);
    frame.getContentPane().add(btnAfiseazaProduse);
    frame.getContentPane().add(btnAdaugaProdus);
    frame.getContentPane().add(btnModificaSalariu);
    frame.getContentPane().add(btnModificaCantitatea);
    frame.getContentPane().add(btnStergeProdus);





    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);



}






private static void createStergeProdus() {
    JFrame deleteProdusFrame = new JFrame("Stergere Produs");
    deleteProdusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblCodProdus = new JLabel("Cod Produs:");
    JTextField txtCodProdus = new JTextField();

    
    JButton btnSterge = new JButton("Sterge");
    btnSterge.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codProdus = txtCodProdus.getText();

            
            try {
                String selectQuery = "SELECT * FROM produse WHERE codProdus = ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setString(1, codProdus);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    // Produsul exista în baza de date, efectuam stergerea
                    try {
                        String deleteQuery = "DELETE FROM produse WHERE codProdus = ?";
                        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                        deleteStatement.setString(1, codProdus);
                        int rowsAffected = deleteStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(deleteProdusFrame, "Produsul a fost sters din baza de date.");
                        } else {
                            JOptionPane.showMessageDialog(deleteProdusFrame, "Stergerea produsului din baza de date a esuat.");
                        }

                        deleteStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    
                    JOptionPane.showMessageDialog(deleteProdusFrame, "Produsul cu codul specificat nu exista în baza de date.");
                }

                resultSet.close();
                selectStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });

    
    deleteProdusFrame.setLayout(new GridLayout(2, 2));
    deleteProdusFrame.add(lblCodProdus);
    deleteProdusFrame.add(txtCodProdus);
    deleteProdusFrame.add(new JLabel());
    deleteProdusFrame.add(btnSterge);

    
    deleteProdusFrame.setSize(300, 100);
    deleteProdusFrame.setLocationRelativeTo(null);

    
    deleteProdusFrame.setVisible(true);
}







private static void createModificaSalariu() {
    JFrame modifySalariuAngajatFrame = new JFrame("Modificare Salariu Angajat");
    modifySalariuAngajatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblCodAngajat = new JLabel("Cod Angajat:");
    JLabel lblSalariuNou = new JLabel("Salariu Nou:");

    
    JTextField txtCodAngajat = new JTextField();
    JTextField txtSalariuNou = new JTextField();

    
    JButton btnModifica = new JButton("Modifica");
    btnModifica.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codAngajat = txtCodAngajat.getText();
            String salariuNou = txtSalariuNou.getText();

            if (!codAngajat.isEmpty() && !salariuNou.isEmpty()) {
                try {
                    String query = "UPDATE angajati SET salariu = ? WHERE codAngajat = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, salariuNou);
                    statement.setString(2, codAngajat);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(modifySalariuAngajatFrame, "Salariul a fost modificat cu succes.");
                    } else {
                        JOptionPane.showMessageDialog(modifySalariuAngajatFrame, "Nu s-a gasit niciun angajat cu codul specificat.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(modifySalariuAngajatFrame, "A intervenit o eroare la modificarea salariului.");
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(modifySalariuAngajatFrame, "Codul angajatului și salariul nou nu pot fi goale.");
            }
        }
    });

    
    modifySalariuAngajatFrame.setLayout(new GridLayout(3, 2));

    
    modifySalariuAngajatFrame.add(lblCodAngajat);
    modifySalariuAngajatFrame.add(txtCodAngajat);
    modifySalariuAngajatFrame.add(lblSalariuNou);
    modifySalariuAngajatFrame.add(txtSalariuNou);
    modifySalariuAngajatFrame.add(btnModifica);

   
    modifySalariuAngajatFrame.setSize(400, 200);
    modifySalariuAngajatFrame.setLocationRelativeTo(null);

    
    modifySalariuAngajatFrame.setVisible(true);
}








private static void createModificaCantitate() {
    JFrame modifyCantitateProdusFrame = new JFrame("Modificare Cantitate Produs");
    modifyCantitateProdusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblDenumire = new JLabel("codProdus:");
    JLabel lblCantitateNoua = new JLabel("Cantitate Noua:");

    
    JTextField txtDenumire = new JTextField();
    JTextField txtCantitateNoua = new JTextField();

   
    JButton btnModifica = new JButton("Modifica");
    btnModifica.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codProdus = txtDenumire.getText();
            int cantitateNoua = Integer.parseInt(txtCantitateNoua.getText());

           
            try {
                String updateQuery = "UPDATE produse SET cantitate = ? WHERE codProdus = ?";
                PreparedStatement statement = connection.prepareStatement(updateQuery);

                statement.setInt(1, cantitateNoua);
                statement.setString(2, codProdus);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(modifyCantitateProdusFrame, "Cantitatea produsului a fost modificata.");
                } else {
                    JOptionPane.showMessageDialog(modifyCantitateProdusFrame, "Modificarea cantitatii produsului a esuat.");
                }

                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });

    
    modifyCantitateProdusFrame.setLayout(new GridLayout(3, 2));

    
    modifyCantitateProdusFrame.add(lblDenumire);
    modifyCantitateProdusFrame.add(txtDenumire);
    modifyCantitateProdusFrame.add(lblCantitateNoua);
    modifyCantitateProdusFrame.add(txtCantitateNoua);
    modifyCantitateProdusFrame.add(btnModifica);

    
    modifyCantitateProdusFrame.setSize(400, 200);
    modifyCantitateProdusFrame.setLocationRelativeTo(null);

    
    modifyCantitateProdusFrame.setVisible(true);
}






private static void createAdaugaAngajat() {
    JFrame addAngajatFrame = new JFrame("Adaugare Angajat");
    addAngajatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblCodAngajat = new JLabel("Cod Angajat:");
    JLabel lblNume = new JLabel("Nume:");
    JLabel lblPrenume = new JLabel("Prenume:");
    JLabel lblDataNastere = new JLabel("Data Nastere:");
    JLabel lblSalariu = new JLabel("Salariu:");

    
    JTextField txtCodAngajat = new JTextField();
    JTextField txtNume = new JTextField();
    JTextField txtPrenume = new JTextField();
    JTextField txtDataNastere = new JTextField();
    JTextField txtSalariu = new JTextField();

    
    JButton btnAdauga = new JButton("Adauga");
    btnAdauga.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String codAngajat = txtCodAngajat.getText();
        String nume = txtNume.getText();
        String prenume = txtPrenume.getText();
        String dataNastere = txtDataNastere.getText();
        double salariu = Double.parseDouble(txtSalariu.getText());

       
        try {
            String selectQuery = "SELECT * FROM angajati WHERE codAngajat = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, codAngajat);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(addAngajatFrame, "Codul Angajatului este deja atribuit");
            } else {
                // Adaugarea angajatului în baza de date
                try {
                    String insertQuery = "INSERT INTO angajati (codAngajat, nume, prenume, data_nastere, salariu) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                    insertStatement.setString(1, codAngajat);
                    insertStatement.setString(2, nume);
                    insertStatement.setString(3, prenume);
                    insertStatement.setString(4, dataNastere);
                    insertStatement.setDouble(5, salariu);

                    int rowsAffected = insertStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(addAngajatFrame, "Angajatul a fost adăugat în baza de date.");
                    } else {
                        JOptionPane.showMessageDialog(addAngajatFrame, "Adaugarea angajatului în baza de date a esuat.");
                    }

                    insertStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            resultSet.close();
            selectStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
});


    
    addAngajatFrame.setLayout(new GridLayout(7, 2));
    addAngajatFrame.add(lblCodAngajat);
    addAngajatFrame.add(txtCodAngajat);
    addAngajatFrame.add(lblNume);
    addAngajatFrame.add(txtNume);
    addAngajatFrame.add(lblPrenume);
    addAngajatFrame.add(txtPrenume);
    addAngajatFrame.add(lblDataNastere);
    addAngajatFrame.add(txtDataNastere);
    addAngajatFrame.add(lblSalariu);
    addAngajatFrame.add(txtSalariu);
    addAngajatFrame.add(new JLabel()); 
    addAngajatFrame.add(btnAdauga);

    
    addAngajatFrame.pack();

    
    addAngajatFrame.setLocationRelativeTo(null);

    
    addAngajatFrame.setVisible(true);
    
}






private static void createAdaugaProduse() {
    JFrame addProdusFrame = new JFrame("Adaugare Produs");
    addProdusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblDenumire = new JLabel("Denumire:");
    JLabel lblCantitate = new JLabel("Cantitate:");
    JLabel lblPret = new JLabel("Pret:");
    JLabel lblCodProdus = new JLabel("Cod Produs:");

    
    JTextField txtDenumire = new JTextField();
    JTextField txtCantitate = new JTextField();
    JTextField txtPret = new JTextField();
    JTextField txtCodProdus = new JTextField();

    
    JButton btnAdauga = new JButton("Adauga");
    btnAdauga.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String denumire = txtDenumire.getText();
            int cantitate = Integer.parseInt(txtCantitate.getText());
            double pret = Double.parseDouble(txtPret.getText());
            String codProdus = txtCodProdus.getText();

            
            try {
                String selectQuery = "SELECT * FROM produse WHERE codProdus = ? OR denumire = ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setString(1, codProdus);
                selectStatement.setString(2, denumire);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(addProdusFrame, "Produsul cu codul sau denumirea specificata exista deja în baza de date.");
                } else {
                    // Adaugarea produsului în baza de date
                    try {
                        String insertQuery = "INSERT INTO produse (denumire, cantitate, pret, codProdus) VALUES (?, ?, ?, ?)";
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                        insertStatement.setString(1, denumire);
                        insertStatement.setInt(2, cantitate);
                        insertStatement.setDouble(3, pret);
                        insertStatement.setString(4, codProdus);

                        int rowsAffected = insertStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(addProdusFrame, "Produsul a fost adaugat în baza de date.");
                        } else {
                            JOptionPane.showMessageDialog(addProdusFrame, "Adaugarea produsului în baza de date a esuat.");
                        }

                        insertStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                resultSet.close();
                selectStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });

    
    addProdusFrame.setLayout(new GridLayout(5, 2));
    addProdusFrame.add(lblDenumire);
    addProdusFrame.add(txtDenumire);
    addProdusFrame.add(lblCantitate);
    addProdusFrame.add(txtCantitate);
    addProdusFrame.add(lblPret);
    addProdusFrame.add(txtPret);
    addProdusFrame.add(lblCodProdus);
    addProdusFrame.add(txtCodProdus);
    addProdusFrame.add(new JLabel()); 
    addProdusFrame.add(btnAdauga);

   
    addProdusFrame.pack();

    
    addProdusFrame.setLocationRelativeTo(null);

    
    addProdusFrame.setVisible(true);
}





private static void showProduseFrame() {
    

JButton btnExportPDF = new JButton("Export PDF");
btnExportPDF.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        exportTableToPDF();
    }
});
    
JButton btnRefresh = new JButton("Refresh");
btnRefresh.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        refreshTable();
    }
});


JButton btnCautare = new JButton("Căutare");
btnCautare.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Deschideți fereastra pentru căutarea produselor
        createCautareProduseFrame();
    }
});

    JButton btnFiltrare = new JButton("Filtrare");
btnFiltrare.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Deschideți fereastra pentru filtrarea produselor
        createFiltrareProduseFrame();
    }
});
    JFrame produseFrame = new JFrame("Lista Produse");
    produseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Creare model tabel
    tableModel = new DefaultTableModel();
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    produseFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
// Crearea unui panel pentru butoanele de căutare și filtrare
JPanel buttonPanel = new JPanel();

// Adăugarea butonului de filtrare în panel
buttonPanel.add(btnFiltrare);

// Adăugarea butonului de căutare în panel
buttonPanel.add(btnCautare);
buttonPanel.add(btnRefresh);
buttonPanel.add(btnExportPDF);
// Plasarea panelului cu butoane în partea de jos a ferestrei
produseFrame.add(buttonPanel, BorderLayout.SOUTH);

    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM produse");

        // Clear table model
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Get metadata
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Add column names to table model
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            tableModel.addColumn(columnName);
        }

        // Add rows to table model
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    produseFrame.pack();
    produseFrame.setLocationRelativeTo(null);
    produseFrame.setVisible(true);



    
}



private static void exportTableToPDF() {
    Document document = new Document(PageSize.A4);

    try {
        
        PdfWriter.getInstance(document, new FileOutputStream("ListaProduse.pdf"));
        document.open();

        
        document.add(new Paragraph("Lista Produse"));

        
        com.itextpdf.text.pdf.PdfPTable pdfTable = new com.itextpdf.text.pdf.PdfPTable(tableModel.getColumnCount());
        pdfTable.setWidthPercentage(100);

        
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            pdfTable.addCell(tableModel.getColumnName(i));
        }

        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                pdfTable.addCell(tableModel.getValueAt(i, j).toString());
            }
        }

        document.add(pdfTable);
        document.close();

        
        JOptionPane.showMessageDialog(null, "Export PDF realizat cu succes!");

    } catch (DocumentException | FileNotFoundException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Exportul PDF a esuat!");
    }
}



private static void createFiltrareProduseFrame() {
    JFrame filtrareProduseFrame = new JFrame("Filtrare Produse");
    filtrareProduseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblPretMinim = new JLabel("Pret Minim:");
    JTextField txtPretMinim = new JTextField();
    JLabel lblPretMaxim = new JLabel("Pret Maxim:");
    JTextField txtPretMaxim = new JTextField();

    
    JButton btnFiltrare = new JButton("Filtrare");
    btnFiltrare.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            double pretMinim = Double.parseDouble(txtPretMinim.getText());
            double pretMaxim = Double.parseDouble(txtPretMaxim.getText());

            
            filterProduseByPret(pretMinim, pretMaxim);

            
            filtrareProduseFrame.dispose();
        }
    });

    
    filtrareProduseFrame.setLayout(new GridLayout(3, 2));
    filtrareProduseFrame.add(lblPretMinim);
    filtrareProduseFrame.add(txtPretMinim);
    filtrareProduseFrame.add(lblPretMaxim);
    filtrareProduseFrame.add(txtPretMaxim);
    filtrareProduseFrame.add(new JLabel());
    filtrareProduseFrame.add(btnFiltrare);

    filtrareProduseFrame.pack();
    filtrareProduseFrame.setLocationRelativeTo(null);
    filtrareProduseFrame.setVisible(true);
}



private static void filterProduseByPret(double pretMinim, double pretMaxim) {
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM produse WHERE pret >= " + pretMinim + " AND pret <= " + pretMaxim);

        
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            tableModel.addColumn(columnName);
        }

        
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}



private static void createCautareProduseFrame() {
    JFrame cautareProduseFrame = new JFrame("Cautare Produse");
    cautareProduseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    JLabel lblCodProdus = new JLabel("Cod Produs:");
    JTextField txtCodProdus = new JTextField();

    
    JButton btnCautare = new JButton("Cautare");
    btnCautare.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codProdus = txtCodProdus.getText();

            
            searchProduseByCodProdus(codProdus);

            
            cautareProduseFrame.dispose();
        }
    });

    
    cautareProduseFrame.setLayout(new GridLayout(2, 2));
    cautareProduseFrame.add(lblCodProdus);
    cautareProduseFrame.add(txtCodProdus);
    cautareProduseFrame.add(new JLabel());
    cautareProduseFrame.add(btnCautare);

    cautareProduseFrame.pack();
    cautareProduseFrame.setLocationRelativeTo(null);
    cautareProduseFrame.setVisible(true);
}




private static void refreshTable() {
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM produse");

        
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            tableModel.addColumn(columnName);
        }

       
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}




private static void searchProduseByCodProdus(String codProdus) {
    try {
        String selectQuery = "SELECT * FROM produse WHERE codProdus = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, codProdus);
        ResultSet resultSet = selectStatement.executeQuery();

        
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            tableModel.addColumn(columnName);
        }

        
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        selectStatement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}




private static void showAngajatiFrame() {
    JFrame angajatiFrame = new JFrame("Lista Angajati");
    angajatiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    tableModel = new DefaultTableModel();
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    angajatiFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

    JButton btnExportPDF = new JButton("Export PDF");
    btnExportPDF.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exportAngajatiToPDF();
        }
    });

    JButton btnStergere = new JButton("Stergere");
    btnStergere.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String codAngajat = tableModel.getValueAt(selectedRow, 0).toString();
                deleteAngajatByCodAngajat(codAngajat);
            } else {
                JOptionPane.showMessageDialog(angajatiFrame, "Va rugam sa selectați un angajat pentru a-l sterge.");
            }
        }
    });

    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(btnExportPDF);
    buttonPanel.add(btnStergere);

    
    angajatiFrame.add(buttonPanel, BorderLayout.SOUTH);

    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM angajati");

        
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            tableModel.addColumn(columnName);
        }

        
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    angajatiFrame.pack();
    angajatiFrame.setLocationRelativeTo(null);
    angajatiFrame.setVisible(true);
}



private static void deleteAngajatByCodAngajat(String codAngajat) {
    try {
        String deleteQuery = "DELETE FROM angajati WHERE codAngajat = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setString(1, codAngajat);

        int rowsAffected = deleteStatement.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Angajatul a fost sters cu succes.");
            
            refreshAngajatiTable();
        } else {
            JOptionPane.showMessageDialog(null, "Stergerea angajatului a esuat.");
        }

        deleteStatement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}




private static void refreshAngajatiTable() {
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM angajati");

        
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            tableModel.addColumn(columnName);
        }

        
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}




private static void exportAngajatiToPDF() {
    Document document = new Document(PageSize.A4);

    try {
        
        PdfWriter.getInstance(document, new FileOutputStream("ListaAngajati.pdf"));
        document.open();

        
        document.add(new Paragraph("Lista Angajati"));

        
        com.itextpdf.text.pdf.PdfPTable pdfTable = new com.itextpdf.text.pdf.PdfPTable(tableModel.getColumnCount());
        pdfTable.setWidthPercentage(100);

        
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            pdfTable.addCell(tableModel.getColumnName(i));
        }

        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                pdfTable.addCell(tableModel.getValueAt(i, j).toString());
            }
        }

        document.add(pdfTable);
        document.close();

        
        JOptionPane.showMessageDialog(null, "Export PDF realizat cu succes!");

    } catch (DocumentException | FileNotFoundException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Exportul PDF a esuat!");
    }
}

   


}
 

