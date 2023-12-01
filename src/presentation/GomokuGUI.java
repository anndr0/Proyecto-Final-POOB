package presentation;

import domain.Gomoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.File;

public class GomokuGUI extends JFrame {
    private Gomoku gomoku;
    private JPanel[][] boardPanels;
    private int currentPlayerTurn = 1;
    private Color[] playersColors = {
            new Color(0,0,0,255),
            new Color(255, 255, 255,255),
            new Color(134, 1, 175, 255),
            new Color(167, 25, 75,255),
            new Color(2, 71, 254,255),
            new Color(3, 146, 206, 255)
    };
    private int[] boardPanelSize = {
            15
    };
    private JPanel boardContainerPanel, outerPanel, verticalContainer, panelToTheRight, verticalContainer1, verticalContainer2;
    private ImagePanel outerOuterPanel;
    private String player1Name, player2Name;
    private Color normal1Color, normal2Color, pesada1Color,pesada2Color,temp1Color,temp2Color;


    public GomokuGUI(String player1Name, String player2Name, Color normal1Color,Color normal2Color,Color pesada1Color, Color pesada2Color,Color temp1Color,Color temp2Color ) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        this.normal1Color = normal1Color; playersColors[0] = normal1Color;
        this.normal2Color = normal2Color; playersColors[1] = normal2Color;
        this.pesada1Color = pesada1Color; playersColors[2] = pesada1Color;
        this.pesada2Color = pesada2Color; playersColors[3] = pesada2Color;
        this.temp1Color = temp1Color; playersColors[4] = temp1Color;
        this.temp2Color = temp2Color; playersColors[5] = temp2Color;


        setTitle("Gomoku POOs");
        boardPanels = new JPanel[boardPanelSize[0]][boardPanelSize[0]];
        gomoku = new Gomoku(boardPanelSize[0]);
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        int width = 950;
        int height = 850;
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        prepareElementsMenu();
        prepareElementsBoard();
    }

    private void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        prepareActionsMenu();
        prepareActionsBoard();
    }

    private void confirmExit() {
        int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void prepareElementsMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Options");
        menuBar.add(fileMenu);

        String[] menuItems = {"Nuevo", "Abrir", "Guardar", "Exit"};

        for (int i = 0; i < menuItems.length; i++) {
            JMenuItem menuItem = new JMenuItem(menuItems[i]);
            fileMenu.add(menuItem);
            if (i == menuItems.length - 2 || i == 0) {
                fileMenu.addSeparator();
            }
        }
    }

    private void prepareActionsMenu() {
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            JMenu fileMenu = menuBar.getMenu(0);

            int menuItemIndex = 0;
            for (Component component : fileMenu.getMenuComponents()) {
                if (component instanceof JMenuItem) {
                    JMenuItem menuItem = (JMenuItem) component;
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String itemName = menuItem.getText();
                            switch (itemName) {
                                case "Nuevo":
                                    optionNew();
                                    break;
                                case "Abrir":
                                    openFile();
                                    break;
                                case "Guardar":
                                    JOptionPane.showMessageDialog(null, "Función Guardar en construcción");
                                    break;
                                case "Exit":
                                    confirmExit();
                                    break;
                            }
                        }
                    });
                    menuItemIndex++;
                }
            }
        }
    }

    private void optionNew() {
        int option = JOptionPane.showConfirmDialog(null, "¿Quieres empezar un nuevo juego?", "Nuevo juego", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetBoard();
            resetNumbers();
            disableClickListeners();
        }
    }
    private void resetBoard() {
        gomoku.resetGame();

        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                boardPanels[i][j].removeAll();
                boardPanels[i][j].revalidate();
                boardPanels[i][j].repaint();
            }
        }
    }

    private void resetNumbers() {
        gomoku.reiniciarFichas();
        updatePieceNumbers();
    }


    public void disableClickListeners() {
        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                PieceClickListener clickListener = (PieceClickListener) boardPanels[i][j].getMouseListeners()[0];
                clickListener.setIsClickInProgress(false);
            }
        }
    }
    private void prepareElementsBoard() {
        String imagePath = "./images/img.jpg";

        // Crear outerOuterPanel
        outerOuterPanel = new ImagePanel(imagePath);
        outerOuterPanel.setLayout(new BorderLayout());
        outerOuterPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Crear boardContainerPanel y llenar con casillas
        outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new BorderLayout());

        boardContainerPanel = new JPanel();
        fillBoardWithTiles();

        outerPanel.add(boardContainerPanel, BorderLayout.CENTER);
        outerOuterPanel.add(outerPanel, BorderLayout.CENTER);

        // Crear nuevo panel a la derecha de outerOuterPanel
        panelToTheRight = new JPanel();
        // Puedes personalizar este panel según tus necesidades
        panelToTheRight.setBackground(Color.WHITE);
        panelToTheRight.setPreferredSize(new Dimension(200, outerOuterPanel.getHeight())); // Ajusta el ancho según sea necesario

        // Crear contenedor y agregar ambos paneles
        JPanel containerPanel = new JPanel(new BorderLayout());

        //AQUI VA EL METODO
        addVerticalContainersToPanelToTheRight();

        containerPanel.add(outerOuterPanel, BorderLayout.CENTER);
        containerPanel.add(panelToTheRight, BorderLayout.EAST);

        add(containerPanel);
    }

    private void addVerticalContainersToPanelToTheRight() {
        verticalContainer1 = createVerticalContainer(player1Name, normal1Color, pesada1Color, temp1Color);
        verticalContainer2 = createVerticalContainer(player2Name,normal2Color, pesada2Color, temp2Color);
        updatePieceNumbers();
        panelToTheRight.setLayout(new GridLayout(2, 1, 0, 10));
        panelToTheRight.add(verticalContainer1);
        panelToTheRight.add(verticalContainer2);

        revalidate();
        repaint();
    }

    private JPanel createVerticalContainer(String title, Color normalColor, Color pesadaColor, Color tempColor) {
        verticalContainer = new JPanel();
        verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        PiecePanel circlePanel = new PiecePanel(normalColor);
        circlePanel.setPreferredSize(new Dimension(40, 40));
        circlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Center Baseline", Font.CENTER_BASELINE, 20));
        titleLabel.setForeground(Color.BLACK);

        topPanel.add(circlePanel);
        topPanel.add(titleLabel);
        topPanel.setBackground(Color.WHITE);

        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(createTitleRow(new String[]{"Normal", "Pesada", "Temporal"}));

        topPanel.add(createCircleRow(normalColor, pesadaColor,  tempColor));
        topPanel.add(numberPieces(currentPlayerTurn));

        verticalContainer.add(topPanel);
        verticalContainer.setBackground(Color.WHITE);

        return verticalContainer;
    }


    private JPanel createCircleRow(Color normalColor, Color pesadaColor, Color tempColor) {
        JPanel circleRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        circleRow.setOpaque(false);

        Color[] circleColors = {normalColor, pesadaColor, tempColor};

        for (int i = 0; i < 3; i++) {
            Color circleColor = circleColors[i];

            PiecePanel individualCirclePanel = new PiecePanel(circleColor);
            individualCirclePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    updateBoardView();
                }
            });
            individualCirclePanel.setPreferredSize(new Dimension(40, 40));
            individualCirclePanel.setOpaque(false);

            individualCirclePanel.setLayout(new BorderLayout());
            circleRow.add(individualCirclePanel);
        }
        return circleRow;
    }

    private JPanel numberPieces(int player) {
        JPanel numberRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        numberRow.setOpaque(false);

        int[] types = {
                gomoku.contarFichasNormalesPorJugador(player),
                gomoku.contarFichasPesadasPorJugador(player),
                gomoku.contarFichasTemporalesPorJugador(player)
        };

        for (int i = 0; i < 3; i++) {
            int type = types[i];
            JLabel numeros = new JLabel(String.valueOf(type));
            numeros.setPreferredSize(new Dimension(30, 30));
            numeros.setOpaque(false);
            numeros.setLayout(new BorderLayout());
            numberRow.add(numeros);
        }
        return numberRow;
    }

    private void updatePieceNumbers() {
        updateNumberRow(verticalContainer1, 1);
        updateNumberRow(verticalContainer2, 2);
    }

    private void updateNumberRow(JPanel existingContainer, int player) {
        if (existingContainer != null) {
            Component[] components = existingContainer.getComponents();

            if (components.length >= 1 && components[0] instanceof JPanel) {
                JPanel topPanel = (JPanel) components[0];
                Component[] topComponents = topPanel.getComponents();

                if (topComponents.length >= 6 && topComponents[5] instanceof JPanel) {
                    JPanel existingNumbersRow = (JPanel) topComponents[5];
                    existingNumbersRow.removeAll();

                    JPanel newNumberRow = numberPieces(player);

                    for (Component newComponent : newNumberRow.getComponents()) {
                        existingNumbersRow.add(newComponent);
                    }
                    existingNumbersRow.revalidate();
                    existingNumbersRow.repaint();
                }
            }
        }
    }


    private void updateBoardView() {
        int[][] boardState = gomoku.getBoardState();
        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                if (boardState[i][j] != 0) {
                    PiecePanel piecePanel = new PiecePanel(playersColors[boardState[i][j] - 1]);
                    boardPanels[i][j].removeAll();
                    boardPanels[i][j].add(piecePanel);
                }else {
                    boardPanels[i][j].removeAll();
                    disableClickListeners();
                }
            }
        }
        repaint();
    }

    private JPanel createTitleRow(String[] circleTypes) {
        JPanel titlesRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        titlesRow.setOpaque(false);

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);

        JLabel typeLabel1 = new JLabel("Fichas disponibles");
        typeLabel1.setHorizontalAlignment(JLabel.CENTER);
        typeLabel1.setForeground(Color.BLACK);
        titleRow.add(typeLabel1, BorderLayout.NORTH);

        JPanel typesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        typesPanel.setOpaque(false);

        for (int i = 0; i < 3; i++) {
            JLabel typeLabel = new JLabel(circleTypes[i]);
            typeLabel.setHorizontalAlignment(JLabel.CENTER);
            typeLabel.setForeground(Color.BLACK);
            typesPanel.add(typeLabel, BorderLayout.CENTER);
        }

        titleRow.add(typesPanel, BorderLayout.SOUTH);
        titlesRow.add(titleRow, BorderLayout.CENTER);
        return titlesRow;
    }

    private void fillBoardWithTiles() {
        boardContainerPanel.setLayout(new GridLayout(boardPanelSize[0],boardPanelSize[0])); // No hay espacio entre las casillas
        boardContainerPanel.setOpaque(false);
        boardContainerPanel.setLayout(new GridLayout(boardPanelSize[0], boardPanelSize[0]));

        Border blackBorder = BorderFactory.createLineBorder(new Color(52, 30, 18, 255),3);
        boardContainerPanel.setBorder(blackBorder);
        Border emptyBorder = BorderFactory.createEmptyBorder(24, 21, 24, 21);
        boardContainerPanel.setBorder(BorderFactory.createCompoundBorder(blackBorder, emptyBorder));

        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.setPreferredSize(new Dimension(40, 40));
                panel.setBorder(BorderFactory.createLineBorder(new Color(52, 30, 18, 255)));

                panel.setLayout(new OverlayLayout(panel));
                boardPanels[i][j] = panel;
                boardContainerPanel.add(panel);
            }
        }
        addNumberLabels();
    }
    private void addNumberLabels() {
        JPanel topNumbersPanel = new JPanel(new GridLayout(1, boardPanelSize[0], -50, 150));

        for (int i = 1; i <= boardPanelSize[0]; i++) {
            JLabel numberLabel = createNumberLabel(String.valueOf(i));
            topNumbersPanel.setOpaque(false);
            topNumbersPanel.add(numberLabel);
        }

        outerPanel.add(topNumbersPanel, BorderLayout.NORTH);

        JPanel leftNumbersPanel = new JPanel(new GridLayout(boardPanelSize[0], 1, -50, -60));

        for (int i = 1; i <= boardPanelSize[0]; i++) {
            JLabel verticalLabel = createNumberLabel(String.valueOf(i)
                    +"    ");
            leftNumbersPanel.setOpaque(false);
            leftNumbersPanel.add(verticalLabel);
        }

        outerPanel.add(leftNumbersPanel, BorderLayout.WEST);

        JPanel mainGridPanel = new JPanel(new GridLayout(boardPanelSize[0], boardPanelSize[0]));

        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.setPreferredSize(new Dimension(40, 40));
                panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                panel.setLayout(new OverlayLayout(panel));
                mainGridPanel.add(panel);
            }
        }
        outerPanel.add(mainGridPanel, BorderLayout.CENTER);
    }


    private JLabel createNumberLabel(String text) {
        JLabel numberLabel = new JLabel(text, SwingConstants.CENTER);
        numberLabel.setForeground(Color.BLACK);

        return numberLabel;
    }

    private void prepareActionsBoard() {
        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                boardPanels[i][j].addMouseListener(new PieceClickListener(this, i, j));
            }
        }
    }

    public void handlePieceClick(int row, int col) {
        gomoku.makeMove(row, col);
        currentPlayerTurn = (currentPlayerTurn == 1) ? 2 : 1;

        updateBoardView();
        updatePieceNumbers();

        int winner = gomoku.checkWinner();
        if (winner != 0) {
            if(currentPlayerTurn == 1){
                GanadorGUI winnerP = new GanadorGUI(player1Name);
                winnerP.setVisible(true);
                winnerBoard(winner);
            } else if (currentPlayerTurn == 2) {
                GanadorGUI winnerP = new GanadorGUI(player2Name);
                winnerP.setVisible(true);
                winnerBoard(winner);
            }

        } else {
            if (gomoku.isBoardFull()) {
                handleTie();
                disableClickListeners();
            }
        }
        revalidate();
        repaint();
    }

    private void handleTie() {
        gomoku.handleTie();
        int middleRow = gomoku.getSize() / 2;
        //  pintar bonito jiji
        for (int i = 0; i < middleRow; i++) {
            for (int j = 0; j < gomoku.getSize(); j++) {
                PiecePanel piecePanel = new PiecePanel(playersColors[0]);
                boardPanels[i][j].removeAll();
                boardPanels[i][j].add(piecePanel);
                boardPanels[i][j].revalidate();
                boardPanels[i][j].repaint();
            }
        }
        //fila de la mitad
        for (int i = middleRow; i == middleRow; i++) {
            for (int j = 0; j < gomoku.getSize(); j++) {
                HalfCircle piecePanel = new HalfCircle(playersColors[0],playersColors[1]);
                piecePanel.setOpaque(false);
                boardPanels[i][j].removeAll();
                boardPanels[i][j].add(piecePanel);
                boardPanels[i][j].revalidate();
                boardPanels[i][j].repaint();
            }
        }
        for (int i = middleRow+1; i < gomoku.getSize(); i++) {
            for (int j = 0; j < gomoku.getSize(); j++) {
                PiecePanel piecePanel = new PiecePanel(playersColors[1]);
                boardPanels[i][j].removeAll();
                boardPanels[i][j].add(piecePanel);
                boardPanels[i][j].revalidate();
                boardPanels[i][j].repaint();
            }
        }
        EmpateGUI empate = new EmpateGUI();
        empate.setVisible(true);

    }

    private void winnerBoard(int winner) {
        gomoku.resetGame();
        currentPlayerTurn = winner;

        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                PiecePanel piecePanel = new PiecePanel(playersColors[winner-1]);
                boardPanels[i][j].removeAll();
                boardPanels[i][j].add(piecePanel);
                boardPanels[i][j].revalidate();
                boardPanels[i][j].repaint();
            }
        }
        disableClickListeners();
    }


    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Abriendo archivo: " + selectedFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        GomokuGUI window = new GomokuGUI("ANA","NAT", Color.BLACK, Color.WHITE,new Color(134, 1, 175, 255), new Color(167, 25, 75,255),new Color(2, 71, 254,255), new Color(3, 146, 206, 255) );
        window.setVisible(true);
    }
}
