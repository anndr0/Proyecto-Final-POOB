package presentation;

import domain.*;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GomokuGUI extends JFrame {
    private Gomoku gomoku;
    private JPanel[][] boardPanels;
    private int currentPlayerTurn=1;

    private Piedra selectedPieceType;



    private Color[] playersColors = {
            new Color(0,0,0,255),
            new Color(255, 255, 255,255),
            new Color(134, 1, 175, 255),
            new Color(167, 25, 75,255),
            new Color(2, 71, 254,255),
            new Color(3, 146, 206, 255)
    };
    private int[] boardPanelSize = {
            5
    };
    private JPanel boardContainerPanel, outerPanel, verticalContainer, panelToTheRight, verticalContainer1, verticalContainer2;
    private ImagePanel outerOuterPanel;
    private String player1Name, player2Name;
    private Color normal1Color, normal2Color, pesada1Color,pesada2Color,temp1Color,temp2Color;


    public GomokuGUI(int size, String player1Name, String player2Name, Color normal1Color,Color normal2Color,Color pesada1Color, Color pesada2Color,Color temp1Color,Color temp2Color ) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        this.normal1Color = normal1Color; playersColors[0] = normal1Color;
        this.normal2Color = normal2Color; playersColors[1] = normal2Color;
        this.pesada1Color = pesada1Color; playersColors[2] = pesada1Color;
        this.pesada2Color = pesada2Color; playersColors[3] = pesada2Color;
        this.temp1Color = temp1Color; playersColors[4] = temp1Color;
        this.temp2Color = temp2Color; playersColors[5] = temp2Color;

        this.boardPanelSize[0] = size;

        setTitle("Gomoku POOs");
        boardPanels = new JPanel[boardPanelSize[0]][boardPanelSize[0]];
        gomoku = new Gomoku(boardPanelSize[0]);
        prepareElements();
        prepareActions();
    }

    public Piedra getSelectedPieceType() {
        return selectedPieceType;
    }


    private void prepareElements() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int) (screenSize.getWidth() * 0.5);
        int height = (int) (screenSize.getHeight() * 0.8);

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
            resetBox();
            disableClickListeners();
            currentPlayerTurn = 0;
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
        gomoku.reiniciarFichas(boardPanelSize[0], 0.25);
        updatePieceNumbers();
    }

    private  void resetBox(){
        gomoku.reiniciarCasillas(0.2);
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

        outerOuterPanel = new ImagePanel(imagePath);
        outerOuterPanel.setLayout(new BorderLayout());
        outerOuterPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new BorderLayout());

        boardContainerPanel = new JPanel();
        fillBoardWithTiles();

        outerPanel.add(boardContainerPanel, BorderLayout.CENTER);
        outerOuterPanel.add(outerPanel, BorderLayout.CENTER);

        panelToTheRight = new JPanel();
        panelToTheRight.setBackground(Color.WHITE);
        panelToTheRight.setPreferredSize(new Dimension(200, outerOuterPanel.getHeight())); // Ajusta el ancho según sea necesario

        JPanel containerPanel = new JPanel(new BorderLayout());

        addVerticalContainersToPanelToTheRight();

        containerPanel.add(outerOuterPanel, BorderLayout.CENTER);
        containerPanel.add(panelToTheRight, BorderLayout.EAST);

        add(containerPanel);
    }

    private void addVerticalContainersToPanelToTheRight() {
        verticalContainer1 = createVerticalContainer1(player1Name, normal1Color, pesada1Color, temp1Color);
        verticalContainer2 = createVerticalContainer2(player2Name,normal2Color, pesada2Color, temp2Color);
        updatePieceNumbers();
        panelToTheRight.setLayout(new GridLayout(2, 1, 0, 10));
        panelToTheRight.add(verticalContainer1);
        panelToTheRight.add(verticalContainer2);

        revalidate();
        repaint();
    }

    private JPanel createVerticalContainer1(String title, Color normalColor, Color pesadaColor, Color tempColor) {
        JPanel verticalContainer = new JPanel(); // Instancia local para createVerticalContainer1
        verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));

        PiecePanel circlePanel = new PiecePanel(normalColor);
        circlePanel.setPreferredSize(new Dimension(40, 40));
        circlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./fuente/LEMONMILK-Medium.otf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            titleLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        titleLabel.setForeground(Color.BLACK);

        topPanel.add(circlePanel);
        topPanel.add(titleLabel);
        topPanel.setBackground(Color.WHITE);

        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(createTitleRow(new String[]{"Normal", "Pesada", "Temporal"}, Color.BLACK));

        topPanel.add(createCircleRowJ1(normalColor, pesadaColor,  tempColor));
        topPanel.add(numberPieces(0,Color.BLACK));

        verticalContainer.add(topPanel);
        verticalContainer.setBackground(Color.WHITE);

        return verticalContainer;
    }

    private JPanel createVerticalContainer2(String title, Color normalColor, Color pesadaColor, Color tempColor) {
        JPanel verticalContainer = new JPanel();
        verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));

        PiecePanel circlePanel = new PiecePanel(normalColor);
        circlePanel.setPreferredSize(new Dimension(40, 40));
        circlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./fuente/LEMONMILK-Medium.otf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            titleLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        titleLabel.setForeground(Color.WHITE);

        topPanel.add(circlePanel);
        topPanel.add(titleLabel);
        topPanel.setBackground(Color.BLACK);

        topPanel.add(createTitleRow(new String[]{"Normal", "Pesada", "Temporal"}, Color.WHITE));

        topPanel.add(createCircleRowJ2(normalColor, pesadaColor, tempColor));
        topPanel.add(numberPieces(1, Color.WHITE));

        verticalContainer.add(topPanel);
        verticalContainer.setBackground(Color.WHITE);

        return verticalContainer;
    }


    private JPanel createCircleRow(Color normalColor, Color pesadaColor, Color tempColor) {
        JPanel circleRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        circleRow.setOpaque(false);

        PiecePanel normalCirclePanel = createPiecePanel(normalColor, PiedraNormal.class);
        circleRow.add(normalCirclePanel);
        PiecePanel pesadaCirclePanel = createPiecePanel(pesadaColor, PiedraPesada.class);
        circleRow.add(pesadaCirclePanel);
        PiecePanel tempCirclePanel = createPiecePanel(tempColor, PiedraTemporal.class);
        circleRow.add(tempCirclePanel);

        return circleRow;
    }



    private PiecePanel createPiecePanel(Color circleColor, Class<?> pieceType) {
        PiecePanel circlePanel = new PiecePanel(circleColor);
        circlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                selectedPieceType = gomoku.getPlayers()[gomoku.getCurrentPlayerIndex()].ObtenerPiedra(pieceType);

            }
        });
        circlePanel.setPreferredSize(new Dimension(40, 40));
        circlePanel.setOpaque(false);
        circlePanel.setLayout(new BorderLayout());
        return circlePanel;
    }

    private JPanel createCircleRowJ1(Color normalColor, Color pesadaColor, Color tempColor) {
        return createCircleRow(normalColor, pesadaColor, tempColor);
    }

    private JPanel createCircleRowJ2(Color normalColor, Color pesadaColor, Color tempColor) {
        return createCircleRow(normalColor, pesadaColor, tempColor);
    }

    private JPanel numberPieces(int player, Color color) {
        JPanel numberRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        numberRow.setOpaque(false);

        // Obtener el contador de fichas por tipo
        HashMap<String, Integer> contadorFichas = gomoku.getPlayers()[player].contarFichasPorTipo();

        // Ordenar los tipos de fichas
        List<String> tiposOrdenados = Arrays.asList("Normal", "Pesada", "Temporal");

        // Crear etiquetas para cada tipo de ficha en el orden especificado
        for (String tipo : tiposOrdenados) {
            int cantidad = contadorFichas.getOrDefault(tipo, 0);
            JLabel numeros = new JLabel("  " + String.valueOf(cantidad));
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./fuente/LEMONMILK-Medium.otf")).deriveFont(12f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                numeros.setFont(customFont);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
            numeros.setForeground(color);
            numeros.setPreferredSize(new Dimension(30, 30));
            numeros.setOpaque(false);
            numberRow.add(numeros);
        }

        return numberRow;
    }

    private void updatePieceNumbers() {
        updateNumberRow(verticalContainer1, 0, Color.BLACK, 0);
        updateNumberRow(verticalContainer2, 1, Color.WHITE, 1);
    }

    private void updateNumberRow(JPanel existingContainer, int player, Color color, int delta) {
        if (existingContainer != null) {
            Component[] components = existingContainer.getComponents();

            if (components.length >= 1 && components[0] instanceof JPanel) {
                JPanel topPanel = (JPanel) components[0];
                Component[] topComponents = topPanel.getComponents();

                if (topComponents.length >= (6 - delta) && topComponents[5 - delta] instanceof JPanel) {
                    JPanel existingNumbersRow = (JPanel) topComponents[5 - delta];
                    existingNumbersRow.removeAll();

                    JPanel newNumberRow = numberPieces(player, color);

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
        Piedra[][] boardState = gomoku.getBoardState();
        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                if (boardState[i][j] != null) {
                    Color piedraColor = obtenerColorDePiedra(boardState[i][j]);
                    PiecePanel piecePanel = new PiecePanel(piedraColor);
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

    private Color obtenerColorDePiedra(Piedra piedra) {
        int jugador = piedra.getJugador();

        if (jugador == 1) {
            if (piedra instanceof PiedraNormal) {
                return playersColors[0];
            } else if (piedra instanceof PiedraPesada) {
                return playersColors[2];
            } else if (piedra instanceof PiedraTemporal) {
                return playersColors[4];
            }
        } else if (jugador == 2) {
            if (piedra instanceof PiedraNormal) {
                return playersColors[1];
            } else if (piedra instanceof PiedraPesada) {
                return playersColors[3];
            } else if (piedra instanceof PiedraTemporal) {
                return playersColors[5];
            }
        }
        return Color.GRAY;
    }



    private JPanel createTitleRow(String[] circleTypes, Color color) {
        JPanel titlesRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        titlesRow.setOpaque(false);

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);

        JLabel typeLabel1 = new JLabel("Fichas disponibles");
        typeLabel1.setHorizontalAlignment(JLabel.CENTER);
        typeLabel1.setForeground(color);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./fuente/LEMONMILK-Medium.otf")).deriveFont(13f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            typeLabel1.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        titleRow.add(typeLabel1, BorderLayout.NORTH);

        JPanel typesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        typesPanel.setOpaque(false);

        for (int i = 0; i < 3; i++) {
            JLabel typeLabel = new JLabel(circleTypes[i]);
            typeLabel.setHorizontalAlignment(JLabel.CENTER);
            typeLabel.setForeground(color);
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./fuente/LEMONMILK-Medium.otf")).deriveFont(10f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                typeLabel.setFont(customFont);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
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

    public void handlePieceClick(int row, int col, Piedra selectedPieceType) {


        if (gomoku.isSpecialCell(row, col, "Mine")){
            JOptionPane.showMessageDialog(this, "¡Cuidado! Caíste en una mina.");
        } else if (gomoku.isSpecialCell(row, col, "Tel")) {
            JOptionPane.showMessageDialog(this, "¡Cuidado! Tu ficha se teletransportó.");
        } else if (gomoku.isSpecialCell(row, col, "Golden")) {
            JOptionPane.showMessageDialog(this, "¡Caiste en una casilla golden! Tu regalo es una nueva ficha.");
            gomoku.handleGoldenCell(row,col);
        }
        gomoku.makeMove(row, col, selectedPieceType);

        updateBoardView();
        updatePieceNumbers();

        int winner = gomoku.checkWinner();
        if (winner != 0) {
            String winnerName = (gomoku.getCurrentPlayerIndex() == 1) ?  player1Name : player2Name;
            GanadorGUI winnerP = new GanadorGUI(winnerName);
            winnerP.setVisible(true);
            winnerBoard(winner);
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
        GomokuGUI window = new GomokuGUI(10,"ANA","NAT", Color.BLACK, Color.WHITE,new Color(134, 1, 175, 255), new Color(167, 25, 75,255),new Color(2, 71, 254,255), new Color(3, 146, 206, 255) );
        window.setVisible(true);
    }
}
