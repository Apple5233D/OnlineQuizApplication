import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    static String dbUrl = "jdbc:mysql://localhost:3306/online_quiz";
    static String dbUsername = "root";
    static String dbPassword = "Apple5233q";

    static ArrayList<Question> questions = new ArrayList<>();
    static String[] userAnswers;

    static int currentQuestion = 0;
    static int score = 0;
    static int currentQuizId = 0;
    static String currentQuizName = "";
    static String loggedInUsername;

    static JFrame frame;
    static JLabel questionLabel;
    static JRadioButton option1;
    static JRadioButton option2;
    static JRadioButton option3;
    static JRadioButton option4;
    static ButtonGroup group;

    public static void main(String[] args) {
        showLogin();
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    static void showLogin() {
        frame = new JFrame("Online Quiz Application");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel title = new JLabel("Online Quiz Application");
        title.setBounds(130, 40, 250, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(80, 110, 100, 25);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(180, 110, 200, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 150, 100, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(180, 150, 200, 25);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(130, 210, 100, 35);
        frame.add(loginButton);

        JButton registerButton = new JButton("Create Account");
        registerButton.setBounds(245, 210, 130, 35);
        frame.add(registerButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter username and password.");
                return;
            }

            try {
                Connection con = getConnection();

                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    loggedInUsername = username;

                    rs.close();
                    pst.close();
                    con.close();

                    if (username.equalsIgnoreCase("admin")) {
                        showAdminPanel();
                    } else {
                        showHome();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Invalid Username or Password");

                    rs.close();
                    pst.close();
                    con.close();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Database Connection Failed!");
                ex.printStackTrace();
            }
        });

        registerButton.addActionListener(e -> showRegister());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void showRegister() {
        frame.getContentPane().removeAll();

        frame.setTitle("Create Account");
        frame.setSize(500, 450);
        frame.setLayout(null);

        JLabel title = new JLabel("Create Account");
        title.setBounds(170, 40, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(80, 110, 100, 25);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(180, 110, 200, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 150, 100, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(180, 150, 200, 25);
        frame.add(passwordField);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(80, 190, 120, 25);
        frame.add(confirmLabel);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(200, 190, 180, 25);
        frame.add(confirmField);

        JButton createButton = new JButton("Create Account");
        createButton.setBounds(160, 240, 170, 35);
        frame.add(createButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 290, 90, 35);
        frame.add(backButton);

        createButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());

            if (username.isEmpty() ||
                    password.isEmpty() ||
                    confirmPassword.isEmpty()) {

                JOptionPane.showMessageDialog(frame,
                        "Please fill all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame,
                        "Passwords do not match.");
                return;
            }

            try {
                Connection con = getConnection();

                String checkSql = "SELECT * FROM users WHERE username = ?";
                PreparedStatement checkPst = con.prepareStatement(checkSql);

                checkPst.setString(1, username);

                ResultSet rs = checkPst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame,
                            "Username already exists.");

                    rs.close();
                    checkPst.close();
                    con.close();

                    return;
                }

                rs.close();
                checkPst.close();

                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, username);
                pst.setString(2, password);

                pst.executeUpdate();

                pst.close();
                con.close();

                JOptionPane.showMessageDialog(frame,
                        "Account created successfully!");

                showLogin();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Unable to create account.");
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> showLogin());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void showHome() {
        frame.getContentPane().removeAll();

        frame.setTitle("Online Quiz Application");
        frame.setSize(500, 400);
        frame.setLayout(null);

        JLabel title = new JLabel("Online Quiz Application");
        title.setBounds(120, 40, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title);

        JLabel welcome = new JLabel("Welcome, " + loggedInUsername + "!");
        welcome.setBounds(170, 90, 250, 25);
        welcome.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(welcome);

        JButton startButton = new JButton("Start Quiz");
        startButton.setBounds(160, 140, 180, 40);
        frame.add(startButton);

        JButton attemptsButton = new JButton("Previous Attempts");
        attemptsButton.setBounds(160, 195, 180, 40);
        frame.add(attemptsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(160, 250, 180, 40);
        frame.add(logoutButton);

        startButton.addActionListener(e -> showQuizSelection());
        attemptsButton.addActionListener(e -> showPreviousAttempts());

        logoutButton.addActionListener(e -> {
            loggedInUsername = null;
            showLogin();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void showQuizSelection() {
        frame.getContentPane().removeAll();

        frame.setTitle("Select Quiz");
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Select a Quiz", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<Integer> quizIds = new ArrayList<>();

        try {
            Connection con = getConnection();

            String sql = "SELECT id, title FROM quizzes ORDER BY id";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                quizIds.add(rs.getInt("id"));
                model.addElement(rs.getString("title"));
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to load quizzes.");
            ex.printStackTrace();
        }

        JList<String> quizList = new JList<>(model);
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(quizList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton startButton = new JButton("Start Selected Quiz");
        JButton backButton = new JButton("Back");

        bottomPanel.add(startButton);
        bottomPanel.add(backButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            int index = quizList.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(frame,
                        "Please select a quiz.");
                return;
            }

            currentQuizId = quizIds.get(index);
            currentQuizName = quizList.getSelectedValue();

            loadQuestions(currentQuizId);
        });

        backButton.addActionListener(e -> showHome());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void loadQuestions(int quizId) {
        questions.clear();

        try {
            Connection con = getConnection();

            String sql = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY id";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, quizId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Question q = new Question();

                q.id = rs.getInt("id");
                q.quizId = rs.getInt("quiz_id");
                q.question = rs.getString("question");
                q.option1 = rs.getString("option1");
                q.option2 = rs.getString("option2");
                q.option3 = rs.getString("option3");
                q.option4 = rs.getString("option4");
                q.correctAnswer = rs.getString("correct_answer");

                questions.add(q);
            }

            rs.close();
            pst.close();
            con.close();

            if (questions.size() == 15) {
                currentQuestion = 0;
                score = 0;
                userAnswers = new String[15];

                showQuiz();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "This quiz must contain exactly 15 questions.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to load questions!");
            ex.printStackTrace();
        }
    }

    static void showQuiz() {
        frame.getContentPane().removeAll();

        frame.setTitle(currentQuizName);
        frame.setSize(600, 450);
        frame.setLayout(null);

        questionLabel = new JLabel();

        questionLabel.setBounds(40, 40, 520, 50);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        frame.add(questionLabel);

        option1 = new JRadioButton();
        option1.setBounds(60, 120, 450, 30);
        frame.add(option1);

        option2 = new JRadioButton();
        option2.setBounds(60, 165, 450, 30);
        frame.add(option2);

        option3 = new JRadioButton();
        option3.setBounds(60, 210, 450, 30);
        frame.add(option3);

        option4 = new JRadioButton();
        option4.setBounds(60, 255, 450, 30);
        frame.add(option4);

        group = new ButtonGroup();

        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(230, 320, 120, 35);
        frame.add(nextButton);

        displayQuestion();

        nextButton.addActionListener(e -> {
            saveAnswer();

            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                displayQuestion();
            } else {
                calculateScore();
                saveResult();
                showResult();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void displayQuestion() {
        Question q = questions.get(currentQuestion);

        questionLabel.setText(
                "Q" + (currentQuestion + 1) + ". " + q.question
        );

        option1.setText(q.option1);
        option2.setText(q.option2);
        option3.setText(q.option3);
        option4.setText(q.option4);

        group.clearSelection();
    }

    static void saveAnswer() {
        if (option1.isSelected()) {
            userAnswers[currentQuestion] = option1.getText();
        } else if (option2.isSelected()) {
            userAnswers[currentQuestion] = option2.getText();
        } else if (option3.isSelected()) {
            userAnswers[currentQuestion] = option3.getText();
        } else if (option4.isSelected()) {
            userAnswers[currentQuestion] = option4.getText();
        } else {
            userAnswers[currentQuestion] = "Not answered";
        }
    }

    static void calculateScore() {
        score = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] != null &&
                    userAnswers[i].equals(questions.get(i).correctAnswer)) {
                score++;
            }
        }
    }

    static void saveResult() {
        try {
            Connection con = getConnection();

            String sql =
                    "INSERT INTO quiz_results " +
                            "(username, quiz_name, score, total_questions) " +
                            "VALUES (?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, loggedInUsername);
            pst.setString(2, currentQuizName);
            pst.setInt(3, score);
            pst.setInt(4, questions.size());

            pst.executeUpdate();

            pst.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(frame,
                    "Result could not be saved, but your quiz is completed.");
        }
    }

    static void showResult() {
        frame.getContentPane().removeAll();

        frame.setTitle("Quiz Result");
        frame.setSize(750, 650);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        JLabel result = new JLabel(
                "Quiz Completed! Score: " +
                        score + " / " + questions.size()
        );

        result.setFont(new Font("Arial", Font.BOLD, 22));
        result.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(result);
        panel.add(Box.createVerticalStrut(20));

        boolean mistakesFound = false;

        for (int i = 0; i < questions.size(); i++) {
            String userAnswer = userAnswers[i];

            if (userAnswer == null) {
                userAnswer = "Not answered";
            }

            String correctAnswer = questions.get(i).correctAnswer;

            if (!userAnswer.equals(correctAnswer)) {
                mistakesFound = true;

                JLabel mistake = new JLabel(
                        "Q" + (i + 1) +
                                " | Your answer: " +
                                userAnswer +
                                " | Correct answer: " +
                                correctAnswer
                );

                mistake.setFont(new Font("Arial", Font.PLAIN, 14));
                mistake.setAlignmentX(Component.CENTER_ALIGNMENT);

                panel.add(mistake);
                panel.add(Box.createVerticalStrut(10));
            }
        }

        if (!mistakesFound) {
            JLabel perfect =
                    new JLabel("Excellent! You made no mistakes!");

            perfect.setFont(new Font("Arial", Font.BOLD, 16));
            perfect.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(perfect);
        }

        panel.add(Box.createVerticalStrut(20));

        JButton homeButton = new JButton("Back to Home");
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(homeButton);

        homeButton.addActionListener(e -> showHome());

        JScrollPane scrollPane = new JScrollPane(panel);

        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void showPreviousAttempts() {
        frame.getContentPane().removeAll();

        frame.setTitle("Previous Attempts");
        frame.setSize(700, 450);
        frame.setLayout(new BorderLayout());

        JLabel title =
                new JLabel(
                        "Previous Quiz Attempts - " +
                                loggedInUsername,
                        SwingConstants.CENTER
                );

        title.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(title, BorderLayout.NORTH);

        String[] columns = {
                "Quiz",
                "Score",
                "Total",
                "Date"
        };

        ArrayList<String[]> data = new ArrayList<>();

        try {
            Connection con = getConnection();

            String sql =
                    "SELECT quiz_name, score, total_questions, attempt_date " +
                            "FROM quiz_results " +
                            "WHERE username = ? " +
                            "ORDER BY attempt_date DESC";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, loggedInUsername);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String[] row = {
                        rs.getString("quiz_name"),
                        String.valueOf(rs.getInt("score")),
                        String.valueOf(rs.getInt("total_questions")),
                        rs.getTimestamp("attempt_date").toString()
                };

                data.add(row);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to load previous attempts!");
            ex.printStackTrace();
        }

        String[][] tableData = new String[data.size()][4];

        for (int i = 0; i < data.size(); i++) {
            tableData[i] = data.get(i);
        }

        JTable table = new JTable(tableData, columns);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Home");

        backButton.addActionListener(e -> showHome());

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(backButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void showAdminPanel() {
        frame.getContentPane().removeAll();

        frame.setTitle("Admin Panel");
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Admin Quiz Management",
                SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 22));

        frame.add(title, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<Integer> quizIds = new ArrayList<>();

        loadQuizList(model, quizIds);

        JList<String> quizList = new JList<>(model);
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame.add(new JScrollPane(quizList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton createButton = new JButton("Create Quiz");
        JButton editButton = new JButton("Edit Quiz");
        JButton deleteButton = new JButton("Delete Quiz");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        createButton.addActionListener(e -> {
            createQuiz();
        });

        editButton.addActionListener(e -> {
            int index = quizList.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(frame,
                        "Please select a quiz.");
                return;
            }

            editQuiz(quizIds.get(index));
        });

        deleteButton.addActionListener(e -> {
            int index = quizList.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(frame,
                        "Please select a quiz.");
                return;
            }

            deleteQuiz(quizIds.get(index));
        });

        logoutButton.addActionListener(e -> {
            loggedInUsername = null;
            showLogin();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    static void loadQuizList(DefaultListModel<String> model,
                             ArrayList<Integer> quizIds) {

        model.clear();
        quizIds.clear();

        try {
            Connection con = getConnection();

            String sql = "SELECT id, title FROM quizzes ORDER BY id";
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                quizIds.add(rs.getInt("id"));
                model.addElement(
                        rs.getInt("id") + " - " +
                                rs.getString("title")
                );
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to load quizzes.");
            ex.printStackTrace();
        }
    }

    static void createQuiz() {
        JTextField titleField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 1));

        panel.add(new JLabel("Quiz Title:"));
        panel.add(titleField);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Create Quiz",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title = titleField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Quiz title cannot be empty.");
            return;
        }

        int quizId = 0;

        try {
            Connection con = getConnection();

            String sql = "INSERT INTO quizzes (title) VALUES (?)";

            PreparedStatement pst =
                    con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            pst.setString(1, title);
            pst.executeUpdate();

            ResultSet keys = pst.getGeneratedKeys();

            if (keys.next()) {
                quizId = keys.getInt(1);
            }

            keys.close();
            pst.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to create quiz.");
            ex.printStackTrace();
            return;
        }

        for (int i = 1; i <= 15; i++) {
            boolean added = addQuestionDialog(quizId, i);

            if (!added) {
                deleteQuizFromDatabase(quizId);

                JOptionPane.showMessageDialog(frame,
                        "Quiz creation cancelled.");

                showAdminPanel();
                return;
            }
        }

        JOptionPane.showMessageDialog(frame,
                "Quiz created successfully with 15 questions.");

        showAdminPanel();
    }

    static boolean addQuestionDialog(int quizId, int number) {
        JTextField questionField = new JTextField();
        JTextField option1Field = new JTextField();
        JTextField option2Field = new JTextField();
        JTextField option3Field = new JTextField();
        JTextField option4Field = new JTextField();

        JComboBox<String> correctBox =
                new JComboBox<>(
                        new String[]{
                                "Option 1",
                                "Option 2",
                                "Option 3",
                                "Option 4"
                        }
                );

        JPanel panel = new JPanel(new GridLayout(12, 1, 5, 5));

        panel.add(new JLabel("Question " + number));
        panel.add(questionField);

        panel.add(new JLabel("Option 1"));
        panel.add(option1Field);

        panel.add(new JLabel("Option 2"));
        panel.add(option2Field);

        panel.add(new JLabel("Option 3"));
        panel.add(option3Field);

        panel.add(new JLabel("Option 4"));
        panel.add(option4Field);

        panel.add(new JLabel("Correct Answer"));
        panel.add(correctBox);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        int result = JOptionPane.showConfirmDialog(
                frame,
                scrollPane,
                "Add Question " + number,
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return false;
        }

        String question = questionField.getText().trim();
        String op1 = option1Field.getText().trim();
        String op2 = option2Field.getText().trim();
        String op3 = option3Field.getText().trim();
        String op4 = option4Field.getText().trim();

        if (question.isEmpty() ||
                op1.isEmpty() ||
                op2.isEmpty() ||
                op3.isEmpty() ||
                op4.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "All fields are required.");

            return addQuestionDialog(quizId, number);
        }

        String correctAnswer;

        int selected = correctBox.getSelectedIndex();

        if (selected == 0) {
            correctAnswer = op1;
        } else if (selected == 1) {
            correctAnswer = op2;
        } else if (selected == 2) {
            correctAnswer = op3;
        } else {
            correctAnswer = op4;
        }

        try {
            Connection con = getConnection();

            String sql =
                    "INSERT INTO questions " +
                            "(quiz_id, question, option1, option2, option3, option4, correct_answer) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, quizId);
            pst.setString(2, question);
            pst.setString(3, op1);
            pst.setString(4, op2);
            pst.setString(5, op3);
            pst.setString(6, op4);
            pst.setString(7, correctAnswer);

            pst.executeUpdate();

            pst.close();
            con.close();

            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to add question.");
            ex.printStackTrace();
            return false;
        }
    }

    static void editQuiz(int quizId) {
        try {
            Connection con = getConnection();

            String titleSql = "SELECT title FROM quizzes WHERE id = ?";
            PreparedStatement titlePst =
                    con.prepareStatement(titleSql);

            titlePst.setInt(1, quizId);

            ResultSet titleRs = titlePst.executeQuery();

            String oldTitle = "";

            if (titleRs.next()) {
                oldTitle = titleRs.getString("title");
            }

            titleRs.close();
            titlePst.close();
            con.close();

            JTextField titleField = new JTextField(oldTitle);

            JPanel titlePanel = new JPanel(new GridLayout(2, 1));

            titlePanel.add(new JLabel("Quiz Title:"));
            titlePanel.add(titleField);

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    titlePanel,
                    "Edit Quiz",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String newTitle = titleField.getText().trim();

            if (newTitle.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Quiz title cannot be empty.");
                return;
            }

            updateQuizTitle(quizId, newTitle);

            int questionChoice = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to edit all 15 questions?",
                    "Edit Questions",
                    JOptionPane.YES_NO_OPTION
            );

            if (questionChoice == JOptionPane.YES_OPTION) {
                editAllQuestions(quizId);
            }

            JOptionPane.showMessageDialog(frame,
                    "Quiz updated successfully.");

            showAdminPanel();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to edit quiz.");
            ex.printStackTrace();
        }
    }

    static void updateQuizTitle(int quizId, String title) {
        try {
            Connection con = getConnection();

            String sql = "UPDATE quizzes SET title = ? WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, title);
            pst.setInt(2, quizId);

            pst.executeUpdate();

            pst.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void editAllQuestions(int quizId) {
        ArrayList<Question> oldQuestions =
                new ArrayList<>();

        try {
            Connection con = getConnection();

            String sql =
                    "SELECT * FROM questions " +
                            "WHERE quiz_id = ? ORDER BY id";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, quizId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Question q = new Question();

                q.id = rs.getInt("id");
                q.quizId = rs.getInt("quiz_id");
                q.question = rs.getString("question");
                q.option1 = rs.getString("option1");
                q.option2 = rs.getString("option2");
                q.option3 = rs.getString("option3");
                q.option4 = rs.getString("option4");
                q.correctAnswer =
                        rs.getString("correct_answer");

                oldQuestions.add(q);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        if (oldQuestions.size() != 15) {
            JOptionPane.showMessageDialog(frame,
                    "This quiz does not contain exactly 15 questions.");
            return;
        }

        for (int i = 0; i < oldQuestions.size(); i++) {
            Question q = oldQuestions.get(i);

            JTextField questionField =
                    new JTextField(q.question);

            JTextField option1Field =
                    new JTextField(q.option1);

            JTextField option2Field =
                    new JTextField(q.option2);

            JTextField option3Field =
                    new JTextField(q.option3);

            JTextField option4Field =
                    new JTextField(q.option4);

            String correctOption = "Option 1";

            if (q.correctAnswer.equals(q.option2)) {
                correctOption = "Option 2";
            } else if (q.correctAnswer.equals(q.option3)) {
                correctOption = "Option 3";
            } else if (q.correctAnswer.equals(q.option4)) {
                correctOption = "Option 4";
            }

            JComboBox<String> correctBox =
                    new JComboBox<>(
                            new String[]{
                                    "Option 1",
                                    "Option 2",
                                    "Option 3",
                                    "Option 4"
                            }
                    );

            correctBox.setSelectedItem(correctOption);

            JPanel panel = new JPanel(
                    new GridLayout(12, 1, 5, 5)
            );

            panel.add(new JLabel("Question " + (i + 1)));
            panel.add(questionField);

            panel.add(new JLabel("Option 1"));
            panel.add(option1Field);

            panel.add(new JLabel("Option 2"));
            panel.add(option2Field);

            panel.add(new JLabel("Option 3"));
            panel.add(option3Field);

            panel.add(new JLabel("Option 4"));
            panel.add(option4Field);

            panel.add(new JLabel("Correct Answer"));
            panel.add(correctBox);

            JScrollPane scrollPane =
                    new JScrollPane(panel);

            scrollPane.setPreferredSize(
                    new Dimension(500, 400)
            );

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    scrollPane,
                    "Edit Question " + (i + 1),
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String question =
                    questionField.getText().trim();

            String op1 =
                    option1Field.getText().trim();

            String op2 =
                    option2Field.getText().trim();

            String op3 =
                    option3Field.getText().trim();

            String op4 =
                    option4Field.getText().trim();

            if (question.isEmpty() ||
                    op1.isEmpty() ||
                    op2.isEmpty() ||
                    op3.isEmpty() ||
                    op4.isEmpty()) {

                JOptionPane.showMessageDialog(frame,
                        "All fields are required.");

                return;
            }

            String correctAnswer;

            int selected =
                    correctBox.getSelectedIndex();

            if (selected == 0) {
                correctAnswer = op1;
            } else if (selected == 1) {
                correctAnswer = op2;
            } else if (selected == 2) {
                correctAnswer = op3;
            } else {
                correctAnswer = op4;
            }

            updateQuestion(
                    q.id,
                    question,
                    op1,
                    op2,
                    op3,
                    op4,
                    correctAnswer
            );
        }
    }

    static void updateQuestion(
            int questionId,
            String question,
            String op1,
            String op2,
            String op3,
            String op4,
            String correctAnswer) {

        try {
            Connection con = getConnection();

            String sql =
                    "UPDATE questions SET " +
                            "question = ?, " +
                            "option1 = ?, " +
                            "option2 = ?, " +
                            "option3 = ?, " +
                            "option4 = ?, " +
                            "correct_answer = ? " +
                            "WHERE id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, question);
            pst.setString(2, op1);
            pst.setString(3, op2);
            pst.setString(4, op3);
            pst.setString(5, op4);
            pst.setString(6, correctAnswer);
            pst.setInt(7, questionId);

            pst.executeUpdate();

            pst.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void deleteQuiz(int quizId) {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete this quiz?",
                "Delete Quiz",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        deleteQuizFromDatabase(quizId);

        JOptionPane.showMessageDialog(frame,
                "Quiz deleted successfully.");

        showAdminPanel();
    }

    static void deleteQuizFromDatabase(int quizId) {
        try {
            Connection con = getConnection();

            String deleteQuestions =
                    "DELETE FROM questions WHERE quiz_id = ?";

            PreparedStatement questionPst =
                    con.prepareStatement(deleteQuestions);

            questionPst.setInt(1, quizId);
            questionPst.executeUpdate();

            questionPst.close();

            String deleteQuiz =
                    "DELETE FROM quizzes WHERE id = ?";

            PreparedStatement quizPst =
                    con.prepareStatement(deleteQuiz);

            quizPst.setInt(1, quizId);
            quizPst.executeUpdate();

            quizPst.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to delete quiz.");
            ex.printStackTrace();
        }
    }

    static class Question {
        int id;
        int quizId;
        String question;
        String option1;
        String option2;
        String option3;
        String option4;
        String correctAnswer;
    }
}